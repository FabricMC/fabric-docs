---
title: Інші завдання
description: Документація для додаткових завдань Fabric Loom.
authors:
  - modmuss50
---

Наступні завдання не зареєстровані усталено у проєкті Loom, але їх можна зареєструвати для надання додаткових функцій.

## Генерація JSON мода Fabric {#fabric-mod-json}

`net.fabricmc.loom.task.FabricModJsonV1Task` — це завдання, яке можна використовувати для створення дійсного файлу `fabric.mod.json` для вашого мода. Це просте завдання, яке виводить файл, ви повинні налаштувати свій сценарій збірки, щоб включити файл у ваші ресурси модів, як вважаєте за потрібне.

```groovy
tasks.register("generateModJson", net.fabricmc.loom.task.FabricModJsonV1Task) {
    outputFile = file("fabric.mod.json")

    json {
        modId = "example-mod"
        version = "1.0.0"
    }
}
```

Наведений вище приклад є найпростішим використанням завдання, у якому буде створено файл `fabric.mod.json` із зазначеним ID мода та версією. Блок `json` підтримує всі поля, визначені в [схемі JSON мода Fabric](https://wiki.fabricmc.net/documentation:fabric_mod_json). Див. [FabricModJsonV1Spec](https://github.com/FabricMC/fabric-loom/blob/dev/1.12/src/main/java/net/fabricmc/loom/api/fmj/FabricModJsonV1Spec.java), щоб отримати повний список підтримуваних властивостей.

## Завантаження завдання {#download-task}

`net.fabricmc.loom.task.DownloadTask` — це просте завдання, яке можна використовувати для завантаження файлів із URL-адреси у вказане розташування.

Наприклад, щоб завантажити файл із певної URL-адреси та зберегти його в `out.txt` у каталозі проєкту:

```groovy
tasks.register("download", net.fabricmc.loom.task.DownloadTask) {
    url = "https://example.com/file.txt"
    output = file("out.txt")
}
```

Ви також можете вказати очікуваний хеш SHA-1, який використовуватиметься для перевірки цілісності завантаженого файлу, а також максимальний вік, коли вимагатиметься повторне завантаження, коли файл більше не буде свіжим:

```groovy
tasks.register("download", net.fabricmc.loom.task.DownloadTask) {
    url = "https://example.com/file.txt"
    output = file("out.txt")
    sha1 = "EXPECTED-SHA1-HASH-HERE"
    maxAge = Duration.ofDays(1)
}
```

## ModEnigmaTask {#modenigma-task}

`net.fabricmc.loom.task.tool.ModEnigmaTask` — це розширене завдання, яке можна використовувати для запуску [Enigma](https://github.com/FabricMC/Enigma) у файлі мапінгів. Це можна використовувати для генерації javadoc, наданого модом.

```groovy
tasks.register("enigma", net.fabricmc.loom.task.tool.ModEnigmaTask) {
    mappingsFile = file("mappings.mapping")
}
```

## ValidateMixinNameTask {#validatemixinnametask}

`net.fabricmc.loom.task.ValidateMixinNameTask` — це завдання, яке можна використовувати для перевірки відповідності назви класу міксина назві цільового класу.

```groovy
    tasks.register('validateMixinNames', net.fabricmc.loom.task.ValidateMixinNameTask) {
        source(sourceSets.main.output)
    }

    check.dependsOn "validateMixinNames"
```
