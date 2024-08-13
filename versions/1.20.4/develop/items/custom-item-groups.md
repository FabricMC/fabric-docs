---
title: Custom Item Groups
description: Learn how to create your own item group and add items to it.
authors:
  - IMB11

search: false
---

# Custom Item Groups {#custom-item-groups}

Item groups are the tabs in the creative inventory that store items. You can create your own item group to store your items in a separate tab. This is pretty useful if your mod adds a lot of items and you want to keep them organized in one location for your players to easily access.

## Creating the Item Group {#creating-the-item-group}

It's surprisingly easy to create an item group. Simply create a new static final field in your items class to store the item group and a registry key for it, you can then use the item group event similarly to how you added your items to the vanilla item groups:

@[code transcludeWith=:::9](@/reference/1.20.4/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::_12](@/reference/1.20.4/src/main/java/com/example/docs/item/ModItems.java)

<hr />

You should see the item group is now in the creative inventory menu. However, it is untranslated - you must add a translation key to your translations file - similarly to how you translated your first item.

![Item group without translation in creative menu](/assets/develop/items/itemgroups_0.png)

## Adding a Translation Key {#adding-a-translation-key}

If you used `Text.translatable` for the `displayName` method of the item group builder, you will need to add the translation to your language file.

```json
{
    "itemGroup.fabric_docs_reference": "Fabric Docs Reference"
}
```

Now, as you can see, the item group should be correctly named:

![Fully completed item group with translation and items](/assets/develop/items/itemgroups_1.png)
