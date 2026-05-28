---
title: 类调整器
description: 了解什么是类调整器，以及如何进行设置。
authors:
  - cassiancc
  - Earthcomputer
  - its-miroma
  - MildestToucan
---

类调整器（Class Tweakers）过去称为访问加宽器（Access Wideners），在获得更多功能后更名。它提供了与 Mixin 字节码操作互为补充的转换工具， 同时也允许部分运行时修改在开发环境中可见。

::: warning

类调整器并不特定于某个 Minecraft 版本，但仅从 Fabric Loader 0.18.0 和 Loom 1.12 开始可用，并且只能作用于原版 Minecraft 类。

:::

## 设置 {#setup}

### 文件格式 {#file-format}

按照惯例，类调整器文件会以你的 modid 命名，例如 `example-mod.classtweaker`，以便 IDE 插件识别。 该文件应存放在 `resources` 中。

文件第一行必须包含以下头部：

```classtweaker
classTweaker  v1  official
```

某些功能可能需要高于 `v1` 的版本，这会在对应页面中说明。

类调整器文件可以包含空行，也可以包含以 `#` 开头的注释。 注释也可以从一行的末尾开始。

具体语法会因所使用的功能而有所不同，但每项修改都会作为一个“条目”单独声明在一行中，并以一个“指令”开头，用于指定要应用的修改类型。
条目中的各个元素可以使用任意空白字符分隔，包括制表符。

#### 传递性条目 {#transitive-entries}

为了让依赖你的模组的其他模组也能在反编译源码中看到你的更改，需要在指令前添加 `transitive-` 前缀：

```classtweaker:no-line-numbers
# Transitive Access Widening directives
transitive-accessible
transitive-extendable
transitive-mutable

# Transitive Interface Injection directive
transitive-inject-interface

# Transitive Enum Extension directive
transitive-extend-enum
```

### 指定文件位置 {#specifying-the-file-location}

你必须在你的 `build.gradle` 和 `fabric.mod.json` 文件中指定类调整器文件的位置。 请注意，若要使用类调整器，你的模组必须基于 Fabric Loader 0.18.0 或更高版本。

为了保持向后兼容，这些配置项仍然沿用访问加宽器的名称。

#### build.gradle {#build-gradle}

@[code lang=gradle:no-line-numbers transcludeWith=:::classtweaker-setup:gradle:::](@/reference/latest/build.gradle)

#### fabric.mod.json {#fabric-mod-json}

```json:no-line-numbers
...

"accessWidener": "example-mod.classtweaker",

...
```

在 `build.gradle` 文件中指定文件位置后，请确保在 IDE 中重新加载 Gradle 项目。

## 验证文件 {#validating-the-file}

默认情况下，类调整器会忽略那些引用了不存在的修改目标的条目。 若要检查文件中指定的所有类、字段和方法是否有效，请运行 `validateAccessWidener` Gradle 任务。

错误信息会指出无效的条目，但未必会明确说明该条目中的哪一部分无效。
