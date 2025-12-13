---
title: Item Model Generation
description: A guide to generating item models via datagen.
authors:
  - Fellteros
  - VatinMc
  - skycatminepokie
---

:::info PREREQUISITES
Make sure you've completed the [datagen setup](./setup) and created your [first item](../items/first-item).
:::

## Setup {#setup}

First, we will need to create our ModelProvider. Create a class that `extends FabricModelProvider`. Implement both abstract methods: `generateBlockStateModels` and `generateItemModels`.
Lastly, create a constructor matching super.

@[code lang=java transcludeWith=:::datagen-model:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Register this class in your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

## Generated Files {#generated-files}

Generating item models results in creating two separate JSON files:

1. **Item model**. File responsible for defining the textures, rotation and overall looks of the item. It's generated in the `generated/assets/example-mod/models/item` directory.
2. **Client item**. File defining which model should be used depending on various criteria such as components, interactions and more. It's generated in the `generated/assets/example-mod/items` directory.

## Item Models {#item-models}

This section covers the most used and basic methods used for generating item models.

```java
@Override
public void generateItemModels(ItemModelGenerator itemModelGenerator) {
}
```

For item models, we will be using the `generateItemModels` method. Its parameter `ItemModelGenerator itemModelGenerator` is responsible for generating the item models and also contains methods for doing so.

### Simple Item Models {#simple-item-models}

@[code lang=java transcludeWith=:::datagen-model:generated](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Simple item models are the default ones, which most Minecraft items use. Their parent model is the `GENERATED` Model. They use their 2D texture, which is then rendered as 3D in-game. An example would be boats, candles or dyes.

_**assets/example-mod/models/item/ruby.json**_

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/ruby.json)

Since we're using only one variant no matter the circumstances, we reference only the `ruby.json` item model file.

_**assets/example-mod/items/ruby.json**_

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/ruby.json)

:::tip
Search for `generated.json` file to see the exact rotation, scaling and positioning of the model.
:::

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/ruby_big.png" downloadURL="/assets/develop/data-generation/item-model/ruby.png">Ruby</DownloadEntry>

### Handheld Item Models {#handheld-item-models}

@[code lang=java transcludeWith=:::datagen-model:handheld](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

This type of item model is generally used by tools and weapons (axes, swords, trident). They are rotated and positioned a little differently from the simple models to look more natural in hand. Other than that, they look exactly the same as the simple ones.

_**assets/example-mod/models/item/guidite_axe.json**_

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_axe.json)

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/guidite_axe_big.png" downloadURL="/assets/develop/data-generation/item-model/guidite_axe.png">Guidite Axe</DownloadEntry>

### Dyeable Items {#dyeable-items}

@[code lang=java transcludeWith=:::datagen-model:dyeable](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

The `ItemModelGenerator` also provides a method for generating models for dyeable items. Here you have to pass in a default decimal color value the item uses when it isn't dyed. (default value for leather is `-6265536`)
It generates a simple item model JSON and an client item file specifying the tint color.

![Dyeing leather gloves](/assets/develop/data-generation/item-model/leather_gloves_dyeing.png)

_**assets/example-mod/items/leather_gloves.json**_

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/leather_gloves.json)

:::warning IMPORTANT
You have to add your item to the `ItemTags.DYEABLE` Tag so you can dye it in your inventory!
:::

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/leather_gloves_big.png" downloadURL="/assets/develop/data-generation/item-model/leather_gloves.png">Leather Gloves</DownloadEntry>

### Conditional Item Models {#conditional-item-models}

@[code lang=java transcludeWith=:::datagen-model:condition](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Next, we'll look into generating item models that change their visual based when a specific boolean is true; in this case, the second parameter `BooleanProperty`. Here are some of them:

| Property               | Description                                                                    |
|------------------------|--------------------------------------------------------------------------------|
| `IsKeybindDown`  | True when a specified key is pressed.                                          |
| `IsUsingItem`    | True when the item is being used (e.g. when blocking with a shield).           |
| `Broken`       | True when the item has 0 durability (e.g. elytra changes texture when broken). |
| `HasComponent` | True when the item has a certain component.                                    |

Of course, this isn't all of them. There is plenty more that will almost certainly cover your needs.

The third and fourth parameters are the models used when the property is true or false respectively.

<VideoPlayer src="/assets/develop/data-generation/item-model/flashlight_turning_on.webm">Flashlight turning on and off</VideoPlayer>

_**assets/example-mod/items/flashlight.json**_

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/flashlight.json)

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/flashlight_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/flashlight_textures.zip">Flashlight</DownloadEntry>

:::warning IMPORTANT
When obtaining `ResourceLocation`s for the `ItemModelUtils.plainModel()`, always use `itemModelGenerator.createFlatItemModel()` or any other method using it, otherwise your item model files won't generate, only client items!
:::

### Composite Item Models {#composite-item-models}

@[code lang=java transcludeWith=:::datagen-model:composite](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

These item models are composed of one or more textures layered on top of each other. There aren't any vanilla methods for this, you have to use `ItemModelGenerator`'s `itemModelOutput` field and then `accept()` for it to work.
Here's the client item JSON:

_**assets/example-mod/items/enhanced_hoe.json**_

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/enhanced_hoe.json)

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures.zip">Enhanced Hoe</DownloadEntry>

### Select Item Models {#select-item-models}

@[code lang=java transcludeWith=:::datagen-model:select](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Renders an item model based on the value of a specific property. These are some of them:

| Property                    | Description                                                                                        |
|-----------------------------|----------------------------------------------------------------------------------------------------|
| `ContextDimension`  | Renders an item model based on the dimension in which the player is (Overworld, Nether, End).      |
| `MainHand`          | Renders an item model when the item is equipped in player's main hand.                             |
| `DisplayContext`    | Renders an item model based on the position in which the item is (`ground`, `fixed`, `head`, ...). |
| `ContextEntityType` | Renders an item model based on the entity holding the item.                                        |

In this example, the item changes texture when traveling between dimensions; green in Overworld, red in the Nether and black in the End.

![Dimensional crystal changing texture based on dimension](/assets/develop/data-generation/item-model/crystal.png)

_**assets/example-mod/items/dimensional_crystal.json**_

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/dimensional_crystal.json)

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures.zip">Dimensional Crystal</DownloadEntry>

### Range Dispatch Item Models {#range-dispatch-item-models}

@[code lang=java transcludeWith=:::datagen-model:range-dispatch](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Renders an item model based on the value of a numeric property. Take in an item and list of variants paired with a value. There are quite a few, here are some examples:

| Property              | Description                                                                  |
|-----------------------|------------------------------------------------------------------------------|
| `Cooldown`    | Renders an item model based on item's remaining cooldown.                    |
| `Count`        | Renders an item model based on stack size.                                   |
| `UseDuration` | Renders an item model based on how long the item is being used.              |
| `Damage`      | Renders an item model based on attack damage (`minecraft:damage` component). |

This example uses the `Count`, changing the texture from one knife up to three based on the stack size.

:::tip
Some other examples of range dispatch item models are the compass, bow or brush.
:::

![Throwing knives changing texture based on count](/assets/develop/data-generation/item-model/throwing_knives_example.png)

_**assets/example-mod/items/throwing_knives.json**_

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/throwing_knives.json)

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/throwing_knives_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/throwing_knives_textures.zip">Throwing Knives</DownloadEntry>

## Custom Item Models {#custom-item-models}

:::info
All fields and methods for this part of the tutorial are declared in a static inner class called `CustomItemModelGenerator`.
:::

Generating item models isn't limited to only vanilla methods; you can, of course, create your own. In this section, we will be creating a custom model for a balloon item.

### Parent Item Model {#parent-item-model}

First, let's create a parent item model that defines how does the item look in-game. We want the balloon to look the same as simple item models, but scaled up. That's pretty straight-forward; we set the parent to be the `item/generated` model, and then override the scaling.
Put this JSON file in the `resources/assets/mod_id/models/item` folder.

**_models/item/scaled2x.json_**

@[code](@/reference/latest/src/main/resources/assets/example-mod/models/item/scaled2x.json)

This will make the model twice as big as the normal ones. Feel free to experiment with the values until you get the desired output.

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/balloon_big.png" downloadURL="/assets/develop/data-generation/item-model/balloon.png">Balloon</DownloadEntry>

### Custom Model {#custom-model}

Next, we need to create an instance of the `ModelTemplate` class. It will represent the actual [parent item model](#parent-item-model) inside our mod.

@[code lang=java transcludeWith=:::datagen-model-custom:item-model](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

The `item()` method creates a new `ModelTemplate` instance, pointing to the `scaled2x.json` file inside the `resources/assets/mod_id/models/item` folder.

TextureSlot `LAYER0` represents the `#layer0` texture variable, which will then be replaced by an identifier pointing to a texture.

### Custom Datagen Method {#custom-datagen-method}

The last step is creating a custom method, which will be called in the `generateItemModels()` method and will be responsible for generating our item models.

@[code lang=java transcludeWith=:::datagen-model-custom:item-datagen-method](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Let's go over what the parameters are for:

1. `Item item`: The item, for which we are generating the models (in this case `ModItems.BALLOON`).
2. `ItemModelGenerator generator`: the same that get passed into the `generateItemModels()` method. Used for its fields.

First, we get the `ResourceLocation` of the item with `BALLOON.upload()`, passing in a `TextureMapper` and the `modelCollector` from our `generator` parameter.
Then, we'll use another of its fields, the `itemModelOutput` (which essentially works as a consumer), and use the `accept()` method, so that the models are actually generated.

And that's all! Now, we only need to call our method in the `generateItemModels()` method.

@[code lang=java transcludeWith=:::datagen-model-custom:balloon](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

## Sources and Links {#sources-and-links}

You can view the example tests in [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.10/fabric-data-generation-api-v1/src/), this documentation's [Reference Mod](https://github.com/FabricMC/fabric-docs/tree/main/reference) and the [Minecraft Wiki](https://minecraft.wiki/) for more information.
