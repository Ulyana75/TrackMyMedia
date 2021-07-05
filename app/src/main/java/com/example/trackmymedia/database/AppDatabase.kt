package com.example.trackmymedia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trackmymedia.database.daos.MediaDao
import com.example.trackmymedia.database.entities.MediaEntity

@Database(entities = [MediaEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMediaDao(): MediaDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = buildDatabase(context)
            }

            return instance!!   //TODO многопоточку можно сделать
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "app_database.db").build()
        }
    }
}