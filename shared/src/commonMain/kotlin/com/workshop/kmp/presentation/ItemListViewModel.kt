package com.workshop.kmp.presentation

import com.workshop.kmp.domain.usecases.GetItemsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    // TODO (Übung 2): ToggleFavoriteUseCase-Parameter ergänzen
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow<ItemListState>(ItemListState.Loading)
    val state: StateFlow<ItemListState> = _state.asStateFlow()

    init { loadItems() }

    fun loadItems() {
        viewModelScope.launch {
            _state.value = ItemListState.Loading
            try {
                _state.value = ItemListState.Success(getItemsUseCase())
            } catch (e: Exception) {
                _state.value = ItemListState.Error(e.message ?: "Unbekannter Fehler")
            }
        }
    }

    fun toggleFavorite(itemId: String) {
        // TODO (Übung 2): Use Case aufrufen und danach loadItems() triggern
    }
}
