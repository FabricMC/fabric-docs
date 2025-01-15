---
title: 開発環境のセットアップ
description: Fabric を使った Mod 開発環境をセットアップするための段階的なガイド。
authors:
  - IMB11
  - andrew6rant
  - SolidBlock-cn
  - modmuss50
  - daomephsta
  - liach
  - telepathicgrunt
  - 2xsaiko
  - natanfudge
  - mkpoli
  - falseresync
  - asiekierka
authors-nogithub:
  - siglong
---

# 開発環境のセットアップ {#setting-up-a-development-environment}

Fabric を使って Mod を開発するためには、IntelliJ IDEA を使用した開発環境を設定する必要があります。

## JDK 21 のインストール {#installing-jdk-21}

Minecraft 1.21 を対象にした Mod を開発するには、JDK 21 が必要です。

Java のインストールでお困りの場合、[プレイヤーガイド](../../players/index) 内の Java インストールガイドをご覧になれます。

## IntelliJ IDEA のインストール {#installing-intellij-idea}

:::info
このドキュメント内の多くの記事は IntelliJ IDEA の使用を前提として書かれています。Eclipse や Visual Studio Code などの別の IDE もお使いいただけますが、その場合は公式のドキュメントを確認するようにしてください。
:::

まだ IntelliJ IDEA をインストールしていない場合は、[公式ウェブサイト](https://www.jetbrains.com/idea/download/) からダウンロードできます。お使いの OS のインストールガイドに従ってください。

Community 版の IntelliJ IDEA は無料かつオープンソースであり、Fabric を使った Modding に推奨されています。

Community 版のダウンロードリンクを見つけるにはページをスクロールする必要があるかもしれません。リンクは以下のようになっています:

![IDEA Community Edition Download Prompt](/assets/develop/getting-started/idea-community.png)

## IDEA プラグインのインストール {#installing-idea-plugins}

このプラグインは必須ではありません。しかし、Fabric を使った Modding を非常に簡単にします。インストールを検討してください。

### Minecraft Development {#minecraft-development}

Minecraft Development プラグインは Fabric を使った Modding を支援します。これは最もインストールすべきプラグインです。

インストールするには、IntelliJ IDEA を開き、`ファイル（macOS の場合は、IntelliJ IDEA） > 設定 > プラグイン > Marketplace タブ` を選択します。表示された検索バーで `Minecraft Development` を検索し、`インストール` ボタンをクリックしてください。

また、[プラグインページ](https://plugins.jetbrains.com/plugin/8327-minecraft-development) からダウンロードして、`ファイル > 設定 > プラグイン > ディスクからプラグインをインストール` からインストールすることもできます。
