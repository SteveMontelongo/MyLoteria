package com.example.myloteria

import com.example.myloteria.model.Card

object Cards {
    private var _cards = emptyList<Card>().toMutableList()
    fun addCards(cards: MutableList<Card>){
        for(card in cards){
            _cards.add(card)
        }
    }
    fun getCards(): MutableList<Card>{
        return _cards
    }
}