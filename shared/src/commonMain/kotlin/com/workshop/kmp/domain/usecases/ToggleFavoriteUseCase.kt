package com.workshop.kmp.domain.usecases

import com.workshop.kmp.domain.ItemRepository

class ToggleFavoriteUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke(itemId: String) = repository.toggleFavorite(itemId)
}
