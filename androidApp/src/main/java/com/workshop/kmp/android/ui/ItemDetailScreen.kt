package com.workshop.kmp.android.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.workshop.kmp.android.ui.preview.SampleData
import com.workshop.kmp.android.ui.theme.WorkshopTheme
import com.workshop.kmp.domain.Item
import com.workshop.kmp.presentation.ItemDetailState
import com.workshop.kmp.presentation.ItemDetailViewModel
import org.koin.compose.koinInject

// ── Stateful Screen ────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    itemId: String,
    onBack: () -> Unit,
    viewModel: ItemDetailViewModel = koinInject(),
) {
    LaunchedEffect(itemId) { viewModel.loadItem(itemId) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    ItemDetailScaffold(
        state = state,
        onBack = onBack,
        onFavoriteClick = { id -> viewModel.toggleFavorite(id) },
    )
}

// ── Stateless Scaffold (previewbar) ───────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ItemDetailScaffold(
    state: ItemDetailState,
    onBack: () -> Unit,
    onFavoriteClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Artikel-Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        }
    ) { padding ->
        when (val s = state) {
            is ItemDetailState.Loading ->
                Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                    CircularProgressIndicator()
                }
            is ItemDetailState.Success ->
                ItemDetailContent(s.item, { onFavoriteClick(s.item.id) }, Modifier.padding(padding))
            is ItemDetailState.Error ->
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Fehler", style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(8.dp))
                    Text(s.message)
                }
        }
    }
}

@Composable
internal fun ItemDetailContent(item: Item, onFavoriteClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier.fillMaxWidth().height(220.dp)) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surfaceVariant) {
                Box(contentAlignment = Alignment.Center) {
                    Text("🖼", style = MaterialTheme.typography.displayMedium)
                }
            }
        }

        Column(modifier = Modifier.padding(20.dp)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Text(item.title, style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f))
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        if (item.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        tint = if (item.isFavorite) MaterialTheme.colorScheme.error
                               else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Spacer(Modifier.height(4.dp))
            Text("€ %.2f".format(item.price), style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(4.dp))
            Text("SKU: ${item.sku}", style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline)
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Text(item.shortDescription, style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(16.dp))
            Text(item.longDescription, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview(name = "Detail — Laden", showBackground = true)
@Composable
private fun ItemDetailLoadingPreview() {
    WorkshopTheme {
        ItemDetailScaffold(ItemDetailState.Loading, onBack = {}, onFavoriteClick = {})
    }
}

@Preview(name = "Detail — Erfolg", showBackground = true)
@Composable
private fun ItemDetailSuccessPreview() {
    WorkshopTheme {
        ItemDetailScaffold(
            state = ItemDetailState.Success(SampleData.singleItem),
            onBack = {},
            onFavoriteClick = {},
        )
    }
}

@Preview(name = "Detail — Favorit", showBackground = true)
@Composable
private fun ItemDetailFavoritePreview() {
    WorkshopTheme {
        ItemDetailScaffold(
            state = ItemDetailState.Success(SampleData.favoriteItem),
            onBack = {},
            onFavoriteClick = {},
        )
    }
}

@Preview(name = "Detail — Dark Mode", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ItemDetailDarkPreview() {
    WorkshopTheme {
        ItemDetailScaffold(
            state = ItemDetailState.Success(SampleData.singleItem),
            onBack = {},
            onFavoriteClick = {},
        )
    }
}

@Preview(name = "Detail — Fehler", showBackground = true)
@Composable
private fun ItemDetailErrorPreview() {
    WorkshopTheme {
        ItemDetailScaffold(
            state = ItemDetailState.Error("Artikel nicht gefunden"),
            onBack = {},
            onFavoriteClick = {},
        )
    }
}
