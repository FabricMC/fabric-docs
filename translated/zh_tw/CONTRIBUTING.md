# Fabric 文件貢獻指南

這個網站使用 [VitePress](https://vitepress.vuejs.org/) 來從各種 Markdown 檔案轉換成靜態 HTML。 這個網站使用 [VitePress](https://vitepress.vuejs.org/) 來從各種 Markdown 檔案轉換成靜態 HTML。 你應該熟悉 VitePress 支援的 Markdown 擴充功能，詳細內容請參閱[這裡](https://vitepress.vuejs.org/guide/markdown.html#features)。

## 目錄

- [Fabric 文件貢獻指南](#fabric-documentation-contribution-guidelines)
  - [如何貢獻](#how-to-contribute)
  - [貢獻框架](#contributing-framework)
  - [貢獻內容](#contributing-content)
    - [格式指南](#style-guidelines)
    - [擴充指南](#guidance-for-expansion)
    - [內容驗證](#guidance-for-expansion)
    - [清理](#cleanup)

## 如何貢獻

我們建議每次送出合併請求時，都在你的儲存庫中新增一個分支。 這讓同時管理多個合併請求變得更加容易。

如果你想在本機預覽你的變更，你需要安裝 [Node.js 18+](https://nodejs.org/zh-tw/)。

在執行以下指令前，請先執行 `npm install` 來安裝所有相依項。

**正在執行程式開發伺服器：**

這將讓你能在本機預覽你的變更，網址是 `localhost:3000`，並且在你進行變更時會自動重新載入頁面。

```bash
npm run dev
```

**建構網站：**

這將把所有的 Markdown 檔案編譯成靜態 HTML 檔案，並將它們放置在 `.vitepress/dist` 資料夾中。

```bash
npm run build
```

**預覽已建置的網站：**

這將在連接埠 3000 上啟動本機伺服器，提供在 `.vitepress/dist` 中找到的內容。

```bash
npm run preview
```

## 貢獻框架

框架指的是網站的內部結構，任何修改網站框架的合併請求應該標記為 `framework` 標籤。

在進行框架合併請求前，你應該先與 [Fabric Discord](https://discord.gg/v6v4pMv) 上的文件團隊諮詢，或者透過提出問題進行討論。

**備註：修改側邊欄檔案和導覽列配置不算作框架合併請求。**

## 貢獻內容

貢獻內容是貢獻 Fabric 文件的主要方式。

所有內容應遵循我們的風格指南。

### 風格指南

所有 Fabric 文件網站上的頁面都應遵循風格指南。 所有 Fabric 文件網站上的頁面都應遵循風格指南。 如果你對任何事情感到不確定，可以在 [Fabric Discord](https://discord.gg/v6v4pMv) 或 GitHub 討論中提問。

風格指南如下：

1. 所有頁面必須在 frontmatter 中包含標題和描述。

   ```md
   ---
   title: 這是頁面的標題
   description: 這是頁面的描述
   authors:
     - GitHub 使用者名稱
   ---
   ```

2. 如果你建立或修改包含程式碼的頁面，請將程式碼放置在參考模組的適當位置（位於儲存庫的 `/reference` 資料夾中）。 如果你建立或修改包含程式碼的頁面，請將程式碼放置在參考模組的適當位置（位於儲存庫的 `/reference` 資料夾中）。 接著，使用 VitePress 提供的 [程式碼片段功能](https://vitepress.dev/guide/markdown#import-code-snippets) 嵌入程式碼，或者如果你需要更大的控制範圍，可以使用 `markdown-it-vuepress-code-snippet-enhanced` 的 [轉入功能](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced)。

   **範例：**

   ```md
   <<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21 java}
   ```

   這將在參考模組中嵌入 `FabricDocsReference.java` 檔案的第 15 至 21 行程式碼。

   產生的程式碼片段將如下所示：

   ```java
     @Override
   public void onInitialize() {
     // 當 Minecraft 達到模組載入就緒的狀態時，這個程式碼將立即執行。
     // 但是，某些事物（如資源）可能仍未初始化。
     // 請小心謹慎地進行。

     LOGGER.info("你好，Fabric 世界！");
   }
   ```

   **轉入範例：**

   ```md
   @[code transcludeWith=#test_transclude](@/reference/.../blah.java)
   ```

   這將嵌入標記為 `#test_transclude` 的 `blah.java` 檔案的部分。

   例如：

   ```java
   public final String test = "再見，世界！";

   // #test_transclude
   public void test() {
     System.out.println("你好，世界！");
   }
   // #test_transclude
   ```

   只有位於 `#test_transclude` 標籤之間的程式碼將被嵌入。

   ```java
   public void test() {
     System.out.println("你好，世界！");
   }
   ```

3. 我們遵循美式英語的文法規則。 我們遵循美式英語的文法規則。 你可以使用 [LanguageTool](https://languagetool.org/) 在輸入時檢查你的語法，但不要太擔心。 我們的文件團隊將在清理階段審查並更正文法錯誤。 然而，一開始就努力做到正確可以節省我們的時間。 我們的文件團隊將在清理階段審查並更正文法錯誤。 然而，一開始就努力做到正確可以節省我們的時間。

4. 如果你正在建立新章節，你應該在 `.vitepress/sidebars` 資料夾中建立一個新的側邊欄，並將其新增到 `config.mts` 檔案中。 如果你需要幫助，請在 [Fabric Discord](https://discord.gg/v6v4pMv) 的 `#wiki` 頻道中提問。 所有新增內容的合併請求都經過內容驗證，這是最重要的階段，因為它確保內容準確無誤並符合 Fabric 文件的風格指南。

5. 當建立新頁面時，你應將其新增到 `.vitepress/sidebars` 資料夾中相關的側邊欄中。 當建立新頁面時，你應將其新增到 `.vitepress/sidebars` 資料夾中相關的側邊欄中。 同樣，如果需要幫助，請在 Fabric Discord 的 `#wiki` 頻道中提問。

6. 任何影像應該放置在 `/assets` 資料夾中的適當位置。

7. ⚠️ **連結其他頁面時，請使用相對連結。** ⚠️

   這是因為系統中存在的版本控制系統，該系統將處理連結以在前面新增版本號。 如果你使用絕對連結，則不會將版本號新增到連結中。 如果你使用絕對連結，則不會將版本號新增到連結中。

   例如，對於位於 `/players` 資料夾中的頁面，要連結到 `/players/installing-fabric.md` 中的 `installing-fabric` 頁面，你應該執行以下動作：

   ```md
   [這是指向另一個頁面的連結](./installing-fabric.md)
   ```

   你**不應該**執行以下動作：

   ```md
   [這是指向另一個頁面的連結](/player/installing-fabric)
   ```

所有內容貢獻經歷三個階段：

1. 擴充指導（如果可能）
2. 內容驗證
3. 清理（語法等）

### 擴充指南

如果文件團隊認為你可以擴充你的合併請求，團隊成員將在你的合併請求中新增 `expansion` 標籤，並附上一則留言，解釋他們認為你可以擴展的內容。 如果你同意這個建議，你可以擴充你的合併請求。

\*\*不要感到被迫擴充你的合併請求。\*\*如果你不想擴充你的合併請求，你可以簡單地要求移除 `expansion` 標籤。

如果你不想擴展你的合併請求，但你願意讓其他人在以後擴展它，最好是在[議題頁面](https://github.com/FabricMC/fabric-docs/issues)上建立一個問題，並解釋你認為可以擴展的內容。

### 內容驗證

所有新增內容的合併請求都經過內容驗證，這是最重要的階段，因為它確保內容準確無誤並符合 Fabric 文件的風格指南。

### 清理

在這個階段，文件團隊將在合併之前修復任何文法問題並進行他們認為必要的任何其他變更！
