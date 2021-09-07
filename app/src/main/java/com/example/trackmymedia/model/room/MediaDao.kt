package com.example.trackmymedia.model.room

import androidx.room.*
import com.example.trackmymedia.model.entities.MediaEntity
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.utilits.TypesMedia

@Dao
@TypeConverters(TypesConverter::class)
interface MediaDao {

    @Query("SELECT * FROM Media WHERE type LIKE :type AND typeList LIKE :typeList")
    fun getAll(type: TypesMedia, typeList: TypesLists): List<MediaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(media: MediaEntity)

    @Delete
    fun delete(media: MediaEntity)

    @Update
    fun update(media: MediaEntity)
}