//This file is sourced from the class materials!

package com.example.semestdrahosvamz.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book) : Long

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("SELECT * from books WHERE id = :id")
    fun getBook(id: Int): Flow<Book>

    @Query("SELECT * from books ORDER BY title ASC")
    fun getAllBooks(): Flow<List<Book>>
}
