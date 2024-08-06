---
title: 自定义盔甲
description: 学习如何创建自己的盔甲集。
authors:
  - IMB11
---

# 自定义盔甲{#custom-armor}

盔甲增强玩家的防御，抵御来自生物和其他玩家的攻击。

## 创建盔甲材料类{#creating-an-armor-materials-class}

和类似于物品和方块类似，盔甲材料也需要注册。 为了组织好，我们创建一个 `ModArmorMaterials` 类存储我们的自定义盔甲材料。

需要为这个类添加一个静态的 `initialize()` 方法，并从模组的初始化器中调用，从而注册这些材料。

```java
// Within the ModArmorMaterials class
public static void initialize() {};
```

:::warning
确保在注册你的物品**之前**调用这个方法，因为需要注册了材料才能创建物品。
:::

```java
@Override
public void onInitialize() {
  ModArmorMaterials.initialize();
}
```

---

在这个 `ModArmorMaterials` 类中，你还会需要创建一个静态方法，注册盔甲材料。 这个方块应该会返回材料的一个注册表项，因为这个项会用于 ArmorItem 的构造方法中创建盔甲物品。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

## 盔甲材料属性{#armor-material-properties}

:::tip
如果不清楚这些属性的哪些值合适的话，考虑看看 `ArmorMaterials` 中的原版盔甲材料。
:::

创建盔甲材料时，你会需要定义以下属性：

### 防御点{#defense-points}

:::warning
确保为创建的每种盔甲部分的类型都赋一个值，并注册为物品。 如果为盔甲部分制作物品但没有设置防御点值，游戏会崩溃。
:::

`defensePoints` 映射用于定义每个盔甲物品会提供的防御点的数量。 数字越高，盔甲部分提供的保护越多。 这个映射应该包含每个盔甲部分的类型的项。

### 附魔能力{#enchantability}

`enchantability` 属性定义了物品被附魔有多容易。 数字越高，盔甲可以接受更高的附魔。

### 装备声音{#equip-sound}

`equipmentSound` 属性是盔甲装备时会播放的声音。 这个声音应该是 `SoundEvent` 的注册表项。 如果你考虑创建自定义的声音，而不是依靠 `SoundEvents` 类中的原版声音，可以考虑看看[自定义声音事件](../sounds/custom)页面。

### 修复原料{#repair-ingredient}

`repairingIngredientSupplier` 属性是用于修复盔甲的 `Ingredient`（原料）的 supplier。 这个原料什么都可以是，推荐将其保持与事实上用于合成盔甲物品的合成原料相同的材料。

### 坚硬度{#toughness}

`toughness` 属性定义了盔甲会吸收多少伤害。 数字越高，盔甲吸收的伤害越多。

### 击退抗性{#knockback-resistance}

`knockbackResistance` 属性定义了玩家被击中时会反弹多少击退。 数字越高，玩家接收的击退越少。

### 可染色{#dyeable}

`dyable` 属性是布尔值，定义了盔甲是否可染色。 如果设置为 `true`，那么盔甲可以使用染料在工作台中染色。

如果选择让你的盔甲可染色，就必须将你的盔甲层和物品纹理_设计为可染色_，因为染料会叠加在纹理上，而不是替代纹理。 看看原版的皮革装备作为示例，纹理是灰度的，染色应用为叠加层，使盔甲改变颜色。

## 注册盔甲材料{#registering-the-armor-material}

现在创建好了注册盔甲材料的实用方法，可以注册你的自定义盔甲材料了，作为 `ModArmorMaterials` 类的静态字段。

看这个例子，我们创建 Guidite 盔甲，有以下属性：

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

## 创建盔甲物品{#creating-the-armor-items}

现在材料已经注册了，就可以在你的 `ModItems` 类中创建盔甲物品。

显然，盔甲集并不需要满足每种类型，可以让你的集只有靴或护腿等——原版的海龟壳头盔就是个例子，盔甲集缺了部分槽位。

### 耐久度{#durability}

不像 `ToolMaterial`，`ArmorMaterial` 并不储存物品的耐久度信息。
因此当注册盔甲物品时耐久度需要被手动添加到它的 `Item.Settings` 里。

这是使用 `Item.Settings` 类中 `maxDamage` 方法实现的。
不同的盔甲槽位有不同的基础耐久，通常都会在乘以一个共享的盔甲材料倍率，但也可以使用硬编码的值。

对于 Guidite 盔甲，我们使用存储在盔甲材料中的共享盔甲倍率。

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

然后，使用耐久度常数创建盔甲物品。

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

如果需要让物品能从创造模式物品栏获取的话，还需要**给将物品添加到物品组**。

就像所有物品一样，要为这些物品创建翻译键。

## 给予纹理和模型{#texturing-and-modelling}

你会需要创建两套纹理：

- 用于物品本身的纹理和模型。
- 盔甲穿在实体身上时可见的真正盔甲模型。

### 物品纹理和模型{#item-textures-and-model}

这些纹理和其他物品没有区别——必须创建纹理、创建一般的 generated 的物品模型，这在[创建你的第一个物品](./first-item#adding-a-texture-and-model)指南中有讲到。

例如，你可以使用下面的纹理和模型 JSON 作为参考。

<DownloadEntry type="Item Textures" visualURL="/assets/develop/items/armor_0.png" downloadURL="/assets/develop/items/example_armor_item_textures.zip" />

:::info
你需要所有物品的模型 JSON 文件，不只是头盔，这原则和其他物品模型一样。
:::

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/guidite_helmet.json)

可以看到，游戏内的盔甲物品应该有合适的模型：

![盔甲物品模型](/assets/develop/items/armor_1.png)

## 盔甲纹理和模型{#armor-textures-and-model}

实体穿着你的盔甲时，现在显示的还是缺失纹理：

![玩家身上的损坏的盔甲模型](/assets/develop/items/armor_2.png).

盔甲纹理有两层，都要有。

因为我们这个例子中，盔甲的材料名称是 `guidite`，所以文本的位置会是：

- `assets/<mod-id>/textures/models/armor/guidite_layer_1.png`
- `assets/<mod-id>/textures/models/armor/guidite_layer_2.png`

<DownloadEntry type="Armor Model Textures" noVisualURL="true" downloadURL="/assets/develop/items/example_armor_layer_textures.zip" />

第一层包含头盔和胸甲的纹理，第二层包含护腿和靴的纹理。

这些纹理存在时，你应该能够看到实体穿着的盔甲了：

![玩家身上的生效的盔甲模型](/assets/develop/items/armor_3.png).
