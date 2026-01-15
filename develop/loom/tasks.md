---
title: Other Tasks
description: Documentation for Fabric Loom's additional tasks.
authors:
  - modmuss50
---

The following tasks are not registered by default in a Loom project, but can be registered to provide additional functionality.

## Fabric Mod JSON Generation {#fabric-mod-json}

The `net.fabricmc.loom.task.FabricModJsonV1Task` is a task that can be used to generate a valid `fabric.mod.json` file for your mod. This is a simple task that outputs a file, its up to you to configure your buildscript to include the file in your mods resources however you see fit.

```groovy
tasks.register("generateModJson", net.fabricmc.loom.task.FabricModJsonV1Task) {
    outputFile = file("fabric.mod.json")

    json {
        modId = "example-mod"
        version = "1.0.0"
    }
}
```

The above example is the most basic usage of the task, and will generate a `fabric.mod.json` file with the mod id and version specified. The `json` block supports all fields defined in the [Fabric Mod JSON schema](https://wiki.fabricmc.net/documentation:fabric_mod_json). See [FabricModJsonV1Spec](https://github.com/FabricMC/fabric-loom/blob/dev/1.12/src/main/java/net/fabricmc/loom/api/fmj/FabricModJsonV1Spec.java) for a full list of supported properties.

## Download Task {#download-task}

The `net.fabricmc.loom.task.DownloadTask` is a simple task that can be used to download files from a URL to a specified location.

For example, to download a file from a certain URL and save it in `out.txt` in the project directory:

```groovy
tasks.register("download", net.fabricmc.loom.task.DownloadTask) {
    url = "https://example.com/file.txt"
    output = file("out.txt")
}
```

You can also specify an expected SHA-1 hash which will be used to verify the integrity of the downloaded file, and a maximum age to require downloading again once the file is no longer fresh:

```groovy
tasks.register("download", net.fabricmc.loom.task.DownloadTask) {
    url = "https://example.com/file.txt"
    output = file("out.txt")
    sha1 = "EXPECTED-SHA1-HASH-HERE"
    maxAge = Duration.ofDays(1)
}
```

## ModEnigmaTask {#modenigma-task}

The `net.fabricmc.loom.task.tool.ModEnigmaTask` is an advanced task that can be used to launch [Enigma](https://github.com/FabricMC/Enigma) against a mappings file. This can be used to generate mod provided javadoc.

```groovy
tasks.register("enigma", net.fabricmc.loom.task.tool.ModEnigmaTask) {
    mappingsFile = file("mappings.mapping")
}
```

## ValidateMixinNameTask {#validatemixinnametask}

The `net.fabricmc.loom.task.ValidateMixinNameTask` is a task that can be used to validate that the Mixin class name matches the name of the target class.

```groovy
    tasks.register('validateMixinNames', net.fabricmc.loom.task.ValidateMixinNameTask) {
        source(sourceSets.main.output)
    }

    check.dependsOn "validateMixinNames"
```
