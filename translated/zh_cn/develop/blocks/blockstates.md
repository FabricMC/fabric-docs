---
title: 方块状态
description: 学习为什么方块状态是一个向你的方块添加可视化功能的好方法。
authors:
  - IMB11
---

方块状态是附加到 Minecraft 世界中的单个方块上的一段数据，包含属性形式的方块块信息——原版存储在方块状态中的属性的一些示例：

- Rotation：主要用于原木方块和其他自然方块中。
- Activated：主要用于红石装置方块和类似于熔炉、烟熏炉的方块中。
- Age：用于农作物、植物、树苗、海带等方块中。

你可能会明白为什么方块状态有用——它们避免了在方块实体中存储 NBT 数据的需要——这既减小了世界大小，也防止产生 TPS 问题！

方块状态的定义能在 `assets/example-mod/blockstates` 文件夹中找到。

## 示例：柱方块 {#pillar-block}

<!-- Note: This example could be used for a custom recipe types guide, a condensor machine block with a custom "Condensing" recipe? -->

Minecraft 已经有些自定义的类，允许你快速创建特定类型的方块——这个例子会通过创建“Condensed Oak Log”方块来带你创建带有 `axis` 属性的方块。

原版的 `RotatedPillarBlock` 允许方块按 X、Y 或 Z 轴放置。

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

柱方块有两个纹理，顶部（`top`）和侧面（`side`），使用 `block/cube_column` 模型。

同样，纹理文件可以在 `assets/example-mod/textures/block` 中找到

<DownloadEntry visualURL="/assets/develop/blocks/blockstates_0_large.png" downloadURL="/assets/develop/blocks/condensed_oak_log_textures.zip">纹理</DownloadEntry>

由于柱方块有两个位置，水平和垂直，我们需要创建两个单独的模型文件：

- `condensed_oak_log_horizontal.json`，继承 `block/cube_column_horizontal` 模型。
- `condensed_oak_log.json`，继承 `block/cube_column` 模型。

`condensed_oak_log_horizontal.json` 文件的示例：

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/condensed_oak_log_horizontal.json)

::: info

请记住，方块状态文件可以在 `assets/example-mod/blockstates` 中找到，方块状态文件的名称应当匹配你在 `ModBlocks` 类中注册的方块的 ID。 例如，如果方块 ID 为 `condensed_oak_log`，这个文件就应当叫做 `condensed_oak_log.json`。

如需更加深入了解方块状态文件中可用的所有修饰符，可查看[中文 Minecraft Wiki - 教程:制作资源包/模型（方块状态）](https://zh.minecraft.wiki/w/Tutorial:%E5%88%B6%E4%Bd%9C%E8%B5%84%E6%Ba%90%E5%8C%85/%E6%A8%A1%E5%9E%8B#%E6%96%B9%E5%9D%97%E7%8A%B6%E6%80%81)页面。

:::

接下来，我们需要创建一个方块状态文件，这正是见证奇迹的地方。 柱型方块有三个轴，因此我们将针对以下情况使用特定模型：

- `axis=x` - 方块沿 X 轴放置时，旋转模型以朝向正 X 方向。
- `axis=y` - 方块沿 Y 轴旋转时，使用正常的垂直模型。
- `axis=z` - 方块沿 Z 轴放置时，旋转模型以朝向正 Z 方向。

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/condensed_oak_log.json)

同样，你需要为该方块创建本地化条目，并创建一个物品模型，将其父模型指定为前述两种模型之一。

![游戏内柱方块示例](/assets/develop/blocks/blockstates_1.png)

## 自定义方块状态 {#custom-block-states}

如果你的方块有独特的属性，那么自定义方块状态会非常不错——有时你会发现你的方块可以复用原版的属性。

这个例子会创建一个叫做 `activated` 的独特属性——玩家右键单击方块时，方块会由 `activated=false` 变成 `activated=true` 并相应改变纹理。

### 创建属性{#creating-the-property}

首先，需要创建属性本身——因为是个布尔值，所以使用 `BooleanProperty.create` 方法。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

接下来，需要在 `createBlockStateDefinition` 方法中将该属性添加到方块状态管理器。 需要重写此方法以访问构建器：

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

你还需要在你的自定义方块的构造函数中，设置 `activated` 属性的默认状态。

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### 使用属性{#using-the-property}

这个例子会在玩家与方块交互时，翻转 `activated` 属性的布尔值。 我们可以重写 `useWithoutItem` 方法来实现：

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### 视觉呈现属性 {#visualizing-the-property}

创建方块状态前，我们需要为方块的激活的和未激活的状态都提供纹理，以及方块模型。

<DownloadEntry visualURL="/assets/develop/blocks/blockstates_2_large.png" downloadURL="/assets/develop/blocks/prismarine_lamp_textures.zip">纹理</DownloadEntry>

利用你对方块模型的了解，为该方块创建两个模型：一个用于激活状态，另一个用于未激活状态。 完成后，就可以开始创建方块状态文件了。

既然创建了新的属性，就需要更新该方块的方块状态文件以适配这个属性。

如果方块有多个属性，那么会需要包含所有可能的组合。 例如，`activated` 和 `axis` 可能就会导致 6 个组合（`activated` 有两个可能的值，`axis` 有三个可能的值）。

由于该方块只有一个属性（`activated`），因此仅有两种可能的变体，其方块状态 JSON 应如下所示：

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/prismarine_lamp.json)

::: tip

不要忘记为方块添加[客户端物品](../items/first-item#creating-the-client-item)，以便它在物品栏中显示！

:::

由于这个示例方块是灯，我们还需要让它在 `activated` 属性为 true 时发光。 此操作可通过在注册方块时传递给构造函数的方块设置来实现。

可以使用 `lightLevel` 方法设置方块发出的光照等级，我们可以在 `PrismarineLampBlock` 类中创建一个静态方法，根据 `activated` 属性返回光照等级，并将其作为方法引用传递给 `lightLevel` 方法：

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

<!-- Note: This block can be a great starter for a redstone block interactivity page, maybe triggering the blockstate based on redstone input? -->

完成所有步骤后，最终结果应大致如下所示：

<VideoPlayer src="/assets/develop/blocks/blockstates_3.webm">游戏中的海晶灯方块</VideoPlayer>
