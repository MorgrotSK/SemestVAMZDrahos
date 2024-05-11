package com.example.semestdrahosvamz.ui.screens.details

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.Data.BookRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BookDetailsViewModel (savedStateHandle: SavedStateHandle, private val bookRepository: BookRepository, private val context: Context,) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[BookDetailsScreenDestination.bookIdArg])

    val uiState: StateFlow<BookDetailsUiState> =
        bookRepository.getItemStream(itemId)
            .filterNotNull()
            .map {
                BookDetailsUiState(book = it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = BookDetailsUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
