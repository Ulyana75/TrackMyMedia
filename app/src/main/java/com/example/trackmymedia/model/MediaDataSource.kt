package com.example.trackmymedia.model

import com.example.trackmymedia.model.entities.MediaEntity
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.utilits.TypesMedia

interface MediaDataSource {

    fun getAll(type: TypesMedia, typeList: TypesLists): List<MediaEntity>
    fun insert(media: MediaEntity)
    fun delete(media: MediaEntity)
    fun update(media: MediaEntity)

}