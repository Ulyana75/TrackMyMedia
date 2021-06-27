package com.example.trackmymedia.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.trackmymedia.database.AppDatabase
import com.example.trackmymedia.database.entities.MediaEntity
import com.example.trackmymedia.databinding.FragmentEditingBinding
import com.example.trackmymedia.utilits.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import android.util.Log
import java.text.SimpleDateFormat


class EditingFragment(private val typeMedia: TypesMedia, private val typeLists: TypesLists,
                      private val entity: MediaEntity? = null) : Fragment() {

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
        addButtonBack()
    }

    private fun initViews() {
        fillFields()
        if(typeLists == TypesLists.PLANNING) {
            binding.textRate.visibility = View.GONE
            binding.seekBar.visibility = View.GONE
            binding.noRating.visibility = View.GONE
        }
        binding.buttonEditingDone.setOnClickListener {
            if (addEntity()) {
                APP_ACTIVITY.supportFragmentManager.popBackStack()
            }
        }
        binding.editName.requestFocus()
        val inputMethodManager = APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun fillFields() {
        if(entity != null) {
            binding.editName.setText(entity.name)
            binding.editDescription.setText(entity.description)
            if(entity.rating == NO_RATING_VALUE) {
                binding.seekBar.progress = binding.seekBar.max / 2
                binding.noRating.isChecked = true
            } else {
                binding.seekBar.progress = entity.rating
                binding.noRating.isChecked = false
            }
        }
    }

    private fun addEntity(): Boolean {
        val name = binding.editName.text.toString()
        if(name == "") {
            showToast("Введите название!")
            return false
        }
        val description = binding.editDescription.text.toString()
        val rate = if(typeLists == TypesLists.PLANNING || binding.noRating.isChecked) {
            NO_RATING_VALUE
        } else {
            binding.seekBar.progress
        }

        if(entity == null) {
            GlobalScope.launch {
                AppDatabase.getInstance(APP_ACTIVITY).getMediaDao().insert(
                    MediaEntity(name, description, rate, typeMedia, typeLists, Calendar.getInstance().time)
                )
            }
        } else {
            entity.name = name
            entity.description = description
            entity.rating = rate
            GlobalScope.launch {
                AppDatabase.getInstance(APP_ACTIVITY).getMediaDao().update(entity)
            }
        }
        return true
    }

    override fun onStop() {
        super.onStop()
        val inputMethodManager = APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(APP_ACTIVITY.currentFocus?.windowToken, 0)
    }

}