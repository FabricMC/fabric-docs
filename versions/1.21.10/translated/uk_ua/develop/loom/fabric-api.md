---
title: Fabric API DSL
description: Документація щодо підтримки API Fabric у плаґіні Gradle Fabric Loom.
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

Loom має DSL, щоб допомогти налаштувати певні аспекти Fabric API, такі як генерація даних і тестування.

## Генерація даних {#data-gen}

Щоб отримати покрокові інструкції щодо використання генерації даних, перегляньте сторінку [налаштування генерації даних](../data-generation/setup). Основні налаштування генерації даних можна налаштувати за допомогою наступного коду:

```groovy
fabricApi {
 configureDataGeneration()
}
```

Це створить нові налаштування запуску, які запускає Fabric API з увімкненою генерацією даних. Нижче можна налаштувати низку додаткових параметрів:

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

## Тестування {#tests}

Як і у випадку з генерацією даних, ви можете налаштувати звичайне тестування за допомогою наступного:

```groovy
fabricApi {
 configureTests()
}
```

Це створить 2 нових налаштування запуску, одну для тестів гри на стороні сервера та одну для тестів гри на стороні клієнта. Нижче можна налаштувати низку додаткових параметрів:

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
