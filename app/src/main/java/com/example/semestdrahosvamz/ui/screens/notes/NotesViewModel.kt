package com.example.semestdrahosvamz.ui.screens.notes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.BookRepository
import com.example.semestdrahosvamz.ui.screens.details.BookDetailsScreenDestination
import com.example.semestdrahosvamz.ui.screens.library.LibraryUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class NotesViewModel(savedStateHandle: SavedStateHandle, bookRepository: BookRepository) : ViewModel(){
    private val itemId: Int = checkNotNull(savedStateHandle[BookDetailsScreenDestination.bookIdArg])

    val notesUiState = MutableStateFlow(NotesUIState())

    init {
        viewModelScope.launch {
            bookRepository.getItemStream(itemId).collect { books ->
                notesUiState.value = notesUiState.value.copy()
            }
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun updateNotesText(newText : String) {
        notesUiState.value =  notesUiState.value.copy(notesUiState.value.book.copy(notes = newText))
    }



}