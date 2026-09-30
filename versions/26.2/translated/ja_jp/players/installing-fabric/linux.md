---
title: LinuxにFabricをインストールする
description: LinuxにFabricをインストールするための手順付きのガイド。
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info 前提条件

`.jar`を実行するために [Javaをインストール](../installing-java/linux) する必要があります。

:::

<!-- #region common -->

## 1. Fabric インストーラーをダウンロード {#1-download-the-fabric-installer}

[Fabric の公式サイト](https://fabricmc.net/use/)から`Download installer (Universal/.JAR)`をクリックして`.jar`バージョンのFabricインストーラーをダウンロードする。

## 2. Fabric インストーラーを実行する {#2-run-the-fabric-installer}

インストールを実行するためにMinecraftとMinecraft ランチャーを閉じてください。

ターミナルを開き、Javaを使用してインストールします。

```sh
java -jar fabric-installer.jar
```

インストーラーを実行すると、以下のような画面が表示されます：

![Fabricインストーラー（"インストール"がハイライト表示されています）](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

希望するMinecraftのバージョンを選択し、 `Install`をクリックしてください。 `Create Profile`にチェックが入ってることを確認してください。

## 3. セットアップの完了 {#3-finish-setup}

インストールが完了したら、Minecraft ランチャーを起動してください。 次に、バージョンを選択からFabric プロファイルを選択し、Play を押してください。

![Fabricプロファイルを選択したMinecraftランチャー](/assets/players/installing-fabric/launcher-screen.png)

ゲームにModを追加できるようになりました。 詳しくは、[信頼できるモッドを見つける](../finding-mods) のガイドを参照してください。

問題が発生した場合は、[Fabric Discord](https://discord.fabricmc.net/) の`#player-support`チャンネルで遠慮なくサポートを求めてください。
