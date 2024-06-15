---
title: Custom Item Interactions
description: Learn how to create an item that uses built-in vanilla events.
order: 5
---

# Custom Item Interactions

Basic items can only go so far - eventually you will need an item that interacts with the world when it is used.

There are some key classes you must understand before taking a look at the vanilla item events.

## TypedActionResult

For items, the most common `TypedActionResult` you'll see is for `ItemStacks` - this class tells the game what to replace the item stack (or not to replace) after the event has occured.

If nothing has occured in the event, you should use the `TypedActionResult#pass(stack)` method where `stack` is the current item stack.

You can get the current item stack by getting the stack in the player's hand. Usually events that require a `TypedActionResult` pass the hand to the event method.

```java
TypedActionResult.pass(user.getStackInHand(hand))
```

If you pass the current stack - nothing will change, regardless of if you declare the event as failed, passed/ignored or successful.

If you want to delete the current stack, you should pass an empty one. The same can be said about decrementing, you fetch the current stack and decrement it by the amount you want:

```java
ItemStack heldStack = user.getStackInHand(hand);
heldStack.decrement(1);
TypedActionResult.success(heldStack);
```

## ActionResult

Similarly, an `ActionResult` tells the game the status of the event, whether it was passed/ignored, failed or successful.

## Overridable Events

Luckily, the Item class has many methods that can be overriden to add extra functionality to your items.

::: info
A great example of these events being used can be found in the [Playing SoundEvents](../sounds/using-sounds.md) page, which uses the `useOnBlock` event to play a sound when the player right clicks a block.
:::

| Method          | Information                                             |
| --------------- | ------------------------------------------------------- |
| `postHit`       | Ran when the player hits an entity.                     |
| `postMine`      | Ran when the player mines a block.                      |
| `inventoryTick` | Ran every tick whilst the item is in an inventory.      |
| `onCraft`       | Ran when the item is crafted.                           |
| `useOnBlock`    | Ran when the player right clicks a block with the item. |
| `use`           | Ran when the player right clicks the item.              |

## The `use()` Event

Let's say you want to make an item that summons a lightning bolt infront of the player - you would need to create a custom class.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

The `use` event is probably the most useful out of them all - you can use this event to spawn our lightning bolt, you should spawn it 10 blocks in front of the players facing direction.

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

As usual, you should register your item, add a model and texture.

As you can see, the lightning bolt should spawn 10 blocks infront of you - the player.

![Lightning bolt striking the ground after player uses item.](/assets/develop/items/interactive_0.webp)
