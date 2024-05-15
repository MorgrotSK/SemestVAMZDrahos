package com.example.semestdrahosvamz.ui.screens.reader

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.Data.BookRepository
import com.example.semestdrahosvamz.ui.screens.details.BookDetailsScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReaderScreenViewModel(savedStateHandle: SavedStateHandle, private val bookRepository: BookRepository) : ViewModel() {
    var uiState = MutableStateFlow(ReaderUIState())
    private val bookId: Int = checkNotNull(savedStateHandle[BookDetailsScreenDestination.bookIdArg])

    init {
        viewModelScope.launch {
            bookRepository.getItemStream(bookId).collect() { book ->
                if (book != null) {
                    uiState.value = ReaderUIState(book = book, currentUrl = if (book.bookMarkUrl != "") book.bookMarkUrl else book.link)
                } else {
                    uiState.value = ReaderUIState(book = Book(0, "", "", "", 1, "", ""))
                }

            }
        }
    }


    fun updateCurrentUrl(newUrl : String) {

        if (newUrl != uiState.value.currentUrl && newUrl != "about:blank") {
            uiState.value =  uiState.value.copy(
                book = uiState.value.book.copy(bookMarkUrl = newUrl),
                currentUrl = newUrl
            )
        }
    }

    fun updateBookMark() {
        viewModelScope.launch {
            bookRepository.updateItem(uiState.value.book)

        }
    }
}