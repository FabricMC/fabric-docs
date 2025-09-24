---
title: Status Effects
description: Learn how to add custom status effects.
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

search: false
---

Status effects, also known as effects, are a condition that can affect an entity. They can be positive, negative or neutral in nature. The base game
applies these effects in various ways such as food, potions etc.

The `/effect` command can be used to apply effects on an entity.

## Custom Status Effects {#custom-status-effects}

In this tutorial we'll add a new custom effect called _Tater_ which gives you one experience point every game tick.

### Extend `StatusEffect` {#extend-statuseffect}

Let's create a custom effect class by extending `StatusEffect`, which is the base class for all effects.

@[code lang=java transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/effect/TaterEffect.java)

### Registering Your Custom Effect {#registering-your-custom-effect}

Similar to block and item registration, we use `Registry.register` to register our custom effect into the
`STATUS_EFFECT` registry. This can be done in our initializer.

@[code lang=java transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/effect/ExampleModEffects.java)

### Texture {#texture}

The status effect icon is a 18x18 PNG which will appear in the player's inventory screen. Place your custom icon in:

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

<DownloadEntry visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png">Example Texture</DownloadEntry>

### Translations {#translations}

Like any other translation, you can add an entry with ID format `"effect.mod-id.<effect-identifier>": "Value"` to the
language file.

```json
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### Applying The Effect {#applying-the-effect}

It's worth taking a look at how you'd typically apply an effect to an entity.

::: tip
For a quick test, it might be a better idea to use the previously mentioned `/effect` command:

```mcfunction
effect give @p fabric-docs-reference:tater
```

:::

To apply an effect internally, you'd want to use the `LivingEntity#addStatusEffect` method, which takes in
a `StatusEffectInstance`, and returns a boolean, specifying whether the effect was successfully applied.

@[code lang=java transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/ReferenceMethods.java)

| Argument    | Type                          | Description                                                                                                                                                                                                                   |
|-------------|-------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `effect`    | `RegistryEntry<StatusEffect>` | A registry entry that represents the effect.                                                                                                                                                                                  |
| `duration`  | `int`                         | The duration of the effect **in ticks**; **not** seconds                                                                                                                                                                      |
| `amplifier` | `int`                         | The amplifier to the level of the effect. It doesn't correspond to the **level** of the effect, but is rather added on top. Hence, `amplifier` of `4` => level of `5`                                                         |
| `ambient`   | `boolean`                     | This is a tricky one. It basically specifies that the effect was added by the environment (e.g. a **Beacon**) and doesn't have a direct cause. If `true`, the icon of the effect in the HUD will appear with an aqua overlay. |
| `particles` | `boolean`                     | Whether to show particles.                                                                                                                                                                                                    |
| `icon`      | `boolean`                     | Whether to display an icon of the effect in the HUD. The effect will be displayed in the inventory regardless of this flag.                                                                                                   |

::: info
To create a potion that uses this effect, please see the [Potions](../items/potions) guide.
:::
