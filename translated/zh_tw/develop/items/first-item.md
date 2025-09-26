---
title: 製作你的第一個物品
description: 了解如何登錄一個簡單的物品，以及如何為物品加入紋理、模型和名稱。
authors:
  - dicedpixels
  - Earthcomputer
  - IMB11
  - RaphProductions
---

本頁面將向你介紹一些與物品相關的重要概念，以及如何登錄物品、設定紋理、建立模型及命名物品。

如果你不知道的話，Minecraft的所有事物都儲存在登錄中，物品也不例外。

## 準備你的物品類別 {#preparing-your-items-class}

為了簡化登錄物品的流程，你可以建立一個方法，這個方法接受ID字串、物品設定，以及建立`Item`實例的「工廠方法」。

這個方法會使用傳入的ID建立一個物品，並將其登錄到遊戲的物品登錄中。

你可以把這個方法放在叫做 `ModItems`（或是其他你想要的名稱）的類別中 。

Mojang對物品也是這麼做的！ 看看 `Items` 類別以了解。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

注意這個工廠方法使用了介面 [`Function`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Function.html)，它允許我們稍後能夠指定 `Item::new` 作為透過物品設定建立物品的方法。

## 登錄物品 {#registering-an-item}

現在你可以用這個方法來登錄物品。

物品的登錄方法接受一個 'Item.Settings\` 類別的實例作為參數。 這個類別允許你透過各式各樣的「建造方法」設定物品的屬性。

::: tip
If you want to change your item's stack size, you can use the `maxCount` method in the `Item.Settings` class.

如果物品具有耐久，則這不會生效，因為對於任何具有耐久的物品，其堆疊大小永遠是1，以避免重複損壞。
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

`Item::new` 會告訴註冊方法，透過呼叫接收一個`Item.Settings` 作為參數的 `Item` 建構方法（`new Item(...)`），以從 `Item.Settings` 創建一個 `Item` 實例。

然而，若你嘗試執行修改過的用戶端，會發現我們的物品沒有被加入到遊戲中！ 這是因為你還沒有靜態初始化類別。

要這麼做，你需要在類別中添加靜態的初始化方法，然後在你的[模組的初始化類別](../getting-started/project-structure#entrypoints)中呼叫。 目前，這個方法裡不需要有任何東西。

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ExampleModItems.java)

呼叫一個類別裡的方法時，如果該類別尚未載入，就會進行靜態初始化——這意味著所有`static`欄位都會計算。 這就是這個虛擬 `initialize` 方法存在的目的。

## 把物品添加進物品群組{#adding-the-item-to-an-item-group}

:::info
如果想要將物品添加進自訂 `ItemGroup` 中，請參閱 [自訂物品群組](./custom-item-groups)。
:::

舉例來說，如果我們想將這個物品加進「原材料」物品群組中，你會需要使用 Fabric API 的「物品群組事件」——即 `ItemGroupEvents.modifyEntriesEvent`。

這可以在你的物品類別中的 `initialize` 方法中完成。

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

載入遊戲，你就會看到我們的物品已經登錄，並顯示在「原材料」物品群組中了：

![原材料群組中的物品](/assets/develop/items/first_item_0.png)

然而，這個物品還缺少了：

- 物品模型
- 紋理
- 翻譯（名稱）

## 給物品命名{#naming-the-item}

這個物品現在還沒有翻譯，所以你需要為其添加。 Minecraft本身已經提供了這個物品的翻譯鍵： `item.mod_id.suspicious_substance`。

在 `src/main/resources/assets/mod-id/lang/` 中建立一個JSON檔案，名為 `zh_tw.json`（繁體中文，英文則是`en_us.json`），並輸入翻譯鍵及其值：

```json
{
  "item.mod_id.suspicious_substance": "Suspicious Substance"
}
```

你可以選擇重啟遊戲，或者建構模组並按下<kbd>F3</kbd>+<kbd>T</kbd>，以套用變更。

## 添加紋理與模型{#adding-a-texture-and-model}

要為你的物品提供紋理與模型，只需要建立一個 16x16 的紋理圖像並儲存在 `assets/mod-id/textures/item` 資料夾中。 根據物品的ID命名紋理檔案。別忘了 `.png`副檔名。

例如，你可以將這個範例紋理作為 `suspicious_substance.png`

<DownloadEntry visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png">紋理</DownloadEntry>

重啟/重新載入遊戲後，你應該會發現物品還沒有紋理，因為你還需要為物品添加一個使用此紋理的模型。

你可以建立一個簡單的 `item/generated` 模型——它只接受一個紋理，僅此而已。

在 `assets/mod-id/models/item` 資料夾中，建立模型JSON，檔名與物品ID相同；`suspicious_substance.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/suspicious_substance.json)

### 解析模型JSON{#breaking-down-the-model-json}

- `parent` 這個模型繼承的父模型。 在這個例子中，是 `item/generated` 模型。
- `textures`：為模型定義紋理的地方。 `layer0` 鍵是模型要使用的紋理。

大多數物品都會繼承 `item/generated` 模型，因為它是個僅用於顯示紋理的模型。

除此之外，還有其他模型，例如 `item/handheld` 模型，用於表示玩家手中「持有」的物品，如工具。

## 建立物品模型描述{#creating-the-item-model-description}

Minecraft並不知道我們把模型檔案放在哪裡，因此我們需要提供物品模型描述。

在 `assets/mod-id/items` 資料夾中建立物品模型描述JSON，檔案名稱與物品ID相同：`suspicious_substance.json`。

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/suspicious_substance.json)

### 解析物品模型映射JSON{#breaking-down-the-item-model-description-json}

- `model`：包含對我們模型的引用的屬性。
  - `type`：模型的類型。 對於大部分物品而言，它應該是 `minecraft:model`
  - `model`：模型的ID。 它應該是這樣的形式：`mod-id:item/item_name`

現在你的物品在遊戲內應該是長這樣的：

![擁有正確模型的物品](/assets/develop/items/first_item_2.png)

## 讓物品可拿去堆肥或當作燃料{#making-the-item-compostable-or-a-fuel}

Fabric API 提供各式各樣的登錄，可以為你的物品添加額外屬性。

例如，如果你想要讓你的物品可以拿去堆肥，你可以使用 `CompostableItemRegistry`：

@[code transcludeWith=:::_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

或者，如果要讓物品可以當燃料，可以使用 `FuelRegistryEvents.BUILD`事件：

@[code transcludeWith=:::_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## 添加基本的合成配方{#adding-a-basic-crafting-recipe}

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

如果要為你的物品添加合成配方，你需要將配方JSON檔案放在 `data/mod-id/recipe` 資料夾中。

更多關於合成配方格式的資訊，可參閱以下資源：

- [配方 JSON 生成器](https://crafting.thedestruc7i0n.ca/)
- [中文 Minecraft Wiki - 配方 JSON](https://zh.minecraft.wiki/w/配方#JSON格式)

## 自訂物品提示{#custom-tooltips}

如果你想讓你的物品擁有自訂物品提示，你會需要建立繼承了 `Item` 的類別，並覆寫 `appendTooltip`方法。

:::info
這個範例使用了 `LightningStick` 類別，其於[自訂物品交互](./custom-item-interactions)頁面中建立。
:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

每呼叫一次 `add()` 就會增加一行提示。

![物品提示演示](/assets/develop/items/first_item_3.png)
