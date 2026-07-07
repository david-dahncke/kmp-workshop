package com.workshop.kmp.di

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.workshop.kmp.data.local.FavoritesDataSource
import com.workshop.kmp.data.local.SqlDelightFavoritesDataSource
import com.workshop.kmp.db.WorkshopDatabase
import com.workshop.kmp.platform.AndroidLogger
import com.workshop.kmp.platform.Logger
import org.koin.dsl.module

val androidModule = module {
    single<Logger> { AndroidLogger() }
    single<WorkshopDatabase> {
        val driver = AndroidSqliteDriver(WorkshopDatabase.Schema, get<Context>(), "workshop.db")
        WorkshopDatabase(driver)
    }
    single<FavoritesDataSource> { SqlDelightFavoritesDataSource(get()) }
}
