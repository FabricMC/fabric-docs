---
title: Access Widening
description: Learn how to use access wideners from class tweaker files.
authors-nogithub:
  - glitch
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
  - UpcraftLP
  - YTG1234
---

Access widening is a type of [class tweaking](../classtweaker) used to loosen the access limits of classes, methods and fields. This includes making them public, extendable and/or mutable.

For general information about class tweaking and how to set up a class tweaker file, please read [the introduction](../classtweaker).

Before using access wideners, you must first set up a class tweaker file as described in the [introduction](../classtweaker).

Access widening is an alternative to [accessor mixins](https://wiki.fabricmc.net/tutorial:mixin_accessors) for accessing fields and methods. There are two situations where accessors are insufficient:

- Needing to access a (package) private class
- Needing to override a final method or subclass a final class.

Note that, unlike [accessor mixins](https://wiki.fabricmc.net/tutorial:mixin_accessors), [class tweaking](../classtweaker) only works on minecraft code, and not on other mods.

As with other types of [class tweaking](../classtweaker), in order for access changes to show up in the decompiled source, run the `genSources` gradle task and reload the gradle project in the IDE.

## Access Directives {#access-directives}

Access widener entries use a directive keyword at the start of the line to specify the modification to perform on the target.

### Accessible {#accessible}

`accessible` can target classes, methods and fields:

- Fields and Classes are made public.
- Methods are made public, and final if originally `private`.

Making a method or field accessible also makes its class accessible.

### Extendable {#extendable}

`extendable` can target classes and methods:

- Classes are made public and non-final
- Methods are made `protected` and non-final

Making a method extendable also makes its class extendable.

### Mutable {#mutable}

`mutable` can make a field non-final.

To make a private final field both accessible and mutable, you must use two directives, one for each change.

### Transitive Directives {#transitive-directives}

In order to expose certain access widener changes to mods depending on yours, you prefix the relevant directives with `transitive-*`:

``` :no-line-numbers
transitive-accessible
transitive-extendable
transitive-mutable
```

## Specifying Targets {#specifying-targets}

For class tweaking, classes use their [internal names](../mixins/bytecode#class-names) and
fields or methods must specify their name along with their [bytecode descriptor](../mixins/bytecode#field-and-method-descriptors).

The names of targets should match your current mappings. Note that constructor methods are always named `<init>`, and always return void.

### Classes {#classes}

Format:

``` classtweaker:no-line-numbers
<accessible / extendable>    class    <className>
```

Example:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:classes:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

### Methods {#methods}

Format:

``` classtweaker:no-line-numbers
<accessible / extendable>    method    <className>    <methodName>    <methodDescriptor>
```

Example:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:methods:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

### Fields {#fields}

Format:

``` classtweaker:no-line-numbers
<accessible / mutable>    field    <className>    <methodName>    <methodDescriptor>
```

Example:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:fields:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

## Generating Access Widener Entries {#generating-access-widener-entries}

Manually writing access widener entries is prone to human error and time-consuming. This section goes over tools that simplify a part of the process by allowing you to generate and copy entries.

### mcsrc.dev {#mcsrc-dev}

Available for all versions with an [unobfuscated](../migrating-mappings/index#whats-going-on-with-mappings) jar, namely 1.21.11 and above,
[mcsrc](https://mcsrc.dev) allows you to decompile and navigate Minecraft source in the browser and copy Mixin, access widener or access transformer targets to clipboard.
On 1.21.11, the names of classes, methods and fields on [mcsrc](https://mcsrc.dev) will align with [Mojang Mappings](../migrating-mappings/index#mappings).

To copy an access widener entry, first navigate to the class which you want to modify, and right click on your target to open the popup menu.

![mcsrc right clicking on target](/assets/develop/classtweaker/accesswidening/mcsrc-right-click-on-aw-target.png)

Then, click on `Copy Class Tweaker / Access Widener`, and a confirmation should appear at the top of the page.

![mcsrc aw copy confirmation](/assets/develop/classtweaker/accesswidening/mcsrc-aw-copy-confirmation.png)

You can then paste the entry in your class tweaker file.

### Minecraft Development Plugin (IntelliJ IDEA) {#mcdev-plugin}

The Minecraft Development Plugin, also known as MCDev, is an IntelliJ IDEA plugin to assist in various aspects of Minecraft mod development.
This section will show its ability to copy access widener entries to clipboard from the decompiled source target.

To copy an access widener entry, first navigate to the class which you want to modify, and right click on your target to open the popup menu.

![mcdev right clicking on target](/assets/develop/classtweaker/accesswidening/mcdev-right-click-on-aw-target.png)

Then, click on `Copy / Paste Special` and `AW Entry`.

![mcdev copy paste special](/assets/develop/classtweaker/accesswidening/mcdev-copy-paste-special-menu.png)

A confirmation should now pop up on the element you right-clicked.

![mcdev aw copy confirmation](/assets/develop/classtweaker/accesswidening/mcdev-aw-copy-confirmation.png)

### Linkie {#linkie}

[Linkie](https://linkie.shedaniel.dev) is a website to browse and translate between mappings. It also provides access widener entries for the class, method or field you're viewing.

First, make sure you have the correct version and mappings selected on the menu on the left:

![linkie version and mappings selection](/assets/develop/classtweaker/accesswidening/linkie-version-mappings-select.png)

Then, search for the element you want to modify, and the access widener entry will be listed as `AW` under the result:

![linkie search results](/assets/develop/classtweaker/accesswidening/linkie-search-results.png)

## Validating The File {#validating-the-file}

By default, class tweaker entries that specify a non-existent class, method or field will be ignored.

In order to check that all the classes, fields and methods specified in the file exist, run the `validateAccessWidener` Gradle task.

If a specified element does not exist, an error message indicating the invalid entry will appear.
