package com.example.semestdrahosvamz.ui.screens.bookEntry

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.semestdrahosvamz.Data.BookRepository
import com.example.semestdrahosvamz.ui.screens.library.BookEntryUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookEntryViewModel(private val bookRepository: BookRepository) : ViewModel() {
    var bookEntryUIState by mutableStateOf(BookEntryUIState())
        private set

    fun updateState(title : String, link : String, imageUri : Uri) {
        bookEntryUIState = BookEntryUIState(title, link, imageUri)
    }
    suspend fun saveBook() {
        bookRepository.insertItem(bookEntryUIState.getBook())
    }

}
