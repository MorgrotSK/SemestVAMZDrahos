package com.example.semestdrahosvamz.Data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val link : String,
    val imageUri : String,
    val status : Int,
)
