package com.workshop.kmp.android.ui

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.workshop.kmp.domain.Item
import com.workshop.kmp.presentation.ItemDetailState
import com.workshop.kmp.presentation.ItemDetailViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    itemId: String,
    onBack: () -> Unit,
    viewModel: ItemDetailViewModel = koinInject(),
) {
    LaunchedEffect(itemId) { viewModel.loadItem(itemId) }

    val state by viewModel.state.collectAsStateWithLifecycle()

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
            is ItemDetailState.Loading -> Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) { CircularProgressIndicator() }
            is ItemDetailState.Success -> ItemDetailContent(s.item, { viewModel.toggleFavorite(s.item.id) }, Modifier.padding(padding))
            is ItemDetailState.Error -> Column(Modifier.fillMaxSize().padding(padding).padding(24.dp), Alignment.CenterHorizontally, Arrangement.Center) {
                Text("Fehler", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
                Text(s.message)
            }
        }
    }
}

@Composable
private fun ItemDetailContent(item: Item, onFavoriteClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        // WORKSHOP-BUG (Anti-Pattern 7.2): Direkter Zugriff auf ein DTO-Feld aus der UI.

        Box(modifier = Modifier.fillMaxWidth().height(220.dp).padding(0.dp)) {
            // Bild-Placeholder (ohne Coil auf diesem Branch, kommt ggf. in 04-platform)
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surfaceVariant) {
                Box(contentAlignment = Alignment.Center) {
                    Text("🖼", style = MaterialTheme.typography.displayMedium)
                }
            }
        }

        Column(modifier = Modifier.padding(20.dp)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Text(item.title, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.weight(1f))
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        if (item.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        tint = if (item.isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Spacer(Modifier.height(4.dp))
            Text("€ %.2f".format(item.price), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(4.dp))
            // Anti-Pattern sichtbar: Backend-interner Feldname landet in der UI
            Text("SKU: ${item.sku}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Text(item.shortDescription, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(16.dp))
            Text(item.longDescription, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
