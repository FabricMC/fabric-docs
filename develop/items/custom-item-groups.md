---
title: Custom Creative Tabs
description: Learn how to create your own creative tab and add items to it.
authors:
  - IMB11
---

Creative Tabs, also known as Item Groups, are the tabs in the creative inventory that store items. You can create your own creative tab to store your items in a separate tab. This is pretty useful if your mod adds a lot of items and you want to keep them organized in one location for your players to easily access.

## Creating the Creative Tab {#creating-the-item-group}

It's surprisingly easy to create an creative tab. Simply create a new static final field in your items class to store the creative tab and a resource key for it, you can then use Fabric's `ItemGroupEvents.modifyEntriesEvent` similarly to how you added your items to the vanilla creative tabs:

:::info
You'll note that this method is named `ItemGroupEvents` rather than the more fitting `CreativeModeTabEvents`. This will change in 1.21.12.
:::

@[code transcludeWith=:::9](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::_12](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

You should see the creative tab is now in the creative inventory menu. However, it is untranslated - you must add a translation key to your translations file - similarly to how you translated your first item.

![Creative Tab without translation in creative menu](/assets/develop/items/itemgroups_0.png)

## Adding a Translation Key {#adding-a-translation-key}

If you used `Component.translatable` for the `title` method of the creative tab builder, you will need to add the translation to your language file.

```json
{
  "itemGroup.example-mod": "Example Mod"
}
```

Now, as you can see, the creative tab should be correctly named:

![Fully completed creative tab with translation and items](/assets/develop/items/itemgroups_1.png)
