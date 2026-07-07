# Übung 1 — Bug-Safari: Die App crasht beim Laden

**Branch:** `02-repository`
**Schwierigkeit:** ⭐⭐ Mittel
**Dauer:** ~45 Minuten

---

## Aufgabe

Die App crasht beim Laden der Artikel-Liste. Verfolge die Kette:

**API-Response → DTO → Mapper → Domain → ViewModel → UI**

Finde den Fehler, behebe ihn idiomatisch (kein weiteres `!!`).

## Leitfragen

- Welches Item in den Mock-Daten ist anders? Was fehlt ihm?
- Welches DTO-Feld ist nullable? Wird das im Mapper berücksichtigt?
- Fallback `0.0`, ausblenden, oder Error State — was ist die richtige Entscheidung?

## Tipp

```bash
grep -r "WORKSHOP-BUG" shared/src/commonMain/
```

*Lösung: `answer-key.md` (nur für Trainer)*
