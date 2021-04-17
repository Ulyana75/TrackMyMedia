package com.example.trackmymedia.database.entities

import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trackmymedia.utilits.TypesMedia

@Entity(tableName = "Media")
class MediaEntity (
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val name: String,
    val description: String,
    val image: ImageView,
    val rating: Int,
    val type: TypesMedia
)