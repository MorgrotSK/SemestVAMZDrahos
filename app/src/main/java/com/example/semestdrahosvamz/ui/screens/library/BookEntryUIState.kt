package com.example.semestdrahosvamz.ui.screens.library

import com.example.semestdrahosvamz.Data.Book

data class BookEntryUIState(var title : String = "", var link : String = "") {
    fun getBook() : Book {
        return Book(
            title = title,
            link = link,
        )
    }
};
