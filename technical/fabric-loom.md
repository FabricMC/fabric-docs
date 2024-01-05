---
title: Fabric Loom
description: Technical information about the Fabric Loom Gradle plugin.
---

# Fabric Loom

Fabric Loom, or just Loom for short, is a [Gradle](https://gradle.org/)
plugin for development of mods in the Fabric ecosystem. Loom provides
utilities to install Minecraft and mods in a development environment so
that you can link against them with respect to Minecraft obfuscation and
its differences between distributions and versions. It also provides run
configurations for use with Fabric Loader, Mixin compile processing and
utilities for Fabric Loader's jar-in-jar system.

### Useful tasks

<!-- Todo, rewrite migrate mappings guide in the "develop" section? -->

-   `migrateMappings`: Migrates the current source to the specified
    mappings.
-   `remapJar`: Produces a jar containing the remapped output of the
    `jar` task. Also appends any included mods for jar-in-jar. Called
    when running `build`.
-   `genSources`: Decompile the minecraft jar using the default
    decompiler (CFR).
-   `downloadAssets`: Downloads the asset index and asset objects for
    the configured version of Minecraft into the user cache.
-   `genEclipseRuns`: Installs Eclipse run configurations and creates
    the run directory if it does not already exist.
-   `vscode`: Generates or overwrites a Visual Studio Code `launch.json`
    file with launch configurations in the `.vscode` directory and
    creates the run directory if it does not already exist.
-   `ideaSyncTask`: Generates (but not overrides) the Intellij IDEA
    launch config, including client and server by default.
-   `remapSourcesJar`: Only exists if an AbstractArchiveTask
    `sourcesJar` exists. Remaps the output of the `sourcesJar` task in
    place.
-   `runClient`: A JavaExec task to launch Fabric Loader as a Minecraft
    client.
-   `runServer`: A JavaExec task to launch Fabric Loader as a Minecraft
    dedicated server.

### Depending on Sub Projects

When setting up a multi-project build that depenends on another loom
project you should use the `namedElements` configuration when depending
on the other project. By default a projects "outputs" are remapped to
intermediary names. The `namedElements` configuration contains the
project ouputs that have not been remapped.

```groovy
dependencies {
  implementation project(path: ":name", configuration: "namedElements")
}
```

If you are using splitsource sets in a multi-project build, you will
also need to add a dependency for the other projects client sourceset.

```groovy
dependencies {
  clientImplementation project(":name").sourceSets.client.output
}
```

### Split Client and Common code

For years a common source of server crashes has been from mods
accidentally calling client only code when installed on a server. The
latest loom and loader verions provide an option to require all client
code to be moved into its own sourceset. This is done to provide a
compile-time guarantee against calling client only Minecraft code or
client only API on the server. A single jar file that works on both the
client and server is still built from the two sourcesets.

The following snippet from a build.gradle file shows how you can enable
this for your mod. As your mod will now be split across two sourcesets,
you will need to use the new DSL to define your mods sourcesets. This
enables Fabric Loader to group your mods classpath together. This is
also useful for some other complex multi-project setups.

Minecraft 1.18 (1.19 recommended), Loader 0.14 and Loom 1.0 or later are
required to split the client and common code.

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

### Multi project Optimisation

If your Gradle project has many subprojects that use the same Minecraft
version such as Fabric-API, starting with Loom 1.1 you can now opt-in to
advanced optimistations. Adding `fabric.loom.multiProjectOptimisation=true` to the gradle.properties file will help decrease build time and memory
usage by sharing the Tiny Remapper instance between projects when
remapping your output jars.

### Options

```groovy
loom {
  // Set the access widener path, see https://fabricmc.net/wiki/tutorial:accesswideners
  accessWidenerPath = file("src/main/resources/modid.accesswidener")

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
      // The sourceset to run, commonly set to sourceSets.test
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
    // When disabled tiny remapper will be used to remap Mixins instead of the AP. Experimental.
    useLegacyMixinAp = true
    // Set the default refmap name
    defaultRefmapName = "example.refmap.json"

    // See https://github.com/FabricMC/fabric-loom/blob/dev/0.11/src/main/java/net/fabricmc/loom/api/MixinExtensionAPI.java for options to add additional sourcesets
  }

  // Configure or add new decompilers
  decompilers {
    // Configure a default decompiler, either cfr or fernflower
    cfr {
      // Pass additional options to the decompiler
      options += [
        key: "value"
      ]
      // Set the amount of memory in meagabytes used when forking the JVM
      memory = 4096
      // Set the maximum number of threads that the decompiler can use.
      maxThreads = 8
    }
  }

  interfaceInjection {
    // When enabled injected interfaces from dependecies will be applied.
    enableDependencyInterfaceInjection = true
  }

  // Splits the Minecraft jar and incoming dependencies across the main (common) and client only sourcesets.
  // This provides compile time safety for accessing client only code.
  splitEnvironmentSourceSets()

  // This mods block is used group mods that are made up of multiplue classpath entries.
  mods {
    modid {
      // When using split sources you should add the main and client sourceset
      sourceSet sourceSets.main
      sourceSet sourceSets.client
    }
  }

  // Create modExampleImplementation and related configurations that remap mods.
  createRemapConfigurations(sourceSets.example)
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
  // Set the minecraft version.
  minecraft "com.mojang:minecraft:1.18.1"

  // Use mappings from maven.
  mappings "net.fabricmc:yarn:1.18.1+build.22:v2"

  // Use the offical mojang mappings
  mappings loom.officialMojangMappings()

  // Layered mappings using official mojang mappings and parchment.
  mappings loom.layered() {
    officialMojangMappings()
    // Use parchment mappings. NOTE: Parchment maven must be manually added. (https://maven.parchmentmc.org)
    parchment("org.parchmentmc.data:parchment-1.17.1:2021.09.05@zip")
  }

  // Remap a mod from maven and apply to gradle's implementation configuration
  // (Minor detail: it's not exactly applied *to* the configuration, but a clone of it intended for mod dependencies)
  modImplementation "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

  // Remap a mod from maven and apply to gradle's api configuration
  modApi "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

  // Remap a mod from maven and apply to gradle's compileOnly configuration
  modCompileOnly "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

  // Remap a mod from maven and apply to gradle's compileOnlyApi configuration
  modCompileOnlyApi "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

  // Remap a mod from maven and apply to gradle's runtimeOnly configuration
  modRuntimeOnly "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

  // Remap a mod from maven and apply to loom's localRuntime configuration.
  // Behaves like runtimeOnly but is not exposed in to dependents. A bit like testRuntimeOnly but for mods.
  modLocalRuntime "net.fabricmc.fabric-api:fabric-api:0.46.2+1.18"

  // Include a mod jar in the remapped jar. None transitive.
  include "example:example-mod:1.1.1"

  // Include a none mod library jar in the remapped jar. A dummy mod will be generated. None transitive.
  include "example:example-lib:1.1.1"

  // Helper to aid with depending on a specific fabric api version.
  modImplementation fabricApi.module("fabric-api-base", "0.46.2+1.18")

  // Depend on a loom sub project by using the namedElements configuration.
  implementation project(path: ":name", configuration: "namedElements")
}
```

### Resolving issues

Loom and/or gradle can sometimes fail due to corrupted cache files.
Running `./gradlew build --refresh-dependencies` will force gradle and
loom to re-download and recreate all of the files. This may take a few
minutes but is usually quite successful with resolving cache related
issues.

### Development environment setup

Loom is designed to work out of the box by simply setting up a workspace
in the user's IDE of choice. It does quite a few things behind the
scenes to create a development environment with Minecraft:

1.  Downloads the client and server jar from official channels for the
    configured version of Minecraft.
2.  Merges the client and server jar to produce a merged jar with
    `@Environment` and `@EnvironmentInterface` annotations.
3.  Downloads the configured mappings.
4.  Remaps the merged jar with intermediary mappings to produce an
    intermediary jar.
5.  Remaps the merged jar with yarn mappings to produce a mapped jar.
6.  Optional: Decompiles the mapped jar to produce a mapped sources jar
    and linemap, and applies the linemap to the mapped jar.
7.  Adds dependencies of Minecraft.
8.  Downloads Minecraft assets.
9.  Processes and includes mod-augmented dependencies.

### Caches

-   `${GRADLE_HOME}/caches/fabric-loom`: The user cache, a cache shared
    by all Loom projects for a user. Used to cache Minecraft assets,
    jars, merged jars, intermediary jars and mapped jars.
-   `.gradle/loom-cache`: The root project persistent cache, a cache
    shared by a project and its subprojects. Used to cache remapped mods
    as well as generated included mod JARs.
-   `build/loom-cache`: The root project build cache.
-   `**/build/loom-cache`: The (sub)project build cache.

### Dependency configurations

-   `minecraft`: Defines the version of Minecraft to be used in the
    development environment.
-   `mappings`: Defines the mappings to be used in the development
    environment.
-   `modCompile`, `modImplementation`, `modApi` and `modRuntime`:
    Augmented variants of `compile`, `implementation`, `api` and
    `runtime` for mod dependencies. Will be remapped to match the
    mappings in the development environment and has any nested JARs
    removed.
-   `include`: Declares a dependency that should be included as a
    jar-in-jar in the `remapJar` output. This dependency configuration
    is not transitive. For non-mod dependencies, Loom will generate a
    mod JAR with a fabric.mod.json using the name as the mod ID and the
    same version.

### Default configuration

-   Applies the following plugins: `java`, `eclipse` and `idea`.
-   Adds the following Maven repositories: Fabric
    <https://maven.fabricmc.net/>, Mojang
    <https://libraries.minecraft.net/> and Maven Central.
-   Configures the `idea` extension to exclude directories `.gradle`,
    `build`, `.idea` and `out`, to download javadocs sources and to
    inherit output directories.
-   Configures the `idea` task to be finalized by the `genIdeaWorkspace`
    task.
-   Configures the `eclipse` task to be finalized by the
    `genEclipseRuns` task.
-   If an `.idea` folder exists in the root project, downloads assets
    (if not up-to-date) and installs run configurations in
    `.idea/runConfigurations`.
-   Adds `net.fabricmc:fabric-mixin-compile-extensions` and its
    dependencies with the `annotationProcessor` dependency
    configuration.
-   Configures all non-test JavaCompile tasks with configurations for
    the Mixin annotation processor.
-   Configures the `remapJar` task to output a JAR with the same name as
    the `jar` task output, then adds a "dev" classifier to the `jar`
    task.
-   Configures the `remapSourcesJar` task to process the `sourcesJar`
    task output if the task exists.
-   Adds the `remapJar` task and the `remapSourcesJar` task as
    dependencies of the `build` task.
-   Configures the `remapJar` task and the `remapSourcesJar` task to add
    their outputs as `archives` artifacts when executed.
-   For each MavenPublication (from the `maven-publish` plugin):
    -   Manually appends dependencies to the POM for mod-augmented
        dependency configurations, provided the dependency configuration
        has a Maven scope.

All run configurations have the run directory `${projectDir}/run` and
the VM argument `-Dfabric.development=true`. The main classes for run
configurations is usually defined by a `fabric-installer.json` file in
the root of Fabric Loader's JAR file when it is included as a mod
dependency, but the file can be defined by any mod dependency. If no
such file is found, the main classes defaults to
`net.fabricmc.loader.launch.knot.KnotClient` and
`net.fabricmc.loader.launch.knot.KnotServer`.

The client run configuration is configured with `--assetsIndex` and
`--assetsDir` program arguments pointing to the loom cache directory
containing assets and the index file for the configured version of
Minecraft. When running on OSX, the "-XstartOnFirstThread" VM argument
is added.