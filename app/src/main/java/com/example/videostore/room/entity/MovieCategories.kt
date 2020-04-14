package com.example.videostore.room.entity
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.videostore.room.entity.Category
import com.example.videostore.room.entity.Movie

@Entity(
    primaryKeys = ["movieId", "categoryId"],
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["movieId"]),
        Index(value = ["categoryId"])
    ]
)
class MovieCategories(var movieId: Long = 0,
                      var categoryId: Long = 0)