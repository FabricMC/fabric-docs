---
title: 方块模型
description: 一份编写和理解方块模型的指南。
authors:
  - PEQB1145
---

<!-- markdownlint-disable search-replace -->

本页将指导你如何编写自己的方块模型，并了解其所有的选项和可能性。

## 什么是方块模型？ {#what-are-block-models}

方块模型本质上定义了方块的外观和视觉效果。它们指定了纹理、模型平移、旋转、比例及其他属性。

模型以 JSON 文件的形式存储在你的 `resources` 文件夹中。

## 文件结构 {#file-structure}

每个方块模型文件都有一个必须遵循的固定结构。它以一个表示模型**根标签**的空花括号开始。以下是方块模型结构的一个简要示意：

```json
{
  "parent": "...",
  "ambientocclusion": "true/false",
  "display": {
    "<position>": {
      "rotation": [0.0, 0.0, 0.0],
      "translation": [0.0, 0.0, 0.0],
      "scale": [0.0, 0.0, 0.0]
    }
  },
  "textures": {
    "particle": "...",
    "<texture_variable>": "..."
  },
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

<!--@include: ../items/item-models.md#parent-->

将此标签设置为 `builtin/generated` 可使用根据指定图标生成的模型。旋转可以通过[方块状态](./blockstates)来实现。

### 环境光遮蔽 {#ambient-occlusion}

```json
{
  "ambientocclusion": "true/false"
}
```

此标签指定是否使用[环境光遮蔽](https://en.wikipedia.org/wiki/Ambient_occlusion)。默认为 `true`。

<!--@include: ../items/item-models.md#display-->

### 纹理 {#textures}

```json
{
  "textures": {
    "particle": "...",
    "<texture_variable>": "..."
  }
}
```

`textures` 标签以标识符或纹理变量的形式保存模型的纹理。它包含三个附加对象：

1.  `particle`：*字符串*。定义粒子效果所使用的纹理。该纹理也用于下界传送门内的叠加效果、静止的水和熔岩的纹理。它也被视为纹理变量，可通过 `#particle` 引用。
2.  `<texture_variable>`：*字符串*。创建一个变量并分配一个纹理。之后可通过 `#` 前缀引用（例如，`"top": "namespace:path"` ⇒ `#top`）

<!--@include: ../items/item-models.md#elements-->

<!--@include: ../items/item-models.md#from-->

`from` 根据 `[x, y, z]` 模式指定长方体的起点（相对于左下角）。`to` 指定终点。一个标准大小的方块将从 `[0, 0, 0]` 开始并在 `[16, 16, 16]` 结束。
两者的值必须介于 **-16** 到 **32** 之间，这意味着每个方块模型的最大尺寸为 3×3 个方块。

<!--@include: ../items/item-models.md#rotation-->

`rotation` 定义元素的旋转。它包含四个值：

1.  `origin`：*三个浮点数*。根据 `[x, y, z]` 模式设置旋转中心。
2.  `axis`：*字符串*。指定旋转方向，必须是 `x`、`y` 或 `z` 之一。
3.  `angle`：*浮点数*。指定旋转角度。范围从 **-45** 到 **45**。
4.  `rescale`：*布尔值*。指定是否在整个方块上缩放面。默认为 `false`。

<!--@include: ../items/item-models.md#shade-to-faces-->

1.  `uv`：*四个整数*。根据 `[x1, y1, x2, y2]` 模式定义要使用的纹理区域。如果未设置，则默认为等于元素 xyz 坐标的值。
    交换 `x1` 和 `x2` 的值（例如从 `0, 0, 16, 16` 到 `16, 0, 0, 16`）会翻转纹理。UV 是可选的，如果没有提供，则根据元素的位置自动生成。
2.  `texture`：*字符串*。以[纹理变量](#textures)的形式指定面的纹理，前缀为 `#`。
3.  `cullface`：*字符串*。可以是：`down`、`up`、`north`、`south`、`west` 或 `east`。指定当指定位置有方块接触时，是否不需要渲染该面。
    它还确定用于照亮该面的方块光源来自哪一侧；如果未设置，则默认使用该侧。
4.  `rotation`：*整数*。以 90 度为增量，按指定度数顺时针旋转纹理。旋转不影响使用了纹理的哪一部分。
    相反，它相当于对选定的纹理顶点（隐式选定或通过 `uv` 显式选定）进行排列。
5.  `tintindex`：*整数*。使用色调值对该面的纹理进行着色。默认值 `-1` 表示不使用色调。
    任何其他数字将传递给 `BlockColors` 以获取相应索引的色调值（当方块未定义色调索引时返回白色）。

## 来源与链接 {#sources-and-links}

如需更详细的操作说明，你可以访问 Minecraft Wiki 的[方块模型页面](https://minecraft.wiki/w/Model#Block_models)。本页中的许多信息来自该页面。
