---
title: Befehlsvorschläge
description: Lerne, wie man Spielern Werte für Befehlsargumente vorschlagen kann.
authors:
  - IMB11
---

# Befehlsvorschläge

Minecraft hat ein mächtiges System für Befehlsvorschläge, das an vielen Stellen verwendet wird, wie zum Beispiel beim Befehl `/give`. Mit diesem System kannst du dem Spieler Werte für Befehlsargumente vorschlagen, aus denen er dann auswählen kann - eine großartige Möglichkeit, um deine Befehle benutzerfreundlicher und ergonomischer zu gestalten.

## Vorschlaganbieter

Ein `SuggestionProvider` wird verwendet, um eine Liste von Vorschlägen zu erstellen, die an den Spieler gesendet werden. Ein Vorschlaganbieter ist eine funktionales Interface, das einen `CommandContext` und einen `SuggestionBuilder` entgegennimmt und einige `Suggestions` zurückgibt. Der `SuggestionProvider` gibt ein `CompletableFuture` zurück, da die Vorschläge möglicherweise nicht sofort verfügbar sind.

## Verwenden von Vorschlaganbietern

Um einen Vorschlaganbieter zu verwenden, musst du die Methode `suggests` auf dem Argument Builder aufrufen. Diese Methode nimmt einen `SuggestionProvider` und gibt den geänderten Argument Builder mit dem angehängten Suggestion Provider zurück.

@[code java highlight={4} transcludeWith=:::command_with_suggestions](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code java transcludeWith=:::execute_command_with_suggestions](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Eingebaute Vorschlaganbieter

Es gibt einige eingebaute Vorschlaganbieter, du verwenden kannst:

| Vorschlaganbieter                         | Beschreibung                                                              |
| ----------------------------------------- | ------------------------------------------------------------------------- |
| `SuggestionProviders.SUMMONABLE_ENTITIES` | Schläft alle Entitäten vor, die beschworen werden können. |
| `SuggestionProviders.AVAILABLE_SOUNDS`    | Schlägt alle Klänge vor, die abgespielt werden können.    |
| `LootCommand.SUGGESTION_PROVIDER`         | Zeigt alle verfügbaren Loottabellen an.                   |
| `SuggestionProviders.ALL_BIOMES`          | Schlägt alle Biome vor, die verfügbar sind.               |

## Erstellen eines benutzerdefinierten Vorschlagsanbieters

Wenn ein eingebauter Anbieter deine Anforderungen nicht erfüllt, kannst du einen eigenen Vorschlaganbieter erstellen. Zu diesem Zweck musst du eine Klasse erstellen, die das Interface `SuggestionProvider` implementiert und die Methode `getSuggestions` überschreibt.

In diesem Beispiel erstellen wir einen Vorschlaganbieter, der alle Benutzernamen der Spieler auf dem Server vorschlägt.

@[code java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/PlayerSuggestionProvider.java)

Um diesen Vorschlaganbieter zu verwenden, übergebe einfach eine Instanz davon an die Methode `.suggests` im Argument Builder.

@[code java highlight={4} transcludeWith=:::command_with_custom_suggestions](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code java transcludeWith=:::execute_command_with_custom_suggestions](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Natürlich können die Anbieter von Vorschlägen komplexer sein, da sie auch den Befehlskontext lesen können, um Vorschläge zu machen, die auf dem Zustand des Befehls basieren - zum Beispiel auf den Argumenten, die bereits angegeben wurden.

Dies könnte in Form von Lesen des Inventars des Spielers und Vorschlagen von Gegenständen oder Entitäten, die sich in der Nähe des Spielers befinden, geschehen.
