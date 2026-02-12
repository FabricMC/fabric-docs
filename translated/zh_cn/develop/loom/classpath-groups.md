---
title: 类路径组
description: Fabric Loom 类路径组功能文档。
authors:
  - modmuss50
---

Loom 提供了一个 DSL，用于配置 Fabric 加载器的类路径分组系统属性。 这使得 Fabric 加载器能够将不同的类路径条目分组在一起，这对于将代码拆分为多个源集的模组非常有用，例如客户端代码和通用代码，或者通用代码和平台特定代码。 这对于确保模组资源正确加载至关重要。 如果你的单个模组由多个源集构建而成，则应在 `loom.mods` 代码块中定义所有源集，以确保 Fabric 加载器能够正确分组它们。 此功能仅在你的开发环境中运行游戏时生效，不会影响构建生成的最终模组 jar 文件（因为所有内容都打包在一个 jar 文件中）。

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            sourceSet sourceSets.client
        }
    }
}
```

在上面的示例中，`example-mod` 由两个源集构建而成：`main` 和 `client`。 Loom 会配置 Fabric 加载器，将这两个源集分组到同一个类路径组下，以确保它们在运行时正确加载。

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            sourceSet sourceSets.client
        }
        "example-mod-test" {
            sourceSet sourceSets.testmod
        }
    }
}
```

在上面的示例中，`example-mod-test` 是由单个源集 `testmod` 构建的。 Loom 会配置 Fabric 加载器，将此源集分组到其自己的类路径组中，使其与 `example-mod` 分开。

## 子项目 {#multi-project}

当你希望定义跨越多个 Gradle 项目的模组时（在多平台设置中很常见），你可以通过指定源集名称和项目路径来实现。

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            sourceSet("main", ":core")
        }
    }
}
```

如果你依赖的项目未使用 Loom，则必须将 `net.fabricmc.fabric-loom-companion` 插件应用于该项目。 这样，Loom 项目才能以符合 Gradle 最佳实践的方式访问所需数据。 此插件仅公开类路径组所需的信息，不应用 Loom 的任何其他功能。

```groovy
plugins {
    id 'net.fabricmc.fabric-loom-companion'
}
```

## 已着色依赖项 {#shaded-dependencies}

如果你要将依赖项着色到你的模组 jar 文件中，你还得在 `loom.mods` 代码块中定义包含这些已着色依赖项的配置。 这可以确保 Fabric 加载器能够将已着色依赖项正确地与你的模组代码分组。 你不应该对其他模组依赖项或你使用 `include` 进行 jar 嵌套的依赖项这样做。

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            configuration configurations.shade
        }
    }
}
```
