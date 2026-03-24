---
title: Fabric API DSL
description: Dokumentation für die Fabric API Unterstützung beim Fabric Loom Gradle Plugin.
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

Loom verfügt über eine DSL, die bei der Konfiguration bestimmter Aspekte der Fabric-API hilft, z. B. bei der Datengenerierung und bei Tests.

## Datengenerator {#data-gen}

Einen Schritt-für-Schritt-Leitfaden zur Vernwendung der Datengenerierung findest du auf der Seite [Einrichtung der Datengenerierung](../data-generation/setup). Die einfachste Art der Datengenerierung kann mit dem folgenden Code konfiguriert werden:

```groovy
fabricApi {
 configureDataGeneration()
}
```

Dadurch wird eine neue Laufkonfiguration erstellt, die die Fabric API mit aktivierter Datengenerierung ausführt. Eine Reihe von fortgeschritteneren Optionen kann wie unten gezeigt konfiguriert werden:

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

## Tests {#tests}

Wie bei der Datengenerierung kannst du mit den folgenden Methoden grundlegende Tests einrichten:

```groovy
fabricApi {
 configureTests()
}
```

Dadurch werden zwei neue Laufkonfigurationen erstellt, eine für die serverseitigen Spieltests und eine für die clientseitigen Spieltests. Eine Reihe von fortgeschritteneren Optionen kann wie unten gezeigt konfiguriert werden:

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
