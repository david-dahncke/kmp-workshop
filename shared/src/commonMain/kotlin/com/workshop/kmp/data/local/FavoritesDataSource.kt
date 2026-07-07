package com.workshop.kmp.data.local

interface FavoritesDataSource {
    suspend fun getFavoriteIds(): Set<String>
    suspend fun addFavorite(itemId: String)
    suspend fun removeFavorite(itemId: String)
    suspend fun isFavorite(itemId: String): Boolean
}
