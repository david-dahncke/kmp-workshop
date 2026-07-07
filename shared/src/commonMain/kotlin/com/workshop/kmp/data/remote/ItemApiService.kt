package com.workshop.kmp.data.remote

import com.workshop.kmp.data.dto.ItemDto
import com.workshop.kmp.data.dto.ItemListResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val BASE_URL = "https://api.workshop.fake"

class ItemApiService(private val httpClient: HttpClient) {

    suspend fun fetchItems(): List<ItemDto> {
        val response: ItemListResponseDto = httpClient.get("$BASE_URL/items").body()
        return response.items
    }

    suspend fun fetchItemDetail(itemId: String): ItemDto {
        return httpClient.get("$BASE_URL/items/$itemId").body()
    }
}
