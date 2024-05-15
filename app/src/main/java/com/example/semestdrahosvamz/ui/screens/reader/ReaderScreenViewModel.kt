package com.example.semestdrahosvamz.ui.screens.reader

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.Data.BookRepository
import com.example.semestdrahosvamz.ui.screens.details.BookDetailsScreenDestination
import com.example.semestdrahosvamz.ui.screens.details.BookDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReaderScreenViewModel(savedStateHandle: SavedStateHandle, bookRepository: BookRepository) : ViewModel() {
    var uiState = MutableStateFlow(BookDetailsUiState())
    private val bookId: Int = checkNotNull(savedStateHandle[BookDetailsScreenDestination.bookIdArg])

    init {
        viewModelScope.launch {
            bookRepository.getItemStream(bookId).collect() { book ->
                if (book != null) {
                    uiState.value = BookDetailsUiState(book = book)
                } else {
                    uiState.value = BookDetailsUiState(book = Book(0, "", "", "", 1, ""))
                }

            }
        }
    }


    fun updateCurrentUrl(newUrl : String) {

    }

    fun updateBookMark() {

    }
}