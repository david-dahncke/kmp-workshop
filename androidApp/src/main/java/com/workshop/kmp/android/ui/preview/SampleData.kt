package com.workshop.kmp.android.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.workshop.kmp.domain.Item

/**
 * Statische Beispieldaten ausschließlich für @Preview-Composables.
 * Nie in produktivem Code verwenden.
 */
internal object SampleData {
    val items = listOf(
        Item(
            id = "item-001",
            title = "Kotlin Multiplatform Handbook",
            shortDescription = "Die ultimative Referenz für KMP-Entwicklung",
            price = 29.99,
            imageUrl = "",
            longDescription = "Dieses umfassende Handbuch führt dich Schritt für Schritt " +
                "durch alle Aspekte der Kotlin-Multiplatform-Entwicklung — von der " +
                "Projektstruktur bis hin zu plattformspezifischen APIs.",
            sku = "SKU-KH-001",
            isFavorite = false,
        ),
        Item(
            id = "item-002",
            title = "KMP Guide",
            shortDescription = "Cross-Platform-Entwicklung leicht gemacht",
            price = 19.99,
            imageUrl = "",
            longDescription = "Praxisnaher Leitfaden mit vielen Beispielen.",
            sku = "SKU-KMP-002",
            isFavorite = true,
        ),
        Item(
            id = "item-003",
            title = "Clean Architecture",
            shortDescription = "Sauberer Code auf allen Plattformen",
            price = 0.0,
            imageUrl = "",
            longDescription = "Preis derzeit nicht verfügbar — Artikel trotzdem ladbar dank Fallback.",
            sku = "SKU-CA-003",
            isFavorite = false,
        ),
    )

    val singleItem get() = items[0]
    val favoriteItem get() = items[1]
    val freeItem get() = items[2]
}

// ── PreviewParameterProvider ──────────────────────────────────────────────────

/**
 * Liefert verschiedene Item-Zustände für parametrisierte Previews.
 * Wird mit @PreviewParameter(ItemProvider::class) in einer Preview verwendet.
 */
class ItemProvider : PreviewParameterProvider<Item> {
    override val values = sequenceOf(
        SampleData.singleItem,
        SampleData.favoriteItem,
        SampleData.freeItem,
    )
}
