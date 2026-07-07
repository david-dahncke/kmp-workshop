package com.workshop.kmp.domain

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
