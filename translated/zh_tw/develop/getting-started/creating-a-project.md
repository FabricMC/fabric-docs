---
title: 建立一個專案
description: 關於如何使用 Fabric 模組模板生成器來製作新的模組專案的逐步指南。
authors:
  - IMB11
---

# 建立一個專案 {#creating-a-project}

Fabric 提供了使用 Fabric 模組模板生成器創建新模組專案的簡單方法。 —— 如果你願意，也可以使用範例模組儲存庫手動創建一個新專案，請參閱 [手動創建專案](#manual-project-creation) 章節。

## 生成專案 {#generating-a-project}

你可以使用 [Fabric Template Mod Generator](https://fabricmc.net/develop/template/) 來生成你的新模組專案 —— 你需要填寫必要的部分，比如套件名稱和模組名稱，還有你想開發的模組的 Minecraft 版本。

![Preview of the generator](/assets/develop/getting-started/template-generator.png)

如果你想要使用 Kotlin 進行開發，或是想增加 data generators ，可以在 `Advanced Options` 部分勾選對應的選項。

![Advanced options section](/assets/develop/getting-started/template-generator-advanced.png)

必要的部分輸入完成後，點擊 `Generate` 按鈕，生成器會以 zip 檔的形式產生新專案給你使用。

你需要把這個 zip 檔解壓縮到你想要的位置，然後從 IntelliJ IDEA 打開解壓縮後的資料夾:

![Open Project Prompt](/assets/develop/getting-started/open-project.png)

## 導入專案 {#importing-the-project}

在 IntelliJ IDEA 打開專案後， IDE 會自動載入專案的 Gradle 配置並執行必要的初始化任務。

如果你收到了一個關於 Gradle 構建腳本的通知，你應該點擊 `Import Gradle Project` 按鈕:

![Gradle Prompt](/assets/develop/getting-started/gradle-prompt.png)

專案導入完成後，你就可以在專案資源管理器中看到專案的文件，就能開始開發你的模組了。

## 手動建立專案 {#manual-project-creation}

:::warning
你會需要安裝 [Git](https://git-scm.com/) 來複製範例模組儲存庫。
:::

如果你無法使用 Fabric 模組模板生成器，可以按照以下步驟手動建立新專案。

首先，使用 Git 複製範例模組儲存庫：

```sh
git clone https://github.com/FabricMC/fabric-example-mod/ my-mod-project
```

這會把儲存庫複製到一個叫 `my-mod-porject` 的新資料夾。

你應該從複製的存儲庫中刪除 `.git` 資料夾，然後在 IntelliJ IDEA 中打開專案。 如果 `.git` 資料夾沒有顯示，你應該在檔案總管中開啟 `顯示隱藏的項目` 。

在 IntelliJ IDEA 中打開專案後，它應該會自動載入專案的 Gradle 配置並執行必要的初始化任務。

再次強調，如果你收到了一個關於 Gradle 構建腳本的通知，你應該點擊 `Import Gradle Project` 按鈕。

### 修改模板 {#modifying-the-template}

專案導入後，你應該修改專案的細節，以符合你的模組的資訊：

- 修改專案中的 `gradle.properties` 檔案，把 `maven_group` 和 `archive_base_name` 修改成與你的模組的資訊相符。
- 修改 `fabric.mod.json` 文件，將 `id`、`name` 和 `description` 屬性改為成與你的模組的資訊相符。
- 請確保更新Minecraft的版本、映射、Loader和Loom的版本，你可以透過 \<https://fabricmc. net/develop/> 查詢這些資訊，以確保它們符合你希望的目標版本。

你也可以修改套件名稱和模組的主類別來符合你的模組的細節。
