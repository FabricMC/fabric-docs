---
title: Установлення модів
description: Покрокова інструкція з установлення модів для Fabric.
authors:
  - IMB11
---

Ця інструкція допоможе вам встановити моди для Fabric якщо ви використовуєте запускач Minecraft.

Для запускачів сторонніх розробників слід ознайомитися з їх документацією.

## 1. Завантаження мода {#1-download-the-mod}

::: warning

Ви повинні завантажувати моди тільки з джерел, яким ви довіряєте. Додаткову інформацію про пошук модів див. у посібнику [пошуку надійних модів](./finding-mods).

:::

Більшість модів також потребують Fabric API, що можна завантажити з [Modrinth](https://modrinth.com/mod/fabric-api) або [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api).

При завантаженні модів переконайтеся, що:

- Вони працюють з версією Minecraft, на якій ви хочете грати. Наприклад, мод що працює на версії 1.21.8, може не працювати на версії 1.21.11.
- Вони призначені для Fabric, а не для іншого завантажувача модів.
- Вони призначені для правильного видання Minecraft (Java Edition).

## 2. Перемістіть мод до теки `mods` {#2-move-the-mod-to-the-mods-folder}

Теку mods можна знайти в наступних місцях для кожної операційної системи.

Зазвичай ви можете вставити ці шляхи в адресний рядок провідника файлів, щоб швидко перейти до теки.

::: code-group

```text:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```text:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```text:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

Коли ви знайдете теку `mods`, ви можете перемістити туди файли `.jar` мода.

![Установлені моди в теці mods](/assets/players/installing-mods.png)

## 3. Усе готово! {#3-you-re-done}

Після встановлення модів в теку `mods` ви можете відкрити запускач Minecraft і вибрати профіль Fabric зі спадного списку в нижньому лівому куті та натиснути «Грати»!

![Запускач Minecraft з вибраним профілем Fabric](/assets/players/installing-fabric/launcher-screen.png)

## Усунення несправностей {#troubleshooting}

Якщо під час виконання цієї інструкції у вас виникнуть проблеми, ви можете звернутися за допомогою в [Discord Fabric](https://discord.fabricmc.net/) в каналі `#player-support`.

Ви також можете спробувати розв'язувати проблему самостійно, прочитавши сторінки усунення несправностей:

- [Звіти про збої](./troubleshooting/crash-reports)
- [Вивантаження журналів](./troubleshooting/uploading-logs)
