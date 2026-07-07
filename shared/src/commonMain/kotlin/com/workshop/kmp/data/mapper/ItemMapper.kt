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
    // Bug aus Übung 1 gefixt: explizite Exception statt NPE, landet sauber im Error State.
    price = price ?: throw IllegalStateException("Kein Preis für Item '$id' — Backend-Daten prüfen"),
    imageUrl = imageUrl,
    longDescription = longDescription,
    isFavorite = isFavorite,
)

fun List<ItemDto>.toDomain(favoriteIds: Set<String> = emptySet()): List<Item> =
    map { it.toDomain(isFavorite = it.id in favoriteIds) }
