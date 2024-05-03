package com.example.semestdrahosvamz.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.semestdrahosvamz.LibraryApplication
import com.example.semestdrahosvamz.ui.screens.bookEntry.BookEntryViewModel
import com.example.semestdrahosvamz.ui.screens.library.LibraryViewModel


object ViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for LibraryViewModel
        initializer {
            LibraryViewModel(inventoryApplication().container.bookRepository)
        }
        initializer {
            BookEntryViewModel(inventoryApplication().container.bookRepository, inventoryApplication())
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): LibraryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LibraryApplication)