//This code is sourced from the class materials

package com.example.semestdrahosvamz.Data

import kotlinx.coroutines.flow.Flow

class OfflineIBookRepository(private val itemDao: BookDao) : BookRepository{
    override fun getAllBooksStream(): Flow<List<Book>> = itemDao.getAllBooks()

    override fun getBookStream(id: Int): Flow<Book> = itemDao.getBook(id)

    override suspend fun insertBook(item: Book) : Long = itemDao.insert(item)

    override suspend fun deleteBook(item: Book) = itemDao.delete(item)

    override suspend fun updateBook(item: Book) = itemDao.update(item)
}
