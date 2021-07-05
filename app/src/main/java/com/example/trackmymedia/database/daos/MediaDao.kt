package com.example.trackmymedia.database.daos

import androidx.room.*
import com.example.trackmymedia.database.TypesConverter
import com.example.trackmymedia.database.entities.MediaEntity
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.utilits.TypesMedia

@Dao
@TypeConverters(TypesConverter::class)
interface MediaDao {

    @Query("SELECT * FROM Media WHERE type LIKE :type AND typeList LIKE :typeList")
    fun getAll(type: TypesMedia, typeList: TypesLists): List<MediaEntity>

    @Query("SELECT * FROM Media Where id LIKE :id")
    fun findById(id: Int): MediaEntity

    @Insert
    fun insert(media: MediaEntity)

    @Delete
    fun delete(media: MediaEntity)

    @Update
    fun update(media: MediaEntity)
}