package com.example.semestdrahosvamz.ui.screens.library

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.ui.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun BookGrid(
    bookList: List<Book>,
    onBookClick: (Book) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(items = bookList, key = { it.id }) { book ->
            ShowBook(book = book, onBookClick)
        }
    }

}

@Composable
fun ShowBook(book: Book, onBookClick: (Book) -> Unit) {
    val bitmap = remember(book.imageUri) { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Card( modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .height(210.dp)
        .width(210.dp)
        .clickable { onBookClick(book) },

    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            if (book.imageUri != "") {
                LaunchedEffect(book.imageUri) {
                    coroutineScope.launch(Dispatchers.IO) {
                        val source = ImageDecoder.createSource(context.contentResolver, Uri.parse(book.imageUri))
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }
                }

                bitmap.value?.let { btm ->
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .height(175.dp)
                            .width(150.dp)
                    )
                }
            }

            Text(
                text = book.title,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreenTopBar(
    onSearchType: (String) -> Unit,
    searchValue: String,
) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(Modifier.width(8.dp))
                TextField(
                    value = searchValue,
                    onValueChange = { value -> onSearchType(value)},
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text("Search") },
                    modifier = Modifier.width(200.dp)
                )
            }
        }
    )
}

@Composable
fun LibraryBottomBar(
    onCategoryChange: (Boolean, Boolean, Boolean) -> Unit,
    planned: Boolean,
    reading: Boolean,
    finished: Boolean,
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CheckboxWithLabel("Finished", finished) { isChecked -> onCategoryChange(planned, reading, isChecked) }
            CheckboxWithLabel("Reading", reading) { isChecked -> onCategoryChange(planned, isChecked, finished) }
            CheckboxWithLabel("Planned", planned) { isChecked -> onCategoryChange(isChecked, reading, finished) }
        }
    }
}

@Composable
fun CheckboxWithLabel(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { isChecked -> onCheckedChange(isChecked)            }
        )
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = viewModel(factory = ViewModelProvider.Factory),
    navigateToBookDetails: (Book) -> Unit,
    navigateToBookEntry: () -> Unit,
) {
    val uiState by viewModel.libraryUiState.collectAsState()

    Scaffold(
        topBar = {
            LibraryScreenTopBar(viewModel::updateFilter, uiState.searchValue)
        },
        bottomBar = {
           LibraryBottomBar(viewModel::setCategories, uiState.planned, uiState.reading, uiState.finished)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToBookEntry,
            ) {
                Icon(Icons.Filled.Add, "")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val filteredBooks = uiState.bookList.filter {
                it.title.contains(uiState.searchValue, ignoreCase = true) && ((uiState.reading && it.status == 1) || (uiState.planned && it.status == 2) || (uiState.finished && it.status == 0))
            }
            BookGrid(bookList = filteredBooks, navigateToBookDetails)
        }
    }
}
