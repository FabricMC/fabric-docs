---
title: Налаштування середовища розробки
description: Покрокова інструкція щодо налаштування середовища розробки для створення модів за допомогою Fabric.
authors:
  - IMB11
  - andrew6rant
  - SolidBlock-cn
  - modmuss50
  - daomephsta
  - liach
  - telepathicgrunt
  - 2xsaiko
  - natanfudge
  - mkpoli
  - falseresync
  - asiekierka
authors-nogithub:
  - siglong
---

# Налаштування середовища розробки {#setting-up-a-development-environment}

Щоб розпочати розробку модів за допомогою Fabric, вам потрібно буде налаштувати середовище розробки за допомогою IntelliJ IDEA.

## Установлення JDK 21 {#installing-jdk-21}

Для розробки модів для Minecraft 1.21.4 вам знадобиться JDK 21.

Якщо вам потрібна допомога щодо встановлення Java, ви можете звернутися до різних посібників із встановлення Java у [розділі посібників для гравців](../../players/index).

## Установлення IntelliJ IDEA {#installing-intellij-idea}

:::info
Очевидно, ви можете використовувати інші IDE, такі як Eclipse або Visual Studio Code, але на більшості сторінок цього сайту документації буде припущено, що ви використовуєте IntelliJ IDEA. Вам слід звернутися до документації для вашої IDE, якщо ви використовуєте іншу.
:::

Якщо у вас не встановлено IntelliJ IDEA, ви можете завантажити його з [офіційної сторінки](https://www.jetbrains.com/idea/download/) – дотримуйтеся вказівок із встановлення для вашої операційної системи.

Версія спільноти IntelliJ IDEA є безплатний та має відкритий вихідний код, і це рекомендована версія для модифікації за допомогою Fabric.

Можливо, вам доведеться прокрутити вниз, щоб знайти посилання для завантаження видання Community – воно виглядає так:

![Запит про завантаження версії IDEA Community Edition](/assets/develop/getting-started/idea-community.png)

## Встановлення плаґінів IDEA {#installing-idea-plugins}

Хоча ці плаґіни не є абсолютно необхідними, вони можуть значно полегшити модифікацію за допомогою Fabric – вам слід розглянути можливість їх встановлення.

### Minecraft Development {#minecraft-development}

Плаґін Minecraft Development підтримує модифікацію за допомогою Fabric, і це найважливіший плаґін для встановлення.

Ви можете встановити його, відкривши IntelliJ IDEA, а потім перейшовши до `File > Settings > Plugins > Marketplace` - знайдіть `Minecraft Development` у рядку пошуку, а потім натисніть кнопку `Install`.

Крім того, ви можете завантажити його зі [сторінки плаґінів](https://plugins.jetbrains.com/plugin/8327-minecraft-development), а потім установити, перейшовши до `File > Settings > Plugins > Install Plugin From Disk`.
