---
title: Fabric API DSL
description: Gradle용 Fabric Loom 플러그인에서 지원하는 Fabric API 지원에 관한 문서입니다.
authors:
  - Atakku
  - caoimhe
  - daomephsta
  - jamieswhiteshirt
  - Juuxel
  - kb-1000
  - modmuss50
  - SolidBlock-cn
---

Loom은 데이터 생성과 테스트 등 Fabric API의 일부 기능의 구성을 돕는 DSL을 제공하고 있습니다.

## 데이터 생성 {#data-gen}

데이터 생성 방법에 대한 단계적 설명서는, [데이터 생성 시작하기](data-generation/setup)를 참조해야 합니다. 가장 기본적인 데이터 생성 구성은 다음 코드로 할 수 있습니다:

```groovy
fabricApi {
 configureDataGeneration()
}
```

이렇게 하면 데이터 생성이 활성화된 Fabric API를 사용하는 새로운 실행 구성이 추가됩니다. 다음 코드를 활용하면 더 심층적으로 구성할 수 있습니다:

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

## 테스트 {#tests}

데이터 생성처럼, 기본적인 테스트 구성은 다음 코드로 할 수 있습니다:

```groovy
fabricApi {
 configureTests()
}
```

이렇게 하면 서버 측 게임 테스트와 클라이언트 측 게임 테스트, 총 2가지 새로운 실행 구성이 생성됩니다. 다음 코드를 활용하면 더 심층적으로 구성할 수 있습니다:

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
