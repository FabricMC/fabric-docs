---
title: Custom Armor
description: Learn how to create your own armor sets.
authors:
  - IMB11
---

# Custom Armor {#custom-armor}

Armor provides the player with increased defense against attacks from mobs and other players.

## Creating an Armor Materials Class {#creating-an-armor-materials-class}

Technically, you dont need a dedicated class for your armor material, but it's good practice anyways with the number of static fields you will need.

For this example, we'll create an `GuiditeArmorMaterial` class to store our static fields.

### Base Durability {#base-durability}

This constant will be used in the `Item.Settings#maxDamage(int damageValue)` method when creating our armor items, it is also required as a parameter in the `ArmorMaterial` constructor when we create our `ArmorMaterial` object later.

@[code transcludeWith=:::base_durability](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

If you're struggling to determine a balanced base durability, you can refer to the vanilla armor material instances found in the `ArmorMaterials` interface.

### Equipment Asset Registry Key {#equipment-asset-registry-key}

Whilst we dont have to register our `ArmorMaterial` to any registries, the game will use this to find the relevant textures for our armor, it's generally good practice to store any registry keys as constants.

@[code transcludeWith=:::material_key](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

We will pass this to the `ArmorMaterial` constructor later.

### `ArmorMaterial` Instance {#armormaterial-instance}

To create our material, we need to create a new instance of the `ArmorMaterial` record, the base durability and material registry key constants will be used here.

@[code transcludeWith=:::guidite_armor_material](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

The `ArmorMaterial` constructor accepts the following parameters, in this specific order:

| Parameter | Description |
| --------- | ----------- |
| `durability` | The base durability of all armor pieces, this is used when calculating the total durability of each individual armor piece that use this material. This should be the base durability constant you created earlier. |
| `defense` | A map of `EquipmentType` (an enum representing each armor slot) to an integer value, which indicates the defense value of the material when used in the corresponding armor slot. |
| `enchantmentValue` | The "enchantibility" of armor items which use this material. |
| `equipSound` | A registry entry of a sound event that is played when you equip a piece of armor which uses this material. For more information on sounds, check out the [Custom Sounds](../sounds/custom) page. |
| `toughness` | A float value which represents the "toughness" attribute of the armor material - essentially how well the armor will absorb damage. |
| `knockbackResistance` | A float value which represents the amount of knockback resistance the armor material grants the wearer. |
| `repairIngredient` | An item tag that represents all items which can be used to repair the armor items of this material in an anvil. |
| `assetId` | An `EquipmentAsset` registry key, this should be the equipment asset registry key constant you created earlier. |

If you're struggling to determine values for any of the parameters, you can consult the vanilla `ArmorMaterial` instances which can be found in the `ArmorMaterials` interface.

## Registering the Armor Material {#registering-the-armor-material}

Now that you've created a utility method which can be used to register armor materials, you can register your custom armor materials as a static field in the `ModArmorMaterials` class.

For this example, we'll be creating Guidite armor, with the following properties:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

## Creating the Armor Items {#creating-the-armor-items}

Now that you've registered the material, you can create the armor items in your `ModItems` class:

Obviously, an armor set doesn't need every type to be satisfied, you can have a set with just boots, or leggings etc. - the vanilla turtle shell helmet is a good example of an armor set with missing slots.

### Durability {#durability}

Unlike `ToolMaterial`, `ArmorMaterial` does not store any information about the durability of items.
For this reason the durability needs to be manually added to the armor items' `Item.Settings` when registering them.

This is achieved using the `maxDamage` method in the `Item.Settings` class.
The different armor slots have different base durabilities which are commonly multiplied by a shared armor material multiplier but hard-coded values can also be used.

For the Guidite armor we'll be using a shared armor multiplier stored alongside the armor material:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

We can then create the armor items using the durability constant:

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

You will also need to **add the items to an item group** if you want them to be accessible from the creative inventory.

As with all items, you should create translation keys for them as well.

## Texturing and Modelling {#texturing-and-modelling}

You will need to create two sets of textures:

- Textures and models for the items themselves.
- The actual armor texture that is visible when an entity wears the armor.

### Item Textures and Model {#item-textures-and-model}

These textures are no different to other items - you must create the textures, and create a generic generated item model - which was covered in the [Creating Your First Item](./first-item#adding-a-texture-and-model) guide.

For example purposes, you may use the following textures and model JSON as a reference.

<DownloadEntry visualURL="/assets/develop/items/armor_0.png" downloadURL="/assets/develop/items/example_armor_item_textures.zip">Item Textures</DownloadEntry>

::: info
You will need model JSON files for all the items, not just the helmet, it's the same principle as other item models.
:::

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/guidite_helmet.json)

As you can see, in-game the armor items should have suitable models:

![Armor item models](/assets/develop/items/armor_1.png)

## Armor Textures and Model {#armor-textures-and-model}

When an entity wears your armor, currently the missing texture will appear:

![Broken armor model on player](/assets/develop/items/armor_2.png)

There are two layers for the armor texture, both must be present.

Since the armor material name in our case is `guidite`, the locations of the textures will be:

- `assets/<mod-id>/textures/models/armor/guidite_layer_1.png`
- `assets/<mod-id>/textures/models/armor/guidite_layer_2.png`

<DownloadEntry downloadURL="/assets/develop/items/example_armor_layer_textures.zip">Armor Model Textures</DownloadEntry>

The first layer contains textures for the helmet and chestplate, whilst the second layer contains textures for leggings and boots.

When these textures are present, you should be able to see your armor on entities that wear it:

![Working armor model on player](/assets/develop/items/armor_3.png)
