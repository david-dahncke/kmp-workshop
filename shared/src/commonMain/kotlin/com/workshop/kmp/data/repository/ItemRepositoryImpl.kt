package com.workshop.kmp.data.repository

import com.workshop.kmp.data.local.FavoritesDataSource
import com.workshop.kmp.data.mapper.toDomain
import com.workshop.kmp.data.remote.ItemApiService
import com.workshop.kmp.domain.Item
import com.workshop.kmp.domain.ItemRepository

/**
 * Repository-Implementierung: verbindet API (Ktor) und lokalen Speicher.
 * Das Domain-Layer kennt nur das Interface — nie diese Klasse direkt.
 */
class ItemRepositoryImpl(
    private val apiService: ItemApiService,
    private val favoritesDataSource: FavoritesDataSource,
) : ItemRepository {

    override suspend fun getItems(): List<Item> {
        val favoriteIds = favoritesDataSource.getFavoriteIds()
        return apiService.fetchItems().toDomain(favoriteIds)
    }

    override suspend fun getItemDetail(itemId: String): Item? {
        return try {
            val favoriteIds = favoritesDataSource.getFavoriteIds()
            apiService.fetchItemDetail(itemId).toDomain(isFavorite = itemId in favoriteIds)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun toggleFavorite(itemId: String) {
        if (favoritesDataSource.isFavorite(itemId)) {
            favoritesDataSource.removeFavorite(itemId)
        } else {
            favoritesDataSource.addFavorite(itemId)
        }
    }

    override suspend fun getFavoriteIds(): Set<String> = favoritesDataSource.getFavoriteIds()
}
