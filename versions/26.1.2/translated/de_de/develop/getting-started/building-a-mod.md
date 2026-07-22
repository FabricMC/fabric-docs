---
title: Einen Mod bauen
description: Lerne, wie du einen Minecraft Mod baust, der geteilt oder in einer Produktivumgebung getestet werden kann.
authors:
  - cassiancc
  - cputnam-a11y
  - gdude2002
  - Scotsguy
---

Sobald dein Mod zum Testen bereit ist, kannst du ihn in eine JAR-Datei exportieren, die auf Mod-Hosting-Websites geteilt oder zum Testen deines Mods in der Produktivumgebung zusammen mit anderen Mods verwendet werden kann.

## Deine Entwicklungsumgebung wählen {#choose-your-ide}

<ChoiceComponent :choices="[
{
 name: 'IntelliJ IDEA',
 href: './intellij-idea/building-a-mod',
 icon: 'simple-icons:intellijidea',
 color: '#FE2857',
},
{
 name: 'Visual Studio Code',
 icon: 'codicon:vscode',
 color: '#007ACC',
},
]" />

## Im Terminal bauen {#terminal}

::: warning

Die Verwendung des Terminals zum Bauen eines Mods anstelle einer IDE kann zu Problemen führen, wenn deine Standard Java-Installation nicht den Anforderungen des Projekts entspricht. Für zuverlässigere Builds solltest du eine IDE verwenden, mit der du die richtige Java-Version einfach festlegen kannst.

:::

Öffne ein Terminal aus demselben Verzeichnis wie das Mod-Projektverzeichnis und führe den folgenden Befehl aus:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat build
```

```sh:no-line-numbers [macOS/Linux]
./gradlew build
```

:::

Die JAR-Dateien sollten im Ordner `build/libs` deines Projekts erscheinen. Verwende außerhalb der Entwicklung die JAR-Datei mit dem kürzesten Namen.

## Installieren und Teilen {#installing-and-sharing}

Von dort aus kann der Mod [wie gewohnt installiert werden](../../players/installing-mods) oder auf vertrauenswürdige Mod-Hosting-Seiten wie [CurseForge](https://www.curseforge.com/minecraft) und [Modrinth](https://modrinth.com/discover/mods) hochgeladen werden.
