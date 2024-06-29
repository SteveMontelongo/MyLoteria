package com.example.myloteria.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myloteria.adapter.GalleryAdapter
import com.example.myloteria.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
//        val textView: TextView = binding.textGallery
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val recyclerView = binding.galleryRecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        val galleryAdapter = GalleryAdapter()
        recyclerView.adapter = galleryAdapter
        galleryViewModel.cards.observe(viewLifecycleOwner, { cards ->
            galleryAdapter.submitList(cards)
        })

    }

        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}