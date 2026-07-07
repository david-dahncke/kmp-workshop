package com.workshop.kmp.presentation

import com.workshop.kmp.domain.Item

/**
 * ScreenState als sealed class — genau drei mögliche Zustände,
 * kein undefined State möglich.
 */
sealed class ItemListState {
    data object Loading : ItemListState()
    data class Success(val items: List<Item>) : ItemListState()
    data class Error(val message: String) : ItemListState()
}
