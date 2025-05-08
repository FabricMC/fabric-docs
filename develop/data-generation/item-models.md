---
title: Item Model Generation
description: A guide to generating item models via datagen.
authors:
  - Fellteros
---

:::info PREREQUISITES
Make sure you've completed the [datagen setup](./setup) and created your [first item](../items/first-item).
:::

## Setup {#setup}

First, we will need to create our ModelProvider. Create a class that `extends FabricModelProvider`. Implement both abstract methods: `generateBlockStateModels` and `generateItemModels`.
Lastly, create a constructor matching super.

@[code lang=java transcludeWith=:::datagen-model:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Register this class in your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

## Generated Files {#generated-files}

Generating item models results in creating two separate JSON files:

1. **Item model**. File responsible for defining the textures, rotation and overall looks of the item. It's generated in the `generated/assets/modid/models/item` directory.
2. **Item model definition**. File defining which model should be used depending on various criteria such as components, interactions and more. It's generated in the `generated/assets/modid/items` directory.

## Item Models {#item-models}

This section covers the most used and basic methods used for generating item models.

```java
@Override
public void generateItemModels(ItemModelGenerator itemModelGenerator) {
}
```

For item models, we will be using the `generateItemModels` method. Its parameter `ItemModelGenerator itemModelGenerator` is responsible for generating the item models and also contains methods for doing so.

### Simple Item Models {#simple-item-models}

@[code lang=java transcludeWith=:::datagen-model:generated](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Simple item models are the default ones, which most Minecraft items use. Their parent model is the `GENERATED` Model. They use their 2D texture, which is then rendered as 3D in-game. An example would be boats, candles or dyes.

_**assets/modid/models/item/ruby.json**_

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/ruby.json)

Since we're using only one variant no matter the circumstances, we reference only the `ruby.json` item model file.

_**assets/modid/items/ruby.json**_

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/items/ruby.json)

:::tip
Search for `generated.json` file to see the exact rotation, scaling and positioning of the model.
:::

### Handheld Item Models {#handheld-item-models}

@[code lang=java transcludeWith=:::datagen-model:handheld](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

This type of item model is generally used by tools and weapons (axes, swords, trident). They are rotated and positioned a little differently from the simple models to look more natural in hand. Other than that, they look exactly the same as the simple ones.

_**assets/modid/models/item/guidite_axe.json**_

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/guidite_axe.json)

### Spawn Eggs {#spawn-eggs}

@[code lang=java transcludeWith=:::datagen-model:spawn-egg](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Spawn eggs have a little different approach when it comes to defining their looks. Since all spawn eggs use the same texture, we only need to specify its colors by passing in two **decimal** int values.

The first value is the base color, the second is for the spots.

:::tip
If you're struggling with finding the right decimal value for a color, search for "int-to-color convertor" and use one of the many calculators out there.
:::

_**assets/modid/items/suspicious_egg.json**_

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/items/suspicious_egg.json)

### Dyeable Items {#dyeable-items}

@[code lang=java transcludeWith=:::datagen-model:dyeable](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

The `ItemModelGenerator` also provides a method for generating models for dyeable items. Here you have to pass in a default decimal color value the item uses when it isn't dyed. (default value for leather is ``-6265536``)
It generates a simple item model JSON and an item model definition file specifying the tint color.

_**assets/modid/items/leather_gloves.json**_

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/items/leather_gloves.json)

:::warning IMPORTANT
You have to add your item to the ``ItemTags.DYEABLE`` Tag so you can dye it in your inventory!
:::

### Conditional Item Models {#conditional-item-models}

@[code lang=java transcludeWith=:::datagen-model:condition](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Lastly, we'll look into generating item models that change their visual based when a specific criteria is met; in this case, when the second parameter ``BooleanProperty`` is true. Here are some of them:

| Property               | Description                                                                    |
|------------------------|--------------------------------------------------------------------------------|
| `KeybindDownProperty`  | True when a specified key is pressed.                                          |
| `UsingItemProperty`    | True when the item is being used (e.g. when blocking with a shield).           |
| `BrokenProperty`       | True when the item has 0 durability (e.g. elytra changes texture when broken). |
| `HasComponentProperty` | True when the item has a certain component.                                    |

Of course, this isn't all of them. There is plenty more that will almost certainly cover your needs.

The third and fourth parameters are the models used when the property is true or false respectively.

## Custom Item Models {#custom-item-models}

Generating item models isn't limited to only vanilla methods; you can, of course, create your own. In this section, we will be creating a custom model for a balloon item.

### Parent Item Model {#parent-item-model}

First, let's create a parent item model that defines how does the item look in-game. We want the balloon to look the same as simple item models, but scaled up. That's pretty straight-forward; we set the parent to be the `item/generated` model, and then override the scaling.
Put this JSON file in the `resources/assets/mod_id/models/item` folder.

**_models/item/scaled2x.json_**

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/scaled2x.json)

This will make the model twice as big as the normal ones. Feel free to experiment with the values until you get the desired output.

### Custom Model {#custom-model}

Next, we need to create an instance of the `Model` class. It will represent the actual [parent item model](#parent-item-model) inside our mod.

@[code lang=java transcludeWith=:::datagen-model-custom:item-model](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

The `item()` method creates a new `Model` instance, pointing to the `scaled2x.json` file inside the `resources/assets/mod_id/models/item` folder.

TextureKey `LAYER0` represents the `#layer0` texture variable, which will then be replaced by an identifier pointing to a texture.

### Custom Datagen Method {#custom-datagen-method}

The last step is creating a custom method, which will be called in the `generateItemModels()` method and will be responsible for generating our item models.

@[code lang=java transcludeWith=:::datagen-model-custom:item-datagen-method](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Let's go over what the parameters are for:
1. ``Item item``: The item, for which we are generating the models (in this case `ModItems.BALLOON`).
2. ``ItemModelGenerator generator``: the same that get passed into the `generateItemModels()` method. Used for its fields.

First, we get the ``Identifier`` of the item with `BALLOON.upload()`, passing in a ``TextureMap`` and the `modelCollector` from our `generator` parameter.
Then, we'll use another of its fields, the `output` (which essentially works as a supplier), and use the `accept()` method, so that the models are actually generated.

And that's all! Now, we only need to call our method in the `generateItemModels()` method.

@[code lang=java transcludeWith=:::datagen-model-custom:balloon](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

## Sources and Links {#sources-and-links}

You can view the example tests in [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.4/fabric-data-generation-api-v1/src/), this documentation's [Reference Mod](https://github.com/FabricMC/fabric-docs/tree/main/reference) and the [Minecraft Wiki](https://minecraft.wiki/) for more information.
