package com.example.trackmymedia.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.trackmymedia.R
import com.example.trackmymedia.databinding.FragmentChooseListBinding
import com.example.trackmymedia.utilits.*


class ChooseListFragment(
    private val typeMedia: TypesMedia, private val text_button_done: String,
    private val text_button_planning: String
) : Fragment() {

    private lateinit var binding: FragmentChooseListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChooseListBinding.inflate(inflater, container, false)
        MainListFragment.scrollPosition = 0
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStart() {
        super.onStart()
        initViews()
        addButtonBack()
        APP_ACTIVITY.title = typeMedia.getStringOnRussian()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initViews() {
        binding.buttonPlanning.textView.text = text_button_planning
        binding.buttonDone.textView.text = text_button_done

        binding.buttonDone.imageView.setImageDrawable(APP_ACTIVITY.getDrawable(R.drawable.icon_done))
        binding.buttonPlanning.imageView.setImageDrawable(APP_ACTIVITY.getDrawable(R.drawable.icon_planning))

        binding.buttonPlanning.mainChooseItem.setOnClickListener {
            replaceFragment(MainListFragment(typeMedia, TypesLists.PLANNING), true)
        }
        binding.buttonDone.mainChooseItem.setOnClickListener {
            replaceFragment(MainListFragment(typeMedia, TypesLists.DONE), true)
        }
    }

}