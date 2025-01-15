---
title: Fabric と Modding への入門
description: "Fabric と Minecraft: Java 版における Modding への入門"
authors:
  - IMB11
  - itsmiir
authors-nogithub:
  - basil4088
---

# Fabric と Modding への入門 {#introduction-to-fabric-and-modding}

## 前提条件 {#prerequisites}

Fabric と Minecraft の Modding を始めるには、Java の基礎文法と、オブジェクト指向プログラミング（OOP）の理解が必要です。

これらの知識がない方は、Java と OOP に関する以下のチュートリアルをご覧いただけます。

- [W3: Java Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Introduction to OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

### 用語集 {#terminology}

始める前に、Fabric における Modding で使われる用語について説明します:

- **Mod**: 新しい機能の追加や既存の機能の変更といった、ゲームに加える変更。
- **Mod ローダー**: Fabric Loader のような、Mod をゲームに読み込ませるツール。
- **Mixin**: 実行時にゲームのプログラムを変更するツール。詳細は [Mixin Introduction](https://fabricmc.net/wiki/tutorial:mixin_introduction) を参照してください。
- **Gradle**: Mod のビルドとコンパイルを自動化するツール。Fabric では Mod のビルドに使用している。
- **マッピング（Mappings）**: 難読化されたプログラムと人間が読めるプログラムの間における対応。
- **難読化（Obfuscation）**: プログラムを読みづらくする処理。Mojang が Minecraft のプログラムを守るために行う。
- **再マッピング**: 難読化されたプログラムを人間が読めるプログラムに変更する処理。

## Fabric とは {#what-is-fabric}

Fabric は Minecraft: Java 版向けの軽量な Modding ツールです。

Fabric はシンプルで扱いやすい Modding プラットフォームを目指して設計されています。 開発はコミュニティによって行われており、オープンソースです。つまり、誰でもプロジェクトに貢献することができます。

Fabric には 4 つの主要なコンポーネントがあります:

- **Fabric Loader**: Minecraft やその他のゲームやアプリケーション向けに設計された、柔軟でプラットフォームに依存しない Mod ローダー。
- **Fabric Loom**: Mod の開発とデバッグを簡単にする Gradle プラグイン。
- **Fabric API**: Modding をする際に使用する API とツールのセット。
- **Yarn**: オープンな Minecraft のマッピング。Creative Commons Zero ライセンスのもと、誰でも自由に使うことができる。

## Fabric が Minecraft の Modding に必要である理由 {#why-is-fabric-necessary-to-mod-minecraft}

> Modding とは、挙動を変更したり、新しい機能を追加したりするためにゲームを改造することです。Minecraft の場合、新しいアイテム、ブロック、エンティティの追加から、ゲームの仕様の変更、新しいゲームモードの追加まで、あらゆることが含まれます。

Minecraft: Java 版は Mojang によって難読化されており、単独での Modding は困難です。 しかし、Fabric のような Modding ツールを使うことで、簡単に変更を加えることができます。 ツールの中には、Modding を支援するいくつかのマッピングシステムが存在します。

Loom はマッピングを用いて、難読化されたプログラムを人間が読める形式に再マッピングします。これにより、Mod 開発者が簡単にゲームのプログラムを理解し、変更を加えることができるようになります。 Yarn は人気があり、Modding に非常に優れたマッピングです。しかし、別のマッピングも存在します。 マッピングにはそれぞれ独自の強みや目的があります。

Loom は再マッピングされたプログラムにおける開発とコンパイルを容易にします。また、Fabric Loader はこれらの Mod をゲームに読み込ませることを可能にします。

## Fabric API が提供するものと、必要である理由 {#what-does-fabric-api-provide-and-why-is-it-needed}

> Fabric API は Modding をする際に使用する API とツールのセットです。

Fabric API は Minecraft の既存の機能に対する様々な API を提供します。例えば、新しいフックやイベントを提供したり、Modding を簡単にするユーティリティやツール（推移的アクセスワイドナーや、内部レジストリへのアクセス機能など）を提供したりします。

Fabric API は強力な機能を提供しますが、ブロックの登録を含む一部の基本的なタスクはバニラの API を通じて行うことができます。
