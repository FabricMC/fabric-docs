---
title: Statuseffekte
description: Lerne, wie man benutzerdefinierte Statuseffekte erstellt.
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
authors-nogithub:
  - siglong
  - tao0lu
---

# Statuseffekte

Statuseffekte, auch Effekte genannt, sind ein Zustand, der eine Entität beeinflussen kann. Sie können positiver, negativer oder neutraler Natur sein. Das Basisspiel wendet diese Effekte auf verschiedene Weise an, zum Beispiel durch Nahrung, Tränke usw.

Der Befehl `/effect` kann verwendet werden, um Effekte auf eine Entität anzuwenden.

## Benutzerdefinierte Statuseffekte

In diesem Tutorial fügen wir einen neuen benutzerdefinierten Effekt namens _Tater_ hinzu, der dir einen Erfahrungspunkt pro Spieltick gibt.

### `StatusEffect` erweitern

Lasst uns eine benutzerdefinierte Effektklasse erstellen, indem wir `StatusEffect` erweitern, die die Basisklasse für alle Effekte ist.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### Deinen benutzerdefinierten Effekt registrieren

Ähnlich wie bei der Registrierung von Blöcken und Items verwenden wir `Registry.register`, um unseren benutzerdefinierten Effekt in der `STATUS_EFFECT`-Registry zu registrieren. Dies kann in unserem Initialisierer geschehen.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### Texturen

Das Statuseffekt-Symbol ist ein 18x18 PNG. Platziere dein eigenes Icon in:

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

<DownloadEntry type="Example Texture" visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png" />

### **Übersetzungen**

Wie jede andere Übersetzung kannst du einen Eintrag mit dem ID-Format `"effect.<mod-id>.<effect-identifier>": "Wert"` zur Sprachdatei hinzufügen.

```json
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### Testen

Benutze den Befehl `/effect give @p fabric-docs-reference:tater`, um dem Spieler unseren Tater-Effekt zu geben.
Verwende `/effect clear`
um den Effekt zu entfernen.

:::info
Um einen Trank zu erstellen, der diesen Effekt nutzt, lies bitte die Anleitung [Tränke](../items/potions).
:::
