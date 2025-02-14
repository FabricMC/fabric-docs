---
title: Встановлення модів
description: Покрокова інструкція по установці модів для Fabric.
authors:
  - IMB11
---

Ця інструкція допоможе вам встановити моди для Fabric якщо ви використовуєте запускач Minecraft.

Ця інструкція допоможе вам встановити моди для Fabric якщо ви використовуєте запускач Minecraft.

## 1. Завантаження мода {#1-download-the-mod}

:::warning
Ви повинні завантажувати моди тільки з джерел, яким ви довіряєте. Додаткову інформацію про пошук модів див. у посібнику [пошуку надійних модів](./finding-mods).
:::

Більшість модів також потребує Fabric API, що може бути завантажено з [Modrinth](https://modrinth.com/mod/fabric-api) або [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api).

Більшість модів також потребує Fabric API, що може бути завантажено з [Modrinth](https://modrinth.com/mod/fabric-api) або [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api).

- Вони працюють з версією Minecraft, на якій ви хочете грати. Наприклад, мод що працює на версії 1.20, може не працювати на версії 1.20.2.
- Вони призначені для Fabric, а не для іншого завантажувача модів.
- Вони призначені для правильної версії Minecraft (Java Edition).

## 2. Перемістіть мод до теки `mods` {#2-move-the-mod-to-the-mods-folder}

Теку mods можна знайти в наступних місцях для кожної операційної системи.

Теку mods можна знайти в наступних місцях для кожної операційної системи.

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

Коли ви знайдете теку `mods`, ви можете перемістити туди файли `.jar` мода.

Коли ви знайдете теку `mods`, ви можете перемістити туди файли `.jar` мода.

## 3. Готово! {#3-you-re-done}

Після встановлення модів в теку `mods` ви можете відкрити запускач Minecraft і вибрати профіль Fabric зі спадного списку в нижньому лівому куті та натиснути грати!

Після встановлення модів в теку `mods` ви можете відкрити запускач Minecraft і вибрати профіль Fabric зі спадного списку в нижньому лівому куті та натиснути грати!

## Усунення несправностей {#troubleshooting}

Якщо під час виконання цієї інструкції у вас виникнуть проблеми, ви можете звернутися за допомогою в [Discord Fabric](https://discord.gg/v6v4pMv) в каналі `#player-support`.

Якщо під час виконання цієї інструкції у вас виникнуть проблеми, ви можете звернутися за допомогою в [Discord Fabric](https://discord.gg/v6v4pMv) в каналі `#player-support`.

- [Звіти про збої](./troubleshooting/crash-reports)
- [Завантаження журналів](./troubleshooting/uploading-logs)
