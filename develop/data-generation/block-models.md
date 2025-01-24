---
title: Block Model Generation
description: A guide to generating block models and blockstates via datagen.
authors:
  - Fellteros
  - natri0
  - IMB11
  - its-miroma
---

# Block Model Generation {#block-model-generation}

::: info PREREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.
:::

## Setup {#setup}

First, we will need to create our ModelProvider. Create a class that `extends FabricModelProvider`. Implement both abstract methods: `generateBlockStateModels` and `generateItemModels`.
Lastly, create a constructor matching super.

@[code lang=java transcludeWith=:::datagen-model:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Register this class in your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

## Blockstates and Block Models {#blockstates-and-block-models}

```java
@Override
public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
}
```

For block models, we will primarily be focusing on the `generateBlockStateModels` method. Notice the parameter `BlockStateModelGenerator blockStateModelGenerator` - this object will be responsible for generating all the JSON files.
Here are some handy examples you can use to generate your desired models:

### Simple Cube All {#simple-cube-all}

@[code lang=java transcludeWith=:::datagen-model:cube-all](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

This is the most commonly used function. It generates a JSON model file for a normal `cube_all` block model. One texture is used for all six sides, in this case we use `steel_block`.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/block/steel_block.json)

It also generates a blockstate JSON file. Since we have no blockstate properties (e.g. Axis, Facing, ...), one variant is sufficient, and is used every time the block is placed.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/blockstates/steel_block.json)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/steel_block_big.png" downloadURL="/assets/develop/data-generation/block-model/steel_block.png">Steel Block</DownloadEntry>

### Singletons {#singletons}

The `registerSingleton` method provides JSON model files based on the `TexturedModel` you pass in and a single blockstate variant.

@[code lang=java transcludeWith=:::datagen-model:cube-top-for-ends](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

This method will generate models for a normal cube, that uses the texture file `pipe_block` for the sides and the texture file `pipe_block_top` for the top and bottom sides.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/block/pipe_block.json)

:::tip
If you're stuck choosing which `TextureModel` you should use, open the `TexturedModel` class and look at the [`TextureMaps`](#using-texture-map)!
:::

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/pipe_block_textures_big.png" downloadURL="/assets/develop/data-generation/block-model/pipe_block_textures.zip">Pipe Block</DownloadEntry>

### Block Texture Pool {#block-texture-pool}

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-normal](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Another useful method is `registerCubeAllModelTexturePool`: define the textures by passing in the "base block", and then append the "children", which will have the same textures.
In this case, we passed in the `RUBY_BLOCK`, so the stairs, slab and fence will use the `RUBY_BLOCK` texture.

::: warning
It will also generate a [simple cube all JSON model](#simple-cube-all) for the "base block" to ensure that it has a block model.

Be aware of this, if you're changing block model of this particular block, as it will result in en error.
:::

You can also append a `BlockFamily`, which will generate models for all of its "children".

@[code lang=java transcludeWith=:::datagen-model:family-declaration](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-family](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_block_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_block.png">Ruby Block</DownloadEntry>

### Doors and Trapdoors {#doors-and-trapdoors}

@[code lang=java transcludeWith=:::datagen-model:door-and-trapdoor](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Doors and trapdoors are a little different. Here, you have to make three new textures - two for the door, and one for the trapdoor.

1. The door:
    - It has two parts - the upper half and the lower half. **Each needs its own texture:** in this case `ruby_door_top` for the upper half and `ruby_door_bottom` for the lower.
    - The `registerDoor()` method will create models for all orientations of the door, both open and closed.
    - **You also need an item texture!** Put it in `assets/<mod_id>/textures/item/` folder.
2. The trapdoor:
   - Here, you need only one texture, in this case named `ruby_trapdoor`. It will be used for all sides.
   - Since `TrapdoorBlock` has a `FACING` property, you can use the commented out method to generate model files with rotated textures = the trapdoor will be "orientable". Otherwise, it will look the same no matter the direction it's facing.

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_textures.zip">Ruby Door and Trapdoor</DownloadEntry>

## Custom Block Models {#custom-block-models}

In this section, we'll create the models for a Vertical Oak Log Slab, with Oak Log textures.

_Points 2. - 6. are declared in an inner static helper class called `CustomBlockStateModelGenerator`._

### Custom Block Class {#custom-block-class}

Create a `VerticalSlab` block with a `FACING` property and a `SINGLE` boolean property, like in the [Block States](../blocks/blockstates) tutorial. `SINGLE` will indicate if there are both slabs.
Then you should override `getOutlineShape` and `getCollisionShape`, so that the outline is rendered correctly, and the block has the correct collision shape.

@[code lang=java transcludeWith=:::datagen-model-custom:voxels](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

@[code lang=java transcludeWith=:::datagen-model-custom:collision](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Also override the `canReplace()` method, otherwise you couldn't make the slab a full block.

@[code lang=java transcludeWith=:::datagen-model-custom:replace](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

And you're done! You can now test the block out and place it in game.

### Parent Block Model {#parent-block-model}

Now, let's create a parent block model. It will determine the size, position in hand or other slots and the `x` and `y` coordinates of the texture.
It's recommended to use an editor such as [Blockbench](https://www.blockbench.net/) for this, as making it manually is a really tedious process. It should look something like this:

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/vertical_slab.json)

See [how blockstates are formatted](https://minecraft.wiki/w/Blockstates_definition_format) for more information.
Notice the `#bottom`, `#top`, `#side` keywords. They act as variables that can be set by models that have this one as a parent:

```json
{
  "parent": "minecraft:block/cube_bottom_top",
  "textures": {
    "bottom": "minecraft:block/sandstone_bottom",
    "side": "minecraft:block/sandstone",
    "top": "minecraft:block/sandstone_top"
  }
}
```

The `bottom` value will replace the `#bottom` placeholder and so on. **Put it in the `resources/assets/mod_id/models/block/` folder.**

### Custom Model {#custom-model}

Another thing we will need is an instance of the `Model` class. It will represent the actual [parent block model](#parent-block-model) inside our mod.

@[code lang=java transcludeWith=:::datagen-model-custom:model](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

The `block()` method creates a new `Model`, pointing to the `vertical_slab.json` file inside the `resources/assets/mod_id/models/block/` folder.
The `TextureKey`s represent the "placeholders" (`#bottom`, `#top`, ...) as an Object.

### Using Texture Map {#using-texture-map}

What does `TextureMap` do? It actually provides the Identifiers that point to the textures. It technically behaves like a normal map - you associate a `TextureKey` (Key) with an `Identifier` (Value).

You can either use the vanilla ones, like `TextureMap.all()`(which associates all TextureKeys with the same Identifier), or create a new one, by creating a new instance and then using `.put()` to associate keys with values.

::: tip
`TextureMap.all()` associates all TextureKeys with the same Identifier, no matter how many of them there are!
:::

Since we want to use the Oak Log textures, but have the ``BOTTOM``, ``TOP`` and ``SIDE`` ``TextureKey``s, we need to create a new one.

@[code lang=java transcludeWith=:::datagen-model-custom:texture-map](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

The ``bottom`` and ``top`` faces will use `oak_log_top.png`, the sides will use `oak_log.png`.

::: warning
All `TextureKey`s in the TextureMap **have to** match all `TextureKey`s in your parent block model!
:::

### Custom `BlockStateSupplier` Method {#custom-supplier-method}

The `BlockStateSupplier` contains all blockstate variants, their rotation, and other options like uvlock.

@[code lang=java transcludeWith=:::datagen-model-custom:supplier](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

First, we create a new `VariantsBlockStateSupplier` using `VariantsBlockStateSupplier.create()`.
Then we create a new `BlockStateVariantMap` that contains parameters for all variants of the block, in this case `FACING` and `SINGLE`, and pass it into the `VariantsBlockStateSupplier`.
Specify which model and which transformations (uvlock, rotation) is used when using `.register()`.
For example:

- On the first line, the block is facing north, and is single => we use the model with no rotation.
- On the fourth line, the block is facing west, and is single => we rotate the model on the Y axis by 270°.
- On the sixth line, the block is facing east, but isn't single => it looks like a normal oak log => we don't have to rotate it.

### Custom Datagen Method {#custom-datagen-method}

The last step - creating an actual method you can call and that will generate the JSONs.
But what are the parameters for?

1. `BlockStateModelGenerator generator`, the same one that got passed into `generateBlockStateModels`.
2. `Block vertSlabBlock` is the block to which we will generate the JSONs.
3. `Block fullBlock` - is the model used when the `SINGLE` property is false = the slab block looks like a full block.
4. `TextureMap textures` defines the actual textures the model uses. See the [Using Texture Map](#using-texture-map) chapter.

@[code lang=java transcludeWith=:::datagen-model-custom:gen](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

First, we get the `Identifier` of the single slab model with `VERTICAL_SLAB.upload()`. Then we get the `Identifier` of the full block model with `ModelIds.getBlockModelId()`, and pass those two models into `createVerticalSlabBlockStates`.
The `BlockStateSupplier` gets passed into the `blockStateCollector`, so that the JSON files are actually generated.
Also, we create a model for the vertical slab item with `BlockStateModelGenerator.registerParentedItemModel()`.

And that is all! Now all that's left to do is to call our method in our `ModelProvider`:

@[code lang=java transcludeWith=:::datagen-model-custom:method-call](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

## Sources and Links {#sources-and-links}

You can view the example tests in [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.4/fabric-data-generation-api-v1/src/) and this documentation's [Reference Mod](https://github.com/FabricMC/fabric-docs/tree/main/reference) for more information.

You can also find more examples of using custom datagen methods by browsing mods' open-source code, for example [Vanilla+ Blocks](https://github.com/Fellteros/vanillablocksplus) and [Vanilla+ Verticals](https://github.com/Fellteros/vanillavsplus) by Fellteros.
