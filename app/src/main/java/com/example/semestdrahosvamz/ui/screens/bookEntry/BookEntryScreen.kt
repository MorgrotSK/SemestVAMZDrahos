package com.example.semestdrahosvamz.ui.screens.bookEntry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semestdrahosvamz.ui.ViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookEntryScreen(navigateBack: () -> Unit, viewModel: BookEntryViewModel = viewModel(factory = ViewModelProvider.Factory)) {

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Place holder add book")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            viewModel.saveBook()
                            navigateBack()
                        }
                    },
                    ) {
                        Icon(Icons.Default.Done,  contentDescription = "Save thew new book")
                    }
                }
            }
        }
    ) { innerPadding ->
        BookEntryForm(innerPadding, viewModel.bookEntryUIState.title, viewModel.bookEntryUIState.link, viewModel::updateState)
    }
}
@Composable
fun BookEntryForm(
    innerPadding: PaddingValues,
    bookTitle: String,
    bookLink: String,
    onValueChange: (String, String) -> Unit
) {
    Column(
        modifier = Modifier.padding(innerPadding).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Text field for book title
        OutlinedTextField(
            value = bookTitle,
            onValueChange = { title -> onValueChange(title, bookLink) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        // Text field for book link
        OutlinedTextField(
            value = bookLink,
            onValueChange = { link -> onValueChange(bookTitle, link) },
            label = { Text("Link") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
