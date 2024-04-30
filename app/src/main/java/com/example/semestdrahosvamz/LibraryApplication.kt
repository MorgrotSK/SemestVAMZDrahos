package com.example.semestdrahosvamz

import android.app.Application
import com.example.semestdrahosvamz.Data.AppContainer
import com.example.semestdrahosvamz.Data.AppDataContainer

class LibraryApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}