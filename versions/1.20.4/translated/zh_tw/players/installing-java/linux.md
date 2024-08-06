---
title: 在 Linux 上安裝 Java
description: 在 Linux 上安裝 Java 的逐步指南。
authors:
  - IMB11

search: false
---

# 在 Linux 上安裝 Java

這個指南將引導你在 Linux 上安裝 Java 17。

## 1. 驗證 Java 是否安裝

開啟終端機，輸入 `java -version`，然後按下 <kbd>Enter</kbd> 鍵。

![終端機中輸入了「java -version」](/assets/players/installing-java/linux-java-version.png)

:::warning
要使用大多數現代的 Minecraft，你需要至少安裝 Java 17。 如果這個命令顯示低於 17 的任何版本，你需要更新現有的 Java 安裝。 如果你打算開發模組，你應參考另一個指南。 如果你打算開發模組，你應參考另一個指南。 如果你打算開發模組，你應參考另一個指南。
:::

## 2. 下載並安裝 Java 17

我們建議使用 OpenJDK 17，在大多數 Linux 發行版中都可用。

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

你可以使用 apt 安裝 Java 17，使用以下命令：

```sh
sudo apt update
sudo apt install openjdk-17-jdk
```

### Fedora

你可以使用 dnf 安裝 Java 17，使用以下命令：

```sh
sudo dnf install java-17-openjdk
```

如果你不需要圖形化介面，則可以安裝無頭版本：

```sh
sudo dnf install java-17-openjdk-headless
```

如果你打算開發模組，則需要 JDK：

```sh
sudo dnf install java-17-openjdk-devel
```

### 其他 Linux 發行版

如果你的發行版未在上述清單中，你可以從 [Eclipse Temurin](https://adoptium.net/temurin/) 下載最新的 JRE。

如果你打算開發模組，你應參考另一個指南。

## 3. 驗證 Java 17 是否安裝

安裝完成後，你可以開啟終端機，輸入 `java -version` 來驗證是否安裝了 Java 17。

如果這個命令成功執行，你將看到類似於以前顯示的內容，其中顯示了 Java 版本：

![終端機中輸入了「java -version」](/assets/players/installing-java/linux-java-version.png)
