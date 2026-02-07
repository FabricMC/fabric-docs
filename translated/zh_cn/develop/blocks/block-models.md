---
title: 方块模型
description: 一篇编写和理解方块模型的指南。
authors:
  - Fellteros
  - its-miroma
---

<!-- markdownlint-disable search-replace -->

本页将指导你编写自己的方块模型并了解其所有选项和可能性。

## 什么是方块模型？ {#what-are-block-models}

方块模型本质上是对方块外观和视觉效果的定义。 其指定了纹理、模型平移、旋转、缩放和其他属性。

模型以 JSON 文件的形式存储在你的 `resources` 文件夹中。

## 文件结构 {#file-structure}

每个方块模型文件都有一个必须遵循的定义结构。 它以空的花括号开头，代表模型的**根标签**。 以下是方块模型结构的简要示意：

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

将此标签设置为 `builtin/generated` 以使用从指定图标创建的模型。 旋转可以通过[方块状态](./blockstates)实现。

### 环境光遮蔽 {#ambient-occlusion}

```json
{
  "ambientocclusion": "true/false"
}
```

此标签指定是否使用[环境光遮蔽](https://zh.wikipedia.org/zh-cn/%E7%8E%AF%E5%A2%83%E5%85%89%E9%81%AE%E8%94%BD)。 默认为 `true`。

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

`textures` 标签以标识符或纹理变量的形式保存模型的纹理。 它包含三个附加对象：

1. `particle`：_字符串_。 定义从中加载粒子的纹理。 此纹理也会用作站在下界传送门中时的叠加层，也用于水和熔岩的静态纹理。 它也被视为一个纹理变量，可以引用为 `#particle`。
2. `<texture_variable>`：_字符串_。 创建一个变量并分配一个纹理。 稍后可以使用 `#` 前缀引用（例如 `"top": "namespace:path"` ⇒ `#top`）。

<!--@include: ../items/item-models.md#elements-->

<!--@include: ../items/item-models.md#from-->

`from` 指定长方体的起点，按照 `[x, y, z]` 格式，相对于左下角。 `to` 指定终点。 一个与标准方块大小相同的长方体将从 `[0, 0, 0]` 开始，到 `[16, 16, 16]` 结束。
两者的值都必须在 **-16** 到 **32** 之间，这意味着每个方块模型的最大尺寸为 3×3 方块。

<!--@include: ../items/item-models.md#rotation-->

`rotation` 定义元素的旋转。 其包含四个值：

1. `origin`：_三个浮点数_。 根据 `[x, y, z]` 格式设置旋转中心。
2. `axis`：_字符串_。 指定旋转方向，必须是以下值之一：`x`、`y` 或 `z`。
3. `angle`：_浮点数_。 指定旋转角度。 范围从 **-45** 到 **45**。
4. `rescale`：_布尔值_。 指定是否在整个方块上缩放面。 默认为 `false`。

<!--@include: ../items/item-models.md#shade-to-faces-->

1. `uv`：_四个整数_。 根据 `[x1, y1, x2, y2]` 格式定义要使用的纹理区域。 如果未设置，则默认为元素 xyz 位置的值。
   翻转 `x1` 和 `x2` 的值（例如从 `0, 0, 16, 16` 到 `16, 0, 0, 16`）会翻转纹理。 UV 是可选的，如果未提供，则根据元素的位置自动生成。
2. `texture`：_字符串_。 以[纹理变量](#textures)的形式指定面的纹理，前缀为 `#`。
3. `cullface`：_字符串_。 可以为：`down`、`up`、`north`、`south`、`west` 或 `east`。 指定当在指定位置有方块接触该面时，该面是否不需要渲染。
   它还决定使用方块的哪一侧的光照等级来照亮该面，如果未设置，则默认为该侧。
4. `rotation`：_整数_。 将纹理以 90 度为增量顺时针旋转指定的度数。 旋转不会影响纹理的使用部分。
   而是相当于对所选纹理顶点（隐式选择或通过 `uv` 显式选择）进行置换。
5. `tintidex`：_整数_。 使用色调值对该面的纹理进行着色。 默认值 `-1` 表示不使用色调。
   向 `BlockColors` 传入任何其他数字即可获取与该索引对应的色调值（当方块未定义色调索引时返回白色）。

## 来源和链接 {#sources-and-links}

你可以访问 Minecraft Wiki 的[方块模型页面](https://zh.minecraft.wiki/w/%E6%A8%A1%E5%9E%8B#%E6%96%B9%E5%9D%97%E6%A8%A1%E5%9E%8B)来获取更详细的说明。 这里的很多信息都来自该页面。
