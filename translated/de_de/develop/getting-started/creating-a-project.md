---
title: Ein Projekt erstellen
description: Eine Schritt-für-Schritt-Anleitung, wie man ein neues Mod-Projekt mit dem Fabric Template Mod Generator erstellt.
authors:
  - Cactooz
  - IMB11
  - radstevee
  - Thomas1034
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

Wenn du entweder Kotlin oder die Yarn-Mappings von Fabric anstelle der standardmäßigen Mojang-Mapping verwenden oder Datengeneratoren hinzufügen willst, kannst du die entsprechenden Optionen im Abschnitt `Advanced Options` auswählen.

::: info

Die auf dieser Website angegebenen Codebeispiele verwenden [die offiziellen Namen von Mojang](../migrating-mappings/#mappings). Wenn dein Mod nicht dieselben Mappings verwendet, in denen diese Dokumente geschrieben sind, musst du die Beispiele mithilfe von Websites wie [mappings.dev](https://mappings.dev/) oder [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=) konvertieren.

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
git clone https://github.com/FabricMC/fabric-example-mod/ example-mod
```

Dadurch wird das Repository in einen neuen Ordner mit dem Namen `example-mod` geklont.

Anschließend solltest du den Ordner `.git` aus dem geklonten Repository löschen und das Projekt öffnen. Wenn der Ordner `.git` nicht angezeigt wird, solltest du die Option zur Anzeige versteckter Dateien in deinem Dateimanager aktivieren.

Sobald du das Projekt in deiner Entwicklungsumgebung geöffnet hast, sollte es automatisch die Gradle-Konfiguration des Projekts laden und die notwendigen Einrichtungsaufgaben durchführen.

Wie bereits erwähnt, solltest du, wenn du eine Benachrichtigung über ein Gradle-Build-Skript erhältst, auf die Schaltfläche `Import Gradle Project` klicken.

### Die Vorlage bearbeiten {#modifying-the-template}

Sobald das Projekt importiert wurde, solltest du die Details des Projekts so ändern, dass sie mit deiner Mod übereinstimmen:

- Ändere die Datei `gradle.properties` des Projekts, um die Eigenschaften `maven_group` und `archive_base_name` an die Details deiner Mod anzupassen.
- Ändere die Datei `fabric.mod.json`, um die Eigenschaften `id`, `name` und `description` an die Details deiner Mod anzupassen.
- Stelle sicher, dass die Versionen von Minecraft, den Mappings, des Loaders und Loom - die alle über <https://fabricmc.net/develop/> abgefragt werden können - mit den Versionen übereinstimmen, die du ansprechen möchtest.

Du kannst natürlich den Paketnamen und die Hauptklasse der Mod ändern, um die Details deiner Mod anzupassen.
