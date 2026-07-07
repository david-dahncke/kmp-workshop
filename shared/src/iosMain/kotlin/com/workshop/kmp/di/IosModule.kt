package com.workshop.kmp.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.workshop.kmp.data.local.FavoritesDataSource
import com.workshop.kmp.data.local.SqlDelightFavoritesDataSource
import com.workshop.kmp.db.WorkshopDatabase
import com.workshop.kmp.platform.IosLogger
import com.workshop.kmp.platform.Logger
import org.koin.dsl.module

val iosModule = module {
    single<Logger> { IosLogger() }
    single<WorkshopDatabase> {
        val driver = NativeSqliteDriver(WorkshopDatabase.Schema, "workshop.db")
        WorkshopDatabase(driver)
    }
    single<FavoritesDataSource> { SqlDelightFavoritesDataSource(get()) }
}
