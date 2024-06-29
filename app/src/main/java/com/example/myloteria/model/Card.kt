package com.example.myloteria.model

import android.graphics.drawable.Drawable

data class Card(
    val id: Int,
    val image: Int,
    val name: String
) {
    override fun toString(): String {
        return "Card(id='$id', image='$image', name='$name')"
    }
}