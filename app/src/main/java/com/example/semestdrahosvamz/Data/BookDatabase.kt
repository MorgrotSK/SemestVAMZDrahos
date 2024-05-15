//This file is sourced from the class materials.

package com.example.semestdrahosvamz.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room database for storing book information.
 *
 * @constructor Creates an instance of the Room database.
 */
@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    /**
     * Gets the Data Access Object for the Book database.
     *
     * @return The BookDao for the Book database.
     */
    abstract fun bookDao(): BookDao
    companion object {
        @Volatile
        private var Instance: BookDatabase? = null

        /**
         * Gets the singleton instance of the BookDatabase.
         *
         * @param context The context used to build the database.
         * @return The singleton instance of the BookDatabase.
         */
        fun getDatabase(context: Context): BookDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BookDatabase::class.java, "book_database_final")
                    .build().also { Instance = it }
            }
        }
    }
}
