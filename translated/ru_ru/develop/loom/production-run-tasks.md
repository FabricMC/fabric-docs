---
title: Задачи производственного цикла
description: Документация по задачам производственного запуска в плагине Fabric Loom Gradle.
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

При сборке мода для распространения среди пользователей он ремапится в промежуточные маппинги, что приводит к небольшим несоответствиям между средой разработки и производством (например, в версии vanilla). Хотя такие проблемы встречаются редко, имеет смысл протестировать мод в производственной среде, прежде чем выпускать его.

## Общие варианты {#common}

Задачи сервера и клиента наследуются от одного и того же класса `AbstractProductionRunTask`. Это означает, что у них есть общие возможности:

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

## Сервер {#server}

В задаче запуска сервера на производство используется та же программа запуска сервера, которую вы загружаете с сайта Fabric, что гарантирует создание среды, максимально приближенной к производственной.

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

Задание сервера имеет уникальную возможность запускать ваш мод с другой версией Minecraft, что может быть полезно, если вы разрабатываете кросс-версионный мод.

## Клиент {#client}

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
