---
title: IntelliJ IDEA 提示和技巧
description: 有助于高效使用 IDE 处理和遍历项目的有用信息。
authors:
  - AnAwesomGuy
  - Earthcomputer
  - JR1811
prev:
  text: 在 IntelliJ IDEA 中生成源代码
  link: ./generating-sources
next: false
---

本页面提供一些有用的信息，加速并简化开发者的工作流程。 根据你的喜好自由发挥到你的项目中。
学习并习惯这些捷径和其他选项可能会花点时间。 你可以以此页面作为参考。

::: warning

如果没有特别说明，本文中的按键映射均指 IntelliJ IDEA 的默认按键映射预设。
如果你所使用的是不同的键盘布局，请参考 `文件 > 设置 > 按键映射` 设置，或直接搜索对应功能。

:::

## 遍历项目 {#traversing-projects}

### 手动 {#manually}

IntelliJ 有许多不同的方式来遍历项目。 如果你已使用终端中的 `./gradlew genSources` 命令生成源代码或在 Gradle 窗口中使用了 `Tasks > fabric > genSources` Gradle 任务，则可以在项目窗口的外部库中手动浏览 Minecraft 的源文件。

![Gradle 任务](/assets/develop/misc/using-the-ide/traversing_01.png)

在项目窗口的外部库查找 `net.minecraft` 即可找到 Minecraft 源代码。
如果你的项目使用了在线[模板模组生成器](https://fabricmc.net/develop/template)中的分离源码集，则会有两个分开的源代码(client/common）。
此外，通过 `build.gradle` 文件导入的其他项目源、库和依赖也将可用。
这种方法常用于浏览资源文件、标签和其他文件。

![外部库](/assets/develop/misc/using-the-ide/traversing_02_1.png)

![拆分源](/assets/develop/misc/using-the-ide/traversing_02_2.png)

### 搜索{#search}

按两次 <kbd>Shift</kbd> 键可打开“搜索”窗口。 你可以在其中搜索项目的文件和类。 当激活复选框 `包括非项目条目` 或再次按两次 <kbd>Shift</kbd> 时，搜索不仅会查找你自己的项目，还会查找其他项目，例如外部库。

你还可以使用快捷键 <kbd>⌘/CTRL</kbd> + <kbd>N</kbd> 来搜索类，以及使用快捷键 <kbd>⌘/CTRL</kbd> + <kbd>Shift</kbd> + <kbd>N</kbd> 搜索所有_文件_。

![搜索窗口](/assets/develop/misc/using-the-ide/traversing_03.png)

### 最近窗口{#recent-window}

IntelliJ 中的另一个有用的工具是 `最近` 窗口， 可以使用快捷键 <kbd>⌘/CTRL</kbd> + <kbd>E</kbd> 打开。
在那里可以跳转到已经访问过的文件并打开工具窗口，例如[结构](#structure-of-a-class)或[书签](#bookmarks)窗口。

![最近窗口](/assets/develop/misc/using-the-ide/traversing_04.png)

## 遍历代码{#traversing-code}

### 跳转至定义/用法 {#jump-to-definition-usage}

如果你需要查看变量、方法、类和其他内容的定义或用法，可以按 <kbd>⌘/CTRL</kbd> + <kbd>左键单击/B</kbd> 或在其名称上使用<kbd>鼠标中键</kbd>（按下鼠标滚轮）。 这样，你就可以避免长时间滚动会话或手动搜索位于另一个文件中的定义。

你还可以使用 <kbd>⌘/CTRL</kbd> + <kbd>⌥/Shift</kbd> + <kbd>左键单击/B</kbd> 查看某个类或接口的所有实现。

### 书签 {#bookmarks}

你可以为代码行、文件甚至打开的“编辑器”选项卡添加书签。
特别是在研究源代码时，书签可以帮助你标记将来想要再次快速找到的位置。

右键单击 `项目` 窗口、编辑器选项卡或文件中的行号中的文件。
创建`助记书签`使你能够使用其热键 <kbd>⌘/CTRL</kbd> 和你为其选择的数字快速切换回这些书签。

![设置书签](/assets/develop/misc/using-the-ide/traversing_05.png)

如果你需要分离或排序书签列表，可以在`书签`窗口中同时创建多个书签列表。
[断点](../../debugging#breakpoints)也将显示在那里。

![书签窗口](/assets/develop/misc/using-the-ide/traversing_06.png)

## 分析类 {#analyzing-classes}

### 类的结构 {#structure-of-a-class}

打开`结构`窗口（<kbd>⌘/Alt</kbd> + <kbd>7</kbd>），可以获得当前活动类的概览。 你可以看到该文件中有哪些类和枚举、实现了哪些方法以及声明了哪些字段和变量。

有时，在寻找可能要覆盖的方法时，在“视图”选项顶部激活`继承`选项也会很有帮助。

![结构窗口](/assets/develop/misc/using-the-ide/analyzing_01.png)

### 类的类型层次结构 {#type-hierarchy-of-a-class}

将光标放在类名上并按 <kbd>⌘/CTRL</kbd> + <kbd>H</kbd>，可以打开一个新的类型层次结构窗口，其中显示所有父类和子类。

![类型层次结构窗口](/assets/develop/misc/using-the-ide/analyzing_02.png)

## 代码实用工具 {#code-utility}

### 代码补全 {#code-completion}

代码补全应默认激活。 你将在编写代码时自动获得建议。
如果你不小心把它关闭了或者不慎将光标移动到了新位置，可以使用 <kbd>⌘/CTRL</kbd> + <kbd>空格</kbd> 重新打开。

例如，当使用 lambda 表达式时，你可以使用这种方法快速编写。

![具有多个参数的 Lambda](/assets/develop/misc/using-the-ide/util_01.png)

### 代码生成 {#code-generation}

可以通过 <kbd>Alt</kbd> + <kbd>Insert</kbd>（在 macOS 上为 <kbd>⌘ Command</kbd> + <kbd>N</kbd>）快速访问生成菜单，或者转到顶部的 `代码` 并选择 `生成`。
在 Java 文件中，你可以生成构造器、getter、setter、重写或实现方法，等等。
如果安装了 [Minecraft Development 插件](./setting-up#installing-idea-plugins)，你还可以生成访问器和调用器。

此外，你可以使用 <kbd>⌘/CTRL</kbd> + <kbd>O</kbd> 快速重写方法，并使用 <kbd>⌘/CTRL</kbd> + <kbd>I</kbd> 实现方法。

![Java 文件中的代码生成菜单](/assets/develop/misc/using-the-ide/generate_01.png)

在 Java 测试文件中，会有选项生成相关的测试方法，就像下面这样：

![Java 测试文件中的代码生成菜单](/assets/develop/misc/using-the-ide/generate_02.png)

### 显示参数 {#displaying-parameters}

显示参数应默认激活。 你将在编写代码时自动获取参数的类型和名称。
如果你意外把它关闭了或者不慎将光标移动到了新位置，可以使用 <kbd>⌘/CTRL</kbd> + <kbd>P</kbd> 再次打开。

方法和类可以有具有不同参数的多个实现，也称为“重载”。 这样，你可以在编写方法调用时决定要使用哪种实现。

![显示方法参数](/assets/develop/misc/using-the-ide/util_02.png)

### 重构 {#refactoring}

重构是在不改变代码运行时功能的情况下重组代码的过程。 安全地重命名和删除部分代码是其中的一部分，但将部分代码提取到单独的方法中以及为重复的代码语句引入新变量等操作也称为“重构”。

许多 IDE 都有丰富的工具包来帮助完成这一过程。 在 IntelliJ 中，只需右键单击文件或部分代码即可访问可用的重构工具。

![重构](/assets/develop/misc/using-the-ide/refactoring_01.png)

习惯`重命名`重构工具的按键绑定 <kbd>Shift</kbd>+<kbd>F6</kbd> 特别有用，因为你将来可能会重命名很多东西。
使用这一功能，重命名的代码中每次出现的地方都会被重命名，并保持功能不变。

你还可以根据你的代码风格重新格式化代码。
要这么做，请选择要重新格式化的代码（如果未选择任何内容，则将重新格式化整个文件）并按 <kbd>⌘/CTRL</kbd> + <kbd>⌥/ALT</kbd> + <kbd>L</kbd>。
要更改 IntelliJ 格式化代码的方式，参见 `文件 > 设置 > 编辑器 > 代码风格 > Java` 中的设置。

#### 上下文操作 {#context-actions}

上下文操作允许根据上下文重构特定代码段。
要使用它，只需将光标移动到要重构的区域，然后按 <kbd>⌥/ALT</kbd> + <kbd>Enter</kbd> 或者单击左侧的灯泡。
将出现一个弹出窗口，会显示所选代码可用的上下文操作。

![上下文操作示例](/assets/develop/misc/using-the-ide/context_actions_01.png)

![上下文操作示例](/assets/develop/misc/using-the-ide/context_actions_02.png)

### 查找和替换文件内容{#search-and-replace-file-content}

有时需要用更简单的工具来编辑代码出现的各个地方。

| 按键绑定                                                | 功能                       |
| --------------------------------------------------- | ------------------------ |
| <kbd>⌘/CTRL</kbd> + <kbd>F</kbd>                    | 在当前文件中查找                 |
| <kbd>⌘/CTRL</kbd> + <kbd>R</kbd>                    | 在当前文件中替换                 |
| <kbd>⌘/CTRL</kbd> + <kbd>Shift</kbd> + <kbd>F</kbd> | 在更大的范围内查找（可以设置特定的文件类型掩码） |
| <kbd>⌘/CTRL</kbd> + <kbd>Shift</kbd> + <kbd>R</kbd> | 在更多的范围内替换（可以设置特定的文件类型掩码） |

如果使用，所有这些工具都允许通过[正则表达式](https://zh.wikipedia.org/zh-cn/%E6%AD%A3%E5%88%99%E8%A1%A8%E8%BE%BE%E5%BC%8F)进行更具体的模式匹配。

![正则表达式替换](/assets/develop/misc/using-the-ide/search_and_replace_01.png)

### 其他有用的按键绑定{#other-keybinds}

选择一些文本并使用<kbd>⌘/CTRL</kbd>+<kbd>Shift</kbd>+<kbd>↑ 上 / ↓ 下</kbd>可以将选择的文本向上或者向下移动。

在 IntelliJ 中，“重做”的按键绑定不是常用的<kbd>⌘/CTRL</kbd>+<kbd>Y</kbd>（删除行），
相反地，它可能是 <kbd> ⌘/CTRL</kbd>+<kbd>Shift</kbd>+<kbd>Z</kbd> 可以在**按键映射**中修改。

键盘快捷键的更多信息，可见 [IntelliJ 的文档](https://www.jetbrains.com/help/idea/mastering-keyboard-shortcuts.html)。

## 注释 {#comments}

好的代码应该可读并且[是自解释的](https://bytedev.medium.com/code-comment-anti-patterns-and-why-the-comment-you-just-wrote-is-probably-not-needed-919a92cf6758)。
变量、类、方法选择能清晰表达的名称，这会很有帮助，但有时也需要用注释来留下记录，或者**暂时**把代码禁用以做测试。

要更快地将代码注释掉，可以选择一些文本，并使用<kbd>⌘/CTRL</kbd>+<kbd>/</kbd>（行注释）和<kbd>⌘/CTRL</kbd>+<kbd>⌥/Shift</kbd>+<kbd>/</kbd>（块注释）按键绑定。

现在你可以高亮必要的代码（或者只是鼠标指在上面），并使用快捷键将这一段注释掉。

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

### 代码折叠{#code-folding}

在 IntelliJ 中，在行号旁边，可能会看到小的箭头图标。
这些可以用于暂时折叠方法、if-语句、类和很多其他东西，如果这些你并不是正在处理这些。
要创建自定义的可以被折叠的块，使用 `region` 和 `endregion` 注释。

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

::: warning

如果你发现这些用太多了，考虑重构你的代码从而让它更加可读。

:::

### 禁用格式化器{#disabling-formatter}

注释还可以在上述代码重构期间禁用格式化程序，方法是像这样围绕一段代码：

```java
//formatter:off (disable formatter)
    public static void disgustingMethod() { /* ew this code sucks */ }
//formatter:on (re-enable the formatter)
```

### 禁止检查{#noinspection}

`//noinspection` 注释可用于禁止检查和警告。
它们在功能上与 `@SuppressWarnings` 注释相同，但没有注释的限制，并且可以在语句上使用。

```java
// below is bad code and IntelliJ knows that

@SuppressWarnings("rawtypes") // annotations can be used here
List list = new ArrayList();

//noinspection unchecked (annotations cannot be here so we use the comment)
this.processList((List<String>)list);

//noinspection rawtypes,unchecked,WriteOnlyObject (you can even suppress multiple!)
new ArrayList().add("bananas");
```

::: warning

如果你注意到你禁止了太多警告，请考虑重写代码以免产生太多警告！

:::

### TODO 和 FIXME 注释{#todo-and-fixme-notes}

写代码时，把需要关心的东西留下注释也是很方便的。 有时你可以也注意到代码中有潜在的问题，但是不想停下当前的事情去关注这个问题。 这时，可以使用 `TODO` 或 `FIXME` 注释。

![TODO 和 FIXME 注释](/assets/develop/misc/using-the-ide/comments_03.png)

IntelliJ 会在 `TODO` 窗口中跟踪，并如果在提交使用了这些类型的注释的代码时提醒你。

![TODO 和 FIXME 注释](/assets/develop/misc/using-the-ide/comments_04.png)

![带有 TODO 的提交](/assets/develop/misc/using-the-ide/comments_05.png)

### Javadoc{#javadocs}

给你的代码写文档最好的方式是使用 JavaDoc。
JavaDoc 不仅提供方法和类的实现的有用信息，还能与 IntelliJ 深度融合。

当鼠标悬念在有添加了 JavaDoc 注释的方法或类名上时，信息窗口中会显示这个信息。

![JavaDoc](/assets/develop/misc/using-the-ide/comments_06.png)

要开始写，先在方法或类的定义上方写上 `/**`，然后按 <kbd>Enter</kbd>。 IntelliJ 会自动为返回值及各参数生成行，但你也可以随意改变。 有很多可用的自定义功能，还可以在需要时使用 HTML。

Minecraft 的 `ScreenHandler` 类有些例子。 要开启渲染视图，可使用行号旁边的笔按钮。

![JavaDoc 编辑](/assets/develop/misc/using-the-ide/comments_07.png)

## 查看字节码 {#viewing-bytecode}

在编写 mixins 时，查看字节码是必要的。 你可以通过在编辑器中打开一个库类（例如 Minecraft 类），然后在 `View` 菜单中选择 `Show Bytecode` 来查看该类的字节码。

![“View”菜单中的“Show bytecode”按钮](/assets/develop/getting-started/intellij/show_bytecode.png)

![java/lang/String.class 的字节码](/assets/develop/getting-started/intellij/bytecode_example.png)

## 进一步优化 IntelliJ {#optimizing-intellij-further}

还有很多捷径和便捷的小技巧，这个页面没有提到。
Jetbrains 有很多关于如何进一步个性化你的工作空间的讨论、视频和文档页面。

### 后缀补全{#postfix-completion}

写了代码后，可以用后缀补全来快速修改。 常用的一些例子有 `.not`、`.if`、`.var`、`.null`、`.nn`、`.for`、`.fori`、`.return` 和 `.new`。
除了这些已有的之外，你也可以在 IntelliJ 的设置中创建你自己的。

<VideoPlayer src="https://youtu.be/wvo9aXbzvy4">IntelliJ IDEA 专业技巧：YouTube 上的 Postfix 补全</VideoPlayer>

### 实时模板 {#live-templates}

使用实时模板可以更快地生成自定义样板代码。

<VideoPlayer src="https://youtu.be/XhCNoN40QTU">IntelliJ IDEA 专业技巧：YouTube 上的实时模板</VideoPlayer>

### 更多提示和技巧{#more-tips}

Jetbrains 的 Anthon Arhipov 也有关于正则表达式匹配、代码补全、调试和其他 IntelliJ 话题的深入讨论。

<VideoPlayer src="https://youtu.be/V8lss58zBPI">YouTube 上的 Anton Arhipov 的 IntelliJ talk</VideoPlayer>

如需更多信息，请查看 [Jetbrains' Tips & Tricks 网站](https://blog.jetbrains.com/idea/category/tips-tricks) 和 [IntelliJ 文档](https://www.jetbrains.com/help/idea/getting-started)。
大多数这些帖子都适用于 Fabric 的生态系统中。
