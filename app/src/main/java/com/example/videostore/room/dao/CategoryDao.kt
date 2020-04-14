package com.example.videostore.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.videostore.room.entity.Category

@Dao
interface CategoryDao {
    @Query("SELECT * from Category")
    fun getAll(): LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Category)
}