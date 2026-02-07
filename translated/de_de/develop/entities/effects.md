---
title: Mobeffekte
description: Lerne, wie man benutzerdefinierte Mobeffekte erstellt.
authors:
  - dicedpixels
  - Friendly-Banana
  - Manchick0
  - SattesKrokodil
  - TheFireBlast
  - YanisBft
authors-nogithub:
  - siglong
  - tao0lu
---

Mobeffekte, auch bekannt als Statuseffekte oder einfach Effekte, sind eine Bedingung, die eine Entität beeinflussen kann. Sie können positiver, negativer oder neutraler Natur sein. Das Basisspiel wendet diese Effekte auf verschiedene Weise an, zum Beispiel durch Nahrung, Tränke usw.

Der Befehl `/effect` kann verwendet werden, um Effekte auf eine Entität anzuwenden.

## Benutzerdefinierte Mobeffekte {#custom-mob-effects}

In diesem Tutorial fügen wir einen neuen benutzerdefinierten Effekt namens _Tater_ hinzu, der dir einen Erfahrungspunkt pro Spieltick gibt.

### `stepOn` erweitern {#extend-mobeffect}

Lasst uns eine benutzerdefinierte Effektklasse erstellen, indem wir `MobEffect` erweitern, die die Basisklasse für alle Effekte ist.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### Deinen benutzerdefinierten Effekt registrieren {#registering-your-custom-effect}

Ähnlich wie bei der Registrierung von Blöcken und Items verwenden wir `Registry.register`, um unseren benutzerdefinierten Effekt in der `MOB_EFFECT`-Registry zu registrieren. Dies kann in unserem Initialisierer geschehen.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/ExampleModEffects.java)

### Texturen {#texture}

Das Mobeffekt-Symbol ist ein 18x18 PNG, welches in der Oberfläche des Inventars des Spielers erscheinen wird. Platziere dein eigenes Icon in:

```text:no-line-numbers
resources/assets/example-mod/textures/mob_effect/tater.png
```

<DownloadEntry visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png">Beispiel Textur</DownloadEntry>

### Übersetzungen {#translations}

Wie jede andere Übersetzung kannst du einen Eintrag mit dem ID-Format `"effect.example-mod.effect-identifier": "Wert"` zur Sprachdatei hinzufügen.

```json
{
  "effect.example-mod.tater": "Tater"
}
```

### Einen Effekt anwenden {#applying-the-effect}

Es lohnt sich, einen Blick darauf zu werfen, wie man normalerweise einen Effekt auf eine Entität anwendet.

::: tip

Für einen schnellen Test ist es möglicherweise besser, den zuvor erwähnten Befehl `/effect` zu verwenden:

```mcfunction
effect give @p example-mod:tater
```

:::

Um einen Effekt intern anzuwenden, sollte man die Methode `LivingEntity#addMobEffect` verwenden, die eine
eine `MobEffectInstance` entgegennimmt und einen boolean zurückgibt, der angibt, ob der Effekt erfolgreich angewendet wurde.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/ReferenceMethods.java)

| Argument    | Typ                 | Beschreibung                                                                                                                                                                                                                                                                                                                                                  |
| ----------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `effect`    | `Holder<MobEffect>` | Ein Halter, der den Effekt darstellt.                                                                                                                                                                                                                                                                                                         |
| `duration`  | `int`               | Die Dauer des Effekts **in Ticks**, **nicht** in Sekunden                                                                                                                                                                                                                                                                                                     |
| `amplifier` | `int`               | Der Verstärker auf das Level des Effekts. Es entspricht nicht dem **Level** des Effekts, sondern wird zusätzlich zu diesem hinzugefügt. Folglich, `amplifier` von `4` => Level von `5`                                                                                                                                        |
| `ambient`   | v                   | Dies ist ein schwieriger. Es gibt im Grunde an, dass der Effekt durch die Umgebung (z. B. ein **Leuchtfeuer**) hinzugefügt wurde und keine direkte Ursache hat. Wenn `true`, wird das Icon des Effekts im HUD mit einer türkiesen Überlagerung erscheinen. |
| `particles` | v                   | Ob Partikel angezeigt werden sollen.                                                                                                                                                                                                                                                                                                          |
| `icon`      | v                   | Ob das Icon des Effekts im HUD angezeigt werden soll. Der Effekt wird im Inventar unabhängig von dieser Flag angezeigt.                                                                                                                                                                                                       |

::: info

Um einen Trank zu erstellen, der diesen Effekt nutzt, lies bitte die Anleitung [Tränke](../items/potions).

:::

<!---->
