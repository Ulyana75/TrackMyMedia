package com.example.trackmymedia.view.recycler_view

import androidx.recyclerview.widget.DiffUtil
import com.example.trackmymedia.model.entities.MediaEntity

class MediaDiffUtilCallback(
    private val oldList: List<MediaEntity>,
    private val newList: List<MediaEntity>
) : DiffUtil.Callback()  {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}