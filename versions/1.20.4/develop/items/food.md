---
title: Food Items
description: Learn how to add a FoodComponent to an item to make it edible, and configure it.
authors:
  - IMB11

search: false
---

# Food Items {#food-items}

Food is a core aspect of survival Minecraft, so when creating edible items you have to consider the food's usage with other edible items.

Unless you're making a mod with overpowered items, you should consider:

- How much hunger your edible item adds or removes.
- What potion effect(s) does it grant?
- Is it early-game or endgame accessible?

## Adding the Food Component {#adding-the-food-component}

To add a food component to an item, we can pass it to the `FabricItemSettings` instance:

```java
new FabricItemSettings().food(new FoodComponent.Builder().build())
```

Right now, this just makes the item edible and nothing more.

The `FoodComponent.Builder` class has many methods that allow you to modify what happens when a player eats your item:

| Method               | Description                                                                                                                                                           |
| -------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `hunger`             | Sets the amount of hunger points your item will replenish.                                                                                                            |
| `saturationModifier` | Sets the amount of saturation points your item will add.                                                                                                              |
| `meat`               | Declares your item as meat. Carnivorous entities, such as wolves, will be able to eat it.                                                                             |
| `alwaysEdible`       | Allows your item to be eaten regardless of hunger level.                                                                                                              |
| `snack`              | Declares your item as a snack.                                                                                                                                        |
| `statusEffect`       | Adds a status effect when you eat your item. Usually a status effect instance and chance is passed to this method, where chance is a decimal percentage (`1f = 100%`) |

When you've modified the builder to your liking, you can call the `build()` method to get the `FoodComponent`

Using the example created in the [Creating Your First Item](./first-item) page, I'll be using the following options for the builder:

@[code transcludeWith=:::5](@/reference/1.20.4/src/main/java/com/example/docs/item/ModItems.java)

This makes the item:

- Always edible, it can be eaten regardless of hunger level.
- A "snack".
- Always give Poison II for 6 seconds when eaten.

<VideoPlayer src="/assets/develop/items/food_0.webm" title="Eating the Suspicious Substance" />
