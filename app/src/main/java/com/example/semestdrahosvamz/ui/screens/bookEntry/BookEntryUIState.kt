package com.example.semestdrahosvamz.ui.screens.bookEntry

import android.net.Uri
import com.example.semestdrahosvamz.Data.Book

/**
 * Data class representing the UI state for a book entry.
 * * @property title The title of the book.
 * @property link The link to the book.
 * @property imageUri The URI of the book's cover image.
 */
data class BookEntryUIState(
    var title: String = "",
    var link: String = "",
    var imageUri: Uri = Uri.EMPTY
) {
    /**
     * Converts the UI state to a Book object.
     *
     * @return A Book object with the current state values.
     */
    fun getBook(): Book {
        return Book(
            title = title,
            link = link,
            imageUri = imageUri.toString(),
            status = 1,
            notes = "",
            bookMarkUrl = link
        )
    }
}

