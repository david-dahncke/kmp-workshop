package com.workshop.kmp.di

import com.workshop.kmp.data.remote.ItemApiService
import com.workshop.kmp.data.remote.createHttpClient
import com.workshop.kmp.data.repository.ItemRepositoryImpl
import com.workshop.kmp.domain.ItemRepository
import com.workshop.kmp.domain.usecases.GetItemDetailUseCase
import com.workshop.kmp.domain.usecases.GetItemsUseCase
import com.workshop.kmp.domain.usecases.ToggleFavoriteUseCase
import com.workshop.kmp.presentation.ItemDetailViewModel
import com.workshop.kmp.presentation.ItemListViewModel
import org.koin.dsl.module

// FavoritesDataSource wird vom plattformspezifischen Modul (androidModule / iosModule) bereitgestellt
val sharedModule = module {
    single { createHttpClient() }
    single { ItemApiService(get()) }
    single<ItemRepository> { ItemRepositoryImpl(get(), get()) }

    factory { GetItemsUseCase(get()) }
    factory { GetItemDetailUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }

    factory { ItemListViewModel(get(), get()) }
    factory { ItemDetailViewModel(get(), get()) }
}
