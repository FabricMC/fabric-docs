---
title: Custom Data Components
description: Learn how to add custom data to your items using the new 1.21 component system.
authors:
  - Romejanic
---

# Custom Data Components {#custom-data-components}

As your items grow more complex, you may find yourself needing to store custom data associated with your item. The game allows you to store persistent data within an `ItemStack`, and as of 1.21 the way we do that is by using **Data Components**.

Data Components replace NBT data from previous versions with structured data types (i.e. components) which can be applied to an `ItemStack` to store persistent data about that stack. Data components are namespaced, meaning we can implement our own data components to store custom data about an `ItemStack` and access it later. A full list of the vanilla data components can be found in this [Minecraft snapshot changelog](https://www.minecraft.net/en-us/article/minecraft-snapshot-24w09a).

The page also covers the general usage of the components API which also applies to vanilla components. You can see and access the definitions of all vanilla components in the `DataComponentTypes` class.

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

When you run the command you should recieve the item containing the component. However we are not currently using our component to do anything useful. Let's start by reading the value of the component in a way we can see.

## Reading component value {#reading-component-value}

Let's extend the `LightningStick` tooltip code to display the current value of the click count when we hover over our item in the inventory. We can use the `get()` method on our `ItemStack` to get our component value like so:

```java
int clickCount = stack.get(ModItems.CLICK_COUNT_COMPONENT);
```

This will return the current component value as the type which we defined when we registered our component. We can then use this value to add a tooltip entry. Add this line to the `appendTooltip` method in the item class:

```java
@Override
public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
    int clickCount = stack.get(ModItems.CLICK_COUNT_COMPONENT);
    tooltip.add(Text.translatable("item.modid.lightning_stick.used", clickCount).formatted(Formatting.GOLD));
}
```

Don't forget to update your lang file (`/assets/<mod id>/lang/en_us.json`) and add this line:

```json
{
    "item.modid.lightning_stick.used": "Used %1$s times"
}
```

If you start the game and hover over the item you gave yourself earlier, you should see the count displayed as a tooltip!

![Tooltip showing "Used 5 times"](/assets/develop/items/custom_component_1.png)

However, if you give yourself a new Lightning Stick item *without* the custom component, the game will crash when you hover over the item in your inventory. You should see an error like this in the crash report:

```
java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "net.minecraft.item.ItemStack.get(net.minecraft.component.ComponentType)" is null
        at com.example.LightningStick.appendTooltip(LightningStick.java:45)
        at net.minecraft.item.ItemStack.getTooltip(ItemStack.java:767)
```

As expected, since the `ItemStack` doesn't currently contain an instance of our custom component, calling `stack.get()` with our component type will return `null`. 

There are two solutions we can use to address this problem:

### Setting a default component value {#setting-default-value}

When you register your item and pass a `Item.Settings` object to your item constructor, you can also provide a list of default components which are applied to all new items. If we go back to our `ModItems` class to where we register the Lightning Stick item, we can add a default value for our custom component.

```java
public static final Item LIGHTNING_STICK = register(
    // initialize component with default count of 0
    new LightningStick(new Item.Settings().component(CLICK_COUNT_COMPONENT, 0)),
    "lightning_stick"
);
```

When a new item is created it will automatically apply our custom component with the given value.

### Reading with a default value {#reading-default-value}

Alternatively, when we are reading our value in the `appendTooltip` method, we can use the `getOrDefault()` method on our `ItemStack` object to return a specified default value if the component is not present on the component. This will safeguard against any errors resulting from a missing component. We can adjust our tooltip code like so:

```java
int clickCount = stack.getOrDefault(ModItems.CLICK_COUNT_COMPONENT, 0);
```

As you can see this method takes two arguments, our component type like before and a default value to return if the component is not present.

If you implement either of these solutions and hover over the item without the component you should see that it displays "Used 0 times" and no longer crashes the game.

![Tooltip showing "Used 0 times"](/assets/develop/items/custom_component_2.png)

## Updating the component value {#setting-component-value}

Now let's try updating our component value. We're going to increase the click count each time we use our Lightning Stick item. To change the value of a component on an `ItemStack` we use the `set()` method like so:

```java
stack.set(ModItems.CLICK_COUNT_COMPONENT, newValue);
```

This takes our component type and the value we want to set it to. In this case it will be our new click count.

Let's add a few lines to our `use()` method to read the old click count, increase it by one and then set the updated click count.

```java
@Override
public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    // spawn lightning
    ...

    // Update the click count
    ItemStack stack = user.getStackInHand(hand);
    int clickCount = stack.getOrDefault(ModItems.CLICK_COUNT_COMPONENT, 0);
    stack.set(ModItems.CLICK_COUNT_COMPONENT, ++clickCount);

    return TypedActionResult.success(stack);
}
```

Now try starting the game and right clicking with the Lightning Stick. Count how many times you right click it. If you open up your inventory and look at the item again you should see that the usage number has gone up by the amount you clicked it.

![Tooltip showing "Used 8 times"](/assets/develop/items/custom_component_3.png)

## Removing the component value {#removing-component-value}

You can also remove a component from your `ItemStack` if it is no longer needed. This is done by using the `remove()` method which takes your component type.

```java
stack.remove(ModItems.CLICK_COUNT_COMPONENT);
```

This method also returns the value of the component before being removed, so you can also use it like so:

```java
int oldCount = stack.remove(ModItems.CLICK_COUNT_COMPONENT);
```
