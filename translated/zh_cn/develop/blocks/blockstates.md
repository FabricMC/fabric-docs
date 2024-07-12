---
title: 方块状态
description: 学习为什么方块状态是一个向你的方块添加可视化功能的好方法。
authors:
  - IMB11
---

# 方块状态{#block-states}

方块状态是附加到 Minecraft 世界中的单个方块上的一段数据，包含属性形式的方块块信息——原版存储在方块状态中的属性的一些示例：

- Rotation：主要用于原木方块和其他自然方块中。
- Activated：主要用于红石装置方块和类似于熔炉、烟熏炉的方块中。
- Age：用于农作物、植物、树苗、海带等方块中使用。

你可能看出了为什么方块状态有用——避免了在方块实体中存储 NBT 数据的需要——这既减小了世界大小，也防止产生 TPS 问题！

方块状态的定义能在 `assets/<mod id here>/blockstates` 文件夹中找到。

## 示例：柱方块{#pillar-block}

<!-- Note: This example could be used for a custom recipe types guide, a condensor machine block with a custom "Condensing" recipe? -->

Minecraft 已经有些自定义的类，允许你快速创建特定类型的方块——这个例子会通过创建“Condensed Oak Log”方块来带你创建带有 `axis` 属性的方块。

原版的 `PillarBlock` 允许方块按 X、Y 或 Z 轴放置。

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

柱方块有两个纹理，顶部（`top`）和侧面（`side`），使用 `block/cube_column` 模型。

同样，纹理文件可以在 `assets/<mod id here>/textures/block` 中找到。

<DownloadEntry type="Textures" visualURL="/assets/develop/blocks/blockstates_0_large.png" downloadURL="/assets/develop/blocks/condensed_oak_log_textures.zip" />

由于柱方块有两个位置，水平和垂直，我们需要创建两个单独的模型文件：

- `condensed_oak_log_horizontal.json`，继承 `block/cube_column_horizontal` 模型。
- `condensed_oak_log.json`，继承 `block/cube_column` 模型。

`condensed_oak_log_horizontal.json` 文件的示例：

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/condensed_oak_log_horizontal.json)

---

::: info
Remember, blockstate files can be found in the `assets/<mod id here>/blockstates` folder, the name of the blockstate file should match the block ID used when registering your block in the `ModBlocks` class. For instance, if the block ID is `condensed_oak_log`, the file should be named `condensed_oak_log.json`.

更加深入了解方块状态文件中可用的所有修饰器，可看看 [Minecraft Wiki - 模型（方块状态）](https://zh.minecraft.wiki/w/Tutorial:模型/方块状态)页面。
:::

然后，我们需要创建方块状态文件。 方块状态文件就是魔法发生的地方——柱方块有三个轴，所以我们使用以下情形中的特定模型：

- `axis=x` - 方块沿 X 轴放置时，旋转模型以朝向正 X 方向。
- `axis=y` - 方块沿 Y 轴旋转时，使用正常的垂直模型。
- `axis=z` - 方块沿Z 轴放置时，旋转模型以朝向正 X 方向。

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/condensed_oak_log.json)

同样，需要为你的方块创建翻译，以及继承了这两个模型中的任意一个的物品模型。

![游戏内的柱方块的示例](/assets/develop/blocks/blockstates_1.png)

## 自定义方块状态{#custom-block-states}

如果你的方块有独特的属性，那么自定义方块状态会非常不错——有时你会发现你的方块可以复用原版的属性。

这个例子会创建一个叫做 `activated` 的独特属性——玩家右键单击方块时，方块会由 `activated=false` 变成 `activated-true` 并相应改变纹理。

### 创建属性{#creating-the-property}

首先，需要创建属性本身——因为是个布尔值，所以使用 `BooleanProperty.of` 方法。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

然后，需要在 `appendProperties` 方法中将属性添加到 blockstate manager\` 中。 需要覆盖此方法以访问 builder：

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

你还需要在你的自定义方块的构造函数中，设置 `activated` 属性的默认状态。

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

:::warning
别忘了注册方块时使用自定义的类而不是 `Block`！
:::

### 使用属性{#using-the-property}

这个例子会在玩家与方块交互时，翻转 `activated` 属性的布尔值。 我们可以为此覆盖 `onUse` 方法：

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### 视觉呈现属性{#visualizing-the-property}

创建方块状态前，我们需要为方块的激活的和未激活的状态都提供纹理，以及方块模型。

<DownloadEntry type="Textures" visualURL="/assets/develop/blocks/blockstates_2_large.png" downloadURL="/assets/develop/blocks/prismarine_lamp_textures.zip" />

用你的方块模型知识，创建方块的两个模型：一个用于激活的状态，一个用于未激活的状态。 完成后，就可以开始创建方块状态文件了。

因为创建了新的属性，所以需要为方块更新方块状态文件以使用那个属性。

如果方块有多个属性，那么会需要包含所有可能的组合。 例如，`activated` 和 `axis` 可能就会导致 6 个组合（`activated` 有两个可能的值，`axis` 有三个可能的值）。

因为方块只有一个属性（`activated`），只有两个变种，所以方块状态 JSON 看起来应该像这样：

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/prismarine_lamp.json)

---

因为这个示例方块是灯，所以还需要让它在 `activated` 属性为 true 时发光。 可以通过在注册方块时传入构造器的 block settings 来完成。

可以使用 `luminance` 方法设置方块放出的光，可以在 `PrismarineLampBlock` 类中创建一个静态方法，从而根据 `activated` 属性返回光照等级，并将其作为方法引入传入 `luminance` 方法中。

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

<!-- Note: This block can be a great starter for a redstone block interactivity page, maybe triggering the blockstate based on redstone input? -->

一切完成后，最终的结果应该看起来像这样：

<VideoPlayer src="/assets/develop/blocks/blockstates_3.webm" title="Prismarine Lamp Block in-game" />
