package com.example.myloteria.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.example.myloteria.model.Card

class CardViewModel: ViewModel() {

    private var cards = emptyList<Card>().toMutableList()
    fun createCard(id: Int, image: Int, name: String): Card {
        val card: Card = Card(id, image, name)
        cards.add(card)
        return card
    }

    fun deleteCardById(id: Int){
        for ((i, card) in cards.withIndex()){
            if(card.id == id){
                cards.removeAt(i)
                break
            }
        }
    }

    fun retrieveCards(): MutableList<Card>{
        return cards
    }

}