package com.workshop.kmp.domain

/**
 * Domain-Modell für einen Artikel.
 * Plattformunabhängig — darf KEIN DTO-Feld direkt enthalten.
 */
data class Item(
    val id: String,
    val title: String,
    val shortDescription: String,
    val price: Double,
    val imageUrl: String,
    val longDescription: String,
    val sku: String,
    val isFavorite: Boolean = false,
)
