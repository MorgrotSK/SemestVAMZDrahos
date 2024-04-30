package com.example.semestdrahosvamz.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
)
