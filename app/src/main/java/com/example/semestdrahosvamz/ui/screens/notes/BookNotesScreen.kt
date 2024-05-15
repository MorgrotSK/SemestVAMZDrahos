package com.example.semestdrahosvamz.ui.screens.notes

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.internal.updateLiveLiteralValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semestdrahosvamz.R
import com.example.semestdrahosvamz.ui.ViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookNotesScreen(navigateBack : () -> Unit,  viewModel: NotesViewModel = viewModel(factory = ViewModelProvider.Factory)) {

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
            onSave = { coroutineScope.launch {
                viewModel.saveChanges()
                navigateBack()
            } }
        )
    }
}


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
            TextField(
                value = noteText,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
        ControlButtons(onSave = onSave, onCancel = onCancel)
    }
}

@Composable
fun ControlButtons(onSave : () -> Unit, onCancel : () -> Unit) {
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
