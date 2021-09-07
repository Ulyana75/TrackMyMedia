package com.example.trackmymedia.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.trackmymedia.databinding.FragmentEditingBinding
import com.example.trackmymedia.model.entities.MediaEntity
import com.example.trackmymedia.utilits.*
import com.example.trackmymedia.viewModel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*


class EditingFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: FragmentEditingBinding

    private var typeMedia: TypesMedia? = null
    private var typeLists: TypesLists? = null
    private var entity: MediaEntity? = null

    companion object {

        fun newInstance(typeMedia: TypesMedia, typeList: TypesLists, entity: MediaEntity? = null): EditingFragment {
            val fragment = EditingFragment()
            fragment.arguments = bundleOf(
                key_type_media to typeMedia,
                key_type_list to typeList,
                key_entity to entity
            )

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        typeMedia = arguments?.get(key_type_media) as TypesMedia?
        typeLists = arguments?.get(key_type_list) as TypesLists?
        entity = arguments?.get(key_entity) as MediaEntity?

        binding = FragmentEditingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if(typeMedia != null && typeLists != null) {
            viewModel.setTypes(typeMedia!!, typeLists!!)
            initViews()
        }
        addButtonBack()
    }

    override fun onStop() {
        super.onStop()
        val inputMethodManager =
            APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(APP_ACTIVITY.currentFocus?.windowToken, 0)
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        binding.seekBar.splitTrack = false

        fillFields()

        if (typeLists == TypesLists.PLANNING) {
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
            if (isChecked) {
                binding.ratingValue.text = "Нет оценки"
            } else {
                binding.ratingValue.text = binding.seekBar.progress.toString() + "/10"
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!binding.noRating.isChecked) {
                    binding.ratingValue.text = "$progress/10"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.editName.requestFocus()
        val inputMethodManager =
            APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun fillFields() {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")

        if (entity != null) {
            binding.editName.setText(entity!!.name)
            binding.editDescription.setText(entity!!.description)
            binding.textDate.text = "Дата добавления: " + dateFormat.format(entity!!.date)

            if (entity!!.rating == NO_RATING_VALUE) {
                binding.seekBar.progress = binding.seekBar.max / 2
                binding.noRating.isChecked = true
                binding.ratingValue.text = "Нет оценки"
            } else {
                binding.seekBar.progress = entity!!.rating
                binding.ratingValue.text = entity!!.rating.toString() + "/10"
                binding.noRating.isChecked = false
            }
        } else {
            binding.textDate.text =
                "Дата добавления: " + dateFormat.format(Calendar.getInstance().time)
        }
    }

    private fun addEntity(): Boolean {
        val name = binding.editName.text.toString()
        if (name == "") {
            showToast("Введите название!")
            return false
        }

        val description = binding.editDescription.text.toString()

        val rate = if (typeLists == TypesLists.PLANNING || binding.noRating.isChecked) {
            NO_RATING_VALUE
        } else {
            binding.seekBar.progress
        }

        if (entity == null) {
            viewModel.insert(
                MediaEntity(
                    name,
                    description,
                    rate,
                    typeMedia!!,
                    typeLists!!,
                    Calendar.getInstance().time
                )
            )
        } else {
            entity!!.name = name
            entity!!.description = description
            entity!!.rating = rate
            viewModel.update(entity!!)
        }
        return true
    }

}