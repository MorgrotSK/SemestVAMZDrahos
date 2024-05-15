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

/**
 * Composable function to display a quick access widget layout.
 *
 * This layout displays the cover image of a book and makes the entire layout clickable,
 * which triggers an action to open the book activity.
 *
 * @param book The book data containing the image URI and link.
 * @param context The context used to resolve the image URI.
 */
@Composable
fun QuickAccessWidgetLayout(book: Book, context: Context) {
    // State to hold the bitmap of the book cover image
    val bitmapState = remember { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Load the image bitmap when the image URI changes
    LaunchedEffect(book.imageUri) {
        if (book.imageUri.isNotEmpty()) {
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val source = ImageDecoder.createSource(context.contentResolver, Uri.parse(book.imageUri))
                    bitmapState.value = ImageDecoder.decodeBitmap(source)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    // Column layout to display the image and make it clickable
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
        // Display the image if the bitmap is not null
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
