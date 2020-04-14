package com.example.videostore.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.videostore.room.VideoStoreRoomDatabase
import com.example.videostore.room.entity.Movie

class SeedMoviesWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val database =
        VideoStoreRoomDatabase.getDatabase(context)

    override fun doWork(): Result {
        return try {

            for (x in 0 until 10){
                database.movieDao().add(Movie(0,"Filme", "Autor"))
            }

            Result.success()

        } catch (throwable: Throwable) {
            Result.failure()
        }
    }
}
