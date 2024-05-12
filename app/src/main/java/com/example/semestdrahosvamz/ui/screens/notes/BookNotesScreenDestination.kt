package com.example.semestdrahosvamz.ui.screens.notes

import com.example.semestdrahosvamz.ui.navigation.NavigationDestination
import com.example.semestdrahosvamz.ui.screens.details.BookDetailsScreenDestination

object BookNotesScreenDestination : NavigationDestination {
    override val route: String = "notesEditor"
    const val bookIdArg = "bookId"
    val routeWithArgs = "${route}/{$bookIdArg}"
}

