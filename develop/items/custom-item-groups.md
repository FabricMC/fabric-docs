---
title: Custom Item Groups
description: Learn how to create your own item group and add items to it.
authors:
  - IMB11
---

# Custom Item Groups

In the last few pages - you've added quite a few items, it's time to consider making your own item group.

## Creating the Item Group

It's surprisingly easy to create an item group. Simply create a new static final field in your items class to store the item group.

I'll be using the "Guidite Sword" from the previous [Custom Tools](./custom-tools.md) page as the icon for the group.

```java
public static final ItemGroup MY_MOD_ITEMGROUP = FabricItemGroup.builder()
  .icon(() -> new ItemStack(ModItems.GUIDITE_SWORD))
  .displayName(Text.translatable("itemGroup.fabric_docs_reference"))
  .build();
```

You will need to register your item group to the `ITEM_GROUP` registry for it to show up in-game.

```java
Registry.register(Registries.ITEM_GROUP, new Identifier(FabricDocsReference.MOD_ID, "item_group"), MY_MOD_ITEMGROUP);
```

## Adding Items

To add items to the group, you can use the modify item group event similarly to how you added your items to the vanilla item groups:

```java
var groupRegistryKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier(FabricDocsReference.MOD_ID, "item_group"))
ItemGroupEvents.modifyEntriesEvent(groupRegistryKey).register(itemGroup -> {
    itemGroup.add(ModItems.SUSPICIOUS_SUBSTANCE);
    itemGroup.add(ModItems.GUIDITE_SWORD);
    // .. other items you've made
});
```

<hr />

You should see the item group is now in the creative inventory menu. However, it is untranslated - you must add a translation key to your translations file - similarly to how you translated your first item.

![](/assets/develop/items/itemgroups_0.png)

## Adding a Translation Key

If you used `Text.translatable` for the `displayName` method of the item group builder, you will need to add the translation to your language file.

```json
{
    "itemGroup.fabric_docs_reference": "Fabric Reference Mod Items"
}
```

Now, as you can see, the item group should be correctly named:

![](/assets/develop/items/itemgroups_1.png)