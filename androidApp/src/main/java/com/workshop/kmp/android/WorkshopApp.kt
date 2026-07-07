package com.workshop.kmp.android

import android.app.Application
import com.workshop.kmp.di.androidModule
import com.workshop.kmp.di.initKoin
import org.koin.android.ext.koin.androidContext

class WorkshopApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(listOf(androidModule)).also { koinApp ->
            koinApp.koin.declare(this as android.content.Context)
        }
    }
}
