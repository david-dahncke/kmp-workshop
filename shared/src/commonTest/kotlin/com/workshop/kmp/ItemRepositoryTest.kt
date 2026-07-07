package com.workshop.kmp

import com.workshop.kmp.data.remote.ItemApiService
import com.workshop.kmp.data.remote.createHttpClient
import com.workshop.kmp.data.remote.createMockEngine
import com.workshop.kmp.data.repository.ItemRepositoryImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ItemRepositoryTest {

    private val favoritesDataSource = FakeFavoritesDataSource()
    private val repository = ItemRepositoryImpl(
        apiService = ItemApiService(createHttpClient(createMockEngine())),
        favoritesDataSource = favoritesDataSource,
    )

    @Test
    fun `getItems loads all items including item with null price`() = runTest {
        // Nach dem Fix: item-003 hat price=null → Mapper mappt auf 0.0 → alle Items laden
        val items = repository.getItems()
        assertTrue(items.isNotEmpty())
        val itemWithNullPrice = items.firstOrNull { it.id == "item-003" }
        assertNotNull(itemWithNullPrice, "item-003 muss trotz null-price geladen werden")
        assertEquals(0.0, itemWithNullPrice.price)
    }

    @Test
    fun `getFavoriteIds is empty initially`() = runTest {
        assertTrue(repository.getFavoriteIds().isEmpty())
    }

    @Test
    fun `toggleFavorite adds item`() = runTest {
        repository.toggleFavorite("item-001")
        assertTrue("item-001" in repository.getFavoriteIds())
    }

    @Test
    fun `toggleFavorite removes item when already favorite`() = runTest {
        repository.toggleFavorite("item-001")
        repository.toggleFavorite("item-001")
        assertFalse("item-001" in repository.getFavoriteIds())
    }
}
