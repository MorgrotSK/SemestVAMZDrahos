package com.example.semestdrahosvamz.widgets.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
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
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.widgets.actions.OpenBookActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun QuickAccessWidgetLayout(book: Book, context : Context) {
    val bitmap = remember(book.imageUri) { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .padding(16.dp)
            .background(GlanceTheme.colors.background)
            .clickable(
                actionRunCallback<OpenBookActivity>(
                actionParametersOf(OpenBookActivity.KEY_BOOK_LINK to book.link)
            )
            )

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
                    provider = ImageProvider(btm),
                    contentDescription = null,
                    modifier = GlanceModifier
                        .fillMaxHeight()
                        .width(225.dp)
                )
            }
        }
        Text(
            text = "Hello! " + book.title,
            style = TextStyle(
                color = GlanceTheme.colors.primary
            )
        )
    }
}