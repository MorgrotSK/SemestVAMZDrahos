package com.example.semestdrahosvamz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.semestdrahosvamz.ui.screens.bookEntry.BookEntryScreen
import com.example.semestdrahosvamz.ui.screens.bookEntry.BookEntryScreenDestination
import com.example.semestdrahosvamz.ui.screens.details.BookDetailsScreen
import com.example.semestdrahosvamz.ui.screens.details.BookDetailsScreenDestination
import com.example.semestdrahosvamz.ui.screens.library.LibraryScreen
import com.example.semestdrahosvamz.ui.screens.library.LibraryScreenDestination
import com.example.semestdrahosvamz.ui.screens.notes.BookNotesScreen
import com.example.semestdrahosvamz.ui.screens.notes.BookNotesScreenDestination
import com.example.semestdrahosvamz.ui.screens.reader.ReaderScreen
import com.example.semestdrahosvamz.ui.screens.reader.ReaderScreenDestination

@Composable
fun LibraryNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = LibraryScreenDestination.route, modifier = modifier) {
        composable(route = LibraryScreenDestination.route) {
           LibraryScreen(
               navigateToBookDetails = {navController.navigate("${BookDetailsScreenDestination.route}/${it.id}") },
               navigateToBookEntry = {navController.navigate(BookEntryScreenDestination.route)}
           )
        }
        composable(route = BookEntryScreenDestination.route ) {
            BookEntryScreen(navigateBack = {navController.popBackStack()})
        }
        composable(
            route = BookDetailsScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(BookDetailsScreenDestination.bookIdArg) {
                type = NavType.IntType
            })

        ) {
            BookDetailsScreen(
                navigateBack = {navController.popBackStack()},
                navigateToNotes = {navController.navigate("${BookNotesScreenDestination.route}/${it}")},
                navigateToReader = {navController.navigate("${ReaderScreenDestination.route}/${it}")},
            )
        }

        composable(
            route = ReaderScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(BookDetailsScreenDestination.bookIdArg) {type = NavType.IntType},
            )
        ) {
            ReaderScreen(navigateBack = {navController.popBackStack()})
        }


        composable(route = BookNotesScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(BookNotesScreenDestination.bookIdArg) {
                type = NavType.IntType
            })
        ) {
            BookNotesScreen(navigateBack = {navController.popBackStack()})
        }
    }

}