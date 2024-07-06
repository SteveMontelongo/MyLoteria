package com.example.myloteria.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myloteria.R
import com.example.myloteria.`interface`.GalleryOnClick
import com.example.myloteria.model.Card
import com.example.myloteria.viewmodel.CardViewModel
import kotlin.math.absoluteValue

class GalleryAdapter(listener: GalleryOnClick): ListAdapter<Card, GalleryAdapter.GalleryViewHolder>(DiffCallback()){
    val _listener = listener
    class GalleryViewHolder(view: View): RecyclerView.ViewHolder(view){
//        val idView: TextView = view.findViewById<TextView>(R.id.cardId)
        val imageView: ImageView = view.findViewById<ImageView>(R.id.cardImage)
        fun bind(card: Card) {
            Glide.with(imageView.context).clear(imageView)
            Glide.with(imageView.context).load(card.image).apply(
                RequestOptions().fitCenter().transform(
                    RoundedCorners(25)
                ).placeholder(R.drawable.card_back).error(R.drawable.card_back)
            ).into(imageView)
//            idView.text = card.id.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val galleryView = inflater.inflate(R.layout.item_gallery, parent, false)
        return GalleryViewHolder(galleryView)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val card: Card = getItem(position.absoluteValue)
        Log.d("GalleryAdapter", "Binding Card: $card")
        holder.imageView.setOnClickListener {
            _listener.itemClick(card.name, card.image)
        }
        holder.bind(card)



    }

//    override fun getItemCount(): Int {
//        return cards.size
//    }

    class DiffCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }
    }
}