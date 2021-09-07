package com.example.trackmymedia.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trackmymedia.model.entities.MediaEntity
import com.example.trackmymedia.utilits.DATABASE_NAME

@Database(entities = [MediaEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMediaDao(): MediaDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val _instance = buildDatabase(context)
                instance = _instance
                _instance
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}