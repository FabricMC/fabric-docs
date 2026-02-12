---
title: 啟動遊戲
description: 了解如何利用各種啟動設定檔在即時遊戲環境中啟動和除錯您的模組。
authors:
  - IMB11
  - Tenneb22
---

Fabric Loom 提供多種啟動配置檔案，幫助你在實時遊戲環境中啟動模組並進行除錯。 本指南將介紹各種啟動配置檔案以及如何用它們來除錯及在遊戲中測試你的模組。

## 啟動配置檔案 {#launch-profiles}

如果你使用的是 IntelliJ IDEA，你可以在視窗的右上角找到啟動配置檔案。 點擊下拉選單可以查看可用的啟動配置檔案。

應該有一個客戶端和伺服器端配置檔案，可以選擇正常執行或在除錯模式下執行它:

![啟動配置](/assets/develop/getting-started/launch-profiles.png)

## Gradle 任務 {#gradle-tasks}

如果你使用的是命令行，則可以使用以下 Gradle 指令啟動遊戲：

- `./gradlew runClient` - 以用戶端啟動遊戲。
- `./gradlew runServer` - 以伺服器端啟動遊戲。

這種方法的唯一問題是無法輕鬆為程式碼除錯。 如果想要進行程式碼除錯，你需要使用 IntelliJ IDEA 中的啟動配置檔案，或者通過你的 IDE 的 Gradle 集成來進行。

## 熱插拔類別 {#hotswapping-classes}

在除錯模式下運行遊戲時，可以熱插拔你的類別而無需重啟遊戲。 這對於快速測試程式碼的更改很有用。

但依舊會受到很大的限制：

- 無法新增或刪除方法
- 無法修改方法參數
- 無法添加或刪除欄位

不過，使用 [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime) ，你就可以避開大多數的限制，甚至可以增加或移除類別的方法。 這應該能夠讓大多數變更在不重新啟動遊戲的情況下生效。

不要忘記在你的 Minecraft 運行配置中的 VM Arguments 選項中新增以下內容：

```:no-line-numbers
-XX:+AllowEnhancedClassRedefinition
```

## 熱插拔 Mixins {#hotswapping-mixins}

如果你正在使用 Mixin，則可以熱插拔 Mixin 類別而無須重啟遊戲。 這對於快速測試 Mixin 的更改很有用。

但是需要安裝 Mixin Java 代理才能正常運作。

### 1. 找到 Mixin 程式庫 Jar {#1-locate-the-mixin-library-jar}

在 IntelliJ IDEA中，你可以在「Project」部分的「External Libraries」部分找到 mixin 程式庫：

![Mixin Library](/assets/develop/getting-started/mixin-library.png)

你需要複製 Jar 的「絕對路徑」以供下一步使用。

### 2. 加入 `-javaagent` VM 參數 {#2-add-the--javaagent-vm-argument}

在你的「Minecraft Client」和/或「Minecraft Server」啟動配置中，將以下內容加進 VM 參數選項：

```:no-line-numbers
-javaagent: 「mixin 程式庫 Jar 的路徑」
```

![VM 參數截圖](/assets/develop/getting-started/vm-arguments.png)

現在，你應該能夠在除錯期間修改 mixin 方法的內容，並且無須重啟遊戲即可使修改生效。
