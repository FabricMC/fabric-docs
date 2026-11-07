---
title: Fabric API DSL
description: Fabric Loom Gradle 插件中 Fabric API 支持的文档。
authors:
  - Atakku
  - caoimhebyrne
  - Daomephsta
  - JamiesWhiteShirt
  - Juuxel
  - kb-1000
  - modmuss50
  - SolidBlock-cn
---

Loom 有一个 DSL 来帮助配置 Fabric API 的某些方面，例如数据生成和测试。

## 数据生成 {#data-gen}

有关如何使用数据生成的分步指南，请参阅[数据生成设置](../data-generation/setup)页面。 最基本的数据生成设置可以用以下代码来配置：

```groovy
fabricApi {
 configureDataGeneration()
}
```

这将创建一个新的运行配置，运行启用了数据生成的 Fabric API。 可以配置一系列更高级的选项，如下所示：

```groovy
fabricApi {
 configureDataGeneration {
  // Contains the output directory where generated data files will be stored.
  // Defaults to `src/main/generated`
  outputDirectory = file("src/generated/resources")

  // Contains a boolean indicating whether a run configuration should be created for the data generation process.
  // Defaults to `true`
  createRunConfiguration = true

  // Contains a boolean indicating whether a new source set should be created for the data generation process.
  // This is useful if you do not want your datagen code to be exported in your mod jar.
  // Defaults to `false`
  createSourceSet = true

  // Contains a string representing the mod ID associated with the data generation process. This must be set if `createSourceSet` is true.
  // This must be the mod id of the mod used for datagen in the datagen source set and not your main mod id.
  modId = "example-datagen"

  // Contains a boolean indicating whether strict validation is enabled.
  // Defaults to `false`
  strictValidation = true

  // Contains a boolean indicating whether the generated resources will be automatically added to the main source set.
  // Defaults to `true`
  addToResources = true

  // Contains a boolean indicating whether data generation will be compiled and run with the client.
  // Defaults to `false`
  client = true
 }
}
```

## 测试 {#tests}

与数据生成一样，你可以使用以下命令设置基本测试：

```groovy
fabricApi {
 configureTests()
}
```

这将创建 2 个新的运行配置，一个用于服务器端游戏测试，一个用于客户端游戏测试。 可以配置一系列更高级的选项，如下所示：

```groovy
fabricApi {
 configureTests {
  // Contains a boolean indicating whether a new source set should be created for the tests.
  // Defaults to `false`
  createSourceSet = true

  // Contains a string representing the mod ID associated with the tests. This must be set if `createSourceSet` is true.
  // This must be the mod id of the mod used for tests in the gametest source set and not your main mod id.
  modId = "example-tests"

  // Contains a boolean indicating whether a run configuration will be created for the server side game tests, using Vanilla Game Test framework.
  // Defaults to `true`
  enableGameTests = true

  // Contains a boolean indicating whether a run configuration will be created for the client side game tests, using the Fabric API Client Test framework.
  // Defaults to `true`
  enableClientGameTests = true

  // Contains a boolean indicating whether the eula has been accepted. By enabling this you agree to the Minecraft EULA located at https://aka.ms/MinecraftEULA.
  // Defaults to `false`
  eula = true

  // Contains a boolean indicating whether the run directories should be cleared before running the tests.
  // This only works when `enableClientGameTests` is `true`.
  // Defaults to `true`
  clearRunDirectory = true

  // Contains a string representing the username to use for the client side game tests.
  // Defaults to `Player0`
  username = "Username"
 }
}
```
