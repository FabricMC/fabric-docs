---
title: Status Effects
description: Learn how to add custom status effects.
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
authors-nogithub:
  - siglong
  - tao0lu

search: false
---

# Status Effects {#status-effects}

Status effects, also known as effects, are a condition that can affect an entity. They can be positive, negative or neutral in nature. The base game
applies these effects in various ways such as food, potions etc.

The `/effect` command can be used to apply effects on an entity.

## Custom Status Effects {#custom-status-effects}

In this tutorial we'll add a new custom effect called _Tater_ which gives you one experience point every game tick.

### Extend `StatusEffect` {#extend-statuseffect}

Let's create a custom effect class by extending `StatusEffect`, which is the base class for all effects.

@[code lang=java transcludeWith=:::1](@/reference/1.20.4/src/main/java/com/example/docs/effect/TaterEffect.java)

### Registering Your Custom Effect {#registering-your-custom-effect}

Similar to block and item registration, we use `Registry.register` to register our custom effect into the
`STATUS_EFFECT` registry. This can be done in our initializer.

@[code lang=java transcludeWith=:::1](@/reference/1.20.4/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### Texture {#texture}

The status effect icon is a 18x18 PNG which will appear in the player's inventory screen. Place your custom icon in:

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

<DownloadEntry type="Example Texture" visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png" />

### Translations {#translations}

Like any other translation, you can add an entry with ID format `"effect.<mod-id>.<effect-identifier>": "Value"` to the
language file.

```json
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### Testing {#testing}

Use the command `/effect give @p fabric-docs-reference:tater` to give the player our Tater effect.
Use `/effect clear @p fabric-docs-reference:tater` to remove the effect.

::: info
To create a potion that uses this effect, please see the [Potions](../items/potions) guide.
:::
