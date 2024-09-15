---
title: 在 Windows 上安裝 Java
description: 在 Windows 上安裝 Java 的逐步指南。
authors:
  - IMB11
---

# 在 Windows 上安裝 Java {#installing-java-on-windows}

這個指南將引導你在 Windows 上安裝 Java 21。

Minecraft 啟動器附帶了自己的 Java 安裝，因此這部分只在你想使用 Fabric 的 `.jar` 安裝程式，或者你想使用 Minecraft 伺服器的 `.jar` 時相關。

## 1. 檢查 Java 是否已安裝 {#1-check-if-java-is-already-installed}

要檢查 Java 是否已安裝，你首先必須開啟命令提示字元。

你可以透過按下 <kbd>Win</kbd> + <kbd>R</kbd> 並在出現的對話方塊中輸入 `cmd.exe` 來執行這項操作。

![Windows執行對話方塊中的「cmd.exe」](/assets/players/installing-java/windows-run-dialog.png)

開啟命令提示字元後，輸入 `java -version` 並按下 <kbd>Enter</kbd> 鍵。

如果命令成功執行，你會看到類似這樣的內容。 如果命令失敗，請繼續進行下一步。

![命令提示字元中輸入了「java -version」](/assets/players/installing-java/windows-java-version.png)

:::warning
要使用 Minecraft 1.21，你至少需要安裝 Java 21。 如果這個指令顯示任何低於 21 的版本，則需要更新現有的 Java 安裝。
:::

## 2. 下載Java 21安裝程式 {#2-download-the-java-installer}

要安裝Java 21，你需要從 [Adoptium](https://adoptium.net/en-GB/temurin/releases/?os=windows\&package=jdk\&version=21) 下載安裝程式。

你需要下載 `Windows Installer (.msi)` 版本：

![Adoptium 下載頁面，突顯了 Windows 安裝程式 (.msi)](/assets/players/installing-java/windows-download-java.png)

如果你有 32 位元作業系統，應該選擇 `x86`；如果你有 64 位元作業系統，則應該選擇 `x64`。

現代大多數電腦都執行 64 位元作業系統。 如果你不確定，請嘗試使用 64 位元的下載。 如果你不確定，請嘗試使用 64 位元的下載。 要檢查 Java 是否已安裝，你首先必須開啟命令提示字元。

## 3. 執行安裝程式！ {#3-run-the-installer}

依照安裝程式中的步驟安裝Java 21。 當你到達這個頁面時，你應該將以下功能設為「整個功能將安裝在本機硬碟上」：

- `設定 JAVA_HOME 變數` - 這將加入到你的 PATH 中。
- `JavaSoft (Oracle) 登錄機碼`

![Java 21 安裝程式，其中「設定 JAVA\_HOME 變數」和「JavaSoft (Oracle) 登錄機碼」已醒目提示](/assets/players/installing-java/windows-wizard-screenshot.png)

完成後，你可以按 `下一步` 繼續安裝。

## 4. 驗證 Java 21 是否已安裝 {#4-verify-that-java-is-installed}

安裝完成後，你可以再次開啟命令提示字元，並輸入 `java -version` 來驗證 Java 21 是否已安裝。

如果這個命令成功執行，你將看到類似於以前顯示的內容，其中顯示了 Java 版本：

![命令提示字元中輸入了「java -version」](/assets/players/installing-java/windows-java-version.png)

---

如果遇到任何問題，你可以在 [Fabric Discord](https://discord.gg/v6v4pMv) 的 `#player-support` 頻道中尋求幫助。
