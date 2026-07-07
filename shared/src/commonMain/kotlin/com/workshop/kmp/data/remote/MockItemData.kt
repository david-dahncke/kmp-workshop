package com.workshop.kmp.data.remote

/**
 * Eingebettete Mock-Daten als JSON-String.
 * Der Ktor-MockEngine liefert diesen Response — so läuft der gesamte
 * Deserialisierungs-Pfad durch, ohne echtes Netzwerk.
 *
 * Item "item-003" hat absichtlich price: null — das ist der Auslöser für Bug 7.1.
 */
internal const val MOCK_ITEMS_JSON = """
{
  "items": [
    {
      "id": "item-001",
      "title": "Kotlin Coroutines Handbook",
      "short_description": "Das komplette Nachschlagewerk für asynchrones Kotlin.",
      "price": 39.99,
      "image_url": "https://picsum.photos/seed/item001/400/300",
      "long_description": "Dieses Buch führt dich von den Grundlagen der Coroutines bis hin zu komplexen Flow-Pipelines. Mit zahlreichen Praxisbeispielen und Best Practices für den produktiven Einsatz.",
      "dto_internal_sku": "SKU-KCH-2024"
    },
    {
      "id": "item-002",
      "title": "KMP Starter Kit",
      "short_description": "Werkzeuge und Vorlagen für deinen ersten KMP-Start.",
      "price": 19.99,
      "image_url": "https://picsum.photos/seed/item002/400/300",
      "long_description": "Ein kuratiertes Set aus Projektvorlagen, Gradle-Konventionen und CI-Skripten, das dir den Einstieg in Kotlin Multiplatform erheblich erleichtert.",
      "dto_internal_sku": "SKU-KSK-2024"
    },
    {
      "id": "item-003",
      "title": "Mystery Item",
      "short_description": "Preis noch nicht festgelegt.",
      "price": null,
      "image_url": "https://picsum.photos/seed/item003/400/300",
      "long_description": "Dieser Artikel ist noch in der Preisfindung. Er wird demnächst verfügbar sein.",
      "dto_internal_sku": "SKU-MYST-TBD"
    },
    {
      "id": "item-004",
      "title": "Compose Deep Dive",
      "short_description": "Jetpack Compose von innen verstehen.",
      "price": 29.99,
      "image_url": "https://picsum.photos/seed/item004/400/300",
      "long_description": "Eine tiefe Reise in die Internals von Jetpack Compose — Recomposition, Snapshots, State-Management und Performance-Optimierung aus erster Hand.",
      "dto_internal_sku": "SKU-CDD-2024"
    },
    {
      "id": "item-005",
      "title": "Clean Architecture Guide",
      "short_description": "Saubere Softwarearchitektur für mobile Apps.",
      "price": 24.99,
      "image_url": "https://picsum.photos/seed/item005/400/300",
      "long_description": "Von SOLID-Prinzipien über Schichtenarchitektur bis zu konkreten Implementierungsmustern in Kotlin — der pragmatische Guide für Architekturentscheidungen.",
      "dto_internal_sku": "SKU-CAG-2024"
    }
  ]
}
"""
