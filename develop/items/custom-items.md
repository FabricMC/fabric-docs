---
title: Items
description: Learn how to add a custom item group.
authors:
  - dicedpixels
---

# Custom Items

To add additional behavior to an item, you'll need to create your own custom item by extending `Item`.

Let's create a `SadTaterItem` which extends `Item`. The default constructor takes an `Item.Settings` instance.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/SadTaterItem.java)

Let's play a sound when our custom item is used. We can override `use` to add custom behavior.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/SadTaterItem.java)

We return `TypedActionResult` indicating the result of the item use.

Finally, we register our custom item.

@[code lang=java transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

Now when you left-click to use your item, it will play a villager hurt sound.

## Custom Tooltips

You can add a custom tooltip to your item by overriding `appendTooltip()` in your custom item class.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/SadTaterItem.java)

Each call to `add()` will add one line to the tooltip.

![Cool Tater Tooltip](/assets/develop/items/sad-tater-tooltip.png)