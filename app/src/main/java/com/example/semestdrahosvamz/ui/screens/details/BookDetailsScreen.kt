package com.example.semestdrahosvamz.ui.screens.details


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.R
import com.example.semestdrahosvamz.ui.ViewModelProvider
import com.example.semestdrahosvamz.ui.screens.details.UiElements.BookBaseInfoSection
import com.example.semestdrahosvamz.ui.screens.details.UiElements.BookNotesSection
import com.example.semestdrahosvamz.ui.screens.details.UiElements.ControlButtonsSection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(navigateBack: () -> Unit, navigateToNotes: (Long) -> Unit, viewModel: BookDetailsViewModel = viewModel(factory = ViewModelProvider.Factory), navigateToReader: (Int) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsState()
    var deleteDialogue by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.value.book.title) },
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
                onClick = { deleteDialogue = true }
            ) {
                Icon(Icons.Filled.Delete, "")
            }
        },
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (maxWidth < maxHeight) {
                // Portrait Layout
                ContentSectionPortrait(
                    book = uiState.value.book,
                    onOpenClick = viewModel::openBookLink,
                    onBindClick = viewModel::bindToWidget,
                    onReadClick = {navigateToReader(uiState.value.book.id.toInt()) },
                    onStatusChange = viewModel::updateReadingStatus,
                    navigateToNotes = navigateToNotes
                )
            } else {
                // Landscape Layout
                ContentSectionLandscape(
                    book = uiState.value.book,
                    onOpenClick = viewModel::openBookLink,
                    onBindClick = viewModel::bindToWidget,
                    onReadClick = {navigateToReader(uiState.value.book.id.toInt()) },
                    onStatusChange = viewModel::updateReadingStatus,
                    navigateToNotes = navigateToNotes
                )
            }
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
                onCancel = { deleteDialogue = false }
            )
        }
    }
}

@Composable
fun ContentSectionPortrait(
    book: Book,
    onOpenClick: () -> Unit,
    onBindClick: () -> Unit,
    onReadClick: () -> Unit,
    onStatusChange: (Int) -> Unit,
    navigateToNotes: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        BookBaseInfoSection(
            book = book,
            innerPadding = PaddingValues(0.dp),
            onStatusChange = onStatusChange,
        )
        Spacer(modifier = Modifier.height(10.dp))
        ControlButtonsSection(
            onOpenClick = onOpenClick,
            onBindClick = onBindClick,
            onReadClick = onReadClick
        )
        Spacer(modifier = Modifier.height(10.dp))
        BookNotesSection(book = book, navigateToNotes)
    }

}

@Composable
fun ContentSectionLandscape(
    book: Book,
    onOpenClick: () -> Unit,
    onBindClick: () -> Unit,
    onReadClick: () -> Unit,
    onStatusChange: (Int) -> Unit,
    navigateToNotes: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        BookBaseInfoSection(
            book = book,
            innerPadding = PaddingValues(0.dp),
            onStatusChange = onStatusChange,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ControlButtonsSection(
                onOpenClick = onOpenClick,
                onBindClick = onBindClick,
                onReadClick = onReadClick
            )
            Spacer(modifier = Modifier.height(16.dp))
            BookNotesSection(book = book, navigateToNotes)
        }
    }
}

@Composable
fun DeleteDialogue(onConfirm: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
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
        }
    )
}
