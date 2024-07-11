---
title: 自定义物品组
description: 学习如何创建自己的物品组，并往里面添加物品。
authors:
  - IMB11
---

# 自定义物品组{#custom-item-groups}

物品组是创造模式物品栏内存储物品的标签页。 你可以创建自己的物品组，从而在单独的标签页内存储物品。 如果你的模组添加许多物品，需要保持组织在你的玩家容易访问的一个位置中，这就非常有用。

## 创建物品组{#creating-the-item-group}

创建物品组极其容易。 只要在你的物品类中简单创建一个新的 static final 字段，存储物品组以及注册表键，就可以使用物品组事件，类型于给原版物品组添加物品的方式：

@[code transcludeWith=:::9](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::_12](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

<hr />

你现在应该可以在物品栏菜单内看到物品组了。 然而还没有翻译——必须给你的翻译文件添加翻译键——类似于翻译你的第一个物品的方式。

![创建模式菜单内没有翻译的物品组](/assets/develop/items/itemgroups_0.png)

## 添加翻译键{#adding-a-translation-key}

如果在物品组的 builder 内，为 `displayName` 方法使用了 `Text.translatable`，就需要往语言文件添加翻译。

```json
{
    "itemGroup.fabric_docs_reference": "Fabric Docs Reference"
}
```

现在可以看到，物品组应该被正确命名了。

![完全完成的物品组，有翻译和物品](/assets/develop/items/itemgroups_1.png)
