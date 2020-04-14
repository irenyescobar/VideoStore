package com.example.videostore.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.videostore.room.dao.CategoryDao
import com.example.videostore.room.dao.MovieDao
import com.example.videostore.room.entity.Category
import com.example.videostore.room.entity.Movie
import com.example.videostore.room.entity.MovieCategories

@Database(entities = [Category::class, Movie::class, MovieCategories::class ], version = 1, exportSchema = false)
abstract class VideoStoreRoomDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: VideoStoreRoomDatabase? = null

        fun getDatabase(context: Context): VideoStoreRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VideoStoreRoomDatabase::class.java,
                    "VideoStoreRoomDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}