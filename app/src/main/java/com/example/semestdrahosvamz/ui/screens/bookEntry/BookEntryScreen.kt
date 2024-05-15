package com.example.semestdrahosvamz.ui.screens.bookEntry

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semestdrahosvamz.R
import com.example.semestdrahosvamz.ui.ViewModelProvider
import kotlinx.coroutines.Dispatchers
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
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.fillMaxHeight(0.075f)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.saveBook()
                                navigateBack()
                            }
                        },
                        enabled = viewModel.validateInput()
                    ) {
                        Icon(Icons.Default.Done,  contentDescription = "")
                    }
                }
            }
        }
    ) { innerPadding ->
        BookEntryForm(innerPadding, viewModel.bookEntryUIState.title, viewModel.bookEntryUIState.link, viewModel.bookEntryUIState.imageUri, viewModel::updateState)
    }
}


@Composable
fun BookEntryForm(
    innerPadding: PaddingValues,
    bookTitle: String,
    bookLink: String,
    bookImageUri : Uri,
    onValueChange: (String, String, Uri) -> Unit
) {

    BoxWithConstraints(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        if (maxWidth < maxHeight) {
            // Portrait Layout
            BookEntryFormPortrait(
                innerPadding = innerPadding,
                bookTitle = bookTitle,
                bookLink = bookLink,
                bookImageUri = bookImageUri,
                onValueChange = onValueChange
            )
        } else {
            //Lanscape Layout
            BookEntryFormLandscape(
                innerPadding = innerPadding,
                bookTitle = bookTitle,
                bookLink = bookLink,
                bookImageUri = bookImageUri,
                onValueChange = onValueChange
            )
        }
    }

}
@Composable
fun BookEntryFormLandscape(
    innerPadding: PaddingValues,
    bookTitle: String,
    bookLink: String,
    bookImageUri: Uri,
    onValueChange: (String, String, Uri) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectableImage(bookTitle, bookLink, bookImageUri, onValueChange)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Text field for book title
            OutlinedTextField(
                value = bookTitle,
                onValueChange = { title -> onValueChange(title, bookLink, bookImageUri) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Text field for book link
            OutlinedTextField(
                value = bookLink,
                onValueChange = { link -> onValueChange(bookTitle, link, bookImageUri) },
                label = { Text("Link") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BookEntryFormPortrait(
    innerPadding: PaddingValues,
    bookTitle: String,
    bookLink: String,
    bookImageUri: Uri,
    onValueChange: (String, String, Uri) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectableImage(bookTitle, bookLink, bookImageUri, onValueChange)

        // Text field for book title
        OutlinedTextField(
            value = bookTitle,
            onValueChange = { title -> onValueChange(title, bookLink, bookImageUri) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        // Text field for book link
        OutlinedTextField(
            value = bookLink,
            onValueChange = { link -> onValueChange(bookTitle, link, bookImageUri) },
            label = { Text("Link") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SelectableImage(
    bookTitle: String,
    bookLink: String,
    bookImageUri: Uri,
    onValueChange: (String, String, Uri) -> Unit
) {
    val context = LocalContext.current
    val bitmap = remember(bookImageUri) { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onValueChange(bookTitle, bookLink, it) }
    }

    BoxWithConstraints {
        val imageModifier = Modifier
            .fillMaxWidth(0.5f)
            .aspectRatio(2f / 3f)
            .clickable { launcher.launch("image/*") }

        if (bookImageUri != Uri.EMPTY) {
            LaunchedEffect(bookImageUri) {
                coroutineScope.launch(Dispatchers.IO) {
                    val source = ImageDecoder.createSource(context.contentResolver, bookImageUri)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Box(
                modifier = imageModifier,
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.book_cover_placeholder),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = stringResource(R.string.messagePickImage),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}





