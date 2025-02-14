---
title: 貢献ガイドライン
description: Fabricドキュメントへの貢献ガイドライン
---

このWebサイトは、Markdownファイルから静的HTMLを生成するのに [VitePress](https://vitepress.dev/) を使っています。 VitePress がサポートする Markdown 拡張機能については、[こちら](https://vitepress.dev/guide/markdown#features)でよく理解しておく必要があります。 VitePress がサポートする Markdown 拡張機能については、[こちら](https://vitepress.dev/guide/markdown#features)でよく理解しておく必要があります。

この Web サイトに貢献するには、次の 3 つの方法があります。

- [ドキュメントの翻訳](#translating-documentation)
- [コンテンツの寄稿](#contributing-content)
- [フレームワークの貢献](#contributing-framework)

すべての貢献は、[スタイルガイドライン](#style-guidelines)に従ってください。

## ドキュメントの翻訳 {#translating-documentation}

ドキュメントを自分の言語に翻訳したい場合は、[Fabric Crowdin ページ](https://crowdin.com/project/fabricmc)で行うことができます。

<!-- markdownlint-disable-next-line titlecase-rule -->

## <0>new-content</0> コンテンツの寄稿 {#contributing-content}

コンテンツの寄稿は、Fabric ドキュメントに貢献する主な方法です。

すべてのコンテンツ投稿は次の段階を経て行われ、各段階にはラベルが関連付けられます。

1. <Badge type="tip">locally</Badge> 変更を準備してPRをプッシュ
2. <Badge type="tip">stage:expansion</Badge>: 必要に応じて拡張するためのガイダンス
3. <Badge type="tip">stage:verification</Badge>: コンテンツの検証
4. <Badge type="tip">stage:cleanup</Badge>: 文法、添削...
5. <0>stage:ready</0>: マージの準備完了！

すべてのコンテンツは、[スタイルガイドライン](#style-guidelines)に従ってください。

### 1. 変更を準備 {#1-prepare-your-changes}

このWebサイトはオープンソースで、Githubのリポジトリで管理されているため、Githubでの手順に依存しています。

1. [GitHubのリポジトリをForkする](https://github.com/FabricMC/fabric-docs/fork)
2. あなたのForkで新しいブランチを作成する
3. そのブランチで変更を作る
4. 元のリポジトリにPull Requestを作る

Github フローについて詳しくは[こちら](https://docs.github.com/en/get-started/using-github/github-flow)をご覧ください。

GitHub のWebインターフェースから編集するか、ローカルでWebサイトを構築してプレビューすることもできます。

#### あなたのForkをクローンする {#clone-your-fork}

ローカルで開発する場合は、[Git](https://git-scm.com/)をインストールする必要があります。

そのあと、あなたのリポジトリをクローンします。

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### 依存関係のインストール {#install-dependencies}

もしローカルで変更をプレビューしたい場合、[Node.js 18+](https://nodejs.org/en/)をインストールする必要があります。

その後、次のコマンドを使用してすべての依存関係を必ずインストールしてください。

```sh
npm install
```

#### 開発サーバーの実行 {#run-the-development-server}

`localhost:5173` でローカルに変更をプレビューできるようになり、変更を加えるとページが自動的にリロードされます。

```sh
npm run dev
```

これで、`http://localhost:5173` にアクセスして、ブラウザからWebサイトを開いて閲覧できるようになります。

#### ウェブサイトの構築 {#building-the-website}

これによりすべての Markdown ファイルが静的 HTML ファイルにコンパイルされ、`.vitepress/dist` に配置されます。

```sh
npm run build
```

#### 構築した Web サイトのプレビュー {#previewing-the-built-website}

これにより、`.vitepress/dist` にあるコンテンツを提供するポート `4173` でローカル サーバーが起動します。

```sh
npm run preview
```

#### Pull Requestを作る {#opening-a-pull-request}

変更内容に満足したら、変更内容を `push` できます。

```sh
git add .
git commit -m "Description of your changes"
git push
```

次に、`git push` の出力にあるリンクに従って PR を開きます。

### 2. <Badge type="tip">stage:expansion</Badge> 必要に応じて拡張するためのガイダンス {#2-guidance-for-expansion-if-needed}

ドキュメンテーションチームがPull Requestを拡張できると判断した場合、チームのメンバーがPull Requestに <Badge type="tip">stage:expansion</Badge> ラベルを追加し、拡張できると思われる内容を説明するコメントをします。 提案に同意する場合は、Pull Requestを拡張できます。 提案に同意する場合は、Pull Requestを拡張できます。

自分でPull Requestを拡張するつもりはないが、後日他のユーザーが拡張しても構わない場合は、[Issueページ](https://github.com/FabricMC/fabric-docs/issues) で新しいIssueを作成し、拡張が必要と思われる内容を説明してください。 ドキュメンテーションチームは、あなたのPull Requestに <Badge type="tip">help-wanted</Badge> ラベルを追加します。 ドキュメンテーションチームは、あなたのPull Requestに <Badge type="tip">help-wanted</Badge> ラベルを追加します。

### 3. <Badge type="tip">stage:verification</Badge> コンテンツの検証 {#3-content-verification}

コンテンツが正確で、Fabricドキュメントのスタイルガイドに従っていることを確認するため、このステージが最も重要です。

このステージでは、次の質問に答える必要があります。

- すべての内容が正確ですか？
- すべての内容が最新ですか？
- 内容はOSの違いなど、あらゆる場合をカバーしているか？

### 4. <Badge type="tip">stage:cleanup</Badge> クリーンアップ {#4-cleanup}

このステージでは、次のことが行われます。

- [LanguageTool](https://languagetool.org/)を使って、文法の問題や誤字を修正します。
- [`markdownlint`](https://github.com/DavidAnson/markdownlint)を使って、リンティングします。
- [Checkstyle](https://checkstyle.sourceforge.io/)を使って、Javaのコードをフォーマットします。
- その他の修正や改善

## <Badge type="tip">framework</Badge> コントリビュートの枠組み {#contributing-framework}

フレームワークとは、Webサイトの構造のことを指します。フレームワークを変更するPull Requestには <Badge type="tip">framework</Badge> ラベルがつけられます。

フレームワークのPull Requestは、[Fabric Discord](https://discord.gg/v6v4pMv)またはissueでドキュメントチームに相談した後にのみ行う必要があります。

:::info
サイドバーとナビゲーションバーの設定の変更は、フレームワークのPull Requestとしてみなされません。
:::

## スタイルガイドライン {#style-guidelines}

分からないことがあれば、[Fabric Discord](https://discord.gg/v6v4pMv)またはGitHub Discussionsで聞いてください。

### 原文をアメリカ英語で書く {#write-the-original-in-american-english}

オリジナルドキュメントはすべて、アメリカの文法規則に従って英語で書かれています。

### Frontmatterにデータを追加 {#add-data-to-the-frontmatter}

それぞれのページは`titile`と`description`をfrontmatterに含んでいる必要があります。

Markdownファイルのfrontmatterの`authors`にあなたのGitHubのユーザー名も忘れずに追加してください！ そうすればあなたに適切なクレジットを付与することができます。 そうすればあなたに適切なクレジットを付与することができます。

```yaml
---
title: Title of the Page
description: This is the description of the page.
authors:
  - your-username
---
```

### 見出しにアンカーを追加する {#add-anchors-to-headings}

それぞれの見出しには、その見出しへのリンクに使用されるアンカーが必要です。

```md
## This Is a Heading {#this-is-a-heading}
```

アンカーには、英小文字, 数字, ハイフン(-) のみを使用してください。

### コードを`/reference`のModに配置する {#place-code-within-the-reference-mod}

コードを含むページを作成または変更する場合は、リファレンスMOD（リポジトリの `/reference` フォルダにあります）内の適切な場所にコードを配置してください。 コードを含むページを作成または変更する場合は、リファレンスMOD（リポジトリの `/reference` フォルダにあります）内の適切な場所にコードを配置してください。 そして、[VitePressが提供するコードスニペット機能](https://vitepress.dev/guide/markdown#import-code-snippets)を使ってコードを埋め込みます。

たとえば、`FabricDocsReference.java`ファイルの15行目から21行目をリファレンスモッドからハイライトするには

::: code-group

```md
<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}
```

<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}[java]

:::

より大きな制御範囲が必要な場合は、[`markdown-it-vuepress-code-snippet-enhanced`のtransclude機能](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced)を使うことができます。

たとえば、これは上のファイルの `#entrypoint` タグでマークされた部分を埋め込むには

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)
```

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

:::

### 新しいセクションごとにサイドバーを作る {#create-a-sidebar-for-each-new-section}

新しいセクションを作成する場合は、`.vitepress/sidebars`フォルダに新しいサイドバーを作成し、`i18n.mts`ファイルに追加してください。

サポートが必要な場合は、[Fabric Discord](https://discord.gg/v6v4pMv) の `#docs` チャンネルで聞いてください。

### 関連するサイドバーに新しいページを追加する {#add-new-pages-to-the-relevant-sidebars}

新しいページを作成する際は、`.vitepress/sidebars`フォルダ内の関連するサイドバーに追加してください。

繰り返しになりますが、サポートが必要な場合はFabric Discordの `#docs` チャンネルで聞いてください。

### 画像を`/assets`に配置する {#place-media-in-assets}

画像は`/public/assets`内の適切な場所に配置してください。

### 相対リンクを使う！ 相対リンクを使う！ {#use-relative-links}

これはバージョン管理が導入されているためで、リンクはあらかじめバージョンを追加するように処理されます。 これはバージョン管理が導入されているためで、リンクはあらかじめバージョンを追加するように処理されます。 絶対リンクを使う場合、バージョン番号はリンクに追加されません。

また、リンクにファイルの拡張子を追加しないでください。

たとえば、`/develop/index.md`から`/players/index.md`にリンクを貼るには、次のようにする必要があります。

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
