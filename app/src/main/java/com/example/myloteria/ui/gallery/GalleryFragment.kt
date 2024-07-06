package com.example.myloteria.ui.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myloteria.`interface`.GalleryOnClick
import com.example.myloteria.adapter.GalleryAdapter
import com.example.myloteria.databinding.FragmentGalleryBinding
import com.example.myloteria.ui.record.RecordActivity

class GalleryFragment : Fragment(), GalleryOnClick {

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
        val galleryAdapter = GalleryAdapter(this)
        recyclerView.adapter = galleryAdapter
        galleryViewModel.cards.observe(viewLifecycleOwner, { cards ->
            galleryAdapter.submitList(cards)
        })

    }

        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun itemClick(name: String, image: Int) {
        val intent = Intent(this.context, RecordActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("image", image)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data:Intent? = result.data
            val dataListName = data?.getStringExtra("ListName")
            val dataListDate = data?.getStringExtra("ListDate")

        }
    }
}