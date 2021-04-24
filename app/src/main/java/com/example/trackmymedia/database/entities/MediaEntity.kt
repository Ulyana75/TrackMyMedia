package com.example.trackmymedia.database.entities

import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.trackmymedia.database.TypesConverter
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.utilits.TypesMedia

@Entity(tableName = "Media")
data class MediaEntity (
    var name: String,
    var description: String,
    var rating: Int,
    @field:TypeConverters(TypesConverter::class)
    val type: TypesMedia,
    @field:TypeConverters(TypesConverter::class)
    var typeList: TypesLists
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}