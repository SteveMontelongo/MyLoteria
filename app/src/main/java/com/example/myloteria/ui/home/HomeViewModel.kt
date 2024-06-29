package com.example.myloteria.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myloteria.Cards
import com.example.myloteria.model.Card

class HomeViewModel : ViewModel() {

    private var _cards = MutableLiveData<MutableList<Card>>().apply {
        value = emptyList<Card>().toMutableList()
    }
    var cards: LiveData<MutableList<Card>> = _cards

    private var _usedCards = MutableLiveData<MutableList<Card>>().apply {
        value = emptyList<Card>().toMutableList()
    }
    var usedCards: LiveData<MutableList<Card>> = _usedCards

    var currentCard: Card


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    init {
        // Load or fetch your images here and post the value
        _cards.postValue(
            Cards.getCards()
        )
        currentCard = Card(0, 0, "")

    }

    fun drawCard(){
        if(cards.value!!.size > 0) {
            currentCard = cards.value!!.get(cards.value!!.size - 1)
            cards.value!!.removeAt(cards.value!!.size - 1)
            if (currentCard.id != 0) {
                usedCards.value!!.add(currentCard as Card)
                Log.d("HomeViewModel", usedCards.value.toString())
            }
        }
    }

    fun play(){
        drawCard()
    }

    fun shuffle(){

    }
}