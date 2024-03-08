---
title: Items
description: Learn how to add a custom item group.
authors:
  - dicedpixels
---

# Item Groups

Item groups are categories of items based on their characteristics or usages.

So far, you have used `/give @p fabric-docs-reference:cool_tater` to give the player your item. To simplify obtaining
the item from the creative inventory, you can add the item to an item group.

There are several predefined item groups, and it's possible to create your own.

## Adding to Item Groups

Vanilla item groups are defined in `ItemGroups` as registry keys. The Fabric API exposes an `ItemGroupEvents` to add
your item into an existing item group.

Let's add our item to `ItemGroups.FOOD_AND_DRINK`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

By calling `entries.add()` with your item as an argument, it will be added at the end of the item group.

![Item Group](/assets/develop/items/item-group.png)

It's also possible to position your item relatively to existing vanilla items by calling `entries.addBefore()`
or `entries.addAfter()`.

For example, to place your item after the vanilla Potato item, you can call:

```java:no-line-numbers
entries.addAfter(Items.POTATO, COOL_TATER_ITEM)
```

## Creating an Item Group

It's possible to create your own item group as well.

::: warning

Before you create an item group, determine whether it would have enough content to warrant its own group. Your item
group will be placed on a separate creative tabs, impacting its discoverability, and users may be confused if the item
is not where similar items are in the creative inventory.

:::

The Fabric API provides `FabricItemGroup` which exposes a builder to create an item group.

Let's create an item group first.

@[code lang=java transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

You can add entries to your item group within the `entries()` callback method.

::: warning

Unlike vanilla item groups, where you can add items relative to existing items, you must add items to your own
item group in order since there are no vanilla items to position your items relative to.

:::

Finally, we register the item group.

@[code lang=java transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

![Item Group](/assets/develop/items/custom-item-group.png)