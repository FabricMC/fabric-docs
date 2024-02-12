---
title: Status Effects
description: Learn how to add custom status effects.
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
---

<!-- Couldn't find GitHub usernames for: siglong, tao0lu  -->

# Status Effects

Status effects, also known as effects, are a condition that can affect an entity. They can be positive, negative or neutral in nature. The base game
applies these effects in various ways such as food, potions etc.

The `/effect` command can be used to apply effects on an entity.

## Custom Status Effects

In this tutorial we'll add a new custom effect called _Tater_ which gives you one experience point every game tick.

### Extend `StatusEffect`

Let's create a custom effect class by extending `StatusEffect`, which is the base class for all effects.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### Registering Your Custom Effect

Similar to block and item registration, we use `Registry.register` to register our custom effect into the
`STATUS_EFFECT` registry. This can be done in our initializer.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### Translations and Textures

You can assign a name to your status effect and provide a texture icon that will appear in the player inventory screen.

**Texture**

The status effect icon is a 18x18 PNG. Place your custom icon in:

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

![Effect in player inventory](/assets/develop/tater-effect.png)

**Translations**

Like any other translation, you can add an entry with ID format `"effect.<mod-id>.<effect-identifier>": "Value"` to the
language file.

::: code-group

```json[assets/fabric-docs-reference/lang/en_us.json]
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### Testing

Use the command `/effect give @p fabric-docs-reference:tater` to give the player our Tater effect. Use `/effect clear`
to remove the effect.

::: info
To create a potion that uses this effect, please see the [Potions](../items/potions.md) guide.
:::