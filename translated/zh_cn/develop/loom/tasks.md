---
title: 其他任务
description: Fabric Loom 额外任务的文档。
authors:
  - modmuss50
---

以下任务默认情况下不会在 Loom 项目中注册，但可以注册以提供额外的功能。

## Fabric 模组 JSON 生成 {#fabric-mod-json}

`net.fabricmc.loom.task.FabricModJsonV1Task` 是一个可以用来为你的模组生成有效的 `fabric.mod.json` 文件的任务。 这是一个简单任务，其会输出一个文件，至于如何配置你的构建脚本，以便将该文件包含在模组的资源中，则需要你自己决定。

```groovy
tasks.register("generateModJson", net.fabricmc.loom.task.FabricModJsonV1Task) {
    outputFile = file("fabric.mod.json")

    json {
        modId = "example-mod"
        version = "1.0.0"
    }
}
```

以上示例是该任务最基本的用法，将生成一个 `fabric.mod.json` 文件，其中包含指定的模组 ID 和版本。 这个 `json` 代码块支持所有在 [Fabric Mod JSON 格式](https://wiki.fabricmc.net/documentation:fabric_mod_json)中定义的字段。 有关支持的属性的完整列表，请参见 [FabricModJsonV1Spec](https://github.com/FabricMC/fabric-loom/blob/dev/1.12/src/main/java/net/fabricmc/loom/api/fmj/FabricModJsonV1Spec.java)。

## 下载任务 {#download-task}

`net.fabricmc.loom.task.DownloadTask` 是一个简单任务，可用于将文件从 URL 下载到指定位置。

例如，要从某个 URL 下载文件并将其保存到项目目录下的 `out.txt` 文件中：

```groovy
tasks.register("download", net.fabricmc.loom.task.DownloadTask) {
    url = "https://example.com/file.txt"
    output = file("out.txt")
}
```

你还可以指定一个预期的 SHA-1 哈希值，用于验证下载文件的完整性，以及一个最大有效期，超过该有效期后，文件将不再有效，需要重新下载：

```groovy
tasks.register("download", net.fabricmc.loom.task.DownloadTask) {
    url = "https://example.com/file.txt"
    output = file("out.txt")
    sha1 = "EXPECTED-SHA1-HASH-HERE"
    maxAge = Duration.ofDays(1)
}
```

## ModEnigmaTask {#modenigma-task}

`net.fabricmc.loom.task.tool.ModEnigmaTask` 是一个高级任务，可用于针对映射文件启动 [Enigma](https://github.com/FabricMC/Enigma)。 这可以用来生成模组提供的 javadoc。

```groovy
tasks.register("enigma", net.fabricmc.loom.task.tool.ModEnigmaTask) {
    mappingsFile = file("mappings.mapping")
}
```

## ValidateMixinNameTask {#validatemixinnametask}

`net.fabricmc.loom.task.ValidateMixinNameTask` 是一个可用于验证 Mixin 类名是否与目标类名匹配的任务。

```groovy
    tasks.register('validateMixinNames', net.fabricmc.loom.task.ValidateMixinNameTask) {
        source(sourceSets.main.output)
    }

    check.dependsOn "validateMixinNames"
```
