---
title: Установка модов
description: Пошаговая инструкция по установке модов для Fabric.
authors:
  - IMB11
---

В этом гайде мы расскажем вам об установке модов для Fabric на официальный лаунчер Minecraft.

Для сторонних лаунчеров вам следует ознакомиться с их документацией.

## 1. Загрузка модов {#1-download-the-mod}

::: warning

Вы должны загружать моды только из источников, которым вы доверяете. Смотрите гайд [Поиск безопасных модов](./finding-mods) для большей информации.

:::

Для большинства модов также требуется Fabric API, который можно загрузить с [Modrinth](https://modrinth.com/mod/fabric-api) или [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api).

При загрузке модов убедитесь, что:

- Они работают с той версией Minecraft, на которой вы хотите играть. Модификация, которая работала, например, в версии 1.21.8, может перестать работать в версии 1.21.11.
- Они предназначены для Fabric, а не для другого загрузчика модов.
- Кроме того, они предназначены для правильной версии Minecraft (Java Edition).

## 2. Перемещение мода в папку `mods` {#2-move-the-mod-to-the-mods-folder}

Папку mods можно найти в следующих местах для каждой операционной системы.

Обычно вы можете вставить эти пути в адресную строку проводника, чтобы быстро перейти к нужной папке.

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

Как только вы найдете папку `mods`, вы можете переместить в нее `.jar` файл мода.

![Установленный мод в папке модов](/assets/players/installing-mods.png)

## 3. Готово! {#3-you-re-done}

Как только вы переместите мод в папку `mods`, вы можете открыть лаунчер Minecraft, выбрать профиль Fabric из списка в левом нижнем углу и нажать Играть!

![Minecraft Launcher с выбранным профилем Fabric](/assets/players/installing-fabric/launcher-screen.png)

## Устранение неполадок {#troubleshooting}

Если у вас возникнут проблемы при выполнении инструкций этого руководства, вы можете обратиться за помощью в [Fabric Discord](https://discord.fabricmc.net/) в канале `#player-support`.

Вы также можете попытаться устранить проблему самостоятельно, ознакомившись со страницами, посвященными устранению неполадок:

- [Краш-репорты](./troubleshooting/crash-reports)
- [Загрузка логов](./troubleshooting/uploading-logs)
