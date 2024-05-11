package com.example.semestdrahosvamz.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BookDetailsViewModel (savedStateHandle: SavedStateHandle, private val bookRepository: BookRepository) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[BookDetailsScreenDestination.bookIdArg])

    var uiState = MutableStateFlow(BookDetailsUiState())

    init {
        viewModelScope.launch {
            bookRepository.getItemStream(itemId).collect { book ->
                uiState.value = BookDetailsUiState(book = book)
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
}
