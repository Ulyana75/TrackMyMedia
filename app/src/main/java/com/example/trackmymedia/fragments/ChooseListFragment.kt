package com.example.trackmymedia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trackmymedia.R
import com.example.trackmymedia.databinding.FragmentChooseListBinding
import com.example.trackmymedia.utilits.*


class ChooseListFragment(private val typeMedia: TypesMedia, private val text_button_done: String,
                         private val text_button_planning: String) : Fragment() {

    private lateinit var binding: FragmentChooseListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChooseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
        addButtonBack()
        APP_ACTIVITY.title = typeMedia.getStringOnRussian()
    }

    private fun initViews() {
        binding.buttonPlanning.textView.text = text_button_planning
        binding.buttonDone.textView.text = text_button_done

        binding.buttonPlanning.mainChooseItem.setOnClickListener {
            replaceFragment(MainListFragment(typeMedia, TypesLists.PLANNING), true)
        }
        binding.buttonDone.mainChooseItem.setOnClickListener {
            replaceFragment(MainListFragment(typeMedia, TypesLists.DONE), true)
        }
    }

}