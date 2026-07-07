# KMP Mobile Workshop

Ein vollständiges Kotlin-Multiplatform-Übungsprojekt für einen zweitägigen internen Schulungsworkshop.

> **Hinweis:** Dieses Projekt ist bewusst klein und fiktiv — keine echten Kundendaten, keine echte Backend-Anbindung.

---

## Voraussetzungen

| Tool | Mindestversion | Empfohlen |
|------|---------------|-----------|
| JDK | 17 | 17 (LTS) |
| Android Studio | Hedgehog (2023.1.1) | Ladybug (2024.2.1) oder neuer |
| Kotlin Plugin | 2.0.x | mitgeliefert in Android Studio |
| Gradle | 8.9 | automatisch via Wrapper |
| Xcode | 15.x | 16.x (nur für iOS, nur Trainer) |

**Wichtig:** Das Kotlin Multiplatform Plugin muss in Android Studio aktiviert sein:
`Settings → Plugins → "Kotlin Multiplatform" suchen → installieren`

---

## Schnellstart

### 1. Repository klonen

```bash
git clone <repo-url>
cd kmp-mobile-workshop
```

### 2. Gradle Sync

Öffne das Projekt in **Android Studio**: `File → Open → kmp-mobile-workshop/ auswählen`

Der erste Sync dauert **3–10 Minuten** (Abhängigkeiten werden heruntergeladen).

### 3. Android App starten

1. Emulator starten: `Device Manager → Create Device → Pixel 8 → API 34`
2. Run-Konfiguration `androidApp` auswählen → ▶

---

## iOS-App

> **Die iOS-App wird ausschließlich vom Trainer live demonstriert.**

---

## Branch-Struktur / Lösungsstände

Jeder Branch ist ein eigenständiger, inkrementeller Schritt:

| Branch / Tag | Inhalt |
|-------------|--------|
| `00-start` | Leeres Grundgerüst — Gradle-Setup, App startet fehlerfrei |
| `01-model` | + Domain-Modelle, DTOs, Mapper |
| `02-repository` | + Repository + Ktor MockEngine (**Bug eingebaut!**) |
| `03-state` | + Use Cases + ScreenState + vollständige UI |
| `04-platform` | + `expect/actual` + Logger + Koin + SQLDelight (**2. Bug!**) |
| `05-tests` | + Alle commonTests grün, Bugs gefixt — **Referenzlösung** |
| `06-favorit-uebung` | Vorlage Übung 2 (Favoriten unvollständig) |

```bash
# Zu einem bestimmten Stand wechseln:
git checkout 02-repository
# Oder über Tag:
git checkout tags/02-repository
```

---

## Übungen

| # | Datei | Branch | Dauer |
|---|-------|--------|-------|
| 1 | `docs/exercises/exercise-01-bug-safari.md` | `02-repository` | 45 min |
| 2 | `docs/exercises/exercise-02-favoriten.md` | `06-favorit-uebung` | 60 min |
| 3 | `docs/exercises/exercise-03-testing-kata.md` | `04-platform` | 45 min |

---

## Known Issues

| Problem | Lösung |
|---------|--------|
| Erster Gradle Sync sehr langsam | Normal, Geduld. VPN kann es verlangsamen. |
| KMP Plugin fehlt | Android Studio → Settings → Plugins → installieren |
| `JAVA_HOME` nicht gesetzt | JDK 17 installieren. `gradle.properties` enthält bereits den Standard-Pfad für macOS (Temurin 17). |
| Compile-Fehler nach Branch-Wechsel | `File → Invalidate Caches → Restart` |
| Gradle Cache-Probleme | `./gradlew clean`, dann Sync |

---

## Merksatz

> "Wenn ein Backend-Feldname direkt in der UI sichtbar wird, fehlt wahrscheinlich eine Schicht."

## Tests ausführen

Die `commonTest`-Tests laufen als Android-Unit-Tests. Dafür muss das Android-SDK bekannt sein.

**Einmalig — `local.properties` anlegen** (wird nicht ins Git eingecheckt):

```bash
echo "sdk.dir=$HOME/Library/Android/sdk" > local.properties
```

**Tests starten:**

```bash
./gradlew :shared:testDebugUnitTest
```

> Hinweis: Das Projekt hat kein JVM-Target. `./gradlew :shared:jvmTest` existiert nicht.
> Alternativ: In Android Studio → `shared` → Rechtsklick auf `commonTest` → `Run Tests`
