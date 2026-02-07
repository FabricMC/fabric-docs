---
title: 贡献指南
description: Fabric 文档贡献指南
---

此网站使用 [VitePress](https://vitepress.dev/) 从多个 Markdown 文件生成静态 HTML 网页。 你应该熟悉 [VitePress 支持的 Markdown 扩展](https://vitepress.dev/guide/markdown#features)。

贡献此网站有三种方法：

- [翻译文档](#translating-documentation)
- [贡献内容](#contributing-content)
- [贡献网页框架](#contributing-framework)

所有贡献应该遵守我们的[样式指南](#style-guidelines)。

## 翻译文档{#translating-documentation}

如果想把文档翻译成你的语言，你可以在 [Fabric 的 Crowdin 页面](https://crowdin.com/project/fabricmc)进行。

<!-- markdownlint-disable titlecase-rule -->

## <Badge type="tip">new-content</Badge> 贡献内容{#contributing-content}

<!-- markdownlint-enable titlecase-rule -->

内容贡献是参与 Fabric 文档的主要方式。

所有内容贡献都经过以下阶段，每个阶段都关联有一个标签：

1. <Badge type="tip">locally</Badge> 准备你的更改并推送一个 PR
2. <Badge type="tip">stage:expansion</Badge> 如果需要，扩展指南
3. <Badge type="tip">stage:verification</Badge> 内容验证
4. <Badge type="tip">stage:cleanup</Badge> 语法、代码检查……
5. <Badge type="tip">stage:ready</Badge> 准备合并！

所有内容都必须遵循我们的[风格指南](#style-guidelines)。

### 1. 准备你的更改 {#1-prepare-your-changes}

本网站是开源的，它是在 GitHub 代码仓库中开发的，这意味着我们依赖于 GitHub 流：

1. [Fork GitHub 仓库](https://github.com/FabricMC/fabric-docs/fork)
2. 在你的 fork 上创建一个新分支
3. 在那个分支上进行你的修改
4. 向原始仓库打开一个 Pull Request

你可以阅读更多关于 [GitHub 流](https://docs.github.com/en/get-started/using-github/github-flow)的信息。

你可以从 GitHub 的 Web UI 进行更改，也可以在本地开发和预览网站。

#### 克隆你的分支 {#clone-your-fork}

如果你想在本地开发，你需要安装 [Git](https://git-scm.com/) 。

之后，使用以下命令克隆你的代码仓库分支：

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### 安装依赖项 {#install-dependencies}

如果希望在本地预览你的更改，你需要安装 [Node.js 18 或更高版本](https://nodejs.org/en/)。

之后，确保使用以下命令安装所有依赖项：

```sh
npm install
```

#### 运行开发服务器 {#run-the-development-server}

这将允许你在本地预览 `localhost:5173` 的更改，并在你进行更改时自动重新加载页面。

```sh
npm run dev
```

现在你可以通过访问 `http://localhost:5173` 在浏览器中打开和浏览网站。

#### 构建网站 {#building-the-website}

这将编译所有 Markdown 文件为静态 HTML 文件，并将它们放置在 `.vitepress/dist` ：

```sh
npm run build
```

#### 预览已构建的网站 {#previewing-the-built-website}

这将启动一个本地服务器，监听端口 `4173` ，并提供 `.vitepress/dist` 中的内容：

```sh
npm run preview
```

#### 打开 Pull Request {#opening-a-pull-request}

一旦你对更改满意，你可以 `push` 你的更改：

```sh
git add .
git commit -m "Description of your changes"
git push
```

然后，点击 `git push` 输出中的链接来创建一个 PR。

### 2. <Badge type="tip">stage:expansion</Badge> 按需扩展指南 {#2-guidance-for-expansion-if-needed}

如果文档团队认为你的 pull request 可以进行扩展，团队中的某成员会在你的 pull request 上添加 <Badge type="tip">stage:expansion</Badge> 标签，并附上一条评论说明他们认为你可以扩展的内容。 如果你同意这个建议，你可以对 pull request 进行扩展。

如果你不希望扩展你的 pull request，但愿意日后让别人来扩展，你应该在 [Issues 页面](https://github.com/FabricMC/fabric-docs/issues)创建一个 issue 并解释你认为可以扩展的内容。 然后文档团队会向你的 PR 添加 <Badge type="tip">help-wanted</Badge> 标签。

### 3. <Badge type="tip">stage:verification</Badge> 内容验证 {#3-content-verification}

这是最重要的阶段，因为它确保内容准确并遵循 Fabric 文档风格指南。

在这个阶段，以下问题应该得到回答：

- 所有内容是否都是正确的？
- 所有内容是否都是最新的？
- 内容是否涵盖了所有情况，例如不同的操作系统？

### 4. <Badge type="tip">stage:cleanup</Badge> 清理 {#4-cleanup}

在这个阶段，会发生以下情况：

- 使用 [LanguageTool](https://languagetool.org/) 修复任何语法问题
- 使用 [markdownlint](https://github.com/DavidAnson/markdownlint) 检查所有 Markdown 文件
- 使用 [Checkstyle](https://checkstyle.sourceforge.io/) 格式化所有 Java 代码
- 其他杂项修复或改进

## <Badge type="tip">framework</Badge> 贡献网页框架 {#contributing-framework}

框架指的是网站的内部结构，任何修改网站框架的 pull request 都将被标记为 <Badge type="tip">framework</Badge> 标签。

你应该在咨询 [Fabric Discord](https://discord.fabricmc.net/) 上的文档团队或通过 issue 提问后，才真正地提交框架 pull request。

::: info

修改侧边栏文件和导航栏配置不算作框架 pull request。

:::

## 风格指南 {#style-guidelines}

如果你有任何疑问，可以在 [Fabric Discord](https://discord.fabricmc.net/) 或通过 GitHub Discussions 提问。

### 用美式英语撰写原始内容 {#write-the-original-in-american-english}

所有原始文档均以英语撰写，遵循美式语法规则。

### 向元数据头添加数据 {#add-data-to-the-frontmatter}

每个页面在元数据头中必须有一个 `title` 和一个 `description` 。

记得在 Markdown 文件的元数据头中将你的 GitHub 用户名添加到 `authors` ！ 这样我们才能正确地署上你的名字。

```yaml
---
title: Title of the Page
description: This is the description of the page.
authors:
  - your-username
---
```

### 为标题添加锚点 {#add-anchors-to-headings}

每个标题必须有一个锚点，用于链接到该标题：

```md
## This Is a Heading {#this-is-a-heading}
```

锚点必须使用小写字母、数字和连字符。

### 在示例模组中放置代码 {#place-code-within-the-example-mod}

如果你创建或修改包含代码的页面，请将代码放置在示例模组中的适当位置（位于存储库的 `/reference` 文件夹中）。 然后，使用 [VitePress 提供的代码片段功能](https://vitepress.dev/guide/markdown#import-code-snippets)嵌入代码。

例如，要突出显示来自模组的 `ExampleMod.java` 文件的第 15-21 行：

::: code-group

```md
<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java{15-21}
```

<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java{15-21}[java]

:::

如果你需要更大的控制范围，你可以使用 [`markdown-it-vuepress-code-snippet-enhanced` 的 transclude 功能](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced)。

例如，这将嵌入上面文件中带有 `#entrypoint` 标签的部分：

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)
```

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

:::

### 为每个新部分创建一个侧边栏 {#create-a-sidebar-for-each-new-section}

如果你正在创建一个新章节，你应该在 `.vitepress/sidebars` 文件夹中创建一个新的侧边栏，并将其添加到 `i18n.mts` 文件中。

如果你需要这方面的帮助，请在 [Fabric Discord](https://discord.fabricmc.net/) 的 `#docs` 频道中提问。

### 向相关侧边栏添加新页面 {#add-new-pages-to-the-relevant-sidebars}

在创建新页面时，你应该将其添加到 `.vitepress/sidebars` 文件夹中的相关侧边栏。

再次，如果您需要帮助，请在 Fabric Discord 的 `#docs` 频道中提问。

### 将媒体文件放置在 `/assets` 中 {#place-media-in-assets}

任何图片都应该放置在 `/public/assets` 文件夹中的合适位置。

### 使用相对链接！ {#use-relative-links}

这是因为版本管理系统会处理链接，在链接中预先添加版本号。 如果你使用绝对链接，链接中不会添加版本号。

你也不应该在链接中添加文件扩展名。

例如，要从页面 `/develop/index.md` 链接到页面 `/players/index.md` 中的页面，你需要这样做：

::: code-group

```md:no-line-numbers [✅ Correct]
This is a relative link!
[Page](../players/index)
```

```md:no-line-numbers [❌ Wrong]
This is an absolute link.
[Page](/players/index)
```

```md:no-line-numbers [❌ Wrong]
This relative link has the file extension.
[Page](../players/index.md)
```

:::

<!---->
