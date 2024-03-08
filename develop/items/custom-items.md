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


