---
title: Tastenbelegung
description: Erstellen von Tastenbelegungen und darauf reagieren.
authors:
  - cassiancc
  - dicedpixels
---

Minecraft verarbeitet Benutzereingaben von Peripheriegeräten wie Tastatur und Maus mithilfe von Tastenbelegungen.
Viele dieser Tastenbelegungen können über das Einstellungsmenü konfiguriert werden.

Mit Hilfe der Fabric-API kannst du deine eigenen benutzerdefinierten Tastenbelegungen erstellen und in deinem Mod darauf reagieren.

Tastenbelegungen existieren nur auf der Client-Seite. Das bedeutet, dass die Registrierung und die Reaktion auf Tastenbelegungen auf der Client-Seite erfolgen sollten. Hierfür kannst du den **Client-Initialisierer** verwenden.

## Erstellen einer Tastenbelegung {#creating-a-key-mapping}

Eine Tastenbelegung besteht aus zwei Teilen: der Belegung einer Taste und der Kategorie, zu der sie gehört.

Lasst uns mit der Erstellung einer Kategorie beginnen. Eine Kategorie definiert eine Gruppe von Tastenbelegungen, die zusammen im Einstellungsmenü angezeigt werden.

@[code lang=java transcludeWith=:::category](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

Als Nächstes können wir eine Tastenbelegung erstellen. Wir werden Fabric APIs `KeyBindingHelper` verwenden, um
gleichzeitig unsere Tastenbelegung zu registrieren.

@[code lang=java transcludeWith=:::key_mapping](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

::: info

Beachte, dass die Namen der Tastentoken (`GLFW.GLFW_KEY_*`) von einem
[Standard-US-Layout](https://upload.wikimedia.org/wikipedia/commons/d/da/KB_United_States.svg) ausgehen.

Das bedeutet, dass bei Verwendung eines AZERTY-Layouts das Drücken von <kbd>A</kbd> zu `GLFW.GLFW_KEY_Q` führt.

:::

Klebrige Tasten können auch mit dem `KeyBindingHelper` erstellt werden, indem anstelle einer `KeyMapping`-Instanz eine `ToggleKeyMapping`-Instanz übergeben wird.

Sobald registriert, findest du Tastenbelegungen unter _Optionen_ > _Steuerung_ > _Tastenbelegung_.

![Nicht übersetzte Schlüsselkategorie und Zuordnung](/assets/develop/key-mappings/untranslated.png)

## Übersetzungen {#translations}

Du musst sowohl für die Tastenbelegung als auch für die Kategorie Übersetzungen bereitstellen.

Der Übersetzungscode für den Kategorienamen hat die Form `key.category.<namespace>.<path>`. Der Schlüssel für die Schlüsselzuordnung ist derjenige, den du beim Erstellen der Schlüsselzuordnung angegeben hast.

Übersetzungen können manuell oder mithilfe der [Datengenerierung](./data-generation/translations) hinzugefügt werden.

```json
{
  "key.category.example-mod.custom_category": "Example Mod Custom Category",
  "key.example-mod.send_to_chat": "Send to Chat"
}
```

![Übersetzte Schlüsselkategorie und Zuordnung](/assets/develop/key-mappings/translated.png)

## Reagieren auf Tastenbelegungen {#reacting-to-key-mappings}

Jetzt, da wir eine Tastenbelegung haben, können wir mit einem Client-Tick-Ereignis darauf reagieren.

@[code lang=java transcludeWith=:::client_tick_event](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

Dadurch wird jedes Mal, wenn die zugewiesene Taste gedrückt wird, "Key Pressed!" im Chat des Spiels angezeigt. Beachte, dass durch Gedrückthalten der Taste die Nachricht wiederholt im Chat angezeigt wird. Daher solltest du möglicherweise Schutzmaßnahmen implementieren, wenn diese Logik nur einmal ausgelöst werden soll.

![Nachricht im Chat](/assets/develop/key-mappings/key_mapping_pressed.png)
