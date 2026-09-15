---
title: Сборка мода в IntelliJ IDEA
description: Узнайте, как использовать IntelliJ IDEA для сборки Minecraft-мода, который можно распространять или тестировать в производственной среде.
authors:
  - cassiancc
  - cputnam-a11y
  - gdude2002
  - Scotsguy
prev:
  text: Генерация исходников в IntelliJ IDEA
  link: ./generating-sources
next:
  text: Советы и рекомендации для IntelliJ IDEA
  link: ./tips-and-tricks
---

В IntelliJ IDEA откройте вкладку Gradle справа и выполните задачу `build`. JAR-файлы должны появиться в папке `build/libs` в каталоге вашего проекта. Используйте JAR-файл с самым коротким именем вне среды разработки.

![Боковая панель IntelliJ IDEA с выделенной задачей build](/assets/develop/getting-started/build-idea.png)

![Папка build/libs с выделенным нужным файлом](/assets/develop/getting-started/build-libs.png)

## Установка и распространение {#installing-and-sharing}

После этого мод можно [установить как обычно](../../../players/installing-mods) или загрузить на надёжные сайты для размещения модов, такие, как [CurseForge](https://www.curseforge.com/minecraft) и [Modrinth](https://modrinth.com/discover/mods).
