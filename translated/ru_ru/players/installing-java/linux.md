---
title: Установка Java на Linux
description: Пошаговая инструкция по установке Java на Linux.
authors:
  - IMB11
---

# Установка Java на Linux

Это руководство поможет вам установить Java 21 на Linux.

## 1. Проверьте, не установлена ли Java

Откройте терминал, впишите `java -version` и нажмите <kbd>Enter</kbd>.

![Терминал с введённой командой "java -version"](/assets/players/installing-java/linux-java-version.png)

:::warning
Для использования Minecraft 1.21 вам потребуется установить как минимум Java 21. Если эта команда отображает версию ниже 21, вам необходимо обновить существующую установку Java.
:::

## 2. Загрузка и установка Java 21 {#2-downloading-and-installing-java}

Мы рекомендуем использовать OpenJDK 21, который доступен для большинства дистрибутивов Linux.

### Arch Linux

:::info
Для дополнительной информации об установке Java в Arch Linux смотрите [википедию Arch Linux](https://wiki.archlinux.org/title/Java_\\\(Русский\\\)).
:::

Вы можете установить последнюю версию JRE из официальных репозиториев:

```sh
sudo pacman -S jre-openjdk
```

Если вы используете сервер без необходимости в графическом интерфейсе, вы можете вместо этого установить `headless` версию:

```sh
sudo pacman -S jre-openjdk-headless
```

Если вы планируете разрабатывать моды, вместо этого вам понадобится JDK:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu

Вы можете установить Java 21 с помощью `apt`, выполнив следующие команды

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora

Вы можете установить Java 21 с помощью `dnf`, выполнив следующие команды:

```sh
sudo dnf install java-21-openjdk
```

Если вам не нужен графический интерфейс, вы можете вместо этого установить `headless` версию:

```sh
sudo dnf install java-21-openjdk-headless
```

Если вы планируете разрабатывать моды, вместо этого вам понадобится JDK:

```sh
sudo dnf install java-21-openjdk-devel
```

### Другие дистрибутивы Linux

Если вашего дистрибутива нет в списке выше, вы можете загрузить последнюю версию JRE с [Adoptium](https://adoptium.net/temurin/)

Вам следует обратиться к альтернативному гайду для вашего дистрибутива, если вы планируете разрабатывать моды.

## 3. Проверьте, установлена ​​ли Java 21 {#3-verify-that-java-is-installed}

После завершения установки вы можете проверить, установлена ​​ли Java 21, открыв терминал и введя `java -version`.

Если команда будет выполнена успешно, вы увидите что-то вроде показанного ранее, где отображается версия Java:

![Терминал с введённой командой "java -version"](/assets/players/installing-java/linux-java-version.png)
