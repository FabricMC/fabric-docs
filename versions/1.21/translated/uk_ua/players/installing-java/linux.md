---
title: Встановлення Java у Linux
description: Покрокова інструкція щодо встановлення Java у Linux.
authors:
  - IMB11
---

# Встановлення Java у Linux {#installing-java-on-linux}

Цей посібник допоможе вам встановити Java 21 на Linux.

## 1. Перевірте, чи Java вже встановлено {#1-check-if-java-is-already-installed}

Відкрийте термінал, введіть `java -version` і натисніть <0>Enter</0>.

![Термінал із введеним "java -version"](/assets/players/installing-java/linux-java-version.png)

:::warning
Щоб використовувати Minecraft 1.21, вам знадобиться встановити принаймні Java 21. Якщо ця команда показує будь-яку версію, нижчу за 21, вам потрібно буде оновити встановлювач Java.
:::

## 2. Завантажити встановлювач Java 21 {#2-downloading-and-installing-java}

Ми рекомендуємо використовувати OpenJDK 21, який доступний для більшості дистрибутивів Linux.

### Arch Linux {#arch-linux}

:::info
Щоб отримати додаткові відомості про встановлення Java на Arch Linux, перегляньте [Arch Linux Wiki](https://wiki.archlinux.org/title/Java).
:::

Ви можете встановити останню версію JRE з офіційних репозиторіїв:

```sh
sudo pacman -S jre-openjdk
```

Якщо ви працюєте на сервері без графічного інтерфейсу, замість цього можна встановити версію headless:

```sh
sudo pacman -S jre-openjdk-headles
```

Якщо ви плануєте розробляти моди, замість цього вам знадобиться JDK:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu {#debian-ubuntu}

You can install Java 21 using `apt` with the following commands:

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora {#fedora}

Ви можете встановити Java 21 за допомогою `dnf` за допомогою таких команд:

```sh
sudo dnf install java-21-openjdk
```

Якщо вам не потрібен графічний інтерфейс, ви можете замість нього встановити версію headless:

```sh
sudo dnf install java-21-openjdk-headless
```

Якщо ви плануєте розробляти моди, замість цього вам знадобиться JDK:

```sh
sudo dnf install java-21-openjdk-devel
```

### Інші дистрибутиви Linux {#other-linux-distributions}

Якщо вашого дистрибутиву немає в списку вище, ви можете завантажити останню версію JRE з [Adoptium](https://adoptium.net/temurin/)

Вам слід звернутися до альтернативного посібника для вашого дистрибутиву, якщо ви плануєте розробляти моди.

## 3. Перевірте, чи встановлено Java 21 {#3-verify-that-java-is-installed}

Перевірте, чи встановлено Java 21 {#3-verify-that-java-is-installed}.

Якщо команда виконана успішно, ви побачите щось подібне до показаного раніше, де показана версія Java:

![Термінал із введеним "java -version"](/assets/players/installing-java/linux-java-version.png)
