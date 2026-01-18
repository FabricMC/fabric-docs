---
title: Tipps und Tricks für VS Code
description: Nützliche Tipps und Tricks, umd eine Arbeit zu erleichtern.
authors:
  - dicedpixels
prev:
  text: Quellcode in VS Code generieren
  link: ./generating-sources
next: false
---

Es ist wichtig zu lernen, wie man die Generierung des Quellcodes durchläuft, damit du debuggen und ein Verständnis für die inneren Abläufe von Minecraft entwickeln kannst. Hier beschreiben wir einige gängige Anwendungen der Entwicklungsumgebung.

## Nach einer Minecraft-Klasse suchen {#searching-for-a-minecraft-class}

Sobald der Quellcode generiert ist. Es sollte möglich sein, Minecraft-Klassen zu suchen oder anzusehen.

### Klassendefinition ansehen {#viewing-class-definitions}

**Schnellöffnung** (<kbd>Ctrl</kbd>+<kbd>P</kbd>): Schreibe `#` gefolgt von dem Namen einer Klasse (z.B. `#Identifier`).

![Schnellöffnung](/assets/develop/getting-started/vscode/quick-open.png)

**Zur Definition gehen** (<kbd>F12</kbd>): Navigiere im Quellcode zu einer Klassendefinition, indem du bei gedrückter <kbd>Strg</kbd> -Taste auf den Namen klickst oder mit der rechten Maustaste darauf klickst und "Go to Definition" auswählst.

![Zur Definition gehen](/assets/develop/getting-started/vscode/go-to-definition.png)

### Referenzen finden {#finding-references}

Du kannst alle Verwendungen einer Klasse finden, indem du mit der rechten Maustaste auf einen Klassennamen und dann auf **Find All References** klickst.

![Alle Referenzen finden](/assets/develop/getting-started/vscode/find-all-references.png)

::: info

Wenn die oben genannten Funktionen nicht wie erwartet funktionieren, ist der Quellcode vermutlich nicht richtig angehängt. Dies lässt sich in der Regel durch Bereinigen des Arbeitsbereich-Caches beheben.

- Klicke auf die Schaltfläche **Show Java Status Menu** in der Statusleiste.

![Java Status anzeigen](/assets/develop/getting-started/vscode/java-ready.png)

- Klicke im gerade geöffneten Menü auf **Clean Workspace Cache...** und bestätige den Vorgang.

![Workspace leeren](/assets/develop/getting-started/vscode/clear-workspace.png)

- Schließe und öffne das Projekt erneut.

:::

## Bytecode ansehen {#viewing-bytecode}

Das Anzeigen von Bytecode ist beim Schreiben von Mixins erforderlich. Allerdings fehlt Visual Studio Code die native Unterstützung für die Anzeige von Bytecode, und die wenigen Erweiterungen, die diese hinzufügen, funktionieren möglicherweise nicht.

In diesem Fall kannst du den in Java integrierten Befehl `javap` verwenden, um den Bytecode anzuzeigen.

- **Suche den Pfad zu der Minecraft JAR:**

  Öffne die Explorer-Ansicht und erweitere den Abschnitt **Java-Projekte**. Erweitere den Knoten **Reference Libraries** in der Projektstruktur und suche eine JAR-Datei, deren Name `minecraft-` enthält. Klicken mit der rechten Maustaste auf die JAR-Datei und kopiere den vollständigen Pfad.

  Es könnte etwa wie folgt aussehen:

  ```:no-line-numbers
  C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar
  ```

![Pfad kopieren](/assets/develop/getting-started/vscode/copy-path.png)

- **`javap` ausführen:**

  Du kannst dann `javap` ausführen, indem du den oben genannten Pfad als `cp` (Klassenpfad) und den vollqualifizierten Klassennamen als letztes Argument angibst.

  ```sh
  javap -cp C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar -c -private net.minecraft.util.Identifier
  ```

  Dies wird den Bytecode in deine Terminalausgabe ausgegeben.

![javap](/assets/develop/getting-started/vscode/javap.png)
