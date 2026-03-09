---
title: 迁移映射
description: 了解如何迁移模组的混淆映射。
authors:
  - ArduFish
  - asiekierka
  - cassiancc
  - Daomephsta
  - deirn
  - Earthcomputer
  - florensie
  - Friendly-Banana
  - IMB11
  - jamierocks
  - JamiesWhiteShirt
  - liach
  - MildestToucan
  - modmuss50
  - natanfudge
  - Spinoscythe
  - UpcraftLP
authors-nogithub:
  - basil4088
---

你可能想[将模组更新到“群骑纷争”游戏小更新之后](#whats-going-on-with-mappings)，或者相反。

有两种方式实现：你可以使用 **Loom Gradle 插件** ，也可以使用 **Ravel IntelliJ IDEA 插件**。

Loom 通过 `migrateMappings` 任务提供半自动映射迁移，但**不支持迁移用 Kotlin 编写的代码**。

Ravel 是 IntelliJ IDEA 的一个插件，添加了一个用于迁移的 GUI 对话框。 与 Loom 不同的是，Ravel 还**支持 Kotlin**。 此外，由于 Ravel 使用 IDE 来处理更改，因此在处理更复杂的项目时，其性能可能优于 Loom。

::: info

Fabric API 使用 Ravel 将 Yarn 迁移到 Mojang 映射。 参见 [Fabric API 代码库中的 PR #4690](https://github.com/FabricMC/fabric/pull/4960)。

:::

两种方法都不完美，你仍需检查结果并进行手动修复，尤其是在迁移 mixin 时。

<ChoiceComponent :choices="[
{
 name: 'Loom Gradle Plugin',
 href: './loom',
 icon: 'simple-icons:gradle',
 color: '#4DC9C0',
},
{
 name: 'Ravel IntelliJ IDEA Plugin',
 href: './ravel',
 icon: 'simple-icons:intellijidea',
 color: '#FE2857',
},
]" />

## 映射发生了什么变化？ {#whats-going-on-with-mappings}

历史上，《Minecraft：Java 版》曾使用过混淆技术，这促成了混淆映射的开发，而 Fabric Loom 正是将混淆映射用于制作模组。 有两种选择：或是 Fabric 自带的 Yarn 映射，或是 Mojang 官方映射。

Mojang 近期宣布[他们将要移除《Minecraft：Java 版》中的代码混淆](https://www.minecraft.net/zh-hans/article/removing-obfuscation-in-java-edition)，而 Fabric 项目随后公布了[应对这一变化的计划](https://fabricmc.net/2025/10/31/obfuscation.html)。 如果你计划将模组更新到此版本，则需要在更新之前先迁移到 Mojang 的混淆映射。

## 什么是映射？ {#mappings}

《Minecraft：Java 版》自发布以来就进行了代码混淆，这意味着其代码中像 `Creeper` 这样易于理解的类名被替换成了像 `brc` 这样的乱码。 为了方便制作模组，Fabric Loom 使用了混淆映射：这些映射可以将混淆后的类名（例如 `brc`）转换回易于理解的名称（例如 `CreeperEntity`）。

作为 Fabric 开发者，你会遇到三组主要名称：

- **中间映射**：Fabric 编译后的模组使用的映射集，例如 `brc` 可能变成 `class_1548`。 中间映射的目的是在不同版本之间提供一组稳定的名称，因为混淆后的类名会随着 Minecraft 的每个新版本而改变。 这通常会使某个版本构建的模组可在其他版本上运行，只要游戏中受影响的部分没有发生太大变化就行。
- **Yarn**：Fabric 为开发者编写模组而开发的开源映射集。 大多数 Fabric 模组都使用 Yarn 映射，因为它们是 2025 年之前的默认映射。 例如，`CreeperEntity` 就是一个 Yarn 映射。
- **Mojang 映射**：游戏官方的混淆映射，由 Mojang 于 2019 年发布，旨在帮助模组开发。 值得注意的是，Mojang 的混淆映射缺少参数名称和 Javadoc，因此一些用户还会在官方映射上叠加 [Parchment](https://parchmentmc.org/)。 例如，`Creeper` 就是一个 Mojang 映射。

Minecraft 26.1 将被反混淆并包含参数名称，因此不需要任何混淆映射。
