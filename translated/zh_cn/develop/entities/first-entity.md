---
title: 创建你的第一个实体
description: 学习如何注册一个简单的实体，并为其添加目标、渲染器、模型和动画。
authors:
  - cassiancc
  - Earthcomputer
  - JaaiDead
  - skycatminepokie
  - SzczurekYT
  - voidedaries
resources:
  https://docs.neoforged.net/docs/entities/: Entities - NeoForge 文档
  https://www.desmos.com/calculator/9r6lh5knfu: 实体行走动画 - Desmos
---

实体是游戏中动态、可交互的对象，它们不是地形的一部分（例如方块）。 实体可以移动，并以各种方式与世界交互。 一些示例包括：

- `Villager`、`Pig` 和 `Goat` 都是 `Mob` 的示例。`Mob` 是最常见的实体类型，表示有生命的生物。
- `Zombie` 和 `Skeleton` 是 `Monster` 的示例。`Monster` 是一种敌对 `Player` 的 `Entity` 变体。
- `Minecart` 和 `Boat` 是 `VehicleEntity` 的示例。这类实体具有处理玩家输入的特殊逻辑。

本教程将引导你创建一个自定义的 _迷你傀儡_。 这个实体会带有有趣的动画。 它将是一个 `PathfinderMob`，这是大多数具有寻路能力的生物所使用的类，例如 `Zombie` 和 `Villager`。

## 准备你的第一个实体 {#preparing-your-first-entity}

创建自定义实体的第一步，是定义它的类，并将其注册到游戏中。

我们会为该实体创建 `MiniGolemEntity` 类，并首先为它设置属性。 [属性](attributes)决定了实体的多项内容，包括最大生命值、移动速度和生物引诱范围。

@[code transcludeWith=:::registerclass](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

若要注册你的实体，建议创建一个单独的 `ModEntityTypes` 类，用于注册所有实体类型、设置它们的尺寸，并注册它们的属性。

@[code transcludeWith=:::types](@/reference/latest/src/main/java/com/example/docs/entity/ModEntityTypes.java)

## 添加目标 {#adding-goals}

目标是用于处理实体目的或行为意图的系统，它会为实体提供一组明确的行为。 目标具有一定的优先级：优先级数值较低的目标，会优先于数值较高的目标执行。

若要为实体添加目标，需要在实体类中创建一个 `registerGoals` 方法，用于定义该实体的目标。

@[code transcludeWith=:::goals](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

::: info

1. `TemptGoal` - 使实体被手持特定物品的玩家吸引。
2. `RandomStrollGoal` - 使实体在世界中随机行走或游荡。
3. `LookAtPlayerGoal` - 尽管名称如此，但它可以接受任意实体。 这里用于注视 `Cow` 实体。
4. `RandomLookAroundGoal` - 使实体随机朝不同方向看。

:::

## 创建渲染 {#creating-rendering}

渲染是指将方块、实体和环境等游戏数据转换为玩家屏幕上可见画面的过程。 这包括决定对象如何被照亮、着色和渲染贴图。

::: info

实体渲染始终在客户端处理。 服务器负责管理实体的逻辑和行为，而客户端负责显示实体的模型、纹理和动画。

:::

渲染包含多个步骤，并会涉及各自对应的类。我们先从 `EntityRenderState` 类开始。

@[code transcludeWith=:::entitystate](@/reference/latest/src/client/java/com/example/docs/entity/state/MiniGolemEntityRenderState.java)

存储在渲染状态中的数据会用于决定实体的视觉表现，包括移动、空闲行为等动画状态。

### 设置模型 {#setting-up-model}

`MiniGolemEntityModel` 类用于描述实体的形状和组成部分，从而定义实体的外观。 模型通常会在 [Blockbench](https://www.blockbench.net/) 等第三方工具中创建，而不是手写。 不过，本教程仍会通过一个手写示例来展示其工作方式。

::: warning

Blockbench 支持多种[映射](../migrating-mappings/#mappings)（例如 Mojang Mappings、Yarn 等）。 请确保选择与你的开发环境匹配的正确映射。本教程使用 Mojang Mappings。

映射不一致可能会导致集成 Blockbench 生成的代码时出错。

:::

@[code transcludeWith=:::model1](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

`MiniGolemEntityModel` 类定义了迷你傀儡实体的视觉模型。 它继承自 `EntityModel`，并指定实体各个身体部件（身体、头部、左腿和右腿）的名称。

@[code transcludeWith=:::model_texture_data](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

该方法通过将迷你傀儡的身体、头部和腿创建为长方体，设置它们的位置和纹理映射，并返回用于渲染的 `LayerDefinition`，从而定义迷你傀儡的 3D 模型。

每个部件都会以一个偏移点添加，该偏移点是应用到该部件的所有变换的原点。 模型部件中的其他所有坐标，都是相对于这个偏移点进行测量的。

::: warning

模型中更大的 Y 值对应实体的**底部**。 这与游戏内坐标相反。

:::

现在，我们需要在客户端包中创建一个 `ModEntityModelLayers` 类。 这个实体只有一个纹理层，但其他实体可能会使用多个纹理层；可以想想 `Player` 这类实体的第二皮肤层，或 `Spider` 的眼睛。

@[code transcludeWith=:::model_layer](@/reference/latest/src/client/java/com/example/docs/entity/model/ModEntityModelLayers.java)

随后，必须在模组的客户端初始化器中初始化这个类。

@[code transcludeWith=::register_client](@/reference/latest/src/client/java/com/example/docs/entity/ExampleModCustomEntityClient.java)

### 设置纹理 {#setting-up-texture}

::: tip

纹理尺寸应与 `LayerDefinition.create(modelData, 64, 32)` 中的值一致：宽 64 像素，高 32 像素。 如果你需要使用不同尺寸的纹理，别忘了同时修改 `LayerDefinition.create` 中的尺寸，使其匹配。

:::

每个模型部件或方盒都会期望纹理上的某个特定位置存在对应的展开图。 默认情况下，它会期望该位置在 `0, 0`（左上角），但可以通过调用 `CubeListBuilder` 中的 `texOffs` 函数来更改。

作为示例，你可以将以下纹理用于 `assets/example-mod/textures/entity/mini_golem.png`：

<DownloadEntry visualURL="/assets/develop/entity/mini_golem.png" downloadURL="/assets/develop/entity/mini_golem_small.png">Texture</DownloadEntry>

### 创建渲染器 {#creating-the-renderer}

实体的渲染器使你能够在游戏中看到该实体。 我们会创建一个新的 `MiniGolemEntityRenderer` 类，用于告诉 Minecraft 该实体应使用哪种纹理、模型和实体渲染状态。

@[code transcludeWith=:::renderer](@/reference/latest/src/client/java/com/example/docs/entity/renderer/MiniGolemEntityRenderer.java)

这里也会设置阴影半径。对于该实体，阴影半径为 `0.375f`。

随后，必须在模组的客户端初始化器中注册这个渲染器。

@[code transcludeWith=::register_renderer](@/reference/latest/src/client/java/com/example/docs/entity/ExampleModCustomEntityClient.java)

### 添加行走动画 {#walking-animations}

可以将以下代码添加到 `MiniGolemEntityModel` 类中，为实体添加行走动画。

@[code transcludeWith=:::model_animation](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

首先，将偏航角和俯仰角应用到头部模型部件上。

然后，将行走动画应用到腿部模型部件。 我们使用 `cos` 函数创建基础的腿部摆动效果，再对余弦波进行变换，以获得正确的摆动速度和摆动幅度。

- 公式中的 `0.2` 常量控制余弦波的频率（腿摆动的速度）。 值越大，频率越高。
- 公式中的 `1.4` 常量控制余弦波的振幅（腿摆动的幅度）。 值越大，振幅越大。
- `limbSwingAmplitude` 变量也会像 `1.4` 常量一样影响振幅。 该变量会根据实体速度变化，因此实体移动得越快，腿部摆动越明显；实体移动较慢或静止时，腿部摆动会减弱或完全停止。
- 左腿公式中的 `Mth.PI` 常量会将余弦波平移半个相位，使左腿与右腿朝相反方向摆动。

你可以将它们绘制到图表上，观察其效果：

![图表](/assets/develop/entity/graphs/dark_graph.png){.dark-only}
![图表](/assets/develop/entity/graphs/light_graph.png){.light-only}

蓝色曲线表示左腿，棕色曲线表示右腿。 水平 x 轴表示时间，y 轴表示腿部肢体的角度。

你可以随意在 [Desmos 上调整这些常量](https://www.desmos.com/calculator/9r6lh5knfu)，看看它们会如何影响曲线。

现在回到游戏中，你已经具备了使用 `/summon example-mod:mini_golem` 生成该实体所需的一切！

![生成出的傀儡](/assets/develop/entity/mini_golem_summoned.png)

## 为实体添加数据 {#adding-data-to-an-entity}

若要在实体上存储数据，通常的做法是在实体类中直接添加字段。

有时，你需要将服务端实体中的数据同步到客户端实体。 关于客户端-服务端架构的更多信息，请参阅[网络通信页面](../networking)。 为此，我们可以通过定义 `EntityDataAccessor` 来使用 _synched data_ \[原文如此]。

在本例中，我们希望实体每隔一段时间跳舞一次，因此需要创建一个会在客户端之间同步的跳舞状态，以便之后为其播放动画。 不过，跳舞冷却时间不需要与客户端同步，因为动画由服务器触发。

@[code transcludeWith=:::datatracker](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

如你所见，我们添加了一个 tick 方法来控制跳舞状态。

## 将数据存储到 NBT {#storing-data-to-nbt}

对于需要在游戏关闭后仍然保存的持久数据，我们会在 `MiniGolemEntity` 中重写 `addAdditionalSaveData` 和 `readAdditionalSaveData` 方法。 我们可以用它们来存储跳舞动画剩余的时间。

@[code transcludeWith=:::savedata](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

现在，每当实体被加载时，它都会恢复到先前留下的状态。

## 添加动画 {#adding-an-animation}

为实体添加动画的第一步，是在实体类中添加动画状态。 我们会创建一个动画状态，用于让实体跳舞。

@[code transcludeWith=:::dancing_animation](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

我们重写了 `onSyncedDataUpdated` 方法。 每当同步数据在服务器或客户端更新时，该方法都会被调用。 这里的 if 语句会检查被更新的同步数据是否为跳舞同步数据。

现在，我们继续处理动画本身。 我们会创建 `MiniGolemAnimations` 类，并添加一个 `AnimationDefinition`，用于定义动画应如何应用到实体上。

@[code transcludeWith=:::dancing_animation](@/reference/latest/src/client/java/com/example/docs/entity/animation/MiniGolemAnimations.java)

这里包含了不少内容，请注意以下关键点：

- `withLength(1)` 会让动画持续 1 秒。
- `looping()` 会让动画反复循环播放。
- 随后是一系列 `addAnimation` 调用，它们会添加针对各个模型部件的独立动画。 这里，我们为头部、左腿和右腿分别设置了不同的动画。
  - 每个动画都会作用于该模型部件的特定属性。在本例中，我们每次修改的都是模型部件的旋转。
  - 一个动画由一组关键帧组成。 当动画时间（经过的秒数）等于某个关键帧的时间时，我们所作用的属性值就会等于该关键帧中指定的值（在本例中即旋转值）。
  - 当动画时间位于两个关键帧之间时，该属性值会在相邻两个关键帧之间进行插值（混合）。
  - 我们使用了线性插值，这是最简单的插值方式，会使属性值（在本例中是模型部件的旋转）以恒定速率从一个关键帧变化到下一个关键帧。 原版还提供了 Catmull-Rom 样条插值，可在关键帧之间产生更平滑的过渡。
  - 模组开发者也可以创建自定义插值类型。

最后，让我们将动画接入模型：

@[code transcludeWith=:::dancing_animation](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

当动画正在播放时，我们会应用该动画；否则，则使用原来的腿部动画代码。

## 添加刷怪蛋 {#adding-spawn-egg}

若要为迷你傀儡实体添加刷怪蛋，请参阅关于[创建刷怪蛋](../items/spawn-egg)的完整文章。
