package com.workshop.kmp.domain.usecases

import com.workshop.kmp.domain.Item
import com.workshop.kmp.domain.ItemRepository

/**
 * Use Case: Alle Artikel laden.
 * Kapselt den Repository-Aufruf — kann später Sortierung/Filterung enthalten,
 * ohne dass die UI davon weiß.
 */
class GetItemsUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke(): List<Item> = repository.getItems()
}
