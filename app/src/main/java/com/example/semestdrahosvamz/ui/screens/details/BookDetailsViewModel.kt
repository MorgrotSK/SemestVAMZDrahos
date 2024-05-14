package com.example.semestdrahosvamz.ui.screens.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.Data.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch

class BookDetailsViewModel (savedStateHandle: SavedStateHandle, private val bookRepository: BookRepository, private val context: Context) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[BookDetailsScreenDestination.bookIdArg])

    var uiState = MutableStateFlow(BookDetailsUiState())

    init {
        viewModelScope.launch {
            bookRepository.getItemStream(itemId).collect { book ->
                if (book != null) {
                    uiState.value = BookDetailsUiState(book = book)
                } else {
                    uiState.value = BookDetailsUiState(book = Book(0, "", "", "", 1, ""))
                }

            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun updateReadingStatus(newStatus : Int) {
        viewModelScope.launch {
            val currentBook = uiState.value.book
            if (currentBook != null) {
                val updatedBook = currentBook.copy(status = newStatus)
                bookRepository.updateItem(updatedBook)
                uiState.value = uiState.value.copy(book = updatedBook)
            }
        }
    }

    fun deleteBook() {
        viewModelScope.launch {
            val currentBook = uiState.value.book
            if (currentBook != null) {
                bookRepository.deleteItem(currentBook)
            }
        }
    }
    fun OpenBookLink() {
        val webIntent: Intent = Uri.parse(uiState.value.book.link).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
        context.startActivity(webIntent)
    }
}
