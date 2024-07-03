package com.example.myloteria.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myloteria.Cards
import com.example.myloteria.R
import com.example.myloteria.model.Card
import java.util.*
import com.example.myloteria.util.Timer

class HomeViewModel() : ViewModel() {

    private var _cards = MutableLiveData<MutableList<Card>>().apply {
        value = emptyList<Card>().toMutableList()
    }
    var cards: LiveData<MutableList<Card>> = _cards

    private var _usedCards: MutableList<Card> = emptyList<Card>().toMutableList()

    private val _currentCard = MutableLiveData<Card>()

    val currentCard: LiveData<Card> get() = _currentCard

    private var playState = false

    private var _history_1 = MutableLiveData<Int>()
    val history_1: LiveData<Int> get() = _history_1
    private var _history_2 = MutableLiveData<Int>()
    val history_2: LiveData<Int> get() = _history_2
    private var _history_3 = MutableLiveData<Int>()
    val history_3: LiveData<Int> get() = _history_3
    private var _history_4 = MutableLiveData<Int>()
    val history_4: LiveData<Int> get() = _history_4

    private var timer:com.example.myloteria.util.Timer = com.example.myloteria.util.Timer(){drawCard()}

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    init {
        // Load or fetch your images here and post the value
        _cards.postValue(
            Cards.getCards()
        )
//        timer = Timer(){
//            drawCard()
//        }
//        timer.startTimer()
//        timer.cancelTimer()
        _currentCard.value = Card(0, 0, "")
        shuffleCards()
    }

    fun drawCard(){
        if(cards.value!!.size > 0) {
            _currentCard.value = cards.value!!.get(cards.value!!.size - 1)
            cards.value!!.removeAt(cards.value!!.size - 1)
            if (currentCard.value!!.id != 0) {
                _usedCards.add(currentCard.value!!)
                Log.d("HomeViewModel", _usedCards.toString())
                updateHistory()
            }
            Log.d("HomeViewModel", "Card Drawn")
        }
    }

    fun haveCurrentCard(): Boolean{
        return currentCard.value!!.id != 0
    }

    fun play(): Boolean{
        if(!playState){
            timer = Timer(){
                drawCard()
            }
            timer.startTimer()
        }else{
            timer.cancelTimer()
        }
        playState = !playState
        Log.d("HomeViewModel", "Play")
        return playState
    }

    fun shuffleCards(){
        var tempStack: Stack<Card> = Stack()
        while(_usedCards.size >0){
            cards.value!!.add(_usedCards[_usedCards.size - 1])
            _usedCards.remove(_usedCards[_usedCards.size - 1])
        }

        while(cards.value!!.size > 0){
            val indexSel = (0.. cards.value!!.size -1).random()
            tempStack.add(cards.value!![indexSel])
            cards.value!!.removeAt(indexSel)
        }
        while(!tempStack.isEmpty()){
            cards.value!!.add(tempStack.pop())
        }

        _currentCard.value = Card(0, 0, "")
        updateHistory()

        Log.d("HomeViewModel", "Cards Shuffled")
    }

    private fun updateHistory(){
        _history_1.value = if(_usedCards.size > 1) _usedCards[_usedCards.size -2].image else R.drawable.card_back
        _history_2.value = if(_usedCards.size > 2) _usedCards[_usedCards.size -3].image else R.drawable.card_back
        _history_3.value = if(_usedCards.size > 3) _usedCards[_usedCards.size -4].image else R.drawable.card_back
        _history_4.value = if(_usedCards.size > 4) _usedCards[_usedCards.size -5].image else R.drawable.card_back
    }
}