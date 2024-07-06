package com.example.myloteria.ui.record

import android.Manifest.permission.RECORD_AUDIO
import android.content.Context
import android.media.MediaRecorder
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.myloteria.R
import com.example.myloteria.databinding.ActivityRecordBinding
import com.example.myloteria.databinding.ContentRecordBinding
import com.example.myloteria.databinding.FragmentSlideshowBinding
import com.example.myloteria.ui.slideshow.SlideshowViewModel
import com.example.myloteria.util.Timer
import java.io.File
import java.io.IOException

class RecordActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRecordBinding
    private lateinit var itemName: String
    private lateinit var recordViewModel: RecordViewModel

    private var mediaPlayer: MediaPlayer? = null
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: String = ""
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private var permissionToRecordAccepted = false
    private val permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private var recording: Boolean = false
    private var myTimer = Timer(){
        isRecording()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        recordViewModel =
            ViewModelProvider(this).get(RecordViewModel::class.java)

        setContentView(binding.root)
        binding.contentRecord.recordAudio.setOnClickListener { view ->
            val snackbar = Snackbar.make(view, "Recording", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
            snackbar.anchorView = binding.contentRecord.soundImage
            snackbar.show()
            myTimer.startTimer(2500)
        }
        binding.contentRecord.playAudio.setOnClickListener { view ->
            val snackbar = Snackbar.make(view, "Audio played", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
            snackbar.anchorView = binding.contentRecord.soundImage
            snackbar.show()
            playRecording()
        }

        binding.contentRecord.deleteAudio.setOnClickListener{ view ->
            deleteRecording()
        }

        recordViewModel.setName(intent.getStringExtra("name")!!)
        Glide.with(this)
            .load(intent.getIntExtra("image", R.drawable.card_back))
            .apply(RequestOptions().fitCenter().transform(RoundedCorners(25)))
            .into(binding.contentRecord.soundImage)
        binding.contentRecord.galleryItemLabel.text = recordViewModel.name.value
        audioFile = "${externalCacheDir?.absolutePath}/"+ recordViewModel.fileName.value
        if (audioFile == null || !File(audioFile).exists()) {
            binding.contentRecord.playAudio.visibility = INVISIBLE
            binding.contentRecord.deleteAudio.visibility = INVISIBLE
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    private fun startRecording() {
        Log.d("RecordActivity", "filename " + audioFile)
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioFile)
            try {
                binding.contentRecord.recordAudio.visibility = INVISIBLE
                binding.contentRecord.playAudio.visibility = INVISIBLE
                prepare()
            } catch (e: IOException) {
                Log.e("AudioRecordTest", "prepare() failed")
            }
            start()
        }
    }

    private fun stopRecording() {
        try{
            binding.contentRecord.recordAudio.visibility = VISIBLE
            binding.contentRecord.playAudio.visibility = VISIBLE
            binding.contentRecord.deleteAudio.visibility = VISIBLE
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            myTimer.cancelTimer()
        }catch(e: Exception){

        }
    }

    override fun onStop() {
        super.onStop()
        mediaRecorder?.release()
        mediaRecorder = null
    }

    private fun playRecording() {
        Log.d("RecordActivity", "filename " + audioFile)
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(audioFile)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("AudioPlayTest", "prepare() failed")
            }
        }
    }

    private fun deleteRecording(){
        if (audioFile == null || !File(audioFile!!).exists()) {
            Toast.makeText(this, "Recording does not exist!", Toast.LENGTH_SHORT).show()
            return
        }

        val file = File(audioFile!!)
        if (file.delete()) {
            Toast.makeText(this, "Recording deleted", Toast.LENGTH_SHORT).show()
            binding.contentRecord.playAudio.visibility = INVISIBLE
            binding.contentRecord.deleteAudio.visibility = INVISIBLE
        } else {
            Toast.makeText(this, "Failed to delete recording", Toast.LENGTH_SHORT).show()
        }
    }

    fun isRecording(){
        if(recording){
            stopRecording()
        }else{
            startRecording()
        }
        recording =  !recording
    }

}