---
title: Налаштування IntelliJ IDEA
description: Покрокова інструкція з налаштування IntelliJ IDEA для створення модів Fabric.
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
  text: Налаштування вашого IDE
  link: ../setting-up
next:
  text: Відкриття проєкту в IntelliJ IDEA
  link: ./opening-a-project
---

<!---->

:::info ПЕРЕДУМОВИ

Спершу переконайтеся, що ви [встановили JDK](../setting-up#install-jdk-21).

:::

## Установлення IntelliJ IDEA {#installing-intellij-idea}

Якщо у вас не встановлено IntelliJ IDEA, ви можете завантажити його з [офіційного сайту](https://www.jetbrains.com/idea/download/) — дотримуйтеся вказівок зі встановлення для вашої операційної системи.

![Запит на завантаження IntelliJ IDEA](/assets/develop/getting-started/idea-download.png)

## Установлення плаґіна «Minecraft Development» {#installing-idea-plugins}

Плаґін Minecraft Development підтримує модифікацію за допомогою Fabric, і це найважливіший плаґін для встановлення.

::: tip

Попри те, що цей плаґін не є абсолютно необхідним, він може значно полегшити модифікацію за допомогою Fabric — вам слід подумати про його встановлення.

:::

Ви можете встановити його, відкривши IntelliJ IDEA, а потім перейшовши до **File** > **Settings…** > **Plugins** > вкладка **Marketplace**, потім знайдіть `Minecraft Development` у рядку пошуку та натисніть кнопку **Install**.

Крім того, ви можете завантажити його зі [сторінки плаґінів](https://plugins.jetbrains.com/plugin/8327-minecraft-development), а потім установити, перейшовши до **File** > **Settings** > **Plugins** > **Install Plugin From Disk**.

### Про створення проєкту {#about-creating-a-project}

Хоча за допомогою цього плаґіна можна створити проєкт, це не рекомендується, оскільки шаблони часто застаріли. Натомість спробуйте виконати [створення проєкту](../creating-a-project), щоб скористатися генератором шаблонного мода Fabric.
