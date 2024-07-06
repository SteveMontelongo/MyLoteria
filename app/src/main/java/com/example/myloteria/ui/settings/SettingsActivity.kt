package com.example.myloteria.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myloteria.R
import com.example.myloteria.databinding.ActivityRecordBinding
import com.example.myloteria.databinding.SettingsActivityBinding
import com.example.myloteria.ui.home.HomeViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: SettingsActivityBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val playSpeed = sharedPreferences.getInt("speed", 4)
        var customVoice = sharedPreferences.getBoolean("custom", false)

        binding.aiSwitch.isChecked = customVoice

        Log.d("Speed", playSpeed.toString())
        settingsViewModel.setSpeed(playSpeed)
        settingsViewModel.speed.observe(this, Observer {
            binding.playbackSpeed.text = it.toString()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt("speed", it)
            editor.apply()
            if(it < 4){
                binding.decrementButton.visibility = INVISIBLE
            }else{
                binding.decrementButton.visibility = VISIBLE
            }
            if(it > 10){
                binding.incrementButton.visibility = INVISIBLE
            }else{
                binding.incrementButton.visibility = VISIBLE
            }
        })
        binding.decrementButton.setOnClickListener{
            Log.d("Speed", settingsViewModel.speed.toString())
            settingsViewModel.decreaseSpeed()
        }
        binding.incrementButton.setOnClickListener{
            Log.d("Speed", settingsViewModel.speed.toString())
            settingsViewModel.increaseSpeed()
        }
        binding.aiSwitch.setOnClickListener{
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("custom", binding.aiSwitch.isChecked)
            editor.apply()
            Log.d("SettingsActivity", binding.aiSwitch.isChecked.toString())
        }
    }
}