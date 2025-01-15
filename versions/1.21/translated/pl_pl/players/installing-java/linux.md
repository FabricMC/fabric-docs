---
title: Instalowanie oprogramowania Java na Linux
description: Przewodnik krok po kroku o tym, jak zainstalować oprogramowanie Java na Linux.
authors:
  - IMB11
---

# Instalowanie oprogramowania Java na Linux {#installing-java-on-linux}

Ten przewodnik pokaże ci, jak zainstalować Javę 21 na systemie Linux.

## 1. Sprawdź, czy nie masz już zainstalowanej Javy {#1-check-if-java-is-already-installed}

Otwórz terminal, wpisz `java -version` i naciśnij <kbd>Enter</kbd>.

![Terminal z wpisanym poleceniem "java -version"](/assets/players/installing-java/linux-java-version.png)

:::warning
Aby używać Minecrafta w wersji 1.21, musisz mieć zainstalowaną co najmniej Javę 21. Jeśli to polecenie wyświetla wersję mniejszą niż 21, wymagane będzie zaktualizowanie istniejącej instalacji Javy.
:::

## 2. Pobieranie i instalowanie Javy 21 {#2-downloading-and-installing-java}

Zalecamy wykorzystanie OpenJDK 21, który jest dostępny dla większości dystrybucji systemu Linux.

### Arch Linux {#arch-linux}

:::info
Więcej informacji na temat instalowania Javy na systemie Linux Arch, znajdziesz na [Arch Linux Wiki](https://wiki.archlinux.org/title/Java).
:::

Najnowszą wersję środowiska JRE możesz zainstalować z oficjalnych repozytoriów:

```sh
sudo pacman -S jre-openjdk
```

Jeśli prowadzisz serwer bez potrzeby korzystania z interfejsu graficznego, możesz zainstalować wersję headless (bez interfejsu graficznego):

```sh
sudo pacman -S jre-openjdk-headless
```

Natomiast jeśli planujesz tworzyć mody, będzie ci potrzebny JDK:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu {#debian-ubuntu}

Możesz zainstalować Javę 21 przy użyciu `apt` następującymi poleceniami:

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora {#fedora}

Możesz zainstalować Javę 21 przy użyciu `dnf` następującymi poleceniami:

```sh
sudo dnf install java-21-openjdk
```

Jeśli nie potrzebujesz interfejsu graficznego, możesz zainstalować wersję headless (bez interfejsu graficznego):

```sh
sudo dnf install java-21-openjdk-headless
```

Natomiast jeśli planujesz tworzyć mody, będzie ci potrzebny JDK:

```sh
sudo dnf install java-21-openjdk-devel
```

### Inne dystrybucje systemu Linux {#other-linux-distributions}

Jeśli twoja dystrybucja nie jest wymieniona powyżej, możesz pobrać najnowszą wersję środowiska JRE ze strony [Adoptium](https://adoptium.net/temurin/)

Jeśli planujesz tworzyć mody, zapoznaj się z alternatywnym przewodnikiem dla swojej dystrybucji.

## 3. Zweryfikuj czy Java 21 została zainstalowana {#3-verify-that-java-is-installed}

Po zakończeniu instalacji możesz zweryfikować czy Java 21 została zainstalowana, otwierając terminal i wpisując `java -version`.

Jeśli polecenie zostanie uruchomione pomyślnie, to zobaczysz coś takiego, gdzie wyświetlana jest wersja Javy:

![Terminal z wpisanym poleceniem "java -version"](/assets/players/installing-java/linux-java-version.png)
