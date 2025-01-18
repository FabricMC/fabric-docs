---
title: Block Model Generation
description: A guide to generating block models & blockstates via datagen.
authors:
  - Fellteros
---

# Block Model Generation {#model-generation}

::: info PREREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.
:::

## Setup {#setup}

First, we will need to create our ModelProvider. Create a plain Java class that `extends FabricModelProvider`. Implement both abstract methods, `generateBlockStateModels` & `generateItemModels`.
Lastly, create constructor matching super.

:::tip
If you're developing mods in JetBrains' Intellij Idea, you can press `Alt+Shift+Enter` while hovering over the problem to bring up a quick fix menu!
:::

@[code lang=java transcludeWith=:::datagen-model:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Register this class in your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

## Blockstates and block models {#blockstates-and-block-models}

::: info PREREQUISITES
You'll have to register the blocks, to which you want the data to be generated. Also see the [blockstate](../blocks/blockstates.md) and [first block](../blocks/first-block.md) tutorials.
:::

```java
@Override
public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
}
```

This part of the tutorial will happen entirely in the `generateBlockStateModels` method. Notice the parameter `BlockStateModelGenerator blockStateModelGenerator` - this class will be responsible for generating all the JSON files.
Here are some handy examples you can use to generate your desired models:

### Simple Cube All {#simple-cube-all}

@[code lang=java transcludeWith=:::datagen-model:cube-all](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

The easiest and most basic method there is. It generates a JSON model file for a normal cubic block. It provides the same texture (named `steel_block`) for all six sides.

```json
{
  "parent": "minecraft:block/cube_all",
  "textures": {
    "all": "fabric-docs-reference:block/steel_block"
  }
}
```

It also generates a blockstate JSON file. Since we have no blockstates (e.g. Axis, Facing, ...), one variant is sufficient, and is used everytime the block is placed.

```json
{
  "variants": {
    "": {
      "model": "fabric-docs-reference:block/steel_block"
    }
  }
}
```

### Singletons {#singletons}

The `registerSingleton` method provides JSON model files based on the `TexturedModel` you pass in and a single blockstate variant. For example

@[code lang=java transcludeWith=:::datagen-model:cube-top-for-ends](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

will generate models for a normal cube, that uses a PNG file `pipe_block` for the sides and a PNG file `pipe_block_top` for the top and bottom sides.

```json
{
  "parent": "minecraft:block/cube_column",
  "textures": {
    "end": "fabric-docs-reference:block/pipe_block_top",
    "side": "fabric-docs-reference:block/pipe_block"
  }
}
```

:::tip
If you're stuck choosing which TextureModel you should use, open the `TexturedModel` class and look at the `TextureMaps`!
:::

### Block Texture Pool {#block-texture-pool}

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-normal](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Another useful method is `registerCubeAllModelTexturePool`. Define the textures by passing in the "base block", and then append the "children", which will have the same textures. <br>
In this case, we passed in the `RUBY_BLOCK`, so the stairs, slab and fence will use the `RUBY_BLOCK` texture.
***It will also generate a [simple cube all JSON model](#simple-cube-all) for the "base block" to ensure the texture provision!*** <br>

You can also append a `BlockFamily`, which will generate models for all of its "children".

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-family](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)



::: warning
Make sure you're using the correct Block class when passing in the block!
For example, in the `.stairs()` method, you have to pass in an instance of StairsBlock; in `.slab()`, it has to be an instance of SlabBlock and so on! 
:::

### Doors and Trapdoors {#doors-and-trapdoors}

@[code lang=java transcludeWith=:::datagen-model:door-and-trapdoor](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Doors and trapdoors are a little different. Here, you have to make three new textures - two for the door, and one for the trapdoor. <br>

1. **The door**. It has two parts - the upper half and the lower half. **Each needs its own texture:** in this case `ruby_door_top` for the upper half and `ruby_door_bottom` for the lower.
8 new model JSONs should be created. <br>
**You also need an item texture!** Put it in `assets/<mod_id>/textures/item/` folder.
2. **The trapdoor**. Here, you need only one texture, in this case named `ruby_trapdoor`. It will be used for all sides.
Since `TrapdoorBlock` has a `FACING` property, you can use the commented out method to generate model files with rotated textures = the trapdoor will be "orientable".
Otherwise, it will look the same no matter the facing.

::: warning
Again, make sure the door and trapdoor blocks are instances of their corresponding block classes (`DoorBlock`, `TrapdoorBlock`)!
:::

## Custom Block Models and Datagen Methods {#custom-models-and-methods}

In this tutorial, we will be creating a method, that will generate block models and blockstate for a custom block. <br>
In this case it will be a **vertical slab block** with Oak Log textures => _Vertical Oak Log Slab_.

::: info THINGS WE WILL NEED
1. [Custom Block Class](#custom-block-class)
2. [Parent Block Model](#parent-block-model)
3. [Custom Model](#custom-model)
4. [Using Texture Map](#using-texture-map)
5. [Custom BlockStateSupplier Method](#custom-supplier-method)
6. [Custom Datagen Method](#custom-datagen-method)
:::

### Custom Block Class {#custom-block-class}

First, create a plain Java class that `extends Block`. Then create constructor matching super.

@[code lang=java transcludeWith=:::datagen-model-custom:constructor](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Next, create two properties:
1. `BooleanProperty` SINGLE, indicating if the vertical slab is single or not. 
2. `EnumProperty<Direction>` FACING, providing a direction in which the block is facing.

@[code lang=java transcludeWith=:::datagen-model-custom:properties](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Determine four `VoxelShape`s, one for each direction, as VoxelShapes cannot be rotated.

@[code lang=java transcludeWith=:::datagen-model-custom:voxels](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Override the `getSidesShape()` method to determine the voxel shape used. Use switch statement and pass in the `direction` variable.
Then call this method in the `getCollisionShape()` & `getOutlineShape()` methods.

@[code lang=java transcludeWith=:::datagen-model-custom:collision](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Override the `canReplace()` method, otherwise you couldn't make the slab a full block.

@[code lang=java transcludeWith=:::datagen-model-custom:replace](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Override the `getPlacementState()` method to determine placement blockstate.

@[code lang=java transcludeWith=:::datagen-model-custom:placement](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Lastly, override the `appendProperties()` method and add both properties to the builder.

@[code lang=java transcludeWith=:::datagen-model-custom:append](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

And you're done! You can now create a vertical slab block and place it in the game!

### Parent Block Model {#parent-block-model}

Now, let's create a parent block model. It will determine the size, position in hand or other slots and the `x` and `y` coordinates of the texture.
I highly recommend using [Blockbench](https://www.blockbench.net/) for this, as making it manually is a really tedious process. It should look something like this:

```json
{
  "parent": "minecraft:block/block",
  "textures": {
    "particle": "#side"
  },
  "display": {
    "gui": {
      "rotation": [ 30, -135, 0 ],
      "translation": [ -1.5, 0.75, 0],
      "scale":[ 0.625, 0.625, 0.625 ]
    },
    "firstperson_righthand": {
      "rotation": [ 0, -45, 0 ],
      "translation": [ 0, 2, 0],
      "scale":[ 0.375, 0.375, 0.375 ]
    },
    "firstperson_lefthand": {
      "rotation": [ 0, 315, 0 ],
      "translation": [ 0, 2, 0],
      "scale":[ 0.375, 0.375, 0.375 ]
    },
    "thirdperson_righthand": {
      "rotation": [ 75, -45, 0 ],
      "translation": [ 0, 0, 2],
      "scale":[ 0.375, 0.375, 0.375 ]
    },
    "thirdperson_lefthand": {
      "rotation": [ 75, 315, 0 ],
      "translation": [ 0, 0, 2],
      "scale":[ 0.375, 0.375, 0.375 ]
    }
  },
  "elements": [
    {   "from": [ 0, 0, 0 ],
      "to": [ 16, 16, 8 ],
      "faces": {
        "down":  { "uv": [ 0, 8, 16, 16 ], "texture": "#bottom", "cullface": "down", "tintindex": 0 },
        "up":    { "uv": [ 0, 0, 16, 8 ], "texture": "#top",    "cullface": "up", "tintindex": 0 },
        "north": { "uv": [ 0, 0, 16, 16 ], "texture": "#side",   "cullface": "north", "tintindex": 0 },
        "south": { "uv": [ 0, 0, 16, 16 ], "texture": "#side", "tintindex": 0 },
        "west":  { "uv": [ 0, 0, 8, 16 ], "texture": "#side",   "cullface": "west", "tintindex": 0 },
        "east":  { "uv": [ 8, 0, 16, 16 ], "texture": "#side",   "cullface": "east", "tintindex": 0 }
      }
    }
  ]
}
```

See [how blockstates are formatted](https://minecraft.wiki/w/Blockstates_definition_format) for more information. <br>
Notice the `#bottom`, `#top`, `#side` keywords. They act as a kind of "placeholders", that will get replaced by `Identifier`s in the generated JSON file.
Something like in sandstone's model file:
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

`#bottom` gets "replaced" by the `"bottom"` and so on. **Put it in the `resources/assets/<mod_id>/models/block/` folder.**

### Custom Model {#custom-model}

Another thing we will need is an instance of the `Model` class. It will represent the actual [parent block model](#parent-block-model) inside our mod.

@[code lang=java transcludeWith=:::datagen-model-custom:model](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

The `block()` method creates a new `Model`, pointing to the `vertical_slab.json` file inside the `resources/assets/<mod_id>/models/block/` folder.
The `TextureKey`s represent the "placeholders" (`#bottom`, `#top`, ...) as an Object.

### Using Texture Map {#using-texture-map}

What does `TextureMap` do? It actually provides the Identifiers that point to the textures. It technically behaves like a normal map - you associate a `TextureKey` (Key) with an `Identifier` (Value).
You can:
1. Use the Vanilla ones, e.g. `TextureMap.all()`(associates all TextureKeys with the same Identifier) or other.
2. Create a new one by creating a new instance and then using `.put()` to associate keys with values.

::: tip
`TextureMap.all()` associates all TextureKeys with the same Identifier, no matter how many of them there are!
:::

Since we want to use the Oak Log textures, but have the ``BOTTOM``, ``TOP`` and ``SIDE`` ``TextureKey``s, we need to create a new one.

@[code lang=java transcludeWith=:::datagen-model-custom:texture-map](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

=> the ``bottom`` and ``top`` faces will use `oak_log_top.png`, the sides will use `oak_log.png`.

::: warning
All `TextureKey`s in the TextureMap **have to** match all `TextureKey`s in your parent block model! 
:::

### Custom BlockStateSupplier Method {#custom-supplier-method}

The `BlockStateSupplier` contains all blockstate variants, their rotation, and other options like uvlock.

@[code lang=java transcludeWith=:::datagen-model-custom:supplier](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

First, we create a new `VariantsBlockStateSupplier` using `.create()`. Then append `.coordinate()`, in which create a new `BlockStateVariantMap`. Here, pass in all of our properties - in this case `FACING` & `SINGLE`.
Specify which model and which transformations (uvlock, rotation) is used when using `.register()`. <br>
For example: 
* On the first line, the block is facing north, and is single => we use the model with no rotation.
* On the fourth line, the block is facing west, and is single => we rotate the model on the Y axis by 270°.
* On the sixth line, the block is facing east, but isn't single => it looks like a normal oak log => we don't have to rotate it.

### Custom Datagen Method {#custom-datagen-method}

The last step - creating an actual method you can call and that will generate the JSONs.
But what are the parameters for?
1. `BlockStateModelGenerator bsmg` is used for its variables. **Creating new ones won't work!**
2. `Block vertSlabBlock` is the block to which we will generate the JSONs.
3. `Block fullBlock` - is the model used when the `SINGLE` property is false = the slab block looks like a full block.
4. `TextureMap textures` defines the actual textures the model uses. See the [Using Texture Map](#using-texture-map) chapter.

@[code lang=java transcludeWith=:::datagen-model-custom:gen](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Let's go over line by line to really understand what is going on:
1. Here we obtain the `Identifier` representing the vertical slab model via the `.upload()` method. We will pass in our [TextureMap](#using-texture-map) when calling this method.
2. Here we obtain the `Identifier` of the full block - in our case of Oak Log.
3. Here the `Consumer blockStateCollector` is called, accepting all of our blockstate variants.
4. Here we register an item model for the vertical slab.

And that is all! Now all that's left to do is to call our method in our `ModelProvider`:

@[code lang=java transcludeWith=:::datagen-model-custom:method-call](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

## Sources and links {#sources-and-links}

Other examples of implementing custom datagen methods can be found [here](https://github.com/Fellteros/vanillablocksplus) and [here](https://github.com/Fellteros/vanillavsplus)
Textures, free to download:
<DownloadEntry visualURL="/assets/develop/data-generation/block-model/atlas.png" downloadURL="/assets/develop/data-generation/block-model/atlas.png">Atlas of all textures</DownloadEntry>

