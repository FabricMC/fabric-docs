---
title: Loom
description: Documentation for the Fabric Loom Gradle plugin.
authors:
  - modmuss50
---

# Fabric Loom {#loom}

Fabric Loom, or just Loom for short, is a [Gradle](https://gradle.org/|Gradle) plugin for development of mods in the Fabric ecosystem. Loom provides utilities to install Minecraft and mods in a development environment so that you can link against them with respect to Minecraft obfuscation and its differences between distributions and versions. It also provides run configurations for use with Fabric Loader, Mixin compile processing and utilities for Fabric Loader's jar-in-jar system.

If you are just getting started please see the [Getting Started](getting-started/setting-up-a-development-environment) page, this page here is a reference to all of the options and features of Loom. The documentation for Fabric Loom is minecraft version independent, Loom supports ALL versions of Minecraft even ones not officialy supported by Fabric-API.

# Depending on Sub Projects {#subprojects}

When setting up a multi-project build that depenends on another loom project you should use the `namedElements` configuration when depending on the other project. By default a projects "outputs" are remapped to intermediary names. The `namedElements` configuration contains the project ouputs that have not been remapped.

```groovy
dependencies {
	implementation project(path: ":name", configuration: "namedElements")
}
```

If you are using splitsource sets in a multi-project build, you will also need to add a dependency for the other projects client sourceset.

```groovy
dependencies {
	clientImplementation project(":name").sourceSets.client.output
}
```

# Split Client and Common code {#split-sources}

For years a common source of server crashes has been from mods accidentally calling client only code when installed on a server. The latest loom and loader verions provide an option to require all client code to be moved into its own sourceset. This is done to provide a compile-time guarantee against calling client only Minecraft code or client only API on the server. A single jar file that works on both the client and server is still built from the two sourcesets.

The following snippet from a build.gradle file shows how you can enable this for your mod. As your mod will now be split across two sourcesets, you will need to use the new DSL to define your mods sourcesets. This enables Fabric Loader to group your mods classpath together. This is also useful for some other complex multi-project setups.

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

# Resolving issues {#issues}

Loom and/or gradle can sometimes fail due to corrupted cache files. Running `./gradlew build --refresh-dependencies` will force gradle and loom to re-download and recreate all of the files. This may take a few minutes but is usually quite successful with resolving cache related issues.

# Development environment setup {#setup}

Loom is designed to work out of the box by simply setting up a workspace in the user's IDE of choice. It does quite a few things behind the scenes to create a development environment with Minecraft:

  - Downloads the client and server jar from official channels for the configured version of Minecraft.
  - Merges the client and server jar to produce a merged jar with `@Environment` and `@EnvironmentInterface` annotations.
  - Downloads the configured mappings.
  - Remaps the merged jar with intermediary mappings to produce an intermediary jar.
  - Remaps the merged jar with yarn mappings to produce a mapped jar.
  - Optional: Decompiles the mapped jar to produce a mapped sources jar and linemap, and applies the linemap to the mapped jar.
  - Adds dependencies of Minecraft.
  - Downloads Minecraft assets.
  - Processes and includes mod-augmented dependencies.

# Caches {#caches}

  * `${GRADLE_HOME}/caches/fabric-loom`: The user cache, a cache shared by all Loom projects for a user. Used to cache Minecraft assets, jars, merged jars, intermediary jars and mapped jars.
  * `.gradle/loom-cache`: The root project persistent cache, a cache shared by a project and its subprojects. Used to cache remapped mods as well as generated included mod JARs.
  * `build/loom-cache`: The root project build cache.
  * `**/build/loom-cache`: The (sub)project build cache.

# Dependency configurations {#configurations}

  * `minecraft`: Defines the version of Minecraft to be used in the development environment. 
  * `mappings`: Defines the mappings to be used in the development environment.
  * `modImplementation`, `modApi` and `modRuntime`: Augmented variants of `implementation`, `api` and `runtime` for mod dependencies. Will be remapped to match the mappings in the development environment and has any nested JARs removed.
  * `include`: Declares a dependency that should be included as a jar-in-jar in the `remapJar` output. This dependency configuration is not transitive. For non-mod dependencies, Loom will generate a mod JAR with a fabric.mod.json using the name as the mod ID and the same version.

# Default configuration {#configuration}

  * Applies the following plugins: `java`, `eclipse`.
  * Adds the following Maven repositories: Fabric [https://maven.fabricmc.net/](https://maven.fabricmc.net/), Mojang [https://libraries.minecraft.net/](https://libraries.minecraft.net/) and Maven Central.
  * Configures the `eclipse` task to be finalized by the `genEclipseRuns` task.
  * If an `.idea` folder exists in the root project, downloads assets (if not up-to-date) and installs run configurations in `.idea/runConfigurations`.
  * Adds `net.fabricmc:fabric-mixin-compile-extensions` and its dependencies with the `annotationProcessor` dependency configuration.
  * Configures all non-test JavaCompile tasks with configurations for the Mixin annotation processor.
  * Configures the `remapJar` task to output a JAR with the same name as the `jar` task output, then adds a "dev" classifier to the `jar` task.
  * Configures the `remapSourcesJar` task to process the `sourcesJar` task output if the task exists.
  * Adds the `remapJar` task and the `remapSourcesJar` task as dependencies of the `build` task.
  * Configures the `remapJar` task and the `remapSourcesJar` task to add their outputs as `archives` artifacts when executed.
  * For each MavenPublication (from the `maven-publish` plugin):
	* Manually appends dependencies to the POM for mod-augmented dependency configurations, provided the dependency configuration has a Maven scope.

All run configurations have the run directory `${projectDir}/run` and the VM argument `-Dfabric.development=true`. The main classes for run configurations is usually defined by a `fabric-installer.json` file in the root of Fabric Loader's JAR file when it is included as a mod dependency, but the file can be defined by any mod dependency. If no such file is found, the main classes defaults to `net.fabricmc.loader.launch.knot.KnotClient` and `net.fabricmc.loader.launch.knot.KnotServer`.

