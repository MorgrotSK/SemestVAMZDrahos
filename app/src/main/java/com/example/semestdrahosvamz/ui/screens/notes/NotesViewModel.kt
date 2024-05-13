package com.example.semestdrahosvamz.ui.screens.notes

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestdrahosvamz.Data.BookRepository
import com.example.semestdrahosvamz.ui.screens.details.BookDetailsScreenDestination
import com.example.semestdrahosvamz.ui.screens.library.LibraryUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class NotesViewModel(savedStateHandle: SavedStateHandle, private val bookRepository: BookRepository) : ViewModel(){
    private val itemId: Int = checkNotNull(savedStateHandle[BookNotesScreenDestination.bookIdArg])

    val notesUiState = MutableStateFlow(NotesUIState())

    init {
        viewModelScope.launch {
            bookRepository.getItemStream(itemId).collect { book ->
                notesUiState.value = notesUiState.value.copy(book = book)
            }
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun updateNotesText(newText : String) {
        notesUiState.value =  notesUiState.value.copy(notesUiState.value.book.copy(notes = newText))
    }

    fun saveChanges() {
        Log.i("", notesUiState.value.book.id.toString());
        viewModelScope.launch {
            bookRepository.updateItem(notesUiState.value.book)

        }
    }



}