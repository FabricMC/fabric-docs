---
title: 创建你的第一个方块
description: 学习如何在 Minecraft 中创建你的第一个自定义方块。
authors:
  - Earthcomputer
  - IMB11
  - its-miroma
  - xEobardThawne
---

方块是构成 Minecraft 世界的主要组成部分——和 Minecraft 的其他一切一样，是储存在注册表中的。

## 准备你的方块类 {#preparing-your-blocks-class}

如果你已经完成了[创建你的第一个物品](../items/first-item)，那么这一过程会非常熟悉——你会需要创建一个注册方块以及方块物品的方法。

你应该把这个方块放在叫做 `ModBlocks` 的类中（也可以是其他你想要的名称）。

Mojang 对原版方块的处理方法和这个也非常相似，你可以参考 `Blocks` 类看看他们是怎么做的。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

像物品一样，你需要确保类被加载，这样所有包含方块实体的静态字段都会初始化。

你可以添加一个 `initialize` 方法，并在模组的[初始化](./getting-started/project-structure#entrypoints)中调用以进行静态初始化。

:::info
如果不知道什么是静态初始化，那么这里说下，这是初始化类中的所有静态字段的过程。 JVM 加载类时，以及创建类的任何实例之前，都会完成这一过程。
:::

```java
public class ModBlocks {
    // ...

    public static void initialize() {}
}
```

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/FabricDocsReferenceBlocks.java)

## 创建并注册你的方块 {#creating-and-registering-your-block}

和物品类似，方块会在构造函数中接收一个 `Block.Settings` 类，指定了方块的属性，例如其声音效果和挖掘等级。

我们不会在这里介绍所有的选项：你可以自己查看类来了解各种选项，应该是不言自明的。

这里为作举例，我们会创建一个拥有和泥土的相同属性但材料不同的方块。

- 我们以与在物品教程中创建物品设置类似的方式创建方块设置。
- 我们通过调用 `Block` 构造函数来告诉 `register` 方法从方块设置中创建一个 `Block` 实例。

:::tip
可以使用 `AbstractBlock.Settings.copy(AbstractBlock block)` 从已存在的方块中复制 settings，这种情况下，可以使用 `Blocks.DIRT` 以从泥土中复制 settings，但是为作举例，我们使用 builder。
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

我们上一步创建过 `register` 方法，要自动创建方块物品，我们在方法的 `shouldRegisterItem` 参数中传入 `true`。

### 将方块的物品添加到物品组中 {#adding-your-block-s-item-to-an-item-group}

由于 `BlockItem` 是自动创建和注册的，要将其添加到物品组中，必须使用 `Block.asItem()` 方法来获得 `BlockItem` 实例。

例如，我们使用在[自定义物品组](../items/custom-item-groups)页面中创建的自定义物品组。

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

你应将其置于类的 `initialize()` 函数中。

你应该注意到，你的方块现在在创造模式物品栏中，并且可以放在世界中！

![世界内没有模型和纹理的方块](/assets/develop/blocks/first_block_0.png)

但是还有点问题——方块物品没有命名，方块没有纹理、方块模型和物品模型。

## 添加方块翻译 {#adding-block-translations}

要添加翻译，必须在你的翻译文件——`assets/mod-id/lang/en_us.json` 中创建翻译键。（类似地，中文翻译可添加到 `assets/mod-id/lang/zh_cn.json`。）

Minecraft 会在创造模式物品栏中，以及其他显示方块名称的地方（例如命令反馈）中显示这个翻译。

```json
{
  "block.mod_id.condensed_dirt": "Condensed Dirt"
}
```

你可以重启游戏，或者构建你的模组，然后在游戏里按 <kbd>F3</kbd> + <kbd>T</kbd> 以重新加载资源文件——你将会看到方块在创造模式物品栏里或者其他地方（例如统计屏幕）中有个名字了。

## 模型和纹理 {#models-and-textures}

所有方块纹理都可以在 `assets/mod-id/textures/block` 文件夹中找到——“Condensed Dirt”方块的示例纹理可以自由使用。

<DownloadEntry visualURL="/assets/develop/blocks/first_block_1.png" downloadURL="/assets/develop/blocks/first_block_1_small.png">纹理</DownloadEntry>

要在游戏中显示纹理，必须创建一个方块模型，该模型可在 `assets/mod-id/models/block/condensed_dirt.json` 文件中的 "Condensed Dirt" 方块中找到。 对于这个方块，我们将使用 `block/cube_all` 模型类型。

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/block/condensed_dirt.json)

为了让方块显示在物品栏中，您需要创建一个指向方块模型的[物品模型描述](../items/first-item#creating-the-item-model-description)。 在本例中，"Condensed Dirt" 方块的项目模型描述可在 `assets/mod-id/items/condensed_dirt.json` 中找到。

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/items/condensed_dirt.json)

:::tip
只有在注册方块的同时注册了 `BlockItem` 时，才需要创建项目模型描述！
:::

载入游戏，你可能会发现模型还是缺失。 这是因为，你还需要添加方块状态定义。

## 创建方块状态定义 {#creating-the-block-state-definition}

方块状态定义用于指示游戏基于当前方块的状态要渲染哪个模型。

示例方块没有复杂的方块状态，只需要定义一项。

这个方块应该位于 `assets/mod-id/blockstates` 文件夹内，名字应该匹配在 `ModBlocks` 类中注册方块时使用的方块 ID。 例如，方块 ID 是 `condensed_dirt`，那么文件名称就是 `condensed_dirt.json`。

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/blockstates/condensed_dirt.json)

:::tip
方块状态非常复杂，因此接下来将在 [单独页面](./blockstates) 中介绍。
:::

重启游戏，或者按下<kbd>F3</kbd> + <kbd>T</kbd>重新加载资源文件以应用更改——你应该能看到方块在物品栏内的纹理，以及在世界中呈现：

![世界内有适当的纹理和模型的方块](/assets/develop/blocks/first_block_4.png)

## 添加方块掉落物 {#adding-block-drops}

在生存模式下破坏方块时，你可能看到方块不会掉落——你可能想要这个功能，但是要让方块被破坏时掉落为物品，必须要实现其战利品表——战利品表文件应置于 `data/mod-id/loot_table/blocks/` 文件夹中。

:::info
对战利品表的更深入理解，可参考 [Minecraft Wiki - 战利品表](https://zh.minecraft.wiki/w/战利品表)页面。
:::

@[code](@/reference/latest/src/main/resources/data/fabric-docs-reference/loot_tables/blocks/condensed_dirt.json)

这个战利品表提供了方块在被破坏以及被爆炸破坏时掉落的单个方块物品。

## 推荐挖掘工具 {#recommending-a-harvesting-tool}

你可能也想要让方块只能被特定类型的方块挖掘——例如，可能想让你的方块用锹挖掘更快。

所有的工具标签都位于 `data/minecraft/tags/block/mineable/` 文件夹内——其中文件的名称取决于使用的工具的类型，是以下之一：

- `hoe.json`（锄）
- `axe.json`（斧）
- `pickaxe.json`（镐）
- `shovel.json`（锹）

文件的内容很简单，是要添加到标签中的物品的列表。

这个例子会将“Condensed Dirt”方块添加到 `shovel` 标签中。

@[code](@/reference/latest/src/main/resources/data/minecraft/tags/mineable/shovel.json)

如果你希望玩家需要使用工具来挖掘这个方块，则需要在方块设置中添加 `.requiresTool()`，并添加相应的挖掘等级标签。

## 挖掘等级 {#mining-levels}

类似地，`data/minecraft/tags/block/` 文件夹内也可以找到挖掘等级，并遵循以下格式：

- `needs_stone_tool.json` - 最低需要石质工具
- `needs_iron_tool.json` - 最低需要铁质工具
- `needs_diamond_tool.json` - 最低需要钻石工具

文件与挖掘工具文件的格式相同——要添加到标签中的物品的列表。

## 备注 {#extra-notes}

如果将多个方块添加到你的模组中，可能需要考虑使用[数据生成](../data-generation/setup)来自动化创建方块和物品模型、方块状态定义和战利品表。
