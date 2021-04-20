package com.example.trackmymedia.recycler_view

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.trackmymedia.R
import com.example.trackmymedia.database.entities.MediaEntity

class MainListAdapter(private val liveData: MutableLiveData<MutableList<MediaEntity>>) :
    RecyclerView.Adapter<MainListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MainListHolder(view)
    }

    override fun getItemCount(): Int = liveData.value?.size ?: 0

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainListHolder, position: Int) {
        holder.name.text = liveData.value?.get(position)?.name
        holder.description.text = liveData.value?.get(position)?.description?.substring(0, 30) + "..."
    }
}