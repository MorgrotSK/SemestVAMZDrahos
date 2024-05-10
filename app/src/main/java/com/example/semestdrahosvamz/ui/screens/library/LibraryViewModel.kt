package com.example.semestdrahosvamz.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class LibraryViewModel(bookRepository: BookRepository) : ViewModel(){
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

    fun updateFilter(searchValue: String) {
        libraryUiState.value = libraryUiState.value.copy(searchValue = searchValue)
    }
    fun setCategories(planned : Boolean, reading : Boolean, finished : Boolean) {
        libraryUiState.value = libraryUiState.value.copy(reading = reading, planned = planned, finished = finished)
    }

}