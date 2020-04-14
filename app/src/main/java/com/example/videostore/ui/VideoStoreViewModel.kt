package com.example.videostore.ui

import android.app.Application
import androidx.lifecycle.*
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.videostore.room.VideoStoreRoomDatabase
import com.example.videostore.room.entity.Category
import com.example.videostore.room.entity.Movie
import com.example.videostore.workers.SeedCategoriesWorker
import com.example.videostore.workers.SeedMoviesWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoStoreViewModel (application: Application ): AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application)

    private val database =
        VideoStoreRoomDatabase.getDatabase(application.applicationContext)

    var allMovies: LiveData<List<Movie>> = database.movieDao().getMovies()
    val allCategories: LiveData<List<Category>> = database.categoryDao().getAll()

    var currentMovie: MutableLiveData<Movie> = MutableLiveData<Movie>().apply { value = null }
    var currentAssignCategories: LiveData<List<Category>>? = null

    fun setCurrentMovie(movieId: Long){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                currentMovie.postValue(
                    database.movieDao().getMovie(movieId)
                )
            }
        }
    }

    fun setCurrentAssignCategories(movieId: Long){
        currentAssignCategories = database.movieDao().getMovieCategories(movieId)
    }

    fun saveMovie(movie:Movie, assignCategories:List<Category>){
        viewModelScope.launch {
            currentMovie.value?.run {
                movie.id = id
            }
            withContext(Dispatchers.IO){
                database.movieDao().save(movie, assignCategories)
                setCurrentMovie(movie.id)
            }
        }
    }

    fun seedMoviesData() {
        workManager.enqueue(OneTimeWorkRequest.from(SeedMoviesWorker::class.java))
    }

    fun seedCategoriesData() {
        workManager.enqueue(OneTimeWorkRequest.from(SeedCategoriesWorker::class.java))
    }
}