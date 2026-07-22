---
title: 在 macOS 上安装 Fabric
description: 在 macOS 上安装 Fabric 的逐步指南。
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info 前置条件

运行 `.jar` 之前，你可能需要[安装 Java](../installing-java/macos)。

:::

<!-- #region common -->

## 1. 下载 Fabric 安装程序 {#1-download-the-fabric-installer}

从 [Fabric 网站](https://fabricmc.net/use/)下载 Fabric 安装程序的 `.jar` 版本，单击 `Download installer (Universal/.JAR)`。

## 2. 运行 Fabric 安装程序 {#2-run-the-fabric-installer}

请先关闭 Minecraft 和 Minecraft 启动器，然后再运行安装程序。

::: tip

你可能会收到一条警告，提示 Apple 无法验证 `.jar` 文件。 要绕过此警告，请打开系统设置 > 隐私和安全，然后单击 `仍然打开`。 如果出现提示，请确认并输入你的管理员密码。

![macOS 系统设置](/assets/players/installing-fabric/macos-settings.png)

:::

打开安装程序后，你应该会看到类似这样的屏幕：

![高亮显示“安装”的 Fabric 安装程序](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

选择你想要的 Minecraft 版本，然后单击 `Install`。 请确保勾选 `Create Profile`。

### 通过 Homebrew 安装 {#installing-via-homebrew}

如果你已经安装了 [Homebrew](https://brew.sh)，则可以改用 `brew` 安装 Fabric 安装程序：

```sh
brew install fabric-installer
```

## 3. 完成安装 {#3-finish-setup}

安装完成后，打开 Minecraft 启动器。 然后从版本下拉菜单中选择 Fabric 配置文件，然后单击“开始游戏”。

![已选择 Fabric 配置文件的 Minecraft 启动器](/assets/players/installing-fabric/launcher-screen.png)

现在你可以给游戏添加模组了。 详情请参阅[寻找可信的模组](../finding-mods)指南。

如果你有任何问题，欢迎在[Fabric Discord](https://discord.fabricmc.net/)的`#player-support`频道中提问。
