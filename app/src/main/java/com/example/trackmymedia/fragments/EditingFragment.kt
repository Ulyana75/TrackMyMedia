package com.example.trackmymedia.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
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
import android.widget.SeekBar
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStart() {
        super.onStart()
        initViews()
        addButtonBack()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    private fun initViews() {
        binding.seekBar.splitTrack = false
        fillFields()
        if(typeLists == TypesLists.PLANNING) {
            binding.textRate.visibility = View.GONE
            binding.seekBar.visibility = View.GONE
            binding.noRating.visibility = View.GONE
            binding.ratingValue.visibility = View.GONE
        }
        binding.buttonEditingDone.setOnClickListener {
            if (addEntity()) {
                APP_ACTIVITY.supportFragmentManager.popBackStack()
            }
        }
        binding.noRating.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                binding.ratingValue.text = "Нет оценки"
            } else {
                binding.ratingValue.text = binding.seekBar.progress.toString() + "/10"
            }
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(!binding.noRating.isChecked) {
                    binding.ratingValue.text = "$progress/10"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        binding.editName.requestFocus()
        val inputMethodManager = APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    @SuppressLint("SetTextI18n")
    private fun fillFields() {
        if(entity != null) {
            binding.editName.setText(entity.name)
            binding.editDescription.setText(entity.description)
            if(entity.rating == NO_RATING_VALUE) {
                binding.seekBar.progress = binding.seekBar.max / 2
                binding.noRating.isChecked = true
                binding.ratingValue.text = "Нет оценки"
            } else {
                binding.seekBar.progress = entity.rating
                binding.ratingValue.text = entity.rating.toString() + "/10"
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