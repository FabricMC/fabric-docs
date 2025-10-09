---
title: 崩潰報告
description: 了解如何處理崩潰報告，以及如何閱讀它們。
authors:
  - IMB11
---

:::tip
如果你在尋找崩潰原因時遇到任何困難，你可以在 [Fabric Discord](https://discord.gg/v6v4pMv) 的 `#player-support` 或 `#server-admin-support` 頻道中尋求幫助。
:::

崩潰報告是排解遊戲或伺服器問題的非常重要的一部分。 它們包含了關於崩潰的大量資訊，可以幫助你找到崩潰的原因。

## 尋找崩潰報告 {#finding-crash-reports}

崩潰報告儲存在遊戲目錄中的 crash-reports 資料夾中。 如果您正在使用伺服器，則它們儲存在伺服器目錄中的 `crash-reports` 資料夾中。

對於第三方啟動器，你應參考它們的文件以找到崩潰報告的位置。

崩潰報告可以在以下位置找到：

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\crash-reports
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/crash-reports
```

```:no-line-numbers [Linux]
~/.minecraft/crash-reports
```

:::

## 閱讀崩潰報告 {#reading-crash-reports}

崩潰報告非常長，且讀起來可能會很困難。 然而，它們包含了許多關於崩潰的資訊，可以幫助你找出崩潰的原因。

在本指南中，我們將使用[這個崩潰報告作為範例](/assets/players/crash-report-example.txt)。

:::details 崩潰報告

<<< @/public/assets/players/crash-report-example.txt{log}

:::

### 崩潰報告部分 {#crash-report-sections}

崩潰報告由幾個部分組成，每個部分都使用標題分隔：

- `---- Minecraft Crash Report ----`，報告的摘要。 這個部分包含導致崩潰的主要錯誤、發生時間以及相關的堆疊追蹤。 這是崩潰報告中最重要的部分，因為堆疊追蹤通常包含導致崩潰的模組的參考。
- `-- Last Reload --`，這個部分除非崩潰發生在資源重新載入期間（<kbd>F3</kbd> <kbd>T</kbd>），否則沒有什麼用。 這個部分將包含上次重新載入的時間以及重新載入過程中發生的任何錯誤的相關堆疊追踪。 這些錯誤通常是由資源包引起的，除非它們對遊戲造成問題，否則可以忽略。
- `-- System Details --`，這個部分包含有關你系統的資訊，例如作業系統、Java 版本和配置給遊戲的記憶體。 這個部分對於確定你是否使用正確的 Java 版本以及是否為遊戲配置足夠的記憶體很有用。
  - 在這一部分，Fabric 會加入一個客製化的行，顯示 `Fabric Mods:`，後面會列出您已安裝的所有模組。 這個部分對於確定模組之間是否可能發生衝突很有用。

### 解析崩潰報告 {#breaking-down-the-crash-report}

現在我們知道崩潰報告的每個部分是什麼，我們可以開始分解崩潰報告，找出崩潰的原因。

使用上面連結的範例，我們可以分析崩潰報告，找出崩潰的原因，包括導致崩潰的模組。

`---- Minecraft Crash Report ----`，報告的摘要。 這個部分包含導致崩潰的主要錯誤、發生時間以及相關的堆疊追蹤。 這是崩潰報告中最重要的部分，因為堆疊追蹤通常包含導致崩潰的模組的參考。 這個部分包含導致崩潰的主要錯誤、發生時間以及相關的堆疊追蹤。 這是崩潰報告中最重要的部分，因為堆疊追蹤通常包含導致崩潰的模組的參考。 這個部分包含導致崩潰的主要錯誤、發生時間以及相關的堆疊追蹤。 這是崩潰報告中最重要的部分，因為堆疊追蹤通常包含導致崩潰的模組的參考。 這個部分包含導致崩潰的主要錯誤、發生時間以及相關的堆疊追蹤。 這是崩潰報告中最重要的部分，因為堆疊追蹤通常包含導致崩潰的模組的參考。

:::details 展示錯誤詳情

<<< @/public/assets/players/crash-report-example.txt{7 log}

:::

由於堆疊追蹤中提到了大量的模組，要指出責任者可能有些困難，但首先要做的是尋找導致崩潰的模組。

在這種情況下，導致崩潰的模組是 `snownee`，因為它是堆疊追蹤中首先提到的模組。

不過，這裡提到了很多模組，可能代表模組之間存在兼容性的問題，導致崩潰的模組可能不是出錯的模組。 在這種情況下，最好向模組作者回報崩潰的情形，以便他們調查崩潰原因。

## Mixin 崩潰 {#mixin-crashes}

:::info
Mixin是一種無需修改遊戲原始碼即可修改遊戲的方法。 這對開發者而言是非常強大的工具，許多模組都有使用。
:::

當 mixin 崩潰時，堆疊追蹤通常會提到 mixin 和被修改的類。

方法 mixin 將在堆疊追蹤中包含 `modid$handlerName`，其中 `modid` 是模組的 ID，`handlerName` 是 mixin 處理程序的名稱。

```:no-line-numbers
... net.minecraft.class_2248.method_3821$$$mod-id$handlerName() ... // [!code focus]
```

你可以透過這項資訊找到導致崩潰的模組，並向模組作者回報崩潰情形。

## 如何處理崩潰報告 {#what-to-do-with-crash-reports}

處理崩潰報告的最佳方法是將它上傳至文字分享網站，並將連結分享給模組作者、錯誤追蹤器或透過某種形式的交流（Discord 等）。

這使模組作者可以調查崩潰、嘗試復現，並解決崩潰的原因。

常用於張貼崩潰報告的網站有：

- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
- [Pastebin](https://pastebin.com/)
