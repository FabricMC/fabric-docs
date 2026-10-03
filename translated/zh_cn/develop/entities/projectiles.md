---
title: 自定义弹射物
description: 学习如何添加自定义弹射物。
authors:
  - ayutac
  - cassiancc
  - CelDaemon
  - ChampionAsh5357
  - dicedpixels
  - Earthcomputer
  - ekulxam
  - haykam
  - kanpov
  - NetUserGet
  - onlyspxctre
  - patrickmsm
  - tianjun
  - upcraftlp
resources:
  https://minecraft.wiki/w/Projectile: 弹射物 - Minecraft Wiki
  https://docs.neoforged.net/docs/entities/#projectiles: 弹射物 - NeoForge 文档
---

弹射物是可由玩家或其他实体投掷或发射的实体。在本指南中，我们将了解如何实现像雪球一样的简单弹射物。

我们将我们的弹射物称为 Hot Tater。它将是一个能够点燃所击中的方块或实体的马铃薯。

:::info 前置知识

创建弹射物需要你注册一个物品以及一个实体，因此我们建议先阅读[创建你的第一个物品](../items/first-item)和[创建你的第一个实体](./first-entity)指南。

:::

## 创建弹射物实体 {#creating-the-projectile-entity}

让我们通过继承 `ThrowableItemProjectile` 来创建 `HotTaterEntity`。此类应位于你的 `main` 源集中。

`ThrowableItemProjectile` 类处理弹射物的物理特性和物品形式。

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#entity

这里包含相当多的内容。让我们看看重要的代码部分。

### 构造函数 {#constructors}

我们定义了 3 个构造函数。它们分别用于实体注册、弹射物生成和弹射物转换。

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#constructors

### 重写 `getDefaultItem()` {#override-get-default-item}

定义此弹射物的物品形式。

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#default_item

:::warning 重要

你的 IDE 可能会提示无法解析该物品：我们很快就会在[注册](#registration)小节中创建它。

:::

### 重写 `onHitBlock()` {#override-on-hit-block}

定义此弹射物击中方块时的行为。我们检查弹射物击中的位置，然后点燃该方块被击中的面。此逻辑在服务端进行处理。

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_block

### 重写 `onHitEntity()` {#override-on-hit-entity}

定义此弹射物击中实体时的行为。我们将被击中的实体点燃 5 秒。

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_entity

### 重写 `onHit()` {#override-on-hit}

定义此弹射物击中任何目标（无论方块还是实体）时的行为。我们将用它来清除弹射物，以便在击中时将其移除；如果不这样做，弹射物将会继续穿过。

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit

## 创建物品 {#creating-the-item}

我们注册一个简单的物品。由于我们需要实现投掷逻辑，我们的 `HotTaterItem` 类将继承 `Item` 并实现 `ProjectileItem`。此类应位于你的 `main` 源集中。

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#item

这是一个标准的物品实现，并带有来自 `ProjectileItem` 的一些特殊方法。让我们看看它们：

### 重写 `asProjectile()` {#override-as-projectile}

此方法将物品转换为其实体形态。

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#as_projectile

### 重写 `use()` {#override-use}

定义使用该物品时发生的动作。在我们的示例中，我们调用 `Projectile.spawnProjectileFromRotation()` 工具方法来生成弹射物。

除了标准参数（level、物品堆叠和玩家）外，该工具方法还接受三个额外的浮点数：

- `yOffset`：俯仰角（绕 X 轴旋转，向上或向下）的偏移量，单位为度。负值会将初始速度朝上方倾斜。
- `pow`：弹射物运动速度的乘数。
- `uncertainty`：弹射物的不精准度。 0 表示没有随机散射。随着该值的增大，即使以相同的初始位置和旋转角度投掷，弹射物的散射范围也会更大。

有关更多信息，请参阅 [Minecraft Wiki 上关于弹射物的文章](https://minecraft.wiki/w/Projectile#Initial_conditions)。

:::details 为什么这个参数被称为 `yOffset`？

亲爱的读者，我们其实也不知道。尽管名称如此，[该偏移量实际上应用于俯仰角](https://mcsrc.dev/2/26.2/net/minecraft/world/entity/projectile/Projectile#L156)，即 _速度_ 绕 X 轴的旋转：

```java
float yd = -Mth.sin((xRot + yOffset) * (float) (Math.PI / 180.0));
```

例如，当原版为[滞留/喷溅药水使用 `-20.0F` 的 `yOffset`](https://mcsrc.dev/2/26.2/net/minecraft/world/item/ThrowablePotionItem#L28) 时，它会将初始速度俯仰角从 `source.getXRot()` 更改为 `source.getXRot() - 20.0F`（向上偏转 20 度，指向天空）。

也许叫 `xRotOffset` 会是更合适的名字。

:::

最后，我们授予 `ITEM_USED` 统计数据，从物品堆叠中消耗一个物品，并将交互标记为成功。

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#use

## 注册 {#registration}

注册该物品，就像我们在[创建你的第一个物品](../items/first-item#registering-an-item)指南中所做的两样。首先，在 `ModItemIds` 中定义物品的键：

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#hot_tater

然后在 `ModItems` 中与其他物品一起注册该物品：

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#hot_tater

不要忘记使用标识符 `hot_tater` 添加[模型](../items/first-item#adding-a-model)、[纹理](../items/first-item#adding-a-texture)、[客户端物品](../items/first-item#creating-the-client-item)和[名称](../items/first-item#naming-the-item)。你还应该[将物品添加到创造模式标签页](../items/first-item#adding-the-item-to-a-creative-tab)。这是一个纹理示例：

<DownloadEntry visualURL="/assets/develop/projectiles/hot_tater_preview.png" downloadURL="/assets/develop/projectiles/hot_tater.png">纹理</DownloadEntry>

请确保将新实体的 ID 添加到 `ModEntityTypeIds` 中：

<<< @/reference/latest/src/main/java/com/example/docs/entity/ModEntityTypeIds.java#hot_tater

现在注册实体，就像我们在[创建你的第一个实体](./first-entity#preparing-your-first-entity)指南中所做的那样，通过在 `ModEntityTypes` 中将其添加为静态字段。由于实体和物品存在于不同的注册表中，实体类型只需使用与物品相同的路径，类似于原版的 `snowball`：

<<< @/reference/latest/src/main/java/com/example/docs/entity/ModEntityTypes.java#hot_tater

最后，让我们在客户端初始化器中使用原版的 `ThrownItemRenderer`：

<<< @/reference/latest/src/client/java/com/example/docs/projectile/ExampleModProjectileClient.java#renderer

然后就大功告成了！

<VideoPlayer src="/assets/develop/projectiles/hot-tater.mp4">Hot Tater 点燃了村民</VideoPlayer>
