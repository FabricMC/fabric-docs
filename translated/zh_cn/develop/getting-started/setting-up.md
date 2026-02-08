---
title: 设置开发环境
description: 关于如何搭建 Fabric 模组开发环境的逐步指南。
authors:
  - 2xsaiko
  - Andrew6rant
  - asiekierka
  - Daomephsta
  - dicedpixels
  - falseresync
  - IMB11
  - its-miroma
  - liach
  - mkpoli
  - modmuss50
  - natanfudge
  - SolidBlock-cn
  - TelepathicGrunt
authors-nogithub:
  - siglong
outline: false
---

## 安装 JDK 21 {#install-jdk-21}

要为 Minecraft 1.21.11 开发模组，你需要 JDK 21。

如果你在安装 Java 时需要帮助，可以参考 [Java 安装指南](../../players/installing-java/)。

## 设置你的 IDE {#set-up-your-ide}

要开始使用 Fabric 开发模组，你需要使用 IntelliJ IDEA（推荐）或 Visual Studio Code 设置开发环境。

<ChoiceComponent :choices="[
{
 name: 'IntelliJ IDEA',
 href: './intellij-idea/setting-up',
 icon: 'simple-icons:intellijidea',
 color: '#FE2857',
},
{
 name: 'Visual Studio Code',
 href: './vscode/setting-up',
 icon: 'codicon:vscode',
 color: '#007ACC',
},
]" />
