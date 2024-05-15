package com.example.semestdrahosvamz.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.semestdrahosvamz.LibraryApplication
import com.example.semestdrahosvamz.ui.screens.bookEntry.BookEntryViewModel
import com.example.semestdrahosvamz.ui.screens.details.BookDetailsViewModel
import com.example.semestdrahosvamz.ui.screens.library.LibraryViewModel
import com.example.semestdrahosvamz.ui.screens.notes.NotesViewModel
import com.example.semestdrahosvamz.ui.screens.reader.ReaderScreenViewModel


object ViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for LibraryViewModel
        initializer {
            LibraryViewModel(inventoryApplication().container.bookRepository)
        }
        initializer {
            BookEntryViewModel(inventoryApplication().container.bookRepository, inventoryApplication())
        }
        initializer {
            BookDetailsViewModel(this.createSavedStateHandle(), inventoryApplication().container.bookRepository, inventoryApplication())
        }
        initializer {
            NotesViewModel(this.createSavedStateHandle(), inventoryApplication().container.bookRepository)
        }
        initializer {
            ReaderScreenViewModel(this.createSavedStateHandle(), inventoryApplication().container.bookRepository)
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): LibraryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LibraryApplication)