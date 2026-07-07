package com.workshop.kmp.presentation

import com.workshop.kmp.domain.Item

sealed class ItemDetailState {
    data object Loading : ItemDetailState()
    data class Success(val item: Item) : ItemDetailState()
    data class Error(val message: String) : ItemDetailState()
}
