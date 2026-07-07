package com.workshop.kmp.presentation

import com.workshop.kmp.domain.usecases.GetItemDetailUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    private val getItemDetailUseCase: GetItemDetailUseCase,
    // TODO (Übung 2): ToggleFavoriteUseCase-Parameter ergänzen
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow<ItemDetailState>(ItemDetailState.Loading)
    val state: StateFlow<ItemDetailState> = _state.asStateFlow()

    fun loadItem(itemId: String) {
        viewModelScope.launch {
            _state.value = ItemDetailState.Loading
            try {
                val item = getItemDetailUseCase(itemId)
                _state.value = if (item != null) ItemDetailState.Success(item)
                               else ItemDetailState.Error("Artikel nicht gefunden")
            } catch (e: Exception) {
                _state.value = ItemDetailState.Error(e.message ?: "Unbekannter Fehler")
            }
        }
    }

    fun toggleFavorite(itemId: String) {
        // TODO (Übung 2): Use Case aufrufen und danach loadItem(itemId) triggern
    }
}
