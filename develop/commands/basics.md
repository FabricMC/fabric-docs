---
title: Creating Commands
description: Create commands with complex arguments and actions.
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
---

# Creating Commands

Creating commands can allow a mod developer to add functionality that can be used through a command. This tutorial will
teach you how to register commands and the general command structure of Brigadier.

::: info
Brigadier is a command parser and dispatcher written by Mojang for Minecraft. It is a tree-based command library where
you
build a tree of commands and arguments. Brigadier is open source: https://github.com/Mojang/brigadier
:::

### The `Command` Interface

`com.mojang.brigadier.Command` is a functional interface, which runs some specific code, and throws a
`CommandSyntaxException` in certain cases. It has a generic type `S`, which defines the type of the _command source_.
The command
source provides some context in which a command was run. In Minecraft, the command source is typically a
`ServerCommandSource` which can represent a server, a command block, a remote connection (RCON), a player or an entity.

The single method in `Command`, `run(CommandContext<S>)` takes a `CommandContext<S>` as the sole parameter and returns
an integer. The command context holds your command source of `S` and allows you to obtain arguments, look at the parsed
command nodes and see the input used in this command.

Like other functional interfaces, it is usually used as a lambda or a method reference:

```java
Command<ServerCommandSource> command = context -> {
    return 0;
};
```

The integer can be considered the result of the command. Typically negative values mean a command has failed and will do
nothing. A result of `0` means the command has passed. Positive values mean the command was successful and did
something. Brigadier provides a constant to indicate success; `Command#SINGLE_SUCCESS`.

#### What Can the `ServerCommandSource` Do?

A `ServerCommandSource` provides some additional implementation-specific context when a command is run. This includes
the
ability to get the entity that executed the command, the world the command was run in or the server the command was run
on.

You can access the command source from a command context by calling `getSource()` on the `CommandContext` instance.

```java
Command<ServerCommandSource> command = context -> {
    ServerCommandSource source = context.getSource(); 
    return 0;
};
```

### Registering a Basic Command

Commands are registered within the `CommandRegistrationCallback` provided by the Fabric API.

::: info
For information on registering callbacks, please see the [Events](../events.md) guide.
:::

The event should be registered in your mod's initializer.

The callback has three parameters:

* `CommandDispatcher<ServerCommandSource> dispatcher` - Used to register, parse and execute commands. `S`  is the type
  of command source the command dispatcher supports.
* `CommandRegistryAccess registryAccess` - Provides an abstraction to registries that may be passed to certain command
  argument methods
* `CommandManager.RegistrationEnvironment environment` - Identifies the type of server the commands are being registered
  on.

In the mod initializer, we just register a simple command:

@[code lang=java transcludeWith=:::_1](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

In the `sendFeedback()` method, the first parameter is the text to be sent, which is a `Supplier<Text>` to avoid
instantiating Text objects when not needed.

The second parameter determines whether to broadcast the feedback to other
operators. Generally, if the command is to query something without actually affecting the world, such as query the
current time or some player's score, it should be `false`. If the command does something, such as changing the
time or modifying someone's score, it should be `true`.

If the command fails, instead of calling `sendFeedback()`, you may directly throw any exception and the server or client
will handle it appropriately.

`CommandSyntaxException` is generally thrown to indicate syntax errors in commands or arguments. You can also implement 
your own exception.

To execute this command, you must type `/foo`, which is case-sensitive.

#### Registration Environment

If desired, you can also make sure a command is only registered under some specific circumstances, for example, only in
the dedicated environment:

@[code lang=java highlight={2} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

#### Command Requirements

Let's say you have a command that you only want operators to be able to execute. This is where the `requires()` method
comes into play. The `requires()` method has one argument of a `Predicate<S>` which will supply a `ServerCommandSource`
to test with and determine if the `CommandSource` can execute the command.

@[code lang=java highlight={3} transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

This command will only execute if the source of the command is a level 2 operator at a minimum, including command
blocks. Otherwise, the command is not registered.

This has the side effect of not showing this command in tab completion to anyone who is not a level 2 operator. This is
also why you cannot tab-complete most commands when you do not enable cheats.

#### Sub Commands

To add a sub command, you register the first literal node of the command normally. To have a sub command, you have to append the next literal node to the existing node.

@[code lang=java highlight={3} transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Similar to arguments, sub command nodes can also be set optional. In the following case, both `/subtater`
and `/subtater subcommand` will be valid.

@[code lang=java highlight={2,8} transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Client Commands

Fabric API has a `ClientCommandManager` in `net.fabricmc.fabric.api.client.command.v2` package that can be used to register client-side commands. The code should exist only in client-side code.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

### Command Redirects

Command redirects - also known as aliases - are a way to redirect the functionality of one command to another. This is useful for when you want to change the name of a command, but still want to support the old name.

@[code lang=java transcludeWith=:::12](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

### FAQ

<br>

###### Why does my code not compile?

* Catch or throw a `CommandSyntaxException` - `CommandSyntaxException` is not a `RuntimeException`. If you throw it,
  where it is thrown should be in methods that throw `CommandSyntaxException` in method signatures, or be caught.
  Brigadier will handle the checked exceptions and forward the proper error message in the game for you.

* Issues with generics - You may have an issue with generics once in a while. If you are registering server
  commands (which are most of the case), make sure you are using `CommandManager.literal`
  or `CommandManager.argument` instead of `LiteralArgumentBuilder.literal` or `RequiredArgumentBuilder.argument`.

* Check `sendFeedback()` method - You may have forgotten to provide a boolean as the second argument. Also remember
  that, since Minecraft 1.20, the first parameter is `Supplier<Text>` instead of `Text`.

* A Command should return an integer - When registering commands, the `executes()` method accepts a `Command` object,
  which is usually a lambda. The lambda should return an integer, instead of other types.

###### Can I register commands in runtime?

::: warning
You can do this, but it is not recommended. You would get the `CommandManager` from the server and add anything commands
you wish to its `CommandDispatcher`.

After that, you need to send the command tree to every player again
using `CommandManager.sendCommandTree(ServerPlayerEntity)`.

This is required because the client locally caches the command tree it receives during login (or when operator packets
are sent) for local completions-rich error messages.
:::

###### Can I unregister commands in runtime?

::: warning
You can also do this, however, it is much less stable than registering commands at runtime and could cause unwanted side
effects.

To keep things simple, you need to use reflection on Brigadier and remove nodes. After this, you need to send the
command tree to every player again using `sendCommandTree(ServerPlayerEntity)`.

If you don't send the updated command tree, the client may think a command still exists, even though the server will
fail execution.
:::