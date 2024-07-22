---
title: Custom Data Components
description: Learn how to add custom data to your items using the new 1.20.5 component system.
authors:
  - Romejanic
---

# Custom Data Components {#custom-data-components}

As your items grow more complex, you may find yourself needing to store custom data associated with each item. The game allows you to store persistent data within an `ItemStack`, and as of 1.20.5 the way we do that is by using **Data Components**.

Data Components replace NBT data from previous versions with structured data types which can be applied to an `ItemStack` to store persistent data about that stack. Data components are namespaced, meaning we can implement our own data components to store custom data about an `ItemStack` and access it later. A full list of the vanilla data components can be found on this [Minecraft wiki page](https://minecraft.wiki/w/Data_component_format#List_of_components).

Along with registering custom components, this page covers the general usage of the components API, which also applies to vanilla components. You can see and access the definitions of all vanilla components in the `DataComponentTypes` class.

## Registering a Component {#registering-a-component}

As with anything else in your mod you will need to register your custom component using a `ComponentType`. This component type takes a generic argument containing the type of your component's value. We will be focusing on this in more detail further down when covering [basic](#basic-data-components) and [advanced](#advanced-data-components) components.

Choose a sensible class to place this in. For this example we're going to make a new package called `component` and a class to contain all of our component types called `ModComponents`. Make sure you call `ModComponents.initialize()` in your mod initializer.

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

This is the basic template to register a component type:

```java
public static final ComponentType<?> MY_COMPONENT_TYPE = Registry.register(
    Registries.DATA_COMPONENT_TYPE,
    Identifier.of(FabricDocsReference.MOD_ID, "my_component"),
    ComponentType.<?>builder().codec(null).build()
);
```

There are a few things here worth noting. On the first and fourth lines, you can see a `?`. This will be replaced with the type of your component's value. We'll fill this in soon.

Secondly, you must provide an `Identifier` containing the intended ID of your component. This is namespaced with your mod's mod ID.

Lastly, we have a `ComponentType.Builder` that creates the actual `ComponentType` instance that's being registered. This contains another crucial detail we will need to discuss: your component's `Codec`. This is currently `null` but we will also fill it in soon.

## Basic Data Components {#basic-data-components}

Basic data components (like `minecraft:damage`) consist of a single data value, such as an `int`, `float`, `boolean` or `String`.

As an example, let's create an `Integer` value that will track how many times the player has right-clicked while holding our item. Let's update our component registration to the following:

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

You can see that we're now passing `<Integer>` as our generic type, indicating that this component will be stored as a single `int` value. For our codec, we are using the provided `Codec.INT` codec. We can get away with using basic codecs for simple components like this, but more complex scenarios might require a custom codec (this will be covered briefly later on).

If you start the game, you should be able to enter a command like this:

![`/give` command showing the custom component](/assets/develop/items/custom_component_0.png)

When you run the command, you should receive the item containing the component. However, we are not currently using our component to do anything useful. Let's start by reading the value of the component in a way we can see.

## Reading Component Value {#reading-component-value}

Let's add a new item which will increase the counter each time it is right clicked. You should read the [Custom Item Interactions](./custom-item-interactions) page which will cover the techniques we will use in this guide.

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Remember as usual to register the item in your `ModItems` class.

```java
public static final Item COUNTER = register(new CounterItem(
    new Item.Settings()
), "counter");
```

We're going to add some tooltip code to display the current value of the click count when we hover over our item in the inventory. We can use the `get()` method on our `ItemStack` to get our component value like so:

```java
int clickCount = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
```

This will return the current component value as the type we defined when we registered our component. We can then use this value to add a tooltip entry. Add this line to the `appendTooltip` method in the `CounterItem` class:

```java
public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
    int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
    tooltip.add(Text.translatable("item.fabric-docs-reference.counter.info", count).formatted(Formatting.GOLD));
}
```

Don't forget to update your lang file (`/assets/<mod id>/lang/en_us.json`) and add these two lines:

```json
{
    "item.fabric-docs-reference.counter": "Counter",
    "item.fabric-docs-reference.counter.info": "Used %1$s times",
}
```

Start up the game and run this command to give yourself a new Counter item with a count of 5.

```mcfunction
/give @p fabric-docs-reference:counter[fabric-docs-reference:click_count=5]
```

When you hover over this item in your inventory, you should see the count displayed in the tooltip!

![Tooltip showing "Used 5 times"](/assets/develop/items/custom_component_1.png)

However, if you give yourself a new Counter item _without_ the custom component, the game will crash when you hover over the item in your inventory. You should see an error like this in the crash report:

```log
java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "net.minecraft.item.ItemStack.get(net.minecraft.component.ComponentType)" is null
        at com.example.docs.item.custom.CounterItem.appendTooltip(LightningStick.java:45)
        at net.minecraft.item.ItemStack.getTooltip(ItemStack.java:767)
```

As expected, since the `ItemStack` doesn't currently contain an instance of our custom component, calling `stack.get()` with our component type will return `null`.

There are three solutions we can use to address this problem.

### Setting a Default Component Value {#setting-default-value}

When you register your item and pass a `Item.Settings` object to your item constructor, you can also provide a list of default components that are applied to all new items. If we go back to our `ModItems` class, where we register the `CounterItem`, we can add a default value for our custom component. Add this so that new items display a count of `0`.

@[code transcludeWith=::_13](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

When a new item is created, it will automatically apply our custom component with the given value.

::: warning
Using commands, it is possible to remove a default component from an `ItemStack`. You should refer to the next two sections to properly handle a scenario where the component is not present on your item.
:::

### Reading with a Default Value {#reading-default-value}

In addition, when reading the component value, we can use the `getOrDefault()` method on our `ItemStack` object to return a specified default value if the component is not present on the stack. This will safeguard against any errors resulting from a missing component. We can adjust our tooltip code like so:

```java
int clickCount = stack.getOrDefault(ModComponents.CLICK_COUNT_COMPONENT, 0);
```

As you can see, this method takes two arguments: our component type like before, and a default value to return if the component is not present.

### Checking if a Component Exists {#checking-if-component-exists}

You can also check for the existence of a specific component on an `ItemStack` using the `contains()` method. This takes the component type as an argument and returns `true` or `false` depending on whether the stack contains that component.

```java
boolean exists = stack.contains(ModComponents.CLICK_COUNT_COMPONENT);
```

### Fixing the Error {#fixing-the-error}

We're going to go with the third option. So along with adding a default component value, we'll also check if the component is present on the stack and only show the tooltip if it is.

@[code transcludeWith=::3](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Start the game again and hover over the item without the component, you should see that it displays "Used 0 times" and no longer crashes the game.

![Tooltip showing "Used 0 times"](/assets/develop/items/custom_component_2.png)

Try giving yourself a Counter with our custom component removed. You can use this command to do so:

```mcfunction
/give @p fabric-docs-reference:counter[!fabric-docs-reference:click_count]
```

When hovering over this item, the tooltip should be missing.

![Counter item with no tooltip](/assets/develop/items/custom_component_7.png)

## Updating Component Value {#setting-component-value}

Now let's try updating our component value. We're going to increase the click count each time we use our Counter item. To change the value of a component on an `ItemStack` we use the `set()` method like so:

```java
stack.set(ModComponents.CLICK_COUNT_COMPONENT, newValue);
```

This takes our component type and the value we want to set it to. In this case it will be our new click count. This method also returns the old value of the component (if one is present) which may be useful in some situations. For example:

```java
int oldValue = stack.set(ModComponents.CLICK_COUNT_COMPONENT, newValue);
```

Let's set up a new `use()` method to read the old click count, increase it by one, and then set the updated click count.

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Now try starting the game and right-clicking with the Counter item in your hand. If you open up your inventory and look at the item again you should see that the usage number has gone up by the amount of times you've clicked it.

![Tooltip showing "Used 8 times"](/assets/develop/items/custom_component_3.png)

## Removing Component Value {#removing-component-value}

You can also remove a component from your `ItemStack` if it is no longer needed. This is done by using the `remove()` method, which takes in your component type.

```java
stack.remove(ModComponents.CLICK_COUNT_COMPONENT);
```

This method also returns the value of the component before being removed, so you can also use it as follows:

```java
int oldCount = stack.remove(ModComponents.CLICK_COUNT_COMPONENT);
```

## Advanced Data Components {#advanced-data-components}

You may need to store multiple attributes in a single component. As a vanilla example, the `minecraft:food` component stores several values related to food, such as `nutrition`, `saturation`, `eat_seconds` and more. In this guide we'll refer to them as "composite" components.

For composite components, you must create a `record` class to store the data. This is the type we'll register in our component type and what we'll read and write when interacting with an `ItemStack`. Start by making a new record class in the `component` package we made earlier.

```java
public record MyCustomComponent() {
}
```

Notice that there's a set of brackets after the class name. This is where we define the list of properties we want our component to have. Let's add a float and a boolean called `temperature` and `burnt` respectively.

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/MyCustomComponent.java)

Since we are defining a custom data structure, there won't be a pre-existing `Codec` for our use case like with the [basic component](#basic-data-components). This means we're going to have to construct our own codec. Let's define one in our record class using a `RecordCodecBuilder` which we can reference once we register the component. For more details on using a `RecordCodecBuilder` you can refer to [this section of the Codecs page](../codecs#merging-codecs-for-record-like-classes).

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/component/MyCustomComponent.java)

You can see that we are defining a list of custom fields based on the primitive `Codec` types. However, we are also telling it what our fields are called using `fieldOf()`, and then using `forGetter()` to tell the game which attribute of our record to populate.

You can also define optional fields by using `optionalFieldOf()` and passing a default value as the second argument. Any fields not marked optional will be required when setting the component using `/give` so make sure you mark any optional arguments as such when creating your codec.

Finally, we call `apply()` and pass our record's constructor. For more details on how to construct codecs and more advanced use cases, be sure to read the [Codecs](../codecs) page.

Registering a composite component is similar to before. We just pass our record class as the generic type, and our custom `Codec` to the `codec()` method.

@[code transcludeWith=::3](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

Now start the game. Using the `/give` command, try applying the component. Composite component values are passed as an object enclosed with `{}`. If you put blank curly brackets, you'll see an error telling you that the required key `temperature` is missing.

![Give command showing missing key "temperature"](/assets/develop/items/custom_component_4.png)

Add a temperature value to the object using the syntax `temperature:8.2`. You can also optionally pass a value for `burnt` using the same syntax but either `true` or `false`. You should now see that the command is valid, and can give you an item containing the component.

![Valid give command showing both properties](/assets/develop/items/custom_component_5.png)

### Getting, Setting and Removing Advanced Components {#getting-setting-removing-advanced-comps}

Using the component in code is the same as before. Using `stack.get()` will return an instance of your `record` class, which you can then use to read the values. Since records are read-only, you will need to create a new instance of your record to update the values.

```java
// read values of component
MyCustomComponent comp = stack.get(ModComponents.MY_CUSTOM_COMPONENT);
float temp = comp.temperature();
boolean burnt = comp.burnt();

// set new component values
stack.set(ModComponents.MY_CUSTOM_COMPONENT, new MyCustomComponent(8.4f, true));

// check for component
if (stack.contains(ModComponents.MY_CUSTOM_COMPONENT)) {
    // do something
}

// remove component
stack.remove(ModComponents.MY_CUSTOM_COMPONENT);
```

You can also set a default value for a composite component by passing a component object to your `Item.Settings`. For example:

```java
public static final Item COUNTER = register(new CounterItem(
    new Item.Settings().component(ModComponents.MY_CUSTOM_COMPONENT, new MyCustomComponent(0.0f, false))
), "counter");
```

Now you can store custom data on an `ItemStack`. Use responsibly!

![Item showing tooltips for click count, temperature and burnt](/assets/develop/items/custom_component_6.png)
