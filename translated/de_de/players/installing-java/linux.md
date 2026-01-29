---
title: Java auf Linux installieren
description: Ein Schritt-für-Schritt-Leitfaden zur Installation von Java auf Linux.
authors:
  - IMB11
next: false
---

Diese Anleitung führt Sie durch die Installation von Java 21 auf Linux.

Der Minecraft Launcher kommt bereits mit seiner eigenen Java Installation, diese Sektion ist also nur relevant, wenn du den auf der Fabric `.jar` basierenden Installer oder die Minecraft Server `.jar` verwenden möchtest.

## 1. Überprüfen, ob Java bereits installiert ist {#1-check-if-java-is-already-installed}

Öffne ein Terminal und gib `java -version` ein, drücke anschließend <kbd>Enter</kbd>.

![Kommandozeile mit "java -version"](/assets/players/installing-java/linux-java-version.png)

::: warning

Um Minecraft 1.21.11 zu verwenden, musst du mindestens Java 21 installiert haben.

Wenn dieser Befehl, eine Version kleiner als 21 anzeigt, musst du deine existierende Java Installation aktualisieren; lies diese Seite weiter.

:::

## 2. Herunterladen und Installieren von Java 21 {#2-downloading-and-installing-java}

Wir empfehlen die Verwendung von OpenJDK 21, das für die meisten Linux-Distributionen verfügbar ist.

### Arch Linux {#arch-linux}

::: info

Für mehr Informationen über die Installation von Java auf Arch Linux, schaue in das [Arch Linux Wiki](https://wiki.archlinux.org/title/Java).

:::

Du kannst die aktuellste JRE aus den offiziellen Repositories installieren:

```sh
sudo pacman -S jre-openjdk
```

Wenn du einen Server betreibst, der keine grafische Benutzeroberfläche benötigt, kannst du stattdessen die Headless-Version installieren:

```sh
sudo pacman -S jre-openjdk-headless
```

Wenn du planst Mods zu entwickeln, brauchst du stattdessen die JDK:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu {#debian-ubuntu}

Du kannst Java 21 mit `apt` mit den folgenden Befehlen installieren:

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora {#fedora}

Du kannst Java 21 über `dnf` mit dem folgenden Befehlen installieren:

```sh
sudo dnf install java-21-openjdk
```

Wenn du keine grafische Benutzeroberfläche benötigst, kannst du die Headless-Version installieren:

```sh
sudo dnf install java-21-openjdk-headless
```

Wenn du planst Mods zu entwickeln, brauchst du stattdessen die JDK:

```sh
sudo dnf install java-21-openjdk-devel
```

### Andere Linux Distributionen {#other-linux-distributions}

Wenn deine Distribution oben nicht gelistet ist, kannst du die aktuellste JRE von [AdoptOpenJDK](https://adoptium.net/en-GB/temurin.html) herunterladen

Du solltest einen alternativen Leitfaden für deine Distribution verwenden, wenn du Mods entwickeln willst.

## 3. Verifiziere, dass Java 21 installiert ist {#3-verify-that-java-is-installed}

Sobald die Installation abgeschlossen ist, kannst du überprüfen, ob Java 21 installiert ist, indem du ein Terminal öffnest und `java -version` eingibst.

Wenn der Befehl erfolgreich ausgeführt wird, wird die Java-Version wie zuvor gezeigt angezeigt:

![Kommandozeile mit "java -version"](/assets/players/installing-java/linux-java-version.png)
