package com.workshop.kmp

import com.workshop.kmp.data.dto.ItemDto
import com.workshop.kmp.data.mapper.toDomain
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ItemMapperTest {

    private val validDto = ItemDto(
        id = "item-001",
        title = "Test Titel",
        shortDescription = "Kurzbeschreibung",
        price = 19.99,
        imageUrl = "https://example.com/image.jpg",
        longDescription = "Lange Beschreibung",
        dtoInternalSku = "SKU-TEST",
    )

    @Test
    fun `toDomain maps all fields correctly`() {
        val domain = validDto.toDomain()
        assertEquals("item-001", domain.id)
        assertEquals("Test Titel", domain.title)
        assertEquals("Kurzbeschreibung", domain.shortDescription)
        assertEquals(19.99, domain.price)
        assertEquals("https://example.com/image.jpg", domain.imageUrl)
        assertEquals("Lange Beschreibung", domain.longDescription)
        assertEquals("SKU-TEST", domain.sku)
        assertFalse(domain.isFavorite)
    }

    @Test
    fun `toDomain sets isFavorite true when passed`() {
        assertTrue(validDto.toDomain(isFavorite = true).isFavorite)
    }

    @Test
    fun `toDomain does not expose dtoInternalSku in domain model`() {
        val domain = validDto.toDomain()
        // data class toString() listet alle Primary-Constructor-Felder — kein Reflection nötig
        assertFalse(
            domain.toString().contains("dtoInternalSku"),
            "Domain-Modell darf kein DTO-internes Feld enthalten"
        )
    }

    @Test
    fun `toDomain with null price falls back to 0,0`() {
        // Nach dem Fix: kein Crash mehr — null price wird zu 0.0 (Artikel trotzdem ladbar)
        val domain = validDto.copy(price = null).toDomain()
        assertEquals(0.0, domain.price)
    }

    @Test
    fun `list toDomain maps favoriteIds correctly`() {
        val dtos = listOf(validDto, validDto.copy(id = "item-002"), validDto.copy(id = "item-003"))
        val domains = dtos.toDomain(setOf("item-001", "item-003"))
        assertTrue(domains[0].isFavorite)
        assertFalse(domains[1].isFavorite)
        assertTrue(domains[2].isFavorite)
    }
}
