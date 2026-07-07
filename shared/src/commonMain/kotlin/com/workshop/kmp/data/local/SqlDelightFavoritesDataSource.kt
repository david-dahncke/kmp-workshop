package com.workshop.kmp.data.local

import com.workshop.kmp.db.WorkshopDatabase

class SqlDelightFavoritesDataSource(private val database: WorkshopDatabase) : FavoritesDataSource {
    override suspend fun getFavoriteIds(): Set<String> =
        database.favoriteItemQueries.selectAll().executeAsList().toSet()
    override suspend fun addFavorite(itemId: String) =
        database.favoriteItemQueries.insert(itemId)
    override suspend fun removeFavorite(itemId: String) =
        database.favoriteItemQueries.delete(itemId)
    override suspend fun isFavorite(itemId: String): Boolean =
        database.favoriteItemQueries.isFavorite(itemId).executeAsOne()
}
