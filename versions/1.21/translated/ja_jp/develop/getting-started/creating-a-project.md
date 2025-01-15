---
title: プロジェクトの作成
description: Fabric テンプレート Mod ジェネレータを使用した Mod 作成の段階的なガイド。
authors:
  - IMB11
---

# プロジェクトの作成 {#creating-a-project}

Fabric は、Mod プロジェクトを簡単に作成することを可能にする Fabric テンプレート Mod ジェネレータを提供しています。手動でプロジェクトを作成したい場合は、サンプルの Mod リポジトリを使用することができます。[手動で作成する](#manual-project-creation) セクションを参照してください。

## プロジェクトの生成 {#generating-a-project}

[Fabric テンプレート Mod ジェネレータ](https://fabricmc.net/develop/template/) を使用すると新しいプロジェクトを生成することができます。パッケージ名、Mod 名、Mod が対応する Minecraft バージョンの入力が必要です。

![ジェネレータのプレビュー](/assets/develop/getting-started/template-generator.png)

Kotlin を使用したい場合や、データ生成を行いたい場合は、`Advanced Options` セクションで適切なオプションを選択してください。

![Advanced Options セクション](/assets/develop/getting-started/template-generator-advanced.png)

必要な項目の入力が完了したら、`Generate` ボタンを押してください。ジェネレータが新しいプロジェクトを zip ファイル形式で生成します。

zip ファイルを任意の場所で解凍して、解凍されたフォルダを IntelliJ IDEA で開いてください。

![プロジェクトを開く 画面](/assets/develop/getting-started/open-project.png)

## プロジェクトのインポート {#importing-the-project}

プロジェクトを IntelliJ IDEA で開くと、IDE は自動で Gradle 設定を読み込み、セットアップに必要なタスクを行います。

Gradle ビルドスクリプトに関する通知が表示された場合は、`Gradle プロジェクトのインポート` ボタンを押してください:

![Gradle プロンプト](/assets/develop/getting-started/gradle-prompt.png)

プロジェクトのインポートが完了すると、プロジェクトツールウィンドウにファイルが表示され、Mod の開発を始めることができるようになります。

## 手動によるプロジェクトの作成 {#manual-project-creation}

:::warning
サンプルの Mod リポジトリをクローンするためには、[Git](https://git-scm.com/) のインストールが必要になります。
:::

Fabric テンプレート Mod ジェネレータを使用できない場合は、次のステップを踏むことで手動で新しいプロジェクトを作成できます。

まず、Git を使用してサンプル Mod リポジトリをクローンします:

```sh
git clone https://github.com/FabricMC/fabric-example-mod/ my-mod-project
```

このコマンドは、リポジトリを `my-mod-project` という名前のフォルダにクローンします。

続いて、フォルダ内の `.git` フォルダを削除し、IntelliJ IDEA でプロジェクトを開いてください。 `.git` フォルダが表示されない場合は、ファイルマネージャで隠しファイルの表示を有効にする必要があります。

プロジェクトを IntelliJ IDEA で開くと、IDE は自動で Gradle 設定を読み込み、セットアップに必要なタスクを行います。

前述の通り、Gradle ビルドスクリプトに関する通知が表示された場合は、`Gradle プロジェクトのインポート` ボタンを押してください。

### テンプレートの編集 {#modifying-the-template}

プロジェクトがインポートされたら、プロジェクトの詳細をあなたの Mod の詳細に合わせて編集してください:

- `gradle.properties` ファイル内の `maven_group` と `archive_base_name` を編集します。
- `fabric.mod.json` ファイル内の `id`、`name`、`description` プロパティを編集します。
- Minecraft、マッピング、Fabric Loader、Loom のバージョンを、対応させたい値に編集します。これらは https://fabricmc.net/develop/ で照会できます。

言うまでもないですが、パッケージ名と Mod のメインクラスも適宜編集してください。
