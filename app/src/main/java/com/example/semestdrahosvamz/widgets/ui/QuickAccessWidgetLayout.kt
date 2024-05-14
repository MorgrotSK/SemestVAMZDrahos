package com.example.semestdrahosvamz.widgets.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.widgets.actions.OpenBookActivity

@Composable
fun QuickAccessWidgetLayout(book: Book) {
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
        Text(
            text = "Hello! " + book.title,
            style = TextStyle(
                color = GlanceTheme.colors.primary
            )
        )
    }
}