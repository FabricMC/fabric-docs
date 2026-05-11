---
title: 介面注入
description: 了解如何在反編譯原始碼中，讓 Minecraft 類別實作介面。
authors-nogithub:
  - salvopelux
authors:
  - Daomephsta
  - CelDaemon
  - Earthcomputer
  - its-miroma
  - Juuxel
  - MildestToucan
  - Sapryx
  - SolidBlock-cn
---

介面注入是[類別調整](../class-tweakers/)的一種類型，用於在反編譯原始碼中，為 Minecraft 類別加入介面實作。

當介面實作能在類別的反編譯原始碼中看見時，使用其方法時就不需要再轉型為該介面。

此外，介面注入可以是[傳遞性](../class-tweakers/index#transitive-entries)的，讓函式庫能更容易地向依賴它們的模組公開新增的方法。

為了展示介面注入，本頁的程式碼片段會使用一個範例：我們會為 `FlowingFluid` 新增一個輔助方法。

## 建立介面 {#creating-the-interface}

在非 Mixin 套件的套件中，建立你想注入的介面：

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/BucketEmptySoundGetter.java#interface-injection-example-interface

在我們的範例中，由於我們打算透過 mixin 實作該方法，因此預設會擲出例外。

::: warning

被注入介面的所有方法都必須是 `default`，才能透過類別調整器注入；即使你打算使用 Mixin 在目標類別中實作這些方法也是如此。

方法名稱也應以你的模組 ID 作為前綴，並使用 `$` 或 `_` 等分隔符號，避免與其他模組新增的方法發生命名衝突。

:::

## 實作介面 {#implementing-the-interface}

::: tip

如果介面的方法已完全透過介面的 `default` 實作完成，就不需要使用 mixin 來注入介面；只要加入[類別調整器條目](#making-the-class-tweaker-entry)即可。

:::

若要在目標類別中建立介面方法的覆寫，你應使用一個實作該介面、並以你想注入介面的類別為目標的 Mixin。

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/FlowingFluidMixin.java#interface-injection-example-mixin

這些覆寫會在執行時期加入目標類別，但即使你使用類別調整器讓介面實作可見，它們也不會出現在反編譯原始碼中。

## 建立類別調整器條目 {#making-the-class-tweaker-entry}

介面注入使用以下語法：

```:no-line-numbers
inject-interface    <targetClassName>    <injectedInterfaceName>
```

對於類別調整，類別與介面會使用其[內部名稱](../mixins/bytecode#class-names)。

以我們的範例介面而言，條目會是：

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-example-entry{classtweaker:no-line-numbers}

### 泛型介面 {#generic-interfaces}

如果你的介面具有泛型，你可以在條目中指定它們。 為此，請在介面名稱結尾加上 `<>` 尖括號，並在括號中以 Java 位元組碼簽名格式填入泛型。

簽名格式如下：

| 說明             | Java 範例                 | 語法                                                           | 簽名格式範例 |
| -------------- | ----------------------- | ------------------------------------------------------------ | ------ |
| 類別型別           | `java.lang.String`      | [描述符](../mixins/bytecode#type-descriptors)格式                 |        |
| 陣列型別           | `java.lang.String[]`    | [描述符](../mixins/bytecode#type-descriptors)格式                 |        |
| 基本型別           | `boolean`               | [描述符](../mixins/bytecode#type-descriptors)字元                 |        |
| 型別變數           | `T`                     | `T` + 名稱 + `;`                                               |        |
| 泛型類別型別         | `java.util.List<T>`     | L + [內部名稱](../mixins/bytecode#class-names) + `<` + 泛型 + `>;` |        |
| 萬用字元           | `?`、`java.util.List<?>` | `*` 字元                                                       |        |
| extends 萬用字元界限 | `? extends String`      | `+` + 界限                                                     |        |
| super 萬用字元界限   | `? super String`        | `-` + 界限                                                     |        |

因此，若要注入以下介面：

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/GenericInterface.java#interface-injection-generic-interface

並使用泛型 `<? extends String, Boolean[]>`

類別調整器條目會是：

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-generic-interface-entry{classtweaker:no-line-numbers}

## 套用變更 {#applying-changes}

若要看到你的介面實作已套用，你必須重新整理 Gradle 專案並[重新產生原始碼](../getting-started/generating-sources)。
若修改沒有出現，可以嘗試[驗證](../class-tweakers/index#validating-the-file)檔案，並檢查是否出現任何錯誤。

現在可以在已注入該介面的類別實例上使用新增的方法：

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/ExampleModInterfaceInjection.java#interface-injection-using-added-method

如有需要，你也可以在介面注入目標的子類別中覆寫這些方法。
