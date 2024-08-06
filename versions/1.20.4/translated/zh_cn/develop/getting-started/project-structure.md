---
title: 项目结构
description: Fabric 模组项目结构概述
authors:
  - IMB11

search: false
---

# 项目结构{#project-structure}

本页将介绍 Fabric 模组项目的结构以及项目中每个文件和文件夹的用途。

## `fabric.mod.json`{#fabric-mod-json}

`fabric.mod.json` 是向 Fabric Loader 描述你的模组的主要文件， 包含模组的 ID、版本、依赖等信息。

`fabric.mod.json` 文件最重要的字段是：

- `id`：模组的 ID，必须是独特的，不能和其他模组重复。
- `name`：模组的显示名称。
- `environment`：模组运行环境，可以是 `client`（仅客户端）、`server`（仅服务端）和 `*`（双端）。
- `entrypoints`：模组提供的入口点，例如 `main` 和 `client` 等。
- `depends`：模组的依赖模组/库。
- `mixins`：模组提供的 Mixin。

下面看到的是示例的 `fabric.mod.json` 文件——这是该文档的开发参考项目的 `fabric.mod.json` 文件。

:::details 参考项目 `fabric.mod.json`
@[code lang=json](@/reference/latest/src/main/resources/fabric.mod.json)
:::

## 入口点{#entrypoints}

如前所述，`fabric.mod.json` 文件包含一个名为 `entrypoints` 的字段——该字段用于指定你的模组提供的入口点。

模组开发模板生成器默认创建 `main` 和 `client` 入口点——`main` 入口点用于双端共用部分，`client` 入口点用于客户端特定部分。 这些入口点将会在游戏启动时依次调用。

@[code lang=java transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

上面是一个简单的 `main` 入口点的使用示例，会在游戏开始时记录一条消息到控制台。

## `src/main/resources`{#src-main-resources}

`src/main/resources` 用于存储模组的资源文件，例如纹理、模型和声音。

它也是 `fabric.mod.json` 和模组使用的 Mixin 配置文件的存放位置。

资源文件存储在与资源包结构相似的结构中——例如，方块的纹理会存放在 `assets/modid/textures/block/block.png` 中。

## `src/client/resources`{#src-client-resources}

`src/client/resources` 文件夹用于存储客户端特定的资源，例如仅在客户端使用的纹理、模型和音效。

## `src/main/java`{#src-main-java}

`src/main/java` 文件夹用于存储模组的 Java 源代码——在客户端和服务端环境中都存在。

## `src/client/java`{#src-client-java}

`src/client/java` 文件夹用于存储客户端专属的 Java 源代码，例如渲染代码或客户端逻辑——例如方块颜色提供程序。
