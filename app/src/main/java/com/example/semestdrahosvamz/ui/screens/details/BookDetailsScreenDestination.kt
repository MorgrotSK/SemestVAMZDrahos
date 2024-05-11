package com.example.semestdrahosvamz.ui.screens.details

import com.example.semestdrahosvamz.ui.navigation.NavigationDestination
import com.example.semestdrahosvamz.ui.screens.bookEntry.BookEntryScreenDestination

object BookDetailsScreenDestination : NavigationDestination {
    override val route: String = "bookDetails"
    const val bookIdArg = "bookId"
    val routeWithArgs = "${BookDetailsScreenDestination.route}/{$bookIdArg}"
}
