---
title: Fabric API DSL
description: Документация по поддержке Fabric API в плагине Fabric Loom Gradle.
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

Loom имеет DSL, который помогает в конфигурации некоторых аспектов Fabric API, например как генерация данных и тесты.

## Генерация данных {#data-gen}

Пошаговое руководство по использованию генерации данных см. на странице [Настройка генерации данных](.../data-generation/setup). Большинство самой базовой настройки data-gen (ген. данных) можно выполнить следующим кодом:

```groovy
fabricApi {
 configureDataGeneration()
}
```

Это создаст новую конфигурацию запуска, которая запустит Fabric API с включенной генерацией данных. Диапазон расширенных настроек может быть конфигурирован, как показано ниже:

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

## Тесты {#tests}

Как с ген. данных, вы можете настроить базовые тесты следующим кодом:

```groovy
fabricApi {
 configureTests()
}
```

Это создаст уже 2 новые конфигурации запуска, один для серверной части, другой для клиента игры. Диапазон расширенных настроек может быть конфигурирован, как показано ниже:

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
