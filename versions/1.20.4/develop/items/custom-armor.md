---
title: Custom Armor
description: Learn how to create your own armor sets.
authors:
  - IMB11

search: false
---

# Custom Armor {#custom-armor}

Armor provides the player with increased defense against attacks from mobs and other players.

## Creating an Armor Material {#creating-an-armor-material}

::: info
If you plan to make multiple armor materials, consider using an `Enum` to store them. Vanilla does this in the `ArmorMaterials` class, which stores all the armor materials that are used in the game.

This class can also be used to determine your armor material's properties in relation to vanilla armor materials.
:::

All armor items - like tools - have an armor material.

The armor material tells the game what protection and durability the armor item should have depending on the slot.

You'll need to create a class that inherits `ArmorMaterial`, like so:

@[code transcludeWith=:::1](@/reference/1.20.4/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

The following methods will have to be implemented as well - these methods tell the game vital information on your armor items:

- ### Durability - `getDurability(ArmorItem.Type type)`

  Returns the durability for a specific armor type - in hit points.

  The hit points specify the amount of hits the armor item can take before breaking.

  **Example**

  @[code transcludeWith=:::2](@/reference/1.20.4/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

- #### Protection - `getProtection(ArmorItem.Type type)`

  Returns the protection value for a specific armor type.

  Usually this is always the same, regardless of your armor material.

  **Example**

  @[code transcludeWith=:::3](@/reference/1.20.4/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

- #### Enchantability - `getEnchantability()`

  How easy is it to get better and higher level enchantments with this item?

  **Example**

  @[code transcludeWith=:::4](@/reference/1.20.4/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

- #### Equip Sound - `getEquipsound()`

  What sound should be played when the armor is equipped?

  **Example**

  @[code transcludeWith=:::5](@/reference/1.20.4/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

- #### Repair Ingredient - `getRepairIngredient()`

  What item or items can be used in an anvil to repair the armor items?

  **Example**

  @[code transcludeWith=:::6](@/reference/1.20.4/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

- #### Name - `getName()`

  The name of the armor material - must be lowercase.

  **Example**

  @[code transcludeWith=:::7](@/reference/1.20.4/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

- #### Toughness - `getToughness()`

  How much protection should be given for high-damage attacks?

  For reference, everything except diamond (`2.0F`) and netherite (`4.0F`) have a toughness of zero.

  **Example**

  @[code transcludeWith=:::8](@/reference/1.20.4/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

- #### Knockback Resistance - `getKnockbackResistance()`

  How much knockback resistance should the armor give the entity?

  **Example**

  @[code transcludeWith=:::9](@/reference/1.20.4/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

## Creating an Instance of the ArmorMaterial {#creating-an-instance-of-the-armormaterial}

To use the armor material with the armor items, you'll need to create an instance of it - similar to a tool material:

@[code transcludeWith=:::_10](@/reference/1.20.4/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

You can place this instance in the armor material class itself.

## Creating the Armor Items {#creating-the-armor-items}

Now that you've created an instance of the material, you can create the armor items in your `ModItems` class:

Obviously, an armor set doesn't need every type to be satisfied, you can have a set with just boots, or leggings etc. - the vanilla turtle shell helmet is a good example of an armor set with missing slots.

@[code transcludeWith=:::6](@/reference/1.20.4/src/main/java/com/example/docs/item/ModItems.java)

You will also need to **add the items to an item group** if you want them to be accessible from the creative inventory.

As with all items, you should create translation keys for them as well.

## Texturing and Modelling {#texturing-and-modelling}

You will need to create two sets of textures:

- Textures and models for the items themselves.
- The actual armor texture that is visible when an entity wears the armor.

### Item Textures and Model {#item-textures-and-model}

These textures are no different to other items - you must create the textures, and create a generic generated item model - which was covered in the [Creating Your First Item](./first-item#adding-a-texture-and-model) guide.

For example purposes, you may use the following textures and model JSON as a reference.

<DownloadEntry type="Item Textures" visualURL="/assets/develop/items/armor_0.png" downloadURL="/assets/develop/items/example_armor_item_textures.zip" />

::: info
You will need model JSON files for all the items, not just the helmet, it's the same principle as other item models.
:::

@[code](@/reference/1.20.4/src/main/resources/assets/fabric-docs-reference/models/item/guidite_helmet.json)

As you can see, in-game the armor items should have suitable models:

![Armor item models](/assets/develop/items/armor_1.png)

## Armor Textures and Model {#armor-textures-and-model}

When an entity wears your armor, currently the missing texture will appear:

![Broken armor model on player](/assets/develop/items/armor_2.png).

This is because all armor textures are hardcoded by vanilla, to create our own, we'll have to place the texture in the vanilla armor texture folder.

There are two layers for the armor texture, both must be present.

Since the armor material name in our case is `guidite`, the locations of the textures will be:

- `assets/minecraft/textures/models/armor/guidite_layer_1.png`
- `assets/minecraft/textures/models/armor/guidite_layer_2.png`

<DownloadEntry type="Armor Model Textures" noVisualURL="true" downloadURL="/assets/develop/items/example_armor_layer_textures.zip" />

The first layer contains textures for the helmet and chestplate, whilst the second layer contains textures for leggings and boots.

When these textures are present, you should be able to see your armor on entities that wear it:

![Working armor model on player](/assets/develop/items/armor_3.png).
