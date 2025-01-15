---
title: 在 Linux 上安裝 Java
description: 在 Linux 上安裝 Java 的逐步指南。
authors:
  - IMB11
---

# 在 Linux 上安裝 Java

本指南將引導你在 Linux 上安裝 Java 21。

## 1. 檢查 Java 是否已安裝 {#1-check-if-java-is-already-installed}

開啟終端機，輸入 `java -version`，然後按下 <kbd>Enter</kbd> 鍵。

![終端機中輸入了「java -version」](/assets/players/installing-java/linux-java-version.png)

:::warning
要使用 Minecraft 1.21，你至少需要安裝 Java 21。 如果這個指令顯示任何低於 21 的版本，則需要更新現有的 Java 安裝。
:::

## 2. 下載和安裝 Java 21 {#2-downloading-and-installing-java}

我們建議使用 OpenJDK 21，它適用於大部分 Linux 發行版。

### Arch Linux

:::info
有關在 Arch Linux 上安裝 Java 的更多資訊，請參閱 [Arch Linux Wiki](https://wiki.archlinuxcn.org/wiki/Java)。
:::

你可以從官方儲存庫安裝最新的 JRE：

```sh
sudo pacman -S jre-openjdk
```

如果你在沒有圖形化介面的伺服器上執行，則可以安裝無頭版本：

```sh
sudo pacman -S jre-openjdk-headless
```

如果你打算開發模組，則需要 JDK：

```sh
sudo pacman -S jdk-openjdk
```

### Debian／Ubuntu

你可以使用 `apt` 執行以下指令來安裝 Java 21：

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora

你可以使用 `dnf` 執行以下指令來安裝 Java 21：

```sh
sudo dnf install java-21-openjdk
```

如果你不需要圖形化介面，則可以安裝無頭版本：

```sh
sudo dnf install java-21-openjdk-headless
```

如果你打算開發模組，則需要 JDK：

```sh
sudo dnf install java-21-openjdk-devel
```

### 其他 Linux 發行版 {#other-linux-distributions}

如果你的發行版未在上述清單中，你可以從 [Eclipse Temurin](https://adoptium.net/temurin/) 下載最新的 JRE。

如果你打算開發模組，你應參考另一個指南。

## 3. 驗證 Java 21 是否已安裝 {#3-verify-that-java-is-installed}

安裝完成後，你可以透過開啟終端機並輸入 `java -version` 來驗證 Java 21 是否已安裝。

如果這個命令成功執行，你將看到類似於以前顯示的內容，其中顯示了 Java 版本：

![終端機中輸入了「java -version」](/assets/players/installing-java/linux-java-version.png)
