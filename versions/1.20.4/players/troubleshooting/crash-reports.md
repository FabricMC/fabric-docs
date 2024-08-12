---
title: Crash Reports
description: Learn what to do with crash reports, and how to read them.
authors:
  - IMB11

search: false
---

# Crash Reports {#crash-reports}

::: tip
If you're having any difficulty with finding the cause of the crash, you can ask for help in the [Fabric Discord](https://discord.gg/v6v4pMv) in the `#player-support` or `#server-admin-support` channel.
:::

Crash reports are a very important part of troubleshooting issues with your game or server. They contain a lot of information about the crash, and can help you find the cause of the crash.

## Finding Crash Reports {#finding-crash-reports}

Crash reports are stored in the `crash-reports` folder in your game directory. If you are using a server, they are stored in the `crash-reports` folder in the server directory.

For third party launchers, you should refer to their documentation on where to find crash reports.

Crash reports can be found in the following locations:

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\crash-reports
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/crash-reports
```

```:no-line-numbers [Linux]
~/.minecraft/crash-reports
```

:::

## Reading Crash Reports {#reading-crash-reports}

Crash reports are very long, and can be very confusing to read. However, they contain a lot of information about the crash, and can help you find the cause of the crash.

For this guide, we will be using [this crash report](/public/assets/players/crash-report-example.txt).

::: details Show Crash Report

<<< @/public/assets/players/crash-report-example.txt{log}

:::

### Crash Report Sections {#crash-report-sections}

Crash reports consist of several sections, each separated using a header:

- `---- Minecraft Crash Report ----`, the summary of the report. This section will contain the main error that caused the crash, the time it occurred, and the relevant stack trace. This is the most important section of the crash report as the stack trace can usually contain references to the mod that caused the crash.
- `-- Last Reload --`, this section isn't really useful unless the crash occurred during a resource reload (<kbd>F3</kbd> <kbd>T</kbd>). This section will contain the time of the last reload, and the relevant stack trace of any errors that occurred during the reload process. These errors are usually caused by resource packs, and can be ignored unless they are causing issues with the game.
- `-- System Details --`, this section contains information about your system, such as the operating system, Java version, and the amount of memory allocated to the game. This section is useful for determining if you are using the correct version of Java, and if you have allocated enough memory to the game.
  - In this section, Fabric will have included a custom line that says `Fabric Mods:`, followed by a list of all the mods you have installed. This section is useful for determining if any conflicts could have occurred between mods.

### Breaking Down the Crash Report {#breaking-down-the-crash-report}

Now that we know what each section of the crash report is, we can start to break down the crash report and find the cause of the crash.

Using the example linked above, we can analyze the crash report and find the cause of the crash, including the mods that caused the crash.

The stack trace in the `---- Minecraft Crash Report ----` section is the most important in this case, as it contains the main error that caused the crash. In this case, the error is `java.lang.NullPointerException: Cannot invoke "net.minecraft.class_2248.method_9539()" because "net.minecraft.class_2248.field_10540" is null`.

With the amount of mods mentioned in the stack trace, it can be difficult to point fingers, but the first thing to do is to look for the mod that caused the crash.

<!-- TODO: show part of this file -->
<<< @/public/assets/players/crash-report-example.txt{8-9,14-15 log}

In this case, the mod that caused the crash is `snownee`, as it is the first mod mentioned in the stack trace.

However, with the amount of mods mentioned, it could mean there are some compatibility issues between the mods, and the mod that caused the crash may not be the mod that is at fault. In this case, it is best to report the crash to the mod author, and let them investigate the crash.

## Mixin Crashes {#mixin-crashes}

::: info
Mixins are a way for mods to modify the game without having to modify the game's source code. They are used by many mods, and are a very powerful tool for mod developers.
:::

When a mixin crashes, it will usually mention the mixin in the stack trace, and the class that the mixin is modifying.

Method mixins will contain `modid$handlerName` in the stack trace, where `modid` is the mod's ID, and `handlerName` is the name of the mixin handler.

```:no-line-numbers
... net.minecraft.class_2248.method_3821$$$modid$handlerName() ... // [!code focus]
```

You can use this information to find the mod that caused the crash, and report the crash to the mod author.

## What to Do with Crash Reports {#what-to-do-with-crash-reports}

The best thing to do with crash reports is to upload them to a paste site, and then share the link with the mod author, either on their issue trackers or through some form of communication (Discord etc.).

This will allow the mod author to investigate the crash, potentially reproduce it, and solve the problem that caused it.

Common paste sites that are used frequently for crash reports are:

- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
- [Pastebin](https://pastebin.com/)
