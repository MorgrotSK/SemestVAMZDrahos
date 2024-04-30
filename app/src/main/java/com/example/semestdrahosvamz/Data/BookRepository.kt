package com.example.semestdrahosvamz.Data

import kotlinx.coroutines.flow.Flow

interface BookRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Book>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Book?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Book)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Book)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: Book)
}