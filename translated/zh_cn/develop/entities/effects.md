---
title: 状态效果
description: 学习如何添加自定义状态效果。
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
authors-nogithub:
  - siglong
  - tao0lu
---

# 状态效果{#status-effects}

状态效果，又称效果，是一种可以影响实体的状况， 可以是正面、负面或中性的。 游戏本体通过许多不同的方式应用这些效果，如食物和药水等等。

命令 `/effect` 可用来给实体应用效果。

## 自定义状态效果{#custom-status-effects}

在这篇教程中我们将加入一个叫 _土豆_ 的新状态效果，每游戏刻给你 1 点经验。

### 继承 `StatusEffect`{#extend-statuseffect}

让我们通过继承所有状态效果的基类 `StatusEffect` 来创建一个自定义状态效果类。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### 注册你的自定义状态效果{#registering-your-custom-effect}

与注册方块和物品类似，我们使用 `Registry.register` 将我们的自定义状态效果注册到 `STATUS_EFFECT` 注册表。 这可以在我们的初始化器内完成。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### 纹理{#texture}

状态效果的图片是一个 18x18 的 PNG，显示在玩家的背包中。 将你的自定义图标放在：

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

<DownloadEntry type="Example Texture" visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png" />

### 翻译{#translations}

像其它翻译一样，你可以在语言文件中添加一个 ID 格式的条目 `"effect.<mod-id>.<effect-identifier>": "Value"`。

```json
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### 测试{#testing}

使用命令 `/effect give @p fabric-docs-reference:tater` 为玩家提供 Tater 效果。
使用 `/effect clear @p fabric-docs-reference:tater` 移除效果。

:::info
要创建使用此效果的药水，请参阅[药水](../items/potions)指南。
:::
