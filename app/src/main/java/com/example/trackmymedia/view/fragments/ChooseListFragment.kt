 package com.example.trackmymedia.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.trackmymedia.R
import com.example.trackmymedia.databinding.FragmentChooseListBinding
import com.example.trackmymedia.utilits.*


 class ChooseListFragment : Fragment() {

    private lateinit var binding: FragmentChooseListBinding

    private var typeMedia: TypesMedia? = null
    private var textButtonDone: String? = null
    private var textButtonPlanning: String? = null


    companion object {

        fun newInstance(
            typeMedia: TypesMedia,
            textButtonDone: String,
            textButtonPlanning: String
        ): ChooseListFragment {
            val fragment = ChooseListFragment()
            fragment.arguments = bundleOf(
                key_type_media to typeMedia,
                key_text_done to textButtonDone,
                key_text_planning to textButtonPlanning
            )

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseListBinding.inflate(inflater, container, false)
        MainListFragment.scrollPosition = 0

        typeMedia = arguments?.get(key_type_media) as TypesMedia?
        textButtonDone = arguments?.getString(key_text_done)
        textButtonPlanning = arguments?.getString(key_text_planning)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if(typeMedia != null) {
            initViews()
        }
        addButtonBack()
        APP_ACTIVITY.title = typeMedia?.getStringOnRussian()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initViews() {
        binding.buttonPlanning.textView.text = textButtonPlanning
        binding.buttonDone.textView.text = textButtonDone

        binding.buttonDone.imageView.setImageDrawable(APP_ACTIVITY.getDrawable(R.drawable.icon_done))
        binding.buttonPlanning.imageView.setImageDrawable(APP_ACTIVITY.getDrawable(R.drawable.icon_planning))

        binding.buttonPlanning.mainChooseItem.setOnClickListener {
            replaceFragment(MainListFragment.newInstance(typeMedia!!, TypesLists.PLANNING), true)
        }
        binding.buttonDone.mainChooseItem.setOnClickListener {
            replaceFragment(MainListFragment.newInstance(typeMedia!!, TypesLists.DONE), true)
        }
    }

}