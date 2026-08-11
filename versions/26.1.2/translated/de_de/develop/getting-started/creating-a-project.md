---
title: Ein Projekt erstellen
description: Eine Schritt-für-Schritt-Anleitung, wie man ein neues Mod-Projekt mit dem Fabric Template Mod Generator erstellt.
authors:
  - Cactooz
  - IMB11
  - radstevee
  - Thomas1034
resources:
  https://fabricmc.net/develop/template/: Fabric Vorlagenmod Generator
  https://fabricmc.net/develop/: Entwickeln mit Fabric Website
---

Fabric bietet eine einfache Möglichkeit, ein neues Mod-Projekt mit dem Fabric Template Mod Generator zu erstellen - wenn du möchtest, kannst du ein neues Projekt auch manuell erstellen, indem du das Beispiel-Mod-Repository verwendest, dann solltest du den Abschnitt [Manuelle Projekterstellung](#manuelle-projekterstellung) lesen.

## Erstellung eines Projekts {#generating-a-project}

Du kannst den [Fabric Template Mod Generator](https://fabricmc.net/develop/template/) verwenden, um ein neues Projekt für deine Mod zu generieren - du solltest die erforderlichen Felder ausfüllen, wie beispielsweise den Paketnamen und den Mod-Namen, sowie die Minecraft-Version, für die du entwickeln möchtest.

Der Paketname sollte kleingeschrieben, durch Punkte getrennt und eindeutig sein, um Konflikte mit den Paketen anderer Programmierer zu vermeiden. Er ist typischerweise wie eine umgedrehte Internet-Domain formatiert, beispielsweise wie `com.example.example-mod`.

:::warning WICHTIG

Stelle sicher, dass du dir deine Mod-ID merkst! Wenn du in dieser Dokumentation, insbesondere in Dateipfaden, auf `example-mod` stoßt, musst du dies durch deine eigene ersetzen.

Wenn deine Mod-ID beispielsweise **`my-cool-mod`** lautet, verwende anstelle von _`resources/assets/example-mod`_ **`resources/assets/my-cool-mod`**.

:::

![Vorschau des Generators](/assets/develop/getting-started/template-generator.png)

Wenn du entweder Kotlin oder Kotlin-Buildscripts verwendest oder Datengeneratoren hinzufügen möchtest, kannst du die entsprechenden Optionen im Abschnitt `Advanced Options` auswählen.

::: info

Die auf dieser Seite aufgeführten Code-Beispiele verwenden [die Namen aus dem nicht verschleiterten Spiel](../porting/mappings/#whats-going-on-with-mappings). Falls du bereits einen Mod hast, der andere Mappings als die von Mojang bereitgestellten verwendet, findest du weitere Informationen in unserer Dokumentation zum Thema [Portierung auf 26.1](../porting/index).

:::

![Der Abschnitt "Advanced options"](/assets/develop/getting-started/template-generator-advanced.png)

Nachdem du die erforderlichen Felder ausgefüllt hast, klickst du auf die Schaltfläche `Generate`. Der Generator erstellt dann ein neues Projekt in Form einer ZIP-Datei für dich.

Du solltest diese ZIP-Datei an einem Ort deiner Wahl entpacken und dann den entpackten Ordner in deiner Entwicklungsumgebung öffnen.

::: tip

Du solltest diese Regeln befolgen, wenn du den Pfad zu deinem Projekt auswählst:

- Vermeide Cloudspeicher-Verzeichnisse (zum Beispiel Microsoft OneDrive)
- Vermeide nicht-ASCII Zeichen (zum Beispiel Emoji, akzentuierte Buchstaben)
- Vermeide Leerzeichen

Ein Beispiel eines "guten" Pfad könnte sein: `C:\Projects\YourProjectName`

:::

## Manuelle Projekterstellung {#manual-project-creation}

:::info VORAUSSETZUNGEN

Du musst [Git](https://git-scm.com/) installiert haben, um das Beispiel-Mod-Repository klonen zu können.

:::

Wenn du den Fabric Template Mod Generator nicht verwenden kannst, kannst du ein neues Projekt manuell erstellen, indem du folgende Schritte befolgst.

Klone zunächst das Beispiel-Mod-Repository mit Git:

```sh
git clone https://github.com/FabricMC/fabric-example-mod.git example-mod
```

Dadurch wird das Repository in einen neuen Ordner mit dem Namen `example-mod` geklont.

Anschließend solltest du den Ordner `.git` aus dem geklonten Repository löschen und das Projekt öffnen. Wenn der Ordner `.git` nicht angezeigt wird, solltest du die Option zur Anzeige versteckter Dateien in deinem Dateimanager aktivieren.

### Deine IDE einrichten {#setting-up}

Sobald du das Projekt in deiner Entwicklungsumgebung geöffnet hast, sollte es automatisch die Gradle-Konfiguration des Projekts laden und die notwendigen Einrichtungsaufgaben durchführen.

Wenn du eine Benachrichtigung über ein Gradle-Build-Skript erhältst, solltest du auf die Schaltfläche `Import Gradle Project` klicken.

Weitere Informationen findest du auf der Seite [Einrichten deiner IDE](./setting-up).

### Die Vorlage bearbeiten {#modifying-the-template}

Sobald das Projekt importiert wurde, solltest du die Details des Projekts so ändern, dass sie mit deiner Mod übereinstimmen:

- Ändere die Datei `gradle.properties` des Projekts, um die Eigenschaften `maven_group` und `archive_base_name` an die Details deiner Mod anzupassen.
- Ändere die Datei `fabric.mod.json`, um die Eigenschaften `id`, `name` und `description` an die Details deiner Mod anzupassen.
- Stelle sicher, die Versionen von Minecraft, den Mappings, dem Loader und Loom - die alle über die [Develop-Website](https://fabricmc.net/develop/) abgefragt werden können - so anzupassen, dass sie mit den von dir gewünschten Zielversionen übereinstimmen.

Du kannst dann den Paketnamen und die Hauptklasse der Mod ändern, um die Details deines Mods anzupassen.
