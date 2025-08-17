---
title: 建立專案
description: 如何一步一步使用 Fabric 範本模組產生器建立新模組專案的指南。
authors:
  - Cactooz
  - IMB11
  - radstevee
---

Fabric 提供了一種使用 Fabric 範本模組產生器輕鬆建立新模組專案的方法——如果你願意，你可以使用範例模組倉儲手動建立新專案，你應該參閱[手動建立專案](#manual-project-creation)章節。

## 生成專案 {#generating-a-project}

你可以使用 [Fabric 範本模組產生器](https://fabricmc.net/develop/template/) 生成你的新模組專案 —— 有一些必填的項目，例如模組名稱、套件名稱，以及你想基於其開發的 Minecraft 版本。

套件名稱應是小寫字母，由點區隔；套件名稱須具有獨特性，避免和其他開發者的套件衝突。 其格式通常反向的是網域名，例如 `com.example.mod-id`。

![生成器的預覽圖](/assets/develop/getting-started/template-generator.png)

如果你想要使用 Kotlin 進行開發，想使用Mojang官方映射而非Yarn映射，或是想增加資料生成器，可以在 `Advanced Options` 中勾選對應的選項。

![Advanced options section](/assets/develop/getting-started/template-generator-advanced.png)

必填項目輸入完後，點擊 `Generate` 按鈕，產生器會以 zip 檔的形式產生新專案供你使用。

你需要把這個 zip 檔解壓縮到你想要的位置，然後從 IntelliJ IDEA 開啟解壓縮後的資料夾：

![開啟專案提示](/assets/develop/getting-started/open-project.png)

## 導入專案 {#importing-the-project}

在 IntelliJ IDEA 開啟專案後， IDE 會自動載入專案的 Gradle 配置並執行必要的初始化任務。

如果你收到關於 Gradle 建構腳本的通知，你應該點擊 `Import Gradle Project` 按鈕：

![Gradle 提示](/assets/develop/getting-started/gradle-prompt.png)

專案導入完成後，你就可以在專案資源管理器中看到專案的檔案，並能開始開發你的模組了。

## 手動建立專案 {#manual-project-creation}

:::warning
你會需要安裝 [Git](https://git-scm.com/) 來複製範例模組倉儲。
:::

如果你無法使用 Fabric 範本模組產生器，你可以按照以下步驟手動建立新專案。

首先，使用 Git 複製範例模組倉儲：

```sh
git clone https://github.com/FabricMC/fabric-example-mod/ my-mod-project
```

這會把倉儲複製到名為 `my-mod-porject` 的新資料夾中。

你應該從複製的存儲庫中刪除 `.git` 資料夾，然後在 IntelliJ IDEA 中打開專案。 如果 `.git` 資料夾沒有顯示，你需要在檔案管理器中開啟 `顯示隱藏的項目` 。

在 IntelliJ IDEA 中開啟專案後，它應該會自動載入專案的 Gradle 配置並執行必要的初始化任務。

再次強調，如果你收到關於 Gradle 建構腳本的通知，你應該點擊 `Import Gradle Project` 按鈕。

### 修改模板 {#modifying-the-template}

專案導入後，你應該修改專案的細節，以符合你的模組：

- 修改專案中的 `gradle.properties` 檔案，將 `maven_group` 和 `archive_base_name` 屬性改成為你的模組的資訊。
- 修改 `fabric.mod.json` 文件，將 `id`、`name` 和 `description` 屬性改為與你的模組的資訊。
- 記得更新Minecraft的版本、映射、Loader和Loom的版本，你可以透過 <https://fabricmc.net/develop/> 查詢相關資訊，以確保它們符合你期望的目標版本。

你也可以修改套件名稱和模組的主類別以符合你的模組。
