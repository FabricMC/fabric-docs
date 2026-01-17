---
title: DSL dell'API di Fabric
description: Documentazione per il supporto dell'API di Fabric in Loom, il plugin Gradle di Fabric.
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

Loom ha un DSL (Linguaggio Specifico del Dominio) che permette di configurare certi aspetti dell'API di Fabric come la generazione di dati e i test.

## Generazione di Dati {#data-gen}

Per una guida passo passo su come usare la generazione dei dati, guarda la pagina [Data Generation Setup](../data-generation/setup). La configurazione di generazione di dati più basilare si può configurare con il codice seguente:

```groovy
fabricApi {
 configureDataGeneration()
}
```

Questo creerà una nuova configurazione d'avvio che esegue l'API di Fabric attivando la generazione di dati. Una serie di opzioni più avanzate si può configurare come visto qua sotto:

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

## Test {#tests}

Come per la generazione di dati, puoi configurare test basilari con ciò che segue:

```groovy
fabricApi {
 configureTests()
}
```

Questo creerà due nuove configurazioni d'avvio, una per i test nel gioco lato server e una per quelli lato client. Una serie di opzioni più avanzate si può configurare come visto qua sotto:

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
