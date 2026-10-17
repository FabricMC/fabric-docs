---
title: 构建 Mod
description: 学习如何构建可分享或在生产环境中测试的 Minecraft Mod。
authors:
  - cassiancc
  - cputnam-a11y
  - gdude2002
  - Scotsguy
---

一旦模组准备好进行测试，你可以将其导出为 JAR 文件，以便在模组托管网站上分享，或与其他模组一起在生产环境中测试。

## 选择你的 IDE {#choose-your-ide}

<ChoiceComponent :choices="[
{
 name: 'IntelliJ IDEA',
 href: './intellij-idea/building-a-mod',
 icon: 'simple-icons:intellijidea',
 color: '#FE2857',
},
{
 name: 'Visual Studio Code',
 icon: 'codicon:vscode',
 color: '#007ACC',
},
]" />

## 在终端中构建 {#terminal}

::: warning

若使用终端而非 IDE 构建模组，且默认 Java 安装版本与项目预期不符，可能会引发问题。 为确保构建可靠，建议使用可轻松指定正确 Java 版本的 IDE。

:::

在与模组项目目录相同的目录下打开终端，运行以下命令：

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat build
```

```sh:no-line-numbers [macOS/Linux]
./gradlew build
```

:::

生成的 JAR 文件将位于项目中的 `build/libs` 文件夹中。 若在非开发环境下使用，请选取文件名最短的那个 JAR 文件。

## 安装与分享 {#installing-and-sharing}

完成上述步骤后，该模组 即可[按常规方式安装](../../players/installing-mods)，或上传至可靠的模组托管网站，如 [CurseForge](https://www.curseforge.com/minecraft) 和 [Modrinth](https://modrinth.com/discover/mods)。
