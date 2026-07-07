package com.workshop.kmp

import com.workshop.kmp.data.remote.ItemApiService
import com.workshop.kmp.data.remote.createHttpClient
import com.workshop.kmp.data.remote.createMockEngine
import com.workshop.kmp.data.repository.ItemRepositoryImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ItemRepositoryTest {

    private val favoritesDataSource = FakeFavoritesDataSource()
    private val repository = ItemRepositoryImpl(
        apiService = ItemApiService(createHttpClient(createMockEngine())),
        favoritesDataSource = favoritesDataSource,
    )

    @Test
    fun `getItems throws for item with null price (Mapper-Verhalten)`() = runTest {
        // item-003 hat price=null → Mapper wirft IllegalStateException → landet als Error State
        val result = kotlin.runCatching { repository.getItems() }
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
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
