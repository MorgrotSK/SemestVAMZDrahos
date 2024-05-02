package com.example.semestdrahosvamz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.semestdrahosvamz.ui.screens.bookEntry.BookEntryScreen
import com.example.semestdrahosvamz.ui.screens.bookEntry.BookEntryScreenDestination
import com.example.semestdrahosvamz.ui.screens.details.BookDetailsScreenDestination
import com.example.semestdrahosvamz.ui.screens.library.LibraryScreen
import com.example.semestdrahosvamz.ui.screens.library.LibraryScreenDestination

@Composable
fun LibraryNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = LibraryScreenDestination.route, modifier = modifier) {
        composable(route = LibraryScreenDestination.route) {
           LibraryScreen(
               navigateToBookDetails = {navController.navigate("${BookDetailsScreenDestination.route}/${it}") },
               navigateToBookEntry = {navController.navigate(BookEntryScreenDestination.route)}
           )
        }
        composable(route = BookEntryScreenDestination.route) {
            BookEntryScreen(navigateBack = {navController.popBackStack()})
        }
    }

}