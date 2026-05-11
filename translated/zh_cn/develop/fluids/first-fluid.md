---
title: 创建你的第一个流体
description: 学习如何在 Minecraft 中创建你的第一个自定义流体方块。
authors:
  - AlbanischeWurst
  - AlexiyOrlov
  - cassiancc
  - CelDaemon
  - Clomclem
  - comp500
  - Daomephsta
  - Earthcomputer
  - florensie
  - Fusion-Flux
  - InfinityChances
  - Kilip1000
  - MaxURhino
  - SolidBlock-cn
  - SuperSoupr
  - Virtuoel
  - UpcraftLP
authors-nogithub:
  - alfiejfs
  - salvopelux
---

<!---->

:::info 前置知识

你必须先了解如何[创建方块](../blocks/first-block)，以及如何[创建物品](../items/first-item)。

:::

本示例将介绍如何创建一种酸液流体：站在其中的实体会受到伤害、虚弱并失明。 为此，我们需要两个流体实例，分别用于源状态和流动态；还需要一个液体方块、一个桶物品，以及一个流体标签。

## 创建流体类 {#creating-the-fluid-class}

首先，我们会创建一个抽象类，这里命名为 `AcidFluid`，并让它继承基础的 `FlowingFluid` 类。 随后，我们会重写那些源流体和流动流体都应保持一致的方法。

请特别注意以下方法：

- `animateTick` 用于显示粒子和播放声音。 下面展示的行为基于水：水在流动时会播放声音，并会产生水下气泡粒子。
- `entityInside` 用于处理实体接触流体时应发生的事情。 我们会以水为基础，使其熄灭实体身上的火焰，同时也让其中的实体受到伤害、虚弱并失明——毕竟这是酸液。
- `canBeReplacedWith` 处理一部分流动逻辑。请注意，`ModFluidTags.ACID` 目前还没有定义，我们会在最后处理它。

把这些内容整合起来后，我们会得到以下类：

@[code transcludeWith=:::abstractFluid](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

在 `AcidFluid` 内部，我们会为 `Source` 和 `Flowing` 流体创建两个子类。

@[code transcludeWith=:::fluidSubclasses](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

### 注册流体 {#registering-fluids}

接下来，我们会创建一个用于注册所有流体实例的类， 并将其命名为 `ModFluids`。

@[code transcludeWith=:::register](@/reference/latest/src/main/java/com/example/docs/fluid/ModFluids.java)

和方块一样，你需要确保该类被加载，这样所有包含流体实例的静态字段才会完成初始化。 你可以创建一个空的 `initialize` 方法，并在你的[模组初始化器](../getting-started/project-structure#entrypoints)中调用它，以触发静态初始化。

现在，回到 `AcidFluid` 类，添加以下方法，将已注册的流体实例与该流体关联起来：

@[code transcludeWith=:::sources](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

到目前为止，我们已经注册了流体的源状态和流动状态。 接下来，还需要为它注册一个桶和一个 `LiquidBlock`。

### 注册流体方块 {#fluid-blocks}

现在，让我们为该流体添加一个液体方块。 某些命令（例如 `setblock`）会需要它，这样你的流体才能存在于世界中。 如果你还没有了解过，建议先阅读[如何创建你的第一个方块](../blocks/first-block)。

打开你的 `ModBlocks` 类，并注册以下 `LiquidBlock`：

@[code transcludeWith=:::acid](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

然后，在 `AcidFluid` 中重写以下方法，将你的方块与该流体关联起来：

@[code transcludeWith=:::legacyBlock](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

### 注册桶 {#buckets}

Minecraft 中的流体通常可以装入桶中，因此我们来看看如何为酸液添加一个酸液桶物品。 如果你还没有了解过，建议先阅读[如何创建你的第一个物品](../items/first-item)。

打开你的 `ModItems` 类，并注册以下 `BucketItem`：

@[code transcludeWith=:::acid_bucket](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

然后，在 `AcidFluid` 中重写以下方法，将你的桶与该流体关联起来：

@[code transcludeWith=:::bucket](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

不要忘记，为了正确渲染，物品需要名称为 `acid_bucket` 的翻译、[纹理](../items/first-item#adding-a-texture)、[模型](../items/first-item#adding-a-model)以及[客户端物品](../items/first-item#creating-the-client-item)。 下面提供了一个示例纹理。

<DownloadEntry visualURL="/assets/develop/fluids/acid_bucket.png" downloadURL="/assets/develop/fluids/acid_bucket_small.png">纹理</DownloadEntry>

另外，建议将你的模组中的桶添加到 `ConventionalItemTags.BUCKET` 物品标签中，这样其他模组就能正确处理它。你可以[手动](#tagging)添加，也可以通过[数据生成](../data-generation/tags)添加。

## 为流体添加标签 {#tagging}

::: info

使用[数据生成](../data-generation/tags)的用户可能会希望通过 `FabricTagProvider.FluidTagProvider` 注册标签，而不是手写标签文件。

:::

由于一个流体在流动状态和静止状态下会被视为两个独立的流体，通常会使用标签来同时检查这两种状态。 我们会在 `data/example-mod/tags/fluid/acid.json` 中创建一个流体标签：

<<< @/reference/latest/src/main/generated/data/example-mod/tags/fluid/acid.json

::: tip

Minecraft 还提供了其他标签，用于控制流体的行为：

- 如果你需要让模组的流体表现得像水一样（水下迷雾、可被海绵吸收、可游泳、减缓实体速度等），可以考虑将其添加到 `minecraft:water` 流体标签中。
- 如果你需要让它表现得像熔岩一样（熔岩迷雾、可被炽足兽/恶魂游泳通过、减缓实体速度等），可以考虑将其添加到 `minecraft:lava` 流体标签中。
- 如果你只需要其中的_部分_行为，则可以考虑使用 Mixin 对行为进行更精细的控制。

:::

在这个演示中，我们还会将酸液流体标签添加到水流体标签 `data/minecraft/tags/fluid/water.json` 中。

<<< @/reference/latest/src/main/generated/data/minecraft/tags/fluid/water.json

## 为流体添加纹理 {#textures}

若要为流体添加纹理，应使用 Fabric API 的 `FluidRenderHandlerRegistry`。

::: tip

为简化示例，本演示使用 `BlockTintSources.constant` 为原版水纹理应用一个恒定的绿色色调。 有关 `BlockTintSource` 的更多细节，请参阅[方块着色](../blocks/block-tinting)。

:::

将以下代码行添加到你的 `ClientModInitializer` 中，以创建一个 `FluidModel.Unbaked`。它会接收两个用于纹理的 `Material`：一个用于静止的源流体，另一个用于流动流体；同时还会接收一个方块着色源，用于指定要应用的颜色。

@[code transcludeWith=:::fluid_texture](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

至此，我们已经具备了在游戏中看到酸液所需的一切！ 你可以使用 `setblock`，也可以使用酸液桶物品，将酸液放置到世界中。

![世界中的绿色酸液流体截图](/assets/develop/fluids/acid.png)
