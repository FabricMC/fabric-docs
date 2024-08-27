---
title: IDE 提示和技巧
description: 让你在 IDE 中高效处理并遍历你的项目的有用信息
authors:
  - JR1811
---

# IDE 提示和技巧{#ide-tips-and-tricks}

本页面提供一些有用的信息，加速并简化开发者的工作流程。 根据你的喜好自由发挥到你的项目中。
学习并习惯这些捷径和其他选项可能会花点时间。 你可以以此页面作为参考。

:::warning
除非另有说明，本文中所述快捷键均绑定为 Windows 快捷键，并参考 IntelliJ IDEA 的默认按键映射。
如果你使用不同的键盘布局，请参阅 `文件 > 设置 > 按钮映射` 设置或在其他地方搜索相关功能。
:::

## 遍历项目{#traversing-projects}

### 手动{#manually}

IntelliJ 有许多不同的方式来遍历项目。 如果你已使用终端中的 `./gradlew genSources` 命令生成源代码或在 Gradle 窗口中使用了 `Tasks > fabric > genSources` Gradle 任务，则可以在项目窗口的外部库中手动浏览 Minecraft 的源文件。

![Gradle 任务](/assets/develop/misc/using-the-ide/traversing_01.png)

如果你在项目窗口的外部库中搜索 `net.minecraft`，则可以找到 Minecraft 源代码。 如果你的项目使用来自在线[模板模组生成器](https://fabricmc.net/develop/template/)的拆分源，则会有两个源，如名称所示（client/common）。 此外，通过 `build.gradle` 文件导入的其他项目源、库和依赖项也将可用。 这种方法常用于浏览资源文件、标签和其他文件。

![外部库](/assets/develop/misc/using-the-ide/traversing_02_1.png)

![拆分源](/assets/develop/misc/using-the-ide/traversing_02_2.png)

### 搜索{#search}

按两次 <kbd>Shift</kbd> 键可打开“搜索”窗口。 你可以在其中搜索项目的文件和类。 当激活复选框 `包括非项目条目` 或再次按两次 <kbd>Shift</kbd> 时，搜索不仅会查找你自己的项目，还会查找其他项目，例如外部库。

![搜索窗口](/assets/develop/misc/using-the-ide/traversing_03.png)

### 最近窗口{#recent-window}

IntelliJ 中的另一个有用的工具是 `最近` 窗口， 可以使用快捷键 <kbd>CTRL</kbd> + <kbd>E</kbd> 打开。 在那里可以跳转到已经访问过的文件并打开工具窗口，例如[结构](#structure-of-a-class)或[书签](#bookmarks)窗口。

![最近窗口](/assets/develop/misc/using-the-ide/traversing_04.png)

## 遍历代码{#traversing-code}

### 跳转至定义/用法{#jump-to-definition-usage}

如果你需要查看变量、方法、类和其他内容的定义或用法，可以按 <kbd>CTRL</kbd> + <kbd>左键单击</kbd>
或在其名称上使用 <kbd>鼠标中键</kbd>（按下鼠标滚轮）。 这样，你就可以避免长时间滚动会话或手动搜索位于另一个文件中的定义。

### 书签{#bookmarks}

你可以为代码行、文件甚至打开的“编辑器”选项卡添加书签。
特别是在研究源代码时，书签可以帮助你标记将来想要再次快速找到的位置。

右键单击 `项目` 窗口、编辑器选项卡或文件中的行号中的文件。
创建`助记书签`使你能够使用其快捷键 <kbd>CTRL</kbd> 和你为其选择的数字快速切换回这些书签。

![设置书签](/assets/develop/misc/using-the-ide/traversing_05.png)

如果你需要分离或排序书签列表，可以在`书签`窗口中同时创建多个书签列表。
[断点](./basic-problem-solving#breakpoint) 也将显示在那里。

![书签窗口](/assets/develop/misc/using-the-ide/traversing_06.png)

## 分析类{#analyzing-classes}

### 类的结构{#structure-of-a-class}

打开`结构`窗口（<kbd>Alt</kbd> + <kbd>7</kbd>），可以获得当前活动类的概览。 你可以看到该文件中有哪些类和枚举、实现了哪些方法以及声明了哪些字段和变量。

有时，在寻找可能要覆盖的方法时，在“视图”选项顶部激活`继承`选项也会很有帮助。

![结构窗口](/assets/develop/misc/using-the-ide/analyzing_01.png)

### 类的类型层次结构{#type-hierarchy-of-a-class}

将光标放在类名上并按 <kbd>CTRL</kbd> + <kbd>H</kbd>，可以打开一个新的类型层次结构窗口，其中显示所有父类和子类。

![类型层次结构窗口](/assets/develop/misc/using-the-ide/analyzing_02.png)

## 代码实用工具{#code-utility}

### 代码补全{#code-completion}

代码补全应该默认激活。 你将在编写代码时自动获得建议。 如果不小心关闭或者只是将光标移动到了新的位置，可以使用 <kbd>CTRL</kbd> + <kbd>空格</kbd> 再次打开。

例如，使用 Lambda 时可以用这种方法快速编写。

![具有多个参数的 Lambda](/assets/develop/misc/using-the-ide/util_01.png)

### 代码生成{#code-generation}

“生成”菜单可通过 <kbd>⌘/CTRL</kbd><kbd>N</kbd> 快速访问。
在 Java 文件中，你可以生成构造器、getter、setter、重写或实现方法，等等。
你还可以生成 accessor 和 invoker，如果有安装了 [Minecraft Development 插件](./getting-started/setting-up-a-development-environment#minecraft-development)的话。

此外，你还可以通过 <kbd>⌘/CTRL</kbd><kbd>O</kbd> 快速重写方法，或通过 <kbd>⌘/CTRL</kbd><kbd>I</kbd> 实现方法。

![Java 文件中的代码生成菜单](/assets/develop/misc/using-the-ide/generate_01.png)

在 Java 测试文件中，会有选项生成相关的测试方法，就像下面这样：

![Java 测试文件中的代码生成菜单](/assets/develop/misc/using-the-ide/generate_02.png)

### 显示参数{#displaying-parameters}

显示参数应默认激活。 你将在编写代码时自动获取参数的类型和名称。
如果不小心关闭或者只是将光标移动到了新的位置，可以使用 <kbd>CTRL</kbd> + <kbd>P</kbd> 再次打开。

方法和类可以有具有不同参数的多个实现，也称为“重载”。 这样，你可以在编写方法调用时决定要使用哪种实现。

![显示方法参数](/assets/develop/misc/using-the-ide/util_02.png)

### 重构{#refactoring}

重构是在不改变代码运行时功能的情况下重组代码的过程。 安全地重命名和删除部分代码是其中的一部分，但将部分代码提取到单独的方法中以及为重复的代码语句引入新变量等操作也称为“重构”。

许多 IDE 都有丰富的工具包来帮助完成这一过程。 在 IntelliJ 中，只需右键单击文件或部分代码即可访问可用的重构工具。

![重构](/assets/develop/misc/using-the-ide/refactoring_01.png)

重构工具“重命名”的快捷键 <kbd>Shift</kbd> + <kbd>F6</kbd>，习惯之后会非常有用，因为未来你会需要重命名很多东西。 使用这一功能，重命名的代码中每次出现的地方都会被重命名，并保持功能不变。

### 查找和替换文件内容{#search-and-replace-file-content}

有时需要用更简单的工具来编辑代码出现的各个地方。

| 按键绑定                                              | 功能                       |
| ------------------------------------------------- | ------------------------ |
| <kbd>CTRL</kbd> + <kbd>F</kbd>                    | 在当前文件中查找                 |
| <kbd>CTRL</kbd> + <kbd>R</kbd>                    | 在当前文件中替换                 |
| <kbd>CTRL</kbd> + <kbd>Shift</kbd> + <kbd>F</kbd> | 在更大的范围内查找（可以设置特定的文件类型掩码） |
| <kbd>CTRL</kbd> + <kbd>Shift</kbd> + <kbd>R</kbd> | 在更多的范围内替换（可以设置特定的文件类型掩码） |

如果启用，还可以使用“[正则表达式](https://zh.wikipedia.org/wiki/正则表达式)”以进行更加专门的样式匹配。

![正则表达式替换](/assets/develop/misc/using-the-ide/search_and_replace_01.png)

## 注释{#comments}

好的代码应该可读并且[是自解释的](https://bytedev.medium.com/code-comment-anti-patterns-and-why-the-comment-you-just-wrote-is-probably-not-needed-919a92cf6758)。 变量、类、方法选择能清晰表达的名称，这会很有帮助，但有时也需要用注释来留下记录，或者**暂时**把代码禁用以做测试。

### 准备快捷键{#prepare-shortcuts}

要更快地将代码注释掉，打开 IntelliJ 的设置，找到“使用行注释进行注释”和“使用块注释进行注释”，并根据自己的喜好设置按键绑定。

![按键映射设置](/assets/develop/misc/using-the-ide/comments_01.png)

现在你可以高亮必要的代码，并使用快捷键将这一段注释掉。

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

### 区域注释{#region-comments}

在 IntelliJ 中，在行号旁边，可以会有小的 [+] 和 [-] 图标。 这些可以用于暂时折叠方法、if-语句、类和很多其他东西，如果这些你并不是正在进行工作。 要创建自定义的可以被折叠的块，使用 `region` 和 `endregion` 注释。

```java
// region collapse block name
    ModBlocks.initialize();
    ModBlockEntities.registerBlockEntityTypes();
    ModItems.initialize();
    ModSounds.initializeSounds();
    ModParticles.initialize();
// endregion
```

![区域折叠](/assets/develop/misc/using-the-ide/comments_02.png)

:::warning
如果你发现这些用太多了，考虑重构你的代码从而让它更加可读。
:::

### TODO 和 FIXME 注释{#todo-and-fixme-notes}

写代码时，把需要关心的东西留下注释也是很方便的。 有时你可以也注意到代码中有潜在的问题，但是不想停下当前的事情去关注这个问题。 这时，可以使用 `TODO` 或 `FIXME` 注释。

![TODO 和 FIXME 注释](/assets/develop/misc/using-the-ide/comments_03.png)

IntelliJ 会在 `TODO` 窗口中跟踪，并如果在提交使用了这些类型的注释的代码时提醒你。

![TODO 和 FIXME 注释](/assets/develop/misc/using-the-ide/comments_04.png)

![带有 TODO 的提交](/assets/develop/misc/using-the-ide/comments_05.png)

### Javadoc{#javadocs}

给你的代码写文档最好的方式是使用 JavaDoc。 JavaDoc 不仅提供方法和类的实现的有用信息，还能与 IntelliJ 深度融合。

当鼠标悬念在有添加了 JavaDoc 注释的方法或类名上时，信息窗口中会显示这个信息。

![JavaDoc](/assets/develop/misc/using-the-ide/comments_06.png)

要开始写，先在方法或类的定义上方写上 `/**`，然后按 Enter。 IntelliJ 会自动为返回值及各参数生成行，但你也可以随意改变。 有很多可用的自定义功能，还可以在需要时使用 HTML。

Minecraft 的 `ScreenHandler` 类有些例子。 要开启渲染视图，可使用行号旁边的笔按钮。

![JavaDoc 编辑](/assets/develop/misc/using-the-ide/comments_07.png)

## 进一步优化 IntelliJ{#optimizing-intellij-further}

还有很多捷径和便捷的小技巧，这个页面没有提到。
Jetbrains 有很多关于如何进一步个性化你的工作空间的讨论、视频和文档页面。

### PostFix Completion{#postfix-completion}

写了代码后，可以用 PostFix Completion 来快速修改。 常用的一些例子有 `.not`、`.if`、`.var`、`.null`、`.nn`、`.for`、`.fori`、`.return` 和 `.new`。 除了这些已有的之外，你也可以在 IntelliJ 的设置中创建你自己的。

<VideoPlayer src="https://youtu.be/wvo9aXbzvy4?si=oSI1NVuOKtYI1wev" title="IntelliJ IDEA Pro Tips: Postfix Completion on YouTube"/>

### 实时模板{#live-templates}

使用实时模板可以更快地生成自定义样板代码。

<VideoPlayer src="https://youtu.be/XhCNoN40QTU?si=dGYFr2hY7lPJ6Wge" title="IntelliJ IDEA Pro Tips: Live Templates on YouTube"/>

### 更多提示和技巧{#more-tips}

Jetbrains 的 Anthon Arhipov 也有关于正则表达式匹配、代码补全、调试和其他 IntelliJ 话题的深入讨论。

<VideoPlayer src="https://youtu.be/V8lss58zBPI?si=XKl5tuUN-hCG_bTG" title="IntelliJ talk by Anton Arhipov on YouTube"/>

更多信息请看 [Jetbrains' Tips & Tricks site](https://blog.jetbrains.com/zh-hans/idea/category/tips-tricks/)。 大多数这些帖子都适用于 Fabric 的生态系统中。

---
