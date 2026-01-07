---
title: 物品模型
description: 一篇编写和理解物品模型的指南。
authors:
  - Fellteros
  - its-miroma
  - VatinMc
---

<!-- markdownlint-disable search-replace -->

本页将指导你编写自己的物品模型并了解其所有选项和可能性。

## 什么是物品模型？ {#what-are-item-models}

物品模型本质上是对物品外观和视觉效果的定义。 其指定了纹理、模型平移、旋转、缩放和其他属性。

模型以 JSON 文件的形式存储在你的 `resources` 文件夹中。

## 文件结构 {#file-structure}

每个物品模型文件都有一个必须遵循的定义结构。 它以空的花括号开头，代表模型的**根标签**。 以下是物品模型结构的简要示意：

```json
{
  "parent": "...",
  "display": {
    "<position>": {
      "rotation": [0.0, 0.0, 0.0],
      "translation": [0.0, 0.0, 0.0],
      "scale": [0.0, 0.0, 0.0]
    }
  },
  "textures": {
    "<layerN>": "...",
    "particle": "...",
    "<texture_variable>": "..."
  },
  "gui_light": "...",
  "elements": [
    {
      "from": [0.0, 0.0, 0.0],
      "to": [0.0, 0.0, 0.0],
      "rotation": {
        "origin": [0.0, 0.0, 0.0],
        "axis": "...",
        "angle": "...",
        "rescale": "true/false"
      },
      "shade": "true/false",
      "light_emission": "...",
      "faces": {
        "<key>": {
          "uv": [0, 0, 0, 0],
          "texture": "...",
          "cullface": "...",
          "rotation": "...",
          "tintindex": "..."
        }
      }
    }
  ]
}
```

<!-- #region parent -->

### 父级 {#parent}

```json
{
  "parent": "..."
}
```

从给定路径加载一个包含所有属性的不同模型，并以标识符（`namespace:path`）的形式加载。

<!-- #endregion parent -->

将此标签设置为 `item/generated` 即可使用基于指定图标创建的模型；设置为 `builtin/generated` 即可使用不包含任何平移、旋转或缩放的模型。

<!-- #region display -->

### 显示 {#display}

```json
{
  "display": {
    "<position>": {
      "rotation": [0.0, 0.0, 0.0],
      "translation": [0.0, 0.0, 0.0],
      "scale": [0.0, 0.0, 0.0]
    }
  }
}
```

此标签负责设置模型在指定位置的平移、旋转和缩放。

位置对象可以是以下字符串之一，用于定义模型在不同位置的外观：

| 值                       | 说明                        |
| ----------------------- | ------------------------- |
| `firstperson_righthand` | 右手，以第一人称视角                |
| `firstperson_lefthand`  | 左手，以第一人称视角                |
| `thirdperson_righthand` | 右手，以第三人称视角（<kbd>F5</kbd>） |
| `thirdperson_lefthand`  | 左手，以第三人称视角（<kbd>F5</kbd>） |
| `gui`                   | 在 GUI 中，例如物品栏             |
| `head`                  | 戴在玩家头上，例如旗帜               |
| `ground`                | 在地面上                      |
| `fixed`                 | 放在物品展示框中                  |

此外，每个位置可以包含以下三个值，以浮点数组的形式：

```json
{
  "rotation": [0.0, 0.0, 0.0],
  "translation": [0.0, 0.0, 0.0],
  "scale": [0.0, 0.0, 0.0]
}
```

1. `rotation`：_三个浮点数_。 根据方案 `[x, y, z]` 指定模型的旋转。
2. `translation`：_三个浮点数_。 根据 `[x, y, z]` 格式指定模型的平移。 该值必须介于 `-80` 到 `80` 之间；超出此范围的值将被设为最接近的极值。
3. `scale`：_三个浮点数_。 根据 `[x, y, z]` 格式指定模型的缩放。 最大值为 `4`，更大的值将被视为 `4`。

<!-- #endregion display -->

### 纹理 {#textures}

```json
{
  "textures": {
    "<layerN>": "...",
    "particle": "...",
    "<texture_variable>": "..."
  }
}
```

`textures` 标签以标识符或纹理变量的形式保存模型的纹理。 它包含三个附加对象：

1. `<layerN>`：_字符串_。 指定物品栏中使用的物品图标。 可以有多个图层（例如刷怪蛋）且最多 3 个。
2. `particle`：_字符串_。 定义从中加载粒子的纹理。 如果未定义，则使用 `layer0`。
3. `<texture_variable>`：_字符串_。 创建一个变量并分配一个纹理。 稍后可以使用 `#` 前缀引用（例如 `"top": "namespace:path"` ⇒ `#top`）。

:::warning 重要
仅当 `parent` 设为 `item/generated` 时 `<layerN>` 才有效！
:::

### GUI 光照 {#gui-light}

```json
{
  "gui_light": "..."
}
```

此标签定义物品模型的照明方向。 可以是 `front` 或 `side`，默认为后者。 如果设为 `front`，则模型渲染为平面物品；如果设为 `side`，则模型渲染为方块。

<!-- #region elements -->

### 元素 {#elements}

```json
{
  "elements": [
    {
      "from": [0.0, 0.0, 0.0],
      "to": [0.0, 0.0, 0.0],
      "rotation": {
        "origin": [0.0, 0.0, 0.0],
        "axis": "...",
        "angle": "...",
        "rescale": "true/false"
      },
      "shade": "true/false",
      "light_emission": "...",
      "faces": {
        "<face>": {
          "uv": [0, 0, 0, 0],
          "texture": "...",
          "cullface": "...",
          "rotation": "...",
          "tintindex": "..."
        }
      }
    }
  ]
}
```

包含模型的所有元素，且只能是立方体。 如果同时设置了 `parent` 和 `elements` 标签，则该文件的 `elements` 标签将覆盖父级的 `elements` 标签。

<!-- #endregion elements -->

<!-- #region from -->

```json
{
  "from": [0.0, 0.0, 0.0],
  "to": [0.0, 0.0, 0.0]
}
```

<!-- #endregion from -->

`from` 指定长方体的起点，按照 `[x, y, z]` 格式，相对于左下角。 `to` 指定终点。 一个与标准方块大小相同的长方体将从 `[0, 0, 0]` 开始，到 `[16, 16, 16]` 结束。
两者的值都必须在 **-16** 到 **32** 之间，这意味着每个物品模型的最大尺寸为 3×3 方块。

<!-- #region rotation -->

```json
{
  "rotation": {
    "origin": [0.0, 0.0, 0.0],
    "axis": "...",
    "angle": "...",
    "rescale": "true/false"
  }
}
```

<!-- #endregion rotation -->

`rotation` 定义元素的旋转。 其包含四个值：

1. `origin`：_三个浮点数_。 根据 `[x, y, z]` 格式设置旋转中心。
2. `axis`：_字符串_。 指定旋转方向，必须是以下值之一：`x`、`y` 或 `z`。
3. `angle`：_浮点数_。 指定旋转角度。 范围为 **-45** 到 **45**，增量为 22.5 度。
4. `rescale`：_布尔值_。 指定是否在整个方块上缩放面。 默认为 `false`。

<!-- #region shade-to-faces -->

```json
{
  "shade": "true/false"
}
```

`shade` 定义是否渲染阴影。 默认为 `true`。

```json
{
  "light_emission": "..."
}
```

`light_emission` 定义元素可以接收到的最小光照等级。 取值范围为 **0** 到 **15**。 默认为 0。

```json
{
  "faces": {
    "<key>": {
      "uv": [0, 0, 0, 0],
      "texture": "...",
      "cullface": "...",
      "rotation": 0,
      "tintindex": 0
    }
  }
}
```

`faces` 保存长方体的所有面。 如果某个面未设置，则不会被渲染。 它的键（`<key>`）可以是以下之一：`down`、`up`、`north`、`south`、`west` 或 `east`。 每个键包含该面的属性：

<!-- #endregion shade-to-faces -->

1. `uv`：_四个整数_。 根据 `[x1, y1, x2, y2]` 格式定义要使用的纹理区域。 如果未设置，则默认为元素 xyz 位置的值。
   翻转 `x1` 和 `x2` 的值（例如从 `0, 0, 16, 16` 到 `16, 0, 0, 16`）会翻转纹理。 UV 是可选的，如果未提供，则根据元素的位置自动生成。
2. `texture`：_字符串_。 以[纹理变量](#textures)的形式指定面的纹理，前缀为 `#`。
3. `cullface`：_字符串_。 可以为：`down`、`up`、`north`、`south`、`west` 或 `east`。 指定当在指定位置有方块接触该面时，该面是否不需要渲染。
   它还决定使用方块的哪一侧的光照等级来照亮该面，如果未设置，则默认为该侧。
4. `rotation`：_整数_。 将纹理以 90 度为增量旋转指定的度数。 旋转不会影响纹理的使用部分。
   而是相当于对所选纹理顶点（隐式选择或通过 `uv` 显式选择）进行置换。
5. `tintidex`：_整数_。 使用从客户端物品中引用的色调值为该面的纹理着色。 如果未提供色调颜色（或白色），则纹理不着色。

## 来源和链接 {#sources-and-links}

你可以访问 Minecraft Wiki 的[物品模型页面](https://zh.minecraft.wiki/w/%E6%A8%A1%E5%9E%8B#%E7%89%A9%E5%93%81%E6%A8%A1%E5%9E%8B)来获取更详细的说明。 这里的很多信息都来自该页面。
