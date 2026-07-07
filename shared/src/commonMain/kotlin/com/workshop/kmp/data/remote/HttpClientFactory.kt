package com.workshop.kmp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Erstellt einen Ktor-HttpClient.
 * Standard ist der MockEngine (kein echtes Netzwerk nötig) — so bleibt der
 * gesamte Serialisierungs-Pfad testbar, ohne API-Abhängigkeit.
 */
fun createHttpClient(engine: HttpClientEngine = createMockEngine()): HttpClient {
    return HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }
}

fun createMockEngine(): MockEngine = MockEngine { request ->
    val url = request.url.toString()
    when {
        url.endsWith("/items") -> respond(
            content = MOCK_ITEMS_JSON,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
        )
        url.contains("/items/") -> {
            val itemId = url.substringAfterLast("/")
            val singleItemJson = extractSingleItemJson(itemId)
            if (singleItemJson != null) {
                respond(
                    content = singleItemJson,
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
                )
            } else {
                respond(
                    content = """{"error": "not found"}""",
                    status = HttpStatusCode.NotFound,
                    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
                )
            }
        }
        else -> respond(
            content = """{"error": "unknown endpoint"}""",
            status = HttpStatusCode.NotFound,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
        )
    }
}

private fun extractSingleItemJson(itemId: String): String? {
    val itemsBlock = MOCK_ITEMS_JSON
        .substringAfter("\"items\": [")
        .substringBefore("]")
    val items = itemsBlock.split("},\n    {")
    for (rawItem in items) {
        val cleaned = rawItem.trim().removePrefix("[").removeSuffix("]").trim()
            .let { if (it.startsWith("{")) it else "{$it" }
            .let { if (it.endsWith("}")) it else "$it}" }
        if (cleaned.contains("\"id\": \"$itemId\"")) return cleaned
    }
    return null
}
