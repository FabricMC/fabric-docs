---
title: Benutzerdefinierte Verzauberungseffekte
description: Lerne, wie du deine eigenen Verzauberungseffekte erstellst.
authors:
  - CelDaemon
  - krizh-p
---

Ab der Version 1.21 verwenden benutzerdefinierte Verzauberungen in Minecraft einen "datengesteuerten" Ansatz. Das macht es einfacher, einfache Verzauberungen hinzuzufügen, wie z. B. die Erhöhung des Angriffsschadens, aber schwieriger, komplexe Verzauberungen zu erstellen. Dabei werden die Verzauberungen in _Effektkomponenten_ zerlegt.

Eine Effektkomponente enthält den Code, der die speziellen Effekte einer Verzauberung definiert. Minecraft unterstützt verschiedene Standardeffekte, wie z. B. Schaden, Rückschlag und Erfahrung.

::: tip

Überprüfe, ob die Standard-Minecraft-Effekte deinen Bedürfnissen entsprechen, indem du [die Seite der Verzauberungseffekt-Komponenten im Minecraft-Wiki](https://de.minecraft.wiki/w/Verzauberungsdefinition#Effektkomponenten) besuchst. Dieser Leitfaden setzt voraus, dass du weißt, wie man "einfache" datengesteuerte Verzauberungen konfiguriert und konzentriert sich auf die Erstellung von benutzerdefinierten Verzauberungseffekten, die nicht standardmäßig unterstützt werden.

:::

## Benutzerdefinierte Verzauberungseffekte {#custom-enchantment-effects}

Starte mit der Erstellung eines Ordners `enchantment` und erstelle innerhalb dieses Ordners einen Ordner `effect`. Darin erstellen wir den Record `LightningEnchantmentEffect`.

Als nächstes können wir einen Konstruktor erstellen und die Methoden des Interfaces `EnchantmentEntityEffect` überschreiben. Wir werden auch eine `CODEC`-Variable erstellen, um unseren Effekt zu kodieren und zu dekodieren; Du kannst [hier](../codecs) mehr über Codecs lesen.

Der Großteil unseres Codes wird in das Ereignis `apply()` einfließen, das aufgerufen wird, wenn die Kriterien für die Wirkung der Verzauberung erfüllt sind. Wir werden diesen `Effect` später so konfigurieren, dass er aufgerufen wird, wenn eine Entität getroffen wird, aber für den Moment wollen wir einfachen Code schreiben, um das Ziel mit einem Blitz zu treffen.

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/effect/LightningEnchantmentEffect.java)

Hier gibt die Variable `amount` einen Wert an, der auf die Stufe der Verzauberung abgestimmt ist. Auf diese Weise können wir die Wirksamkeit der Verzauberung je nach Stufe verändern. Im obigen Code verwenden wir die Stufe der Verzauberung, um zu bestimmen, wie viele Blitze erzeugt werden.

## Den Verzauberungseffekt registrieren {#registering-the-enchantment-effect}

Wie jede andere Komponente deines Mods müssen wir auch diesen `EnchantmentEffect` zur Minecraft-Registry hinzufügen. Füge hierzu eine Klasse `ModEnchantmentEffects` (oder wie immer du sie nennen willst) und eine Hilfsmethode zur Registrierung der Verzauberung hinzu. Stelle sicher, dass die Methode `registerModEnchantmentEffects()` in deiner Hauptklasse aufrufen wird, die die Methode `onInitialize()` enthält.

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantmentEffects.java)

## Die Verzauberung erstellen {#creating-the-enchantment}

Jetzt haben wir einen Verzauberungseffekt! Der letzte Schritt besteht darin, eine Verzauberung zu erstellen, die unseren benutzerdefinierten Effekt anwendet. Dies können wir mit dem datengetriebenen Verzauberungssystem erreichen, indem wir einfach eine JSON-Datei zu den Ressourcen unseres Mods hinzufügen.

Erstelle die JSON-Datei in dem Ordner `data/example-mod/enchantments`. Der Name dieser Datei entspricht der ID der Verzauberung: `thundering.json` wird zu `example-mod:thundering`.

::: info

Weitere Informationen zum Dateiformat findest du unter [Minecraft Wiki - Enchantment definition](https://minecraft.wiki/w/Enchantment_definition).

Um schnell benutzerdefinierte Verzauberungen zu generieren, kannst du den [Misode Generator](https://misode.github.io/enchantment/) verwenden.

:::

In diesem Beispiel verwenden wir die folgende Verzauberungsdefinition, um die Verzauberung `thundering` mit unserem benutzerdefinierten `lightning_effect` hinzuzufügen:

@[code](@/reference/latest/src/main/generated/data/example-mod/enchantment/thundering.json)

Du solltest auch Übersetzungen zu deiner `en_us.json` Datei hinzufügen, um deiner Verzauberung einen lesbaren Namen zu geben:

```json
"enchantment.example-mod.thundering": "Thundering",
```

Du solltest jetzt einen funktionierenden, benutzerdefinierten Verzauberungseffekt haben! Teste es, indem du eine Waffe mit der Verzauberung verzauberst und einen Mob triffst. Ein Beispiel wird im folgenden Video gezeigt:

<VideoPlayer src="/assets/develop/enchantment-effects/thunder.webm">Die Verzauberung Thundering verwenden</VideoPlayer>
