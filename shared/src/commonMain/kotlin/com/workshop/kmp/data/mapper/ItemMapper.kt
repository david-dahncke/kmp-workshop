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
    // Bug 7.1 gefixt: price!! → sicherer Fallback. App crasht nicht mehr bei price: null.
    price = price ?: 0.0,
    imageUrl = imageUrl,
    longDescription = longDescription,
    // Bug 7.2 gefixt: DTO-Feldname bleibt im Mapper — Domain-Feld heißt neutral "sku".
    sku = dtoInternalSku,
    isFavorite = isFavorite,
)

fun List<ItemDto>.toDomain(favoriteIds: Set<String> = emptySet()): List<Item> =
    map { it.toDomain(isFavorite = it.id in favoriteIds) }
