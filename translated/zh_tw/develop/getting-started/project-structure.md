---
title: 專案結構
description: Fabric 模組專案結構概述
authors:
  - IMB11
---

# 專案結構 {#project-structure}

本頁將介紹 Fabric 模組專案的結構和專案中每個檔案和資料夾的用途。

## `fabric.mod.json` {#fabric-mod-json}

`fabric.mod.json` 是向 Fabric Loader 描述你的模組的主要檔案。 這包含了模組的 ID、版本和前置等訊息。

`fabric.mod.json` 檔案中最重要的部分是:

- `id`: 模組的ID，應該要是獨一無二的。
- `name`: 模組的名稱。
- `environment`: 模組運行環境，可以是 `client` ，可以是 `server`，或是 `*`。
- `entrypoints`: 模組的進入點，例如 `main` 或 `client` 。
- `depends`: 你的模組所需要的前置模組。
- `mixins`: 模組提供的 Mixin。

下方是一個範例 `fabric.mod.json` 檔案 —— 這是此文檔網站的參考專案的 `fabric.mod.json` 檔案。

:::details 參考專案 `fabric.mod.json`
@[code lang=json](@/reference/latest/src/main/resources/fabric.mod.json)
:::

## Entrypoints {#entrypoints}

如前所述，`fabric.mod.json` 檔案包含 —— 個名為 `entrypoints` 的欄位 - 這個欄位用來指定你的模組提供的進入點。

模板模組生成器預設會創建一個 `main` 和一個 `client` 進入點 —— `main` 進入點用於共用的程式碼，而 `client` 進入點則用於客戶端特定的程式碼。 這些進入點會在遊戲啟動時分別被調用。

@[code lang=java transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

上面是一個簡單的 `main` 進入點範例，在遊戲啟動時向控制台記錄一條訊息。

## `src/main/resources` {#src-main-resources}

`src/main/resources` 資料夾用於儲存模組的資源檔案，例如材質、模型和音效。

它也是 `fabric.mod.json` 和模組使用的 Mixin 配置檔案存放的位置。

資源檔案儲存在與資源包結構相似的結構中－例如，方塊的材質會存放在 `assets/modid/textures/block/block.png` 中。

## `src/client/resources` {#src-client-resources}

`src/client/resources` 資料夾用於儲存客戶端特定的資源檔案，例如僅在客戶端使用的材質、模型和音效。

## `src/main/java` {#src-main-java}

`src/main/java` 資料夾用來存放模組的 Java 原始碼 —— 它存在於客戶端和伺服器端環境中。

## `src/client/java` {#src-client-java}

`src/client/java` 資料夾用於存放特定於客戶端的 Java 原始碼 —— 例如渲染程式碼或客戶端邏輯，如方塊顏色提供程式。
