package com.example.trackmymedia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trackmymedia.databinding.FragmentChooseMediaBinding
import kotlinx.android.synthetic.main.main_screen_choose_item.view.*


class ChooseMediaFragment : Fragment() {

    private lateinit var binding: FragmentChooseMediaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChooseMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        binding.buttonFilms.textView.text = "Фильмы"
        binding.buttonSeries.textView.text = "Сериалы"
        binding.buttonBooks.textView.text = "Книги"
        binding.buttonGames.textView.text = "Игры"
    }

}