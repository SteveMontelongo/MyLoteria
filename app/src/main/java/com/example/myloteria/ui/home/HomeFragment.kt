package com.example.myloteria.ui.home

import android.os.Bundle
import android.speech.tts.TextToSpeech
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : Fragment(), TextToSpeech.OnInitListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var tts: TextToSpeech

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
        tts = TextToSpeech(this.context, this)
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

                speakOut(it.name)
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
            if(homeViewModel.initialCardPlay){
                speakOut("Corre y te vas con")
                GlobalScope.launch{
                    delay(2000)
                    if(!homeViewModel.play()) {
                        binding.play.setImageResource(R.drawable.ic_play)
                    }else{
                        binding.play.setImageResource(R.drawable.ic_pause)
                    }
                }
            }else{
                if(!homeViewModel.play()) {
                    binding.play.setImageResource(R.drawable.ic_play)
                }else{
                    binding.play.setImageResource(R.drawable.ic_pause)
                }
            }

        }
        binding.shuffle.setOnClickListener{ view ->
            var snackbar = Snackbar.make(view, "Shuffled", Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
            snackbar.setAnchorView(R.id.card)
            snackbar.show()
            homeViewModel.shuffleCards()
            binding.play.setImageResource(R.drawable.ic_play)
        }
    }

    override fun onDestroyView() {
        if (tts.isSpeaking) {
            tts.stop()
        }
        tts.shutdown()
        super.onDestroyView()
        _binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale("es", "MX"))

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
                println("TTS: Language not supported")
            } else {
                // TTS is ready to use.
                tts.setPitch(3f)
                tts.setSpeechRate(1f)

                speakOut("Bienvenida a mi loteria")
            }
        } else {
            println("TTS: Initialization failed")
        }
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}