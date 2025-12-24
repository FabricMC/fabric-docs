---
title: 物品模型
description: 一份关于编写和理解物品模型的指南。
authors:
  - PEQB1145
---
<!-- markdownlint-disable search-replace -->

本页面将引导你编写自己的物品模型，并了解其所有的选项与可能性。

## 什么是物品模型？{#what-are-item-models}

物品模型本质上定义了物品的外观和视觉效果。它指定了纹理、模型平移、旋转、缩放以及其他属性。

模型以JSON文件的形式存储在你的 `resources`（资源）文件夹中。

## 文件结构 {#file-structure}

每个物品模型文件都有必须遵循的固定结构。它以一对花括号开始，表示模型的**根标签**。以下是物品模型结构的一个简要概览：

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

### 父模型{#parent}

```json
{
  "parent": "..."
}
```

从给定路径（作为标识符 `namespace:path`）加载另一个模型及其所有属性。

<!-- #endregion parent -->

将此标签设置为 `item/generated` 以使用根据指定图标创建的模型；或将其设置为 `builtin/generated` 以使用没有任何平移、旋转或缩放的模型。

<!-- #region display -->

### 显示属性{#display}

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

此标签负责在指定位置设置模型的平移、旋转和缩放。

位置对象可以是以下字符串之一，定义了模型在不同位置下的外观：

| 数值 | 描述 |
|-------------------------|-----------------------------------------------------|
| `firstperson_righthand` | 第一人称视角下的右手 |
| `firstperson_lefthand`  | 第一人称视角下的左手 |
| `thirdperson_righthand` | 第三人称视角下的右手（<kbd>F5</kbd>切换视角） |
| `thirdperson_lefthand`  | 第三人称视角下的左手（<kbd>F5</kbd>切换视角） |
| `gui`                   | 在GUI中时，例如物品栏 |
| `head`                  | 放置在玩家头部时，例如旗帜 |
| `ground`                | 在地面上时 |
| `fixed`                 | 放置在物品展示框中时 |

此外，每个位置可以包含以下三个值，形式为浮点数数组：

```json
{
  "rotation": [0.0, 0.0, 0.0],
  "translation": [0.0, 0.0, 0.0],
  "scale": [0.0, 0.0, 0.0]
}
```

1. `rotation`：*三个浮点数*。按照 `[x, y, z]` 格式指定模型的旋转。
2. `translation`：*三个浮点数*。按照 `[x, y, z]` 格式指定模型的平移。值必须在 `-80` 到 `80` 之间；超出此范围的值将被设置为最接近的极端值。
3. `scale`：*三个浮点数*。按照 `[x, y, z]` 格式指定模型的缩放。最大值为 `4`，大于该值视为 `4`。

<!-- #endregion display -->

### 纹理{#textures}

```json
{
  "textures": {
    "<layerN>": "...",
    "particle": "...",
    "<texture_variable>": "..."
  }
}
```

`textures` 标签以标识符或纹理变量的形式保存模型的纹理。它包含三个附加对象：

1. `<layerN>`：*字符串*。指定物品在物品栏中使用的图标。可以有多个图层（例如，刷怪蛋），最多3层。
2. `particle`：*字符串*。定义粒子效果所使用的纹理。若未定义，则使用 `layer0`。
3. `<texture_variable>`：*字符串*。创建一个变量并分配一个纹理。之后可以添加 `#` 前缀进行引用（例如 `"top": "namespace:path"` ⇒ `#top`）。

::: warning 重要
只有当 `parent` 设置为 `item/generated` 时，`<layerN>` 才有效！
:::

### GUI光照{#gui-light}

```json
{
  "gui_light": "..."
}
```

此标签定义物品模型的光照方向。可以是 `front`（前向）或 `side`（侧面），默认为 `side`。如果设置为 `front`，模型将像扁平物品一样渲染；如果设置为 `side`，则像方块一样渲染。

<!-- #region elements -->

### 元素{#elements}

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

包含模型的所有元素，且元素只能是立方体。如果同时设置了 `parent` 和 `elements` 标签，则本文件的 `elements` 标签将覆盖父模型的相应标签。

<!-- #endregion elements -->

<!-- #region from -->

```json
{
  "from": [0.0, 0.0, 0.0],
  "to": [0.0, 0.0, 0.0]
}
```

<!-- #endregion from -->

`from` 按照 `[x, y, z]` 格式指定立方体的起点，相对于左下角。`to` 指定终点。一个与标准方块等大的立方体将从 `[0, 0, 0]` 开始，到 `[16, 16, 16]` 结束。
两者的值必须在 **-16** 到 **32** 之间，这意味着每个物品模型最大可以为 3×3 个方块大小。

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

`rotation` 定义一个元素的旋转。它包含另外四个值：

1. `origin`：*三个浮点数*。按照 `[x, y, z]` 格式设置旋转中心。
2. `axis`：*字符串*。指定旋转方向，必须是以下之一：`x`、`y` 和 `z`。
3. `angle`：*浮点数*。指定旋转角度。范围为 **-45** 到 **45** 度，以22.5度为增量。
4. `rescale`：*布尔值*。指定是否将面缩放以覆盖整个方块。默认为 `false`。

<!-- #region shade-to-faces -->

```json
{
  "shade": "true/false"
}
```

`shade` 定义是否渲染阴影。默认为 `true`。

```json
{
  "light_emission": "..."
}
```

`light_emission` 定义元素可以接收的最小光照等级。范围在 **0** 到 **15** 之间。默认为0。

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

`faces` 包含立方体的所有面。如果未设置某个面，则该面将不会被渲染。其键（`<key>`）可以是以下之一：`down`、`up`、`north`、`south`、`west` 或 `east`。每个键包含该面的属性：

<!-- #endregion shade-to-faces -->

1. `uv`：*四个整数*。按照 `[x1, y1, x2, y2]` 格式定义要使用的纹理区域。如果未设置，则默认值为元素的xyz位置。
   交换 `x1` 和 `x2` 的值（例如从 `0, 0, 16, 16` 到 `16, 0, 0, 16`）会翻转纹理。UV是可选的，如果未提供，则会根据元素位置自动生成。
2. `texture`：*字符串*。以[纹理变量](#textures)的形式指定面的纹理，并添加 `#` 前缀。
3. `cullface`：*字符串*。可以是：`down`、`up`、`north`、`south`、`west` 或 `east`。指定当指定位置有其他方块接触时，该面是否不必渲染。
   它还决定了用于照亮该面的光照等级的方块侧面，如果未设置，则默认为侧面照明。
4. `rotation`：*整数*。以90度为增量，将纹理旋转指定的度数。旋转不会影响使用纹理的哪个部分。
   相反，它相当于对选定的纹理顶点（通过 `uv` 隐式或显式选定）进行排列。
5. `tintindex`：*整数*。使用客户端物品引用的着色值为该面的纹理着色。如果未提供着色颜色（或提供白色），则纹理不会着色。

## 来源与链接{#sources-and-links}

你可以访问 Minecraft 维基的 [物品模型页面](https://minecraft.wiki/w/Model#Item_models) 以获取更详细的说明。本文档的许多信息都来自该页面。
