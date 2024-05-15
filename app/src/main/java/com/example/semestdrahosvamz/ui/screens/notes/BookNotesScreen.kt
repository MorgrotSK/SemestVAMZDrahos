package com.example.semestdrahosvamz.ui.screens.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semestdrahosvamz.R
import com.example.semestdrahosvamz.ui.ViewModelProvider
import kotlinx.coroutines.launch

/**
 * Composable function to display the book notes screen.
 *
 * @param navigateBack Lambda function to handle back navigation.
 * @param viewModel The ViewModel for managing the state of the Book Notes screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookNotesScreen(navigateBack: () -> Unit, viewModel: NotesViewModel = viewModel(factory = ViewModelProvider.Factory)) {
    val uiState = viewModel.notesUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

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
    ) { innerPadding ->
        NotesEditor(
            noteText = uiState.value.book.notes,
            onValueChange = viewModel::updateNotesText,
            innerPadding = innerPadding,
            onCancel = navigateBack,
            onSave = {
                coroutineScope.launch {
                    viewModel.saveChanges()
                    navigateBack()
                }
            }
        )
    }
}

/**
 * Composable function to display the notes editor.
 *
 * @param noteText The current text of the note.
 * @param onValueChange Lambda function to handle value changes in the text field.
 * @param innerPadding Padding values for the editor.
 * @param onSave Lambda function to handle the save action.
 * @param onCancel Lambda function to handle the cancel action.
 */
@Composable
fun NotesEditor(noteText: String, onValueChange: (String) -> Unit, innerPadding: PaddingValues, onSave: () -> Unit, onCancel: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.85f)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)) {
                TextField(
                    value = noteText,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                NotesTextCounter(
                    text = noteText,
                    maxCount = NotesViewModel.MAX_CHAR_COUNT
                )
            }
        }
        ControlButtons(onSave = onSave, onCancel = onCancel)
    }
}

/**
 * Composable function to display the character count of the notes text.
 *
 * @param text The current text of the note.
 * @param maxCount The maximum allowed character count.
 */
@Composable
fun NotesTextCounter(text: String, maxCount: Int) {
    Text(text = "${text.length} / $maxCount")
}

/**
 * Composable function to display the control buttons for saving or canceling note edits.
 *
 * @param onSave Lambda function to handle the save action.
 * @param onCancel Lambda function to handle the cancel action.
 */
@Composable
fun ControlButtons(onSave: () -> Unit, onCancel: () -> Unit) {
    Row {
        Button(onClick = onSave) {
            Text(stringResource(R.string.saveButton))
        }
        Spacer(modifier = Modifier.width(4.dp))
        FilledTonalButton(onClick = onCancel) {
            Text(stringResource(R.string.cancelButton))
        }
    }
}
