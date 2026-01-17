---
title: Andere Aufgaben
description: Dokumentation für die zusätzlichen Aufgaben von Fabric Loom.
authors:
  - modmuss50
---

Die folgenden Aufgaben sind standardmäßig nicht in einem Loom-Projekt registriert, können jedoch registriert werden, um zusätzliche Funktionen bereitzustellen.

## Fabric Mod JSON-Generierung {#fabric-mod-json}

Der `net.fabricmc.loom.task.FabricModJsonV1Task` ist eine Aufgabe, mit der eine gültige `fabric.mod.json`-Datei für deinen Mod generiert werden kann. Dies ist eine einfache Aufgabe, die eine Datei ausgibt. Es liegt an dir, dein Buildskript so zu konfigurieren, dass die Datei nach deinen Vorstellungen in die Ressourcen deines Mods aufgenommen wird.

```groovy
tasks.register("generateModJson", net.fabricmc.loom.task.FabricModJsonV1Task) {
    outputFile = file("fabric.mod.json")

    json {
        modId = "example-mod"
        version = "1.0.0"
    }
}
```

Das obige Beispiel ist die grundlegendste Verwendung der Aufgabe und generiert eine Datei mit dem Namen `fabric.mod.json` mit der angegebenen Mod-ID und Version. Der `json`-Block unterstützt alle Felder, die im [Fabric Mod JSON-Schema](https://wiki.fabricmc.net/documentation:fabric_mod_json) definiert sind. Siehe [FabricModJsonV1Spec](https://github.com/FabricMC/fabric-loom/blob/dev/1.12/src/main/java/net/fabricmc/loom/api/fmj/FabricModJsonV1Spec.java) für eine vollständige Liste allter unterstützten Eigenschaften.

## Download Aufgabe {#download-task}

Der `net.fabricmc.loom.task.DownloadTask` ist eine einfache Aufgabe, mit der Dateien von einer URL an einen bestimmten Speicherort heruntergeladen werden können.

Zum Beispiel, um eine Datei von einer bestimmten URL herunterzuladen und in der Datei `out.txt` im Projektverzeichnis zu speichern:

```groovy
tasks.register("download", net.fabricmc.loom.task.DownloadTask) {
    url = "https://example.com/file.txt"
    output = file("out.txt")
}
```

Du kannst auch einen erwarteten SHA-1-Hash angeben, der zur Überprüfung der Integrität der heruntergeladenen Datei verwendet wird, sowie ein maximales Alter, nach dessen Ablauf die Datei erneut heruntergeladen werden muss:

```groovy
tasks.register("download", net.fabricmc.loom.task.DownloadTask) {
    url = "https://example.com/file.txt"
    output = file("out.txt")
    sha1 = "EXPECTED-SHA1-HASH-HERE"
    maxAge = Duration.ofDays(1)
}
```

## ModEnigmaTask {#modenigma-task}

Der `net.fabricmc.loom.task.tool.ModEnigmaTask` ist eine erweiterte Aufgabe, mit der [Enigma](https://github.com/FabricMC/Enigma) für eine Mapping-Datei gestartet werden kann. Dies kann verwendet werden, um durch den Mod bereitgestellte Javadoc zu generieren.

```groovy
tasks.register("enigma", net.fabricmc.loom.task.tool.ModEnigmaTask) {
    mappingsFile = file("mappings.mapping")
}
```

## ValidateMixinNameTask {#validatemixinnametask}

Der `net.fabricmc.loom.task.ValidateMixinNameTask` ist eine Aufgabe, die verwendet werden kann, um zu überprüfen, ob der Name der Mixin-Klasse mit dem Namen der Zielklasse übereinstimmt.

```groovy
    tasks.register('validateMixinNames', net.fabricmc.loom.task.ValidateMixinNameTask) {
        source(sourceSets.main.output)
    }

    check.dependsOn "validateMixinNames"
```
