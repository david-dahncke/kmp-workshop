package com.workshop.kmp.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.workshop.kmp.domain.Item
import com.workshop.kmp.domain.createItemRepository

// TODO (03-state): Screen mit echtem ViewModel und StateFlow verbinden.
// Hier: Repository direkt im Composable nutzen (kein ViewModel, kein DI).
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(onItemClick: (String) -> Unit = {}) {
    val repository = remember { createItemRepository() }
    var items by remember { mutableStateOf<List<Item>>(emptyList()) }

    LaunchedEffect(Unit) {
        // Kein try-catch — wirft NullPointerException wenn price=null (Bug 7.1)
        items = repository.getItems()
    }

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
        if (items.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(items) { item ->
                    ItemCard(item = item, onClick = { onItemClick(item.id) })
                }
            }
        }
    }
}

@Composable
private fun ItemCard(item: Item, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(item.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(item.shortDescription, style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(8.dp))
            Text("€ %.2f".format(item.price), style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary)
        }
    }
}
