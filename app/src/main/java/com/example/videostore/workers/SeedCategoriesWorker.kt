package com.example.videostore.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.videostore.room.VideoStoreRoomDatabase
import com.example.videostore.room.entity.Category
import com.example.videostore.room.entity.Movie

class SeedCategoriesWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val database =
        VideoStoreRoomDatabase.getDatabase(context)

    override fun doWork(): Result {

        return try {

            for (x in 0 until 10){
                database.categoryDao().insert(Category(0,"Categoria"))
            }

            Result.success()

        } catch (throwable: Throwable) {
            Result.failure()
        }
    }
}
