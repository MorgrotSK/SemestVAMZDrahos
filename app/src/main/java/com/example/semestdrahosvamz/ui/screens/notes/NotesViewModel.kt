package com.example.semestdrahosvamz.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.BookRepository
import com.example.semestdrahosvamz.ui.screens.library.LibraryUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class NotesViewModel(bookRepository: BookRepository) : ViewModel(){
    val libraryUiState = MutableStateFlow(LibraryUIState())

    init {
        viewModelScope.launch {
            bookRepository.getAllItemsStream().collect { books ->
                libraryUiState.value = libraryUiState.value.copy(bookList = books)
            }
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }



}