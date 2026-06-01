---
title: Fabric Loader
description: Der leichtgewichtige Mod-Loader, der das Fabric-Projekt antreibt.
authors:
  - cassiancc
  - falseresync
  - jamieswhiteshirt
  - liach
  - Llamalad7
  - SolidBlock-cn
resources:
  https://maven.fabricmc.net/docs/fabric-loader-0.19.2/: Fabric Loader 0.19.2 Javadocs
  https://wiki.fabricmc.net/documentation:entrypoint: Einstiegspunkte - Fabric Wiki
  https://github.com/FabricMC/fabric-loader: Fabric Loader auf GitHub
---

Fabric Loom ist der leichtgewichtige Mod-Loader von Fabric. Er bietet die notwendigen Werkzeuge, um jede beliebige Version von Minecraft zu erstellen. Andererseits gehören spielspezifische und versionsspezifische Hooks in die Fabric-API.

::: info

Obwohl der Fabric Loader in erster Linie für Minecraft verwendet wird, ist es möglich, einen Spielprovider für andere Java-Anwendungen zu erstellen (zum Beispiel für Spiele wie [Slay the Spire](https://www.youtube.com/watch?v=ZaNI4OJFGTg) oder [Hytale](https://github.com/cootshk/Hybric)).

:::

Der Fabric Loader bietet Dienste, mit denen Mods während der Initialisierung Code ausführen, Klassen umwandeln sowie Mod-Abhängigkeiten deklarieren und bereitstellen können und das alles in verschiedenen Umgebungen.

Die Javadocs zur neuesten Version des Fabric Loader findest du auf der [Develop](https://fabricmc.net/develop/)-Website.

Die aktuelle Instanz des Fabric Loader kann mit `FabricLoader.getInstance()` abgerufen werden. Beispielsweise kann mit `FabricLoader.getInstance().isModLoaded` überprüft werden, ob ein anderer Mod ausgeführt wird.

## Mods {#mods}

Ein Mod ist eine JAR, die im Stammverzeichnis eine Metadatendatei namens [`fabric.mod.json`](./fabric-mod-json) enthält, in der festgelegt ist, wie der Mod geladen werden soll. Diese Datei enthält eine Mod-ID und eine Versionsnummer sowie [Einstiegspunkte](../getting-started/project-structure#entrypoints) und Mixin-Konfigurationen.

Die Mod-ID identifiziert den Mod, und zwei Mods mit derselben ID gelten als ein und derselbe Mod. Es kann jeweils nur eine Version eines Mods geladen werden.

Ein Mod kann auch angeben, von welchen anderen Mods er abhängt oder mit welchen er in Konflikt steht. Fabric Loader versucht, die Abhängigkeiten zu erfüllen und die entsprechenden Mod-Versionen zu laden; sollte dies nicht gelingen, kann das Spiel nicht gestartet werden.

Mit dem Fabric Loader können alle Mods das Spiel auf die gleiche Art verändern. So kann beispielsweise alles, was die Fabric-API macht, auch von jedem anderen Mods gemacht werden.

Mods werden sowohl aus dem Klassenpfad als auch aus dem Verzeichnis `mods` geladen. Dieses Verzeichnis kann über die Systemeigenschaft `fabric.modsFolder` geändert werden.

## Verschachtelte JARs {#nested-jars}

::: info

Durch die Verwendung der Option `include` von Fabric Loom wird die Einbettung der JAR automatisch abgewickelt, einschließlich der Erstellung einer `fabric.mod.json`-Datei für Nicht-Mod-JARs.

:::

Durch verschachtelte JARs kann ein Mod seine eigenen Abhängigkeiten bereitstellen, sodass der Fabric Loader beim Versuch, die Abhängigkeiten zu erfüllen, die beste Version finden kann, anstatt dass diese getrennt installiert werden müssen.

Verschachtelte JARs verhalten sich wie jeder andere Mod, verfügen über eine eigene Metadatendatei, sind jedoch in der übergeordneten JAR enthalten. Beachte, dass verschachtelte Mods ihrerseits auf dieselbe Weise weitere untergeordnete Elemente einbetten können.

Verschachtelte JARs werden beim Ausführen des Spiels auf die Festplatte extrahiert. Pfade zu verschachtelten JARs müssen relativ zum Stammverzeichnis der sie enthaltenden JAR angegeben werden.

## Einstiegspunkte {#entrypoints}

Der Fabric Loader nutzt ein auf [Einstiegspunkte](../getting-started/project-structure#entrypoints) basierendes System, mit dem bestimmter Code zur Initialisierung für Fabric Loader oder für andere Mods verfügbar gemacht wird.

Initialisierer werden früh während des Spielstarts geladen und aufgerufen, wodurch ein Mod seine Änderungen per Code vornehmen kann. Diese Einstiegspunkte werden in der Regel dazu verwendet, Mods zu initialisieren, indem Registrierungsobjekte, Event Listeners und andere Callbacks für spätere Aktionen registriert werden.

## Mixin {#mixin}

Mit Mixins können Mods Minecraft-Klassen und sogar Klassen anderer Mods umwandeln und sie sind die einzige Art der Klassenumwandlung, die der Fabric Loader offiziell unterstützt. Ein Mod kann eine eigene Mixin-Konfiguration definieren, was die Verwendung von Mixin ermöglicht.

Der Fabric Loader verwendet eine modifizierte Abspaltung des ursprünglichen Sponge-Mixin. Das [übergeordnete Mixin-Wiki](https://github.com/SpongePowered/Mixin/wiki) ist jedoch nach wie vor weitgehend gültig.

Zu den Änderungen durch Fabric gehören unter anderem: Die Zulassung aller Standard-Injektionspunkte innerhalb von Konstruktoren, die Optimierung nicht verwendeter Callback-Informationen, Korrekturen zur Gewährleistung der Abwärtskompatibilität, die Behebung von statischen Kopien sowie die Zulassung von Injektoren in Interfaces und vieles mehr.

## Mappings {#mappings}

::: info

Mappings sind nur relevant, wenn der Fabric Loader bei verschleierten Spielen verwendet wird, einschließlich Minecraft-Versionen vor 26.1.

:::

Fabric Loader stellt die `MappingResolver`-API bereit, um die Namen von Klassen, Feldern und Methoden in Abhängigkeit von den verschiedenen Umgebungen zu ermitteln, in denen Mods geladen werden können. Dies kann zur Unterstützung der Reflection in jeder Umgebung genutzt werden, sofern der Fabric Loader Zugriff auf Mappings hat, um den Namen aufzulösen.

```java
FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "net.minecraft.class_5421") // Resolves to `RecipeBookType` on named versions of 1.21.11
```

Wenn der Fabric Loader in einer Nicht-Entwicklungsumgebung mit einem verschleierten Spiel gestartet wird, ordnet er die Spiel-JAR-Dateien [neu zu](../porting/mappings/index#mappings) und vergibt ihnen Zwischennamen. Mods, die für verschleierte Spiele entwickelt wurden, sollen auf Intermediary abgebildet werden, das mit dieser Umgebung kompatibel sein wird. Die neu zugeordneten JARs werden zwischengespeichert und unter `${gameDir}/.fabric/remappedJars/${minecraftVersion}` gespeichert, damit sie über Starts hinweg wiederverwendet werden können.
