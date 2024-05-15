//This code is sourced from the class materials

package com.example.semestdrahosvamz.Data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val bookRepository: BookRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineBookRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [BookRepository]
     */
    override val  bookRepository: BookRepository by lazy {
        OfflineIBookRepository(BookDatabase.getDatabase(context).bookDao())
    }
}
