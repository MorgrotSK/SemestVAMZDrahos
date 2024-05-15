package com.example.semestdrahosvamz.ui.screens.reader

import com.example.semestdrahosvamz.Data.Book

data class ReaderUIState(
    var book : Book = Book(0, "", "", "", 1, ""),
    var currentUrl : String = book.link
)
