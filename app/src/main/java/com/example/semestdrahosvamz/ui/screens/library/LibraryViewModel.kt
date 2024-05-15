package com.example.semestdrahosvamz.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the library screen.
 * This ViewModel manages the state of the library screen and interacts with the BookRepository to fetch data.
 *
 * @param bookRepository The repository to fetch book data.
 */
class LibraryViewModel(bookRepository: BookRepository) : ViewModel() {

    /**
     * MutableStateFlow to hold the current state of the library UI.
     */
    val libraryUiState = MutableStateFlow(LibraryUIState())

    /**
     * Initialization block to fetch the list of books from the repository and update the UI state.
     */
    init {
        viewModelScope.launch {
            bookRepository.getAllBooksStream().collect { books ->
                libraryUiState.value = libraryUiState.value.copy(bookList = books)
            }
        }
    }

    /**
     * Updates the search filter in the UI state.
     *
     * @param searchValue The new search value to update.
     */
    fun updateFilter(searchValue: String) {
        libraryUiState.value = libraryUiState.value.copy(searchValue = searchValue)
    }

    /**
     * Updates the categories in the UI state.
     *
     * @param planned The new state of the planned category.
     * @param reading The new state of the reading category.
     * @param finished The new state of the finished category.
     */
    fun setCategories(planned: Boolean, reading: Boolean, finished: Boolean) {
        libraryUiState.value = libraryUiState.value.copy(reading = reading, planned = planned, finished = finished)
    }
}
