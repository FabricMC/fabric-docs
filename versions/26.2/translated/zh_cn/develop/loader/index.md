---
title: Fabric Loader
description: 轻量级模组加载器，Fabric 项目的核心。
authors:
  - cassiancc
  - falseresync
  - jamieswhiteshirt
  - liach
  - Llamalad7
  - Maganoos
  - SolidBlock-cn
resources:
  https://maven.fabricmc.net/docs/fabric-loader-0.19.2/: Fabric Loader 0.19.2 Javadoc
  https://wiki.fabricmc.net/documentation:entrypoint: 入口点 - Fabric Wiki
  https://github.com/FabricMC/fabric-loader: GitHub 上的 Fabric Loader
---

Fabric Loader 是 Fabric 的轻量模组加载器。 它提供了修改任何 Java 应用程序所需的必要工具。 另一方面，特定于游戏和版本的钩子则属于 Fabric API 的范畴。

::: info

虽然 Fabric Loader 主要用于 Minecraft，但也可以为其他 Java 应用程序创建游戏提供程序（例如[《杀戮尖塔》](https://www.youtube.com/watch?v=ZaNI4OJFGTg)或 [Hytale](https://github.com/cootshk/Hybric) 等游戏）。

:::

Fabric Loader 提供的服务允许模组在初始化期间执行代码、转换类、声明并提供模组依赖项，且这一切都能在各种环境中运行。

最新版本 Fabric Loader 的 Javadoc 可以在 [Develop](https://fabricmc.net/develop/) 网站上找到。

可以通过 `FabricLoader.getInstance()` 获取当前的 Fabric Loader 实例。 例如，可以使用 `FabricLoader.getInstance().isModLoaded` 来检查另一个模组是否正在运行。

## 模组 {#mods}

模组是一个在根目录下包含 [`fabric.mod.json`](./fabric-mod-json) 元数据文件的 JAR 包，该文件声明了模组应如何加载。 该文件包含模组 ID 和版本号，以及[入口点](../getting-started/project-structure#entrypoints)和 Mixin 配置。

模组 ID 用于标识模组，任何两个具有相同 ID 的模组都被视为同一个模组。 一次只能加载一个版本的模组。

模组还可以声明其依赖或冲突的其他模组。 Fabric Loader 会尝试满足依赖关系并加载适当版本的模组；如果无法满足，则会导致游戏启动失败。

Fabric Loader 使所有模组都具备同等的游戏修改能力。 例如，Fabric API 能做的任何事情，其他任何模组也都能做到。

模组可以从类路径和 `mods` 目录中加载。 该目录可以通过 `fabric.modsFolder` 系统属性进行更改。

## 嵌套 JAR {#nested-jars}

::: info

使用 Fabric Loom 的 `include` 选项会自动处理 JAR 的嵌套，包括为非模组 JAR 生成 `fabric.mod.json`。

:::

嵌套 JAR 允许模组提供其自身的依赖项，以便 Fabric Loader 在尝试满足依赖关系时找到最佳版本，而无需单独安装这些依赖。

嵌套 JAR 的行为与任何其他模组一样，拥有自己的元数据文件，但它们包含在父级 JAR 中。 请注意，嵌套模组本身也可以以同样的方式嵌入其他子模组。

嵌套 JAR 在游戏运行时会被提取到磁盘上。 嵌套 JAR 的路径必须相对于其所属 JAR 的根目录进行声明。

## 入口点 {#entrypoints}

Fabric Loader 采用基于[入口点](../getting-started/project-structure#entrypoints)的系统，用于向 Fabric Loader 或其他模组暴露特定代码以进行初始化。

初始化器在游戏启动早期被加载和调用，这使得模组能够通过代码进行修改。 这些入口点通常用于通过注册注册表对象、事件监听器和其他回调来引导模组，以便后续操作。

## Mixin {#mixin}

Mixin 允许模组转换 Minecraft 的类，甚至转换其他模组的类。它们是 Fabric Loader 官方支持的唯一一种类转换类型。 模组可以声明自己的 Mixin 配置来启用 Mixin 的使用。

Fabric Loader 使用了原始 Sponge Mixin 的修改分支。 不过，[上游 Mixin Wiki](https://github.com/SpongePowered/Mixin/wiki) 的大部分内容仍然适用。

Fabric 的修改包括：允许在构造函数中使用所有默认注入点、优化掉未使用的回调信息、提供向后兼容性修复、修复静态阴影、允许在接口中使用注入器等等。

## 映射 {#mappings}

::: info

映射仅在混淆处理的游戏（包括 Minecraft 26.1 之前的版本）上使用 Fabric Loader 时才有意义。

:::

Fabric Loader 提供 `MappingResolver` API，用于根据模组可能加载的不同环境来确定类、字段和方法的名称。 只要 Fabric Loader 能够访问用于解析名称的映射，这就可以用于在任何环境中支持反射。

```java
FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "net.minecraft.class_5421") // Resolves to `RecipeBookType` on named versions of 1.21.11
```

当在混淆处理游戏的非开发环境中启动时，Fabric Loader 会将游戏 JAR [重新映射](../porting/mappings/index#mappings)为中介名。 针对混淆处理游戏设计的模组预期被映射到中介，这将与该环境兼容。 重新映射后的 JAR 会被缓存并保存在 `${gameDir}/.fabric/remappedJars/${minecraftVersion}` 中，以便在多次启动间重复使用。
