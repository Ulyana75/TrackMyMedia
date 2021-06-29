package com.example.trackmymedia.recycler_view

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class MainListHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val name: TextView = view.text_item_name
    val description: TextView = view.text_item_description
    val doneButton: ImageButton = view.button_done

}