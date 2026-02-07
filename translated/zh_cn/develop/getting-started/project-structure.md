---
title: 项目结构
description: Fabric 模组项目结构概述
authors:
  - IMB11
---

本页将介绍 Fabric 模组项目的结构，以及项目中每个文件和文件夹的作用。

## `fabric.mod.json` {#fabric-mod-json}

`fabric.mod.json` 文件是描述你的模组给 Fabric Loader 的主文件。 它包含模组的 ID、版本和依赖关系等信息。

`fabric.mod.json` 文件中最重要的字段是：

- `id`：模组的 ID，应该是唯一的。
- `name`：模组的名称。
- `environment`：你的模组运行环境，可以是 `client`（仅客户端）、`server`（仅服务端）和 `*`（双端）。
- `entrypoints`：你的模组提供的入口点，例如 `main` 或 `client` 。
- `depends`：模组的依赖模组/库。
- `mixins`：模组提供的 Mixin。

你可以看到下面的一个 `fabric.mod.json` 文件示例——这是为此文档站点提供支持的模组的 `fabric.mod.json` 文件。

:::details 示例模组的 `fabric.mod.json`

@[code lang=json](@/reference/latest/src/main/resources/fabric.mod.json)

:::

## 入口点 {#entrypoints}

如前所述，`fabric.mod.json` 文件包含一个名为 `entrypoints` 的字段——该字段用于指定你的模组提供的入口点。

模板模组生成器默认创建 `main` 和 `client` 入口点：

- `main`入口点用于通用代码，它包含在一个实现了 `ModInitializer` 的类中
- `client`入口点用于客户端特定代码，其类实现了 `ClientModInitializer`

这些入口点将会在游戏启动时依次调用。

这是一个简单的 `main` 入口点示例，当游戏启动时，它会向控制台记录一条消息：

@[code lang=java transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

## `src/main/resources` {#src-main-resources}

`src/main/resources` 用于存储你的模组使用的资源文件，例如纹理、模型和声音。

它也是 `fabric.mod.json` 和模组使用的 Mixin 配置文件的存放位置。

资源文件存储在与资源包结构相对应的结构中——例如，方块的纹理会存储在 `assets/example-mod/textures/block/block.png` 中。

## `src/client/resources` {#src-client-resources}

`src/client/resources` 文件夹用于存储客户端的特定资源，例如仅在客户端使用的纹理、模型和音效。

## `src/main/java` {#src-main-java}

`src/main/java` 文件夹用于存储模组的 Java 源代码——在客户端和服务端环境中都存在。

## `src/client/java`{#src-client-java}

`src/client/java` 文件夹用于存储特定于客户端的 Java 源代码，例如渲染代码或客户端逻辑——例如方块颜色提供程序。
