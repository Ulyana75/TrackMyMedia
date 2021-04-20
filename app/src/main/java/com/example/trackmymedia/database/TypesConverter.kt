package com.example.trackmymedia.database

import androidx.room.TypeConverter
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.utilits.TypesMedia

class TypesConverter {

    @TypeConverter
    fun fromTypeMedia(type: TypesMedia): String = type.toString()

    @TypeConverter
    fun toTypeMedia(type: String): TypesMedia {
        return when(type) {
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
        return when(type) {
            "PLANNING" -> TypesLists.PLANNING
            else -> TypesLists.DONE
        }
    }

}