---
title: Tränke
description: Lerne, wie man neue Tränke für verschiedene Statuseffekte hinzufügt.
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst

search: false
---

# Tränke

Tränke sind Verbrauchsmaterialien, die Entitäten Statuseffekte geben können. Spieler können Tränke erlangen, indem sie diese mit einem Braustand brauen oder sie durch andere Spielmechaniken erhalten.

## Benutzerdefinierte Tränke

Das Hinzufügen eines Trankes ist vergleichbar mit dem Hinzufügen eines Items. Erstelle eine Instanz des Trankes und registriere ihn mit `BrewingRecipeRegistry.registerPotionRecipe`.

:::info
Falls die Fabric API vorhanden ist, kann auf `BrewingRecipeRegistry.registerPotionRecipe` durch einen "Access Widener" zugegriffen werden.
:::

### Erstellen eines Trankes

Als Erstes wird die `Potion` Instanz in einer Variable deklariert. Dabei wird die Initialisierungsklasse benutzt, um die Variable zu belegen.

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Es wird eine Instanz der `StatusEffectInstance` benutzt, die drei Parameter besitzt:

- `StatusEffect type` - Ein Effekttyp. Hier verwenden wir unseren benutzerdefinierten Effekt. Alternativ kannst du auf Vanilla-Effekte über `net.minecraft.entity.effect.StatusEffects` zugreifen.
- `int duration` - Länge des Effekts in Spielticks.
- `int amplifier` - Die Stärke des Effekts. Zum Beispiel hätte Eile II einen Verstärker-Wert von 1.

:::info
Um deinen eigenen Effekt zu erstellen, schau bitte in den Leitfaden [Effekte](../entities/effects).
:::

### Registrieren des Tranks

In unsere Klasse, die für das Initialisieren zuständig ist, wird dann die `BrewingRecipeRegistry.registerPotionRecipe` Methode aufgerufen.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Die `registerPotionRecipe` Methode besitzt 3 Parameter:

- `Potion input` - Der Starttrank. In den meisten Fällen ist das eine Wasserflasche oder ein "Seltsamer Trank".
- `Item item` - Der Gegenstand, der die Hauptzutat bildet.
- `Potion output` - Der fertiggestellte Trank.

Wenn die Fabric API benutzt wird, wird der Mixin Invoker nicht benötigt und die `BrewingRecipeRegistry.registerPotionRecipe` Methode kann direkt aufgerufen werden.

Das vollständige Beispiel:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Sobald der Trank registriert wurde, kannst du den Tater-Trank mit einer Kartoffel brauen.

![Effekt im Inventar eines Spielers](/assets/develop/tater-potion.png)

:::info Registrieren von Tränken mit Hilfe eines `Ingredient`

Mit der Hilfe der Fabric API kann beim Erstellen des Rezepts `Ingredient` statt `Item`, mithilfe der `
net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry` Methode, benutzt werden.
:::

### Registrieren des Tranks ohne der Fabric API

Ohne de Fabric API ist die Methode `BrewingRecipeRegistry.registerPotionRecipe` privat. Um auf diese Methode zuzugreifen, verwende den folgenden Mixin Invoker oder verwende einen "Access Widener".

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/mixin/potion/BrewingRecipeRegistryInvoker.java)
