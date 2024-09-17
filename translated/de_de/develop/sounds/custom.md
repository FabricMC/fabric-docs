---
title: Benutzerdefinierte Sounds erstellen
description: Lerne, wie man neue Sounds mit einer Registry hinzufügt und nutzt.
authors:
  - JR1811
---

# Benutzerdefinierte Sounds erstellen

## Vorbereitung der Audio-Datei

Deine Audio-Dateien müssen auf eine bestimmte Weise formatiert werden. OGG Vorbis ist ein offenes Containerformat für Multimediadaten, wie zum Beispiel Audio, und wird für die Sounddateien von Minecraft verwendet. Um Probleme mit der Distanzierung in Minecraft zu vermeiden, darf deine Audio nur einen einzigen Kanal besitzen (Mono).

Viele moderne DAWs (Digital Audio Workstation) können dieses Dateiformat importieren und exportieren. Im folgenden Beispiel wird die freie und Open Source Software "[Audacity](https://www.audacityteam.org/)" verwendet, um die Audio-Datei in das richtige Format zu bringen, aber auch jede andere DAW sollte ausreichen.

![Unvorbereitete Audiodatei in Audacity](/assets/develop/sounds/custom_sounds_0.png)

In diesem Beispiel wird ein [Pfeifton](https://freesound.org/people/strongbot/sounds/568995/) in Audacity importiert. Sie ist derzeit als `.wav`-Datei gespeichert und hat zwei Audiokanäle (Stereo). Bearbeite den Sound nach deinem Geschmack und stelle sicher, dass du einen der Kanäle mit dem Dropdown-Element oben im "Spurkopf" löschst.

![Aufteilung der Stereospur](/assets/develop/sounds/custom_sounds_1.png)

![Löschen von einem der Kanäle](/assets/develop/sounds/custom_sounds_2.png)

Achte beim Exportieren oder Rendern der Audio-Datei darauf, dass du das Dateiformat OGG wählst. REAPER, unterstützen mehrere OGG-Audio-Layer-Formate. In diesem Fall sollte OGG Vorbis sehr gut funktionieren.

![Export als OGG-Datei](/assets/develop/sounds/custom_sounds_3.png)

Denke auch daran, dass Audio-Dateien die Dateigröße deines Mods drastisch erhöhen können. Falls erforderlich, komprimiere die Audiodaten beim Bearbeiten und Exportieren der Datei, um die Dateigröße des fertigen Produkts so gering wie möglich zu halten.

## Laden der Audio-Datei

Füge das neue Verzeichnis `resources/assets/<mod id here>/sounds` für die Sounds in deinem Mod hinzu, und lege die exportierte Audio-Datei `metal_whistle.ogg` dort hinein.

Fahre mit der Erstellung der Datei `resources/assets/<mod id here>/sounds.json` fort, falls sie noch nicht existiert und füge deinen Sound zu den Sound-Einträgen hinzu.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/sounds.json)

Der Untertiteleintrag bietet dem Spieler mehr Kontext. Der Name des Untertitels wird in den Sprachdateien im Verzeichnis `resources/assets/<mod id here>/lang` verwendet und wird angezeigt, wenn die Untertitel-Einstellung im Spiel aktiviert ist und dieser benutzerdefinierte Sound abgespielt wird.

## Registrieren des benutzerdefinierten Sounds

Um den benutzerdefinierten Sound zum Mod hinzuzufügen, registriere ein SoundEvent in der Klasse, die den `ModInitializer`-Einstiegspunkt implementiert.

```java
Registry.register(Registries.SOUND_EVENT, Identifier.of(MOD_ID, "metal_whistle"),
        SoundEvent.of(Identifier.of(MOD_ID, "metal_whistle")));
```

## Das Chaos aufräumen {#cleaning-up-the-mess}

Je nachdem, wie viele Einträge in der Registry vorhanden sind, kann dies schnell unübersichtlich werden. Um dies zu vermeiden, können wir eine neue Hilfsklasse verwenden.

Füge zwei neue Methoden zu der neu erstellten Hilfsklasse hinzu. Eine, die alle Sounds registriert, und eine, die dazu dient, diese Klasse überhaupt erst zu initialisieren. Danach kannst du bequem neue benutzerdefinierte statische Klassenvariablen der Klasse `SoundEvent` nach Bedarf hinzufügen.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/sound/CustomSounds.java)

Auf diese Weise muss die `ModInitializer` implementierende Einstiegsklasse nur eine Zeile implementieren, um alle benutzerdefinierten SoundEvents zu registrieren.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/sound/FabricDocsReferenceSounds.java)

## Das benutzerdefinierte SoundEvent nutzen

Verwende die Hilfsklasse, um auf das benutzerdefinierte SoundEvent zuzugreifen. Auf der Seite [SoundEvents abspielen](./using-sounds) erfährst du, wie man Sounds abspielt.
