---
title: ゲームの起動
description: さまざまな起動プロファイルを活用して、実際のゲーム環境で Mod を起動およびデバッグする方法を学びましょう。
authors:
  - IMB11
  - Tenneb22
---

Fabric Loom は、実際のゲーム環境で Mod を起動およびデバッグするのに役立つさまざまな起動プロファイルを提供します。 このガイドでは、さまざまな起動プロファイルと、それらを使用して Mod をデバッグおよびテストする方法について説明します。 このガイドでは、さまざまな起動プロファイルと、それらを使用して Mod をデバッグおよびテストする方法について説明します。

## 起動プロファイル {#launch-profiles}

IntelliJ IDEA を使用している場合は、ウィンドウの右上に起動プロファイルが表示されます。 ドロップダウンメニューをクリックすると、利用可能な起動プロファイルの一覧が表示されます。 ドロップダウンメニューをクリックすると、利用可能な起動プロファイルの一覧が表示されます。

クライアントプロファイルとサーバープロファイルがあり、通常モードで実行するかデバッグ モードで実行するかを選択できるオプションがあります：

![Launch Profiles](/assets/develop/getting-started/launch-profiles.png)

## Gradle タスク {#gradle-tasks}

コマンドラインを使用している場合は、次の Gradle コマンドを使用してゲームを開始できます：

- `./gradlew runClient` - クライアントモードでゲームを起動。
- `./gradlew runServer` - サーバーモードでゲームを起動。

この方法の唯一の問題は、コードを簡単にデバッグできないことです。 この方法の唯一の問題は、コードを簡単にデバッグできないことです。 コードをデバッグしたい場合は、IntelliJ IDEA の起動プロファイルを使用するか、IDE の Gradle 統合を介して起動プロファイルを使用する必要があります。

## クラスのホットスワップ {#hotswapping-classes}

ゲームをデバッグモードで実行すると、ゲームを再起動せずにクラスをホットスワップできます。 これは、コードの変更をすばやくテストするのに役立ちます。 これは、コードの変更をすばやくテストするのに役立ちます。

ただし、まだかなり制限があります：

- メソッドを追加または削除することはできません
- メソッドのパラメーターを変更することはできません
- フィールドを追加または削除することはできません

ただし、 [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime) を使用すると、ほとんどの制限を回避でき、クラスやメソッドを追加または削除することもできます。 これにより、ゲームを再起動せずにほとんどの変更を行えます。 これにより、ゲームを再起動せずにほとんどの変更を行えます。

Minecraft 実行構成の VM 引数に以下を追加することを忘れないでください：

```:no-line-numbers
-XX:+AllowEnhancedClassRedefinition
```

## Mixin のホットスワップ {#hotswapping-mixins}

Mixin を使用している場合、ゲームを再起動せずに Mixin をホットスワップできます。 これは、Mixin の変更をすばやくテストするのに役立ちます。 これは、Mixin の変更をすばやくテストするのに役立ちます。

ただし、これをするには、Mixin Java エージェントをインストールする必要があります。

### 1. Mixin ライブラリの Jar ファイルを見つける {#1-locate-the-mixin-library-jar}

IntelliJ IDEA では、プロジェクトセクションの外部ライブラリセクションで Mixin ライブラリの Jar ファイルを見つけることができます：

![Mixin Library](/assets/develop/getting-started/mixin-library.png)

次のステップでは、jar の絶対パスをコピーする必要があります。

### 2. `-javaagent` を VM 引数に追加 {#2-add-the--javaagent-vm-argument}

Minecraft Client または Minecraft Server の実行構成で、VM 引数オプションに以下を追加します：

```:no-line-numbers
-javaagent:"ここに Mixin ライブラリの Jar ファイルのパスを入力"
```

![VM Arguments Screenshot](/assets/develop/getting-started/vm-arguments.png)

これで、デバッグ中はゲームの再起動なしに Mixin メソッドの変更を加えられるようになりました。
