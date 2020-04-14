package com.example.videostore.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
class Category(@PrimaryKey(autoGenerate = true) val id: Long,
               val name: String)