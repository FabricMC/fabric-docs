---
title: IDE Tips and Tricks
description: Useful information to handle and traverse your Project using the IDE efficiently.
authors:
  - JR1811
---

# IDE Tips and Tricks{#ide-tips-and-tricks}

This page gives useful bits of information, to speed up and ease the workflow of developers. Incorporate them into yours, to your liking.
It may take some time to learn and get used to the shortcuts and other options. You can use this page as a reference for that.

::: warning
Key binds in the text are listed as Windows shortcuts and refer to the default keymap of IntelliJ IDEA, if not stated otherwise.
Refer to the `File > Setings > Keymap` Settings or search for the functionality elsewhere if you are using a different Keyboard layout.
:::

## Traversing Projects{#traversing-projects}

### Manually{#manually}

IntelliJ has many different ways of traversing projects. If you have generated sources using the `./gradlew genSources` commands in the terminal or used the `Tasks > fabric > genSources` Gradle Tasks in the Gradle Window, you can manually go through the source files of Minecraft in the
Project Window's External Libraries.

![Gradle Task](/assets/develop/misc/using-the-ide/traversing_01.png)

The Minecraft Source code can be found if you look for `net.minecraft` in the Project Window's External Libraries. If your project uses split sources from the online [Template mod generator](https://fabricmc.net/develop/template/), there will be two sources, as indicated by the name (client/common). Additionally other sources of projects, libraries and dependencies, which are imported via the `build.gradle` file
will also be available. This method is often used when browsing for assets, tags and other files.

![External Library](/assets/develop/misc/using-the-ide/traversing_02_1.png)

![Split sources](/assets/develop/misc/using-the-ide/traversing_02_2.png)

### Search{#search}

Pressing <kbd>Shift</kbd> twice opens up a Search window. In there you can search for your project's files and classes. When Activating the checkbox `include non-project items`
or by pressing <kbd>Shift</kbd> two times again, the search will look not only in your own project, but also in other's, such as the External Libraries.

![Search Window](/assets/develop/misc/using-the-ide/traversing_03.png)

### Recent Window{#recent-window}

Another useful tool in IntelliJ is the `Recent` window. You can open it with the Shortcut <kbd>CTRL</kbd> + <kbd>E</kbd>. In there you can jump to the files, which you have already visited and open tool windows, such as the [Structure](#structure-of-a-class) or [Bookmarks](#bookmarks) window.

![Recent window](/assets/develop/misc/using-the-ide/traversing_04.png)

## Traversing Code{#traversing-code}

### Jump to Definition / Usage{#jump-to-definition-usage}

If you need to check out either the definition or the usage of variables, methods, classes, and other things, you can press <kbd>CTRL</kbd> + <kbd>Left Click</kbd>
or use <kbd>Middle Mouse Button</kbd> (pressing mouse wheel) on their name. This way you can avoid long scrolling sessions or a manual
search for a definition which is located in another file.

### Bookmarks{#bookmarks}

You can bookmark lines of code, files or even opened Editor tabs.
Especially when researching source codes, it can help out to mark spots which you want to find again quickly in the future.

Either right click a file in the `Project` window, an editor tab or the line number in a file.
Creating `Mnemonic Bookmarks` enables you to quickly switch back to those bookmarks using their hotkeys, <kbd>CTRL</kbd> and the digit, which you have chosen for it.

![set Bookmark](/assets/develop/misc/using-the-ide/traversing_05.png)

It is possible to create multiple Bookmark lists at the same time if you need to separate or order them, in the `Bookmarks` window.
[Breakpoints](./basic-problem-solving#breakpoint) will also be displayed in there.

![Bookmark window](/assets/develop/misc/using-the-ide/traversing_06.png)

## Analyzing Classes{#analyzing-classes}

### Structure of a Class{#structure-of-a-class}

By opening the `Structure` window (<kbd>Alt</kbd> + <kbd>7</kbd>) you will get an overview of your currently active class. You can see which Classes and Enums
are located in that file, which methods have been implemented and which fields and variables are declared.

Sometimes it can be helpful,
to activate the `Inherited` option at the top in the View options as well, when looking for potential methods to override.

![Structure window](/assets/develop/misc/using-the-ide/analyzing_01.png)

### Type Hierarchy of a Class{#type-hierarchy-of-a-class}

By placing the cursor on a class name and pressing <kbd>CTRL</kbd> + <kbd>H</kbd> you can open a new Type Hierarchy window, which shows all parent and child classes.

![Type Hierarchy window](/assets/develop/misc/using-the-ide/analyzing_02.png)

## Code Utility{#code-utility}

### Code Completion{#code-completion}

Code completion should be activated by default. You will automatically get the recommendations while writing your code. If you closed it by
accident or just moved your cursor to a new place, you can use <kbd>CTRL</kbd> + <kbd>Space</kbd> to open them up again.

For example, when using Lambdas, you can write them quickly using this method.

![Lambda with many parameters](/assets/develop/misc/using-the-ide/util_01.png)

### Code Generation {#code-generation}

The Generate menu can be quickly accessed with <kbd>⌘/CTRL</kbd><kbd>N</kbd>.
In a Java file, you will be able to generate constructors, getters, setters, and override or implement methods, and much more.
You can also generate accessors and invokers if you have the [Minecraft Development plugin](./getting-started/setting-up-a-development-environment#minecraft-development) installed.

In addition, you can quickly override methods with <kbd>⌘/CTRL</kbd><kbd>O</kbd> and implement methods with <kbd>⌘/CTRL</kbd><kbd>I</kbd>.

![Code generation menu in a Java file](/assets/develop/misc/using-the-ide/generate_01.png)

In a Java test file, you will be given options to generate related testing methods, as follows:

![Code generation menu in a Java test file](/assets/develop/misc/using-the-ide/generate_02.png)

### Displaying Parameters{#displaying-parameters}

Displaying parameters should be activated by default. You will automatically get the types and names of the parameters while writing your code.
If you closed them by accident or just moved your cursor to a new place, you can use <kbd>CTRL</kbd> + <kbd>P</kbd> to open them up again.

Methods and classes can have multiple implementations with different parameters, also known as Overloading. This way you can decide on which
implementation you want to use, while writing the method call.

![Displaying method parameters](/assets/develop/misc/using-the-ide/util_02.png)

### Refactoring{#refactoring}

Refactoring is the process of restructuring code without changing its runtime functionality. Renaming and Deleting parts of the code safely is a part of that,
but things like extracting parts of the code into separate methods and introducing new variables for repeated code statements are also called "refactoring".

Many IDEs have an extensive tool kit to aid in this process. In IntelliJ simply right click files or parts of the code to get access to the available refactoring tools.

![Refactoring](/assets/develop/misc/using-the-ide/refactoring_01.png)

It is especially useful to get used to the `Rename` refactoring tool's key bind, <kbd>Shift</kbd> + <kbd>F6</kbd>, since you will rename many things in the future. Using this feature,
every code occurrence of the renamed code will be renamed and will stay functionally the same.

### Search and Replace File Content{#search-and-replace-file-content}

Sometimes simpler tools are needed to edit code occurrences.

| Key bind                                          | Function                                                    |
|---------------------------------------------------| ----------------------------------------------------------- |
| <kbd>CTRL</kbd> + <kbd>F</kbd>                    | Find in current file                                        |
| <kbd>CTRL</kbd> + <kbd>R</kbd>                    | Replace in current file                                     |
| <kbd>CTRL</kbd> + <kbd>Shift</kbd> + <kbd>F</kbd> | Find in a bigger scope (can set specific file type mask)    |
| <kbd>CTRL</kbd> + <kbd>Shift</kbd> + <kbd>R</kbd> | Replace in a bigger scope (can set specific file type mask) |

If enabled, all of those tools allow for a more specific pattern matching using "[Regex](https://en.wikipedia.org/wiki/Regular_expression)".

![Regex replace](/assets/develop/misc/using-the-ide/search_and_replace_01.png)

## Comments{#comments}

Good code should be easily readable and [self-documenting](https://bytedev.medium.com/code-comment-anti-patterns-and-why-the-comment-you-just-wrote-is-probably-not-needed-919a92cf6758). Picking expressive names for variables, classes and methods can help a lot, but sometimes
comments are necessary to leave notes or **temporarily** disable code for testing.

### Prepare Shortcuts{#prepare-shortcuts}

To comment out code faster, open IntelliJ's Settings, look for the `Comment with Line Comment`
and the `Comment with Block Comment` entries, and set their Key binds to your preferences.

![Keymap settings](/assets/develop/misc/using-the-ide/comments_01.png)

Now you can highlight the necessary code and use the shortcuts, to comment the section out.

```java
// private static final int PROTECTION_BOOTS = 2;
private static final int PROTECTION_LEGGINGS = 5;
// private static final int PROTECTION_CHESTPLATE = 6;
private static final int PROTECTION_HELMET = 1;
```

```java
/*
ModItems.initialize();
ModSounds.initializeSounds();
ModParticles.initialize();
*/

private static int secondsToTicks(float seconds) {
    return (int) (seconds * 20 /*+ 69*/);
}
```

### Region Comments{#region-comments}

In IntelliJ, right next to the line numbers, you can have small [+] and [-] icons. Those can be used to temporarily collapse methods, if-statements, classes and many other things
if you are not actively working on them. To create a custom block which can be collapsed, use the `region` and `endregion` comments.

```java
// region collapse block name
    ModBlocks.initialize();
    ModBlockEntities.registerBlockEntityTypes();
    ModItems.initialize();
    ModSounds.initializeSounds();
    ModParticles.initialize();
// endregion
```

![Region collapse](/assets/develop/misc/using-the-ide/comments_02.png)

::: warning
If you notice that you are using too many of them, consider refactoring your code to make it more readable!
:::

### TODO and FIXME Notes{#todo-and-fixme-notes}

When working on code, it can come in handy to leave notes, on what still needs to be taken care of. Sometimes you may also spot
a potential issue in the code, but you don't want to stop focusing on the current problem. In this case, use the
`TODO` or `FIXME` comments.

![TODO and FIXME comments](/assets/develop/misc/using-the-ide/comments_03.png)

IntelliJ will keep track of them in the `TODO` window and may notify you, if you commit code,
which uses those type of comments.

![TODO and FIXME comments](/assets/develop/misc/using-the-ide/comments_04.png)

![Commit with TODO](/assets/develop/misc/using-the-ide/comments_05.png)

### Javadocs{#javadocs}

A great way of documenting your code is the usage of JavaDoc. JavaDocs not only provide useful information for implementation of methods and classes, but are also
deeply integrated into IntelliJ.

When hovering over method or class names, which have JavaDoc comments added to them, they will display this information in their information window.

![JavaDoc](/assets/develop/misc/using-the-ide/comments_06.png)

To get started, simply write `/**` above the method or class definition and press enter. IntelliJ will automatically generate lines for the return value
and the parameters but you can change them however you want. There are many custom functionalities available and you can also use HTML if needed.

Minecraft's `ScreenHandler` class has some examples. To toggle the render view, use the pen button near the line numbers.

![JavaDoc editing](/assets/develop/misc/using-the-ide/comments_07.png)

## Optimizing IntelliJ Further{#optimizing-intellij-further}

There are many more shortcuts and handy little tricks, which would go above the scope of this page.
Jetbrains has many good talks, videos and documentation pages about how to further customize your workspace.

### PostFix Completion{#postfix-completion}

Use PostFix Completion to alter code after writing it quickly. Often used examples contain `.not`, `.if`, `.var`, `.null`, `.nn`, `.for`, `.fori`, `.return` and `.new`. Besides the existing ones, you can also create your own in IntelliJ's Settings.

<VideoPlayer src="https://youtu.be/wvo9aXbzvy4?si=oSI1NVuOKtYI1wev" title="IntelliJ IDEA Pro Tips: Postfix Completion on YouTube"/>

### Live Templates{#live-templates}

Use Live Templates to generate your custom boilerplate code faster.

<VideoPlayer src="https://youtu.be/XhCNoN40QTU?si=dGYFr2hY7lPJ6Wge" title="IntelliJ IDEA Pro Tips: Live Templates on YouTube"/>

### More Tips and Tricks{#more-tips}

Anton Arhipov from Jetbrains also had an in depth talk about Regex Matching, Code Completion, Debugging and many other topics in IntelliJ.

<VideoPlayer src="https://youtu.be/V8lss58zBPI?si=XKl5tuUN-hCG_bTG" title="IntelliJ talk by Anton Arhipov on YouTube"/>

For even more information, check out [Jetbrains' Tips & Tricks site](https://blog.jetbrains.com/idea/category/tips-tricks/). Most of their posts are also applicable to Fabric's ecosystem.

---
