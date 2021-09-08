package com.example.trackmymedia.view.recycler_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trackmymedia.R
import com.example.trackmymedia.model.entities.MediaEntity
import com.example.trackmymedia.utilits.APP_ACTIVITY
import com.example.trackmymedia.utilits.ColorsManager
import com.example.trackmymedia.utilits.NO_RATING_VALUE
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.view.fragments.DialogRate
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class MainListAdapter(
    private var dataList: List<MediaEntity>,
    private val clickListener: (MediaEntity) -> Unit,
    private val longClickListener: (MediaEntity, View) -> Unit
) :
    RecyclerView.Adapter<MainListAdapter.MainListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)

        return MainListHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainListHolder, position: Int) {
        val entity = dataList[position]

        holder.name.text = entity.name
        holder.description.text = entity.description

        if (entity.typeList == TypesLists.DONE) {
            holder.doneButton.visibility = View.GONE
        } else {
            holder.doneButton.visibility = View.VISIBLE
            holder.doneButton.setOnClickListener {
                DialogRate.newInstance(entity).show(APP_ACTIVITY.supportFragmentManager, null)
            }
        }

        holder.itemView.setOnClickListener {
            clickListener(entity)
        }

        holder.itemView.setOnLongClickListener {
            longClickListener(entity, it)
            true
        }

        setBackground(holder.itemView, entity.rating)
    }

    private fun setBackground(view: View, rating: Int?) {
        if (rating != null && rating != NO_RATING_VALUE) {
            view.setBackgroundColor(ColorsManager.values()[rating].getColor())
        } else {
            view.setBackgroundColor(0)
        }
    }

    fun setData(newList: List<MediaEntity>) {
        dataList = newList
        notifyDataSetChanged()
    }

    fun getData(): List<MediaEntity> {
        return dataList
    }


    class MainListHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.text_item_name
        val description: TextView = view.text_item_description
        val doneButton: ImageButton = view.button_done

    }
}