---
title: 建置開發環境
description: 關於建置 Fabric 開發環境的逐步指南。
authors:
  - IMB11
  - andrew6rant
  - SolidBlock-cn
  - modmuss50
  - daomephsta
  - liach
  - telepathicgrunt
  - 2xsaiko
  - natanfudge
  - mkpoli
  - falseresync
  - asiekierka
authors-nogithub:
  - siglong
---

# 建置開發環境 {#setting-up-a-development-environment}

要開始用 Fabric 開發模組，需要使用 IntelliJ IDEA 建置開發環境。

## 安裝 JDK 21 {#installing-jdk-21}

要開發 Minecraft 1.21 的模組，你需要 JDK 21。

如果你需要安裝 Java 的幫助，可以參考 [player guides section](../../players/index) 中的各種 Java 安裝指南。

## 安裝 IntelliJ IDEA {#installing-intellij-idea}

:::info
你當然可以使用其他 IDE，如 Eclipse 或 Visual Studio Code，但本文檔站上的大多數頁面都假設你使用的是 IntelliJ IDEA。如果你使用其他 IDE，請參考該 IDE 的文檔。
:::

如果沒有安裝 IntelliJ IDEA，你可以從 [官網](https://www.jetbrains.com/idea/download/) 下載 - 按照你的作業系統的安裝步驟操作。

IntelliJ IDEA 的 Community 版本是免費且開源的，是使用 Fabric 開發模組的推薦版本。

你可能需要向下滑動才能找到 Community 版的下載連結 - 如下圖所示:

![IDEA Community Edition Download Prompt](/assets/develop/getting-started/idea-community.png)

## 安裝 IDEA 插件 {#installing-idea-plugins}

雖然這些插件並不是必要的，但可以讓使用 Fabric 開發模組更加容易 - 你應該考慮安裝它們。

### Minecraft Development {#minecraft-development}

Minecraft Development 插件提供對 Fabric 模組開發提供支援，是要安裝的最重要的插件。

你可以通過打開IntelliJ IDEA，然後前往 `File > Settings > Plugins > Marketplace Tab` - 在搜索欄中搜尋 `Minecraft Development`，然後點擊 `Install` 按鈕來安裝它。

或者你可以從 [plugin page](https://plugins.jetbrains.com/plugin/8327-minecraft-development) 下載它，然後點擊 `File > Settings > Plugins > Install Plugin From Disk` 來安裝。
