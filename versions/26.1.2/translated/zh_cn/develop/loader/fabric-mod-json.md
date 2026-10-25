---
title: fabric.mod.json
description: "`fabric.mod.json` 规范指南。"
authors:
  - cassiancc
  - falseresync
  - jamieswhiteshirt
  - IMB11
  - SolidBlock-cn
authors-nogithub:
  - skyland1a
resources:
  https://github.com/FabricMC/fabric-loom/blob/dev/1.16/src/main/java/net/fabricmc/loom/api/fmj/FabricModJsonV1Spec.java: fabric.mod.json v1 规范的源代码
  https://github.com/FabricMC/fabric-language-kotlin: Fabric Language Kotlin 的语言提供程序
  https://spdx.org/licenses/: SPDX 许可证标识符
  https://semver.org/: 语义化版本控制
  https://jubianchi.github.io/semver-check/: 语义化版本比较工具
---

`fabric.mod.json` 文件是 Fabric Loader 加载模组时使用的元数据文件。 它必须放置在 JAR 包的根目录下，模组才能被正常加载。

模组模板中包含了一个预定义的 `fabric.mod.json` 文件，你也可以[通过 Fabric Loom 生成](../loom/tasks#fabric-mod-json)它。

你可以查阅 [`fabric.mod.json` v1 规范的源代码](https://github.com/FabricMC/fabric-loom/blob/-/src/main/java/net/fabricmc/loom/api/fmj/FabricModJsonV1Spec.java)。 你还可以在下方找到本站示例模组所使用的 `fabric.mod.json` 文件。

:::details 示例模组的 `fabric.mod.json`

<<< @/reference/26.1.2/src/main/resources/fabric.mod.json

:::

## 必填字段 {#mandatory-fields}

Fabric 加载模组时必须包含以下字段。

- **`schemaVersion`** 必须是第一个条目，且值必须始终为 `1`。 这是 Fabric Loader 正确解析文件所必需的。
- **`id`** 定义模组标识符的字符串。 必须以字母开头。 仅能包含 ASCII 字母、数字、下划线或连字符。 长度为 2 到 64 个字符。
- **`version`** 定义模组版本的字符串，应符合[语义化版本 2.0.0](https://semver.org/) 规范。

```json
"schemaVersion": 1,
"id": "example-mod",
"version": "1.0.0"
```

## 元数据 {#metadata}

- **`name`**：定义易于阅读的模组名称的字符串。 如果不存在，则默认使用 **`id`**。
- **`description`**：定义模组描述的字符串。 如果不存在，则默认为空字符串。

```json
"name": "Example Mod",
"description": "This is an example description! Tell everyone what your mod is about!",
```

### 联系方式 {#contact}

**contact**：一个定义项目联系信息的字典。 一些常见字段包括：

- **`email`**：与模组相关的联系电子邮件。 必须是有效的电子邮件地址。
- **`homepage`**：项目或用户的首页。 必须是有效的 HTTP/HTTPS 地址。
- **`irc`**：与模组相关的 IRC 频道。 必须符合有效的 URL 格式，例如：EsperNet 上 `#charset` 频道的 `irc://irc.esper.net:6667/charset`，端口号可选，未指定默认为 6667。
- **`issues`**：项目的问题追踪器。 必须是有效的 HTTP/HTTPS 地址。
- **`sources`**：项目源代码仓库。 必须是有效的 URL，它可以是针对特定版本控制系统（如 Git 或 Mercurial）的特殊 URL。

此列表并未列出所有项——模组可以提供额外的非标准键值，如 **`discord`**、**`slack`**、**`twitter`**…… 如果可能，这些都应该是有效的 URL。

```json
"contact": {
    "homepage": "https://fabricmc.net",
    "sources": "https://github.com/FabricMC/fabric-example-mod"
}
```

### 作者与贡献者 {#authors-contributors}

- **authors** 模组作者的数组。 条目可以是字符串，也可以是包含下列字段的对象。
- **`contributors`** 模组贡献者的数组。 条目可以是字符串，也可以是包含下列字段的对象。

字段：

- **`name`** 必填，代表该人物的真实姓名或用户名。
- **`contact`** 可选，代表该人物的联系方式。 结构与上文的 [**`contact`**](#contact) 相同。

```json
"authors": [
    "Me!",
    {
        "name": "Tiny Potato",
        "contact": {
          "homepage": "https://fabricmc.net",
          "sources": "https://github.com/FabricMC/fabric-example-mod"
        }
    }
]
```

### 许可证 {#license}

- **`license`** 定义许可信息的字符串或数组。 为了便于自动化工具识别，建议对开源许可使用 [SPDX 许可标识符](https://spdx.org/licenses/)。

此处应提供满足整个模组包所需的最小许可集。 换句话说，任何想要使用或分发你模组的人只需遵守此处列出的许可即可。
如果你的部分代码采用双重许可，请选择首选许可。
此列表并不一定限制你针对特定人员视情况授予额外权利或应用不同的许可。

```json
"license": "CC0-1.0"
```

### 图标 {#icon}

- **`icon`** 定义模组图标的字符串或字典。 图标应为正方形 PNG 文件。 Minecraft 资源包使用 128×128，但这并非硬性要求，不过建议使用 2 的次幂。 可以采用以下两种形式之一提供：
  - 单个 PNG 文件的路径。
  - 一个将图像宽度映射到其文件路径的字典。

```json
"icon": "assets/example-mod/icon.png"
```

## 模组加载 {#mod-loading}

### 环境 {#environment}

- **`environment`**：一个字符串，定义了模组应该运行在什么环境上：
  - **`*`**：运行在任何环境上。 默认值。
  - **`client`**：运行在物理客户端。 如果设置为这个值，你的模组不会在专用服务器上加载。
  - **`server`**：运行在物理服务器。 如果设置为这个值，你的模组不会在客户端加载，包括单人游戏和局域网联机。

```json
"environment": "*"
```

### 入口点 {#entrypoints}

- **`entrypoints`** 定义了你的模组会被加载的主类的对象。
  - **`main`** 一个字符串数组，包含实现了 `ModInitializer` 的类名。
  - **`client`** 一个字符串数组，包含实现了 `ClientModInitializer` 的类名。 这个入口点会在 `main` 之后，且只在物理客户端执行。
  - **`server`** 一个字符串数组，包含实现了 `DedicatedServerModInitializer` 的类名。 这个入口点会在 `main` 之后，且只在物理服务器执行。

Fabric Loader 提供了这三个主要入口点，但其他模组也可以提供自定义入口点。 例如，Fabric API 为数据生成入口点提供了 `fabric-datagen`。

每一种入口点可以加载多个类，甚至是方法和静态字段。 例如：

```json
"main": [
    "net.fabricmc.example.ExampleMod",
    "net.fabricmc.example.ExampleMod::handle"
]
```

::: tip

如果你不在使用 Java 开发，你应该去查询对应语言适配器的文档。 对于 Kotlin，请参阅 [Fabric Language Kotlin 的 README](https://github.com/FabricMC/fabric-language-kotlin/blob/master/README.md)。

:::

### JAR {#jars}

- **`jars`**：包含需要被嵌入你的模组的 JAR 包信息的数组。 当你对依赖使用 `include`，Loom 会自动帮你写入。 每一项都是一个对象，包含一个 `file` 键，值为被嵌入的 JAR 包在你的模组内的路径。 例如：

```json
"jars": [
   {
      "file": "nested/vendor/dependency.jar"
   }
]
```

### 语言适配器 {#language-adapters}

- **`languageAdapters`**：包含实现了 `LanguageAdapter` 类的字典。

```json
"languageAdapters": {
   "kotlin": "net.fabricmc.language.kotlin.KotlinAdapter"
}
```

### Mixin {#mixins}

- **`mixins`**：包含 mixin 配置文件的列表。 每个条目可以是模组 JAR 内 Mixin 配置文件的路径，也可以是一个包含以下字段的对象：
  - **`config`**：模组 JAR 内 Mixin 配置文件的路径。
  - **`environment`**：和 [上述**环境**字段](#environment) 的行为一致。 例如：

```json
"mixins": [
   "example-mod.mixins.json",
   {
      "config": "example-mod.client-mixins.json",
      "environment": "client"
   }
]
```

### 访问加宽 {#accesswideners}

- **`accessWidener`**：指向[访问加宽或者类调整器](../class-tweakers/)文件的字符串。

```json
"accessWidener": "example-mod.classtweaker"
```

### 提供 {#provides}

- **`provides`**：作为该模组别名的模组 ID 数组。 Fabric Loader 将视这些 ID 所对应的模组已经存在。 如果有其他模组使用了其中之一，该模组将不会被加载。

```json
"provides": [
   "example_mod"
]
```

## 依赖解析 {#dependency-resolution}

以下键接受依赖项字典。 有关字典结构的更多细节，请参阅下文：

- **`depends`**：运行所需的依赖项。 **如果缺少任何一项，Fabric Loader 将触发崩溃**。
- **`recommends`**：运行非必需的依赖项。 如果缺少任何一项，Fabric Loader 将记录一条警告。
- **`suggests`**：运行非必需的依赖项。 可将其视为一种元数据。
- **`breaks`**：与你的模组同时存在可能会导致游戏崩溃的模组。 **如果存在任何一项，Fabric Loader 将触发崩溃**。
- **`conflicts`**：与你的模组同时存在会导致某些漏洞等问题的模组。 对于每个存在的冲突模组，Fabric Loader 将记录一条警告。

### 语义化版本控制 {#semantic-versioning}

每个条目的键是依赖项的模组 ID。

每个键的值是声明依赖项支持的版本范围的字符串或字符串数组。 如果是数组，则只需匹配其中一个范围即可满足约束。

以下是一些版本范围及其含义的示例。 尝试使用 [jubianchi 的 Semver 检查工具](https://jubianchi.github.io/semver-check/#/)来测试哪些值符合约束。

:::details 语义化版本控制示例

**注意：** Minecraft 并不遵循语义化版本控制。 如果需要，Fabric 会将 Minecraft 版本翻译为等效的语义化版本。 示例包括 `26.1`->`26.1.0`、`26.1-snapshot-1`->`26.1-alpha.1`、`26w14a`->`26.1.1-alpha.26.14.a`。

| 范围                             | 说明                       | 匹配项                      | 冲突项                      |
| ------------------------------ | ------------------------ | ------------------------ | ------------------------ |
| <Range r="*" />                | 任意版本（不推荐）                | `26.1.2`、`24w14potato`…… | _无_                      |
| <Range r="26.1.2" />           | 仅限确切版本                   | `26.1.2`                 | `26.1`、`26.1.1`、`26.2`…… |
| <Range r="26.1.0 || 26.1.1" /> | 任一范围                     | `26.1.0`、`26.1.1`        | `26.1.2`、`26.2`……        |
| <Range r="[26.1.0, 26.1.1]" /> | 等同于 `26.1.0 \|\| 26.1.1` | `26.1`、`26.1.1`          | `26.1.2`、`26.2`……        |
| <Range r=">26" />              | 高于某个版本（不含）               | `26.1.2`、`26.2`……        | `26`、`25.x`……            |
| <Range r=">=26.1" />           | 等于或高于某个版本（含）             | `26.1`、`26.1.2`、`26.2`…… | `26.0`、`25.x`……          |
| <Range r="<=26.1" />           | 等于或低于某个版本（含）             | `26.1`、`26.0`、`25.x`……   | `26.1.2`、`26.2`……        |
| <Range r=">26 <26.2" />        | 在两个版本之间（均不含）             | `26.1`、`26.1.2`、快照版本……   | `26`、`26.2`……            |
| <Range r=">=26.1 <26.2" />     | 在两个版本之间（含下限）             | `26.1`、`26.1.2`、快照版本……   | `26.0`、`26.2`……          |
| <Range r="26.1.x" />           | 小版本的任何补丁更新               | `26.1`、`26.1.2`、快照版本……   | `26.2`、`27.x`……          |
| <Range r="~26.1" />            | 与 `26.1.x` 相同            | `26.1`、`26.1.2`、快照版本……   | `26.2`、`27.x`……          |
| <Range r="^26.1" />            | 同一大版本下的任意版本              | `26.1.2`、`26.2`、`26.3`…… | `25.x`、`27.x`……          |

:::

```json
"depends": {
    "example-mod": "*",
    "minecraft": [
        "26.1",
        "26.1.1"
    ]
}
"suggests": {
    "another-mod": ">1.0.0"
}
```

## 自定义字段 {#custom-fields}

你可以在 `custom` 字段内添加任何想要的字段。 加载器会忽略它们。 但是，强烈建议为你的字段添加命名空间，以避免与其他模组冲突。
