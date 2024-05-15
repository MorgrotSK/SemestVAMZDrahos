package com.example.semestdrahosvamz.ui.screens.bookEntry

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.semestdrahosvamz.Data.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * ViewModel for managing the state and actions of the book entry screen.
 *
 * @property bookRepository The repository for managing book data.
 * @property context The context used for accessing resources and file operations.
 */
class BookEntryViewModel(
    private val bookRepository: BookRepository,
    private val context: Context
) : ViewModel() {

    var bookEntryUIState by mutableStateOf(BookEntryUIState())
        private set

    /**
     * Updates the UI state with the provided title, link, and image URI.
     *
     * @param title The title of the book.
     * @param link The link to the book.
     * @param imageUri The URI of the book's cover image.
     */
    fun updateState(title: String, link: String, imageUri: Uri) {
        bookEntryUIState = BookEntryUIState(title, link, imageUri)
    }

    /**
     * Validates the input fields.
     *
     * @return True if the input fields are valid, false otherwise.
     */
    fun validateInput(): Boolean {
        // Check if the title, link, and imageUri are not empty
        if (bookEntryUIState.title.isBlank() ||
            bookEntryUIState.link.isBlank() ||
            bookEntryUIState.imageUri == Uri.EMPTY) {
            return false
        }

        // Check if the URL is valid
        return Patterns.WEB_URL.matcher(bookEntryUIState.link).matches()
    }

    /**
     * Saves the book to the repository.
     */
    suspend fun saveBook() {
        Log.d("SaveBook", "Original URI: ${bookEntryUIState.imageUri}")
        val bookId = bookRepository.insertItem(bookEntryUIState.getBook())
        val newUri = saveImageLocally(bookEntryUIState.imageUri, bookId)
        Log.d("SaveBook", "New URI: $newUri")

        val updatedBook = bookEntryUIState.getBook().copy(imageUri = newUri.toString(), id = bookId, status = 1)
        bookRepository.updateItem(updatedBook)
        updateState(updatedBook.title, updatedBook.link, newUri)
        Log.d("SaveBook", "Book updated in repository with new URI")
    }

    /**
     * Saves the image locally and returns the new URI.
     *
     * @param uri The original URI of the image.
     * @param id The ID of the book.
     * @return The new URI of the saved image.
     */
    private suspend fun saveImageLocally(uri: Uri, id: Long): Uri = withContext(Dispatchers.IO) {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        val bitmap = Bitmap.createScaledBitmap(ImageDecoder.decodeBitmap(source), 150, 300, true)

        val filename = "${id}.jpg"
        val file = File(context.filesDir, filename)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
        }

        Uri.fromFile(file)
    }
}
