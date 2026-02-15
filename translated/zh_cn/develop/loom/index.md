---
title: Loom
description: Fabric Loom Gradle 插件的文档。
authors:
  - Atakku
  - caoimhebyrne
  - Daomephsta
  - JamiesWhiteShirt
  - Juuxel
  - kb-1000
  - modmuss50
  - SolidBlock-cn
---

Fabric Loom，或者简称为 Loom，是一个 [Gradle](https://gradle.org/) 插件，用于在 Fabric 生态系统中开发模组。

Loom 提供在开发环境中安装 Minecraft 和模组的实用程序，以便你可以根据 Minecraft 混淆及其在发行版和版本之间的差异对它们进行链接。 Loom 还提供用于 Fabric Loader、Mixin 编译处理和 Fabric Loader 的 jar-in-jar 系统的实用程序的运行配置。

Loom 支持 Minecraft 的 _所有_ 版本，甚至包括那些未被 Fabric API 官方支持的版本，因为它与版本无关。

:::warning 重要

本页面是 Loom 所有选项和功能的参考。 如果你刚入门，请阅读 [Fabric 简介](../)。

:::

## 插件ID {#plugin-ids}

Loom 使用多种不同插件 ID：

- `net.fabricmc.fabric-loom`，对于未混淆的版本（Minecraft 26.1 及以后）
- `net.fabricmc.fabric-loom-remap`，对于混淆的版本（Minecraft 1.21.11 及以前）
- `fabric-loom`（旧版），只向下兼容混淆的版本。 请使用 `net.fabricmc.fabric-loom-remap`
- `net.fabricmc.fabric-loom-companion`，适用于高级多项目场景。 深入了解：[子项目](./classpath-groups#multi-project)

## 依赖子项目 {#subprojects}

在设置依赖于另一个 Loom 项目的多项目构建时，当依赖于其他项目时，应该使用 `namedElements` 配置。 默认情况下，项目的“输出”会重新映射到中间名称。 `namedElements` 配置包含未重新映射的项目输出。

```groovy
dependencies {
 implementation project(path: ":name", configuration: "namedElements")
}
```

如果你在多项目构建中使用拆分源集，则还需要为其他项目的客户端源集添加依赖项。

```groovy
dependencies {
 clientImplementation project(":name").sourceSets.client.output
}
```

## 拆分客户端和通用代码 {#split-sources}

多年来，服务器崩溃的一个常见原因是模组在服务器上安装时意外调用了客户端专用代码。 较新的 Loom 和 Loader 版本提供了一个选项，要求将所有客户端代码移至其自己的源集。 这是为了在编译时防止问题，但构建仍将产生一个可在任一端运行的 jar 文件。

以下来自 `build.gradle` 文件的片段显示了如何为你的模组启用此功能。 由于你的模组现在将拆分为两个源集，因此你需要使用新的 DSL 来定义模组的源集。 这使 Fabric Loader 能够将模组的类路径分组在一起。 这对于其他一些复杂的多项目设置也很有用。

需要 Minecraft 1.18（推荐 1.19）、Loader 0.14 和 Loom 1.0 或更高版本来拆分客户端和通用代码。

```groovy
loom {
 splitEnvironmentSourceSets()

 mods {
   example-mod {
     sourceSet sourceSets.main
     sourceSet sourceSets.client
   }
 }
 }
```

## 解决问题 {#issues}

Loom 与 Gradle 有时会因缓存文件损坏而失败。 运行 `./gradlew build --refresh-dependencies` 将强制 Gradle 和 Loom 重新下载并重新创建所有文件。 可能需要几分钟，但通常足以解决与缓存相关的问题。

## 开发环境设置 {#setup}

Loom 的设计初衷是开箱即用，只需在 IDE 中设置一个工作区即可。 它在幕后做了很多事情来创建 Minecraft 的开发环境：

- 从官方渠道下载已配置版本的 Minecraft 的客户端和服务器 jar
- 合并客户端和服务器 jar 以生成带有 `@Environment` 和 `@EnvironmentInterface` 注释的合并 jar
- 下载已配置的映射
- 使用中间映射重新映射合并 jar 以生成中间 jar
- 使用 Yarn 映射重新映射合并 jar 以生成映射 jar
- 可选：反编译映射 jar 以生成映射源 jar 和线图，并将线图应用于映射 jar
- 添加 Minecraft 依赖项
- 下载 Minecraft 资产
- 处理并包含模组增强依赖项

## 缓存 {#caches}

- `${GRADLE_HOME}/caches/fabric-loom`：用户缓存，由用户的所有 Loom 项目共享的缓存。 用于缓存 Minecraft 资产、jar、合并的 jar、中间 jar 和映射的 jar
- `.gradle/loom-cache`：根项目持久缓存，由项目及其子项目共享的缓存。 用于缓存重新映射的模组，以及生成的包含的模组 jar
- `**/build/loom-cache`：（子）项目的构建缓存

## 依赖项配置 {#configurations}

- `minecraft`：定义在开发环境中要使用的 Minecraft 版本
- `mappings`：定义在开发环境中要使用的映射
- `modImplementation`、`modApi` 和 `modRuntime`：针对模组依赖项的 `implementation`、`api` 和 `runtime` 的增强变体。 将重新映射以匹配开发环境中的映射，并删除所有嵌套 jar
- `include`：声明应在 `remapJar` 输出中作为 `jar-in-jar` 包含的依赖项。 此依赖项配置不是传递性的。 对于非模组依赖项，Loom 将使用模组 ID 作为名称生成带有 fabric.mod.json 的模组 jar，并使用相同的版本

## 默认配置 {#configuration}

- 应用以下插件：`java`、`eclipse`
- 添加以下 Maven 仓库：[Fabric](https://maven.fabricmc.net/)、[Mojang](https://libraries.minecraft.net/) 和 Maven Central
- 配置 `eclipse` 任务以由 `genEclipseRuns` 任务完成
- 如果根项目中存在 `.idea` 文件夹，则下载资产（如果不是最新的）并在 `.idea/runConfigurations` 中安装运行配置
- 使用 `annotationProcessor` 依赖项配置添加 `net.fabricmc:fabric-mixin-compile-extensions` 及其依赖项
- 使用 Mixin 注释处理器的配置用来配置所有非测试 JavaCompile 任务
- 配置 `remapJar` 任务以输出与 `jar` 任务输出同名的 JAR，然后向 `jar` 任务添加“dev”分类器
- 配置 `remapSourcesJar` 任务以处理 `sourcesJar` 任务输出（如果任务存在）
- 将 `remapJar` 任务和 `remapSourcesJar` 任务添加为 `build` 任务的依赖项
- 配置 `remapJar` 任务和 `remapSourcesJar` 任务以在执行时将其输出添加为 `archives` 工件
- 对于每个 MavenPublication（来自 `maven-publish` 插件），手动将依赖项附加到 POM 以进行模组增强的依赖项配置，前提是依赖项配置具有 Maven 范围

所有运行配置都具有运行目录 `${projectDir}/run` 和 VM 参数 `-Dfabric.development=true`。 运行配置的主类通常由 Fabric Loader 的 JAR 文件根目录中的 `fabric-installer.json` 文件定义（当它作为模组依赖项包含时），但该文件可以由任何模组依赖项定义。 如果未找到此类文件，则主类默认为 `net.fabricmc.loader.launch.knot.KnotClient` 和 `net.fabricmc.loader.launch.knot.KnotServer`。
