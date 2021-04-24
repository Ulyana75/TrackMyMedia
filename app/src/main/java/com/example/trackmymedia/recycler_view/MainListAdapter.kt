package com.example.trackmymedia.recycler_view

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.trackmymedia.R
import com.example.trackmymedia.database.entities.MediaEntity
import com.example.trackmymedia.fragments.DialogRate
import com.example.trackmymedia.fragments.MainListFragment
import com.example.trackmymedia.utilits.APP_ACTIVITY
import com.example.trackmymedia.utilits.LENGTH_DESCRIPTION
import com.example.trackmymedia.utilits.TypesLists

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
        val entity = liveData.value?.get(position)
        val description = entity?.description ?: ""

        holder.name.text = entity?.name
        holder.description.text =
            if (description.length > LENGTH_DESCRIPTION) description.substring(
                0,
                30
            ) + "..." else description

        if (entity?.typeList == TypesLists.DONE) {
            holder.doneButton.visibility = View.GONE
        } else {
            holder.doneButton.visibility = View.VISIBLE
            holder.doneButton.setOnClickListener {
                DialogRate(entity, liveData).show(APP_ACTIVITY.supportFragmentManager, null)
                notifyItemChanged(position)
            }
        }
    }
}