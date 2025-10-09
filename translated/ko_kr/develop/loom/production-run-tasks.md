---
title: 배포 환경에서 실행
description: Gradle용 Fabric Loom 플러그인의 배포 환경 실행 작업에 대한 문서입니다.
authors:
  - Atakku
  - caoimhe
  - daomephsta
  - jamieswhiteshirt
  - Juuxel
  - kb-1000
  - modmuss50
  - SolidBlock-cn
---

사용자에게 모드를 배포하기 위해 빌드하면 모드는 Intermediary 매핑으로 리맵되고, 이는 개발 환경과 실제 배포 환경의 약간의 차이를 발생시킵니다. 이로 인한 문제는 매우 드물지만, 모드를 배포하기 전에 배포 환경에서 미리 테스트하는 것이 좋습니다.

## 기본 구성 {#common}

서버와 클라이언트 작업 모두 동일한 `AbstractProductionRunTask` 클래스를 상속합니다. 다시 말해, 두 작업 모두 다음과 같은 옵션을 공유하게 됩니다:

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

## 서버 {#server}

배포 환경에서 서버 실행 작업은 Fabric 웹사이트에 다운로드할 수 있는 동일한 실행기를 이용하기 때문에, 실제 배포 환경과 거의 유사하다고 볼 수 있습니다.

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

이 서버 작업은 다른 버전의 Minecraft에서 모드를 실행할 수 있는 특별한 능력을 가져, 다중 버전 지원 모드를 개발하고 있다면 유용하게 사용할 수 있습니다.

## 클라이언트 {#client}

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
