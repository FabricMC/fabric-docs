---
title: Другие задачи (Other Tasks)
description: "**Описание:** Документация по дополнительным задачам Fabric Loom."
authors:
  - modmuss50
---

Следующие задачи **не регистрируются по умолчанию** в проекте Loom, но могут быть добавлены для расширения функционала.

## Генерация Fabric Mod JSON {#fabric-mod-json}

Задача `net.fabricmc.loom.task.FabricModJsonV1Task` используется для создания корректного файла `fabric.mod.json` для твоего мода. Это простая задача, которая создаёт выходной файл — тебе нужно только настроить билд-скрипт так, чтобы он включал этот файл в ресурсы мода.

```groovy
tasks.register("generateModJson", net.fabricmc.loom.task.FabricModJsonV1Task) {
    outputFile = file("fabric.mod.json")

    json {
        modId = "example-mod"
        version = "1.0.0"
    }
}
```

Приведённый пример — это базовое использование задачи. Он создаст файл `fabric.mod.json` с указанными `modId` и `version`. Блок `json` поддерживает все поля, определённые в [схеме Fabric Mod JSON](https://fabricmc.net/wiki/documentation:fabric_mod_json). Полный список доступных свойств можно посмотреть в [FabricModJsonV1Spec](https://github.com/FabricMC/fabric-loom/blob/dev/1.12/src/main/java/net/fabricmc/loom/api/fmj/FabricModJsonV1Spec.java).

## Задача скачивания (Download Task) {#download-task}

Задача `net.fabricmc.loom.task.DownloadTask` позволяет скачивать файлы из заданного URL в указанное место.

Например, чтобы скачать файл с сайта и сохранить его в `out.txt` в корне проекта:

```groovy
tasks.register("download", net.fabricmc.loom.task.DownloadTask) {
    url = "https://example.com/file.txt"
    output = file("out.txt")
}
```

Также можно указать ожидаемый **SHA-1 хэш** для проверки целостности файла и **максимальный возраст** (время, после которого файл будет перекачан, если устарел):

```groovy
tasks.register("download", net.fabricmc.loom.task.DownloadTask) {
    url = "https://example.com/file.txt"
    output = file("out.txt")
    sha1 = "EXPECTED-SHA1-HASH-HERE"
    maxAge = Duration.ofDays(1)
}
```

## Задача ModEnigmaTask {#modenigma-task}

Задача `net.fabricmc.loom.task.tool.ModEnigmaTask` — это продвинутая задача, которая запускает [Enigma](https://github.com/FabricMC/Enigma) для работы с файлом маппингов. Она может использоваться для генерации **javadoc**, предоставляемого модом.

```groovy
tasks.register("enigma", net.fabricmc.loom.task.tool.ModEnigmaTask) {
    mappingsFile = file("mappings.mapping")
}
```

## Проверка имён Mixin (ValidateMixinNameTask) {#validatemixinnametask}

Задача `net.fabricmc.loom.task.ValidateMixinNameTask` проверяет, совпадает ли имя класса Mixin с именем целевого класса, к которому он применяется.

```groovy
    tasks.register('validateMixinNames', net.fabricmc.loom.task.ValidateMixinNameTask) {
        source(sourceSets.main.output)
    }

    check.dependsOn "validateMixinNames"
```
