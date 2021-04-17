package com.example.trackmymedia.database.daos

import androidx.room.*
import com.example.trackmymedia.database.entities.MediaEntity
import com.example.trackmymedia.utilits.TypesMedia

@Dao
interface MediaDao {

    @Query("SELECT * FROM Media WHERE type LIKE :type")
    fun getAll(type: TypesMedia): List<MediaEntity>

    @Query("SELECT * FROM Media Where id LIKE :id")
    fun findById(id: Int): MediaEntity

    @Insert
    fun insert(media: MediaEntity)

    @Delete
    fun delete(media: MediaEntity)

    @Update
    fun update(media: MediaEntity)
}