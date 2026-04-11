---
title: Events
description: A guide for using events provided by the Fabric API.
authors:
  - Daomephsta
  - dicedpixels
  - Draylar
  - JamiesWhiteShirt
  - Juuxel
  - liach
  - mkpoli
  - natanfudge
  - PhoenixVX
  - SolidBlock-cn
  - YanisBft
authors-nogithub:
  - stormyfabric
---

Fabric API provides a system that allows mods to react to actions or occurrences, also defined as _events_ that occur in the game.

Events are hooks that satisfy common use cases and/or provide enhanced compatibility and performance between mods that hook into the same areas of the code. The use of events often substitutes the use of mixins.

Fabric API provides events for important areas in the Minecraft codebase that multiple modders may be interested in hooking into.

Events are represented by instances of `net.fabricmc.fabric.api.event.Event` which stores and calls _callbacks_. Often there is a single event instance for a callback, which is stored in a static field `EVENT` of the callback interface, but there are other patterns as well. For example, `ClientTickEvents` groups several related events together.

## Callbacks {#callbacks}

Callbacks are a piece of code that is passed as an argument to an event. When the event is triggered by the game, the passed piece of code will be executed.

### Callback Interfaces {#callback-interfaces}

Each event has a corresponding callback interface. Callbacks are registered by calling `register()` method on an event instance, with an instance of the callback interface as the argument.

## Listening to Events {#listening-to-events}

This example registers an `AttackBlockCallback` to damage the player when they hit blocks that don't drop an item when hand-mined.

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#listening

### Adding Items to Existing Loot Tables {#adding-items-to-existing-loot-tables}

Sometimes you may want to add items to loot tables. For example, adding your drops to a vanilla block or entity.

The simplest solution, replacing the loot table file, can break other mods. What if they want to change them as well? We'll take a look at how you can add items to loot tables without overriding the table.

We'll be adding eggs to the coal ore loot table.

#### Listening to Loot Table Loading {#listening-to-loot-table-loading}

Fabric API has an event that is fired when loot tables are loaded, `LootTableEvents.MODIFY`. You can register a callback for it in your [mod's initializer](./getting-started/project-structure#entrypoints). Let's also check that the current loot table is the coal ore loot table:

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#callback

#### Adding Items to the Loot Table {#adding-items-to-the-loot-table}

To add an item, we'll need to add a pool with an item entry to the loot table.

We can make a pool with `LootPool#lootPool`, and add it to the loot table.

Our pool doesn't have any items yet, so we'll make an item entry using `LootItem#lootTableItem` and add it to the pool.

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot-table-item{5-7}

## Custom Events {#custom-events}

Some areas of the game do not have hooks provided by the Fabric API, so you can either use a mixin or create your own event.

We'll look at creating an event that is triggered when sheep are sheared. The process of creating an event is:

- Creating the event callback interface
- Triggering the event from a mixin
- Creating a test implementation

### Creating the Event Callback Interface {#creating-the-event-callback-interface}

The callback interface describes what must be implemented by event listeners that will listen to your event. The callback interface also describes how the event will be called from our mixin. It is conventional to place an `Event` object as a field in the callback interface, which will identify our actual event.

For our `Event` implementation, we will choose to use an array-backed event. The array will contain all event listeners that are listening to the event.

Our implementation will call the event listeners in order until one of them does not return `InteractionResult.PASS`. This means that a listener can say "_cancel this_", "_approve this_", or "_don't care, pass it on to the next event listener_" using its return value.

Using `InteractionResult` as a return value is a conventional way to make event handlers cooperate in this fashion.

You'll need to create an interface that has an `Event` instance and method for response implementation. A basic setup for our sheep shear callback is:

<<< @/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java#sheep-shear

Let's look at this more in-depth. When the invoker is called, we iterate over all listeners:

<<< @/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java#iteration

On each listener, we then call `interact` to get the listener's response. Here's the signature of `interact` that we declared in this interface:

<<< @/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java#method

If the listener says we have to cancel (by returning `FAIL`) or fully finish (`SUCCESS`), the callback returns the result and finishes the loop.

`InteractionResult.PASS` moves on to the next listener, until all listeners are called and `PASS` is finally returned:

<<< @/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java#return

We can add Javadoc comments to the top of callback classes to document what each `InteractionResult` does. In our case, it might be:

<<< @/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java#javadoc

### Triggering the Event From a Mixin {#triggering-the-event-from-a-mixin}

We now have the basic event skeleton, but we need to trigger it. Because we want to have the event called when a player attempts to shear a sheep, we call the event `invoker` in `SheepEntity#interactMob` when `sheared()` is called (i.e. sheep can be sheared, and the player is holding shears):

<<< @/reference/latest/src/main/java/com/example/docs/mixin/event/SheepEntityMixin.java#mixin

### Creating a Test Implementation {#creating-a-test-implementation}

Now we need to test our event. You can register a listener in your initialization method (or another area, if you prefer) and add custom logic there.

Here's an example that drops a diamond instead of wool at sheep's feet:

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#implement

If you enter into your game and shear a sheep, a diamond should drop instead of wool.

<!-- TODO: maybe adding a video of a sheep dropping diamonds? -->
