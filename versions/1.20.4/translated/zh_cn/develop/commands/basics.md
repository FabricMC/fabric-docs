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

search: false
---

# 创建命令

创建命令可以允许模组开发者添加一些可以通过命令使用的功能。 这个指南将会教会你如何注册命令和 Brigadier 的一般命令结构。

:::info
Brigadier是一款由Mojang为Minecraft编写的命令解析器和调度器。 它是一款基于树的命令库，让您可以通过构建树的方式来构建您的命令和参数。 Brigadier是开源的： <https://github.com/Mojang/brigadier>
:::

## `Command` 接口

`com.mojang.brigadier.Command` 是一个可以执行指定行为的函数式接口， 并且在某些情况下会抛出 `CommandSyntaxException` 异常。 它有一个泛型参数 `S`，定义了_命令来源_的类型。
命令来源提供了命令运行的上下文。 在 Minecraft 中，命令来源通常是代表服务器的 `ServerCommandSource`，命令方块，远程连接(RCON)，玩家或者实体。

`Command` 中的单个方法 `run(CommandContext<S>)` 将 `CommandContext<S>` 作为唯一参数并返回一个整型数字。 命令上下文持有来自 `S` 命令来源类型并允许您获取参数，查看解析的命令节点并查看此命令中使用的输入。

像是其他的函数式接口，它一般用作 lambda 或者方法引用：

```java
Command<ServerCommandSource> command = context -> {
    return 0;
};
```

该整型数字可以被认为是命令的执行结果。 通常，小于或等于零的值表示命令失败并将继续执行并且什么也不做。 大于零的值则意味着命令被成功执行并做了某些事情。 Brigadier 提供了一个常量来表示执行成功： `Command#SINGLE_SUCCESS`。

### `ServerCommandSource` 可以做什么？

当执行时 `ServerCommandSource` 提供了一些额外的特殊实现的上下文。 它有获得执行命令的实体、在哪个世界运行或者在哪个服务器上运行的能力。

您可以通过在 `CommandContext` 实例上调用 `getSource()` 方法来获得命令上下文中的命令源。

```java
Command<ServerCommandSource> command = context -> {
    ServerCommandSource source = context.getSource();
    return 0;
};
```

## 注册一个基本命令

可以通过 Fabric API 提供的 `CommandRegistrationCallback` 来注册命令 。

:::info
更多注册回调的信息，请查看 [事件](../events) 指南。
:::

该事件应该在您的 mod 的初始化程序中注册。

回调一共有三个参数：

- `CommandDispatcher<ServerCommandSource> dispatcher` - 用于注册，解析，执行命令。 `S` 是分发器支持的命令源类型。
- `CommandRegistryAccess registryAccess` - 为可传递给某些命令参数方法的注册表提供一个抽象概念
- `CommandManager.RegistrationEnvironment environment` - 标识注册命令的服务器类型。

在 mod 初始化程序中，我们只注册两个简单的命令：

@[code lang=java transcludeWith=:::_1](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

在 `sendFeedback()` 方法之中，第一个参数是要发送的文本， 并且这是一个 `Supplier<Text>` 可以用于避免在不必要的时候实例化 Text 对象。

第二个参数决定是否广播反馈给其他的管理员。 一般来讲，如果一个命令只是查询一些东西而不会改变世界，比如说查询世界的时间或者玩家的分数，它应该是 `false` 的。 如果一个命令做了一些事情，比如说改变时间或者修改一些人的分数，它应该是 `true`。

如果一个命令失败了，您可以直接抛出任何异常而不是调用 `sendFeedback()`，服务器会妥善处理。

通常抛出 `CommandSyntaxException` 异常来指示语法异常或者参数异常。 你也可以实现一个专属的异常类型。

为了执行这个命令，您必须输入大小写敏感的 `/foo`。

### 注册环境

如果需要，您还可以确保仅在某些特定情况下注册命令，例如仅在专用环境中：

@[code lang=java highlight={2} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### 命令需求

假设您有一个只希望管理员可以执行的命令。 有一个 `requires()` 方法可以做到这一点。 `requires()` 方法有一个 `Predicate<S>` 参数，它将提供一个 `ServerCommandSource` 测试并确定 `CommandSource` 是否可以执行该命令。

@[code lang=java highlight={3} transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

这个命令只会在命令源至少为 2 级管理员（包括命令方块）时才会执行。 在其他情况下，命令并不会被注册。

这样做的副作用是不会向不具备 2 级管理员的人显示此命令。 这也是您为什么在未开启作弊模式的情况下不能使用 tab 补全绝大多数命令的原因。

### 子命令

要添加子命令，通常需要注册该命令的第一个字面量节点。 为了添加一个子命令，您必须将下一个字面量节点添加到一个已经存在的节点上。

@[code lang=java highlight={3} transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

类似参数，子命令也可被设置为可选。 在以下情况下，`/subtater` 和 `/subtater subcommand` 都是有效的。

@[code lang=java highlight={2,8} transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## 客户端命令

Fabric API 有一个存在于 `net.fabricmc.fabric.api.client.command.v2` 包中的 `ClientCommandManager`，可以帮助您注册客户端端的命令。 代码应当仅存在于客户端端的代码中。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## 命令重定向

命令重定向（也称为别名）是将一个命令的功能重定向到另一个命令的方法。 这在您想更改命令名称但仍希望支持旧名称时非常有用。

@[code lang=java transcludeWith=:::12](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## 常见问题

<br>

### 为什么我的代码不可以被编译？

- 捕捉或者抛出 `CommandSyntaxException` - `CommandSyntaxException` 并不是一个 `RuntimeException`。 如果你抛出了它， 抛出它的地方应该是在方法签名中抛出 `CommandSyntaxException` 的方法中，或者被捕获。
  Brigadier 将处理已检查的异常并为您转发游戏中正确的错误消息。 如果抛出异常，
  那么应该在方法签名中声明该方法会抛出 `CommandSyntaxException`，或者应该对其进行捕获处理。
  Brigadier 将处理已检查的异常并为您转发游戏中正确的错误消息。

- 泛型问题──您可能有时候会遇到泛型的问题。 泛型问题──您可能有时候会遇到泛型的问题。 如果您要注册服务器命令（大多数情况），请确保使用 `CommandManager.literal` 或者 `CommandManager.argument` 而不是 `LiteralArgumentBuilder.literal` 或者 `RequiredArgumentBuilder.argument`。

- 检查 `sendFeedback()` 方法 - 您可能忘记提供布尔值作为第二个参数。 检查 `sendFeedback()` 方法 - 您可能忘记提供布尔值作为第二个参数。 请您谨记，自从 Minecraft 1.20 版本开始， 第一个参数是类型 `Supplier<Text>` 而不是 `Text`。

- 命令应当返回一个整型数字 - 注册命令时，`executes()` 方法接受一个 `Command` 对象，该对象通常是 lambda。 lambda 应该返回一个整型数字，而不是其他类型。 lambda 应该返回一个整型数字，而不是其他类型。

### 我可以在运行时注册命令吗？

::: warning
You can do this, but it is not recommended. You would get the `CommandManager` from the server and add anything commands
you wish to its `CommandDispatcher`.

之后，需要使用 `CommandManager.sendCommandTree(ServerPlayerEntity)` 将命令树再次发送给每个玩家。

这是必须的，因为客户端只会在玩家登录时本地缓存命令树（或者发送操作包时）以完成丰富的错误信息补全。
:::

### 我可以在运行时注销命令吗？

::: warning
You can also do this, however, it is much less stable than registering commands at runtime and could cause unwanted side
effects.

为了保持事情简单，你需要对 Brigadier 使用反射来移除节点。 为了保持事情简单，你需要对 Brigadier 使用反射来移除节点。 在那之后，您需要使用 `sendCommandTree(ServerPlayerEntity)` 将命令树再次发送给所有玩家。

如果您不发送更新后的命令树，客户端可能认为命令依然存在，即使服务器会执行失败。
:::
:::
