package com.example.trackmymedia.model.room

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import com.example.trackmymedia.utilits.DATE_FORMAT
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.utilits.TypesMedia
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class TypesConverter {

    @TypeConverter
    fun fromTypeMedia(type: TypesMedia): String = type.toString()

    @TypeConverter
    fun toTypeMedia(type: String): TypesMedia {
        return when (type) {
            "FILM" -> TypesMedia.FILM
            "SERIES" -> TypesMedia.SERIES
            "GAME" -> TypesMedia.GAME
            else -> TypesMedia.BOOK
        }
    }

    @TypeConverter
    fun fromTypeList(type: TypesLists): String = type.toString()

    @TypeConverter
    fun toTypeList(type: String): TypesLists {
        return when (type) {
            "PLANNING" -> TypesLists.PLANNING
            else -> TypesLists.DONE
        }
    }

    @SuppressLint("SimpleDateFormat")
    @TypeConverter
    fun fromDate(date: Date): String {
        val formatter = SimpleDateFormat(DATE_FORMAT)
        return formatter.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    @TypeConverter
    fun toDate(date: String): Date {
        val formatter = SimpleDateFormat(DATE_FORMAT)
        return formatter.parse(date)!!
    }

}