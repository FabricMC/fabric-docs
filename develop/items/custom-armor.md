---
title: Custom Armor
description: Learn how to create your own armor sets.
authors:
  - IMB11
---

# Custom Armor {#custom-armor}

Armor provides the player with increased defense against attacks from mobs and other players.

## Creating an Armor Materials Class {#creating-an-armor-materials-class}

Just like items and blocks, armor materials need to be registered. We will create a `ModArmorMaterials` class to store our custom armor materials for the sake of organization.

You will need to add a static `initialize()` method to this class, and call it from your mod's entrypoint so that the materials are registered.

```java
// Within the ModArmorMaterials class
public static void initialize() {};
```

::: warning
Make sure to call this method **before** you register your items, as the materials will need to be registered before the items can be created.
:::

```java
@Override
public void onInitialize() {
  ModArmorMaterials.initialize();
}
```

---

Within this `ModArmorMaterials` class, you will need to create a static method which will register the armor material. This method should return a registry entry of the material, as this entry will be used by the ArmorItem constructor to create the armor items.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

## Armor Material Properties {#armor-material-properties}

::: tip
If you're struggling to gauge a good value for any of these properties, consider looking at the vanilla armor materials in the `ArmorMaterials` class.
:::

When creating an armor material, you will need to define the following properties:

### Defense Points {#defense-points}

::: warning
Ensure you assign a value to each type of armor piece you plan to create and register as an item. If you make an item for an armor piece without a set defense point value, the game will crash.
:::

The `defensePoints` map is used to define the number of defense points that each armor piece will provide. The higher the number, the more protection the armor piece will provide. The map should contain an entry for each armor piece type.

### Enchantability {#enchantability}

The `enchantability` property defines how easily the armor can be enchanted. The higher the number, the more enchantments the armor can receive.

### Equip Sound {#equip-sound}

The `equipSound` property is the sound that will play when the armor is equipped. This sound should be a registry entry of a `SoundEvent`. Consider taking a look at the [Custom Sound Events](../sounds/custom) page if you are considering creating custom sounds rather than relying on vanilla sounds within the `SoundEvents` class.

### Repair Ingredient(s) {#repair-ingredient}

The `repairIngredientSupplier` property is a supplier of an `Ingredient` that is used to repair the armor. This ingredient can pretty much be anything, it's recommended to set it to be the same as the material's crafting ingredient used to actually craft the armor items.

### Toughness {#toughness}

The `toughness` property defines how much damage the armor will absorb. The higher the number, the more damage the armor will absorb.

### Knockback Resistance {#knockback-resistance}

The `knockbackResistance` property defines how much knockback the player will reflect when hit. The higher the number, the less knockback the player will receive.

### Dyeable {#dyeable}

The `dyeable` property is a boolean that defines whether the armor can be dyed. If set to `true`, the armor can be dyed using dyes in a crafting table.

If you do choose to make your armor dyeable, your armor layer and item textures must be **designed to be dyed**, as the dye will overlay the texture, not replace it. Take a look at the vanilla leather armor for an example, the textures are grayscale and the dye is applied as an overlay, causing the armor to change color.

## Registering the Armor Material {#registering-the-armor-material}

Now that you've created a utility method which can be used to register armor materials, you can register your custom armor materials as a static field in the `ModArmorMaterials` class.

For this example, we'll be creative Guidite armor, with the following properties:

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

<DownloadEntry type="Item Textures" visualURL="/assets/develop/items/armor_0.png" downloadURL="/assets/develop/items/example_armor_item_textures.zip" />

::: info
You will need model JSON files for all the items, not just the helmet, it's the same principle as other item models.
:::

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/guidite_helmet.json)

As you can see, in-game the armor items should have suitable models:

![Armor item models](/assets/develop/items/armor_1.png)

## Armor Textures and Model {#armor-textures-and-model}

When an entity wears your armor, currently the missing texture will appear:

![Broken armor model on player](/assets/develop/items/armor_2.png).

There are two layers for the armor texture, both must be present.

Since the armor material name in our case is `guidite`, the locations of the textures will be:

- `assets/<mod-id>/textures/models/armor/guidite_layer_1.png`
- `assets/<mod-id>/textures/models/armor/guidite_layer_2.png`

<DownloadEntry type="Armor Model Textures" noVisualURL="true" downloadURL="/assets/develop/items/example_armor_layer_textures.zip" />

The first layer contains textures for the helmet and chestplate, whilst the second layer contains textures for leggings and boots.

When these textures are present, you should be able to see your armor on entities that wear it:

![Working armor model on player](/assets/develop/items/armor_3.png).
