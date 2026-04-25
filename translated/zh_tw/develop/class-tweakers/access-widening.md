---
title: 存取權限擴寬
description: 了解如何在類別調整器檔案中使用存取權限擴寬器。
authors-nogithub:
  - lightningtow
  - siglong
authors:
  - Ayutac
  - cassiancc
  - CelDaemon
  - cootshk
  - Earthcomputer
  - florensie
  - froyo4u
  - haykam821
  - hYdos
  - its-miroma
  - kb-1000
  - kcrca
  - liach
  - lmvdz
  - matjojo
  - MildestToucan
  - modmuss50
  - octylFractal
  - OroArmor
  - T3sT3ro
  - Technici4n
  - TheGlitch76
  - UpcraftLP
  - YTG1234
---

存取權限擴寬是[類別調整](../class-tweakers)的一種類型，用於放寬類別、方法與欄位的存取限制，並將該變更反映在反編譯原始碼中。
這包括將它們設為公開、可擴充及／或可變更。

存取權限擴寬器條目可以是[傳遞性](../class-tweakers/index#transitive-entries)的，讓依賴你的模組的其他模組也能看到這些變更。

若要存取欄位或方法，使用[存取器 Mixin](https://wiki.fabricmc.net/tutorial:mixin_accessors) 通常會更安全也更簡單；但在以下兩種情況中，存取器並不足夠，必須使用存取權限擴寬：

- 如果你需要存取 `private`、`protected` 或套件私有的類別
- 如果你需要覆寫 `final` 方法，或繼承 `final` 類別

然而，與[存取器 Mixin](https://wiki.fabricmc.net/tutorial:mixin_accessors) 不同，[類別調整](../class-tweakers)只適用於原版 Minecraft 類別，不適用於其他模組。

## 存取指令 {#access-directives}

存取權限擴寬器條目會以三種指令關鍵字之一開頭，用來指定要套用的修改類型。

### accessible {#accessible}

`accessible` 可以以類別、方法與欄位為目標：

- 欄位與類別會被設為公開。
- 方法會被設為公開；若原本是 private，則也會被設為 final。

將方法或欄位設為 accessible，也會讓其所屬類別變為 accessible。

### extendable {#extendable}

`extendable` 可以以類別與方法為目標：

- 類別會被設為公開且非 final。
- 方法會被設為 protected 且非 final。

將方法設為 extendable，也會讓其所屬類別變為 extendable。

### mutable {#mutable}

`mutable` 可以讓欄位變為非 final。

若要讓 private final 欄位同時可存取且可變更，你必須在檔案中建立兩個獨立條目。

## 指定目標 {#specifying-targets}

對於類別調整，類別會使用其[內部名稱](../mixins/bytecode#class-names)。 對於欄位與方法，你必須指定其類別名稱、名稱，以及其[位元組碼描述符](../mixins/bytecode#field-and-method-descriptors)。

::: tabs

== 類別

格式：

```:no-line-numbers
<accessible / extendable>    class    <className>
```

範例：

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:classes:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

== 方法

格式：

```:no-line-numbers
<accessible / extendable>    method    <className>    <methodName>    <methodDescriptor>
```

範例：

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:methods:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

== 欄位

格式：

```:no-line-numbers
<accessible / mutable>    field    <className>    <fieldName>    <fieldDescriptor>
```

範例：

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:fields:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

:::

## 產生條目 {#generating-entries}

手動撰寫存取權限擴寬器條目既耗時，也容易發生人為錯誤。 接下來我們會介紹一些工具，它們可以讓你產生並複製條目，藉此簡化部分流程。

### mcsrc.dev {#mcsrc-dev}

對於所有提供[未混淆 JAR](../migrating-mappings/index#whats-going-on-with-mappings) 的版本，也就是 1.21.11 及以上版本，[mcsrc](https://mcsrc.dev) 可讓你在瀏覽器中反編譯並瀏覽 Minecraft 原始碼，並將 Mixin、存取權限擴寬器或存取轉換器目標複製到剪貼簿。

若要複製存取權限擴寬器條目，請先前往你想修改的類別，然後在目標上按右鍵以開啟快顯選單。

![在 mcsrc 中於目標上按右鍵](/assets/develop/class-tweakers/access-widening/mcsrc-right-click-on-aw-target.png)

接著，點擊 `Copy Class Tweaker / Access Widener`，頁面頂端應會出現確認訊息。

![mcsrc 上的 AW 複製確認訊息](/assets/develop/class-tweakers/access-widening/mcsrc-aw-copy-confirmation.png)

之後，你就可以將條目貼到你的類別調整器檔案中。

### Minecraft Development 外掛（IntelliJ IDEA） {#mcdev-plugin}

[Minecraft Development 外掛](../getting-started/intellij-idea/setting-up#installing-idea-plugins)，也稱為 MCDev，是一個 IntelliJ IDEA 外掛，用於協助 Minecraft 模組開發的各個面向。
例如，它可讓你從反編譯原始碼中的目標，將存取權限擴寬器條目複製到剪貼簿。

若要複製存取權限擴寬器條目，請先前往你想修改的類別，然後在目標上按右鍵以開啟快顯選單。

![使用 MCDev 在目標上按右鍵](/assets/develop/class-tweakers/access-widening/mcdev-right-click-on-aw-target.png)

接著，點擊 `Copy / Paste Special`，再點擊 `AW Entry`。

![MCDev 中的 Copy/Paste special 選單](/assets/develop/class-tweakers/access-widening/mcdev-copy-paste-special-menu.png)

此時，你按右鍵的元素上應會彈出確認訊息。

![MCDev 中的 AW 複製確認訊息](/assets/develop/class-tweakers/access-widening/mcdev-aw-copy-confirmation.png)

之後，你就可以將條目貼到你的類別調整器檔案中。

## 套用變更 {#applying-changes}

若要看到已套用的變更，你必須重新整理 Gradle 專案並[重新產生原始碼](../getting-started/generating-sources)。 你所指定的元素應會相應地修改其存取限制。 若修改沒有出現，可以嘗試[驗證檔案](../class-tweakers/index#validating-the-file)，並檢查是否出現任何錯誤。
