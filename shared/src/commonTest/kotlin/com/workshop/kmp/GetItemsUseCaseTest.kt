package com.workshop.kmp

import com.workshop.kmp.domain.usecases.GetItemsUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class GetItemsUseCaseTest {

    @Test
    fun `invoke returns items from repository`() = runTest {
        val useCase = GetItemsUseCase(FakeItemRepository())
        val result = useCase()
        assertEquals(3, result.size)
        assertEquals("item-001", result.first().id)
    }

    @Test
    fun `invoke propagates repository exception`() = runTest {
        val useCase = GetItemsUseCase(FakeItemRepository(shouldThrow = true))
        assertFailsWith<RuntimeException> { useCase() }
    }

    @Test
    fun `invoke returns items with correct favorite state`() = runTest {
        val repo = FakeItemRepository()
        repo.toggleFavorite("item-002")
        val result = GetItemsUseCase(repo)()
        assertTrue(result.first { it.id == "item-002" }.isFavorite)
        assertTrue(result.none { it.id != "item-002" && it.isFavorite })
    }
}
