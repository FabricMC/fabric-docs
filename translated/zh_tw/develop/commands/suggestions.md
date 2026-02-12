---
title: 指令建議
description: 了解如何向使用者建議指令引數值。
authors:
  - IMB11
---

Minecraft 有個強大的命令建議系統，用在很多地方，例如 `/give` 命令中。 該系統允許您向用戶建議命令參數的值，然後他們可以從中選擇——這是使你的命令更加用戶友好且用起來舒適的好辦法。

## 建議提供器{#suggestion-providers}

`SuggestionProvider` 用於製作將會發送至客戶端的建議的列表。 建議提供器是一個函數式接口，接收一個 `CommandContext` 和 `SuggestionBuilder` 並返回 `Suggestions`。 `SuggestionProvider` 返回 `CompletableFuture`，因為這些建議並不一定立即可用。

## 使用建議提供器{#using-suggestion-providers}

要使用建議提供器，你需要在 argument builder 中調用 `suggests` 方法。 此方法接收一個 `SuggestionProvider`，返回一個附加了新的建議提供器的 argument builder。

@[code java highlight={4} transcludeWith=:::command_with_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code java transcludeWith=:::execute_command_with_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

## 內置的建議提供器{#built-in-suggestion-providers}

你可以使用一些內置的建議提供器：

| 建議提供器                                     | 描述           |
| ----------------------------------------- | ------------ |
| `SuggestionProviders.SUMMONABLE_ENTITIES` | 建議所有可召喚的實體。  |
| `SuggestionProviders.AVAILABLE_SOUNDS`    | 建議所有可播放的聲音。  |
| `LootCommand.SUGGESTION_PROVIDER`         | 建議所有可用的戰利品表。 |
| `SuggestionProviders.ALL_BIOMES`          | 建議所有可用的生物群系。 |

## 創建自定義的建議提供器{#creating-a-custom-suggestion-provider}

如果內置的建議提供器無法滿足你的需要，可以創建自己的建議提供器。 為此，需要創建一個實現 `SuggestionProvider` 接口的類，並重寫 `getSuggestions` 方法。

對此示例，我們需要製作一個建議提供器，建議所有在服務器上的玩家的名稱。

@[code java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/PlayerSuggestionProvider.java)

要使用這個建議提供器，只需將一個實例傳遞到參數構造器的 `.suggests` 方法。

@[code java highlight={4} transcludeWith=:::command_with_custom_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code java transcludeWith=:::execute_command_with_custom_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

顯然，建議提供器能夠更複雜，因為還可以讀取命令上下文以根據命令的狀態（例如已經提供的參數）提供建議。

這可以是讀取玩家的物品欄並推薦物品或玩家附近的實體。
