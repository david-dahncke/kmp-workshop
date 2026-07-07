package com.workshop.kmp

import com.workshop.kmp.data.local.FavoritesDataSource

class FakeFavoritesDataSource : FavoritesDataSource {
    private val favorites = mutableSetOf<String>()
    override suspend fun getFavoriteIds(): Set<String> = favorites.toSet()
    override suspend fun addFavorite(itemId: String) { favorites.add(itemId) }
    override suspend fun removeFavorite(itemId: String) { favorites.remove(itemId) }
    override suspend fun isFavorite(itemId: String): Boolean = itemId in favorites
}
