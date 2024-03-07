---
title: Items
description: Learn how to add a custom items such as tools, armor and food alongside crafting recipes and enchantments.
authors:
  - dicedpixels
---

# Items

Items are objects that can be held, used and placed within inventories.

## Adding an Item

To add an item we create an instance of `Item`, register it and give it a texture. To add custom behavior we
extend `Item`.

### Registering an Item

We start by creating an instance of `Item`. The constructor takes an instance of `Settings`, which is used to set item
properties such as durability and stack count.

::: info

The Fabric API provides `FabricItemSettings` which an extension of `Settings` exposes more properties which are
not available in vanilla `Settings`. You can replace `new Item.Settings()` with `new FabricItemSettings()`.

:::

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

You can then use the vanilla registry system to register the item. Items are registered into `Registries.ITEM`.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

You can simplify the item creation and registration as follows:

```java
public static final Item COOL_TATER_ITEM = s
    Registry.register(
        Registries.ITEM, 
        new Identifier("fabric-docs-reference", "cool_tater"), 
        new Item(new FabricItemSettings()));
```

Since we haven't added the item to an item group, you can only access the item using commands.
Execute `/give @p fabric-docs-reference:cool_tater` to give the player the item.

The item will appear without a texture. Let's fix this in the next subsections.

#### Item Model

The item model defines the three-dimensional shape of the item. You can define a custom models or use a built-in model.

Item models are located in:

```:no-line-numbers
resources/assets/<mod_id>/models/item/
```

::: info

Refer to the [Minecraft Wiki](https://minecraft.wiki/w/Model#Item_models) for a comprehensive definition of item
models.

:::

Let's use the built in `minecraft:item/generated` model for our item. Create a JSON file in
the `resources/assets/<mod_id>/models/item/` directory with the following content.

@[code lang=json](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/cool_tater.json)

`"textures"` contains reference(s) to textures, which are explained in the next section.

#### Item Texture

The item texture is the image applied on to the item model. The dimensions of this texture are 16 x 16 pixels.

Item textures are located in:

```:no-line-numbers
resources/assets/<mod_id>/textures/item/
```

Let's add the following texture to `resources/assets/<mod_id>/textures/item/`.

![Cool Tater texture](/assets/develop/items/cool-tater.png)

::: warning

If your item texture is not placed in the correct directory under your mod's name space, the item will appear
in the game incorrectly. The game log will contain a line indicating the missing model or texture.

```:no-line-numbers
[19:56:45] [Worker-Main-31/WARN] (Minecraft) Missing textures in model fabric-docs-reference:cool_tater#inventory:
    minecraft:textures/atlas/blocks.png:fabric-docs-reference:item/cool_tater
```

:::

Textures can be mapped to texture layers in the item model and can be referenced as `<mod_id>:item/<texture_name>`.

#### Item Name

You can define a name for the item in the format of `item.<mod_id>.<item_id>` in our mod's `en_us.json` file.

```:no-line-numbers
resources/assets/<mod_id>/lang/en_us.json
```

@[code lang=json transclude={4-4}](@/reference/latest/src/main/resources/assets/fabric-docs-reference/lang/en_us.json)

Once an item model, texture and a name is assigned, the item should be properly rendered in game.

![Cool Tater in game](/assets/develop/items/cool-tater-in-game.png)