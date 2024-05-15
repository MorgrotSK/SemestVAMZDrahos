package com.example.semestdrahosvamz.ui.screens.reader

import com.example.semestdrahosvamz.ui.navigation.NavigationDestination

object ReaderScreenDestination : NavigationDestination {
    override val route: String = "reader"
    const val bookIdArg = "bookId"
    val routeWithArgs = "${route}/{${bookIdArg}}"
}
