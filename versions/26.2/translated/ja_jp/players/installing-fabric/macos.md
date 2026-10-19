---
title: MacOSにFabricをインストールする
description: MacOSにFabricをインストールするための手順付きのガイド。
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info 前提条件

`.jar`を実行するために [Javaをインストール](../installing-java/macos) する必要があります。

:::

<!-- #region common -->

## 1. Fabric インストーラーをダウンロード {#1-download-the-fabric-installer}

[Fabric の公式サイト](https://fabricmc.net/use/)から`Download installer (Universal/.JAR)`をクリックして`.jar`バージョンのFabricインストーラーをダウンロードする。

## 2. Fabric インストーラーを実行する {#2-run-the-fabric-installer}

インストールを実行するためにMinecraftとMinecraft ランチャーを閉じてください。

::: tip

Appleが`.jar`ファイルを検証できなかったという警告が表示される場合があります。 これを回避するには、「システム設定」＞「プライバシーとセキュリティ」を開き、「とにかく開く」をクリックします。 指示があった場合は、管理者パスワードを確認して入力してください。

![macOS システム設定](/assets/players/installing-fabric/macos-settings.png)

:::

インストーラーを実行すると、以下のような画面が表示されます：

![Fabricインストーラー （"Install"がハイライト表示されています）](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

希望するMinecraftのバージョンを選択し、 `Install`をクリックしてください。 `Create Profile`にチェックが入ってることを確認してください。

### Homebrew 経由でのインストール {#installing-via.homebrew}

既に[Homebrew](https://brew.sh) がインストールされている場合は、代わりに`brew`を使用してFabricインストーラーをインストールできます：

```sh
brew install fabric-installer
```

## 3. セットアップの完了 {#3-finish-setup}

インストールが完了したら、Minecraft ランチャーを起動してください。 次に、バージョンを選択からFabric プロファイルを選択し、Play を押してください。

![Fabricプロファイルを選択したMinecraftランチャー](/assets/players/installing-fabric/launcher-screen.png)

ゲームにModを追加できるようになりました。 詳しくは、[信頼できるモッドを見つける](../finding-mods) のガイドを参照してください。

問題が発生した場合は、[Fabric Discord](https://discord.fabricmc.net/) の`#player-support`チャンネルで遠慮なくサポートを求めてください。
