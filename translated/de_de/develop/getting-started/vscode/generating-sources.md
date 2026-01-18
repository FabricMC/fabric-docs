---
title: Quellcode in VS Code generieren
description: Ein Leitfaden zum Generieren von Minecraft Quellcode in Visual Studio Code.
authors:
  - dicedpixels
prev:
  text: Das Spiel in VS Code ausführen
  link: ./launching-the-game
next:
  text: Tipps und Tricks für VS Code
  link: ./tips-and-tricks
---

Mit der Fabric-Toolchain kannst du auf den Quellcode von Minecraft zugreifen, indem du ihn lokal generierst, und du kannst Visual Studio Code verwenden, um bequem darin zu navigieren. Um den Quellcode zu generieren, musst du die `genSources`-Gradle-Aufgabe ausführen.

Dies kann wie oben beschrieben in der Gradle-Ansicht erfolgen, indem du die `genSources`-Aufgabe unter **Tasks** > **`fabric`** ausführst:
![„genSources“-Aufgabe in der Gradle-Ansicht](/assets/develop/getting-started/vscode/gradle-gensources.png)

Oder du kannst auch den Befehl vom Terminal aus ausführen:

```sh:no-line-numbers
./gradlew genSources
```

![genSources-Aufgabe im Terminal](/assets/develop/getting-started/vscode/terminal-gensources.png)
