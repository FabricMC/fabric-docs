---
title: 创建命令
description: 创建带有复杂参数和行为的命令。
authors:
  - dicedpixels
  - i509VCB
  - pyrofab
  - natanfudge
  - Juuxel
  - solidblock
  - modmuss50
  - technici4n
  - atakku
  - haykam
  - mschae23
  - treeways
  - xpple
---

# 创建命令

创建命令可以允许模组开发者添加一些可以通过命令使用的功能。 这个指南将会教会你如何注册命令和 Brigadier 的一般命令结构。

::: info
Brigadier is a command parser and dispatcher written by Mojang for Minecraft. It is a tree-based command library where
you build a tree of commands and arguments.

Brigadier 是开源的：https://github.com/Mojang/brigadier

## `Command` 接口{#the-command-interface}

`com.mojang.brigadier.Command` 是一个可以执行指定行为的函数式接口，在某些情况下会抛出 `CommandSyntaxException` 异常。 命令有一个泛型 `S`，定义了_命令来源_的类型。
命令来源提供了命令运行的上下文。 在 Minecraft 中，命令来源通常是 `ServerCommandSource`，代表服务器、命令方块、远程连接（RCON）、玩家或者实体。

`Command` 中的单个方法 `run(CommandContext<S>)`，接收一个 `CommandContext<S>` 作为唯一参数，并返回一个整数。 命令上下文存储命令来源 `S`，并允许你获取参数、查看已解析的命令节点，并查看此命令中使用的输入。

就像其他的函数型接口那样，命令通常用作 lambda 或者方法引用：

```java
Command<ServerCommandSource> command = context -> {
    return 0;
};
```

这个整数相当于命令的结果。 通常，小于或等于零的值表示命令失败，什么也不做。 正数则表示命令执行成功并做了一些事情。 Brigadier 提供了一个常量来表示执行成功：`Command#SINGLE_SUCCESS`。

### `ServerCommandSource` 可以做什么？ {#what-can-the-servercommandsource-do}

`ServerCommandSource` 提供了命令运行时的一些额外的上下文，有特定实现， 包括获取运行这个命令的实体、命令执行时所在的世界以及服务器。

可以通过在 `CommandContext` 实例上调用 `getSource()` 方法来获得命令上下文中的命令来源。

```java
Command<ServerCommandSource> command = context -> {
    ServerCommandSource source = context.getSource(); 
    return 0;
};
```

## 注册一个基本命令{#registering-a-basic-command}

可以通过 Fabric API 提供的 `CommandRegistrationCallback` 来注册命令 。

:::info
关于如何注册回调，请查看[事件](../events) 指南。
:::

这个事件必须在你的模组的初始化器中注册。

这个回调有三个参数：

- `CommandDispatcher<ServerCommandSource> dispatcher` - 用于注册、解析和执行命令。 `S` 是命令派发器支持的命令源的类型。
- `CommandRegistryAccess registryAccess` - 为可能传入特定命令参数的注册表提供抽象方法
- `CommandManager.RegistrationEnvironment environment` - 识别命令将要注册到的服务器的类型。

在模组的初始化器中，我们只注册两个简单的命令：

@[code lang=java transcludeWith=:::test_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

在 `sendFeedback()` 方法之中，第一个参数是要发送的文本， 是 `Supplier<Text>`，以避免在不必要时实例化 Text 对象。

第二个参数决定是否广播反馈给其他的管理员。 一般来讲，如果命令是查询一些东西而不实际改变世界，比如查询世界的时间或者玩家的分数，则应该是 `false`。 如果命令实际上做了些事情，例如改变时间或者修改一些人的分数，则应该是 `true`。

如果命令失败，可以不必调用 `sendFeedback()`，而是直接抛出任何异常，服务器和客户端会适当处理。

通常抛出 `CommandSyntaxException` 异常来指示命令或参数中的语法错误。 你也可以实现你自己的异常。

要执行这个命令，必须输入 `/test_command`，这是大小写敏感的。

:::info
这里也说下，我们会把写在传入 `.execute()` 构造器中的 lambda 中的逻辑，写到单独的方法中。 然后，给 `.execute()` 传入方法引用。 这样做是为了更清晰。
:::

### 注册环境{#registration-environment}

如有需要，你可以确保命令仅在一些特定情况下注册，例如仅在专用的环境中：

@[code lang=java highlight={2} transcludeWith=:::dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### 命令要求{#command-requirements}

假如说你希望命令只有管理员可以执行， 这时就要用到 `requires()` 方法。 `requires()` 方法有一个 `Predicate<S>` 参数，提供一个 `ServerCommandSource` 以检测并确定 `CommandSource` 能否执行命令。

@[code lang=java highlight={3} transcludeWith=:::required_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_required_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

这个命令只会在命令源至少为 2 级管理员（包括命令方块）时才会执行， 否则，命令不会被注册。

这样做的副作用就是，非 2 级管理员会看到命令不会被 tab 补全， 这也就是为什么没有启用作弊时不能够 tab 补全大多数命令。

### 子命令{#sub-commands}

要添加子命令，你需要先照常注册第一个字面节点。 为拥有子命令，需要把下一个节点追加到已经存在的节点后面。

@[code lang=java highlight={3} transcludeWith=:::sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

类似于参数，子命令节点也可以设置为可选的。 在下面这个例子中，`/command_two` 和 `/command_two sub_command_two` 都是有效的。

@[code lang=java highlight={2,8} transcludeWith=:::sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_command_sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## 客户端命令{#client-commands}

Fabric API 有个 `ClientCommandManager`，位于 `net.fabricmc.fabric.api.client.command.v2` 包中，可用于注册客户端命令。 代码应该仅存在于客户端的代码中。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## 命令重定向{#command-redirects}

命令重定向（也称为别名）是将一个命令的功能重定向到另一个命令的方法。 这在您想更改命令名称但仍希望支持旧名称时非常有用。

:::warning
Brigadier [只会重定向有参数的命令节点](https://github.com/Mojang/brigadier/issues/46)。 如果需要重定向没有参数的命令节点，给 `.execute()` 构造器提供一个到相同逻辑的引用，就像这个例子中。
:::

@[code lang=java transcludeWith=:::redirect_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_redirected_by](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## 常见问题{#faq}

### 为什么我的代码为什么不编译？ {#why-does-my-code-not-compile}

- 捕捉或抛出 `CommandSyntaxException` - `CommandSyntaxException` 不是 `RuntimeException`， 如果抛出，则抛出的地方所在方法必须在方法签名中也抛出 `CommandSyntaxException`，或者捕获。
  Brigadier 会处理已检查的异常，并在游戏内为你转发适当的错误消息。

- 泛型问题 -你可能遇到了泛型问题。 如果你在注册服务器命令（大多数情况都是如此），确保你在静态导入中使用 `CommandManager.literal` 或 `CommandManager.argument` 而不是`LiteralArgumentBuilder.literal` 或 `RequiredArgumentBuilder.argument`。

- 检查 `sendFeedback()` 方法 - 你可能忘记了提供第二个参数（一个布尔值）。 还需要注意，从 Minecraft 1.20 开始，第一个参数是 `Supplier<Text>` 而不是 `Text`。

- 命令应该返回整数：注册命令时，`executes()` 方法接受一个 `Command` 对象，通常是 lambda。 这个 lambda 应该返回整数，而不是其他的类型。

### 可以运行时注册命令吗？ {#can-i-register-commands-at-runtime}

::: warning
You can do this, but it is not recommended. You would get the `CommandManager` from the server and add anything commands
you wish to its `CommandDispatcher`.

然后需要通过 `CommandManager.sendCommandTree(ServerPlayerEntity)` 向每个玩家再次发送命令树。

这是必需的，因为客户端已经缓存了命令树并在登录过程中（或发出管理员数据包时）使用，以用于本地的补全和错误消息。
:::

### 可以在运行时取消注册命令吗？ {#can-i-unregister-commands-at-runtime}

::: warning
You can also do this, however, it is much less stable than registering commands at runtime and could cause unwanted side
effects.

为简化事情，你需要在 brigadier 中使用反射并移除这个节点， 然后还需要再次使用 `sendCommandTree(ServerPlayerEntity)` 向每个玩家发送命令树。

如果不发送更新的命令树，客户端可能还是会认为命令依然存在，即使服务器无法执行。
:::
