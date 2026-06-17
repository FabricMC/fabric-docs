---
title: 在 Linux 上安装 Fabric
description: 在 Linux 上安装 Fabric 的逐步指南。
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info 前置条件

运行 `.jar` 之前，你可能需要[安装 Java](../installing-java/linux)。

:::

<!-- #region common -->

## 1. 下载 Fabric 安装程序 {#1-download-the-fabric-installer}

访问 [Fabric 网站](https://fabricmc.net/use/)，点击`Download installer (Universal/.JAR)`按钮，下载 Fabric 安装程序的`.jar`版本。

## 2. 运行 Fabric 安装程序 {#2-run-the-fabric-installer}

请先关闭 Minecraft 和 Minecraft 启动器，然后再运行安装程序。

打开终端并使用 Java 运行安装程序：

```sh
java -jar fabric-installer.jar
```

启动安装程序后，界面应如下图所示：

![高亮显示“安装”的 Fabric 安装程序](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

选择你想要的 Minecraft 版本，然后单击 `Install`。 请确保勾选 `Create Profile`。

## 3. 完成安装 {#3-finish-setup}

安装完成后，打开 Minecraft 启动器。 然后从版本下拉菜单中选择 Fabric 配置文件，然后单击“开始游戏”。

![已选择 Fabric 配置文件的 Minecraft 启动器](/assets/players/installing-fabric/launcher-screen.png)

现在你可以给游戏添加模组了。 详情请参阅[寻找可信的模组](../finding-mods)指南。

如果你有任何问题，欢迎在[Fabric Discord](https://discord.fabricmc.net/)的`#player-support`频道中提问。
