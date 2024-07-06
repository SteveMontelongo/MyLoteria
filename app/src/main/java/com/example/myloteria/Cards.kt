package com.example.myloteria

import com.example.myloteria.model.Card

object Cards {
    private var _cards = emptyList<Card>().toMutableList()
    private var _cardsGallery = emptyList<Card>().toMutableList()
    fun addCards(cards: MutableList<Card>){
        for(card in cards){
            _cards.add(card)
        }
    }
    fun getCards(): MutableList<Card>{
        return _cards
    }
    fun setGallery(cards: MutableList<Card>){
        for(card in cards){
            _cardsGallery.add(card)
        }
    }
    fun getGallery(): MutableList<Card>{
        return _cardsGallery
    }
}