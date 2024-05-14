package com.example.semestdrahosvamz.ui.screens.details

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.R
import com.example.semestdrahosvamz.ui.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun BookNotesSection(book: Book, onEditNotes : (Long) -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.notesSectionTitles),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { onEditNotes(book.id) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(Icons.Filled.Edit, "")
                }
            }
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = book .notes,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BookBaseInfoSection(book: Book, innerPadding: PaddingValues, onStatusChange : (Int) -> Unit) {
    val bitmap = remember(book.imageUri) { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val imageSize = 225.dp

    Surface(modifier = Modifier.padding(innerPadding)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            if (book.imageUri.isNotEmpty()) {
                LaunchedEffect(book.imageUri) {
                    coroutineScope.launch(Dispatchers.IO) {
                        val source = ImageDecoder.createSource(context.contentResolver, Uri.parse(book.imageUri))
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }
                }

                bitmap.value?.let { btm ->
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxHeight(0.85f)
                            .width(imageSize)
                    )
                }
            }
            Column(modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                )
                ReadingStatusOptions(selectedOption = book.status, onOptionSelected = onStatusChange)
            }

        }
    }
}

@Composable
fun ReadingStatusOptions(selectedOption: Int, onOptionSelected: (Int) -> Unit) {
    val options = listOf(stringResource(R.string.readingStatusFinished), stringResource(R.string.readingStatusReading), stringResource(R.string.readingStatusPlanned))
    options.forEachIndexed { index, text ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            RadioButton(
                selected = text == options[selectedOption],
                onClick = { onOptionSelected(index) }
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clickable { onOptionSelected(index) }
            )
        }
    }
}

@Composable
fun DeleteDialogue(onConfirm : () -> Unit, onCancel : () -> Unit) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.delDiaTitle)) },
        text = { Text(stringResource(R.string.delDiaText)) },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.yes))
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(navigateBack: () -> Unit, navigateToNotes : (Long) -> Unit, viewModel: BookDetailsViewModel = viewModel(factory = ViewModelProvider.Factory)) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsState()
    var deleteDialogue by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = uiState.value.book.title)
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {deleteDialogue = true }
            ) {
                Icon(Icons.Filled.Delete, "")
            }
        },

    ) { innerPadding ->
        Column {
            BookBaseInfoSection(book = uiState.value.book, innerPadding = innerPadding, viewModel::updateReadingStatus)
            Button(onClick = viewModel::OpenBookLink) {
                Text(stringResource(R.string.readButton))
            }
            BookNotesSection(book = uiState.value.book, navigateToNotes)
        }

        if (deleteDialogue) {
            DeleteDialogue(
                onConfirm = {
                    coroutineScope.launch {
                        deleteDialogue = false
                        viewModel.deleteBook()
                        navigateBack()
                    }
                },
                onCancel = {deleteDialogue = false}
                )
        }
    }
}
