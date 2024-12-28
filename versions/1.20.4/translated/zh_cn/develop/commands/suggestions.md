---
title: 命令提示
description: 学习如何向用户提示命令参数的值。
authors:
  - IMB11

search: false
---

# 命令提示

Minecraft 有一个使用在了很多地方的十分强大的命令建议系统，比如说 `/give` 命令。 该系统允许您向用户提示命令参数的值，然后他们可以从中选择 —— 这是使您的命令更加用户友好且符合人体工学的好办法。 该系统允许您向用户提示命令参数的值，然后他们可以从中选择 —— 这是使您的命令更加用户友好且符合人体工学的好办法。

## 提示提供器

提示所有可用的战利品表。 `SuggestionProvider` 用于创建发送给客户端的提示列表。 提示提供器是一个函数式接口，它使用 `CommandContext` 和 `SuggestionBuilder` 并返回 `Suggestions`。 `SuggestionProvider` 返回 `CompletableFuture` 因为提示可能不会立即可用。 `SuggestionProvider` 返回 `CompletableFuture` 因为提示可能不会立即可用。

## 使用提示提供器

要使用提示提供器，您可以在参数构造器上调用 `suggests` 方法。 此方法接受一个 `SuggestionProvider`，返回一个新的参数构造器，并附加提示提供器。 此方法接受一个 `SuggestionProvider`，返回一个新的参数构造器，并附加提示提供器。

@[code java transcludeWith=:::9 highlight={4}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## 内建的提示提供器

您可以使用一些内建的提示提供器：

| 提示提供器                                     | 描述           |
| ----------------------------------------- | ------------ |
| `SuggestionProviders.SUMMONABLE_ENTITIES` | 提示所有可被召唤的实体。 |
| `SuggestionProviders.AVAILABLE_SOUNDS`    | 提示所有可被播放的声音。 |
| `LootCommand.SUGGESTION_PROVIDER`         | 提示所有可用的战利品表。 |
| `SuggestionProviders.ALL_BIOMES`          | 提示所有可用的生物群系。 |

## 创建一个自定义的提示提供器

如果内建的提供器无法满足您的需要，您可以创建您自己的提示提供器。 为此，您需要创建一个实现 `SuggestionProvider` 接口的类，并重写 `getSuggestions` 方法。 为此，您需要创建一个实现 `SuggestionProvider` 接口的类，并重写 `getSuggestions` 方法。

对此示例，我们需要制作一个提示提供器提示所有在服务器上的玩家的名称。

@[code java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/PlayerSuggestionProvider.java)

要使用该提示提供器，您只需将它的实例传递到参数构造器的 `.suggests` 方法。

显然，建议提供者可能更复杂，因为它们还可以读取命令上下文以根据命令的状态（例如已经提供的参数）提供建议。

这可以是读取玩家的背包或提示玩家附近的物品或实体的形式。
