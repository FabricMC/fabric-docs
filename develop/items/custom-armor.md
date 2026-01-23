---
title: Custom Armor
description: Learn how to create your own armor sets.
authors:
  - IMB11
---

Armor provides the player with increased defense against attacks from mobs and other players.

## Creating an Armor Materials Class {#creating-an-armor-materials-class}

Technically, you don't need a dedicated class for your armor material, but it's good practice anyways with the number of static fields you will need.

For this example, we'll create a `GuiditeArmorMaterial` class to store our static fields.

### Base Durability {#base-durability}

This constant will be used in the `Item.Properties#maxDamage(int damageValue)` method when creating our armor items, it is also required as a parameter in the `ArmorMaterial` constructor when we create our `ArmorMaterial` object later.

@[code transcludeWith=:::base_durability](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

If you're struggling to determine a balanced base durability, you can refer to the vanilla armor material instances found in the `ArmorMaterials` interface.

### Equipment Asset Registry Key {#equipment-asset-registry-key}

Even though we don't have to register our `ArmorMaterial` to any registries, it's generally good practice to store any registry keys as constants, as the game will use this to find the relevant textures for our armor.

@[code transcludeWith=:::material_key](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

We will pass this to the `ArmorMaterial` constructor later.

### `ArmorMaterial` Instance {#armormaterial-instance}

To create our material, we need to create a new instance of the `ArmorMaterial` record, the base durability and material registry key constants will be used here.

@[code transcludeWith=:::guidite_armor_material](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

The `ArmorMaterial` constructor accepts the following parameters, in this specific order:

| Parameter             | Description                                                                                                                                                                                                         |
| --------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `durability`          | The base durability of all armor pieces, this is used when calculating the total durability of each individual armor piece that use this material. This should be the base durability constant you created earlier. |
| `defense`             | A map of `EquipmentType` (an enum representing each armor slot) to an integer value, which indicates the defense value of the material when used in the corresponding armor slot.                                   |
| `enchantmentValue`    | The "enchantability" of armor items which use this material.                                                                                                                                                        |
| `equipSound`          | A registry entry of a sound event that is played when you equip a piece of armor which uses this material. For more information on sounds, check out the [Custom Sounds](../sounds/custom) page.                    |
| `toughness`           | A float value which represents the "toughness" attribute of the armor material - essentially how well the armor will absorb damage.                                                                                 |
| `knockbackResistance` | A float value which represents the amount of knockback resistance the armor material grants the wearer.                                                                                                             |
| `repairIngredient`    | An item tag that represents all items which can be used to repair the armor items of this material in an anvil.                                                                                                     |
| `assetId`             | An `EquipmentAsset` registry key, this should be the equipment asset registry key constant you created earlier.                                                                                                     |

We define the repair ingredient tag reference as follows:

@[code transcludeWith=:::repair_tag](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

If you're struggling to determine values for any of the parameters, you can consult the vanilla `ArmorMaterial` instances which can be found in the `ArmorMaterials` interface.

## Creating the Armor Items {#creating-the-armor-items}

Now that you've registered the material, you can create the armor items in your `ModItems` class:

Obviously, an armor set doesn't need every type to be satisfied, you can have a set with just boots, or leggings etc. - the vanilla turtle shell helmet is a good example of an armor set with missing slots.

Unlike `ToolMaterial`, `ArmorMaterial` does not store any information about the durability of items. For this reason the base durability needs to be manually added to the armor items' `Item.Properties` when registering them.

This is achieved by passing the `BASE_DURABILITY` constant we created previously into the `maxDamage` method in the `Item.Properties` class.

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

You will also need to **add the items to a creative tab** if you want them to be accessible from the creative inventory.

As with all items, you should create translation keys for them as well.

## Textures and Models {#textures-and-models}

You will need to create a set of textures for the items, and a set of textures for the actual armour when it's worn by a "humanoid" entity (players, zombies, skeletons, etc).

### Item Textures and Model {#item-textures-and-model}

These textures are no different to other items - you must create the textures, and create a generic generated item model - which was covered in the [Creating Your First Item](./first-item#adding-a-texture-and-model) guide.

For example purposes, you may use the following textures and model JSON as a reference.

<DownloadEntry visualURL="/assets/develop/items/armor_0.png" downloadURL="/assets/develop/items/example_armor_item_textures.zip">Item Textures</DownloadEntry>

::: info

You will need model JSON files for all the items, not just the helmet, it's the same principle as other item models.

:::

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_helmet.json)

As you can see, in-game the armor items should have suitable models:

![Armor item models](/assets/develop/items/armor_1.png)

### Armor Textures {#armor-textures}

When an entity wears your armor, nothing will be shown. This is because you're missing textures and the equipment model definitions.

![Broken armor model on player](/assets/develop/items/armor_2.png)

There are two layers for the armor texture, both must be present.

Previously, we created a `ResourceKey<EquipmentAsset>` constant called `GUIDITE_ARMOR_MATERIAL_KEY` which we passed into our `ArmorMaterial` constructor. It's recommended to name the texture similarly, so in our case, `guidite.png`

- `assets/example-mod/textures/entity/equipment/humanoid/guidite.png` - Contains upper body and boot textures.
- `assets/example-mod/textures/entity/equipment/humanoid_leggings/guidite.png` - Contains legging textures.

<DownloadEntry downloadURL="/assets/develop/items/example_armor_layer_textures.zip">Guidite Armor Model Textures</DownloadEntry>

::: tip

If you're updating to 1.21.11 from an older version of the game, the `humanoid` folder is where your `layer0.png` armor texture goes, and the `humanoid_leggings` folder is where your `layer1.png` armor texture goes.

:::

Next, you'll need to create an associated equipment model definition. These go in the `/assets/example-mod/equipment/` folder.

The `ResourceKey<EquipmentAsset>` constant we created earlier will determine the name of the JSON file. In this case, it'll be `guidite.json`.

Since we only plan to add "humanoid" (helmet, chestplate, leggings, boots etc.) armor pieces, our equipment model definition will look like this:

@[code](@/reference/latest/src/main/resources/assets/example-mod/equipment/guidite.json)

With the textures and equipment model definition present, you should be able to see your armor on entities that wear it:

![Working armor model on player](/assets/develop/items/armor_3.png)

<!-- TODO: A guide on creating equipment for dyeable armor could prove useful. -->
