---
title: Tastenbelegung
description: Erstellen von Tastenbelegungen und darauf reagieren.
authors:
  - cassiancc
  - dicedpixels
  - its-miroma
  - NotNightSky
resources:
  https://upload.wikimedia.org/wikipedia/commons/d/da/KB_United_States.svg: Standard US Tastaturbelegung
---

Minecraft verarbeitet Benutzereingaben von Peripheriegeräten wie Tastatur und Maus mithilfe von Tastenbelegungen.
Viele dieser Tastenbelegungen können über das Einstellungsmenü konfiguriert werden.

Mit Hilfe der Fabric-API kannst du deine eigenen benutzerdefinierten Tastenbelegungen erstellen und in deinem Mod darauf reagieren.

Tastenbelegungen existieren nur auf der Client-Seite. Das bedeutet, dass die Registrierung und die Reaktion auf Tastenbelegungen auf der Client-Seite erfolgen sollten. Hierfür kannst du den **Client-Initialisierer** verwenden.

## Erstellen einer Tastenbelegung {#creating-a-key-mapping}

Eine Tastenbelegung besteht aus zwei Teilen: der Belegung einer Taste und der Kategorie, zu der sie gehört.

Lasst uns mit der Erstellung einer Kategorie beginnen. Eine Kategorie definiert eine Gruppe von Tastenbelegungen, die zusammen im Einstellungsmenü angezeigt werden.

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#category

Als Nächstes können wir eine Tastenbelegung erstellen. Wir werden Fabric APIs `KeyMappingHelper` verwenden, um
gleichzeitig unsere Tastenbelegung zu registrieren.

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#key_mapping

::: info

Beachte, dass die Namen der Tastentoken (`InputConstants.KEY_*`) von einem
[Standard-US-Layout](https://upload.wikimedia.org/wikipedia/commons/d/da/KB_United_States.svg) ausgehen.

Das bedeutet, dass bei Verwendung eines AZERTY-Layouts das Drücken von <kbd>A</kbd> zu `InputConstants.KEY_Q` führt.

:::

Klebrige Tasten können auch mit dem `KeyMappingHelper` erstellt werden, indem anstelle einer `KeyMapping`-Instanz eine `ToggleKeyMapping`-Instanz übergeben wird.

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

## Reagieren auf Tastenbelegungen in der Welt {#reacting-to-key-mappings-in-world}

Da wir nun eine Tastenbelegung haben, können wir ein Client-Tick-Event verwenden, wenn wir während des Spiels darauf reagieren möchten:

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#client_tick_event

Jedes Mal, wenn die zugewiesene Taste gedrückt wird, wird im Chat des Spiels die Nachricht "Key press detected in the world" angezeigt. Beachte, dass durch Gedrückthalten der Taste die Nachricht wiederholt im Chat angezeigt wird. Daher solltest du möglicherweise Schutzmaßnahmen implementieren, wenn diese Logik nur einmal ausgelöst werden soll.

![Nachricht im Chat](/assets/develop/key-mappings/key_mapping_pressed.png)

## Reagieren auf Tastenbelegungen im GUI {#reacting-to-key-mappings-in-gui}

Wir können auch auf Tastenbelegungen innerhalb von Oberflächen reagieren, sowohl wenn eine Welt geöffnet ist als auch wenn sie geschlossen ist.

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#screen_before_init_event

Und füge die zwei Handler hinzu:

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#helper_methods

Dies prüft, ob es sich bei der aktuellen Oberfläche um den `TitleScreen` oder den `CreativeModeInventoryScreen` handelt. Ist dies der Fall, implementieren wir zwei unterschiedliche Verhaltensweisen, je nachdem, ob wir uns innerhalb oder außerhalb einer Welt befinden:

- Befindet man sich außerhalb einer Welt, in anderen Worten, wenn keine Spieler-Entität vorhanden ist, wird die Meldung "Key press detected in the title screen" in die Konsole geloggt.
- Andernfalls, wenn die Taste in einer Welt gedrückt wird, wird die Meldung "Key press detected in the GUI with a world open, closing screen" an den Chat im Spiel gesendet und die Oberfläche geschlossen.

<VideoPlayer src="/assets/develop/key-mappings/in_screen_key_map.webm">Tastendruck im GUI bei geöffneter Welt</VideoPlayer>

::: info

Die zweite Meldung "Key press detected in the world" wird aufgrund des zuvor registrierten Event-Listeners `clientTickEvents` an den Chat gesendet.

:::

::: tip

`screen` ist im Überlebensmodus eine Instanz von `InventoryScreen`, während es im Kreativmodus eine Instanz von `CreativeModeInventoryScreen` ist.

Bei Bedarf kannst du die Überprüfung `screen instanceof` entfernen, um den Event-Listener an alle Oberflächen anzuhängen.

:::

<!---->
