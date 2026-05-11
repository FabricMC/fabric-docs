---
title: 列舉擴充
description: 了解如何使用 Mixin 與類別調整器向列舉新增條目。
authors:
  - cassiancc
  - CelDaemon
  - its-miroma
  - Jab125
  - LlamaLad7
  - MildestToucan
---

列舉擴充是 Mixin 的一項功能，可可靠地向列舉新增條目。

當目標是 Minecraft 列舉時，你可以將 Mixin 與[類別調整](../class-tweakers)搭配使用，讓新的列舉條目顯示在反編譯原始碼中。 如果將其設為[傳遞性](../class-tweakers/index#transitive-entries)，依賴你的模組的其他模組也會看到你新增的條目。

::: warning

列舉擴充需要至少 Loader 0.19.0 才能取得 Mixin 支援，並且需要至少 Loom 1.16 才能取得類別調整器支援。

此外，類別調整器檔案標頭必須指定 `v2` 作為版本，才能使用列舉擴充。

:::

## 建立 Mixin {#creating-the-mixin}

在建立 Mixin 類別之前，請先確認你的 `fabric.mod.json` 檔案中，已明確將 Loader 0.19.0 或以上版本列為相依項目：

```json:no-line-numbers
...
"depends": {
  ...
  "fabricloader": ">=0.19.0"
  ...
}
...
```

即使你已在 Gradle 相依項目中使用正確的 Loader 版本，也必須明確依賴至少 0.19.0 版本，才能選擇啟用這項 Mixin 功能。

若要建立列舉擴充，請在你的 Mixin 套件中建立一個 `enum`，以 `@Mixin` 標註它，然後像是把常數加入目標列舉類別本身一樣，將你的常數加進去。 例如，讓我們為 `RecipeBookType` 新增一個條目：

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeBookTypeMixin.java#enum-extension-no-impls-example-mixin

:::warning 重要

你應始終使用你的模組 ID 作為所新增列舉常數的前綴，以確保唯一性。 在這些文件中，我們會使用 `EXAMPLE_MOD_`。

:::

### 傳遞建構子引數 {#passing-constructor-arguments}

如果目標列舉沒有預設建構子，你必須 shadow 目標類別中的建構子，並在新增條目的宣告中傳入所需引數。

例如，讓我們新增一個 `RecipeCategory` 條目。 建立一個與目標類別中所需建構子相符的建構子，並以 `@Shadow` 標註它。

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeCategoryMixin.java#enum-extension-ctor-impls-example-mixin

### 實作抽象方法 {#implementing-abstract-methods}

若要實作目標列舉的抽象方法，請 shadow 該抽象方法，然後在你新增的條目中覆寫並實作它。 例如，讓我們新增一個 `ConversionType` 條目：

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/ConversionTypeMixin.java#enum-extension-abstract-method-impls-example-mixin

## 建立類別調整器條目 {#making-the-class-tweaker-entry}

如果你的目標是 Minecraft 列舉，可以使用類別調整器條目，在反編譯原始碼中明確修改目標列舉。

若要選擇啟用此功能，請記得使用 Loom 1.16 或以上版本，並將[檔案標頭版本](../class-tweakers/index#file-format)設為 `v2`。

列舉擴充條目的語法如下：

```:no-line-numbers
extend-enum  <targetClassName>  <ENUM_CONSTANT_NAME>
```

對於類別調整，類別會使用其[內部名稱](../mixins/bytecode#class-names)。

例如，我們在 [Mixin 章節](#creating-the-mixin)中新增的 `RecipeBookType` 常數，其類別調整器條目會是：

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#enum-extension-no-impls-example-entry{classtweaker:no-line-numbers}

## 套用變更 {#applying-changes}

你必須重新整理 Gradle 專案並[重新產生原始碼](../getting-started/generating-sources)，才能在反編譯原始碼中看到新增的列舉條目。
若修改沒有出現，可以嘗試[驗證](../class-tweakers/index#validating-the-file)檔案，並檢查是否出現任何錯誤。

::: info

你不會在反編譯原始碼中看到[已傳遞的建構子引數](#passing-constructor-arguments)、[方法實作](#implementing-abstract-methods)或其他元素。
這是因為這些內容由 Mixin 處理，並且只會在執行時期套用。

:::

現在你可以在程式碼中使用該列舉常數：

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-usage-example

如果你只透過 Mixin 新增它，且它不在反編譯原始碼中，可以透過比較名稱來檢查它：

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-no-ct-usage-example-check

如果你需要在多個地方使用該常數，請呼叫 `valueOf` 取得它，並將結果儲存在欄位中：

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-no-ct-usage-example-store

## 注意事項 {#pitfalls}

列舉擴充無法保證你新增的條目不會破壞任何東西。

你有責任檢查目標列舉的使用情況，並盡可能預防問題。 如果某些問題無法解決且導致崩潰，最好完全不要使用列舉擴充。

本節會說明擴充列舉時需要注意並避免的一些模式，但並非完整清單。

### Switch 運算式 {#switch-expressions}

Switch 陳述式常用於處理列舉常數。 因此，如果某個 switch 運算式沒有處理由其他模組新增的條目，就可能發生崩潰。 例如，假設我們有以下 switch 運算式：

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-problematic-switch-expr-example

請注意，這裡沒有 `default` 子句。 即使我們已經處理了原版列舉中的所有值，以及我們自己新增的值，如果另一個模組新增了不同條目，這裡仍會擲出例外。

要如何避免這種情況？ 沒有單一通用的方法可以避免崩潰——你的做法應視具體情況調整。 不過，一般而言：

- 如果 `switch` 運算式位於原版方法中，你可以使用 Mixin 修改它。
- 如果 `switch` 運算式來自某個模組，你應嘗試聯絡開發者，一起制定相容的做法。 否則，你可能需要對另一個模組建立 Mixin。

### 序列化列舉 {#serialized-enums}

某些列舉的條目會自動被序列化。 例如 `Axolotl` 類別中的 `Variants` 列舉。

擴充這些列舉會在 Minecraft 的命名空間下序列化你的自訂條目；在某些版本中，這甚至可能基於數值 ID 發生。
這並不理想，因為它可能影響所有其他條目的索引。

如果列舉條目會以這種方式序列化，最好完全避免擴充該列舉。 取而代之的是，如果有可用 API，你可能應該尋找並使用它。
