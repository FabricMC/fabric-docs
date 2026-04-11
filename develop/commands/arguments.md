---
title: Command Arguments
description: Learn how to create commands with complex arguments.
---

Arguments are used in most of the commands. Sometimes they can be optional, which means if you do not provide that
argument,
the command will also run. One node may have multiple argument types, but be aware that there is a possibility of
ambiguity, which should be avoided.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#command-with-arg{3}
<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute-command-with-arg

In this case, after the command text `/command_with_arg`, you should type an integer. For example, if you
run `/command_with_arg 3`, you will get the feedback message:

> Called /command_with_arg with value = 3

If you type `/command_with_arg` without arguments, the command cannot be correctly parsed.

Then we add an optional second argument:

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#command-with-two-args{3,5}
<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute-command-with-two-args

Now you can type one or two integers. If you give one integer, a feedback text with a single value is printed. If you
provide two integers, a feedback text with two values will be printed.

You may find it unnecessary to specify similar executions twice. Therefore, we can create a method that will be used in
both executions.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#command-with-common-exec{4,6}
<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute-common

## Custom Argument Types {#custom-argument-types}

If vanilla does not have the argument type you need, you can create your own. To do this, you need to create a class that inherits the `ArgumentType<T>` interface where `T` is the type of the argument.

You will need to implement the `parse` method, which will parse the input string into the desired type.

For example, you can create an argument type that parses a `BlockPos` from a string with the following format: `{x, y, z}`

<<< @/reference/latest/src/main/java/com/example/docs/command/BlockPosArgumentType.java#custom-argument-types

### Registering Custom Argument Types {#registering-custom-argument-types}

::: warning

You need to register the custom argument type on both the server and the client or else the command will not work!

:::

You can register your custom argument type in the `onInitialize` method of your [mod's initializer](../getting-started/project-structure#entrypoints) using the `ArgumentTypeRegistry` class:

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#register-custom-arg

### Using Custom Argument Types {#using-custom-argument-types}

We can use our custom argument type in a command - by passing an instance of it into the `.argument` method on the command builder.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#custom-arg-command{3}
<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute-custom-arg-command{2}

Running the command, we can test whether or not the argument type works:

![Invalid argument](/assets/develop/commands/custom-arguments_fail.png)

![Valid argument](/assets/develop/commands/custom-arguments_valid.png)

![Command result](/assets/develop/commands/custom-arguments_result.png)
