package com.example.semestdrahosvamz.widgets

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.provideContent
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.Data.BookDatabase
import com.example.semestdrahosvamz.widgets.ui.QuickAccessWidgetLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class QuickAccessWidget() : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val sharedPreferences = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        val bookId = sharedPreferences.getInt("book_id", -1)

        val book = if (bookId != -1) getBookById(bookId, context) else null

        provideContent {
            book?.let{QuickAccessWidgetLayout(book = book, context = context)}
        }
    }
    suspend fun getBookById(id: Int, context: Context): Book? {
        return withContext(Dispatchers.IO) {
            val bookDao = BookDatabase.getDatabase(context).bookDao()
            bookDao.getItem(id).firstOrNull()
        }
    }


}
