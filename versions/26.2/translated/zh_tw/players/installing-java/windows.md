---
title: 在 Windows 上安裝 Java
description: 關於如何在 Windows 上安裝 Java 的逐步指南。
authors:
  - IMB11
  - skycatminepokie
next: false
---

本指南將帶你在 Windows 上安裝 Java 25。

Minecraft Launcher 隨附自己的 Java 安裝，因此本章節只在你想使用以 Fabric `.jar` 為基礎的安裝程式，或想使用 Minecraft Server `.jar` 時才相關。

## 1. 檢查是否已安裝 Java {#1-check-if-java-is-already-installed}

若要檢查是否已安裝 Java，你必須先開啟命令提示字元。

你可以按下 <kbd>Windows</kbd>+<kbd>R</kbd>，並在出現的方框中輸入 `cmd.exe` 來開啟它。

![在執行列中輸入「cmd.exe」的 Windows 執行對話框](/assets/players/installing-java/windows-run-dialog.png)

開啟命令提示字元後，輸入 `java -version` 並按下 <kbd>Enter</kbd>。

如果指令成功執行，你會看到類似下圖的內容。 如果指令失敗，請繼續下一步。

![已輸入「java -version」的命令提示字元](/assets/players/installing-java/windows-java-version.png)

::: warning

若要使用 Minecraft 26.1，你至少需要安裝 Java 25。

如果此指令顯示任何低於 25 的版本，你需要更新現有的 Java 安裝；請繼續閱讀本頁。

:::

## 2. 下載 Java 25 安裝程式 {#2-download-the-java-installer}

若要安裝 Java 25，你需要從 [Adoptium](https://adoptium.net/temurin/releases?version=25&os=windows&arch=any&mode=filter) 下載安裝程式。

你需要下載 `Windows Installer (.msi)` 版本：

![反白顯示 Windows Installer (.msi) 的 Adoptium 下載頁面](/assets/players/installing-java/windows-download-java.png)

如果你的作業系統是 32 位元，請選擇 `x86`；如果是 64 位元，請選擇 `x64`。

大多數現代電腦都使用 64 位元作業系統。 如果你不確定，請先嘗試使用 64 位元下載。

## 3. 執行安裝程式！ {#3-run-the-installer}

依照安裝程式中的步驟安裝 Java 25。 到達此頁面時，你應該將下列功能設定為「Entire feature will be installed on local hard drive」：

- `Set JAVA_HOME environment variable` - 這會加入你的 PATH。
- `JavaSoft (Oracle) registry keys`

![反白顯示「Set JAVA_HOME variable」和「JavaSoft (Oracle) registry keys」的 Java 25 安裝程式](/assets/players/installing-java/windows-wizard-screenshot.png)

完成後，你可以點擊 `Next` 並繼續安裝。

::: warning

在你重新啟動電腦之前，Windows 不一定會通知其他程式 Java 已安裝。

**繼續之前，請務必重新啟動你的電腦！**

:::

## 4. 驗證是否已安裝 Java 25 {#4-verify-that-java-is-installed}

安裝完成後，你可以再次開啟命令提示字元並輸入 `java -version`，以驗證 Java 25 是否已安裝。

如果指令成功執行，你會看到類似前面所示的內容，其中會顯示 Java 版本：

![已輸入「java -version」的命令提示字元](/assets/players/installing-java/windows-java-version.png)

如果遇到任何問題，歡迎到 [Fabric Discord](https://discord.fabricmc.net/) 的 `#player-support` 頻道尋求協助。
