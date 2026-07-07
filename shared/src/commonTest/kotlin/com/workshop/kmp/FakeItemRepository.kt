package com.workshop.kmp

import com.workshop.kmp.domain.Item
import com.workshop.kmp.domain.ItemRepository

class FakeItemRepository(
    private val items: List<Item> = defaultItems(),
    private val shouldThrow: Boolean = false,
) : ItemRepository {

    private val favorites = mutableSetOf<String>()

    override suspend fun getItems(): List<Item> {
        if (shouldThrow) throw RuntimeException("Fake-Netzwerkfehler")
        return items.map { it.copy(isFavorite = it.id in favorites) }
    }

    override suspend fun getItemDetail(itemId: String): Item? {
        if (shouldThrow) throw RuntimeException("Fake-Netzwerkfehler")
        return items.firstOrNull { it.id == itemId }?.copy(isFavorite = itemId in favorites)
    }

    override suspend fun toggleFavorite(itemId: String) {
        if (itemId in favorites) favorites.remove(itemId) else favorites.add(itemId)
    }

    override suspend fun getFavoriteIds(): Set<String> = favorites.toSet()

    companion object {
        fun defaultItems() = listOf(
            Item("item-001", "Kotlin Handbook", "Kurz", 29.99, "https://example.com/1.jpg", "Lang"),
            Item("item-002", "KMP Guide", "Kurz", 19.99, "https://example.com/2.jpg", "Lang"),
            Item("item-003", "Clean Architecture", "Kurz", 24.99, "https://example.com/3.jpg", "Lang"),
        )
    }
}
