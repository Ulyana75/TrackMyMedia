package com.example.trackmymedia.recycler_view

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackmymedia.R
import com.example.trackmymedia.database.entities.MediaEntity
import com.example.trackmymedia.fragments.DialogRate
import com.example.trackmymedia.fragments.EditingFragment
import com.example.trackmymedia.fragments.MainListFragment
import com.example.trackmymedia.utilits.*

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
                LENGTH_DESCRIPTION
            ) + "..." else description

        if (entity?.typeList == TypesLists.DONE) {
            holder.doneButton.visibility = View.GONE
        } else {
            holder.doneButton.visibility = View.VISIBLE
            holder.doneButton.setOnClickListener {
                DialogRate(entity, liveData).show(APP_ACTIVITY.supportFragmentManager, null)
            }
        }
        holder.view.setOnClickListener {
            replaceFragment(EditingFragment(entity!!.type, entity.typeList, entity), true)
        }
        setBackground(holder.view, entity?.rating)
    }

    private fun setBackground(view: View, rating: Int?) {
        if(rating != null && rating != NO_RATING_VALUE) {
            view.setBackgroundColor(ColorsManager.values()[rating].getColor())
        }
        else {
            view.setBackgroundColor(0)
        }
    }
}