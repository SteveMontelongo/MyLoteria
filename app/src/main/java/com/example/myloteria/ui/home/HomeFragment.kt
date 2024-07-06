package com.example.myloteria.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.myloteria.R
import com.example.myloteria.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import java.io.File

class HomeFragment : Fragment(), TextToSpeech.OnInitListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var tts: TextToSpeech

    private lateinit var mediaPlayer: MediaPlayer
    private val PERMISSION_REQUEST_CODE = 1001
    private var permissionToRecordAccepted = false
    private val permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        tts = TextToSpeech(this.context, this)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val crossFadeFactory = DrawableCrossFadeFactory.Builder(500)
            .setCrossFadeEnabled(true)
            .build()
        val sharedPreferences = requireContext().getSharedPreferences("MyPreferences",
            AppCompatActivity.MODE_PRIVATE
        )
        binding.progressBar.progress = 0
        binding.progressBar.max = 1000
        binding.progressBar.visibility = INVISIBLE

        homeViewModel.currentCard.observe(viewLifecycleOwner, Observer { it ->
            if(homeViewModel.haveCurrentCard()) {
                Glide.with(this)
                    .load(it.image)
                    .apply(RequestOptions().fitCenter().transform(RoundedCorners(25)))
                    .transition(DrawableTransitionOptions.withCrossFade(crossFadeFactory))
                    .into(binding.card)
                val customVoice = sharedPreferences.getBoolean("custom", false)
                if(!customVoice) {
                    speakOut(it.name)
                }else{
                    homeViewModel.setName(homeViewModel.currentCard.value!!.name)
                    playRecording()
                }
            }else{
                Glide.with(this)
                    .load(R.drawable.card_back)
                    .apply(RequestOptions().fitCenter().transform(RoundedCorners(25)))
                    .transition(DrawableTransitionOptions.withCrossFade(crossFadeFactory))
                    .into(binding.card)
            }


        })
        homeViewModel.history_1.observe(viewLifecycleOwner, Observer {
            //binding.history1.setImageResource(it)
            Glide.with(this).load(it).apply(
                RequestOptions().fitCenter().transform(
                    RoundedCorners(25)
                ).placeholder(R.drawable.card_back).error(R.drawable.card_back)
            ).into(binding.history1)
        })
        homeViewModel.history_2.observe(viewLifecycleOwner, Observer {
            //binding.history2.setImageResource(it)
            Glide.with(this).load(it).apply(
                RequestOptions().fitCenter().transform(
                    RoundedCorners(25)
                ).placeholder(R.drawable.card_back).error(R.drawable.card_back)
            ).into(binding.history2)
        })
        homeViewModel.history_3.observe(viewLifecycleOwner, Observer {
            //binding.history3.setImageResource(it)
            Glide.with(this).load(it).apply(
                RequestOptions().fitCenter().transform(
                    RoundedCorners(25)
                ).placeholder(R.drawable.card_back).error(R.drawable.card_back)
            ).into(binding.history3)
        })
        homeViewModel.history_4.observe(viewLifecycleOwner, Observer {
            binding.history4.setImageResource(it)
            Glide.with(this).load(it).apply(
                RequestOptions().fitCenter().transform(
                    RoundedCorners(25)
                ).placeholder(R.drawable.card_back).error(R.drawable.card_back)
            ).into(binding.history4)
        })
        homeViewModel.progress.observe(viewLifecycleOwner, Observer { progress ->
            Log.d("HomeFragment", progress.toString())
            binding.progressBar.setProgress(progress.toInt(), true)
        })
        binding.play.setOnClickListener { view ->
            binding.progressBar.visibility = VISIBLE
//            Snackbar.make(view, "Play", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            val playSpeed = (sharedPreferences.getInt("speed", 4) *1000).toLong()
            homeViewModel.setTime(playSpeed)
            binding.progressBar.max = playSpeed.toInt()
            Log.d("HomeFragment", "max" + playSpeed.toString())

            if(homeViewModel.initialCardPlay){
                speakOut("Corre i te vas con")
                GlobalScope.launch{
                    //delay(2000)
                    if(!homeViewModel.play()) {
                        binding.play.setImageResource(R.drawable.ic_play)
                    }else{
                        binding.play.setImageResource(R.drawable.ic_pause)
                    }
                }
            }else{
                if(!homeViewModel.play()) {
                    binding.play.setImageResource(R.drawable.ic_play)
                }else{
                    binding.play.setImageResource(R.drawable.ic_pause)
                }
            }

        }
        binding.shuffle.setOnClickListener{ view ->
            binding.progressBar.visibility = INVISIBLE
            var snackbar = Snackbar.make(view, "Shuffled", Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
            snackbar.setAnchorView(R.id.card)
            snackbar.show()
            homeViewModel.shuffleCards()
            binding.play.setImageResource(R.drawable.ic_play)
        }
    }

    override fun onDestroyView() {
        if (tts.isSpeaking) {
            tts.stop()
        }
        tts.shutdown()
        super.onDestroyView()
        _binding = null
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale("es", "MX"))

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
                println("TTS: Language not supported")
            } else {
                // TTS is ready to use.
                tts.setPitch(1f)
                tts.setSpeechRate(1f)

//                speakOut("Bienvenida a mi loteria")
            }
        } else {
            println("TTS: Initialization failed")
        }
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun playRecording() {
        val audioFile = "${requireContext().externalCacheDir?.absolutePath}/" + homeViewModel.fileName
        var file = File(requireContext().externalCacheDir, homeViewModel.fileName.value)
        if(!file.exists()) {
            speakOut(homeViewModel.currentCard.value!!.name)
            Log.d("HomeFragment", "File NOT Found (AI VOICE PLAYING): " + file.absolutePath)
            return
        }else{
            Log.d("HomeFragment", "File Found: " + file.absolutePath)
        }
        Log.d("RecordActivity", "filename " + audioFile)
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(file.absolutePath)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("AudioPlayTest", "prepare() failed")
            }
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this.context!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }

}