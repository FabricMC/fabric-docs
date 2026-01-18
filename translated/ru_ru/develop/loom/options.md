---
title: Настройки Loom
description: Референс на все настройки плагина Fabric Loom Gradle.
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

Эта страница содержит референсы на все настройки, которые находятся в 'loom', который находится в расширении Gradle. Параметры, связанные с особенностями Fabric API, см. на странице [Fabric API DSL](./fabric-api).

```groovy
loom {
 // Set the access widener path, see https://wiki.fabricmc.net/tutorial:accesswidening
 accessWidenerPath = file("src/main/resources/example-mod.accesswidener")

 // Add additional log4j config files.
 log4jConfigs.from(file("log4j.xml"))

 // When enabled the output archives will be automatically remapped.
 remapArchives = true
 // When enabled the -dev jars in the *Elements configurations will be replaced by the remapped jars
 setupRemappedVariants = true
 // When enabled transitive access wideners will be applied from dependencies.
 enableTransitiveAccessWideners = true
 // When enabled log4j will only be on the runtime classpath, forcing the use of SLF4j.
 runtimeOnlyLog4j = false

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
  // Configure a default decompiler, either cfr, fernflower or vineflower
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

remapJar {
 // Set the input jar for the task, also valid for remapSourcesJar
 inputFile = file("example.jar")
 // Set the source namespace, also valid for remapSourcesJar
 sourceNamespace = "named"
 // Set the target namespace, also valid for remapSourcesJar
 targetNamespace = "intermediary"
 // Add additional jar files to the remap classpath, also valid for remapSourcesJar
 classpath.from file("classpath.jar")

 // Add a nested mod jar to this task, the include configuration should be used for maven libraries and mods.
 nestedJars.from file("nested.jar")
 // When enabled nested jars will be included with the output jar.
 addNestedDependencies = true
}

dependencies {
 // Set the Minecraft version.
 minecraft "com.mojang:minecraft:1.18.1"

 // Use mappings from maven.
 mappings "net.fabricmc:yarn:1.18.1+build.22:v2"

 // Use the official Mojang mappings
 mappings loom.officialMojangMappings()

 // Layered mappings using official Mojang mappings and Parchment.
 mappings loom.layered() {
  officialMojangMappings()
  // Use Parchment mappings. NOTE: Parchment maven must be manually added. (https://maven.parchmentmc.org)
  parchment("org.parchmentmc.data:parchment-1.17.1:2021.09.05@zip")
 }

 // Remap a mod from maven and apply to Gradle's implementation configuration
 // (Minor detail: it's not exactly applied *to* the configuration, but a clone of it intended for mod dependencies)
 modImplementation "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Remap a mod from maven and apply to Gradle's api configuration
 modApi "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Remap a mod from maven and apply to Gradle's compileOnly configuration
 modCompileOnly "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Remap a mod from maven and apply to Gradle's compileOnlyApi configuration
 modCompileOnlyApi "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Remap a mod from maven and apply to Gradle's runtimeOnly configuration
 modRuntimeOnly "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Remap a mod from maven and apply to loom's localRuntime configuration.
 // Behaves like runtimeOnly but is not exposed in to dependents. A bit like testRuntimeOnly but for mods.
 modLocalRuntime "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

 // Include a mod jar in the remapped jar. Not transitive.
 include "example:example-mod:1.1.1"

 // Include a non-mod library jar in the remapped jar. A dummy mod will be generated. Not transitive.
 include "example:example-lib:1.1.1"

 // Helper to aid with depending on a specific Fabric API version.
 modImplementation fabricApi.module("fabric-api-base", "0.46.2+1.18")

 // Depend on a loom subproject by using the namedElements configuration.
 implementation project(path: ":name", configuration: "namedElements")
}
```
