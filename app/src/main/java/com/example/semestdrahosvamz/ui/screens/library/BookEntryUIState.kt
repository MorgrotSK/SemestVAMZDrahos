package com.example.semestdrahosvamz.ui.screens.library

import android.net.Uri
import com.example.semestdrahosvamz.Data.Book

data class BookEntryUIState(var title : String = "", var link : String = "", var imageUri : Uri = Uri.EMPTY) {
    fun getBook() : Book {
        return Book(
            title = title,
            link = link,
            imageUri = imageUri.toString(),
            status = 1,
        )
    }
};
