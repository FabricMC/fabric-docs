---
title: 依赖项重写
description: 关于如何重写模组的 `fabric.mod.json` 中设置的依赖项的指南。
authors:
  - cassiancc
  - skycatminepokie
  - ytg1234
authors-nogithub:
  - kb1000
resources:
  https://semver.org/: 语义化版本控制
---

<!---->

::: warning

依赖项重写旨在让整合包开发者能够控制其使用的模组。 普通玩家不应使用此方法。

在继续之前，建议先了解[模组依赖字段的结构](../../develop/loader/fabric-mod-json#semantic-versioning)。

:::

有时在组建整合包时，你可能会遇到一些依赖要求设置得不合理的模组。例如，某个模组的要求过于严苛，指定需要 Minecraft `26.1`，尽管它在 `26.1.2` 上也能正常工作。

为了解决这个问题，Fabric Loader 允许你重写依赖要求，从而尝试在非预定版本的 Minecraft 上加载模组。

::: tip

如果可能的话，重写依赖应仅作为临时解决方案。 如果该模组仍在积极维护，请考虑在问题追踪器上报告此不兼容性，让上游开发者来处理该问题。

:::

## 设置 {#setup}

:::: info

在本示例中，我们将为 ID 为 `example-mod` 的模组使用以下 `fabric.mod.json`。 你可以随时切换代码块中的标签页，查看依赖项重写如何影响该 `fabric.mod.json`。

:::details `fabric.mod.json`

```json
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

::::

首先，在 `.minecraft/config` 文件夹内创建一个名为 `fabric_loader_dependencies.json` 的文件。

接下来，在该文件中填入以下样板内容：

::: code-group

```json [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {} // [!code highlight]
  }
}
```

```json [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

让我们逐行分析。

首先是 `version`，它指定了我们想要使用的依赖重写规范版本。 在编写本文时，最新版本为 `1`。

其次是 `overrides` 对象，它将包含我们针对各个模组的所有依赖项重写。 初始状态下，它包含一个针对 `example-mod` 的空条目，我们可以在其中添加重写内容。

模组对象内部的键可以是 5 种依赖类型之一（`depends`、`recommends`、`suggests`、`conflicts`、`breaks`）。 这些键的值必须是一个 JSON 对象。 该 JSON 对象的结构与 [`fabric.mod.json` 依赖对象](./fabric-mod-json#semantic-versioning)完全一致。

键名可以根据需要添加 `+` 或 `-` 前缀（例如 `"+depends"`、`"-breaks"`）。

::: tabs

== 使用 + 前缀

如果键名带有 `+` 前缀，该 JSON 对象中的条目将被添加（如果已存在则重写）到该模组中。

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "+depends": {
        "minecraft": ""
      }
    }
  }
}
```

== 使用 - 前缀

如果键名带有 `-` 前缀，每个条目的值都将被完全忽略，Fabric Loader 将从生成的依赖映射中移除这些条目。

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": ""
      }
    }
  }
}
```

== 无前缀

如果键名没有前缀，依赖对象将被完全替换。 **务必小心使用前缀！**

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "depends": {
        "minecraft": ""
      }
    }
  }
}
```

:::

## 重写依赖项 {#overriding-dependencies}

假设 ID 为 `example-mod` 的模组**严格**依赖于 Minecraft 版本 `26.1`，但我们希望它能在其他 26.1 版本上运行。 看看我们可以怎么做：

::: code-group

```json{5-6} [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "depends": {
        "minecraft": "26.1.x"
      }
    }
  }
}
```

```json{2,5-6} [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1.x"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

如果指定了 `"minecraft"` 依赖（我们知道确实指定了），它现在将被重写。 还有另一种方法可以实现这一点：

::: code-group

```json{5-6} [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": "IGNORED"
      }
    }
  }
}
```

```json{2,5-6} [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1.x"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

如上所述，在移除依赖项时，键 `"minecraft"` 的值将被忽略。 如果发现模组 ID 要求为 `minecraft` 的依赖项，它将从目标模组 `example-mod` 中被移除。

我们也可以重写整个 `depends` 代码块，但能力越大，责任越大。 务必小心。

除了更改 `minecraft` 依赖外，我们还想移除所有 `suggests` 依赖。 我们可以通过移除 `suggests` 键的前缀来实现，这会将其替换为空对象，从而达到清空的效果。 看起来像这样：

::: code-group

```json [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": ""
      },
      "suggests": {} // [!code highlight]
    }
  }
}
```

```json [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {} // [!code highlight]
}
```

:::

<!---->
