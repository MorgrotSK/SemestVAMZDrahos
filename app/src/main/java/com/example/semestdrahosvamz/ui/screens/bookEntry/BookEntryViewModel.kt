package com.example.semestdrahosvamz.ui.screens.bookEntry

import androidx.lifecycle.ViewModel
import com.example.semestdrahosvamz.Data.BookRepository
import com.example.semestdrahosvamz.ui.screens.library.BookEntryUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookEntryViewModel(bookRepository: BookRepository) : ViewModel() {
    val bookEntryUIState : StateFlow<BookEntryUIState> = MutableStateFlow(BookEntryUIState())

}
