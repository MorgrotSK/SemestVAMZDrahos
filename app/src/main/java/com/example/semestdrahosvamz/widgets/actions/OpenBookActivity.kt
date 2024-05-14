package com.example.semestdrahosvamz.widgets.actions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback


class OpenBookActivity : ActionCallback {
    override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val bookLink = parameters[KEY_BOOK_LINK]
        val webIntent: Intent = Uri.parse(bookLink).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
        context.startActivity(webIntent)
    }

    companion object {
        val KEY_BOOK_LINK = ActionParameters.Key<String>("book_link")
    }
}
