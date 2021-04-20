package com.example.trackmymedia.recycler_view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trackmymedia.databinding.FragmentMainListBinding
import com.example.trackmymedia.databinding.RecyclerviewItemBinding
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class MainListHolder(view: View): RecyclerView.ViewHolder(view) {

    val name: TextView = view.text_item_name
    val description: TextView = view.text_item_description

}