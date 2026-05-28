---
title: 建立指令
description: 建立具有複雜引數和動作的指令。
authors:
  - Atakku
  - dicedpixels
  - haykam821
  - i509VCB
  - Juuxel
  - MildestToucan
  - modmuss50
  - mschae23
  - natanfudge
  - Pyrofab
  - SolidBlock-cn
  - Technici4n
  - Treeways
  - xpple
resources:
  https://github.com/Mojang/brigadier: Brigadier 原始碼
---

建立指令可以讓模組開發者新增可透過指令使用的功能。 本教學將說明如何註冊指令，以及 Brigadier 的一般指令結構。

::: info

[Brigadier](https://github.com/Mojang/brigadier) 是 Mojang 為 Minecraft 編寫的開源指令解析器與分派器。 它是一個以樹狀結構為基礎的指令函式庫，你可以用它建立由指令與引數組成的樹。

:::

## `Command` 介面 {#the-command-interface}

`com.mojang.brigadier.Command` 是一個函式式介面，用於執行特定程式碼，並在特定情況下擲出 `CommandSyntaxException`。 它具有泛型型別 `S`，用來定義「指令來源」的型別。
指令來源會提供指令執行時的相關上下文。 在 Minecraft 中，指令來源通常是 `CommandSourceStack`，它可以代表伺服器、指令方塊、遠端連線（RCON）、玩家或實體。

`Command` 中唯一的方法 `run(CommandContext<S>)` 只接受一個 `CommandContext<S>` 作為參數，並回傳一個整數。 指令上下文會保存型別為 `S` 的指令來源，並允許你取得引數、查看已解析的指令節點，以及檢視本次指令使用的輸入內容。

與其他函式式介面一樣，它通常會以 lambda 或方法參照的形式使用：

```java
Command<CommandSourceStack> command = context -> {
    return 0;
};
```

這個整數可以視為指令的執行結果。 通常，小於或等於零的值表示指令執行失敗，且不會產生任何效果； 正值則表示指令執行成功，並完成了某些操作。 Brigadier 提供了一個表示成功的常數：`Command#SINGLE_SUCCESS`。

### `CommandSourceStack` 可以做什麼？ {#what-can-the-servercommandsource-do}

`CommandSourceStack` 會在指令執行時提供一些額外的、與具體實作相關的上下文。 這包括取得執行指令的實體、指令執行所在的世界，或指令執行所在的伺服器。

你可以在指令上下文中呼叫 `CommandContext` 實例的 `getSource()` 來存取指令來源。

```java
Command<CommandSourceStack> command = context -> {
    CommandSourceStack source = context.getSource();
    return 0;
};
```

## 註冊基本指令 {#registering-a-basic-command}

指令會透過 Fabric API 提供的 `CommandRegistrationCallback` 來註冊。

::: info

如需了解如何註冊回呼，請參閱[事件](../events)指南。

:::

這個事件應在你的[模組初始化器](../getting-started/project-structure#entrypoints)中註冊。

該回呼有三個參數：

- `CommandDispatcher<CommandSourceStack> dispatcher` —— 用於註冊、解析與執行指令。 `S` 是該指令分派器所支援的指令來源型別。
- `CommandBuildContext registryAccess` —— 提供對註冊表的抽象存取，某些指令引數方法可能會需要它。
- `Commands.CommandSelection environment` —— 表示目前正在為哪一類伺服器註冊指令。

在模組初始化器中，我們只需註冊一個簡單的指令：

@[code lang=java transcludeWith=:::test_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

在 `sendSuccess()` 方法中，第一個參數是要傳送的文字。它是一個 `Supplier<Component>`，用於避免在不需要時實例化 `Component` 物件。

第二個參數決定是否將回饋訊息廣播給其他管理員。 一般來說，如果指令只是查詢某些資訊，而不會實際影響世界，例如查詢目前時間或某位玩家的分數，則應設為 `false`。 如果指令會執行某些操作，例如變更時間或修改某人的分數，則應設為 `true`。

如果指令執行失敗，你可以不呼叫 `sendSuccess()`，而是直接擲出任何例外。伺服器或客戶端會適當地處理它。

`CommandSyntaxException` 通常用於表示指令或引數中的語法錯誤。 你也可以實作自己的例外。

若要執行這個指令，必須輸入 `/test_command`。請注意，指令區分大小寫。

::: info

從這裡開始，我們會將寫在 `.executes()` 建構器 lambda 中的邏輯抽取到獨立方法中， 然後將方法參照傳入 `.executes()`。 這樣做是為了提高可讀性。

:::

### 註冊環境 {#registration-environment}

如有需要，你也可以確保某個指令只在特定情況下註冊，例如只在專用伺服器環境中註冊：

@[code lang=java highlight={2} transcludeWith=:::dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

### 指令需求條件 {#command-requirements}

假設你有一個只希望管理員能夠執行的指令， 這時就可以使用 `requires()` 方法。 `requires()` 方法接受一個 `Predicate<S>` 作為引數，並會提供一個 `CommandSourceStack` 供其測試，以判斷該 `CommandSource` 是否可以執行這個指令。

@[code lang=java highlight={3} transcludeWith=:::required_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_required_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

只有當指令來源至少具備管理員權限時，這個指令才會執行；這也包括指令方塊。 否則，該指令不會對該來源可用。

這也會帶來一個副作用：不具備管理員權限的使用者無法在 <kbd>Tab</kbd> 補全中看到這個指令。 這也是為什麼在未啟用作弊時，你無法用 <kbd>Tab</kbd> 補全大多數指令。

### 子指令 {#sub-commands}

若要新增子指令，你可以照常註冊指令的第一個字面節點。 若要讓它具有子指令，則需要將下一個字面節點附加到現有節點上。

@[code lang=java highlight={3} transcludeWith=:::sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

與引數類似，子指令節點也可以設定為可選。 在下面的情況中，`/command_two` 和 `/command_two sub_command_two` 都是有效的。

@[code lang=java highlight={2,8} transcludeWith=:::sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_command_sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

## 客戶端指令 {#client-commands}

同樣地，Fabric API 在 `net.fabricmc.fabric.api.client.command.v2` 套件中提供了 `ClientCommandRegistrationCallback` 事件，可用於註冊客戶端指令。此時應使用等價的 `ClientCommands` 來取代原版的 `Commands` 類別。 相關程式碼應只存在於客戶端程式碼中。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/ExampleModClientCommands.java)

## 指令重新導向 {#command-redirects}

指令重新導向，也稱為別名，是一種將某個指令的功能重新導向到另一個指令的方式。 當你想變更指令名稱，但仍希望支援舊名稱時，這會非常有用。

::: warning

Brigadier [只會重新導向帶有引數的指令節點](https://github.com/Mojang/brigadier/issues/46)。 如果你想重新導向不帶引數的指令節點，請提供一個 `.executes()` 建構器，並參照與範例中相同的邏輯。

:::

@[code lang=java transcludeWith=:::redirect_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_redirected_by](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

## 常見問題 {#faq}

### 為什麼我的程式碼無法編譯？ {#why-does-my-code-not-compile}

- 捕捉或擲出 `CommandSyntaxException` —— `CommandSyntaxException` 不是 `RuntimeException`。 如果你要擲出它，相關方法的方法簽名中就應宣告會擲出 `CommandSyntaxException`，或者應該捕捉它。
  Brigadier 會處理這些受檢例外，並在遊戲中為你轉送適當的錯誤訊息。

- 泛型問題 —— 你偶爾可能會遇到泛型相關問題。 如果你正在註冊伺服器指令，也就是大多數情況，請確保使用的是 `Commands.literal` 或 `Commands.argument`，而不是 `LiteralArgumentBuilder.literal` 或 `RequiredArgumentBuilder.argument`。

- 檢查 `sendSuccess()` 方法 —— 你可能忘記提供第二個布林值參數。 此外也請記住，自 Minecraft 1.20 起，第一個參數是 `Supplier<Component>`，而不是 `Component`。

- `Command` 應回傳整數 —— 註冊指令時，`executes()` 方法接受一個 `Command` 物件，通常是 lambda。 這個 lambda 應回傳整數，而不是其他型別。

### 我可以在執行時註冊指令嗎？ {#can-i-register-commands-at-runtime}

::: warning

你可以這麼做，但不建議。 你可以從伺服器取得 `Commands`，並將任何想要的指令加入它的 `CommandDispatcher`。

之後，你需要使用 `Commands.sendCommands(ServerPlayer)` 將指令樹重新傳送給每位玩家。

這是必要的，因為客戶端會在本機快取登入時收到的指令樹，或在收到管理員封包時收到的指令樹，以便用於本機補全與更完善的錯誤訊息。

:::

### 我可以在執行時取消註冊指令嗎？ {#can-i-unregister-commands-at-runtime}

::: warning

你也可以這麼做；然而，與在執行時註冊指令相比，這種做法穩定性差得多，並且可能造成不希望出現的副作用。

簡單來說，你需要對 Brigadier 使用反射並移除節點。 完成後，你需要再次使用 `sendCommands(ServerPlayer)` 將指令樹傳送給每位玩家。

如果你沒有傳送更新後的指令樹，客戶端可能會認為某個指令仍然存在，即使伺服器在執行時會失敗。

:::

<!---->
