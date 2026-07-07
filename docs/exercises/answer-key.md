# Answer Key — Nur für Trainer!

> **NICHT an Teilnehmer weitergeben.**

---

## Bug 7.1 — Force-Unwrap auf nullable price

**Symptom:** App crasht beim Laden der Liste mit `NullPointerException`.

**Ursache:** `ItemMapper.kt` → `price!!` — item-003 in MockItemData hat `price: null`.

**Fundort:**
```
shared/src/commonMain/kotlin/com/workshop/kmp/data/mapper/ItemMapper.kt
grep -r "WORKSHOP-BUG" shared/src/commonMain/
```

**Fix-Optionen:**
```kotlin
// Option A — Fallback:
price = price ?: 0.0,

// Option B — Explizite Exception (empfohlen für Workshop):
price = price ?: throw IllegalStateException("Kein Preis für Item '$id'"),
```

---

## Bug 7.2 — Anti-Pattern: DTO-Feldname in der UI

**Symptom:** Kein Crash — funktioniert, ist aber falsch.

**Ursache:** `val dto_internal_sku = "..."` im `ItemDetailScreen.kt` — der Feldname `dto_internal_sku` verrät, dass eine Architekturschicht übersprungen wurde.

**Fundort:**
```
androidApp/.../ui/ItemDetailScreen.kt
iosApp/.../ItemDetailView.swift
Suche: dto_internal_sku
```

**Fix:** `sku: String` ins Domain-Modell, im Mapper befüllen, in UI `item.sku` verwenden.

**Merksatz:** "Wenn ein Backend-Feldname direkt in der UI sichtbar wird, fehlt wahrscheinlich eine Schicht."

---

## Übung 2 — Favoriten-Lösung

```kotlin
// ToggleFavoriteUseCase.kt
class ToggleFavoriteUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke(itemId: String) = repository.toggleFavorite(itemId)
}

// ItemListViewModel.kt
fun toggleFavorite(itemId: String) {
    viewModelScope.launch {
        toggleFavoriteUseCase(itemId)
        loadItems()
    }
}
```

---

## Branch-Übersicht

| Branch | Stand | Bugs |
|--------|-------|------|
| `00-start` | Leeres Grundgerüst | — |
| `01-model` | + Domain, DTOs, Mapper | — |
| `02-repository` | + Ktor, Repository | Bug 7.1 |
| `03-state` | + Use Cases, ScreenState, UI | Bug 7.1 |
| `04-platform` | + expect/actual, Logger, Koin, SQLDelight | Bug 7.1 + 7.2 |
| `05-tests` | + Tests grün, Bugs gefixt | — |
| `06-favorit-uebung` | Favoriten unvollständig | — |
