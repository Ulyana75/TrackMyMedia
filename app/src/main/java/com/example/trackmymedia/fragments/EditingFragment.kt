package com.example.trackmymedia.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trackmymedia.R
import com.example.trackmymedia.database.AppDatabase
import com.example.trackmymedia.database.entities.MediaEntity
import com.example.trackmymedia.databinding.FragmentEditingBinding
import com.example.trackmymedia.utilits.APP_ACTIVITY
import com.example.trackmymedia.utilits.NO_RATING_VALUE
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.utilits.TypesMedia
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EditingFragment(private val typeMedia: TypesMedia, private val typeLists: TypesLists) : Fragment() {

    private lateinit var binding: FragmentEditingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        if(typeLists == TypesLists.PLANNING) {
            binding.textRate.visibility = View.GONE
            binding.seekBar.visibility = View.GONE
        }
        binding.buttonEditingDone.setOnClickListener {
            addEntity()
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
    }

    private fun addEntity() {
        val name = binding.editName.text.toString()
        val description = binding.editDescription.text.toString()
        val rate = if(typeLists == TypesLists.PLANNING) {
            NO_RATING_VALUE
        } else {
            binding.seekBar.progress
        }

        GlobalScope.launch {
            AppDatabase.getInstance(APP_ACTIVITY).getMediaDao().insert(
                MediaEntity(name, description, rate, typeMedia, typeLists)
            )
        }

    }

}