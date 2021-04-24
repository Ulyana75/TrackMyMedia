package com.example.trackmymedia.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackmymedia.R
import com.example.trackmymedia.database.AppDatabase
import com.example.trackmymedia.database.entities.MediaEntity
import com.example.trackmymedia.databinding.FragmentMainListBinding
import com.example.trackmymedia.recycler_view.MainListAdapter
import com.example.trackmymedia.utilits.APP_ACTIVITY
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.utilits.TypesMedia
import com.example.trackmymedia.utilits.replaceFragment
import kotlinx.android.synthetic.main.dialog_rate.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainListFragment(private val typeMedia: TypesMedia, private val typeLists: TypesLists) :
    Fragment() {

    private lateinit var binding: FragmentMainListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val liveData = MutableLiveData<MutableList<MediaEntity>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainListBinding.inflate(inflater, container, false)
        liveData.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter?.notifyDataSetChanged()
        })
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerView
        adapter = MainListAdapter(liveData)
        layoutManager = LinearLayoutManager(APP_ACTIVITY)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        getEntities()
    }

    private fun initViews() {
        binding.buttonAdd.setOnClickListener {
            replaceFragment(EditingFragment(typeMedia, typeLists), true)
        }
    }

    private fun getEntities() {
        GlobalScope.launch {
            liveData.postValue(AppDatabase.getInstance(APP_ACTIVITY).getMediaDao().getAll(typeMedia, typeLists).toMutableList())
        }
    }

}