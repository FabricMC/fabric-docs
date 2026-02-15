---
title: 调试模组
description: 了解有关日志记录、断点和其他有用的调试功能的所有信息。
authors:
  - its-miroma
  - JR1811
---

在编程中，即使是最优秀的人也难免会遇到问题、漏洞和错误。

本指南概述了一些通用步骤，即使没有他人帮助，你也可以通过这些步骤识别并解决这些问题。 独立解决问题可以让你学到很多东西，也会让你感到很有成就感。

但是，如果你遇到了困难，无法独自找到解决方案，向他人寻求帮助也完全没问题！

## 控制台和日志记录器 {#console-and-logger}

定位问题最简单快捷的方法是将日志记录到控制台。

可以在运行时将值打印到控制台，告知开发者代码的当前状态，并方便分析变更和潜在错误。

在模组的实现 `ModInitializer` 的入口点类中，默认定义了一个日志记录器 (`LOGGER`)，用于将所需的输出打印到控制台。

@[code lang=java transcludeWith=:::problems:basic-logger-definition](@/reference/latest/src/main/java/com/example/docs/debug/ExampleModDebug.java)

每当你需要了解代码中任何一点的某个值时，请通过将 `String` 传递给其方法来使用此 `LOGGER`。

@[code lang=java transcludeWith=:::problems:using-logger](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

记录器支持多种模式将文本打印到控制台。 根据使用的模式，记录的行将以不同的颜色显示。

```java
ExampleModDebug.LOGGER.info("Neutral, informative text...");
ExampleModDebug.LOGGER.warn("Non-critical issues..."); // [!code warning]
ExampleModDebug.LOGGER.error("Critical exceptions, bugs..."); // [!code error]
```

::: info

所有记录器模式都支持多重过载，这样你就可以提供更多信息，例如堆栈跟踪！

:::

例如，我们确保当在实体上使用 `TestItem` 时，它将在控制台中输出其当前状态。

@[code lang=java transcludeWith=:::problems:logger-usage-example](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

![控制台显示记录的输出](/assets/develop/debugging/debug_01.png)

在记录的行中，你可以找到：

- `Time` - 打印日志信息的时间
- `Thread` - 打印信息的线程。 你通常会看到一个 _**服务器线程**_ 和一个 _**渲染线程**_，这告诉你代码是在哪一端执行的
- `Name` - 日志记录器的名称。 最好在此处使用你的模组 ID，以便日志和崩溃信息显示哪个模组记录了日志
- `Message` - 消息应简洁且具有描述性。 包含相关值或上下文
- `Stack Trace` - 如果提供了异常的堆栈跟踪，日志记录器也可以打印该堆栈跟踪

### 保持控制台整洁 {#clean-console}

请记住，如果模组在其他环境中使用，所有这些信息也会被打印出来。

如果你记录的数据仅在开发中相关，则创建自定义 `LOGGER` 方法并使用它来避免在生产环境中打印数据可能会很有用。

@[code lang=java transcludeWith=:::problems:dev-logger](@/reference/latest/src/main/java/com/example/docs/debug/ExampleModDebug.java)

如果你不确定是否在调试会话之外打印日志，一个好的经验法则是仅在出现问题时才打印日志。 整合包开发者和用户并不太关心例如物品初始化之类的问题，他们更关心的是，例如数据包是否加载失败。

### 定位问题 {#locating-issues}

日志记录器会在行首打印你的模组 ID。 你可以搜索 (<kbd>⌘/CTRL</kbd>+<kbd>F</kbd>) 来高亮显示。

缺失的资源和纹理（黑紫方格占位符）也会在控制台中记录警告，并且通常会显示预期值。

![资源缺失](/assets/develop/debugging/missing_asset.png)

![日志记录器输出](/assets/develop/debugging/debug_02.png)

## 断点 {#breakpoints}

一种更复杂的调试方法是在 IDE 中使用断点。 顾名思义，断点用于在特定位置暂停正在执行的代码，以便检查和修改软件的状态。

::: tip

要使用断点，你必须使用 `Debug`（调试）选项而不是 `Run`（运行）选项执行实例：

![调试按钮](/assets/develop/debugging/debug_03.png)

:::

我们再次以自定义物品为例。 该物品的 `CUSTOM_NAME` `DataComponentType` 应该会根据其在任何石头方块上使用而改变。
然而，在这个例子中，该物品似乎总是会触发“成功”手势动画，而圆石似乎并没有改变其名称。

我们该如何解决这两个问题？ 让我们来探究一下……

```java
// problematic example code:

public class TestItem extends Item {

    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player user = context.getPlayer();
        BlockPos targetPos = context.getBlockPos();
        ItemStack itemStack = context.getItemInHand();
        BlockState state = level.getBlockState(targetPos);

        if (state.is(ConventionalBlockTags.STONES)) {
            Component newName = Component.literal("[").append(state.getBlock().getName()).append(Component.literal("]"));
            itemStack.set(DataComponents.CUSTOM_NAME, newName);
            if (user != null) {
                user.displayClientMessage(Component.literal("Changed Item Name"), true);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
```

点击行号即可设置断点。 如有需要，你可以一次设置多个断点。 断点会在执行所选行之前停止。

![设置断点](/assets/develop/debugging/debug_04.png)

然后让正在运行的 Minecraft 实例执行这部分代码。 你也可以在游戏运行时设置断点。

在本例中，自定义物品需要作用于方块。 Minecraft 窗口应该会冻结，并且在 IntelliJ 中，断点旁边会出现一个黄色箭头，表示调试器已到达该点。

在底部，会打开一个 `Debug`（调试）窗口，并自动选中 `Threads & Variables`（线程和变量）视图。 你可以使用 `Debug`（调试）窗口中的箭头控件移动执行点。 这种在代码中移动的方式称为“步进”。

![调试控制](/assets/develop/debugging/debug_05.png)

| 操作            | 说明                                                                  |
| ------------- | ------------------------------------------------------------------- |
| 步过（Step Over） | 进入下一行执行，基本上是沿着逻辑移动实例                                                |
| 步入（Step In）   | 进入方法内部，显示正在发生的事情。 如果一行中有多个方法，你可以通过点击来选择要进入的方法。 这对于 Lambda 表达式也是必需的。 |
| 运行到光标         | 逐步执行逻辑，直到到达代码中的光标。 这对于跳过大段代码非常有用。                                   |
| 显示执行点         | 将编码窗口的视图移动到调试器当前所在的位置。 即使你当前正在处理其他文件和选项卡，此操作也有效。                    |

::: info

“步过” <kbd>F8</kbd> 和“步入” <kbd>F7</kbd> 操作是最常见的，因此请尝试熟悉这些快捷键！

:::

如果你完成了当前检查，你可以按绿色的 `恢复程序` 按钮（<kbd>F9</kbd>）。 这将解冻 Minecraft 实例，并且可以进行进一步的测试，直到遇到另一个断点。 但现在我们先继续查看 `调试` 窗口。

在顶部，你可以看到所有当前正在运行的实例。 如果两个实例都在运行，你可以在客户端和服务器实例之间切换。 在其下方，你可以查看调试操作和控件。 如果需要查看日志，你还可以切换到 `控制台` 视图。

在左侧，你可以看到当前活动的线程，及其下方的堆栈跟踪。

在右侧，你可以检查和操作已加载的值和对象。 你还可以将鼠标悬停在代码中的值上：如果它们在作用域内并且仍处于加载状态，则会弹出一个窗口显示它们的具体值。

如果你对特定对象的内容感兴趣，可以使用其旁边的小箭头图标。 这将展开所有嵌套数据。

如果执行点未经过这些值，或者这些值位于完全不同的上下文中，则显然不会加载这些值。

![已加载的值](/assets/develop/debugging/debug_06.png)

你可以使用 `调试` 窗口中的输入行执行许多不同的操作，例如，你可以访问当前加载的对象并对其使用方法。 这将在下方添加一个新条目，显示请求的数据。

![对象分析](/assets/develop/debugging/debug_07.png)

让我们在示例中进行逐过程，以便加载 `BlockState` 变量。 现在我们可以检查目标方块的 `BlockState` 是否确实位于 `Block` 标签中。

::: tip

按下输入行右侧的 `+` 图标来固定当前调试会话的结果。

:::

![布尔表达式](/assets/develop/debugging/debug_08.png)

我们可以看到，`ConventionalBlockTags.STONES` 标签不包含圆石，因为圆石有一个单独的标签。

### 断点切换和条件 {#breakpoint-condition-toggle}

有时你只需要在满足特定条件时暂停代码。 为此，请创建一个断点并右键单击它以打开其设置。 在那里，你可以设置布尔语句作为条件。

空心断点图标表示非活动断点，这些断点不会暂停活动的 Minecraft 实例。 你可以在断点的设置弹出窗口中切换断点，也可以直接在断点上单击鼠标中键来切换。

所有断点都将列在 IntelliJ 的 `书签` 窗口中。

![断点菜单](/assets/develop/debugging/debug_09.png)

![书签中的断点](/assets/develop/debugging/debug_10.png)

### 热交换活动实例 {#hotswapping}

你可以在实例运行时使用带有锤子图标的 `构建 > 构建项目` 操作对代码进行有限的更改。 你还可以通过右键单击 IntelliJ 顶部菜单栏上的空白处将图标放在 `运行配置` 下拉元素旁边。

![在顶栏添加构建按钮](/assets/develop/debugging/debug_11.png)

此过程也称为“热交换”，要求 Minecraft 实例以 `调试` 模式而不是 `运行` 模式启动（参见[上文](#breakpoints)）。

这样，你就不需要再次重新启动 Minecraft 实例。 它还可以更快地测试屏幕元素对齐和其他功能平衡。
如果“热交换”成功，IntelliJ 将通知你。

![热交换状态通知](/assets/develop/debugging/debug_12.png)

Mixin 是个例外。 你可以设置运行配置，允许它们在运行时更改。
更多信息，请参阅[热交换 Mixin](./getting-started/launching-the-game#hotswapping-mixins)。

其他更改可以在游戏中重新加载。

- `assets/` 文件夹的更改 -> 按 <kbd>F3</kbd>+<kbd>T</kbd>
- `data/` 文件夹的更改 -> 使用 `/reload` 命令

为了完成之前的示例，让我们在语句中添加一个条件。 一旦我们到达断点，我们可以看到我们总是获得“成功”的手势动画，因为我们没有返回任何其他内容。

应用修复并使用热交换，即可立即在游戏中看到更改。

@[code lang=java transcludeWith=:::problems:breakpoints](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

## 日志与崩溃 {#logs-and-crashes}

先前执行的实例的控制台会被导出为日志文件，位于 Minecraft 实例的 `logs` 目录中。 最新的日志通常名为 `latest.log`。

用户可以将此文件分享给模组开发者以供进一步检查，详情请参阅[上传日志](../players/troubleshooting/uploading-logs)。

在开发环境中，你可以在 `项目` 窗口的 `run > logs` 文件夹中找到先前的日志，并在 `run > crash-reports` 文件夹中找到崩溃报告。

## 询问社区！ {#ask-the-community}

还是不明白是怎么回事？ 你可以加入 [Fabric Discord 服务器](https://discord.fabricmc.net/)，与社区成员交流！

你也可以访问 [Fabric 官方 Wiki](https://wiki.fabricmc.net/start)，了解更多高级问题。
