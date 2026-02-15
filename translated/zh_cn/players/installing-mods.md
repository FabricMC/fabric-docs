---
title: 安装模组
description: Fabric 模组安装的逐步指南。
authors:
  - IMB11
---

本指南将引导你使用 Minecraft 启动器安装 Fabric 模组。

对于第三方启动器，应该参见这些第三方启动器的文档。

## 1. 下载模组{#1-download-the-mod}

::: warning

你应该只从你信任的来源下载模组。 关于寻找模组的更多信息，请看[寻找可信的模组](./finding-mods)指南。

:::

大多数模组都需要 Fabric API，可从 [Modrinth](https://modrinth.com/mod/fabric-api) 或 [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api) 下载。

下载模组时，请确保：

- 能在你想玩的 Minecraft 版本上运行。 例如，在 1.21.8 版本中可以正常运行的模组，在 1.21.11 中可能无法正常运行。
- 模组是用于 Fabric 的，而不是其他模组加载器。
- 此外，适用于正确的 Minecraft（Java 版）的版本。

## 2. 将模组移到 `mods` 文件夹{#2-move-the-mod-to-the-mods-folder}

各个操作系统中，模组文件夹的位置如下所示：

你通常可以将这些路径粘贴到文件资源管理器的地址栏中，以快速导航到文件夹。

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

找到 `mods` 文件夹后，就可以将模组 `.jar` 文件移入其中。

![已在模组文件夹中安装模组](/assets/players/installing-mods.png)

## 3. 大功告成！ {#3-you-re-done}

将模组移入 `mods` 文件夹后，你可以打开 Minecraft 启动器，从左下角的下拉菜单中选择 Fabric 配置文件，然后按下 `Play`！

![已选择 Fabric 配置文件的 Minecraft 启动器](/assets/players/installing-fabric/launcher-screen.png)

## 疑难解答{#troubleshooting}

如果在遵循本指南时遇到任何问题，可以在 [Fabric Discord](https://discord.fabricmc.net/) 的 `#player-support` 频道中寻求帮助。

也可以尝试阅读疑难解答页面，尝试自己解决问题：

- [崩溃报告](./troubleshooting/crash-reports)
- [上传日志](./troubleshooting/uploading-logs)
