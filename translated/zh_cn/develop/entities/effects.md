---
title: 生物效果
description: 学习如何添加自定义生物效果。
authors:
  - dicedpixels
  - Friendly-Banana
  - Manchick0
  - SattesKrokodil
  - TheFireBlast
  - YanisBft
authors-nogithub:
  - siglong
  - tao0lu
---

生物效果，也称为状态效果或简称效果，是一种可以影响实体的状态。 可以是正面、负面或中性的。 游戏本体通过许多不同的方式应用这些效果，如食物和药水等等。

命令 `/effect` 可用来给实体应用效果。

## 自定义生物效果 {#custom-mob-effects}

在这篇教程中我们将加入一个叫 _土豆_ 的新状态效果，每游戏刻给你 1 点经验。

### 继承 `MobEffect` {#extend-mobeffect}

让我们通过继承所有状态效果的基类 `MobEffect` 来创建一个自定义状态效果类。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### 注册你的自定义状态效果{#registering-your-custom-effect}

与注册方块和物品类似，我们使用 `Registry.register` 将我们的自定义状态效果注册到 `MOB_EFFECT` 注册表。 这可以在我们的初始化器内完成。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/ExampleModEffects.java)

### 纹理{#texture}

生物效果图标是 18x18 的 PNG，出现在玩家的物品栏屏幕中。 将你的自定义图标放在：

```text:no-line-numbers
resources/assets/example-mod/textures/mob_effect/tater.png
```

<DownloadEntry visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png">示例纹理</DownloadEntry>

### 翻译 {#translations}

像其他翻译一样，你可以在语言文件中添加一个 ID 格式的条目 `"effect.example-mod.effect-identifier": "值"`。

```json
{
  "effect.example-mod.tater": "Tater"
}
```

### 应用效果{#applying-the-effect}

不妨看看你会如何将效果应用到实体。

::: tip

为了快速测试，使用前面提到的 `/effect` 命令可能是更好的方案：

```mcfunction
effect give @p example-mod:tater
```

:::

要在代码内部应用状态效果，需要使用 `LivingEntity#addMobEffect` 方法，接收一个 `MobEffectInstance` 实例，返回布尔值，以表示效果是否成功应用了。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/ReferenceMethods.java)

| 参数          | 类型                  | 描述                                                                          |
| ----------- | ------------------- | --------------------------------------------------------------------------- |
| `effect`    | `Holder<MobEffect>` | 代表效果的 holder。                                                               |
| `duration`  | `int`               | 效果的时长，单位为**刻**，而非**秒**                                                      |
| `amplifier` | `int`               | 效果等级的倍率。 不是与效果的**等级**直接对应，而是有增加的。 比如，`amplifier` 为 `4` => 等级为 `5`           |
| `ambient`   | `boolean`           | 这个有些棘手， 基本上是指定效果是由环境（比如**信标**）施加的，没有直接原因。 如果是 `true`，HUD内的效果图标会以青色覆盖层的形式出现。 |
| `particles` | `boolean`           | 是否显示粒子。                                                                     |
| `icon`      | `boolean`           | 是否在 HUD 中显示效果的图标。 效果会在物品栏中显示，无论其设置的属性。                                      |

::: info

要创建使用此效果的药水，请参阅[药水](../items/potions)指南。

:::

<!---->
