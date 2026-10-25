---
title: Tränke
description: Lerne, wie man benutzerdefinierte Tränke für verschiedene Mobeffekte hinzufügt.
authors:
  - cassiancc
  - dicedpixels
  - Drakonkinst
  - JaaiDead
  - PandoricaVi
---

Tränke sind Verbrauchsmaterialien, die Entitäten Statuseffekte geben können. Spieler können Tränke mit einem Braustand brauen oder durch andere Spielmechaniken erhalten.

## Benutzerdefinierte Tränke {#custom-potions}

Genauso wie Items und Blöcke, müssen auch Tränke registriert werden.

### Den Trank erstellen {#creating-the-potion}

Lasst uns mit der Deklaration eines Feldes, welches eine Instanz von `Potion` hält. Wir werden hierfür die `ModInitializer` implementierende Klasse nutzen. Beachte die Verwendung von `Registry.registerForHolder`, da die meisten Vanilla-Methoden, die Tränke verwenden, diese wie Mob-Effekte als Halter bevorzugen.

<<< @/reference/26.1.2/src/main/java/com/example/docs/potion/ExampleModPotions.java#register_potion

Es wird eine Instanz der `MobEffectInstance` benutzt, die drei Parameter besitzt:

- `Holder<MobEffect> effect` - Ein Effekt, repräsentiert als ein Halter. Hier verwenden wir unseren benutzerdefinierten Effekt. Alternativ kannst du durch die Vanilla Klasse `MobEffects` auf die Vanilla Effekte zugreifen.
- `int duration` - Länge des Effekts in Spielticks.
- `int amplifier` - Die Stärke des Effekts. Eile II hätte zum Beispiel einen amplifier-Wert von 1.

::: info

Um deinen eigenen Effekt zu erstellen, schau bitte in den Leitfaden [Effekte](../entities/effects).

:::

### Den Trank registrieren {#registering-the-potion}

In unserer Initialisierungsmethode benutzen wir das `FabricBrewingRecipeRegistryBuilder.BUILD` Event um unseren Trank mithilfe der `BrewingRecipeRegistry.registerPotionRecipe` Methode zu registrieren.

<<< @/reference/26.1.2/src/main/java/com/example/docs/potion/ExampleModPotions.java#register_recipes

Die `addMix` Methode besitzt 3 Parameter:

- `Holder<Potion> from` - Der Starttrank, repräsentiert durch einen Halter. In den meisten Fällen ist das eine Wasserflasche oder ein "Seltsamer Trank".
- `Item item` - Der Gegenstand, der die Hauptzutat bildet.
- `Holder<Potion> to` - Der Endtrank, repräsentiert durch einen Halter.

Sobald der Trank registriert wurde, kannst du den Tater-Trank mit einer Kartoffel brauen.

![Effekt im Inventar eines Spielers](/assets/develop/tater-potion.png)
