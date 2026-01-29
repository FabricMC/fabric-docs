---
title: 实体属性
description: 学习如何为实体添加自定义属性。
authors:
- PEQB1145
---

属性决定了你的模组实体可以具备的特性。通过 Fabric，你可以创建自己的自定义属性以增强游戏机制，同时也可以应用原版属性。

## 创建自定义属性 {#creating-a-custom-attribute}

让我们创建一个名为 `AGGRO_RANGE` 的自定义属性。此属性将控制实体可以侦测并响应潜在威胁的距离。

### 定义属性类 {#define-the-attribute-class}

首先，在你的模组代码结构下创建一个 Java 类来管理和注册你的属性。本示例将在名为 `ModAttributes` 的类中创建以下方法。

从一个基本的辅助方法开始，用于注册模组属性。此方法将接受以下参数并注册一个属性：

- 一个将作为属性名称的 `String`
- 一个将作为属性默认值的 `double`
- 一个将作为属性最小值的 `double`
- 一个将作为属性最大值的 `double`
- 一个决定属性是否同步到客户端的 `boolean`

@[code lang=java transcludeWith=:::register](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

然后，我们将注册一个名为 `AGGRO_RANGE` 的属性，其标识符为 `aggro_range`，默认值为 `8.0`，最小值为 `0`，最大值为尽可能高的数值。此属性不会同步到玩家。

@[code lang=java transcludeWith=:::attributes](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

### 自定义属性的翻译 {#attribute-translation}

要以人类可读的格式显示属性名称，你必须在 `assets/example-mod/lang/en_us.json` 中添加：

```json
{
    "attribute.name.example-mod.aggro_range": "仇恨范围"
}
```

### 初始化 {#initialization}

为确保属性正确注册，你需要在模组启动时对其进行初始化。此操作可以通过向你的类中添加一个公共静态初始化方法，并从你的[模组初始化器](../getting-started/project-structure#entrypoints)类中调用它来完成。目前，此方法内部不需要任何内容。

@[code lang=java transcludeWith=:::initialize](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

@[code lang=java transcludeWith=:::init](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ExampleModAttributes.java)

静态调用类上的方法会（如果之前未加载）触发其静态初始化——这意味着所有 `static` 字段都会被求值。这就是这个空置的 `initialize` 方法的作用。

## 应用属性 {#apply-the-attribute}

属性需要附加到实体上才能生效。这通常在构建或修改实体属性的方法中完成。

原版也提供了一系列属性，包括[最大生命值](https://minecraft.wiki/w/Attribute#Max_health)、[移动速度](https://minecraft.wiki/w/Attribute#Movement_speed)和[攻击伤害](https://minecraft.wiki/w/Attribute#Attack_damage)（如下所示）。完整列表请参见原版 `Attributes` 类和 [Minecraft Wiki](https://minecraft.wiki/w/Attribute)。

作为演示，我们将为实体添加最大生命值、移动速度、攻击伤害以及之前创建的仇恨范围属性。

<!-- TODO: 移至参考模组 -->
```java
public static AttributeSupplier.Builder createEntityAttributes() {
    return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 25.0)
        .add(Attributes.MOVEMENT_SPEED, 0.22)
        .add(Attributes.ATTACK_DAMAGE, 3.0)
        .add(ModAttributes.AGGRO_RANGE, 8.0);
}
```

## 读取与修改属性 {#reading-modifying-attributes}

属性本身只是附加到实体上的数据。要使它发挥作用，我们需要能够读取和写入它。主要有两种方式：获取实体上的 `AttributeInstance`，或直接获取值。

```java
entity.getAttribute(ModAttributes.AGGRO_RANGE) // 返回一个 `AttributeInstance`
entity.getAttributeValue(ModAttributes.AGGRO_RANGE) // 返回当前值的 double
entity.getAttributeBaseValue(ModAttributes.AGGRO_RANGE) // 返回基础值的 double
```

`AttributeInstance` 允许更灵活的操作，例如为该属性设置一个 `AttributeModifier`，这可以使用[原版三种属性修饰符运算](https://minecraft.wiki/w/Attribute#Operations)中的任意一种。修饰符可以是永久的（保存到 NBT）或临时的（不保存到 NBT），分别通过 `addPermanentModifier` 或 `addTransitiveModifier` 添加。

```java
attribute.addPermanentModifier(
    new AttributeModifier(
        ResourceLocation.fromNamespaceAndPath(示例模组.MOD_ID, "范围扩大"), // 修饰符的 ID，应设为静态以便可以被移除
        8, // 修改量的大小
        AttributeModifier.Operation.ADD_VALUE // 使用的运算符，请参见上方链接的 Wiki 页面
    ));
```

一旦获取到属性值，你就可以在实体的 AI 中使用它。
