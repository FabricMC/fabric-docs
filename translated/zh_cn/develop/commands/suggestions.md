---
title: 命令建议
description: 学习如何向用户建议命令参数的值。
authors:
  - IMB11
---

# 命令建议{#command-suggestions}

Minecraft 有个强大的命令建议系统，用在很多地方，例如 `/give` 命令中。 该系统允许您向用户建议命令参数的值，然后他们可以从中选择——这是使你的命令更加用户友好且用起来舒适的好办法。

## 建议提供器{#suggestion-providers}

`SuggestionProvider` 用于制作将会发送至客户端的建议的列表。 建议提供器是一个函数式接口，接收一个 `CommandContext` 和 `SuggestionBuilder` 并返回 `Suggestions`。 `SuggestionProvider` 返回 `CompletableFuture`，因为这些建议并不一定立即可用。

## 使用建议提供器{#using-suggestion-providers}

要使用建议提供器，你需要在 argument builder 中调用 `suggests` 方法。 此方法接收一个 `SuggestionProvider`，返回一个附加了新的建议提供器的 argument builder。

@[code java transcludeWith=:::9 highlight={4}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## 内置的建议提供器{#built-in-suggestion-providers}

你可以使用一些内置的建议提供器：

| 建议提供器                                     | 描述           |
| ----------------------------------------- | ------------ |
| `SuggestionProviders.SUMMONABLE_ENTITIES` | 建议所有可召唤的实体。  |
| `SuggestionProviders.AVAILABLE_SOUNDS`    | 建议所有可播放的声音。  |
| `LootCommand.SUGGESTION_PROVIDER`         | 建议所有可用的战利品表。 |
| `SuggestionProviders.ALL_BIOMES`          | 建议所有可用的生物群系。 |

## 创建自定义的建议提供器{#creating-a-custom-suggestion-provider}

如果内置的建议提供器无法满足你的需要，可以创建自己的建议提供器。 为此，需要创建一个实现 `SuggestionProvider` 接口的类，并重写 `getSuggestions` 方法。

对此示例，我们需要制作一个建议提供器，建议所有在服务器上的玩家的名称。

@[code java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/PlayerSuggestionProvider.java)

要使用这个建议提供器，只需将一个实例传递到参数构造器的 `.suggests` 方法。

显然，建议提供器能够更复杂，因为还可以读取命令上下文以根据命令的状态（例如已经提供的参数）提供建议。

这可以是读取玩家的背包或提示玩家附近的物品或实体的形式。
