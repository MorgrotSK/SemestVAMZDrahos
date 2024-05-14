package com.example.semestdrahosvamz.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.Data.BookDatabase

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class QuickAccessWidget() : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val sharedPreferences = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        val bookId = sharedPreferences.getInt("book_id", -1)

        val book = if (bookId != -1) getBookById(bookId, context) else null

        provideContent {
            book?.let{QuickAccessWidgetContent(book = book)}
        }
    }
    suspend fun getBookById(id: Int, context: Context): Book? {
        return withContext(Dispatchers.IO) {
            val bookDao = BookDatabase.getDatabase(context).bookDao()
            bookDao.getItem(id).firstOrNull()
        }
    }

    @Composable
    fun QuickAccessWidgetContent(book: Book) {
        Column(
            modifier = androidx.glance.GlanceModifier
                .fillMaxSize()
                .padding(16.dp)
                .background(GlanceTheme.colors.background)

        ) {
            Text(
                text = "Hello! " + book.title,
                style = androidx.glance.text.TextStyle(
                    color = GlanceTheme.colors.primary
                )
            )
        }
    }
}


