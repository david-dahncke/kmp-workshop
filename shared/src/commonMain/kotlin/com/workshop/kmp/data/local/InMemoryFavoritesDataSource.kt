package com.workshop.kmp.data.local

/**
 * In-Memory-Implementierung für Favoriten.
 * Wird in 02-repository / 03-state genutzt — Persistenz (SQLDelight) kommt in 04-platform.
 */
class InMemoryFavoritesDataSource : FavoritesDataSource {
    private val favorites = mutableSetOf<String>()

    override suspend fun getFavoriteIds(): Set<String> = favorites.toSet()
    override suspend fun addFavorite(itemId: String) { favorites.add(itemId) }
    override suspend fun removeFavorite(itemId: String) { favorites.remove(itemId) }
    override suspend fun isFavorite(itemId: String): Boolean = itemId in favorites
}
