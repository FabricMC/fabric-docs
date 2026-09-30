---
title: Сборка мода
description: Узнайте, как использовать IntelliJ IDEA для сборки Minecraft-мода, который можно распространять или тестировать в производственной среде.
authors:
  - cassiancc
  - cputnam-a11y
  - gdude2002
  - Scotsguy
---

Как только ваш мод будет готов к тестированию, вы сможете экспортировать его в JAR-файл, который можно будет разместить на вебсайтах по хостингу модов или использовать для тестирования вашего мода в рабочей среде вместе с другими модами.

## Выберите свою IDE {#choose-your-ide}

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

## Сборка в терминале {#terminal}

::: warning

Использование терминала для сборки мода вместо IDE может привести к проблемам, если ваш стандартный пакет Java не соответствует требованиям проекта. Для обеспечения более надёжной сборки рекомендуется использовать IDE, позволяющую легко указать нужную версию Java.

:::

Откройте терминал в том же каталоге, что и каталог проекта мода, и выполните следующую команду:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat build
```

```sh:no-line-numbers [macOS/Linux]
./gradlew build
```

:::

JAR-файлы должны появиться в папке `build/libs` в каталоге вашего проекта. Используйте JAR-файл с самым коротким именем вне среды разработки.

## Установка и распространение {#installing-and-sharing}

После этого мод можно [установить как обычно](../../players/installing-mods) или загрузить на надёжные сайты для размещения модов, такие, как [CurseForge](https://www.curseforge.com/minecraft) и [Modrinth](https://modrinth.com/discover/mods).
