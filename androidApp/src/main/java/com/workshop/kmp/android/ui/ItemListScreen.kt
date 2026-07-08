package com.workshop.kmp.android.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.workshop.kmp.android.ui.preview.ItemProvider
import com.workshop.kmp.android.ui.preview.SampleData
import com.workshop.kmp.android.ui.theme.WorkshopTheme
import com.workshop.kmp.domain.Item
import com.workshop.kmp.presentation.ItemListState
import com.workshop.kmp.presentation.ItemListViewModel
import org.koin.compose.koinInject

// ── Stateful Screen (hält ViewModel, nicht direkt previewbar) ─────────────────

@Composable
fun ItemListScreen(
    onItemClick: (String) -> Unit,
    viewModel: ItemListViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ItemListContent(
        state = state,
        onItemClick = onItemClick,
        onFavoriteClick = { viewModel.toggleFavorite(it) },
        onRetry = { viewModel.loadItems() },
    )
}

// ── Stateless Content (previewbar, kein ViewModel-Zugriff) ────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ItemListContent(
    state: ItemListState,
    onItemClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    onRetry: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KMP Workshop — Artikel") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        }
    ) { padding ->
        when (val s = state) {
            is ItemListState.Loading -> Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center,
            ) { CircularProgressIndicator() }

            is ItemListState.Success -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(s.items, key = { it.id }) { item ->
                    ItemCard(
                        item = item,
                        onItemClick = { onItemClick(item.id) },
                        onFavoriteClick = { onFavoriteClick(item.id) },
                    )
                }
            }

            is ItemListState.Error -> Column(
                modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("Fehler beim Laden", style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
                Text(s.message, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
                Button(onClick = onRetry) { Text("Erneut versuchen") }
            }
        }
    }
}

@Composable
internal fun ItemCard(item: Item, onItemClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onItemClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, style = MaterialTheme.typography.titleMedium,
                    maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(4.dp))
                Text(item.shortDescription, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(8.dp))
                Text("€ %.2f".format(item.price), style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (item.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (item.isFavorite) "Favorit entfernen" else "Als Favorit markieren",
                    tint = if (item.isFavorite) MaterialTheme.colorScheme.error
                           else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview(name = "Liste — Laden", showBackground = true)
@Composable
private fun ItemListLoadingPreview() {
    WorkshopTheme {
        ItemListContent(
            state = ItemListState.Loading,
            onItemClick = {},
            onFavoriteClick = {},
            onRetry = {},
        )
    }
}

@Preview(name = "Liste — Erfolg", showBackground = true)
@Composable
private fun ItemListSuccessPreview() {
    WorkshopTheme {
        ItemListContent(
            state = ItemListState.Success(SampleData.items),
            onItemClick = {},
            onFavoriteClick = {},
            onRetry = {},
        )
    }
}

@Preview(name = "Liste — Fehler", showBackground = true)
@Composable
private fun ItemListErrorPreview() {
    WorkshopTheme {
        ItemListContent(
            state = ItemListState.Error("Netzwerkverbindung unterbrochen"),
            onItemClick = {},
            onFavoriteClick = {},
            onRetry = {},
        )
    }
}

@Preview(name = "Liste — Dark Mode", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ItemListDarkPreview() {
    WorkshopTheme {
        ItemListContent(
            state = ItemListState.Success(SampleData.items),
            onItemClick = {},
            onFavoriteClick = {},
            onRetry = {},
        )
    }
}

// PreviewParameter: eine Preview rendert automatisch alle Varianten der ItemProvider
@Preview(name = "ItemCard — alle Varianten", showBackground = true, widthDp = 380)
@Composable
private fun ItemCardPreview(
    @PreviewParameter(ItemProvider::class) item: Item,
) {
    WorkshopTheme {
        ItemCard(item = item, onItemClick = {}, onFavoriteClick = {})
    }
}
