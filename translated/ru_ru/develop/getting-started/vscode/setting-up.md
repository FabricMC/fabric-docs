---
title: Настройка VS Code
description: Пошаговое руководство по настройке Visual Studio Code для создания модов с помощью Fabric.
authors:
  - dicedpixels
prev:
  text: Настройка IDE
  link: ../setting-up
next:
  text: Открытие проекта в VS Code
  link: ./opening-a-project
---

:::warning ВАЖНО
Хотя можно разрабатывать моды с помощью Visual Studio Code, мы не рекомендуем этого делать.
Рассмотрите возможность использования [IntelliJ IDEA](../intellij-idea/setting-up), которая имеет специализированный инструментарий для Java, расширенные возможности и полезные плагины, созданные сообществом, такие как **Minecraft Development**.
:::

:::info ТРЕБОВАНИЯ
Сначала убедитесь, что вы [установили JDK](../setting-up#install-jdk-21).
:::

## Установка {#installation}

Вы можете загрузить Visual Studio Code с сайта [code.visualstudio.com](https://code.visualstudio.com/) или через предпочитаемый вами менеджер пакетов.

![Страница загрузки Visual Studio Code](/assets/develop/getting-started/vscode/download.png)

## Предварительные условия {#prerequisites}

Visual Studio Code не обеспечивает поддержку языка Java из коробки. Однако Microsoft предоставляет удобный пакет расширений, который содержит все необходимые расширения для включения поддержки языка Java.

Вы можете установить этот пакет расширений из [Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

![Пакет расширений для Java](/assets/develop/getting-started/vscode/extension.png)

Или в самом Visual Studio Code, через представление Extensions.

![Пакет расширений для Java в режиме просмотра расширений](/assets/develop/getting-started/vscode/extension-view.png)

Расширение **Language Support for Java** представит вам начальный экран для настройки JDK. Вы можете сделать это, если еще не сделали.
