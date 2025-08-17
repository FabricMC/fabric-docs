---
title: Запуск виробничих завдань
description: Документація для виробничих завдань у плаґіні Fabric Loom Gradle.
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

Під час створення мода для розповсюдження серед користувачів його буде переназначено на проміжному показу, що спричинить невеликі невідповідності між середовищем розробки та робочим середовищем (як-от запускач). Хоча такі проблеми трапляються рідко, має сенс перевірити мод у робочому середовищі, перш ніж випускати її.

## Загальні параметри {#common}

Завдання сервера та клієнта успадковуються від того самого класу `AbstractProductionRunTask`. Це означає, що вони мають такі можливості:

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

Завдання виконання робочого сервера використовує той самий запускач сервера, який ви завантажуєте із сайту Fabric, гарантуючи, що середовище максимально наближене до робочого.

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

Серверне завдання має унікальну можливість запускати ваш мод з іншою версією Minecraft, це може бути корисним, якщо ви розробляєте крос-версійний мод.

## Клієнт {#client}

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
