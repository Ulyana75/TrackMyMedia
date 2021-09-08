package com.example.trackmymedia.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trackmymedia.R
import com.example.trackmymedia.databinding.FragmentChooseMediaBinding
import com.example.trackmymedia.utilits.APP_ACTIVITY
import com.example.trackmymedia.utilits.TypesMedia
import com.example.trackmymedia.utilits.removeButtonBack
import com.example.trackmymedia.utilits.replaceFragment


class ChooseMediaFragment : Fragment() {

    private lateinit var binding: FragmentChooseMediaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
        removeButtonBack()
        APP_ACTIVITY.title = "TrackMyMedia"
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initViews() {
        binding.buttonFilms.textView.text = TypesMedia.FILM.getStringOnRussian()
        binding.buttonSeries.textView.text = TypesMedia.SERIES.getStringOnRussian()
        binding.buttonBooks.textView.text = TypesMedia.BOOK.getStringOnRussian()
        binding.buttonGames.textView.text = TypesMedia.GAME.getStringOnRussian()

        binding.buttonFilms.imageView.setImageDrawable(APP_ACTIVITY.getDrawable(R.drawable.icon_films))
        binding.buttonSeries.imageView.setImageDrawable(APP_ACTIVITY.getDrawable(R.drawable.icon_series))
        binding.buttonBooks.imageView.setImageDrawable(APP_ACTIVITY.getDrawable(R.drawable.icon_book))
        binding.buttonGames.imageView.setImageDrawable(APP_ACTIVITY.getDrawable(R.drawable.icon_games))

        binding.buttonFilms.mainChooseItem.setOnClickListener {
            replaceFragment(
                ChooseListFragment.newInstance(
                    TypesMedia.FILM,
                    "Просмотрено", "Планирую посмотреть"
                ), true
            )
        }
        binding.buttonGames.mainChooseItem.setOnClickListener {
            replaceFragment(
                ChooseListFragment.newInstance(
                    TypesMedia.GAME,
                    "Пройдено", "Планирую пройти"
                ), true
            )
        }
        binding.buttonBooks.mainChooseItem.setOnClickListener {
            replaceFragment(
                ChooseListFragment.newInstance(
                    TypesMedia.BOOK,
                    "Прочитано", "Планирую прочитать"
                ), true
            )
        }
        binding.buttonSeries.mainChooseItem.setOnClickListener {
            replaceFragment(
                ChooseListFragment.newInstance(
                    TypesMedia.SERIES,
                    "Просмотрено", "Планирую посмотреть"
                ), true
            )
        }
    }

}