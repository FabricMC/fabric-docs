---
title: Установка Fabric в Linux
description: Пошаговое руководство по установке Fabric в Linux.
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info ТРЕБОВАНИЯ

Перед запуском файла `.jar` может потребоваться [установить Java](../installing-java/linux).

:::

<!-- #region common -->

## 1. Загрузка установщика Fabric {#1-download-the-fabric-installer}

Загрузите версию Fabric Installer в формате `.jar` с [веб-сайта Fabric](https://fabricmc.net/use/), нажав на кнопку `Download installer (Universal/.JAR)`.

## 2. Запуск установщика Fabric {#2-run-the-fabric-installer}

Перед запуском установщика сначала закройте Minecraft и Minecraft Launcher.

Откройте терминал и запустите установщик с помощью Java:

```sh
java -jar fabric-installer.jar
```

После запуска установщика вы должны увидеть экран, похожий на этот:

![Установщик Fabric с подсвеченным «Установить»](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

Выберите желаемую версию Minecraft и нажмите «Установить». Убедитесь, что опция «Создать профиль» отмечена.

## 3. Завершение настроек {#3-finish-setup}

После завершения установки откройте Minecraft Launcher. Затем выберите профиль Fabric из раскрывающегося списка версий и нажмите «Играть».

![Minecraft Launcher с выбранным профилем Fabric](/assets/players/installing-fabric/launcher-screen.png)

Теперь вы можете добавлять моды в свою игру. См. руководство [Поиск надежных модов](../finding-mods) для получения доп. информации.

Если у вас возникнут проблемы, не стесняйтесь обращаться за помощью в [Fabric Discord](https://discord.fabricmc.net/) в канале `#player-support`.
