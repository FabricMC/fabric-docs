---
title: Statuseffekte
description: Lerne, wie man benutzerdefinierte Statuseffekte erstellt.
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
  - Manchick0
authors-nogithub:
  - siglong
  - tao0lu
---

# Statuseffekte {#status-effects}

Statuseffekte, auch Effekte genannt, sind ein Zustand, der eine Entität beeinflussen kann. Sie können positiver, negativer oder neutraler Natur sein. Das Basisspiel wendet diese Effekte auf verschiedene Weise an, zum Beispiel durch Nahrung, Tränke usw.

Der Befehl `/effect` kann verwendet werden, um Effekte auf eine Entität anzuwenden.

## Benutzerdefinierte Statuseffekte {#custom-status-effects}

In diesem Tutorial fügen wir einen neuen benutzerdefinierten Effekt namens _Tater_ hinzu, der dir einen Erfahrungspunkt pro Spieltick gibt.

### `StatusEffect` erweitern {#extend-statuseffect}

Lasst uns eine benutzerdefinierte Effektklasse erstellen, indem wir `StatusEffect` erweitern, die die Basisklasse für alle Effekte ist.

@[code lang=java transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/effect/TaterEffect.java)

### Deinen benutzerdefinierten Effekt registrieren {#registering-your-custom-effect}

Ähnlich wie bei der Registrierung von Blöcken und Items verwenden wir `Registry.register`, um unseren benutzerdefinierten Effekt in der `STATUS_EFFECT`-Registry zu registrieren. Dies kann in unserem Initialisierer geschehen.

@[code lang=java transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### Texturen {#texture}

Das Statuseffekt-Symbol ist ein 18x18 PNG. Platziere dein eigenes Icon in:

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

<DownloadEntry visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png">Beispiel Textur</DownloadEntry>

### Übersetzungen {#translations}

Wie jede andere Übersetzung kannst du einen Eintrag mit dem ID-Format `"effect.<mod-id>.<effect-identifier>": "Wert"` zur Sprachdatei hinzufügen.

```json
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### Einen Effekt anwenden {#applying-the-effect}

Es lohnt sich, einen Blick darauf zu werfen, wie man normalerweise einen Effekt auf eine Entität anwendet.

::: tip
For a quick test, it might be a better idea to use the previously mentioned `/effect` command:

```mcfunction
effect give @p fabric-docs-reference:tater
```

:::

Um einen Effekt intern anzuwenden, sollte man die Methode `LivingEntity#addStatusEffect` verwenden, die eine
eine `StatusEffectInstance` entgegennimmt und einen boolean zurückgibt, der angibt, ob der Effekt erfolgreich angewendet wurde.

@[code lang=java transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/ReferenceMethods.java)

| Argument    | Typ                           | Beschreibung                                                                                                                                                                                                                                                                                                                                                  |
| ----------- | ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `effect`    | `RegistryEntry<StatusEffect>` | Ein Registrierungseintrag, der den Effekt repräsentiert.                                                                                                                                                                                                                                                                                      |
| `duration`  | `int`                         | Die Dauer des Effekts **in Ticks**, **nicht** in Sekunden                                                                                                                                                                                                                                                                                                     |
| `amplifier` | `int`                         | Der Verstärker auf das Level des Effekts. Es entspricht nicht dem **Level** des Effekts, sondern wird zusätzlich zu diesem hinzugefügt. Folglich, `amplifier` von `4` => Level von `5`                                                                                                                                        |
| `ambient`   | v                             | Dies ist ein schwieriger. Es gibt im Grunde an, dass der Effekt durch die Umgebung (z. B. ein **Leuchtfeuer**) hinzugefügt wurde und keine direkte Ursache hat. Wenn `true`, wird das Icon des Effekts im HUD mit einer türkiesen Überlagerung erscheinen. |
| `particles` | v                             | Ob Partikel angezeigt werden sollen.                                                                                                                                                                                                                                                                                                          |
| `icon`      | v                             | Ob das Icon des Effekts im HUD angezeigt werden soll. Der Effekt wird im Inventar unabhängig von dieser Flag angezeigt.                                                                                                                                                                                                       |

:::info
Um einen Trank zu erstellen, der diesen Effekt nutzt, lies bitte die Anleitung [Tränke](../items/potions).
:::
