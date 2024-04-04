# Fabric 文档贡献指南

此网站使用 [VitePress](https://vitepress.dev/) 从多个 Markdown 文件生成静态 HTML 网页。 您应该熟悉 VitePress 所支持的 Markdown 扩展语法，参见[此链接](https://vitepress.dev/guide/markdown.html#features)。

## 目录

- [Fabric 文档贡献指南](#fabric-documentation-contribution-guidelines)
  - [如何贡献](#how-to-contribute)
  - [贡献网页框架](#contributing-framework)
  - [贡献内容](#contributing-content)
    - [风格指南](#style-guidelines)
    - [扩展指南](#guidance-for-expansion)
    - [内容验证](#content-verification)
    - [清理](#cleanup)
  - [翻译文档](#translating-documentation)

## 如何贡献

建议您在存储库的分支上为您发出的每个拉取请求创建一个新分支。 这样一次性管理多个拉取请求将更简单。

**如果您需要本地预览您的更改，您需要安装 [Node.js 18+](https://nodejs.org/en/)**

在运行这些指令之前，请确保运行 `npm install` 以安装所有依赖。

**运行开发服务器：**

这将允许您在本地地址 `localhost:3000` 预览您的更改，并自动在修改时重载页面。

```bash
npm run dev
```

**构建网站：**

这将编译所有 Markdown 文件为静态 HTML 页面并保存至 `.vitepress/dist`

```bash
npm run build
```

**预览已构建的网站：**

这将在端口 3000 启动本地服务器并展示 `.vitepress/dist` 中的网页

```bash
npm run preview
```

## 贡献网页框架

“框架”指的是网站的内部结构，任何修改网站框架的拉取请求都应该用 `framework` 标签标记。

您应该在咨询了 [Fabric Discord](https://discord.gg/v6v4pMv) 上的文档团队或通过一个 issue 后再发起框架相关的拉取请求。

**注意：修改侧边栏文件和导航栏配置不算作框架拉取请求。**

## 贡献内容

贡献内容是最主要的向 Fabric 文档贡献的方式。

所有的内容都应当遵循我们的风格指南。

### 风格指南

在 Fabric 文档网站中的所有页面都应该遵循风格指南。 如果你有任何疑问，请在 [Fabric Discord](https://discord.gg/v6v4pMv) 或 GitHub Discussions 中提出。

风格指南如下：

1. 所有页面必须在 frontmatter 中有一个标题和描述。

   ```md
   ---
   title: 这是页面的标题
   description: 这是页面的描述
   authors:
     - 这是GitHub用户名
   ---

   # ...
   ```

2. 如果您创建或修改了包含代码的页面，请将代码放置在参考模组内的适当位置（位于存储库的 `/reference` 文件夹中）。 然后使用 [VuePress 提供的代码片段功能](https://vitepress.dev/zh/guide/markdown#import-code-snippets) 来嵌入代码。如果您需要更大范围的控制，可以使用 [来自 `markdown-it-vuepress-code-snippet-enhanced` 的 transclude 功能](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced)。

   **例如：**

   ```md
   <<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21 java}
   ```

   这会嵌入参考模组中 `FabricDocsReference.java` 的第 15-21 行。

   最终的代码片段将看起来像这样：

   ```java
     @Override
     public void onInitialize() {
       // This code runs as soon as Minecraft is in a mod-load-ready state.
       // However, some things (like resources) may still be uninitialized.
       // Proceed with mild caution.

       LOGGER.info("Hello Fabric world!");
     }
   ```

   **Transclude 示例**

   ```md
   @[code transcludeWith=#test_transclude](@/reference/.../blah.java)
   ```

   这将嵌入标记有 `#test_transclude` 标签的 `blah.java` 文件中的部分。

   例如：

   ```java
   public final String test = "Bye World!"

   // #test_transclude
   public void test() {
     System.out.println("Hello World!");
   }
   // #test_transclude
   ```

   只有 `#test_transclude` 标签之间的代码会被嵌入。

   ```java
   public void test() {
     System.out.println("Hello World!");
   }
   ```

3. 所有原始文档都使用英文书写，跟随美国的语法规则。 虽然你可以使用 [LanguageTool](https://languagetool.org/) 检查你的语法，但不要过于担心。 我们的文档团队会在清理阶段审查并纠正语法。 不过，一开始就努力做到正确可以为我们节省时间。

4. 如果您正在创建新的部分，您应当在 `.vitepress/sidebars` 文件夹中创建新的侧边栏，并将它添加到 `config.mts` 文件中。 如果您需要帮助，请在 [Fabric Discord](https://discord.gg/v6v4pMv) 的` #docs` 频道提问。

5. 创建新页面时，您应当将其添加到 `.vitepress/sidebars` 文件夹中相关的侧边栏中。 重复，如果您需要帮助，请在 Fabric Discord 的` #docs` 频道提问。

6. 任何图片都应该放在 `/assets` 文件夹中的适当位置。

7. ⚠️ **当链接其他页面时，使用相对链接。** ⚠️

   这是因为现有的版本控制系统会预处理链接，以便事先添加版本号。 如果您使用绝对链接，版本号将不会添加到链接中。

   例如，对于 `/players` 文件夹中的页面，要链接到位于 `/players/installing-fabric.md` 的 `installing-fabric` 页面，您需要进行以下操作：

   ```md
   [这是一个其他页面的链接](./installing-fabric.md)
   ```

   您**不**应当进行以下操作：

   ```md
   [这是一个其他页面的链接](/player/installing-fabric.md)
   ```

所有内容贡献都会经历三个阶段：

1. 扩展指南（如果可能）
2. 内容验证
3. 清理（语法等）

### 扩展指南

如果文档团队认为您需要拓展您的拉去请求，团队成员将添加 `expansion` 标签到您的拉去请求，并附上一条评论解释为什么他认为可以拓展。 如果你同意这条建议，你可以拓展你的拉取请求。

**不要对拓展拉取请求感到有压力。** 如果您不想拓展您的拉取请求，您可以简单地请求移除 `expansion` 标签。

如果您不想拓展您的拉取请求，但您乐于让其他人在未来拓展它，最好在 [Issues page](https://github.com/FabricMC/fabric-docs/issues) 创建一个 issue，并解释您想如何拓展。

### 内容验证

所有添加内容的拉取请求都会经过内容验证，这是最重要的阶段，因为它确保内容准确且遵循Fabric文档的风格指南。

### 清理

这个阶段是文档团队会修正任何语法问题并在合并拉取请求之前进行他们认为必要的任何其他修改的时候！

## 翻译文档

如果您想将该文档翻译为您的语言，您可以在 [Fabric Crowdin 页面](https://crowdin.com/project/fabricmc) 做这件事。
