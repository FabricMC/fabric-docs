---
title: 贡献指南
description: Fabric 文档贡献指南
---

此网站使用 [VitePress](https://vitepress.dev/) 从多个 Markdown 文件生成静态 HTML 网页。 您应该熟悉 VitePress 所支持的 Markdown 扩展语法，参见[此链接](https://vitepress.dev/guide/markdown.html#features)。 您应该熟悉 VitePress 所支持的 Markdown 扩展语法，参见[此链接](https://vitepress.dev/guide/markdown.html#features)。

贡献此网站有三种方法：

- [翻译文档](#translating-documentation)
- [贡献内容](#contributing-content)
- [贡献网页框架](#contributing-framework)

所有贡献应该遵守我们的[样式指南](#style-guidelines)。

## 翻译文档 {#translating-documentation}

如果想将文档翻译为你的语言，可以在 [Fabric Crowdin 页面](https://zh.crowdin.com/project/fabricmc)翻译。

<!-- markdownlint-disable-next-line titlecase-rule -->

## <Badge type="tip">new-content</Badge> 贡献内容 {#contributing-content}

贡献内容是贡献 Fabric 文档的主要方式。

所有内容贡献都经过以下步骤，每个步骤都与一个标签相关联：

1. <Badge type="tip">本地</Badge> 准备您的更改并推出一个PR
2. <Badge type="tip">stage:expansion</Badge>：扩展指南（如果可能）
3. <Badge type="tip">stage:verification</Badge>：内容验证
4. <Badge type="tip">步骤：整理中</Badge>：语法检查中...
5. <Badge type="tip">步骤：准备</Badge>：准备合并！

所有的内容都应当遵循我们的[样式指南](#style-guidelines)。

### 1. 准备你的更改 {#1-prepare-your-changes}

网站是开源的，在 GitHub 仓库中开发，意味着我们依赖 GitHub 工作流。

1. [复刻 GitHub 仓库](https://github.com/FabricMC/fabric-docs/fork)
2. 为你的复刻创建新分支
3. 在那个分支上做出更改
4. 在源仓库开拉取请求

可以在[这里](https://docs.github.com/en/get-started/using-github/github-flow)了解更多关于 GitHub 流。

可以在 GitHub 网站界面上做出更改，也可以本地开发和预览网站。

#### 克隆你的复刻 {#clone-your-fork}

如果想要本地克隆复刻，需要安装 [Git](https://git-scm.com/)。

然后，用以下代码克隆仓库的复刻：

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### 安装依赖 {#install-dependencies}

**如果想要本地预览更改，需要安装 [Node.js 18+](https://nodejs.org/en/)**

然后，确保用以下代码安装所有依赖：

```sh
npm install
```

#### 运行开发服务器 {#run-the-development-server}

这将允许您在本地地址 `localhost:3000` 预览您的更改，并自动在修改时重载页面。

```sh
npm run dev
```

现在可以从浏览器访问 `http://localhost:5173` 打开和浏览网站。

#### 构建网站 {#building-the-website}

这会把所有 Markdown 文件编译为静态 HTML 文件并保存至 `.vitepress/dist`：

```sh
npm run build
```

#### 预览构建的网站 {#previewing-the-built-website}

这将在端口 `4173` 启动本地服务器并展示 `.vitepress/dist` 中的网页：

```sh
npm run preview
```

#### 打开拉取请求 {#opening-a-pull-request}

对你的更改满意了，就可以 `推送（push）` 你的更改。

```sh
git add .
git commit -m "Description of your changes"
git push
```

然后，跟随 `git push` 的输出打开拉取请求。

### 2. <Badge type="tip">stage:expansion</Badge> 需要时扩展指南 {#2-guidance-for-expansion-if-needed}

如果文档团队认为您需要拓展您的拉去请求，团队成员将添加 <Badge type="tip">stage:expansion</Badge> 标签到您的拉去请求，并附上一条评论解释为什么他认为可以拓展。 如果同意建议，可以扩展你的拉取请求。 如果同意建议，可以扩展你的拉取请求。

如果不想扩展您的拉取请求，但乐于让其他人在未来扩展它，最好在[议题页面](https://github.com/FabricMC/fabric-docs/issues)创建议题，并解释您想如何扩展。 之后文档制作团队将会在你的PR中加入<Badge type="tip">帮助-追踪</Badge>标签 之后文档制作团队将会在你的PR中加入<Badge type="tip">帮助-追踪</Badge>标签

### 3. <Badge type="tip">stage:verification</Badge> 内容验证 {#3-content-verification}

这是最重要的阶段，因为这能确保内容准确且遵循 Fabric 文档的样式指南。

在这一步，以下问题应被回答：

- 所有内容都正确吗？
- 所有内容都是最新的吗？
- 内容是否涵盖所有情况，例如不同的操作系统？

### 4. <Badge type="tip">stage:cleanup</Badge> 清理 {#4-cleanup}

在这一步，会发生以下内容：

- 使用[LanguageTool](https://languagetool.org/)来修复一切语法问题
- 使用 [`markdownlint`](https://github.com/DavidAnson/markdownlint) 检查所有 Markdown 文件
- 使用[检查工具](https://checkstyle.sourceforge.io/)来格式化所有Java代码
- 其它杂项翻译或优化

## <Badge type="tip">framework</Badge> 贡献网页框架 {#contributing-framework}

“框架”指的是网站的内部结构，任何修改网站框架的拉取请求都应该用 <Badge type="tip">framework</Badge> 标签标记。

您应该在咨询了 [Fabric Discord](https://discord.gg/v6v4pMv) 上的文档团队或通过一个 issue 后再发起框架相关的拉取请求。

:::info
修改侧边栏文件和导航栏配置不算作框架拉取请求。
:::

## 样式指南 {#style-guidelines}

如果有任何疑问，请在 [Fabric Discord](https://discord.gg/v6v4pMv) 或 GitHub Discussions 中提出。

### 用美式英语写原文 {#write-the-original-in-american-english}

所有原始文档都使用英文书写，遵循美国的语法规则。

### 给 Frontmatter 添加数据 {#add-data-to-the-frontmatter}

所有页面必须在 frontmatter 中有 `title` 和 `description`。

记得还要在 Markdown 文件的 frontmatter 中的 `authors` 添加你的 GitHub 用户名！ 这种方式可以给你适当的致谢。 这种方式可以给你适当的致谢。

```yaml
---
title: Title of the Page
description: This is the description of the page.
authors:
  - your-username
---
```

### 给标题添加锚点 {#add-anchors-to-headings}

每个标题都必须要有个锚点，用于链接至那个标题：

```md
## This Is a Heading {#this-is-a-heading}
```

锚点必须使用小写字母、数字和横杠

### 将代码置于 `/reference` 模组中 {#place-code-within-the-reference-mod}

如果创建或修改包含代码的页面，将代码置于参考模组（位于目录的 `/reference` 文件夹内）的适当位置。 如果创建或修改包含代码的页面，将代码置于参考模组（位于目录的 `/reference` 文件夹内）的适当位置。 然后，使用[由 VitePress 提供的代码片段功能](https://vitepress.dev/guide/markdown#import-code-snippets)来嵌入代码。

例如，高亮参考模组中的 `FabricDocsReference.java` 的第 15-21 行：

::: code-group

```md
<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}
```

<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}[java]

:::

如果需要更大范围的控制，可以使用[来自 `markdown-it-vuepress-code-snippet-enhanced` 的 transclude 功能](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced)。

例如，这会嵌入上面的文件中被标记 `#entrypoint` 标签的部分：

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)
```

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

:::

### 为每个新段落创建侧边栏 {#create-a-sidebar-for-each-new-section}

如果创建新章节，你应该在 `.vitepress/sidebars` 文件夹中创建新的侧边栏，并添加到 `i18n.mts` 文件。

如果这个需要帮助，请在 [Fabric Discord](https://discord.gg/v6v4pMv) 的 `#docs` 频道提问。

### 将新页面添加到相关的侧边栏 {#add-new-pages-to-the-relevant-sidebars}

写新页面时，应该将其添加到 `.vitepress/sidebars` 文件夹中的相关侧边栏。

还是那句，如果需要帮助，请在 Fabric Discord 的 `#docs` 频道提问。

### 把媒体放在 `/assets` 中 {#place-media-in-assets}

任何图片都应该放在 `/assets` 文件夹中的适当位置。

### 使用相对链接！ 使用相对链接！ {#use-relative-links}

这是因为现有的版本控制系统会预处理链接，以便事先添加版本号。 如果您使用绝对链接，版本号将不会添加到链接中。

你也不能够给链接添加扩展名。

例如，要从页面 `/players/index.md` 链接到位于 `/players/installing-fabric.md` 的页面，需要进行以下操作：

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
