package com.example.trackmymedia.model

import android.content.Context
import com.example.trackmymedia.model.room.AppDatabase
import com.example.trackmymedia.model.entities.MediaEntity
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.utilits.TypesMedia

class RoomMediaDataSource(context: Context) : MediaDataSource {

    private val dao = AppDatabase.getInstance(context).getMediaDao()

    override fun getAll(type: TypesMedia, typeList: TypesLists): List<MediaEntity> {
        return dao.getAll(type, typeList)
    }

    override fun insert(media: MediaEntity) {
        dao.insert(media)
    }

    override fun delete(media: MediaEntity) {
        dao.delete(media)
    }

    override fun update(media: MediaEntity) {
        dao.update(media)
    }
}