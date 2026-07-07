package com.workshop.kmp.presentation

import com.workshop.kmp.domain.usecases.GetItemsUseCase
import com.workshop.kmp.domain.usecases.ToggleFavoriteUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Plattformunabhängige Presenter-Logik.
 * Android nutzt diesen via lifecycle-aware ViewModel-Wrapper,
 * iOS bindet den StateFlow über einen Coroutines-iOS-Helper ein.
 */
class ItemListViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow<ItemListState>(ItemListState.Loading)
    val state: StateFlow<ItemListState> = _state.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _state.value = ItemListState.Loading
            try {
                val items = getItemsUseCase()
                _state.value = ItemListState.Success(items)
            } catch (e: Exception) {
                _state.value = ItemListState.Error(e.message ?: "Unbekannter Fehler")
            }
        }
    }

    fun toggleFavorite(itemId: String) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(itemId)
                loadItems()
            } catch (e: Exception) {
                _state.value = ItemListState.Error(e.message ?: "Favorit konnte nicht gespeichert werden")
            }
        }
    }
}
