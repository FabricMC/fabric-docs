---
title: 命令参数
description: 学习如何创建带有复杂参数的命令。

search: false
---

# 命令参数

大多数的命令都有参数。 有些时候他们是可选的，这意味着如果你不提供这些参数命令照样可以工作 一个节点可能有多种参数类型，此时有可能使用户困惑，请注意避免这个问题。

@[code lang=java highlight={3} transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

在这个情况里，在命令`/argtater`之后，您应该输入一个整型数字。 举个例子，如果您输入了`/argtater 3`，您应该会收到一条消息：`Called /argtater with value = 3`。 如果您输入了 `/argtataer` 并且没有任何参数，那么这个命令将不会被正确解析。

接下来我们将添加第二个可选的参数：

@[code lang=java highlight={3,13} transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

现在您可以输入一个或者两个整型数字了。 如果您提供了一个整型数字，那么会打印单个值的反馈文本。 如果您提供了两个整型数字，那么会打印有两个值的反馈文本。

您可能会发现没有必要指定两次相似的执行。 因此，我们可以创建一个方法同时用于两种执行。

@[code lang=java highlight={3,5,6,7} transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## 自定义参数类型

如果原版并没有提供您想要的命令参数类型，您可以创建您自己的类型。 为此，您需要创建一个继承`ArgumentType < T >` 接口的类，其中 `T` 是参数的类型。

您需要实现 `parse` 这个方法，这个方法会把输入的字符串解析为期望的类型。

举个例子，您可以创建一个可以把格式形如 `{x, y, z}` 的字符串解析为一个 `BlockPos` 参数类型。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/BlockPosArgumentType.java)

### 注册自定义参数类型

:::warning
您需要同时在服务端和客户端注册您的自定义参数类型，否则您的命令将不会工作！
:::

您可以在您的模组的初始化方法 `onInitialize` 中使用 `ArgumentTypeRegistry` 类来注册：

@[code lang=java transcludeWith=:::11](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### 使用自定义参数类型

我们可以在命令中使用我们的自定义参数类型──通过在 command builder 中传递实例到 `.argument` 方法。

@[code lang=java transcludeWith=:::10 highlight={3}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

运行命令，我们可以测试我们的参数类型是否可以正常工作：

![Invalid argument](/assets/develop/commands/custom-arguments_fail.png)

![Valid argument](/assets/develop/commands/custom-arguments_valid.png)

![Command result](/assets/develop/commands/custom-arguments_result.png)
