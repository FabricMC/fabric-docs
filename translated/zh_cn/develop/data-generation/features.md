---
title: 地物生成
description: 使用数据生成器在世界中生成地物的指南。
authors:
  - cassiancc
  - its-miroma
  - Wind292
---

<!---->

:::info 前置知识

请先确保你已经完成了[数据生成设置](./setup)流程。

:::

Minecraft 世界中的地物生成可分为 3 个部分：

- **已配置地物**：定义地物是什么；例如，一棵单独的树
- **已放置地物**：定义地物应如何分布，包括方向、相对位置等；例如，森林中树木的分布方式
- **生物群系修改**：定义地物会被放置在世界中的什么位置；例如，整片森林所在的坐标范围

::: info

Minecraft 中的地物是世界中的自然或生成图案，例如树木、花、矿石或湖泊。 地物不同于结构（例如村庄、神殿等），后者可以通过 `/locate` 命令查找。

:::

## 设置 {#setup}

首先，我们需要创建自己的 provider。 创建一个继承 `FabricDynamicRegistryProvider` 的类，并补全基础方法：

@[code lang=java transcludeWith=:::datagen-world:provider](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldgenProvider.java)

然后，在你的 `DataGeneratorEntrypoint` 类的 `onInitializeDataGenerator` 方法中添加该 provider：

@[code lang=java transclude={67-67}](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

接下来，为已配置地物和已放置地物分别创建一个类。 这些类不需要继承任何内容。

已配置地物类和已放置地物类都应包含一个公共方法，用于注册并定义你的地物。 该方法的参数（这里我们将其命名为 `context`）对于已配置地物应为 `BootstrapContext<ConfiguredFeature<?, ?>>`，对于已放置地物则应为 `BootstrapContext<PlacedFeature>`。

在你的 `DataGeneratorEntrypoint` 类中，将下面的代码行添加到 `buildRegistry` 方法中，并将方法名替换为你自己选择的名称：

@[code lang=java transcludeWith=:::datagen-world:registries](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

如果你还没有 `buildRegistry` 方法，请创建它，并使用 `@Override` 注解标注。

## 已配置地物 {#configured-features}

若要让一个地物自然生成在世界中，我们应先在已配置地物类中定义一个已配置地物。 这里我们添加一个用于钻石矿脉的自定义已配置地物。

首先，在你的已配置地物类中注册 `ConfiguredFeature` 的键：

@[code lang=java transcludeWith=:::datagen-world:configured-key](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

::: tip

`Identifier` 的第二个参数（在本例中为 `diamond_block_vein`）就是你通过 `/place` 命令生成该结构时使用的名称，这对于调试很有帮助。

:::

### 矿石 {#ores}

接下来，我们会创建一个 `RuleTest`，用于控制你的地物可以替换哪些方块。 例如，下面这个 `RuleTest` 允许替换带有 `DEEPSLATE_ORE_REPLACEABLES` 标签的所有方块：

@[code lang=java transcludeWith=:::datagen-world:ruletest](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

接下来，我们需要创建 `OreConfiguration`，它会告诉游戏应将方块替换成什么。

@[code lang=java transcludeWith=:::datagen-world:ore-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

你可以在列表中加入多个情况，以支持不同变体。 例如，我们可以为普通石头和深板岩设置不同的变体：

@[code lang=java transcludeWith=:::datagen-world:multi-ore-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

最后，我们需要将已配置地物注册到游戏中！

@[code lang=java transcludeWith=:::datagen-world:conf-feature-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

### 树木 {#trees}

若要创建自定义树木，首先需要创建一个 `TreeConfiguration`：

@[code lang=java transcludeWith=:::datagen-world:tree-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

各参数的含义如下：

1. 指定树干的方块类型；例如钻石块
2. 使用树干放置器配置树干的形状和高度行为
3. 指定树叶的方块类型；例如金块
4. 使用树叶放置器定义树冠的形状和大小
5. 控制树干在不同高度处如何收束，主要用于较大的树干

::: tip

我们**强烈**建议你自行调整这些值，制作出让**你**满意的自定义树木！

你可以参考原版树木中内置的树干放置器和树叶放置器。

:::

接下来，我们需要在 `ExampleModWorldConfiguredFeatures` 的 `configure` 方法中添加以下代码行，以注册我们的树木：

@[code lang=java transcludeWith=:::datagen-world:tree-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

## 已放置地物 {#placement-features}

向游戏添加地物的下一步，是创建它的已放置地物。

在你的已放置地物类的 `configure` 方法中，创建如下变量：

@[code lang=java transcludeWith=:::datagen-world:conf-feature-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

在你的已放置地物类中，定义已放置地物的键。

@[code lang=java transcludeWith=:::datagen-world:placed-key](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

### 放置修饰器 {#placement-modifiers}

接下来，我们需要定义放置修饰器。它们是在生成地物时设置的属性， 可以是任意内容：从生成频率，到起始 `y` 层级都可以。 你可以按需要设置任意数量的修饰器。

@[code lang=java transcludeWith=:::datagen-world:placement-modifier](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

上面列出的各修饰器作用如下：

- **CountPlacement**：大致表示每个区块中该地物实例的数量；在本例中即矿脉数量
- **BiomeFilter**：允许我们控制该地物会在哪些生物群系或维度中生成；稍后我们会进一步使用它
- **InSquarePlacement**：使地物以更接近伪随机的方式分散分布
- **HeightRangePlacement**：指定地物可生成的 `y` 坐标范围；它支持三种主要分布类型：
  1. **Uniform**：
     范围内所有 `y` 值生成该地物的概率相同。 如果你不确定该用哪种，就使用这一种。

  2. **Trapezoid**：
     越接近中位 `y` 值的位置，生成该地物的概率越高。

  3. **Biased-Bottom**：
     使用对数尺度，使较低的 `y` 值更容易生成该地物。 它接收一个起始 `y` 坐标，低于该坐标时地物绝不会生成。 第二个参数是地物可以生成的最大高度。 第三个参数定义一个以方块为单位的范围，用于延伸最大概率区间。

::: tip

树木和其他地表结构应使用 `PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP` 修饰器，而不是 `HeightRangePlacement`，以确保树木生成在地表上。

:::

现在我们已经有了修饰器，可以注册已放置地物了：

@[code lang=java transcludeWith=:::datagen-world:register-placed-feature](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

## 生物群系修改 {#biome-modifications}

最后，我们需要在模组初始化期间将已放置地物添加到 `BiomeModifications` 中。 可以在模组初始化器中添加以下代码：

@[code lang=java transcludeWith=:::datagen-world:biome-modifications](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

::: tip

对于树木，第二个参数应设置为 `GenerationStep.Decoration.VEGETAL_DECORATION,`

:::

### 指定生物群系生成 {#biome-specific-generation}

通过修改 `BiomeSelectors` 参数，可以让地物只在特定类型的生物群系中生成：

@[code lang=java transcludeWith=:::datagen-world:selective-biome-modifications](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

这样，该地物只会生成在带有 `minecraft:is_forest` 生物群系标签的生物群系中。

## 运行数据生成 {#running-datagen}

现在运行数据生成后，对于你添加的每个已配置地物，都应能在 `src/main/generated/data/example-mod/worldgen/configured_feature` 下看到一个 `.json` 文件；对于每个已放置地物，也应能在 `src/main/generated/data/example-mod/worldgen/placed_feature` 下看到对应文件！

:::details 已配置地物生成文件

@[code lang=json](@/reference/latest/src/main/generated/data/example-mod/worldgen/configured_feature/diamond_block_vein.json)

:::

:::details 已放置地物生成文件

@[code lang=json](@/reference/latest/src/main/generated/data/example-mod/worldgen/placed_feature/diamond_block_ore_placed.json)

:::

<!---->
