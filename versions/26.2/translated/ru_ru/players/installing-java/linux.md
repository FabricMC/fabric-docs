---
title: Установка Java на Linux
description: Пошаговая инструкция по установке Java на Linux.
authors:
  - IMB11
next: false
---

Это руководство проведёт вас через процесс установки Java 25 на Linux.

Minecraft Launcher включает свою установку Java, так что этот раздел имеет значение если вы желаете использовать установщик Fabric основанный на `.jar`, или если вы желаете использовать `.jar` сервера Minecraft.

## 1. Проверьте наличие уже установленной Java {#1-check-if-java-is-already-installed}

Откройте терминал, впишите `java -version` и нажмите <kbd>Enter</kbd>.

![Терминал с введённой командой "java -version"](/assets/players/installing-java/linux-java-version.png)

::: warning

Чтобы использовать Minecraft 26.1, вам потребуется как минимум установленная Java 25.

Если эта команда показывает версию ниже 25, вам потребуется обновить текущую установку Java; продолжайте читать эту страницу.

:::

## 2. Скачивание и установка Java 25 {#2-downloading-and-installing-java}

Мы рекомендуем использовать OpenJDK 25, который доступен для большинства дистрибутивов Linux.

### Arch Linux {#arch-linux}

::: info

Для дополнительной информации об установке Java в Arch Linux смотрите [википедию Arch Linux](https://wiki.archlinux.org/title/Java).

:::

Вы можете установить последнюю версию JRE из официальных репозиториев:

```sh
sudo pacman -S jre-openjdk
```

Если вы используете сервер без графического пользовательского интерфейса, вместо этого вы можете установить headless версию:

```sh
sudo pacman -S jre-openjdk-headless
```

Если вы планируете разрабатывать моды, вместо этого вам понадобится JDK:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu {#debian-ubuntu}

Вы можете установить Java 25 с помощью `apt`, используя следующие команды:

```sh
sudo apt update
sudo apt install openjdk-25-jdk
```

### Fedora {#fedora}

Вы можете установить Java 25 с помощью `dnf`, используя следующие команды:

```sh
sudo dnf install java-25-openjdk
```

Если вам не нужен графический пользовательский интерфейс, вместо этого вы можете установить headless версию:

```sh
sudo dnf install java-25-openjdk-headless
```

Если вы планируете разрабатывать моды, вместо этого вам понадобится JDK:

```sh
sudo dnf install java-25-openjdk-devel
```

### Другие дистрибутивы Linux {#other-linux-distributions}

Если вашего дистрибутива нет в списке выше, вы можете скачать последнюю версию JRE с [Adoptium](https://adoptium.net/installation/linux)

Вам следует обратиться к альтернативному гайду для вашего дистрибутива, если вы планируете разрабатывать моды.

## 3. Проверьте, что Java 25 установлена {#3-verify-that-java-is-installed}

После завершения установки вы можете проверить, установлена ли Java 25, открыв терминал и введя `java -version`.

Если команда будет выполнена успешно, вы увидите что-то вроде показанного ранее, где отображается версия Java:

![Терминал с введённой командой "java -version"](/assets/players/installing-java/linux-java-version.png)
