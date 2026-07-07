package com.workshop.kmp.di

import com.workshop.kmp.data.remote.ItemApiService
import com.workshop.kmp.data.remote.createHttpClient
import com.workshop.kmp.data.repository.ItemRepositoryImpl
import com.workshop.kmp.domain.ItemRepository
import com.workshop.kmp.domain.usecases.GetItemDetailUseCase
import com.workshop.kmp.domain.usecases.GetItemsUseCase
import com.workshop.kmp.presentation.ItemDetailViewModel
import com.workshop.kmp.presentation.ItemListViewModel
import org.koin.dsl.module

val sharedModule = module {
    single { createHttpClient() }
    single { ItemApiService(get()) }
    single<ItemRepository> { ItemRepositoryImpl(get(), get()) }

    factory { GetItemsUseCase(get()) }
    factory { GetItemDetailUseCase(get()) }
    // TODO (Übung 2): ToggleFavoriteUseCase hier registrieren

    factory { ItemListViewModel(get()) }
    factory { ItemDetailViewModel(get()) }
    // TODO (Übung 2): ViewModels mit ToggleFavoriteUseCase verdrahten
}
