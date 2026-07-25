---
title: Настройка IntelliJ IDEA
description: Пошаговое руководство по настройке IntelliJ IDEA для создания модов с помощью Fabric.
authors:
  - 2xsaiko
  - Andrew6rant
  - asiekierka
  - Daomephsta
  - dicedpixels
  - falseresync
  - IMB11
  - liach
  - mkpoli
  - modmuss50
  - natanfudge
  - SolidBlock-cn
  - TelepathicGrunt
authors-nogithub:
  - siglong
prev:
  text: Настройка IDE
  link: ../setting-up
next:
  text: Открытие проекта в IntelliJ IDEA
  link: ./opening-a-project
resources:
  https://www.jetbrains.com/idea/download/: Скачать IntelliJ IDEA от JetBrains
  https://www.jetbrains.com/help/idea/getting-started.html: Начало работы с IntelliJ IDEA
  https://plugins.jetbrains.com/plugin/8327-minecraft-development: Плагин Minecraft Development — JB Marketplace
---

<!---->

:::info ТРЕБОВАНИЯ

Сначала убедитесь, что вы [установили JDK](../setting-up#install-jdk-21).

:::

## Установка IntelliJ IDEA {#installing-intellij-idea}

::: warning

Для моддинга версии 26.1 требуется IntelliJ IDEA 2025.3 или новее.

:::

Если у вас не установлен IntelliJ IDEA **или версия ниже 2025.3**, вы можете скачать его с [официального сайта](https://www.jetbrains.com/idea/download/) — следуйте инструкциям по установке для вашей операционной системы.

![Страница загрузки IntelliJ IDEA](/assets/develop/getting-started/idea-download.png)

## Установка плагина "Minecraft Development" {#installing-idea-plugins}

Плагин Minecraft Development обеспечивает поддержку моддинга с помощью Fabric, и это самый важный плагин, который необходимо установить.

::: tip

Хотя этот плагин не является строго необходимым, он может значительно облегчить работу над модом Fabric - вам стоит подумать о его установке.

:::

Вы можете установить его, открыв IntelliJ IDEA, а затем перейдя на вкладку **Файл** > **Настройки** > **Плагины** > **Marketplace**, затем найдите `Minecraft Development` в строке поиска и нажмите кнопку **Установить**.

Также вы можете загрузить его со страницы [страница плагина](https://plugins.jetbrains.com/plugin/8327-minecraft-development), а затем установить, перейдя по пути **Файл** > **Настройки** > **Плагины** > **Установка плагина с диска**.

### О создании проекта {#about-creating-a-project}

Хотя можно создать проект с помощью этого плагина, делать это не рекомендуется, поскольку шаблоны часто устаревают. Вместо этого воспользуйтесь [Создание проекта](.../creating-a-project), чтобы использовать Fabric Template Mod Generator.
