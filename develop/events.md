---
title: Events
description: A guide for using events provided by the Fabric API.
authors:
  - dicedpixels
---

# Events

Fabric API provides a system that allows mods to react to actions or occurrences, also defined as *events* that occur in the game.

Events are hooks that satisfy common use cases and/or provide enhanced compatibility and performance between mods that hook into the same areas of the code. The use of events often substitutes the use of mixins.

Fabric API provides events for important areas in the Minecraft codebase that multiple modders may be interested in hooking into.

Events are represented by instances of `net.fabricmc.fabric.api.event.Event` which stores and calls *callbacks*. Often there is a single event instance for a callback, which is stored in a static field `EVENT` of the callback interface, but there are other patterns as well. For example, `ClientTickEvents` groups several related events together.

## Callbacks

Callbacks are a piece of code that is passed as an argument to an event. When the event is triggered by the game, the passed piece of code will be executed.

### Callback Interfaces

Each event has a corresponding callback interface, conventionally named `<EventName>Callback`. Callbacks are registered by calling `register()` method on an event instance, with an instance of the callback interface as the argument. 

All event callback interfaces provided by Fabric API can be found in the `net.fabricmc.fabric.api.event` package.

## Listening to Events

### A Simple Example

This example registers an `AttackBlockCallback` to damage the player when they hit blocks that don’t drop an item when hand-mined.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/event/Events.java)

### Adding Items to Existing Loot Tables

Sometimes you may want to add items to loot tables. For example, adding your drops to a vanilla block or entity.

The simplest solution, replacing the loot table file, can break other mods. What if they want to change them as well? We’ll take a look at how you can add items to loot tables without overriding the table.

We’ll be adding eggs to the coal ore loot table.

#### Listening to Loot Table Loading

Fabric API has an event that is fired when loot tables are loaded, `LootTableEvents.MODIFY`. You can register a callback for it in your mod initializer. Let’s also check that the current loot table is the coal ore loot table.

```java
LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
    // Let's only modify built-in loot tables and leave data pack loot tables untouched by checking the source.
    // We also check that the loot table ID is equal to the ID we want.
    if (source.isBuiltin() && COAL_ORE_LOOT_TABLE_ID.equals(id)) {
        // Our code will go here
    }
});
```

#### Adding Items to the Loot Table

In loot tables, items are stored in *loot pool entries*, and entries are stored in *loot pools*. To add an item, we’ll need to add a pool with an item entry to the loot table. We can make a pool with `LootPool#builder`, and add it to the loot table.

```java
LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
    if (source.isBuiltin() && COAL_ORE_LOOT_TABLE_ID.equals(id)) {
        LootPool.Builder poolBuilder = LootPool.builder();
        tableBuilder.pool(poolBuilder);
    }
});
```

Our pool doesn’t have any items yet, so we’ll make an item entry using `ItemEntry#builder` and add it to the pool.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/event/Events.java)

## Custom Events

Some areas of the game do not have hooks provided by the Fabric API, so you can either use a mixin or create your own event.

We'll look at creating an event that is triggered when sheep are sheared. The process of creating an event is:

* Creating the event callback interface
* Triggering the event from a mixin
* Creating a test implementation

### Creating the Event Callback Interface

The callback interface describes what must be implemented by event listeners that will listen to your event. The callback interface also describes how the event will be called from our mixin. It is conventional to place an `Event` object as a field in the callback interface, which will identify our actual event.

For our Event implementation, we will choose to use an array-backed event. The array will contain all event listeners that are listening to the event. 

Our implementation will call the event listeners in order until one of them does not return `ActionResult.PASS`. This means that a listener can say “*cancel this*”, “*approve this*” or “*don't care, leave it to the next event listener*” using its return value. 

Using `ActionResult` as a return value is a conventional way to make event handlers cooperate in this fashion.

You'll need to create an interface that has an `Event` instance and method for response implementation. A basic setup for our sheep shear callback is:

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Let's look at this more in-depth. When the invoker is called, we iterate over all listeners:

```java
(listeners) -> (player, sheep) -> {
    for (SheepShearCallback listener : listeners) {
```

We then call our method (in this case, `interact`) on the listener to get its response:

```java
ActionResult result = listener.interact(player, sheep);
```

If the listener says we have to cancel (`ActionResult.FAIL`) or fully finish (`ActionResult.SUCCESS`), the callback returns the result and finishes the loop. `ActionResult.PASS` moves on to the next listener, and in most cases should result in success if there are no more listeners registered:

```java
...
    if(result != ActionResult.PASS) {
        return result;
    }
}
 
return ActionResult.PASS;
```

We can Javadoc comments to the top of callback classes to document what each `ActionResult` does. In our case, it might be:

```java
/**
 * Callback for shearing a sheep.
 * Called before the sheep is sheared, items are dropped, and items are damaged.
 * Upon return:
 * - SUCCESS cancels further processing and continues with normal shearing behavior.
 * - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available.
 * - FAIL cancels further processing and does not shear the sheep.
 */
```

### Triggering the Event From a Mixin

We now have the basic event skeleton, but we need to trigger it. Because we want to have the event called when a player attempts to shear a sheep, we call the event `invoker` in `SheepEntity#interactMob` when `sheared()` is called (i.e. sheep can be sheared, and the player is holding shears):

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/mixin/event/SheepEntityMixin.java)

### Creating a Test Implementation

Now we need to test our event. You can register a listener in your initialization method (or another area, if you prefer) and add custom logic there. Here's an example that drops a diamond instead of wool at the sheep's feet:

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/event/Events.java)

If you enter into your game and shear a sheep, a diamond should drop instead of wool.