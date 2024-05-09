---
title: 安装模组
description: Fabric 模组安装的逐步指南
authors:
  - IMB11
---

# 安装模组

这个指南将引导你使用 Minecraft 启动器安装 Fabric 模组。

如您使用第三方启动器，您应参见它们的使用说明。

## 1. 下载模组

:::warning
你应该只从你信任的来源下载模组。 有关查找模组的更多信息，请参阅[查找可信模组](./finding-mods.md)指南。
:::

大多数模组都需要 Fabric API，可从 [Modrinth](https://modrinth.com/mod/fabric-api) 或 [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api) 下载。

下载模组时，请确保：

- 它们能在你想玩的 Minecraft 版本上运行。 例如，一个在 1.20 上运行的模组可能无法在 1.20.2 上运行。
- 它们是用于 Fabric 的，而不是其他模组加载器。
- 此外，它们适用于正确的 Minecraft 版本（Java 版）。

## 2. 将模组移动至 `mods` 文件夹

各个操作系统中，模组文件夹的位置如下所示：

你通常可以将这些路径粘贴到文件资源管理器的地址栏中，以快速导航到文件夹。

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

找到 `mods` 文件夹后，就可以将模组 `.jar` 文件移入其中。

![已在模组文件夹中安装模组](/assets/players/installing-mods.png)

## 3. 大功告成！

将模组移入 `mods` 文件夹后，你可以打开 Minecraft 启动器，从左下角的下拉菜单中选择 Fabric 配置文件，然后按下 `Play`！

![选择了 Fabric 配置的官方启动器](/assets/players/installing-fabric/launcher-screen.png)

## 疑难解答

如果您在遵循本指南时遇到任何问题，可以在 [Fabric Discord](https://discord.gg/v6v4pMv) 的 `#player-support` 频道中寻求帮助。

您也可以阅读疑难解答页面，尝试自己疑难解答：

- [崩溃报告](./troubleshooting/crash-reports.md)
- [上传日志](./troubleshooting/uploading-logs.md)
