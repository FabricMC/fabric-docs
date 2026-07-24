---
title: Das Spiel in VS Code ausführen
description: Lerne, wie man eine Minecraft-Instanz aus Visual Studio Code heraus startet.
authors:
  - dicedpixels
prev:
  text: Ein Projekt in VS Code öffnen
  link: ./opening-a-project
next:
  text: Quellcode in VS Code generieren
  link: ./generating-sources
---

Die Fabric-Toolchain lässt sich in Visual Studio Code integrieren und bietet eine bequeme Möglichkeit, eine Spielinstanz auszuführen, um deinen Mod zu testen und zu debuggen.

## Ausführungsziele generieren {#generating-launch-targets}

Um das Spiel mit aktivierter Debugging-Unterstützung auszuführen, musst du Ausführungsziele generieren, indem du die `vscode`-Gradle-Aufgabe ausführst.

Dies kann über die Gradle-Ansicht in Visual Studio Code erfolgen: Öffne diese und navigiere zu der `vscode`-Aufgabe unter **Tasks** > **`ide`**. Doppelklicke oder verwende die Schaltfläche **Run Task**, um die Aufgabe auszuführen.

![vscode-Aufgabe in der Gradle-Ansicht](/assets/develop/getting-started/vscode/gradle-vscode.png)

Alternativ kannst du das Terminal direkt verwenden: Öffne ein neues Terminal über **Terminal** > **New Terminal** und führe den folgenden Befehl aus:

```sh:no-line-numbers
./gradlew vscode
```

![vscode-Aufgabe im Terminal](/assets/develop/getting-started/vscode/terminal-vscode.png)

### Ausführungsziele verwenden {#using-launch-targets}

Sobald die Startziele generiert sind, kannst du sie verwenden, indem du die Ansicht **Run and Debug** öffnest, das gewünschte Ziel auswählst und die Schaltfläche **Start Debugging** (<kbd>F5</kbd>) drückst.

![Ausführungsziele](/assets/develop/getting-started/vscode/launch-targets.png)
