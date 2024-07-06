package com.example.myloteria.ui.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecordViewModel: ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String> get() = _fileName

    fun setName(data: String){
        _name.value = data
        _fileName.value = name.value.toString()
        _fileName.value = _fileName.value!!.lowercase()
        _fileName.value = _fileName.value!!.replace(" ", "_", true)
        _fileName.value = _fileName.value!! + ".3gp"
    }
}