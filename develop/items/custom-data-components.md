---
title: Custom Data Components
description: Learn how to add custom data to your items using the new 1.21 component system.
authors:
  - Romejanic
---

# Custom Data Components {#custom-data-components}

As your items grow more complex, you may find yourself needing to store custom data associated with your item. The game allows you to store persistent data within an `ItemStack`, and as of 1.21 the way we do that is by using **Data Components**.

Data Components replace NBT data from previous versions with structured data types (i.e. components) which can be applied to an `ItemStack` to store persistent data about that stack. Data components are namespaced, meaning we can implement our own data components to store custom data about an `ItemStack` and access it later. A full list of the vanilla data components can be found in this [Minecraft snapshot changelog](https://www.minecraft.net/en-us/article/minecraft-snapshot-24w09a).

## Registering a Component {#registering-a-component}

As with anything else in your mod you will need to register your custom component using a `ComponentType`. This component type takes a generic argument containing the type of your component's value. We will be focusing on this in more detail further down.

Choose a sensible class to place this in (e.g. your `ModItems` class from [Creating Your First Item](./first-item.md)), and add the following lines.

```java
public static final ComponentType<?> MY_COMPONENT_TYPE = Registry.register(
    Registries.DATA_COMPONENT_TYPE,
    Identifier.of(FabricDocsReference.MOD_ID, "my_component"),
    ComponentType.<?>builder().codec(null).build()
);
```

There's a few things here worth noting. On the first and fourth lines you can see a `?`. This will be replaced with the type of your component's value. We'll fill this in soon.

Secondly, similar to registering a block or item you must provide an `Identifier` containing the intended ID of your component. This is namespaced with your mod's mod ID.

Lastly we have a `ComponentType.Builder` which creates the actual `ComponentType` instance which is being registered. This contains another cruical detail we will need to discuss which is your component's `Codec`. This is currently `null` but we will also fill it in soon.

## Basic Data Components {#basic-data-components}

Basic data components (e.g. `minecraft:damage`) consist of a single data value, such as an `int`, `float`, `boolean` or `String`.

As an example, let's create an Integer value which will track how many times the player has right-clicked while holding our item. Let's update our component registration to the following:

```java
public static final ComponentType<Integer> CLICK_COUNT_COMPONENT = Registry.register(
    Registries.DATA_COMPONENT_TYPE,
    Identifier.of(FabricDocsReference.MOD_ID, "click_count"),
    ComponentType.<Integer>builder().codec(Codec.INT).build()
);
```

You can see that we're now passing `<Integer>` as our generic type, indicating that this component will be stored as a single `int` value. For our codec, we are using the provided `Codec.INT` codec. We can get away with using basic codecs for simple components like this but more complex scenarios might require a custom codec (this will be covered briefly later on).

::: info
This example uses the `LightningStick` class created in the [Custom Item Interactions](./custom-item-interactions) page.
:::

If you start the game you should be able to enter a command like so:

![Give command showing custom component](/assets/develop/items/custom_component_0.png)
