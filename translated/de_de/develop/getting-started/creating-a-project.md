---
title: Ein Projekt erstellen
description: Eine Schritt-für-Schritt-Anleitung, wie man ein neues Mod-Projekt mit dem Fabric Vorlagen Mod Generator erstellt.
authors:
  - IMB11
  - Cactooz
---

# Ein Projekt erstellen

Fabric bietet eine einfache Möglichkeit, ein neues Mod-Projekt mit dem Fabric Template Mod Generator zu erstellen - wenn du möchtest, kannst du ein neues Projekt auch manuell erstellen, indem du das Beispiel-Mod-Repository verwendest, dann solltest du den Abschnitt [Manuelle Projekterstellung](#manuelle-projekterstellung) lesen.

## Erstellung eines Projekts

Du kannst den [Fabric Vorlagen Mod Generator](https://fabricmc.net/develop/template/) verwenden, um ein neues Projekt für deinen Mod zu generrieren - du solltest die erforderlichen Felder ausfüllen, wie beispielsweise den Paketnamen und den Mod-Namen, sowie die Minecraft-Version, für die du entwickeln möchtest.

Der Paketname sollte klein geschrieben, durch Punkte getrennt und eindeutig sein, um Konflikte mit den Paketen anderer Programmierer zu vermeiden. Er ist typischerweise wie eine umgedrehte Internet-Domain formatiert, beispielsweise wie `com.example.modid`.

![Vorschau des Generators](/assets/develop/getting-started/template-generator.png)

Wenn du Kotlin verwenden, die offiziellen Mappings von Mojang anstelle der Yarn-Mappings nutzen oder Datengeneratoren hinzufügen möchtest, kannst du die entsprechenden Optionen im Abschnitt `Advanced Options` auswählen.

![Der Abschnitt "Advanced options"](/assets/develop/getting-started/template-generator-advanced.png)

Der Generator erstellt dann ein neues Projekt in Form einer ZIP-Datei für dich.

Du solltest diese ZIP-Datei an einem Ort deiner Wahl entpacken und dann den entpackten Ordner in IntelliJ IDEA öffnen:

![Aufforderung zum Öffnen des Projekts](/assets/develop/getting-started/open-project.png)

## Import des Projekts

Sobald du das Projekt in IntelliJ IDEA geöffnet hast, sollte IDEA automatisch die Gradle-Konfiguration des Projekts laden und die notwendigen Einrichtungsaufgaben durchführen.

Wenn du eine Benachrichtigung über ein Gradle-Build-Skript erhältst, solltest du auf die Schaltfläche `Import Gradle Project` klicken:

![Gradle Aufforderung](/assets/develop/getting-started/gradle-prompt.png)

Sobald das Projekt importiert wurde, solltest du die Dateien des Projekts im Projekt-Explorer sehen und mit der Entwicklung deines Mods beginnen können.

## Manuelle Projekterstellung

:::warning
Du musst [Git](https://git-scm.com/) installiert haben, um das Beispiel-Mod-Repository klonen zu können.
:::

Wenn du den Fabric Vorlagen Mod Generator nicht verwenden kannst, kannst du ein neues Projekt manuell erstellen, indem du folgende Schritte befolgst.

Klone zunächst das Beispiel-Mod-Repository mit Git:

```sh
git clone https://github.com/FabricMC/fabric-example-mod/ my-mod-project
```

Dadurch wird das Repository in einen neuen Ordner mit dem Namen `my-mod-project` geklont.

Anschließend solltest du den Ordner `.git` aus dem geklonten Repository löschen und das Projekt in IntelliJ IDEA öffnen. Wenn der Ordner `.git` nicht angezeigt wird, solltest du die Option zur Anzeige versteckter Dateien in deinem Dateimanager aktivieren.

Sobald du das Projekt in IntelliJ IDEA geöffnet hast, sollte es automatisch die Gradle-Konfiguration des Projekts laden und die notwendigen Einrichtungsaufgaben durchführen.

Wie bereits erwähnt, solltest du, wenn du eine Benachrichtigung über ein Gradle-Build-Skript erhältst, auf die Schaltfläche `Import Gradle Project` klicken.

### Die Vorlage bearbeiten

Sobald das Projekt importiert wurde, solltest du die Details des Projekts so ändern, dass sie mit den Details deines Mods übereinstimmen:

- Ändere die Datei `gradle.properties` des Projekts, um die Eigenschaften `maven_group` und `archive_base_name` an die Details deines Mods anzupassen.
- Ändere die Datei `fabric.mod.json`, um die Eigenschaften `id`, `name` und `description` an die Details deines Mods anzupassen.
- Stelle sicher, dass die Versionen von Minecraft, die Mappings, der Loader und Loom - die alle über https://fabricmc.net/develop/ abgefragt werden können - mit den Versionen übereinstimmen, die du ansprechen möchtest.

Du kannst natürlich den Paketnamen und die Hauptklasse des Mods ändern, um die Details deines Mods anzupassen.
