---
title: Tränke
description: Lerne, wie man neue Tränke für verschiedene Statuseffekte hinzufügt.
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst
  - JaaiDead
---

# Tränke {#potions}

Tränke sind Verbrauchsmaterialien, die Entitäten Statuseffekte geben können. Spieler können Tränke mit einem Braustand brauen oder durch andere Spielmechaniken erhalten.

## Benutzerdefinierte Tränke {#custom-potions}

Genauso wie Items und Blöcke, müssen auch Tränke registriert werden.

### Den Trank erstellen {#creating-the-potion}

Als Erstes wird die `Potion` Instanz in einer Variable deklariert. Dabei wird die Initialisierungsklasse benutzt, um die Variable zu belegen.

@[code lang=java transclude={19-27}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Es wird eine Instanz der `StatusEffectInstance` benutzt, die drei Parameter besitzt:

- `RegistryEntry<StatusEffect> type` - Einen Effekt. Hier verwenden wir unseren benutzerdefinierten Effekt. Alternativ kann man auch auf die vanilla Effekte durch die vanilla `StatusEffects` Klasse zugreifen.
- `int duration` - Länge des Effekts in Spielticks.
- `int amplifier` - Die Stärke des Effekts. Eile II hätte zum Beispiel einen amplifier-Wert von 1.

:::info
Um deinen eigenen Effekt zu erstellen, schau bitte in den Leitfaden [Effekte](../entities/effects).
:::

### Den Trank registrieren {#registering-the-potion}

In unserer Initialisierungsmethode benutzen wir das `FabricBrewingRecipeRegistryBuilder.BUILD` Event um unseren Trank mithilfe der `BrewingRecipeRegistry.registerPotionRecipe` Methode zu registrieren.

@[code lang=java transclude={29-42}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Die `registerPotionRecipe` Methode besitzt 3 Parameter:

- `RegistryEntry<Potion> input` - Der Registereintrag für den Starttrank. In den meisten Fällen ist das eine Wasserflasche oder ein "Seltsamer Trank".
- `Item item` - Der Gegenstand, der die Hauptzutat bildet.
- `RegistryEntry<Potion> output` -Der Registereintrag für den resultierenden Trank.

Sobald der Trank registriert wurde, kannst du den Tater-Trank mit einer Kartoffel brauen.

![Effekt im Inventar eines Spielers](/assets/develop/tater-potion.png)
