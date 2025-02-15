---
title: Loom
description: Documentation for the Fabric Loom Gradle plugin.
authors:
  - modmuss50
  - kb-1000
  - Juuxel
  - Atakku
  - SolidBlock-cn
authors-nogithub:
  - jamieswhiteshirt
  - daomephsta
  - caoimhe
---

Fabric Loom, or just Loom for short, is a [Gradle](https://gradle.org/) plugin for development of mods in the Fabric ecosystem.

Loom provides utilities to install Minecraft and mods in a development environment so that you can link against them with respect to Minecraft obfuscation and its differences between distributions and versions. It also provides run configurations for use with Fabric Loader, Mixin compile processing and utilities for Fabric Loader's jar-in-jar system.

Loom supports _all_ versions of Minecraft, even those not officially supported by Fabric API, because it is version-independent.

This page is a reference of all options and features of Loom. If you are just getting started, please see the [Getting Started](getting-started/setting-up-a-development-environment) page.

## Depending on Subprojects {#subprojects}

While setting up a multi-project build that depends on another Loom project, you should use the `namedElements` configuration when depending on the other project. By default a project's "outputs" are remapped to intermediary names. The `namedElements` configuration contains the project outputs that have not been remapped.

```groovy
dependencies {
 implementation project(path: ":name", configuration: "namedElements")
}
```

If you are using split source sets in a multi-project build, you will also need to add a dependency for the other project's client source set.

```groovy
dependencies {
 clientImplementation project(":name").sourceSets.client.output
}
```

## Split Client & Common Code {#split-sources}

For years a common source of server crashes had been mods accidentally calling client-only code when installed on a server. Newer Loom and Loader versions provide an option to require all client code to be moved into its own source set. This is to stop the problem at compile time, but the build will still result in a single jar file which works on either side.

The following snippet from a `build.gradle` file shows how you can enable this for your mod. As your mod will now be split across two source sets, you will need to use the new DSL to define your mod's source sets. This enables Fabric Loader to group your mod's classpath together. This is also useful for some other complex multi-project setups.

Minecraft 1.18 (1.19 recommended), Loader 0.14 and Loom 1.0 or later are required to split the client and common code.

```groovy
loom {
 splitEnvironmentSourceSets()

 mods {
   modid {
     sourceSet sourceSets.main
     sourceSet sourceSets.client
   }
 }
 }
```

## Resolving Issues {#issues}

Loom and/or Gradle can sometimes fail due to corrupted cache files. Running `./gradlew build --refresh-dependencies` will force Gradle and Loom to re-download and recreate all files. This may take a few minutes but is usually enough to resolve cache-related issues.

## Development Environment Setup {#setup}

Loom is designed to work out-of-the-box, by simply setting up a workspace in your IDE. It does quite a few things behind the scenes to create a development environment with Minecraft:

- Downloads the client and server jar from official channels for the configured version of Minecraft
- Merges the client and server jar to produce a merged jar with `@Environment` and `@EnvironmentInterface` annotations
- Downloads the configured mappings
- Remaps the merged jar with intermediary mappings to produce an intermediary jar
- Remaps the merged jar with Yarn mappings to produce a mapped jar
- Optional: Decompiles the mapped jar to produce a mapped sources jar and linemap, and applies the linemap to the mapped jar
- Adds Minecraft dependencies
- Downloads Minecraft assets
- Processes and includes mod-augmented dependencies

## Caches {#caches}

- `${GRADLE_HOME}/caches/fabric-loom`: The user cache, a cache shared by all Loom projects for a user. Used to cache Minecraft assets, jars, merged jars, intermediary jars and mapped jars
- `.gradle/loom-cache`: The root project persistent cache, a cache shared by a project and its subprojects. Used to cache remapped mods, as well as generated included mod jars
- `**/build/loom-cache`: The (sub)projects' build cache

## Dependency Configurations {#configurations}

- `minecraft`: Defines the version of Minecraft to be used in the development environment
- `mappings`: Defines the mappings to be used in the development environment
- `modImplementation`, `modApi` and `modRuntime`: Augmented variants of `implementation`, `api` and `runtime` for mod dependencies. Will be remapped to match the mappings in the development environment and has any nested jars removed
- `include`: Declares a dependency that should be included as a jar-in-jar in the `remapJar` output. This dependency configuration is not transitive. For non-mod dependencies, Loom will generate a mod jar with a `fabric.mod.json` using the mod ID for the name, and the same version

## Default Configuration {#configuration}

- Applies the following plugins: `java`, `eclipse`
- Adds the following Maven repositories: [Fabric](https://maven.fabricmc.net/), [Mojang](https://libraries.minecraft.net/) and Maven Central
- Configures the `eclipse` task to be finalized by the `genEclipseRuns` task
- If an `.idea` folder exists in the root project, downloads assets (if not up-to-date) and installs run configurations in `.idea/runConfigurations`
- Adds `net.fabricmc:fabric-mixin-compile-extensions` and its dependencies with the `annotationProcessor` dependency configuration
- Configures all non-test JavaCompile tasks with configurations for the Mixin annotation processor
- Configures the `remapJar` task to output a JAR with the same name as the `jar` task output, then adds a "dev" classifier to the `jar` task
- Configures the `remapSourcesJar` task to process the `sourcesJar` task output if the task exists
- Adds the `remapJar` task and the `remapSourcesJar` task as dependencies of the `build` task
- Configures the `remapJar` task and the `remapSourcesJar` task to add their outputs as `archives` artifacts when executed
- For each MavenPublication (from the `maven-publish` plugin), manually appends dependencies to the POM for mod-augmented dependency configurations, provided the dependency configuration has a Maven scope

All run configurations have the run directory `${projectDir}/run` and the VM argument `-Dfabric.development=true`. The main classes for run configurations are usually defined by a `fabric-installer.json` file in the root of Fabric Loader's JAR file when it is included as a mod dependency, but the file can be defined by any mod dependency. If no such file is found, the main classes default to `net.fabricmc.loader.launch.knot.KnotClient` and `net.fabricmc.loader.launch.knot.KnotServer`.
