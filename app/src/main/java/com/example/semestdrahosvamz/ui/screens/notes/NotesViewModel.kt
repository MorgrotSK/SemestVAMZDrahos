package com.example.semestdrahosvamz.ui.screens.notes

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the state and actions of the Notes screen.
 *
 * @property savedStateHandle The SavedStateHandle to retrieve saved state.
 * @property bookRepository The repository for managing book data.
 */
class NotesViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository
) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[BookNotesScreenDestination.bookIdArg])

    val notesUiState = MutableStateFlow(NotesUIState())

    init {
        viewModelScope.launch {
            bookRepository.getBookStream(itemId).collect { book ->
                notesUiState.value = notesUiState.value.copy(book = book)
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        const val MAX_CHAR_COUNT = 500
    }

    /**
     * Updates the notes text in the UI state.
     *
     * @param newText The new text for the notes.
     */
    fun updateNotesText(newText: String) {
        if (newText.length <= MAX_CHAR_COUNT) {
            notesUiState.value = notesUiState.value.copy(
                book = notesUiState.value.book.copy(notes = newText)
            )
        } else {
            Log.d("NotesViewModel", "Text exceeds maximum character count")
        }
    }

    /**
     * Saves the changes made to the book notes.
     */
    fun saveChanges() {
        viewModelScope.launch {
            bookRepository.updateBook(notesUiState.value.book)
        }
    }
}
