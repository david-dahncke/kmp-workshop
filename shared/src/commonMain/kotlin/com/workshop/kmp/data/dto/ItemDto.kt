package com.workshop.kmp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO: Repräsentiert exakt das JSON-Format der API.
 * Darf NIEMALS direkt in der UI verwendet werden — immer über den Mapper.
 *
 * price ist nullable, weil das Backend für manche Artikel keinen Preis liefert.
 */
@Serializable
data class ItemDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("short_description") val shortDescription: String,
    @SerialName("price") val price: Double?,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("long_description") val longDescription: String,
    @SerialName("dto_internal_sku") val dtoInternalSku: String,
)

@Serializable
data class ItemListResponseDto(
    @SerialName("items") val items: List<ItemDto>,
)
