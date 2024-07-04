---
title: 创建你的第一个方块
description: 学习如何在 Minecraft 中创建你的第一个自定义的方块。
authors:
  - IMB11
---

# 创建你的第一个方块{#creating-your-first-block}

方块是构成 Minecraft 世界的主要组成部分——和 Minecraft 的其他一切一样，是储存在注册表中的。

## 准备你的 Blocks 类{#preparing-your-blocks-class}

如果你已经完成了[创建你的第一个物品](../items/first-item)，那么这一过程会非常熟悉——你会需要创建一个注册方块以及方块物品的方法。

你应该把这个方块放在叫做 `ModBlocks` 的类中（也可以是其他你想要的名称）。

Mojang 对原版方块的处理方法和这个也非常相似，你可以参考 `Blocks` 类看看他们是怎么做的。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

像物品一样，你需要确保类被加载，这样所有包含方块实体的静态字段都会初始化。

要做到这样，你可以创建占位的 `initialize` 方法，并在模组初始化器中调用以触发静态初始化。

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

## 创建并注册你的方块{#creating-and-registering-your-block}

和物品类似，方块会在构造函数中接收一个 `Block.Settings` 类，指定了方块的属性，例如其声音效果和挖掘等级。

我们不会在这里提到所有选项——可以查看类本身来看看各种选项，应该能解释清楚的。

这里为作举例，我们会创建一个拥有和泥土的相同属性但材料不同的方块。

:::tip
可以使用 `AbstractBlock.Settings.copy(AbstractBlock block)` 从已存在的方块中复制 settings，这种情况下，可以使用 `Blocks.DIRT` 以从泥土中复制 settings，但是为作举例，我们使用 builder。
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

我们上一步创建过 `regisger` 方法，要自动创建方块物品，我们在方法的 `shouldRegisterItem` 参数中传入 `true`。

### 将方块添加到物品组{#adding-your-block-to-an-item-group}

由于 `BlockItem` 是自动创建和注册的，要将其添加到物品组中，必须使用 `Block.asItem()` 方法来获得 `BlockItem` 实例。

例如，我们使用在[自定义物品组](../items/custom-item-groups)页面中创建的自定义物品组。

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

你应该注意到，你的方块现在在创造模式物品栏中，并且可以放在世界中！

![世界内没有模型和纹理的方块](/assets/develop/blocks/first_block_0.png)

但是还有点问题——方块物品没有命名，方块没有纹理、方块模型和物品模型。

## 添加方块翻译{#adding-block-translations}

要添加翻译，必须在你的翻译文件——`assets/<mod id here>/lang/en_us.json` 中创建翻译键。（类似地，中文翻译可添加到 `assets/<0>/lang/zh_cn.json`。）

Minecraft 会在创造模式物品栏中，以及其他显示方块名称的地方（例如命令反馈）中显示这个翻译。

```json
{
    "block.mod_id.condensed_dirt": "Condensed Dirt"
}
```

你可以重启游戏，或者构建你的模组然后按 <kbd>F3</kbd> + <kbd>T</kbd> 以应用更改——你应该看到方块在创造模式物品栏中以及其他地方（例如统计屏幕）中有个名字了。

## 模型和纹理{#models-and-textures}

所有方块纹理都可以在 `assets/<mod id here>/textures/block` 文件夹中找到——“Condensed Dirt”方块的示例纹理可以自由使用。

<DownloadEntry type="Texture" visualURL="/assets/develop/blocks/first_block_1.png" downloadURL="/assets/develop/blocks/first_block_1_small.png" />

要确保模型在游戏内显示，必须创建方块和物品模型，“Condensed Dirt”方块的方块和物品模型分别可以在下列地方找到：

- `assets/<mod id here>/models/block/condensed_dirt.json`
- `assets/<mod id here>/models/item/condensed_dirt.json`

物品模型很简单，只需要继承方块模型即可，因为大多数方块模型都支持在 GUI 中渲染。

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/condensed_dirt.json)

但是，在我们的例子中，方块模型就必须继承 `block/cube_all` 模型。

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/condensed_dirt.json)

载入游戏，你可能会发现模型还是缺失。 这是因为，你还需要添加方块状态定义。

## 创建方块状态定义{#creating-the-block-state-definition}

方块状态定义用于指示游戏基于当前方块的状态要渲染哪个模型。

示例方块没有复杂的方块状态，只需要定义一项。

这个方块应该位于 `assets/mod_id/blockstates` 文件夹内，名字应该匹配在 `ModBlocks` 类中注册方块时使用的方块 ID。 例如，方块 ID 是 `condensed_dirt`，那么文件名称就是 `condensed_dirt.json`。

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/condensed_dirt.json)

方块状态很复杂，会在之后的页面[方块状态](./blockstates)中详述。

重启游戏，或者通过<kbd>F3</kbd> + <kbd>T</kbd>重新加载以应用更改——你应该能够看到方块在物品栏内的纹理以及在世界中呈现：

![世界内有适当的纹理和模型的方块](/assets/develop/blocks/first_block_4.png)

## 添加方块掉落物{#adding-block-drops}

在生存模式下破坏方块时，你可能看到方块不会掉落——你可能想要这个功能，但是要让方块被破坏时掉落为物品，必须要实现其战利品表——战利品表文件应置于 `data/<mod id here>/loot_tables/blocks/` 文件夹中。

:::info
对战利品表的更深入理解，可参考 [Minecraft Wiki - 战利品表](https://zh.minecraft.wiki/w/战利品表)页面。
:::

@[code](@/reference/latest/src/main/resources/data/fabric-docs-reference/loot_tables/blocks/condensed_dirt.json)

这个战利品表提供了方块在被破坏以及被爆炸破坏时掉落的单个方块物品。

## 推荐挖掘工具{#recommending-a-harvesting-tool}

你可能也想要让方块只能被特定类型的方块挖掘——例如，可能想让你的方块用锹挖掘更快。

所有的工具标签都位于 `data/minecraft/tags/mineable/` 文件夹内——其中文件的名称取决于使用的工具的类型，是以下之一：

- `hoe.json`（锄）
- `axe.json`（斧）
- `pickaxe.json`（镐）
- `shovel.json`（锹）

文件的内容很简单，是要添加到标签中的物品的列表。

这个例子会将“Condensed Dirt”方块添加到 `shovel` 标签中。

@[code](@/reference/latest/src/main/resources/data/minecraft/tags/mineable/shovel.json)

## 挖掘等级{#mining-levels}

类似地，相同的文件夹内也可以找到挖掘等级，并遵循以下格式：

- `needs_stone_tool.json` - 最低需要石质工具
- `needs_iron_tool.json` - 最低需要铁质工具
- `needs_diamond_tool.json` - 最低需要钻石工具

文件与挖掘工具文件的格式相同——要添加到标签中的物品的列表。

## 备注{#extra-notes}

如果将多个方块添加到你的模组中，可能需要考虑使用[数据生成](https://fabricmc.net/wiki/tutorial:datagen_setup)来自动化创建方块和物品模型、方块状态定义和战利品表。
