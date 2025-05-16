---
title: Basic Problem Solving
description: Learn all about Logging, Breakpointing and other useful Debugging features.
authors:
  - JR1811
---

Issues, mistakes, and bugs can happen even to the best programmer. You can develop a basic set of steps to potentially identify and resolve those issues without the help of others. Problems solved on your own can teach you many things and also feel rewarding. However, if you are stuck without being able to fix the problems by yourself, there is no shame in asking others for help.

This page focuses mostly on functionality that is provided by IntelliJ (and many other IDEs) such as logging and how to use the Debugger.

## Console and LOGGER {#console-and-logger}

The most basic but also the fastest tool to identify problems is the console. Values can be printed there at "run-time" which can inform the developer about the current state of the code and makes it easy to analyze changes and potential mistakes.

In the `ModInitializer` implementing entry point class of the mod, a `LOGGER` is defined by default to print the desired output to the console.

@[code lang=java transcludeWith=:::problems:basic-logger-definition](@/reference/latest/src/main/java/com/example/docs/debug/FabricDocsReferenceDebug.java)

Whenever you need to know a value for something at a specific point in the code, use this `LOGGER` by passing the `String` value to its methods.

@[code lang=java transcludeWith=:::problems:using-logger](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

The logger has different modes of printing text to the console. Depending on which method you used for the logger, the logged line can be displayed in different colors.

```java
FabricDocsReferenceDebug.LOGGER.info("Example Text");
FabricDocsReferenceDebug.LOGGER.warn("Example Text");
FabricDocsReferenceDebug.LOGGER.error("Example Text");
```

| Color | Use case | Explanation |
| -- | -- | -- |
| Green | Info | For basic information text |
| Orange | Warning | For non-critical problems and issues |
| Red | Errors, Exceptions, Issues | For critical problems like bugs, unexpected game states, etc. |

::: info
The logger methods all have different overloads. This way you can provide more information, like a Stacktrace or other things!
:::

When, in this case, the `TestItem` is used on an entity while the game is running, it will provide the values at this current state of the mod in the console of the currently used instance.

@[code lang=java transcludeWith=:::problems:logger-usage-example](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

![logger output](/assets/develop/problem-solving/debug_01.png)

In the logged line, you can find:

- `Logged Time` - The time when the logged information was printed
- `Thread` - The thread in which it was printed. You will see the _**server thread**_ and _**render thread**_ pretty often. This can tell you on which side the code had been executed.
- `Name` - The name of the logger. It is best practice to use your mod id here so that logs and crashes see which mod caused the log to be printed
- `Stacktrace` - If provided, the error logging can also print the exception's Stacktrace

### Keeping The Console Clean {#clean-console}

Keep in mind that this will also be printed if the mod is used in any other environment. This is completely optional, but I like to create a custom `LOGGER` method and use that instead of the `LOGGER` itself to prevent printing data, which is only needed in a development environment.

@[code lang=java transcludeWith=:::problems:dev-logger](@/reference/latest/src/main/java/com/example/docs/debug/FabricDocsReferenceDebug.java)

If you are not sure if you should print something outside a debugging session, the basic rule of thumb is to only print to the logger if something went wrong. Modpack devs and users don't care too much about, e.g., if your mod's items have been initialized, but they would like to know if a datapack wasn't loaded correctly due to typos etc.

It is recommended to clean up the `LOGGER` usage as much as possible to prevent causing a headache for modpack developers and other users.

### Locating The Issues {#locating-the-issues}

The logger prints the `MOD-ID` in front of the line. The search function <kbd>CTRL / CMD + F</kbd> can be used to highlight it, making it easier to spot the problem. Missing assets, such as the Purple & Black placeholder when a texture is missing, also print their errors in the console and usually mention expected values. You can also use the search function here and look for the asset name in question.

![missing asset](/assets/develop/problem-solving/missing_asset.png)

![logger output](/assets/develop/problem-solving/debug_02.png)

## Debugging {#debugging}

### Breakpoint {#breakpoint}

A more "sophisticated" way of debugging is the usage of breakpoints in an IDE. As their name suggests, they are used to halt the executed code at specific locations and make it possible to inspect and modify the state of the software with a variety of tools.

When working with breakpoints, the instance needs to be executed using the `Debug` option instead of the `Run` option.

![logger output](/assets/develop/problem-solving/debug_03.png)

Let's use a custom item as an example again. The item is supposed to change its `CUSTOM_NAME` DataComponentType if it is used on any Stone block. But in this example the item
always does a "success" hand animation, and cobblestone doesn't seem to change the name. How can those two issues be resolved? Let's investigate...

```java
// problematic example code:

public class TestItem extends Item {

    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity user = context.getPlayer();
        BlockPos targetPos = context.getBlockPos();
        ItemStack itemStack = context.getStack();
        BlockState state = world.getBlockState(targetPos);

        if (state.isIn(ConventionalBlockTags.STONES)) {
            Text newName = Text.literal("[").append(state.getBlock().getName()).append(Text.literal("]"));
            itemStack.set(DataComponentTypes.CUSTOM_NAME, newName);
            if (user != null) {
                user.sendMessage(Text.literal("Changed Item Name"), true);
            }
        }

        return ActionResult.SUCCESS;
    }
}
```

Place a breakpoint by clicking on the line number. You can place more than one at once if needed. The breakpoint will stop right before executing the selected line.

![basic breakpoint](/assets/develop/problem-solving/debug_04.png)

Then let the running Minecraft instance execute this part of the code. You can place breakpoints while the game is running, too. In this case, the custom item needs to be used on a block. The Minecraft window should freeze, and in IntelliJ a yellow arrow right next to the Breakpoint appears. This indicates at which point the Debugger is currently.

At the bottom, a `Debug` window should open, and the `Threads & Variables` view should be selected automatically. In the `Debug` window the controls can be used to move the current execution point using the arrow icons. This way the code can be processed step by step.

![debug actions](/assets/develop/problem-solving/debug_05.png)

There are also more actions in the "More" sub-menu (three dots icon).

| Action | Description |
| -- | -- |
| Step Over | Steps to the next executed line, basically moving the instance along in the logic |
| Step Into | Steps into methods to show what is happening inside. If multiple methods are in one line, you can choose them by clicking the specific method that you want to check out. This is also necessary for lambdas. |
| Run To Cursor | Steps through the logic until it reaches the point where you placed your cursor, e.g. with your mouse, in the code. This is useful for skipping large chunks of code. |
| Show Execution Point | Moves the view of your coding window to the point where the debugger is currently at. This also works if you are currently working in other files and tabs. |

::: info
The "Step Over" <kbd>(F8)</kbd> and "Step Into" <kbd>(F7)</kbd> actions are the most common ones, so try to get used to their Key binds!
:::

If you are done with the current inspection, you can press the `Resume Program` button (green resume icon next to the arrows) or press <kbd>F9</kbd>. This will unfreeze the Minecraft instance, and further testing can be done until another Breakpoint is being executed. But let's continue looking at the `Debug` window for now.

On the top, you can see all currently running instances. You can switch between a client instance and a server instance, if you are running them at the same time. Below that, you have the debug actions and controls. You can also switch the tab to the `Console` view if you need to take a look at the logs.

On the left side, you can see the currently active thread and below that the Stacktrace.

On the right side, you can inspect and manipulate loaded values and objects. You can also hover with the mouse cursor over the values in the code. If they are in scope and are still loaded, a pop-up window will show their specific values too.

If you are interested in specific object content, you can use the small arrow icon next to them. This will unfold all their stored data.

Values are obviously not loaded if the execution point didn't pass by them or if they are located in a completely different context.

![loaded values](/assets/develop/problem-solving/debug_06.png)

The text input line above the currently loaded objects and values in the `Debug` window can be used for many different things. For example, you have access for the currently loaded objects, which allows you to use methods on them. This will add a new entry below, showing the requested data.

![object analysis](/assets/develop/problem-solving/debug_07.png)

Let's step over in our example so that the BlockState variable is loaded. We can now check if the BlockState of the targeted block is actually in the given Block Tag. On the right side of the text input field, the `+` icon adds the result permanently for the current debug session.

![boolean expression](/assets/develop/problem-solving/debug_08.png)

As we can see, the `ConventionalBlockTags.STONES` tag does not include cobblestone, since there is a separate tag for that.

### Breakpoint Toggle and Conditions {#breakpoint-condition-toggle}

Sometimes it is necessary to only halt the code when certain conditions are met. Create a basic Breakpoint and right-click it to open the Breakpoint's settings. In there, you can use boolean statements for the condition.

Hollow breakpoint icons symbolize inactive breakpoints. They won't stop the active Minecraft instance. You can toggle breakpoints either in the Breakpoint's settings pop-up window or by simply using <kbd>Middle Mouse Button</kbd> on the Breakpoint itself.

All breakpoints will be listed in IntelliJ's `Bookmarks` window.

![breakpoint menu](/assets/develop/problem-solving/debug_09.png)

![breakpoints in bookmarks](/assets/develop/problem-solving/debug_10.png)

### Reloading An Active Instance {#hotswap}

It is possible to make limited changes to the code, while a Minecraft instance is running, using the `Build > Build Project` action (Hammer icon). If you right-click in the empty space of IntelliJ's top menu bar, you can also put the icon next to the `Run Configuration` drop-down element.

![add build icon](/assets/develop/problem-solving/debug_11.png)

This process, also called "Hotswapping", requires the Minecraft instance to be started in `Debug` mode instead of the `Run` mode (see [above](./problem-solving#breakpoint)).

This way, the Minecraft instance doesn't need to be restarted again. It also makes testing screen element alignment and other feature balancing faster.
IntelliJ will notify you if the "hotswap" was successful.

![hotswap notifications](/assets/develop/problem-solving/debug_12.png)

Mixins are an exception. You can set up your Run Configuration to allow them to change at runtime too.
For more information, check out [Launching the Game](./getting-started/launching-the-game#hotswapping-classes).

Other changes can be reloaded in-game.

- changes to the `assets/` folder -> press `[F3 + T]`
- changes to the `data/` folder -> use the `/reload` command

To finish up the example from earlier, we can simply add the new condition to the statement. And if "breakpointed" correctly, we can see that we always get a "success" hand animation since we never returned anything else.

Apply the fixes and use hotswapping to see the changes in the game instantly.

@[code lang=java transcludeWith=:::problems:breakpoints](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

## Logs And Crashes {#logs-and-crashes}

The console of a previously executed instance helps with finding the issues. They are exported into the log files, located in the Minecraft instance's `logs` directory. The newest log is usually called `latest.log`. Users can send this file, for further inspection, to the mod developer or host the file content on code-hosting websites.
For example [mclo.gs](https://mclo.gs/) will keep the logs as anonymous as possible and makes it easy to analyze and share the issues.

In the development environment, you can find the logs in the `Project` window's `run > logs` folder and the crash reports in the `run > crash-reports` folder.

[see also](../players/troubleshooting/uploading-logs)

## Still Couldn't Solve The Problem? {#join-the-community}

Join the community and ask for help!

- [Official Fabric Wiki](https://fabricmc.net/wiki/start)
- [Discord server](https://discord.com/invite/v6v4pMv)
