---
title: 類別調整器
description: 了解什麼是類別調整器，以及如何設定它們。
authors:
  - cassiancc
  - Earthcomputer
  - its-miroma
  - MildestToucan
---

類別調整器（Class Tweakers）先前稱為存取權限擴寬器（Access Wideners），在取得更多功能後改稱為類別調整器。 它提供了可補充 Mixin 位元組碼操作的轉換工具，也能讓部分執行時期修改在開發環境中可見。

::: warning

類別調整器並不針對特定 Minecraft 版本，但僅從 Fabric Loader 0.18.0 與 Loom 1.12 起可用，且只能以原版 Minecraft 類別為目標。

:::

## 設定 {#setup}

### 檔案格式 {#file-format}

類別調整器檔案通常會依照你的 modid 命名，例如 `example-mod.classtweaker`，以協助 IDE 外掛辨識。 它們應存放在 `resources` 中。

檔案第一行必須包含以下標頭：

```classtweaker
classTweaker  v1  official
```

某些功能可能需要高於 `v1` 的版本；若有此需求，會在各自的頁面中說明。

類別調整器檔案可以包含空白行，以及以 `#` 開頭的註解。 註解也可以從一行的結尾開始。

語法會依使用的功能而有所不同，但每項修改都會以獨立一行的「條目」（entry）宣告，並以指定要套用哪種修改的「指令」（directive）開頭。
條目中的各個元素可以使用任何空白字元分隔，包括定位字元。

#### 傳遞性條目 {#transitive-entries}

若要讓依賴你的模組的其他模組，也能在反編譯原始碼中看到你所做的變更，請在指令前加上 `transitive-`：

```classtweaker:no-line-numbers
# Transitive Access Widening directives
transitive-accessible
transitive-extendable
transitive-mutable

# Transitive Interface Injection directive
transitive-inject-interface

# Transitive Enum Extension directive
transitive-extend-enum
```

### 指定檔案位置 {#specifying-the-file-location}

類別調整器檔案的位置必須在你的 `build.gradle` 與 `fabric.mod.json` 檔案中指定。 請記得，你也必須依賴 Fabric Loader 0.18.0 或以上版本，才能使用類別調整器。

為了維持向後相容性，相關設定名稱仍沿用存取權限擴寬器的命名。

#### build.gradle {#build-gradle}

@[code lang=gradle:no-line-numbers transcludeWith=:::classtweaker-setup:gradle:::](@/reference/latest/build.gradle)

#### fabric.mod.json {#fabric-mod-json}

```json:no-line-numbers
...

"accessWidener": "example-mod.classtweaker",

...
```

在 `build.gradle` 檔案中指定檔案位置後，請務必在 IDE 中重新載入 Gradle 專案。

## 驗證檔案 {#validating-the-file}

預設情況下，類別調整器會忽略參照到找不到的修改目標的條目。 若要檢查檔案中指定的所有類別、欄位與方法是否有效，請執行 `validateAccessWidener` Gradle 工作。

錯誤訊息會指出任何無效條目，但不一定會明確指出條目中的哪個部分無效。
