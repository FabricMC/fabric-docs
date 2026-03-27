---
title: Access Widening
description: Learn how to use access wideners from class tweaker files.
authors-nogithub:
  - lightningtow
  - siglong
authors:
  - Ayutac
  - cassiancc
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

Access widening is a type of [class tweaking](../class-tweakers) used to loosen the access limits of classes, methods and fields and reflect that change in the decompiled source.
This includes making them public, extendable and/or mutable.

Access widener entries can be [transitive](../class-tweakers/index#transitive-entries) to make changes visible to mods depending on yours.

To access fields or methods, it can be safer and simpler to use [accessor mixins](https://wiki.fabricmc.net/tutorial:mixin_accessors),
but there are two situations where accessors are insufficient and access widening is necessary:

- If you need to access a `private`, `protected` or package-private class
- If you need to override a `final` method, or subclass a `final` class

However, unlike [accessor mixins](https://wiki.fabricmc.net/tutorial:mixin_accessors), [class tweaking](../class-tweakers) only works on Vanilla Minecraft classes, and not on other mods.

## Access Directives {#access-directives}

Access widener entries start with one of three directive keywords to specify the type of modification to apply.

### Accessible {#accessible}

`accessible` can target classes, methods and fields:

- Fields and Classes are made public.
- Methods are made public, and final if originally private.

Making a method or field accessible also makes its class accessible.

### Extendable {#extendable}

`extendable` can target classes and methods:

- Classes are made public and non-final
- Methods are made protected and non-final

Making a method extendable also makes its class extendable.

### Mutable {#mutable}

`mutable` can make a field non-final.

To make a private final field both accessible and mutable, you must make two separate entries in the file.

## Specifying Targets {#specifying-targets}

For class tweaking, classes use their [internal names](../mixins/bytecode#class-names). For fields and methods you must specify their class name, their name, and their [bytecode descriptor](../mixins/bytecode#field-and-method-descriptors).

::: tip

The names of targets need to correspond to your current mappings.

:::

::: tabs

== Classes

Format:

```classtweaker:no-line-numbers
<accessible / extendable>    class    <className>
```

Example:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:classes:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

== Methods

Format:

```classtweaker:no-line-numbers
<accessible / extendable>    method    <className>    <methodName>    <methodDescriptor>
```

Example:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:methods:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

== Fields

Format:

```classtweaker:no-line-numbers
<accessible / mutable>    field    <className>    <fieldName>    <fieldDescriptor>
```

Example:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:fields:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

:::

## Generating Entries {#generating-entries}

Manually writing access widener entries is time-consuming and prone to human error. Let's look at tools that simplify a part of the process by allowing you to generate and copy entries.

### mcsrc.dev {#mcsrc-dev}

Available for all versions with an [unobfuscated JAR](../migrating-mappings/index#whats-going-on-with-mappings) namely 1.21.11 and above,
[mcsrc](https://mcsrc.dev) allows you to decompile and navigate Minecraft source in the browser and copy Mixin, access widener or access transformer targets to clipboard.
The names of classes, methods and fields on [mcsrc](https://mcsrc.dev) will align with [Mojang Mappings](../migrating-mappings/index#mappings).

To copy an access widener entry, first navigate to the class which you want to modify, and right-click on your target to open the popup menu.

![Right-clicking on a target in mcsrc](/assets/develop/class-tweakers/access-widening/mcsrc-right-click-on-aw-target.png)

Then, click on `Copy Class Tweaker / Access Widener`, and a confirmation should appear at the top of the page.

![AW copy confirmation on mcsrc](/assets/develop/class-tweakers/access-widening/mcsrc-aw-copy-confirmation.png)

You can then paste the entry in your class tweaker file.

### Minecraft Development Plugin (IntelliJ IDEA) {#mcdev-plugin}

The [Minecraft Development Plugin](../getting-started/intellij-idea/setting-up#installing-idea-plugins), also known as MCDev, is an IntelliJ IDEA plugin to assist in various aspects of Minecraft mod development.
For example, it lets you copy access widener entries from the decompiled source target to the clipboard.

To copy an access widener entry, first navigate to the class which you want to modify, and right-click on your target to open the popup menu.

![Right-clicking on a target with MCDev](/assets/develop/class-tweakers/access-widening/mcdev-right-click-on-aw-target.png)

Then, click on `Copy / Paste Special` and `AW Entry`.

![Copy/Paste special with MCDev](/assets/develop/class-tweakers/access-widening/mcdev-copy-paste-special-menu.png)

A confirmation should now pop up on the element you right-clicked.

![AW copy confirmation with MCDev](/assets/develop/class-tweakers/access-widening/mcdev-aw-copy-confirmation.png)

You can then paste the entry in your class tweaker file.

### Linkie {#linkie}

[Linkie](https://linkie.shedaniel.dev) is a website that allows you to browse and translate across mappings. It also provides access widener entries for the class, method or field you're viewing.

First, make sure you have the correct version and mappings selected on the menu on the left:

![Correct version and mappings selected on Linkie](/assets/develop/class-tweakers/access-widening/linkie-version-mappings-select.png)

Then, search for the element you want to modify, and the access widener entry will be listed as `AW` under the result:

![A search result in Linkie](/assets/develop/class-tweakers/access-widening/linkie-search-results.png)

You can copy it and then paste the entry in your class tweaker file.

## Applying Changes {#applying-changes}

To see your changes applied, you must refresh your Gradle project by [regenerating sources](../getting-started/generating-sources). The elements you targeted should
have their access limits modified accordingly. If modifications do not appear, you can try [validating the file](../class-tweakers/index#validating-the-file)
and checking if any errors appear.
