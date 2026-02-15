---
title: 移植到 1.21.11
description: 移植到 Minecraft 最新版本的指南。
authors:
  - cassiancc
---

Minecraft 是不断发展中的游戏，新的版本改变游戏的同时也会影响模组开发者。 本文章包含了将模组更新到最新的 Minecraft 稳定版本需要遵循的通用步骤。

::: info

这些文档讨论的是从 **1.21.10** 迁移到 **1.21.11**， 如果在找其他的迁移，使用右上角的下拉菜单切换到目标版本。

:::

## 更新构建脚本{#build-script}

首先将你的模组的 `gradle/wrapper/gradle-wrapper.properties`、`gradle.properties` 和 `build.gradle` 更新到最新版本：

1. 运行以下命令，将 Gradle 更新到最新版：`./gradlew wrapper --gradle-version latest`
2. 在 `gradle.properties`（推荐）或 `build.gradle` 中找到 Minecraft、Fabric Loader、Fabric Loom 和 Fabric API 的版本。 在 [Fabric Develop 网站](https://fabricmc.net/develop/)找到 Fabric 组件的推荐版本。
3. 按 IntelliJ IDEA 右上角的刷新按钮刷新 Gradle。 如果按钮不可见，可运行 `./gradlew --refresh-dependencies` 以强制清除缓存。

## 更新代码{#porting-guides}

构建脚本更新到 1.21.11 后，现在就可以检查你的模组并更新任何改变的代码，以使其与新版本兼容。

为帮助你更新，模组开发者会在文章中记下他们遇到的变更，例如 Fabric Blog 和 NeoForge 的 porting primer。

- [Fabric blog 上的 _Fabric for Minecraft 1.21.11_](https://fabricmc.net/2025/12/05/12111.html) 包含对 Fabric API 1.21.11 版本所做更改的详细解释。
- [Minecraft blog 上的 _Minecraft: Java Edition 1.21.11_](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-11) 是 1.21.11 引入的功能的官方概览。
- [Minecraft Wiki 上的《Java版1.21.11》](https://zh.minecraft.wiki/w/Java版1.21.11)是此次更新内容的非官方总结。
- YouTube 上的 [slicedlime 的 Data & Resource Pack News in Minecraft 1.21.11](https://www.youtube.com/watch?v=5yY25GoWQhs&pp=0gcJCSkKAYcqIYzv) 介绍了与更新模组的数据和资源包驱动内容相关的信息。
- [NeoForge 的 _Minecraft 1.21.10 -> 1.21.11 Mod Migration Primer_](https://github.com/neoforged/.github/blob/main/primers/1.21.11/index.md) 介绍了从 1.21.10 迁移到 1.21.11，仅聚焦于原版代码的变更。
  - 请注意，链接的文章是第三方材料，不由 Fabric 维护。 其版权属于 @ChampionAsh5357，按 [Creative Commons Attribution 4.0 International](https://creativecommons.org/licenses/by/4.0/) 的许可协议发布。
