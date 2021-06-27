package com.example.trackmymedia.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.example.trackmymedia.R
import com.example.trackmymedia.database.AppDatabase
import com.example.trackmymedia.database.entities.MediaEntity
import com.example.trackmymedia.databinding.DialogRateBinding
import com.example.trackmymedia.utilits.APP_ACTIVITY
import com.example.trackmymedia.utilits.NO_RATING_VALUE
import com.example.trackmymedia.utilits.TypesLists
import kotlinx.android.synthetic.main.dialog_rate.*
import kotlinx.android.synthetic.main.dialog_rate.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class DialogRate(private val entity: MediaEntity?,
                 private val liveData: MutableLiveData<MutableList<MediaEntity>>) : DialogFragment() {

    private lateinit var _view: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _view = APP_ACTIVITY.layoutInflater.inflate(R.layout.dialog_rate, null)
        val alertBuilder = AlertDialog.Builder(APP_ACTIVITY)
            .setView(_view)
        initButtons()
        return alertBuilder.create()
    }

    private fun initButtons() {
        _view.button_done.setOnClickListener {
            if (entity != null) {
                removeEntityFromLiveData(entity)
                entity.rating = _view.seek_bar.progress
                entity.typeList = TypesLists.DONE
                entity.date = Calendar.getInstance().time
                GlobalScope.launch {
                    AppDatabase.getInstance(APP_ACTIVITY).getMediaDao().update(entity)
                }
                dialog?.dismiss()
            }
        }

        _view.button_dont_rate.setOnClickListener {
            if (entity != null) {
                removeEntityFromLiveData(entity)
                entity.typeList = TypesLists.DONE
                entity.date = Calendar.getInstance().time
                GlobalScope.launch {
                    AppDatabase.getInstance(APP_ACTIVITY).getMediaDao().update(entity)
                }
                dialog?.dismiss()
            }
        }

        _view.button_cancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun removeEntityFromLiveData(entity: MediaEntity) {
        liveData.value?.remove(entity)
        liveData.postValue(liveData.value)
    }

}