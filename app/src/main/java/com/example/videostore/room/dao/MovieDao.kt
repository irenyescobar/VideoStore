package com.example.videostore.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.videostore.room.entity.Category
import com.example.videostore.room.entity.Movie
import com.example.videostore.room.entity.MovieCategories

@Dao
abstract class MovieDao {

    @Transaction
    open fun save(movie: Movie, categories: List<Category>){
        movie.id = add(movie)
        deleteCategories(movie.id)
        categories.forEach {
            add(MovieCategories(movie.id,it.id))
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun add(entity: Movie): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun add(entity: MovieCategories)

    @Query("DELETE FROM MovieCategories WHERE movieId = :movieId")
    abstract fun deleteCategories(movieId:Long)

    @Query("SELECT * FROM Movie WHERE id = :id")
    abstract fun getMovie(id: Long):Movie

    @Query("SELECT * FROM Movie")
    abstract fun getMovies(): LiveData<List<Movie>>

    @Query("SELECT c.id, c.name FROM  MovieCategories mc  INNER JOIN Category c ON c.id = mc.categoryId WHERE mc.movieId = :movieId")
    abstract fun getMovieCategories(movieId: Long): LiveData<List<Category>>
}