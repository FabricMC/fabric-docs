---
title: 伤害类型
description: 学习如何添加自定义伤害类型。
authors:
  - dicedpixels
  - hiisuuii
  - mattidragon
---

# 伤害类型{#damage-types}

伤害类型定义了实体能受到的伤害的种类。 从 Minecraft 1.19.4 开始，创建新的伤害类型的方式已是数据驱动，也就是说由 JSON 文件创建。

## 创建伤害类型{#creating-a-damage-type}

让我们创建一种叫 _土豆_ 的伤害类型。 我们先从为你的自定义伤害创建 JSON 文件开始。 这个文件放在你的模组的 `data` 目录下的 `damage_type` 子目录。

```:no-line-numbers
resources/data/fabric-docs-reference/damage_type/tater.json
```

其结构如下：

@[code lang=json](@/reference/latest/src/main/generated/data/fabric-docs-reference/damage_type/tater.json)

这个自定义伤害类型在玩家每次受到来自非玩家的生物（例：方块）造成的伤害时增加 0.1 [消耗度](https://zh.minecraft.wiki/w/饥饿#饥饿因素)。 此外，造成的伤害量将随世界难度而变化。

::: info

所有可用的键值详见 [Minecraft Wiki](https://zh.minecraft.wiki/w/伤害类型/JSON格式)。

:::

### 通过代码访问伤害类型{#accessing-damage-types-through-code}

当需要在代码中访问我们的自定义伤害类型时，可以用它的 `RegistryKey` 来创建一个 `DamageSource` 实例。

这个 `RegistryKey` 可用以下方式获取：

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/FabricDocsReferenceDamageTypes.java)

### 使用伤害类型{#using-damage-types}

为了演示自定义伤害类型如何使用，我们将使用一个自定义方块 _土豆块_ 。 让我们实现生物踩在 _土豆块_ 上时会造成 _土豆_ 伤害。

你可以重写 `onSteppedOn` 方法来造成这个伤害。

我们从创建一个属于我们的自定义伤害类型的 `DamageSource` 开始。

@[code lang=java transclude={21-24}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

然后，调用 `entity.damage()` 并传入我们的 `DamageSource` 和伤害量。

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

方块的完整实现：

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

现在，每当生物踩在我们的自定义方块上时，都将受到使用我们的自定义伤害类型的 5 点伤害（2.5 颗心）。

### 自定义死亡消息{#custom-death-message}

你可以在你的模组的 `en_us.json` 文件中以 `death.attack.<message_id>` 的格式定义伤害类型的死亡信息。

@[code lang=json transclude={4-4}](@/reference/latest/src/main/resources/assets/fabric-docs-reference/lang/en_us.json)

当死因是我们的伤害类型时，你将会看到如下的死亡信息：

![玩家物品栏内的效果](/assets/develop/tater-damage-death.png)

### 伤害类型标签{#damage-type-tags}

有些伤害类型能够无视护甲、无视状态效果等等。 伤害类型的这些属性是由标签控制的。

你可以在 `data/minecraft/tags/damage_type` 中找到既有的伤害类型标签。

::: info

全部伤害类型的列表详见 [Minecraft Wiki](https://zh.minecraft.wiki/w/标签#伤害类型)。

:::

让我们把我们的土豆伤害类型加入伤害类型标签 `bypasses_armor`。

要将我们的伤害类型加入这些标签，需要在 `minecraft` 命名空间下创建一个 JSON 文件。

```:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

包含以下内容：

@[code lang=json](@/reference/latest/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json)

将 `replace` 设置为 `false` 以确保你的标签不会替换既有的标签。
