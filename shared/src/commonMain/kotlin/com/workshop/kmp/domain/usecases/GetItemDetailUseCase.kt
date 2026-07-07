package com.workshop.kmp.domain.usecases

import com.workshop.kmp.domain.Item
import com.workshop.kmp.domain.ItemRepository

class GetItemDetailUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke(itemId: String): Item? = repository.getItemDetail(itemId)
}
