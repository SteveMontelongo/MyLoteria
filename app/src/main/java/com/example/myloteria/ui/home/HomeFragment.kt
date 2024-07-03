package com.example.myloteria.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.myloteria.R
import com.example.myloteria.adapter.GalleryAdapter
import com.example.myloteria.databinding.FragmentHomeBinding
import com.example.myloteria.ui.gallery.GalleryViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.Date
import java.util.Timer

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val crossFadeFactory = DrawableCrossFadeFactory.Builder(500)
            .setCrossFadeEnabled(true)
            .build()
        homeViewModel.currentCard.observe(viewLifecycleOwner, Observer { it ->
            if(homeViewModel.haveCurrentCard()) {
                Glide.with(this)
                    .load(it.image)
                    .apply(RequestOptions().fitCenter().transform(RoundedCorners(25)))
                    .transition(DrawableTransitionOptions.withCrossFade(crossFadeFactory))
                    .into(binding.card)
            }else{
                Glide.with(this)
                    .load(R.drawable.card_back)
                    .apply(RequestOptions().fitCenter().transform(RoundedCorners(25)))
                    .transition(DrawableTransitionOptions.withCrossFade(crossFadeFactory))
                    .into(binding.card)
            }


        })
        homeViewModel.history_1.observe(viewLifecycleOwner, Observer {
            //binding.history1.setImageResource(it)
            Glide.with(this).load(it).apply(
                RequestOptions().fitCenter().transform(
                    RoundedCorners(25)
                ).placeholder(R.drawable.card_back).error(R.drawable.card_back)
            ).into(binding.history1)
        })
        homeViewModel.history_2.observe(viewLifecycleOwner, Observer {
            //binding.history2.setImageResource(it)
            Glide.with(this).load(it).apply(
                RequestOptions().fitCenter().transform(
                    RoundedCorners(25)
                ).placeholder(R.drawable.card_back).error(R.drawable.card_back)
            ).into(binding.history2)
        })
        homeViewModel.history_3.observe(viewLifecycleOwner, Observer {
            //binding.history3.setImageResource(it)
            Glide.with(this).load(it).apply(
                RequestOptions().fitCenter().transform(
                    RoundedCorners(25)
                ).placeholder(R.drawable.card_back).error(R.drawable.card_back)
            ).into(binding.history3)
        })
        homeViewModel.history_4.observe(viewLifecycleOwner, Observer {
            binding.history4.setImageResource(it)
            Glide.with(this).load(it).apply(
                RequestOptions().fitCenter().transform(
                    RoundedCorners(25)
                ).placeholder(R.drawable.card_back).error(R.drawable.card_back)
            ).into(binding.history4)
        })
        binding.play.setOnClickListener { view ->
//            Snackbar.make(view, "Play", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            val isPlay = homeViewModel.play()
            if(!isPlay) {
                binding.play.setImageResource(R.drawable.ic_play)
            }else{
                binding.play.setImageResource(R.drawable.ic_pause)
            }
        }
        binding.shuffle.setOnClickListener{ view ->
            var snackbar = Snackbar.make(view, "Shuffled", Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
            snackbar.setAnchorView(R.id.card)
            snackbar.show()
            homeViewModel.shuffleCards()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}