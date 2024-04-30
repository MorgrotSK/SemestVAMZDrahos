package com.example.semestdrahosvamz.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.BookRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn



class LibraryViewModel(bookRepository: BookRepository) : ViewModel(){
    val libraryUiState: StateFlow<LibraryUIState> =
        bookRepository.getAllItemsStream().map {LibraryUIState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = LibraryUIState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}