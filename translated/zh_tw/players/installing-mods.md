---
title: 安裝模組
description: Fabric 模組安裝的逐步指南
authors:
  - IMB11
---

# 安裝模組 {#installing-mods}

這個指南將引導你使用 Minecraft 啟動器安裝 Fabric 模組。

對於第三方啟動器，您應該查閱他們的文件。

## 1. 下載模組 {#1-download-the-mod}

:::warning
你應該只從你信任的來源下載模組。 如需更多關於尋找模組的資訊，請參閱[尋找模組](./finding-mods.md)指南。
:::

大部分的模組都需要 Fabric API，它可以從 [Modrinth](https://modrinth.com/mod/fabric-api) 或 [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api)下載。

下載模組時，請確保：

- 它們能在你想玩的 Minecraft 版本上執行。 例如，一個在 1.20 上執行的模組可能無法在 1.20.2 上執行。
- 它是為 Fabric 製作，而不是其他模組載入器。
- 此外，它們適用於正確的 Minecraft 版本（Java 版）。

## 2. 將模組移動到 `mods` 資料夾 {#2-move-the-mod-to-the-mods-folder}

各個作業系統中，模組資料夾的位置如下所示：

您可以將這些路徑貼上到檔案總管的路徑列中，以便快速導航到該資料夾。

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

找到 `mods` 資料夾後，您可以將模組 `.jar` 檔案移動到其中。

![mods 資料夾中已安裝的模組](/assets/players/installing-mods.png)

## 3. 你完成了！ {#3-you-re-done}

將模組移動到 `mods` 資料夾後，您可以開啟 Minecraft 啟動器，並從左下方的下拉式選單中選擇 Fabric 設定檔，然後按下開始遊戲！

![已選擇 Fabric 設定檔的 Minecraft 啟動器](/assets/players/installing-fabric/launcher-screen.png)

## 疑難排解 {#troubleshooting}

如果你在依照本指南操作時遇到任何問題，你可以在 [Fabric Discord](https://discord.gg/v6v4pMv) 的 `#player-support` 頻道中尋求幫助。

您也可以嘗試透過閱讀疑難排解頁面自行解決問題：

- - [崩潰報告](./troubleshooting/crash-reports)
- - [上傳記錄檔](./troubleshooting/uploading-logs)
