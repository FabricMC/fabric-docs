---
title: Tränke
description: Erstelle neue Tränke für verschiedenste Status-Effekte.
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst
---

# Tränke

Tränke sind Verbrauchsmaterialien, die Wesen (Entities) Status-Effekte geben können. Spieler können Tränke erlangen, indem sie diese mit einem Braustand brauen oder sie durch andere Spielmechaniken erhalten.

## Benutzerdefinierte Tränke

Das Hinzufügen eines Trankes ist vergleichbar mit dem Hinzufügen eines Items. Erstelle eine Instanz des Trankes und registriere es mit `BrewingRecipeRegistry.registerPotionRecipe`.

:::info
Falls die Fabric API vorhanden ist, kann auf `BrewingRecipeRegistry.registerPotionRecipe` durch einen "Access Widener" zugegriffen werden.
:::

### Erstellen eines Trankes

Als Erstes wird die `Potion` Instanz in einer Variable deklariert. Dabei wird die Initialisierungsklasse benutzt, um die Variable zu belegen.

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Es wird eine Instanz der `StatusEffectInstance` benutzt, die drei Parameter besitzt:

- `StatusEffect type` - Ein Effekttyp. Hier wird der benutzerdefinierter Effekttyp benutzt. Ansonsten können auch schon existierende Typen, mit `net.minecraft.entity.effect.StatusEffects`, verwendet werden.
- `int duration` - Länge des Effekts in Spiel-Ticks.
- `int amplifier` - Die Stärke des Effekts. Beispielsweise entspricht Eile II dem `amplifier`-Wert 1.

:::info
Das Erstellen eines neuen Effekts kann im Beitrag über [Status-Effekte](../entities/effects.md) nachgelesen werden.
:::

### Registrieren des Tranks

In unsere Klasse, die für das Initialisieren zuständig ist, wird dann die `BrewingRecipeRegistry.registerPotionRecipe` Methode aufgerufen.



Die `registerPotionRecipe` Methode besitzt 3 Parameter:

- `Potion input` - Der Starttrank. In den meisten Fällen ist das eine Wasserflasche oder ein "Seltsamer Trank".
- `Item item` - Der Gegenstand, der die Hauptzutat bildet.
- `Potion output` - Der fertiggestellte Trank.

Wenn die Fabric API benutzt wird, wird der Mixin Invoker nicht gebraucht und die `BrewingRecipeRegistry.registerPotionRecipe` Methode kann direkt aufgerufen werden.

Das vollständige Beispiel:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Sobald der Trank registriert wurde, kann der Tater Trank mit Kartoffeln gebraut werden.



::: info
**Registering Potions Using an `Ingredient`**

Mit der Hilfe der Fabric API kann bei dem Erstellen des Rezepts `Ingredient` statt `Item`, mithilfe der `
net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry` Methode, benutzt werden.
:::

### Registrieren des Tranks ohne der Fabric API

Ohne der Fabric API ist der Zugriff auf die `BrewingRecipeRegistry.registerPotionRecipe` Methode privat. Um dennoch Zugriff auf die Methode zu gewehren, muss der folgende Mixin Invoker oder ein "Access Widener" benutzt werden.

