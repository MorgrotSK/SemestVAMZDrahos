package com.example.semestdrahosvamz.ui.screens.reader

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semestdrahosvamz.R
import com.example.semestdrahosvamz.ui.ViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(viewModel: ReaderScreenViewModel = viewModel(factory = ViewModelProvider.Factory), navigateBack : () -> Unit) {


    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            ReaderTopBar(bookTitle = "", navigateBack, viewModel::updateBookMark)
        }
    ) {
        innerPadding -> ContentSection(innerPadding = innerPadding, onLoadedPage = {})
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderTopBar(bookTitle : String, navigateBack: () -> Unit, onSaveClick : () -> Unit) {
    TopAppBar(
        title = {
            Text(text = bookTitle)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "")
            }
        },
        actions = {
            Button(onClick = onSaveClick) {
                Text(stringResource(R.string.updateBookMarkButton))
            }
        }
    )
}


@Composable
fun ContentSection(innerPadding : PaddingValues, onLoadedPage : (String) -> Unit) {

    //This code is sourced from: https://medium.com/@sahar.asadian90/webview-in-jetpack-compose-71f237873c2e
    AndroidView(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxHeight(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        },
        update = { webView ->
            webView.loadUrl("https://www.google.sk/")
        }
    )
}