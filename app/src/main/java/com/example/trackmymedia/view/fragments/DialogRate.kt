package com.example.trackmymedia.view.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.trackmymedia.R
import com.example.trackmymedia.model.entities.MediaEntity
import com.example.trackmymedia.utilits.APP_ACTIVITY
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.utilits.TypesMedia
import com.example.trackmymedia.utilits.key_entity
import com.example.trackmymedia.viewModel.MainViewModel
import kotlinx.android.synthetic.main.dialog_rate.view.*
import java.util.*

class DialogRate : DialogFragment() {

    private lateinit var _view: View
    private lateinit var viewModel: MainViewModel

    private var entity: MediaEntity? = null


    companion object {

        fun newInstance(entity: MediaEntity?): DialogRate {
            val dialogRate = DialogRate()
            dialogRate.arguments = bundleOf(
                key_entity to entity
            )

            return dialogRate
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        entity = arguments?.get(key_entity) as MediaEntity?

        _view = APP_ACTIVITY.layoutInflater.inflate(R.layout.dialog_rate, null)
        viewModel = ViewModelProvider(APP_ACTIVITY).get(MainViewModel::class.java)

        val alertBuilder = AlertDialog.Builder(APP_ACTIVITY)
            .setView(_view)
        initButtons()

        return alertBuilder.create()
    }

    private fun initButtons() {

        _view.seek_bar.splitTrack = false

        _view.seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                _view.rating_value.text = "$progress/10"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        var typeMedia: TypesMedia
        var typeList: TypesLists

        _view.button_done.setOnClickListener {
            if (entity != null) {
                typeMedia = entity!!.type
                typeList = entity!!.typeList

                entity!!.rating = _view.seek_bar.progress
                entity!!.typeList = TypesLists.DONE
                entity!!.date = Calendar.getInstance().time

                viewModel.update(entity!!) {
                    viewModel.getEntities(typeMedia, typeList)
                }

                dialog?.dismiss()
            }
        }

        _view.button_dont_rate.setOnClickListener {
            if (entity != null) {
                typeMedia = entity!!.type
                typeList = entity!!.typeList

                entity!!.typeList = TypesLists.DONE
                entity!!.date = Calendar.getInstance().time

                viewModel.update(entity!!) {
                    viewModel.getEntities(typeMedia, typeList)
                }

                dialog?.dismiss()
            }
        }

        _view.button_cancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

}