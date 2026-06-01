---
title: Loom Options
description: Reference for all options of the Fabric Loom Gradle plugin.
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

This page contains a reference for all options present in the `loom` Gradle extension. Please see the [Fabric API DSL](./fabric-api) page for options related to Fabric API specific features.

```groovy
loom {
 // Set the class tweaker file path, see https://docs.fabricmc.net/develop/class-tweakers/
 accessWidenerPath = file("src/main/resources/example-mod.classtweaker")

 // Add additional log4j config files.
 log4jConfigs.from(file("log4j.xml"))

 // When set only server related features and jars will be setup.
 serverOnlyMinecraftJar()
 // When set the minecraft jar will be split into common and clientonly. Highly experimental, fabric-loader does not support this option yet.
 splitMinecraftJar()

 // Used to configure existing or new run configurations
 runs {
  client {
   // Add a VM arg
   vmArgs "-Dexample=true"
   // Add a JVM property
   property("example", "true")
   // Add a program arg
   programArg "--example"
   // Add an environment variable
   environmentVariable("example", "true")
   // The environment (or side) to run, usually client or server.
   environment = "client"
   // The full name of the run configuration, i.e. 'Minecraft Client'. By default this is determined from the base name.
   configName = "Minecraft Client"
   // The default main class of the run configuration. This will be overridden if using a mod loader with a fabric_installer.json file.
   defaultMainClass = ""
   // The run directory for this configuration, relative to the root project directory.
   runDir = "run"
   // The source set to run, commonly set to sourceSets.test
   source = sourceSets.main
   // When true a run configuration file will be generated for IDE's. By default only set to true for the root project.
   ideConfigGenerated = true

   // Configure run config with the default client options.
   client()

   // Configure run config with the default server options.
   server()
  }

  // Example of creating a basic run config for tests
  testClient {
   // Copies settings from another run configuration.
   inherit client

   configName = "Test Minecraft Client"
   source = sourceSets.test
  }

    // Example of removing the built-in server configuration
    remove server
 }

 // Configure all run configs to generate ide run configurations. Useful for sub projects.
 runConfigs.configureEach {
  ideConfigGenerated = true
 }

 // Used to configure mixin options or apply to additional source sets.
 mixin {
  // When disabled tiny remapper will be used to remap Mixins instead of the AP. (Disabled by default in Loom 1.12+)
  useLegacyMixinAp = true
  // Set the default refmap name
  defaultRefmapName = "example.refmap.json"

  // See https://github.com/FabricMC/fabric-loom/blob/dev/0.11/src/main/java/net/fabricmc/loom/api/MixinExtensionAPI.java for options to add additional source sets
 }

 // Configure or add new decompilers
 decompilers {
  // Configure a default decompiler, either cfr or vineflower
  cfr {
   // Pass additional options to the decompiler
   options += [
    key: "value"
   ]
   // Set the amount of memory in megabytes used when forking the JVM
   memory = 4096
   // Set the maximum number of threads that the decompiler can use.
   maxThreads = 8
  }
 }

 interfaceInjection {
  // When enabled injected interfaces from dependencies will be applied.
  enableDependencyInterfaceInjection = true
 }

 // Splits the Minecraft jar and incoming dependencies across the main (common) and client only source sets.
 // This provides compile time safety for accessing client only code.
 splitEnvironmentSourceSets()

 // This mods block is used group mods that are made up of multiple classpath entries.
 mods {
  example-mod {
   // When using split sources you should add the main and client source set
   sourceSet sourceSets.main
   sourceSet sourceSets.client
  }
 }

 // Create modExampleImplementation and related configurations that remap mods.
 createRemapConfigurations(sourceSets.example)

 // Specifies the fabric.mod.json file location used in injected interface processing.
 // Defaults to src/main/resources/fabric.mod.json or src/client/resources/fabric.mod.json
 fabricModJsonPath = file("src/custom/resources/fabric.mod.json")
}

dependencies {
 // Set the Minecraft version.
 minecraft "com.mojang:minecraft:26.1.2"

 // Apply a mod to Gradle's implementation configuration
 implementation "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Apply a mod to Gradle's api configuration
 api "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Apply a mod to Gradle's compileOnly configuration
 compileOnly "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Apply a mod to Gradle's compileOnlyApi configuration
 compileOnlyApi "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Apply a mod to Gradle's runtimeOnly configuration
 modRuntimeOnly "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Apply a mod to loom's localRuntime configuration.
 // Behaves like runtimeOnly but is not exposed in to dependents. A bit like testRuntimeOnly but for mods.
 localRuntime "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Include a mod jar in the jar. Not transitive.
 include "example:example-mod:1.1.1"

 // Include a non-mod library jar in the jar. A dummy mod will be generated. Not transitive.
 include "example:example-lib:1.1.1"

 // Helper to aid with depending on a specific Fabric API version.
 implementation fabricApi.module("fabric-api-base", "0.46.2+1.18")

 // Depend on a loom subproject FIXME HOW DOES THIS WORK ON 26.1
}
```
