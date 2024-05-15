package com.example.semestdrahosvamz.ui.screens.reader

import com.example.semestdrahosvamz.ui.navigation.NavigationDestination
import com.example.semestdrahosvamz.ui.screens.notes.BookNotesScreenDestination

object ReaderScreenDestination : NavigationDestination {
    override val route: String = "reader"
    const val bookIdArg = "bookId"
    val routeWithArgs = "${route}/{${bookIdArg}}"
}
