package com.example.myloteria.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myloteria.R
import com.example.myloteria.adapter.GalleryAdapter
import com.example.myloteria.databinding.FragmentHomeBinding
import com.example.myloteria.ui.gallery.GalleryViewModel
import com.google.android.material.snackbar.Snackbar

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
        binding.play.setOnClickListener { view ->
//            Snackbar.make(view, "Play", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            homeViewModel.play()
            binding.card.setImageResource(homeViewModel.currentCard.image)

            homeViewModel.usedCards.observe(viewLifecycleOwner) {
                binding.history1.setImageResource(if(it.size > 4) it[it.size - 5].image else R.drawable.card_back)
                binding.history2.setImageResource(if(it.size > 3) it[it.size - 4].image else R.drawable.card_back)
                binding.history3.setImageResource(if(it.size > 2) it[it.size - 3].image else R.drawable.card_back)
                binding.history4.setImageResource(if(it.size > 1) it[it.size - 2].image else R.drawable.card_back)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}