package com.workshop.kmp.data.mapper

import com.workshop.kmp.data.dto.ItemDto
import com.workshop.kmp.domain.Item

/**
 * Mapper: Übersetzt zwischen DTO (API-Welt) und Domain-Modell (App-Welt).
 * Nur hier darf ein DTO-Feld angefasst werden — nirgendwo sonst in der App.
 */
fun ItemDto.toDomain(isFavorite: Boolean = false): Item = Item(
    id = id,
    title = title,
    shortDescription = shortDescription,
    // WORKSHOP-BUG: Force-Unwrap auf einem nullable Feld — crasht sobald ein Item price=null hat.
    // Fix: price ?: 0.0  oder  price ?: throw IllegalStateException("Preis fehlt für Item $id")
    price = price!!,
    imageUrl = imageUrl,
    longDescription = longDescription,
    isFavorite = isFavorite,
)

fun List<ItemDto>.toDomain(favoriteIds: Set<String> = emptySet()): List<Item> =
    map { it.toDomain(isFavorite = it.id in favoriteIds) }
