---
title: Налаштування VS Code
description: Покрокова інструкція з налаштування Visual Studio Code для створення модів Fabric.
authors:
  - dicedpixels
prev:
  text: Налаштування вашого IDE
  link: ../setting-up
next:
  text: Відкриття проєкту у VS Code
  link: ./opening-a-project
---

<!---->

:::warning ВАЖЛИВО

Хоча можна розробляти моди за допомогою Visual Studio Code, ми рекомендуємо цього не робити.
Розгляньте можливість використання [IntelliJ IDEA](../intellij-idea/setting-up), який містить спеціальні інструменти Java, розширені функції та корисні плаґіни, створені спільнотою, такі як **Minecraft Development**.

:::

:::info ПЕРЕДУМОВИ

Спершу переконайтеся, що ви [встановили JDK](../setting-up#install-jdk-21).

:::

## Установлення {#installation}

Ви можете завантажити Visual Studio Code з [code.visualstudio.com](https://code.visualstudio.com/) або через бажаний менеджер пакетів.

![Сторінка завантаження Visual Studio Code](/assets/develop/getting-started/vscode/download.png)

## Передумови {#prerequisites}

Visual Studio Code не підтримує мову Java із коробки. Однак Microsoft надає зручний пакет розширень, який містить усі необхідні розширення для її підтримки.

Ви можете встановити цей пакет розширення з [ринку Visual Studio](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

![Пакет розширення Java](/assets/develop/getting-started/vscode/extension.png)

Або, у самому Visual Studio Code, через подання розширень.

![Пакет розширень для Java у режимі подання розширень](/assets/develop/getting-started/vscode/extension-view.png)

Розширення **Language Support for Java** запропонує вам екран запуску для налаштування JDK. Ви можете це зробити, якщо ви ще цього не зробили.
