package com.example.semestdrahosvamz.widgets.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.widgets.actions.OpenBookActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun QuickAccessWidgetLayout(book: Book, context: Context) {
    val bitmapState = remember { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(book.imageUri) {
        if (book.imageUri.isNotEmpty()) {
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val source = ImageDecoder.createSource(context.contentResolver, Uri.parse(book.imageUri))
                    bitmapState.value = ImageDecoder.decodeBitmap(source)
                } catch (e: Exception) {
                    e.printStackTrace()  // Keep this for minimal error handling
                }
            }
        }
    }

    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .padding(0.dp)
            .background(GlanceTheme.colors.background)
            .clickable(
                actionRunCallback<OpenBookActivity>(
                    actionParametersOf(OpenBookActivity.KEY_BOOK_LINK to book.link)
                )
            )
    ) {
        bitmapState.value?.let { bitmap ->
            Image(
                provider = ImageProvider(bitmap),
                contentDescription = null,
                modifier = GlanceModifier
                    .fillMaxSize()
                    .padding(0.dp)
            )
        }
    }
}
