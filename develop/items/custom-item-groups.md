---
title: Custom Item Groups
description: Learn how to create your own item group and add items to it.
authors:
  - IMB11
---

# Custom Item Groups

Item groups are the tabs in the creative inventory that store items. You can create your own item group to store your items in a separate tab. This is pretty useful if your mod adds a lot of items and you want to keep them organized in one location for your players to easily access.

## Creating the Item Group

It's surprisingly easy to create an item group. Simply create a new static final field in your items class to store the item group.

I'll be using the "Guidite Sword" from the previous [Custom Tools](./custom-tools.md) page as the icon for the group.

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

You will need to register your item group to the `ITEM_GROUP` registry for it to show up in-game.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

## Adding Items

To add items to the group, you can use the modify item group event similarly to how you added your items to the vanilla item groups:

@[code transcludeWith=:::9](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

<hr />

You should see the item group is now in the creative inventory menu. However, it is untranslated - you must add a translation key to your translations file - similarly to how you translated your first item.

![](/assets/develop/items/itemgroups_0.png)

## Adding a Translation Key

If you used `Text.translatable` for the `displayName` method of the item group builder, you will need to add the translation to your language file.

```json
{
    "itemGroup.fabric_docs_reference": "Fabric Docs Reference Items"
}
```

Now, as you can see, the item group should be correctly named:

![](/assets/develop/items/itemgroups_1.png)