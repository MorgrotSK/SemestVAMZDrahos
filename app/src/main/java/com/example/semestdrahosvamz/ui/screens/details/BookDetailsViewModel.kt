package com.example.semestdrahosvamz.ui.screens.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.Data.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the state and actions of the Book Details screen.
 *
 * @property savedStateHandle The SavedStateHandle to retrieve saved state.
 * @property bookRepository The repository for managing book data.
 * @property context The context used for accessing resources and starting activities.
 */
class BookDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository,
    private val context: Context
) : ViewModel() {
    private val bookId: Int = checkNotNull(savedStateHandle[BookDetailsScreenDestination.bookIdArg])

    var uiState = MutableStateFlow(BookDetailsUiState())

    init {
        viewModelScope.launch {
            bookRepository.getBookStream(bookId).collect { book ->
                if (book != null) {
                    uiState.value = BookDetailsUiState(book = book)
                } else {
                    uiState.value = BookDetailsUiState(book = Book(0, "", "", "", 1, "", ""))
                }
            }
        }
    }

    /**
     * Updates the reading status of the book.
     *
     * @param newStatus The new reading status.
     */
    fun updateReadingStatus(newStatus: Int) {
        viewModelScope.launch {
            val currentBook = uiState.value.book
            if (currentBook != null) {
                val updatedBook = currentBook.copy(status = newStatus)
                bookRepository.updateBook(updatedBook)
                uiState.value = uiState.value.copy(book = updatedBook)
            }
        }
    }

    /**
     * Binds the book to a widget by saving the book ID in shared preferences.
     */
    fun bindToWidget() {
        val sharedPreferences = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("book_id", uiState.value.book.id.toInt())
        editor.apply()
    }

    /**
     * Deletes the current book from the repository.
     */
    fun deleteBook() {
        viewModelScope.launch {
            val currentBook = uiState.value.book
            if (currentBook != null) {
                bookRepository.deleteBook(currentBook)
            }
        }
    }

    /**
     * Opens the book link in a web browser.
     */
    fun openBookLink() {
        val webIntent: Intent = Uri.parse(uiState.value.book.link).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
        context.startActivity(webIntent)
    }
}
