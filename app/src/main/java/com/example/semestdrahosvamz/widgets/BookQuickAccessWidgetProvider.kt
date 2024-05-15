//Sourced from: https://developer.android.com/develop/ui/compose/glance/create-app-widget

package com.example.semestdrahosvamz.widgets

import QuickAccessWidget
import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class BookQuickAccessWidgetProvider : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = QuickAccessWidget()
}