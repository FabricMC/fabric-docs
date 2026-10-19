---
title: 在 macOS 上安裝 Fabric
description: 關於如何在 macOS 上安裝 Fabric 的逐步指南。
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info 先決條件

你可能需要先[安裝 Java](../installing-java/macos)，才能執行 `.jar`。

:::

<!-- #region common -->

## 1. 下載 Fabric 安裝程式 {#1-download-the-fabric-installer}

從 [Fabric 網站](https://fabricmc.net/use/)下載 Fabric Installer 的 `.jar` 版本，方法是點擊 `Download installer (Universal/.JAR)`。

## 2. 執行 Fabric 安裝程式 {#2-run-the-fabric-installer}

在執行安裝程式之前，請先關閉 Minecraft 和 Minecraft Launcher。

::: tip

你可能會收到 Apple 無法驗證該 `.jar` 的警告。 若要略過此限制，請開啟「系統設定」>「隱私權與安全性」，然後點擊 `仍然打開`。 確認後，如有提示，請輸入你的管理員密碼。

![macOS 系統設定](/assets/players/installing-fabric/macos-settings.png)

:::

開啟安裝程式後，你應該會看到如下畫面：

![反白顯示「Install」的 Fabric 安裝程式](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

選擇你想要的 Minecraft 版本，然後點擊 `Install`。 請確認已勾選 `Create Profile`。

### 透過 Homebrew 安裝 {#installing-via-homebrew}

如果你已安裝 [Homebrew](https://brew.sh)，也可以改用 `brew` 安裝 Fabric Installer：

```sh
brew install fabric-installer
```

## 3. 完成設定 {#3-finish-setup}

安裝完成後，開啟 Minecraft Launcher。 接著從版本下拉選單選取 Fabric 設定檔，並點擊開始游戲。

![已選取 Fabric 設定檔的 Minecraft Launcher](/assets/players/installing-fabric/launcher-screen.png)

現在你可以將模組加入遊戲。 請參閱[尋找可信任的模組](../finding-mods)指南以取得相關資訊。

如果遇到問題，歡迎到 [Fabric Discord](https://discord.fabricmc.net/) 的 `#player-support` 頻道尋求協助。
