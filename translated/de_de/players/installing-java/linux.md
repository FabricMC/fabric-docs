---
title: Java auf Linux installieren
description: Eine Schritt-für-Schritt-Anleitung zur Installation von Java auf Linux.
authors:
  - IMB11
---

# Java auf Linux installieren

Diese Anleitung führt Sie durch die Installation von Java 21 auf Linux.

## 1. Überprüfen, ob Java bereits installiert ist

Öffne ein Terminal und gib `java -version` ein, drücke anschließend <kbd>Enter</kbd>.

![Kommandozeile mit "java -version"](/assets/players/installing-java/linux-java-version.png)

:::warning
Um Minecraft 1.21 zu verwenden, muss mindestens Java 21 installiert sein. Wenn dieser Befehl eine niedrigere Version als 21 anzeigt, musst du deine bestehende Java-Installation aktualisieren.
:::

## 2. Herunterladen und Installieren von Java 21 {#2-downloading-and-installing-java}

Wir empfehlen die Verwendung von OpenJDK 21, das für die meisten Linux-Distributionen verfügbar ist.

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

Du kannst Java 21 mit `apt` mit den folgenden Befehlen installieren:

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora

Du kannst Java 21 über `dnf` mit dem folgenden Befehlen installieren:

```sh
sudo dnf install java-21-openjdk
```

Wenn du keine grafische Oberfläche benötigst, kannst du stattdessen die Headless-Version installieren:

```sh
sudo dnf install java-21-openjdk-headless
```

Wenn du planst Mods zu entwickeln, brauchst du stattdessen die JDK:

```sh
sudo dnf install java-21-openjdk-devel
```

### Andere Linux Distributionen

Wenn deine Distribution oben nicht gelistet ist, kannst du die aktuellste JRE von [Adoptium](https://adoptium.net/temurin/) herunterladen

Du solltest einen alternativen Leitfaden für deine Distribution verwenden, wenn du Mods entwickeln willst.

## 3. Verifiziere, dass Java 21 installiert ist {#3-verify-that-java-is-installed}

Sobald die Installation abgeschlossen ist, kannst du überprüfen, ob Java 21 installiert ist, indem du ein Terminal öffnest und `java -version` eingibst.

Wenn der Befehl erfolgreich ausgeführt wird, wird die Java-Version wie zuvor gezeigt angezeigt:

![Kommandozeile mit "java -version"](/assets/players/installing-java/linux-java-version.png)
