---
title: Food Items
description: Learn how to add a FoodComponent to an item to make it edible, and configure it.
authors:
  - IMB11
---

Food is a core aspect of survival Minecraft, so when creating edible items you have to consider the food's usage with other edible items.

Unless you're making a mod with overpowered items, you should consider:

- How much hunger your edible item adds or removes.
- What potion effect(s) does it grant?
- Is it early-game or endgame accessible?

## Adding the Food Component {#adding-the-food-component}

To add a food component to an item, we can pass it to the `Item.Settings` instance:

```java
new Item.Settings().food(new FoodComponent.Builder().build())
```

Right now, this just makes the item edible and nothing more.

The `FoodComponent.Builder` class has some methods that allow you to modify what happens when a player eats your item:

| Method               | Description                                                |
|----------------------|------------------------------------------------------------|
| `nutrition`          | Sets the amount of hunger points your item will replenish. |
| `saturationModifier` | Sets the amount of saturation points your item will add.   |
| `alwaysEdible`       | Allows your item to be eaten regardless of hunger level.   |

When you've modified the builder to your liking, you can call the `build()` method to get the `FoodComponent`.

If you want to add status effects to the player when they eat your food, you will need to use the `ConsumableComponent` alongside the `FoodComponent` class as seen in the following example:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Similar to the example in the [Creating Your First Item](./first-item) page, I'll be using the above component:

@[code transcludeWith=:::poisonous_apple](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

This makes the item:

- Always edible, it can be eaten regardless of hunger level.
- Always give Poison II for 6 seconds when eaten.

<VideoPlayer src="/assets/develop/items/food_0.webm">Eating the Poisonous Apple</VideoPlayer>
