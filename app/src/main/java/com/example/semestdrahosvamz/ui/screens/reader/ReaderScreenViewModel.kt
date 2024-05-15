package com.example.semestdrahosvamz.ui.screens.reader

import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import com.example.semestdrahosvamz.Data.BookRepository
import com.example.semestdrahosvamz.ui.screens.details.BookDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow

class ReaderScreenViewModel(bookRepository: BookRepository) : ViewModel() {
    var uiState = MutableStateFlow(BookDetailsUiState())

    fun updateCurrentUrl(newUrl : String) {

    }

    fun updateBookMark() {

    }
}