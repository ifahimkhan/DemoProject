package com.fahim.demo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    var title: String,
    var author: String,
    var year: String,
    var imageUrl : String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)