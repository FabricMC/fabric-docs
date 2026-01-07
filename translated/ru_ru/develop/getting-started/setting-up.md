---
title: Настройка среды разработки
description: Пошаговое руководство по настройке среды разработки для создания модов с помощью Fabric.
authors:
  - 2xsaiko
  - andrew6rant
  - asiekierka
  - daomephsta
  - dicedpixels
  - falseresync
  - IMB11
  - its-miroma
  - liach
  - mkpoli
  - modmuss50
  - natanfudge
  - SolidBlock-cn
  - telepathicgrunt
authors-nogithub:
  - siglong
outline: false
---

## Установите JDK 21 {#install-jdk-21}

Для разработки модов для Minecraft 1.21.10 вам понадобится JDK 21.

Если вам нужна помощь в установке Java, вы можете обратиться к [руководствам по установке Java](../../players/installing-java/).

## Настройка IDE {#set-up-your-ide}

Чтобы начать разработку модов с помощью Fabric, вам необходимо настроить среду разработки с помощью IntelliJ IDEA (рекомендуется) или альтернативного варианта Visual Studio Code.

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
