---
title: Quellcode in IntelliJ IDEA generieren
description: Ein Leitfaden zum Generieren von Minecraft Quellcode in IntelliJ IDEA.
authors:
  - dicedpixels
prev:
  text: Das Spiel in IntelliJ IDEA ausführen
  link: ./launching-the-game
next:
  text: Tipps und Tricks für IntelliJ IDEA
  link: ./tips-and-tricks
---

Mit der Fabric-Toolchain kannst du auf den Quellcode von Minecraft zugreifen, indem du ihn lokal generierst, und du kannst IntelliJ IDEA verwenden, um bequem darin zu navigieren. Um den Quellcode zu generieren, musst du die `genSources`-Gradle-Aufgabe ausführen.

Dies kann wie oben beschrieben in der Gradle-Ansicht erfolgen, indem du die `genSources`-Aufgabe unter **Tasks** > **`fabric`** ausführst:
![„genSources“-Aufgabe in der Gradle-Ansicht](/assets/develop/getting-started/intellij/gradle-gensources.png)

Oder du kannst auch den Befehl vom Terminal aus ausführen:

```sh:no-line-numbers
./gradlew genSources
```

![genSources-Aufgabe im Terminal](/assets/develop/getting-started/intellij/terminal-gensources.png)

## Quellcode anhängen {#attaching-sources}

IntelliJ erfordert einen zusätzlichen Schritt, um den generierten Quellcode an das Projekt anzuhängen.

Um dies zu tun, öffne eine Minecraft Klasse. Du kannst mit <kbd>Strg</kbd> + Klick zur Definition springen, wodurch die Klasse geöffnet wird, oder verwende "Search everywhere", um eine Klasse zu öffnen.

Lasst uns `MinecraftServer.class` als Beispiel öffnen. Du solltest jetzt oben ein blaues Banner mit dem Link "**Quellen auswählen...**" sehen.

![Quellcode auswählen](/assets/develop/getting-started/intellij/choose-sources.png)

Klicke auf "**Choose Sources...**", um einen Dialog zur Dateiauswahl zu öffnen. Dieser Dialog wird im Standard den richtigen Ort des generierten Quellcodes öffnen.

Wähle die Datei, die mit **`-sources`** endet und drücke **Öffnen** um die Auswahl zu bestätigen.

![Quellcode Auswählen Dialog](/assets/develop/getting-started/intellij/choose-sources-dialog.png)

Du solltest nun in der Lage sein, nach Referenzen zu suchen. Wenn du einen Mapping-Satz verwendest, der Javadocs enthält, wie [Parchment](https://parchmentmc.org/) (für Mojang Mappings) oder Yarn, solltest du nun auch Javadocs sehen.

![Javadoc Kommentare im Quellcode](/assets/develop/getting-started/intellij/javadoc.png)
