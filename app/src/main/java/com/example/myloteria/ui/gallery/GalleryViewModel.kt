package com.example.myloteria.ui.gallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myloteria.Cards
import com.example.myloteria.model.Card
import com.example.myloteria.viewmodel.CardViewModel

class GalleryViewModel : ViewModel() {
    private var _cards = MutableLiveData<MutableList<Card>>().apply {
        value = emptyList<Card>().toMutableList()
    }
    var cards: LiveData<MutableList<Card>> = _cards
    private val _text = MutableLiveData<String>().apply {
        value = "Gallery"
    }
    val text: LiveData<String> = _text

    init {
        // Load or fetch your images here and post the value
        _cards.value = Cards.getGallery()

    }

    fun loadGallery(): MutableList<Card>{
        Log.d("GalleryViewModel", cards.value.toString())
        return cards.value!!
    }

}