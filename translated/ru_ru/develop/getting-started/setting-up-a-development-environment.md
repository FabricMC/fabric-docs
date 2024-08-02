---
title: Настройка среды разработки
description: Пошаговое руководство по настройке среды разработки для создания модов с помощью Fabric.
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

# Настройка среды разработки {#setting-up-a-development-environment}

Для начала разработки модов с использованием Fabric вам нужно настроить среду разработки в IntelliJ IDEA.

## Установка JDK 21 {#installing-jdk-21}

Для разработки модов для Minecraft 1.21 вам потребуется JDK 21.

Если вам нужна помощь в установке Java, вы можете обратиться к различным руководствам по установке Java в [разделе руководств для игроков](../../players/index).

## Установка IntelliJ IDEA {#installing-intellij-idea}

:::info
Вы также можете использовать и другие IDE, например Eclipse или Visual Studio Code, но большинство страниц в этой документации предполагают что вы используете IntelliJ IDEA, если вы используете другую IDE, обратитесь к документации по ней.
:::

Если у вас не установлена IntelliJ IDEA, вы можете её установить на [официальном сайте](https://www.jetbrains.com/idea/download/) - следуйте шагам установки для вашей операционной системы.

Community edition IntelliJ IDEA бесплатна и имеет открытый исходный код, и также она рекомендуемая версия для модинга с Fabric.

Возможно, вам придется прокрутить страницу вниз, чтобы найти ссылку на загрузку Community edition, она выглядит следующим образом:

![Подсказка для загрузки Community Edition](/assets/develop/getting-started/idea-community.png)

## Установка плагинов IDEA {#installing-idea-plugins}

Хотя эти плагины не являются необходимыми, они могут сделать ваш процесс моддинга с Fabric более лёгким, вам следует подумать об их установкой.

### Minecraft Development {#minecraft-development}

Плагин Minecraft Development предоставляет поддержку моддинга с Fabric, и это самый важный плагин для установки.

Вы можете установить его открытием IntelliJ IDEA, а затем перейти по адресу `File > Settings > Plugins > Marketplace Tab` и найти `Minecraft Development` в поисковой строке, и затем нажать кнопку `Install`.

Альтернативный вариант: вы можете установить его на [странице плагина](https://plugins.jetbrains.com/plugin/8327-minecraft-development) и затем установите его по адресу `File > Settings > Plugins > Install Plugin From Disk`.
