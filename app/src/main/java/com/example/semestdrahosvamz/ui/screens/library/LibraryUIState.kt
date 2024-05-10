package com.example.semestdrahosvamz.ui.screens.library

import com.example.semestdrahosvamz.Data.Book

data class LibraryUIState(val bookList: List<Book> = listOf(), var searchValue : String = "")
