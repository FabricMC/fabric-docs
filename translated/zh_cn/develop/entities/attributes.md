---
title: 实体属性
description: 了解如何向实体添加自定义属性。
authors:
  - cassiancc
  - cprodhomme
---

属性决定了你模组中的实体会呈现哪些特点。 使用 Fabric 可以创建自己的自定义属性，增强玩法机制，同时也可以应用原版的属性。

## 创建自定义属性{#creating-a-custom-attribute}

我们来创建一个新的属性，叫做 `AGGRO_RANGE`，仇恨范围。 这个属性会控制实体可以检测并对潜在威胁作出反应的距离。

### 定义属性类{#define-the-attribute-class}

开始先创建一个 Java 类，从而在你的模组的代码结构中，管理你的属性的定义和注册。 这个例子会在叫做 `ModAttributes` 的类中创建以下的函数。

首先是一个基本的辅助方法，以注册模组的属性。 此方法接收以下参数并注册属性。

- 一个字符串，你的属性的名称
- 双精度浮点数，属性的默认值
- 双精度浮点数，属性可达到的最小值
- 双精度浮点数，属性可达到的最大值
- 布尔值，决定属性是否同步至客户端

@[code lang=java transcludeWith=:::register](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

我们注册一个叫做 `AGGRO_RANGE` 的属性，名称为 `aggro_range`，默认值 `8.0`，最小值 `0`，最大值设置为能多高就多高。 属性不会向玩家同步。

@[code lang=java transcludeWith=:::attributes](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

### 翻译自定义属性{#attribute-translation}

以人类可读的方式显示属性名称，需要修改 `assets/example-mod/lang/en_us.json`（以及同目录层级下的 `zh_cn.json`）以包含：

```json
{
  "attribute.name.example-mod.aggro_range": "Aggro Range"
}
```

### 初始化{#initialization}

要确保能正确注册属性，需要确保在模组初始阶段被初始化。 可以在类中添加公共、静态的初始化方法，然后在你的[模组的初始化](../getting-started/project-structure#entrypoints)类中调用。 当前，方法不需要里面有任何东西。

@[code lang=java transcludeWith=:::initialize](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

@[code lang=java transcludeWith=:::init](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ExampleModAttributes.java)

对类调用一个方法会静态初始化，如果还没有加载的话——这意味着所有的 `static` 字段都会计算。 这就是这个占位的 `initialize` 的方法的目的。 这就是这个占位的 `initialize` 的方法的目的。

## 应用属性{#apply-the-attribute}

属性需要附着到实体以生效。 这通常是在实体属性被构建或修改的方法中完成的。

原版也提供属性，包括[最大生命值](https://zh.minecraft.wiki/w/属性/最大生命值)、[移动速度](https://zh.minecraft.wiki/w/属性/速度)和[攻击伤害](https://zh.minecraft.wiki/w/属性/攻击伤害)，如下所示。 完整列表可见 `Attributes` 类以及 [Minecraft Wiki](https://zh.minecraft.wiki/w/属性)。

作为演示，我们包含最大生命值、移动速度、攻击伤害以及刚刚创建的仇恨范围属性。

<!-- TODO: move to the reference mod -->

```java
public static AttributeSupplier.Builder createEntityAttributes() {
    return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 25.0)
        .add(Attributes.MOVEMENT_SPEED, 0.22)
        .add(Attributes.ATTACK_DAMAGE, 3.0)
        .add(ModAttributes.AGGRO_RANGE, 8.0);
}
```

## 读取和修改属性{#reading-modifying-attributes}

属性本身只是附着在实体上的数据。 要让属性有用，需要从中读取和写入。 这有两种方式，获取实体的 `AttributeInstance`，或直接获取值。

```java
entity.getAttribute(ModAttributes.AGGRO_RANGE) // returns an `AttributeInstance`
entity.getAttributeValue(ModAttributes.AGGRO_RANGE) // returns a double with the current value
entity.getAttributeBaseValue(ModAttributes.AGGRO_RANGE) // returns a double with the base value
```

`AttributeInstance` 更灵活些，例如为属性设置 `AttributeModifier`，使用[三种原版属性修饰符运算](https://zh.minecraft.wiki/w/属性#运算模式)中的一个。 属性可以是持久（存储至 NBT）或临时（不存储至 NBT）的，分别可以使用 `addPermanentModifier` or `addTransitiveModifier` 添加。

```java
attribute.addPermanentModifier(
    new AttributeModifier(
        Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "increased_range"), // the ID of your modifier, should be static so it can be removed
        8, // how much to modify it
        AttributeModifier.Operation.ADD_VALUE // what operator to use, see the wiki page linked above
    ));
```

只要能访问属性的值，就可以在实体的 AI 中使用。
