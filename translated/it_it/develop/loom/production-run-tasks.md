---
title: Attività d'Avvio in Produzione
description: Documentazione per le attività d'avvio in produzione in Loom, il plugin Gradle di Fabric.
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

Quando costruisci la tua mod per distribuirla agli utenti, viene rimappata a mapping intermediari, che causano inconsistenze lievi tra l'ambiente di sviluppo e la produzione (per esempio il launcher vanilla). Anche se problematiche del genere sono rare, ha senso testare la tua mod in un ambiente di produzione prima di rilasciarla.

## Opzioni Comuni {#common}

Sia l'attività server che client ereditano dalla stessa classe `AbstractProductionRunTask`. Ciò significa che condividono le seguenti opzioni:

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

L'attività d'avvio in produzione del server usa lo stesso launcher di server che si scarica dal sito di Fabric, il che garantiche che l'ambiente è il più vicino possibile alla produzione.

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

L'attività del server ha un'abilità particolare, ovvero quella di eseguire la tua mod con una versione di Minecraft diversa, il che potrebbe essere utile nello sviluppare mod per più versioni.

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
