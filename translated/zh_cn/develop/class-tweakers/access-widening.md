---
title: 访问加宽
description: 学习如何通过类调整器文件使用访问加宽器。
authors-nogithub:
  - lightningtow
  - siglong
authors:
  - Ayutac
  - cassiancc
  - CelDaemon
  - cootshk
  - Earthcomputer
  - florensie
  - froyo4u
  - haykam821
  - hYdos
  - its-miroma
  - kb-1000
  - kcrca
  - liach
  - lmvdz
  - matjojo
  - MildestToucan
  - modmuss50
  - octylFractal
  - OroArmor
  - T3sT3ro
  - Technici4n
  - TheGlitch76
  - UpcraftLP
  - YTG1234
---

访问加宽是一种[类调整](../class-tweakers)，用于放宽类、方法和字段的访问限制，并将该变更反映到反编译源码中。  
这包括将它们设为 public、可扩展和/或可变。

访问加宽器条目可以是[传递性的](../class-tweakers/index#transitive-entries)，从而让依赖你的模组的其他模组也能看到这些变更。

若只是访问字段或方法，使用 [Mixin 访问器](https://wiki.fabricmc.net/tutorial:mixin_accessors)可能更安全、更简单。  
但在以下两种情况下，访问器无法满足需求，必须使用访问加宽：

- 你需要访问 `private`、`protected` 或包私有的类
- 你需要重写 `final` 方法，或继承 `final` 类

不过，与 [Mixin 访问器](https://wiki.fabricmc.net/tutorial:mixin_accessors)不同，[类调整](../class-tweakers)只能作用于原版 Minecraft 类，不能作用于其他模组。

## 访问指令 {#access-directives}

访问加宽器条目以三种指令关键字之一开头，用于指定要应用的修改类型。

### accessible {#accessible}

`accessible` 可以作用于类、方法和字段：

- 字段和类会被设为 public。
- 方法会被设为 public。如果原本是 private，则还会被设为 final。

将某个方法或字段设为 accessible 时，也会同时将其所属类设为 accessible。

### extendable {#extendable}

`extendable` 可以作用于类和方法：

- 类会被设为 public 且非 final
- 方法会被设为 protected 且非 final

将某个方法设为 extendable 时，也会同时将其所属类设为 extendable。

### mutable {#mutable}

`mutable` 可以将字段设为非 final。

如果要让一个 private final 字段同时变为 accessible 和 mutable，必须在文件中分别添加两个条目。

## 指定目标 {#specifying-targets}

在类调整中，类使用其[内部名称](../mixins/bytecode#class-names)。 对于字段和方法，你必须指定它们所属的类名、名称以及[字节码描述符](../mixins/bytecode#field-and-method-descriptors)。

::: tabs

== 类

格式：

```:no-line-numbers
<accessible / extendable>    class    <className>
```

示例：

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:classes:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

== 方法

格式：

```:no-line-numbers
<accessible / extendable>    method    <className>    <methodName>    <methodDescriptor>
```

示例：

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:methods:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

== 字段

格式：

```:no-line-numbers
<accessible / mutable>    field    <className>    <fieldName>    <fieldDescriptor>
```

示例：

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:fields:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

:::

## 生成条目 {#generating-entries}

手动编写访问加宽器条目既耗时，又容易出现人为错误。 下面介绍了一些工具，它们可以帮助你生成并复制条目，从而简化部分流程。

### mcsrc.dev {#mcsrc-dev}

对于所有提供[未混淆 JAR](../migrating-mappings/index#whats-going-on-with-mappings) 的版本，也就是 1.21.11 及以上版本，[mcsrc](https://mcsrc.dev) 允许你在浏览器中反编译并浏览 Minecraft 源码，还可以将 Mixin、访问加宽器或访问转换器目标复制到剪贴板。

若要复制访问加宽器条目，请先导航到你想要修改的类，然后右键点击目标以打开弹出菜单。

![在 mcsrc 中右键点击目标](/assets/develop/class-tweakers/access-widening/mcsrc-right-click-on-aw-target.png)

接着点击 `Copy Class Tweaker / Access Widener`，页面顶部应会出现确认提示。

![mcsrc 中的访问加宽器复制确认提示](/assets/develop/class-tweakers/access-widening/mcsrc-aw-copy-confirmation.png)

随后，你就可以将该条目粘贴到类调整器文件中。

### Minecraft Development 插件（IntelliJ IDEA） {#mcdev-plugin}

[Minecraft Development 插件](../getting-started/intellij-idea/setting-up#installing-idea-plugins)，也称为 MCDev，是一个 IntelliJ IDEA 插件，可辅助 Minecraft 模组开发的多个方面。
例如，它可以让你从反编译源码中的目标处复制访问加宽器条目到剪贴板。

若要复制访问加宽器条目，请先导航到你想要修改的类，然后右键点击目标以打开弹出菜单。

![使用 MCDev 右键点击目标](/assets/develop/class-tweakers/access-widening/mcdev-right-click-on-aw-target.png)

接着点击 `Copy / Paste Special` 和 `AW Entry`。

![MCDev 中的 Copy/Paste Special 菜单](/assets/develop/class-tweakers/access-widening/mcdev-copy-paste-special-menu.png)

现在，你右键点击的元素上应会弹出确认提示。

![MCDev 中的访问加宽器复制确认提示](/assets/develop/class-tweakers/access-widening/mcdev-aw-copy-confirmation.png)

随后，你就可以将该条目粘贴到类调整器文件中。

## 应用更改 {#applying-changes}

若要看到你的更改生效，必须刷新 Gradle 项目并[重新生成源码](../getting-started/generating-sources)。 你所指定的目标元素的访问限制应会相应发生变化。 如果修改没有出现，可以尝试[验证该文件](../class-tweakers/index#validating-the-file)，并检查是否有错误。
