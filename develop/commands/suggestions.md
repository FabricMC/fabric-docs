---
title: Command Suggestions
description: Learn how to suggest command argument values to users.
authors:
  - IMB11
---

# Command Suggestions

Minecraft has a powerful command suggestion system that's used in many places, such as the `/give` command. This system allows you to suggest values for command arguments to the user, which they can then select from - it's a great way to make your commands more user-friendly and ergonomic.

## Suggestion Providers

A `SuggestionProvider` is used to make a list of suggestions that will be sent to the client. A suggestion provider is a functional interface that takes a `CommandContext` and a `SuggestionBuilder` and returns some `Suggestions`. The `SuggestionProvider` returns a `CompletableFuture` as the suggestions may not be available immediately.

## Using Suggestion Providers

To use a suggestion provider, you need to call the `suggests` method on the argument builder. This method takes a `SuggestionProvider` and returns a new argument builder with the suggestion provider attached.

@[code java transcludeWith=:::9 highlight={4}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Built-in Suggestion Providers

There are a few built-in suggestion providers that you can use:

| Suggestion Provider                       | Description                                  |
| ----------------------------------------- | -------------------------------------------- |
| `SuggestionProviders.SUMMONABLE_ENTITIES` | Suggests all entities that can be summoned.  |
| `SuggestionProviders.AVAILABLE_SOUNDS`    | Suggests all sounds that can be played.      |
| `LootCommand.SUGGESTION_PROVIDER`         | Suggests all loot tables that are available. |
| `SuggestionProviders.ALL_BIOMES`          | Suggests all biomes that are available.      |

## Creating a Custom Suggestion Provider

If a built-in provider doesn't satisfy your needs, you can create your own suggestion provider. To do this, you need to create a class that implements the `SuggestionProvider` interface and override the `getSuggestions` method.

For this example, we'll make a suggestion provider that suggests all the player usernames on the server.

@[code java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/PlayerSuggestionProvider.java)

To use this suggestion provider, you would simply pass an instance of it into the `.suggests` method on the argument builder.

Obviously, suggestion providers can be more complex, since they can also read the command context to provide suggestions based on the command's state - such as the arguments that have already been provided.

This could be in the form of reading the player's inventory and suggesting items, or entities that are nearby the player.

