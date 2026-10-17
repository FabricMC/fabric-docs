---
title: Dynamische Registry
description: Eine Einführung in dynamische Registries - was sie sind, wann sie nützlich sind und wie du mit der Fabric-API deine eigene erstellen kannst.
authors:
  - Jimmy474
---

Eine Registry ist ein zentrales "Telefonbuch", welches einzigartige IDs, wie `minecraft:items` zu spezifischen Objekten zuweist.

Es gibt zwei Arten von Registries: Statische Registries, wie beispielsweise die Block- und die Item-Registries, werden beim Start eingefroren, während dynamische oder benutzerdefinierte Registries zur Laufzeit aus JSON-Dateien in Datenpaketen gefüllt werden.

Sie sind aus mehreren Gründen nützlich:

- Die trennen Logik vom Inhalt.
- Andere Modder können neue Inhalte über Datenpakete hinzufügen, anstatt deinen Code zu patchen.
- Spieler können Standardwerte wie Manakosten oder Upgrade-Preise überschreiben, indem sie Dateneinträge in einem Datenpaket ersetzen.
- Daten einer dynamischen Registry sind weltspezifisch. Sie werden beim Öffnen einer Welt geladen und beim Schließen dieser Welt geleert.
- Dynamische Registries lösen das Problem der "fest codierten Inhalte". Anstatt jede Fertigkeit, jede Quest oder jedes Upgrade direkt in Java-Code mit Enums oder statischen Listen zu verankern, definierst du im Code eine Vorlage und lässt den eigentlichen Inhalt aus den Daten stammen.

Lasst uns eine dynamische Registry für ein magisches Fertigkeitensystem erstellen.

## Aufbau der Klasse {#class-setup}

Erstelle zunächst die Klasse, die einen Eintrag in der Registry darstellt. Es ist ein einfacher Datenspeicher für Werte, die mit den einzelnen magischen Fähigkeiten verknüpft sind, wie Name, Manakosten usw. Es wird ein [`Codec`](./codecs) benötigt, um den Eintrag zu kodieren oder dekodieren.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MagicSkillsRegistryEntry.java#main

- `name` ist der Name der Fähigkeit.
- `manaCost` sind die Manakosten der Fähigkeit.
- `onUseMcFunction` ist eine [Funktion](https://minecraft.wiki/w/Function_(Java_Edition)), die der Server ausführen kann, wenn die Fähigkeit verwendet wird. Wenn dies in der Registry hinterlegt ist, können andere Datapackete die Logik beliebiger Fähigkeiten anpassen oder neue Fähigkeiten mit benutzerdefinierten Funktionen hinzufügen.

## Die Registry registrieren {#registering-the-registry}

Jede Registry ist mit einem Schlüssel registriert, der sie eindeutig identifiziert. Erstellen wir also diesen Schlüssel und eine Klasse, die ihn enthält. Wir nennen diese Klasse `ExampleModRegistries`:

::: tip

Es wird empfohlen, die Registryschlüssel in einer allgemeinen Klasse zu deklarieren, da dies die Verwaltung mehrerer Registries erleichtert.

:::

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#main

Rufe `ExampleModRegistries.initialize()` von deinem [Mod Initialisierer](./getting-started/project-structure#entrypoints) auf.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModDynamicRegistries.java#main

Registriere dann bei den `DynamicRegistries` der Fabric-API, die zwei verschiedene Strategien bieten: `DynamicRegistries.register()` oder `DynamicRegistries.registerSynced()`.

### Verwendung von `register()` {#using-register}

`DynamicRegistries.register()` erstellt eine nicht synchronisierte Registry. Sie wird nur auf dem Server geladen und ist auf dem Client nicht verfügbar. Verwende dies, wenn der Client die Registry nie lesen muss.

In unserem Beispiel spielt das keine Rolle, aber so macht man es:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#simple

### Verwendung von `registerSynced()` {#using-register-synced}

::: info

Die in den folgenden Methoden verwendeten Schlüssel werden über denselben Weg erstellt wie wir [`MAGIC_SKILLS_REGISTRY_KEY`](#registering-the-registry) erstellt haben, allerdings mit einem anderen Namen.

:::

`DynamicRegistries.registerSynced()` erstellt eine synchronisierte Registry. Wenn ein Client einer Welt beitritt, synchronisiert der Server die Daten dieser Registry automatisch mit dem Client. Verwende dies, wenn der Client die Daten für das Rendering, die Benutzeroberfläche, Tooltips oder andere clientseitige Logik benötigt.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#synced

`DynamicRegistries.registerSynced()` hat eine Überladung, die einen zweiten Codec für die clientseitige Dekodierung akzeptiert. Dies ist nützlich, wenn der Client nicht jedes Feld des vollständigen Server-Eintrags benötigt.

In unserem Fall benötigen wir auf der Client-Seite nur die [Felder `name` und `manaCost`](#class-setup). Lasst uns also einen [`Codec`](./codecs) erstellen, der `onUseMcFunction` nicht enthält, und übergeben wir diesen Codec an `registerSynced`:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MagicSkillsRegistryEntry.java#client_codec

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#double_codec

### `SyncOption` {#sync-option}

Beide Überladungen von `DynamicRegistries.registerSynced()` akzeptieren am Ende `SyncOption`-Argumente, um das Synchronisationsverhalten zu konfigurieren. Die einzige verfügbare Option ist:

- `SKIP_WHEN_EMPTY`: Synchronisiert die Registry nur, wenn sie Einträge beinhaltet. Dies kann die Kompatibilität für Clients verbessern, die die Registry möglicherweise nicht benötigen.

Beispiel:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#with_option

## Befüllen der Registry {#populating-the-registry}

JSON-Dateien werden zum Erstellen von Registryeinträgen verwendet. Die JSON-Struktur muss mit dem [`MagicSkillsRegistryEntry`](#class-setup) übereinstimmen. In diesem Beispiel verfügt unsere Eintragsklasse über drei Felder, sodass die JSON-Datei für den Eintrag `healing_skill` etwa wie folgt aussehen könnte:

<<< @/reference/latest/src/main/generated/data/example-mod/example-mod/magic_skills_synced_registry/healing_skill.json

Die JSON-Dateien der Einträge, werden unter `src/main/resources/data/example-mod/example-mod/magic_skills_registry/` gespeichert.

::: info

Das wiederholte `example-mod/example-mod` ist kein Fehler.

Das erste `example-mod` ist der Namensraum des Eintrags, der hinzugefügt wird. Das zweite `example-mod` leitet sich aus der ID der Registry selbst ab. Es ist normal, für beides dieselbe Mod-ID zu verwenden und dies erlaubt anderen Mods oder Datenpaketen unter ihrem eigenen Namensraum Einträge zu deiner Registrierung hinzufügen.

Beispielsweise könnte `another-mod` Elemente zu unserer `magic_skills_registry` hinzufügen wollen und dies würde er über Dateien im Verzeichnis `src/main/resources/data/another-mod/example-mod/magic_skills_registry/` machen.

:::

### ID des Eintrag {#entry-id}

Die ID des Eintrags ist ein eindeutiger Schlüssel für jeden Eintrag und kann nützlich sein, um auf einen spezifischen Eintrag in einer Registry zuzugreifen. Sie setzt sich aus dem Dateinamen und dem [Registrierungsschlüssel](#registering-the-registry) zusammen. Da unsere JSON-Datei des Eintrags beispielsweise den Namen `healing_skill.json` hat, lautet die ID des Eintrags wie folgt:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#entry_id

## Auf die Registrydaten zugreifen {#accessing-the-registry-data}

Dynamische Registries werden mit der Welt geladen und können über die Klasse `RegistryAccess` unter Verwendung deines Registryschlüssel abgerufen werden.
Instanzen von `RegistryAccess` können aus vielen Klassen abgerufen werden, am häufigsten sind jedoch `MinecraftServer`, `ServerLevel`, `ClientLevel`, `Entity` und weitere.

:::warning WICHTIG

Beim Zugriff auf die `RegistryAccess`-Instanz aus einer reinen Client-Klasse wie beispielsweise `ClientLevel` stehen nur [synchronisierte Registries](#using-register-synced) zur Verfügung.

:::

### Die gesamte Registry abrufen {#get-the-entire-registry}

Der Zugriff auf Registries erfolgt über die Methode `lookup` von `RegistryAccess`, die einen `Optional<Registry<T>>` zurückgibt, wobei `T` der Typ der Registry ist.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_registry

### Einen spezifischen Eintrag abrufen {#get-a-specific-entry}

Auf bestimmte Einträge kann über die Methode `get` von `RegistryAccess` zugegriffen werden, die einen `Optional<Holder.Reference<T>>` zurückgibt, wobei `T` der Typ der Registrierung ist.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_specific_registry_entry

Lies [ID des Eintrags](#entry-id), um zu erfahren, wie du die `HEALING_SKILL_ENTRY_ID` erhältst.

In unserem Fall können wir diese Methode verwenden, um den Eintrag für die vom Benutzer auf dem Server verwendete magische Fähigkeit abzurufen und anschließend das Feld [`onUseMcFunction`](#class-setup) zu extrahieren, um die mcfunction auszuführen.

### Über alle Einträge iterieren {#iterate-over-all-entries}

Registryeinträge können für verschiedene Zwecke, wie beispielsweise das Ausfüllen der Benutzeroberfläche, durchlaufen werden. In unserem Fall können wir diese Methode verwenden, um eine Oberfläche wie folgt mit benutzerdefinierten Widgets zu füllen:

<<< @/reference/latest/src/client/java/com/example/docs/dynamic_registries/screens/ExampleModMagicSkillsScreen.java#iterate_over_registry_entries

:::details Eine von einer Registry befüllte benutzerdefinierte Oberfläche

![Beispiel für die Oberfläche für magische Fähigkeiten](/assets/develop/dynamic_registry/magic_skills_screen.png)

Lerne mehr über die Erstellung von [benutzerdefinierten Oberflächen](./rendering/gui/custom-screens) und [benutzerdefinierten Widgets](./rendering/gui/custom-widgets).

:::

## Tags für benutzerdefinierte Registryeinträge {#tags-for-custom-registry-entries}

Tags sind ein Weg, mehrere Einträge zu gruppieren. Beispielsweise können wir Tags wie _attack_ und _defense_ erstellen, um ähnliche magische Fähigkeiten zu gruppieren.

Beispielsweise würde das Angriffstag unter `data/example-mod/tags/example-mod/magic_skills_registry/attacking_skills.json` definiert werden:

<<< @/reference/latest/src/main/generated/data/example-mod/tags/example-mod/magic_skills_synced_registry/attacking_skills.json

### Tags im Code verwenden {#using-tags-in-code}

Erstelle einen Schlüssel für das Tag, um zu prüfen, ob Einträge in dem Tag vorhanden sind oder nicht.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModTags.java#tag

Mit dieser Methode können wir überprüfen, ob es sich bei einer Fähigkeit um eine Angriffsfähigkeit handelt oder nicht.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModTags.java#tag_usage
