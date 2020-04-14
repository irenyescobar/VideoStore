package com.example.videostore.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
class Movie(
           @PrimaryKey(autoGenerate = true) var id: Long,
            val name: String,
            val author: String)