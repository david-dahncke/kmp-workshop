# Übung 3 — Testing-Kata: commonTest schreiben

**Branch:** `04-platform` (Vorlage), `05-tests` (Lösung)
**Schwierigkeit:** ⭐⭐ Mittel
**Dauer:** ~45 Minuten

---

## Aufgabe

Schreibe Tests in `commonTest` — kein Mockito, kein JUnit 5, nur `kotlin.test`.

1. **ItemMapperTest** — normaler Fall, `price=null` Edge Case, `isFavorite`-Setzen
2. **GetItemsUseCaseTest** — mit `FakeItemRepository`
3. **ItemRepositoryTest** — mit `FakeFavoritesDataSource` + Ktor MockEngine

```kotlin
@Test
fun `mein test`() = runTest {
    // suspend-Funktionen direkt aufrufbar
}
```

*Lösung: Branch `05-tests`*
