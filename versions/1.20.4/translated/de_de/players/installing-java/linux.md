---
title: Java auf Linux installieren
description: Eine Schritt-für-Schritt-Anleitung zur Installation von Java auf Linux.
authors:
  - IMB11

search: false
---

# Java auf Linux installieren

Diese Anleitung führt Sie durch die Installation von Java 17 auf Linux.

## 1. Überprüfen, ob Java bereits installiert ist

Öffne ein Terminal und gib `java -version` ein, drücke anschließend <kbd>Enter</kbd>.

![Kommandozeile mit "java -version"](/assets/players/installing-java/linux-java-version.png)

:::warning
Um den Großteil der modernen Minecraft-Versionen nutzen zu können, musst du Java 17 installiert haben. Wenn der Befehl eine Version niedriger als 17 anzeigt, musst du deine bestehende Java-Installation aktualisieren.
:::

## 2. Java 17 herunterladen und installieren

Wir empfehlen OpenJDK 17, welches auf den meisten Linux-Distributionen verfügbar ist.

### Arch Linux

:::info
Für mehr Informationen über die Installation von Java auf Arch Linux, schaue in das [Arch Linux Wiki](https://wiki.archlinux.org/title/Java).
:::

Du kannst die aktuellste JRE aus den offiziellen Repositories installieren:

```sh
sudo pacman -S jre-openjdk
```

Wenn du einen Server betreibst, für den du keine grafische Oberfläche benötigst, kannst du stattdessen die Headless-Version installieren:

```sh
sudo pacman -S jre-openjdk-headless
```

Wenn du planst Mods zu entwickeln, brauchst du stattdessen die JDK:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu

Du kannst Java 17 über `apt` mit dem folgenden Befehl installieren:

```sh
sudo apt update
sudo apt install openjdk-17-jdk
```

### Fedora

Du kannst Java 17 über `dnf` mit dem folgenden Befehl installieren:

```sh
sudo dnf install java-17-openjdk
```

Wenn du keine grafische Oberfläche benötigst, kannst du stattdessen die Headless-Version installieren:

```sh
sudo dnf install java-17-openjdk-headless
```

Wenn du planst Mods zu entwickeln, brauchst du stattdessen die JDK:

```sh
sudo dnf install java-17-openjdk-devel
```

### Andere Linux Distributionen

Wenn deine Distribution oben nicht gelistet ist, kannst du die aktuellste JRE von [Adoptium](https://adoptium.net/temurin/) herunterladen

Du solltest einen alternativen Leitfaden für deine Distribution verwenden, wenn du Mods entwickeln willst.

## 3. Verifizieren, dass Java 17 installiert ist

Sobald die Installation abgeschlossen ist, kannst du überprüfen, ob Java 17 installiert ist, indem du die Kommandozeile erneut öffnest und "java -version" eingibst.

Wenn der Befehl erfolgreich ausgeführt wird, wird die Java-Version wie zuvor gezeigt angezeigt:

![Kommandozeile mit "java -version"](/assets/players/installing-java/linux-java-version.png)
