---
title: 移植到 26.1 快照版本
description: 移植到 Minecraft 的下一个版本的快照版本的指南。
authors:
  - cassiancc
---

即将到来的 Minecraft 的 26.1 版本是未混淆的，其快照也是如此。 知道这一点后，你就需要为你的构建版本做比平时更多的改变，才能移植到新版本。

::: info

这些文档讨论的是从 **1.21.11** 迁移到 **26.1** 的快照版本， 如果你是要迁移到 **1.21.11**，请参阅 [迁移到 1.21.11](./current)。

:::

注意如果你的模组还是在使用 Fabric 的 Yarn 映射，你首先需要[将你的模组迁移到 Mojang 的官方映射](../migrating-mappings/)然后再移植到 26.1。

如果你是使用的 IntelliJ IDEA，还需要将其更新到 `2025.3` 以上版本，才有对 Java 25 的完全支持。

## 更新构建脚本{#build-script}

首先将你的模组的 `gradle/wrapper/gradle-wrapper.properties`、`gradle.properties` 和 `build.gradle` 更新到最新版本。

1. 运行以下命令，将 Gradle 更新到最新版：`./gradlew wrapper --gradle-version latest`
2. 在 `gradle.properties`（推荐）或 `build.gradle` 中找到 Minecraft、Fabric Loader、Fabric Loom 和 Fabric API 的版本。 在 [Fabric Develop 网站](https://fabricmc.net/develop/)找到 Fabric 组件的推荐版本。
   - 注意，因为 26.1 的快照不是 Minecraft 的稳定版本，所以你需要从下拉菜单中手动选择此版本。
3. 在 `build.gradle` 的顶部，将使用的 Loom 的版本，由 `id "fabric-loom"` 修改为 `id "net.fabricmc.fabric-loom"`。 如果你是在 `settings.gradle` 中指定的 Loom，也一并将其更改。
4. 在 `build.gradle` 的 dependencies 段中，移除 `mappings` 的行。
5. 将任何 `modImplementation` 或 `modCompileOnly` 的实例替换为 `implementation` 和 `compileOnly`。
6. 移除任何适用于 26.1 之前的版本的模组，或将其替换为兼容此更新的版本。
   - 1.21.11 或 Minecraft 更早版本的现有模组均不会在 26.1 中生效，即使是仅编译（compile-only）的依赖。
7. 将任何提及 `remapJar` 的都替换为 `jar`。
8. 按 IntelliJ IDEA 右上角的刷新按钮刷新 Gradle。 如果按钮不可见，可运行 `./gradlew --refresh-dependencies` 以强制清除缓存。

## 更新代码{#porting-guides}

构建脚本更新到 26.1 后，现在就可以检查你的模组并更新任何改变的代码，以使其与快照兼容。

:::warning 重要

因为 Minecraft 26.1 仍在快照阶段，特定的变化的文档仍缺失。 祝你好运，你只能靠自己了！

:::

- [Fabric API 26.1 迁移指南](26.1/fabric-api)列举了 Fabric API 在 26.1 中为匹配 Mojang 的名称而进行的更名。
- [Minecraft Wiki 上的《Java版26.1》](https://zh.minecraft.wiki/w/Java版26.1)是此次更新内容的非官方总结。
- [NeoForge 的 _Minecraft 1.21.11 -> 26.1 Mod Migration Primer_](https://github.com/ChampionAsh5357/neoforged-github/blob/update/26.1/primers/26.1/index.md) 介绍了从 1.21.11 迁移到 26.1，仅聚焦于原版代码的变更。
  - 请注意，链接的文章是第三方材料，不由 Fabric 维护。 其版权属于 @ChampionAsh5357，按 [Creative Commons Attribution 4.0 International](https://creativecommons.org/licenses/by/4.0/) 的许可协议发布。

<!---->
