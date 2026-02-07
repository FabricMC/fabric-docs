---
title: Установлення Java на Linux
description: Покрокова інструкція щодо встановлення Fabric на Linux.
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info ПЕРЕДУМОВИ

Можливо, вам знадобиться [встановити Java](../installing-java/linux) перед запуском `.jar`.

:::

<!-- #region common -->

## 1. Завантаження встановлювача Fabric {#1-download-the-fabric-installer}

Завантажте версію `.jar` встановлювача Fabric з [сайту Fabric](https://fabricmc.net/use/), натиснувши `Download installer (Universal/.JAR)`.

## 2. Запустіть встановлювач Fabric {#2-run-the-fabric-installer}

Перш ніж запустити встановлювач, закрийте Minecraft та запускач Minecraft.

Відкрийте термінал і запустіть установлювач за допомогою Java:

```sh
java -jar fabric-installer.jar
```

Коли ви відкриєте встановлювач, ви повинні побачити такий екран:

![Установлювач Fabric із виділеним «Установити»](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

Виберіть потрібну версію Minecraft і натисніть `Установити`. Переконайтеся, що вибрано `Створити інсталяцію`.

## 3. Кінцеве налаштування {#3-finish-setup}

Після завершення встановлення відкрийте запускач Minecraft. Потім виберіть інсталяцію Fabric зі спадного списку версій і натисніть «Грати».

![Запускач Minecraft з вибраною інсталяцією Fabric](/assets/players/installing-fabric/launcher-screen.png)

Тепер ви можете додавати моди до своєї гри. Перегляньте посібник [пошуку надійних модів](../finding-mods), щоб дізнатися більше.

Якщо у вас виникнуть проблеми, не соромтеся звертатися по допомогу до [Fabric Discord](https://discord.fabricmc.net/) на каналі `#player-support`.
