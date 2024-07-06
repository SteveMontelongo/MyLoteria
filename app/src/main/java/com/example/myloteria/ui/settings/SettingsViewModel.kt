package com.example.myloteria.ui.settings

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel: ViewModel() {

    private val _speed = MutableLiveData<Int>()
    val speed: LiveData<Int> get() = _speed

    fun setSpeed(int: Int){
        _speed.value = int
    }
    fun increaseSpeed(){
        _speed.value = _speed.value?.plus(1)
    }

    fun decreaseSpeed(){
        _speed.value = _speed.value?.minus(1)
    }

}