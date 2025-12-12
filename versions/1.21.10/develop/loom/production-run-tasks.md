---
title: Production Run Tasks
description: Documentation for the production run tasks in the Fabric Loom Gradle plugin.
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

When building your mod to distribute to users, it gets remapped to intermediary mappings, which cause slight inconsistencies between the development environment and production (like the vanilla launcher). Although such issues are rare, it makes sense to test your mod in a production environment before releasing it.

## Common Options {#common}

The server and client tasks both inherit from the same `AbstractProductionRunTask` class. This means they share the following options:

```groovy
tasks.register("prodServer", net.fabricmc.loom.task.prod.ServerProductionRunTask) {
    // A collection of mod jars that will be used when running the game. The mods must be remapped to run with intermediary names.
    // This uses a Gradle ConfigurableFileCollection allowing the files to come from a variety of sources.
    mods.from file("mod.jar")
    mods.from configurations.exampleConfiguration

    // A list of additional JVM arguments to pass to the game.
    jvmArgs.add("-Dfabric.client.gametest")

    // A list of additional program arguments to pass to the game.
    programArgs.add("--example")

    // The directory to run the game in.
    runDir = file("run")

    // Specify the Java toolchain to use when running the game.
    // Defaults to the Java version being used to run Gradle.
    // See https://docs.gradle.org/current/userguide/toolchains.html#sec:plugins_toolchains
    javaLauncher = javaToolchains.launcherFor {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
```

## Server {#server}

The server production run task uses the same server launcher that you download from the Fabric website, guaranteeing that the environment is as close to production as possible.

```groovy
tasks.register("prodServer", net.fabricmc.loom.task.prod.ServerProductionRunTask) {
    // The version of the Fabric Installer to use. This must be specified.
    installerVersion = "1.0.1"

    // The version of Fabric Loader to use.
    // Defaults to the version of Fabric Loader that the project is using.
    loaderVersion = "0.16.10"

    // The version of Minecraft to use.
    // Defaults to the version of Minecraft that the project is using.
    minecraftVersion = "1.21.4"
}
```

The server task has the unique ability to run your mod with a different version of Minecraft, this might be useful if you are developing a cross-versions mod.

## Client {#client}

```groovy
tasks.register("prodClient", net.fabricmc.loom.task.prod.ClientProductionRunTask) {
    // Whether to use XVFB to run the game, using a virtual framebuffer. This is useful for headless CI environments.
    // Defaults to true only on Linux and when the "CI" environment variable is set.
    // XVFB must be installed, on Debian-based systems you can install it with: `apt install xvfb`
    useXVFB = true

    // Optionally configure the tracy-capture executable.
    tracy {
        // The path to the tracy-capture executable.
        tracyCapture = file("tracy-capture")

        // The output path of the captured tracy profile.
        output = file("profile.tracy")

        // The maximum number of seconds to wait for tracy-capture to stop on its own before killing it.
        // Defaults to 10 seconds.
        maxShutdownWaitSeconds = 10
    }
}
```
