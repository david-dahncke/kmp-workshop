package com.workshop.kmp.domain

import com.workshop.kmp.data.local.InMemoryFavoritesDataSource
import com.workshop.kmp.data.remote.ItemApiService
import com.workshop.kmp.data.remote.createHttpClient
import com.workshop.kmp.data.repository.ItemRepositoryImpl

/**
 * Repository-Interface im Domain-Layer.
 * Die Implementierung liegt in data/ — Domain kennt keine Infrastrukturdetails.
 */
interface ItemRepository {
    suspend fun getItems(): List<Item>
    suspend fun getItemDetail(itemId: String): Item?
    suspend fun toggleFavorite(itemId: String)
    suspend fun getFavoriteIds(): Set<String>
}

/**
 * Factory für einen sofort nutzbaren Repository (ohne DI-Framework).
 * Nützlich auf frühen Branches (02-repository) wo Koin noch nicht eingerichtet ist.
 */
fun createItemRepository(): ItemRepository = ItemRepositoryImpl(
    apiService = ItemApiService(createHttpClient()),
    favoritesDataSource = InMemoryFavoritesDataSource(),
)
