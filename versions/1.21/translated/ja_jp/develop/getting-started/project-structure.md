---
title: プロジェクトの構成
description: Fabric Mod プロジェクトの構成の概要
authors:
  - IMB11
---

# プロジェクトの構成 {#project-structure}

このページでは、Fabric Mod プロジェクトの構成と、各ファイルやフォルダの目的について説明します。

## `fabric.mod.json` {#fabric-mod-json}

`fabric.mod.json` ファイルは Mod の情報を Fabric Loader に伝えます。 ファイルには Mod の ID、バージョン、依存関係などの情報が書かれます。

`fabric.mod.json` 内の最も重要なフィールドは次の通りです:

- `id`: Mod の ID。一意の値である必要がある。
- `name`: Mod の名前。
- `environment`: Mod が動く環境。`client`、`server`、`*`（両方）のいずれか。
- `entrypoints`: Mod が提供するエントリポイント。`main` や `client` など。
- `depends`: Mod が依存する Mod の ID のリスト。
- `mixins`: Mod が提供する Mixin 構成ファイルのリスト。

以下に `fabric.mod.json` ファイルの例を示します。これは、このドキュメント上のサンプルプロジェクトで使われる `fabric.mod.json` ファイルです。

:::details サンプルプロジェクト `fabric.mod.json`
@[code lang=json](@/reference/latest/src/main/resources/fabric.mod.json)
:::

## エントリポイント {#entrypoints}

上述のとおり、`fabric.mod.json` ファイルは `entrypoints` というフィールドを持ちます。このフィールドは、Mod が提供するエントリポイントを示します。

テンプレート Mod ジェネレータはデフォルトで `main` と `client` のエントリポイントを作成します。`main` エントリポイントは共通するコードに使用され、`client` エントリポイントはクライアントでのみ実行されるコードに使用されます。 これらのエントリポイントは、ゲーム開始時にそれぞれ呼び出されます。

@[code lang=java transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

上記のコードは、ゲーム開始時にコンソールにログを出力する、シンプルな `main` エントリポイントの例です。

## `src/main/resources` {#src-main-resources}

`src/main/resources` フォルダは、 Mod が使用するリソースを格納するために使用されます。リソースは、テクスチャ、モデル、サウンドなどのことです。

また、`fabric.mod.json` ファイルや Mod が使用する Mixin 構成ファイルが配置される場所でもあります。

リソースは、リソースパックの構造に従って格納されます。例えば、ブロックのテクスチャは `assets/modid/textures/block/block.png` に格納されます。

## `src/client/resources` {#src-client-resources}

`src/client/resources` フォルダは、クライアント固有のリソースを格納するために使用されます。

## `src/main/java` {#src-main-java}

`src/main/java` フォルダは、クライアントとサーバの両方の環境で共通する Java コードを格納するために使用されます。

## `src/client/java` {#src-client-java}

`src/client/java` フォルダは、クライアント固有の Java コードを格納するために使用されます。クライアント固有のコードとは、描画処理を行うコードや、クライアントサイドにのみ存在するロジックのコード（ブロックカラープロバイダなど）を指します。
