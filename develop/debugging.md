---
title: Debugging Mods
description: Learn all about logging, breakpoints and other useful debugging features.
authors:
  - its-miroma
  - JR1811
---

In programming, even the best are bound to encounter issues, bugs and mistakes.

This guide outlines some general steps that you can take to potentially identify and resolve those, even without the help of others. Solving problems on your own can teach you many things, and will also feel rewarding.

However, if you are stuck, unable to find fixes by yourself, there is no problem in asking others for help!

## Console and LOGGER {#console-and-logger}

The simplest and fastest way to locate problems is logging to the console.

Values can be printed there at runtime, informing the developer about the current state of the code, and making it easy to analyze changes and potential mistakes.

In the `ModInitializer`-implementing entrypoint class of the mod, a `LOGGER` is defined by default to print the desired output to the console.

@[code lang=java transcludeWith=:::problems:basic-logger-definition](@/reference/latest/src/main/java/com/example/docs/debug/ExampleModDebug.java)

Whenever you need to know a value for something at any point in the code, use this `LOGGER` by passing a `String` to its methods.

@[code lang=java transcludeWith=:::problems:using-logger](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

The logger supports multiple modes of printing text to the console. Depending on which mode you use, the logged line will be displayed in different colors.

```java
ExampleModDebug.LOGGER.info("Neutral, informative text...");
ExampleModDebug.LOGGER.warn("Non-critical issues..."); // [!code warning]
ExampleModDebug.LOGGER.error("Critical exceptions, bugs..."); // [!code error]
```

::: info

All logger modes support multiple overloads; this way you can provide more information like a stack trace!

:::

For example, let's make sure that, when the `TestItem` is used on an entity, it will output its current state in console.

@[code lang=java transcludeWith=:::problems:logger-usage-example](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

![Console showing logged output](/assets/develop/debugging/debug_01.png)

In the logged line, you can find:

- `Time` - The time when the logged information was printed
- `Thread` - The thread in which it was printed. You will often see a _**server thread**_ and a _**render thread**_; this tells you on which side the code had been executed
- `Name` - The name of the logger. It is best practice to use your mod id here, so that logs and crashes show which mod logged
- `Message` - This should be concise but descriptive. Include relevant values or context
- `Stack Trace` - If provided with an exception's stack trace, the logger can print that too

### Keeping The Console Clean {#clean-console}

Keep in mind that all of these will also be printed if the mod is used in any other environment.

If the data you are logging is only relevant in development, it might be useful to create a custom `LOGGER` method and use it to avoid printing data in production.

@[code lang=java transcludeWith=:::problems:dev-logger](@/reference/latest/src/main/java/com/example/docs/debug/ExampleModDebug.java)

If you are unsure whether to log outside a debugging session, a good rule of thumb is to only log if something went wrong. Modpack devs and users don't care too much about, for example, items initializing; they would rather know if, for example, a datapack failed to load correctly.

### Locating Issues {#locating-issues}

The logger prints your mod's ID in front of the line. You can search (<kbd>⌘/CTRL</kbd>+<kbd>F</kbd>) for it to highlight it.

Missing assets and textures (the Purple & Black placeholder) also log a warning to the console, and usually mention the expected values.

![Missing asset](/assets/develop/debugging/missing_asset.png)

![Logger output](/assets/develop/debugging/debug_02.png)

## Breakpoints {#breakpoints}

A more sophisticated way of debugging is using breakpoints in an IDE. As their name suggests, they are used to halt the executed code at specific points to allow for inspecting and modifying the state of the software.

::: tip

To use breakpoints, you must execute the instance using the `Debug` option instead of the `Run` option:

![Debug button](/assets/develop/debugging/debug_03.png)

:::

Let's use our custom item as an example again. The item's `CUSTOM_NAME` `DataComponentType` is supposed to change if it is used on any Stone block.
However, in this example, the item always seems to trigger a "success" hand animation, yet cobblestone doesn't seem to change the name.

How can we resolve these two issues? Let's investigate...

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

Place a breakpoint by clicking on the line number. You can place more than one at once if needed. The breakpoint will stop right before executing the selected line.

![Breakpoint set](/assets/develop/debugging/debug_04.png)

Then let the running Minecraft instance execute this part of the code. You can place breakpoints while the game is running, too.

In this case, the custom item needs to be used on a block. The Minecraft window should freeze, and in IntelliJ a yellow arrow will appear right next to the breakpoint, indicating that the debugger has reached that point.

At the bottom, a `Debug` window should open, and the `Threads & Variables` view should be selected automatically. You can use the arrow controls in the `Debug` window to move the execution point. This way of moving through the code is called "stepping".

![Debug controls](/assets/develop/debugging/debug_05.png)

| Action               | Description                                                                                                                                                                            |
| -------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Step Over            | Steps to the next executed line, basically moving the instance along in the logic                                                                                                      |
| Step Into            | Steps into a method to show what is happening inside. If there are multiple methods on one line, you can choose which to step into by clicking it. This is also necessary for lambdas. |
| Run To Cursor        | Steps through the logic until it reaches your cursor in the code. This is useful for skipping large chunks of code.                                                                    |
| Show Execution Point | Moves the view of your coding window to the point where the debugger is currently at. This also works if you are currently working in other files and tabs.                            |

::: info

The "Step Over" <kbd>F8</kbd> and "Step Into" <kbd>F7</kbd> actions are the most common ones, so try to get used to the shortcuts!

:::

If you are done with the current inspection, you can press the green `Resume Program` button (<kbd>F9</kbd>). This will unfreeze the Minecraft instance, and further testing can be done until another breakpoint is hit. But let's keep looking at the `Debug` window for now.

On the top, you can see all currently running instances. If both are running, you can switch between the client and the server instance. Below that, you have the debug actions and controls. You can also switch to the `Console` view if you need to take a look at the logs.

On the left side, you can see the currently active thread, and the stack trace below that.

On the right side, you can inspect and manipulate loaded values and objects. You can also hover over the values in the code: if they are in scope and are still loaded, a pop-up window will show their specific values too.

If you are interested in the content of a specific object, you can use the small arrow icon next to it. This will unfold all nested data.

Values are obviously not loaded if the execution point didn't pass by them or if they are located in a completely different context.

![A loaded value](/assets/develop/debugging/debug_06.png)

You can use the input line in the `Debug` window for many different things, for example you can access currently loaded objects and use methods on them. This will add a new entry below, showing the requested data.

![Object analysis](/assets/develop/debugging/debug_07.png)

Let's step over in our example, so that the `BlockState` variable is loaded. We can now check if the `BlockState` of the targeted block is actually in the `Block` tag.

::: tip

Press the `+` icon on the right side of the input line to pin the result for the current debug session.

:::

![Boolean expression](/assets/develop/debugging/debug_08.png)

As we can see, the `ConventionalBlockTags.STONES` tag does not include cobblestone because there is a separate tag for that.

### Breakpoint Toggle and Conditions {#breakpoint-condition-toggle}

Sometimes you only need to halt code when certain conditions are met. For that, create a breakpoint and right-click it to open its settings. In there you can set a boolean statement as the condition.

Hollow breakpoint icons indicate inactive breakpoints, which won't halt the active Minecraft instance. You can toggle breakpoints either in the breakpoint's settings pop-up window or simply by middle-clicking the breakpoint itself.

All breakpoints will be listed in IntelliJ's `Bookmarks` window.

![Breakpoint menu](/assets/develop/debugging/debug_09.png)

![Breakpoints in bookmarks](/assets/develop/debugging/debug_10.png)

### Hotswapping An Active Instance {#hotswapping}

You can make limited changes to the code while an instance is running, using the `Build > Build Project` action with the hammer icon. You can also put the icon next to the `Run Configuration` drop-down element by right-clicking the empty space on IntelliJ's top menu bar.

![Adding build button to the top bar](/assets/develop/debugging/debug_11.png)

This process, also called "hotswapping", requires the Minecraft instance to be started in `Debug` mode instead of the `Run` mode (see [above](#breakpoints)).

With this, you don't need to restart the Minecraft instance again. It also makes testing screen element alignment and other feature balancing faster.
IntelliJ will notify you if the "hotswap" was successful.

![Hotswap status notifications](/assets/develop/debugging/debug_12.png)

Mixins are an exception. You can set up your Run Configuration to allow them to change at runtime too.
For more information, check out [Hotswapping Mixins](./getting-started/launching-the-game#hotswapping-mixins).

Other changes can be reloaded in-game.

- changes to the `assets/` folder -> press <kbd>F3</kbd>+<kbd>T</kbd>
- changes to the `data/` folder -> use the `/reload` command

To finish up with the example from earlier, let's add a condition to the statement. Once we hit the breakpoint, we can see that we always get a "success" hand animation because we never returned anything else.

Apply the fixes and use hotswapping to see the changes in the game instantly.

@[code lang=java transcludeWith=:::problems:breakpoints](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

## Logs And Crashes {#logs-and-crashes}

Consoles of previously executed instances are exported as log files, located in the Minecraft instance's `logs` directory. The newest log is usually called `latest.log`.

Users will be able to share this file with the mod's developer for further inspection, as explained in [Uploading Logs](../players/troubleshooting/uploading-logs).

In the development environment, you can find previous logs in the `Project` window's `run > logs` folder, and crash reports in the `run > crash-reports` folder.

## Ask The Community! {#ask-the-community}

Still couldn't figure out what's going on? You can join the [Fabric Discord Server](https://discord.fabricmc.net/) and have a chat with the community!

You may also want to check out the [Official Fabric Wiki](https://wiki.fabricmc.net/start) for more advanced queries.
