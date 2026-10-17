---
title: Vanilla Rezepte erweitern
description: Lerne, wie du benutzerdefinierte Rezepte für bereits vorhandene Arbeitsplätze erstellen kannst.
authors:
  - ekulxam
  - lynndova
resources:
  https://docs.neoforged.net/docs/resources/server/recipes/builtin/: Eingebaute Rezepttypen - NeoForge-Dokumentation
---

Wenn du versuchst, ein Rezept zu einem vorhandenen Arbeitsplatz hinzuzufügen, wie beispielsweise einen Schmiedetisch, eine Werkbank oder eine Steinsäge, musst du lediglich [eine Rezeptklasse erstellen](./custom-recipe-types#creating-the-recipe-class), [ihre Methoden implementieren](./custom-recipe-types#implementing-the-methods), [den Serialisierer registrieren](./custom-recipe-types#creating-a-recipe-serializer) und [die Rezept-JSONs erstellen](./custom-recipe-types#creating-a-recipe), da die Logik für Blöcke, Menüs und Oberflächen bereits (von Mojang) für dich fertiggestellt wurde. Lasst uns einmal einige Beispiele ansehen.

## Überblick {#overview}

Jeder Vanilla-Arbeitsplatz verfügt über einen eigenen `RecipeType`, der in dem Interface `RecipeType` definiert ist. Jeder Arbeitsplatz benötigt einen bestimmten Untertyp von `Recipe`, um zu funktionieren.

::: warning

Beachte bitte, dass deine Rezepte, außer du änderst das zugrunde liegende Menü, auf die Ein- und Ausgabe beschränkt sind, die das Menü bietet. Zum Beispiel hat ein Schmiedetisch drei Eingaben und eine Ausgabe (in Vanilla sind diese in der Regel ein `Optional<Ingredient> template`, `Ingredient base`, `Optional<Ingredient> addition` und `ItemStackTemplate result`). Allerdings hast du innerhalb der Klasse `Recipe` viele Freiheiten bei der Konfiguration der Eingaben, um die Ausgaben zu erzeugen.

:::

## Schmiedetisch {#smithing-table}

Lasst uns einen neuen Typ von Schmiederezept erstellen, bei dem auf die Basiseingabe Verzauberungen angewendet werden, um die Ausgabe herzustellen.

Der Schmiedetisch erwartet eine irgendeine Implementierung des Interface `SmithingRecipe`, die `RecipeTypes.SMITHING` zurückgibt. Wenn man ein neues `SmithingRecipe` erstellt, könnte man einfach eine neue Klasse anlegen und `SmithingRecipe` implementieren, aber ein weiterer gültiger Weg ist es, `SimpleSmithingRecipe`, eine Vanilla-Klasse, die `SmithingRecipe` bereits implementiert, zu erweitern.

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/extending/EnchantingSmithingRecipe.java#enchanting_smithing

Wow, schon wieder so viel Text. Dies scheint in den Rezeptdokumentationen ein häufiges Vorkommnis zu sein (haha). Lasst uns herausfinden, was da vor sich geht.

Die ersten Zeilen beinhalten unsere `Codec`s, `MapCodec`s und `StreamCodec`s zur Serialisierung und Synchronisierung der Rezeptdetails. Wir verwenden eine `Object2IntOpenHashMap` für die Verzauberungen, damit wir beliebige Verzauberungen einer Stufe zuordnen können.

Im Anschluss an den Abschnitt zur Serialisierung haben wir die bereits erwähnten `template`, `base` und `addition`, doch anstelle eines `ItemStackTemplate result` haben wir hier eine `Object2IntOpenHashMap enchantments`.

Die Methode `assemble` bildet den Kern des benutzerdefinierten Rezepts und bietet den `ItemStack` als Ergebnis, wenn das Rezept hergestellt wird. In diesem Fall verwenden wir eine Hilfsmethode aus `EnchantmentHelper`, um die Verzauberungen von unserer Karte anzuwenden.

Unsere `PlacementInfo` dient in erster Linie dazu, Rezepte über das Rezeptbuch zu platzieren, während unsere `RecipeDisplay` dabei hilft, die Rezepte im Rezeptbuch anzuzeigen.

:::details Eine Randbemerkung: Slot Anzeigen

Wenn du versuchen würdest, deine eigene Überschreibung von `display` zu erstellen, würdest du schnell feststellen, dass du aus deinem Ergebnis kein `SlotDisplay` erstellen könntest, da es sich um ein dynamisches Ergebnis handelt, das auf deiner `base` basiert, was ein `Ingredient` ist, aus dem du nicht ohne Weiteres `ItemStack`s erhalten kannst. Allerdings haben wir in unserer Rezeptklasse eine gültige Überschreibung von `display` bereitgestellt. Was ist los?

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/extending/EnchantingSmithingDemoSlotDisplay.java#slot_display

Wir haben eine benutzerdefinierte Implementierung von `SlotDisplay` erstellt. Diese spezielle Implementierung ermöglicht es, das Ergebnis mit den gewünschten Verzauberungen anzuzeigen.

In unserer `resolve`-Methode erstellen wir zunächst eine `RandomSource` und einen `BinaryOperator<ItemStack>` und übergeben beide anschließend an `SlotDisplay.applyDemoTransformation` (da es sich um eine statische, aber private Methode handelt, benötigen wir einen Mixin-Aufrufer).

<<< @/reference/26.2/src/main/java/com/example/docs/mixin/accessor/SlotDisplayAccessor.java#demo_invoker

`applyDemoTransformation` erlaubt Änderungen auf den im `SlotDisplay` angezeigten `ItemStack` anzuwenden. Es benötigt einen `BinaryOperator<ItemStack>`, damit man die Daten von `base` basierend auf `material` ändern kann. Dies ist beispielsweise bei Rüstungsbesatz-Rezepten nützlich, bei denen die Farbe des Besatzes je nach Material variiert. Allerdings wenden wir unsere Verzauberungen direkt auf den Basis-Stack an und ignorieren dabei das Material (das Rezept prüft einfach nur, ob das richtige Material vorhanden ist, bevor es die Zusammenstellung zulässt), sodass wir das Feld `material` in unserer Implementierung von `SlotDisplay` tatsächlich weglassen können (`SlotDisplay.Empty.INSTANCE` würde dann anstelle von `material` an `applyDemoTransformation` übergeben werden).

:::

Schließlich müssen wir unseren Rezept-Serialisierer und den Slot-Anzeigetyp registrieren.

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#enchanting_smithing_registration

::: info

Dieses Rezept ist weiterhin datengetrieben.

<<< @/reference/26.2/src/main/resources/data/example-mod/recipe/smithing_enchanting/netherite_sword_smithing_enchanting.json

:::

![Super-Netherit-Schwert](/assets/develop/recipes/smithing_enchanting.png)

## Werkbank {#crafting-table}

Eine ähnliche Situation tritt auf, wenn man ein neues Handwerksrezept erstellt. Der erwartete Typ ist das Interface `CraftingRecipe` und wenn `ShapedRecipe` und `ShapelessRecipe` nicht ausreichen sollten, empfehlen wir, stattdessen `CustomRecipe` zu erweitern. Wir empfehlen dir, die Untertypen des Rezept Interface deines Ziel-Arbeitsplatz durchzuschauen, um zu prüfen, ob du einen findest, der deinen Anforderungen entspricht.

Lasst uns als Beispiel ein benutzerdefiniertes Handwerksrezept erstellen, mit dem man Tränke in eine seltsame Suppe umwandeln kann.

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/extending/StewSpikingCraftingRecipe.java#stew_spiking

Wie immer müssen wir unseren Rezept-Serialisierer registrieren.

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#stew_spiking_registration

::: info

Dieses Rezept ist weiterhin datengetrieben.

<<< @/reference/26.2/src/main/resources/data/example-mod/recipe/stew_spiking/stew_spiking.json

Wir benötigen nur den Typ, damit Minecraft weiß, dass wir das Rezept laden möchten.

:::

_Pssst, sag es niemandem! >:)_
![Ich habe so viele schädliche Tränke in diese Suppe gegeben](/assets/develop/recipes/stew_spiking.png)

## Steinsäge {#stonecutter}

Rezepte für die Steinsäge sind im `RecipeManager`/`RecipeAccess` von anderen `Recipe`s getrennt, da die Steinsäge anhand seiner einzigen Eingabe alle gültigen Rezepte anzeigen und auswählen muss (Menüs mit Rezeptbüchern werden über `ClientRecipeBook` behandelt, wobei der Server dem Client die erforderlichen Rezepte bereitstellt). Eine einfache Erweiterung von `StonecutterRecipe` (im Gegensatz zu den anderen handelt es sich hierbei nicht um eine Interface!) und das Überschreiben der Methode `assemble` sollte für die meisten Anwendungsfälle funktionieren, abgesehen von der reinen Erstellung von JSON für ein Steinsägerezept.
