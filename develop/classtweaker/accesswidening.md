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
To access fields or methods, it can be an alternative to [accessor mixins](https://wiki.fabricmc.net/tutorial:mixin_accessors),
but there are two situations where [accessors](https://wiki.fabricmc.net/tutorial:mixin_accessors) are insufficient and access widening is necessary:

- Needing to access a (package) private class
- Needing to override a final method or subclass a final class.

Note that, unlike [accessor mixins](https://wiki.fabricmc.net/tutorial:mixin_accessors), [class tweaking](../classtweaker) only works on Vanilla Minecraft classes, and not on other mods.

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

### Transitive Directives {#transitive-directives}

In order to expose certain access widener changes to mods depending on yours, you prefix the relevant directives with `transitive-*`:

```txt:no-line-numbers
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

```txt:no-line-numbers
<accessible / extendable>    class    <className>
```

Example:

@[code lang=txt:no-line-numbers transcludeWith=:::accesswidening-examples:classes:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

### Methods {#methods}

Format:

```txt:no-line-numbers
<accessible / extendable>    method    <className>    <methodName>    <methodDescriptor>
```

Example:

@[code lang=txt:no-line-numbers transcludeWith=:::accesswidening-examples:methods:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

### Fields {#fields}

Format:

```txt:no-line-numbers
<accessible / mutable>    field    <className>    <methodName>    <methodDescriptor>
```

Example:

@[code lang=txt:no-line-numbers transcludeWith=:::accesswidening-examples:fields:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

## Generating Entries {#generating-entries}

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

You can then paste the entry in your class tweaker file.

### Linkie {#linkie}

[Linkie](https://linkie.shedaniel.dev) is a website to browse and translate between mappings. It also provides access widener entries for the class, method or field you're viewing.

First, make sure you have the correct version and mappings selected on the menu on the left:

![linkie version and mappings selection](/assets/develop/classtweaker/accesswidening/linkie-version-mappings-select.png)

Then, search for the element you want to modify, and the access widener entry will be listed as `AW` under the result:

![linkie search results](/assets/develop/classtweaker/accesswidening/linkie-search-results.png)

You can copy it and then paste the entry in your class tweaker file.

## Applying Changes {#applying-changes}

To see your changes applied, you must refresh your gradle project [regenerate sources](../getting-started/generating-sources). The targeted elements should
have their access limits modified appropriately. If modifications do not appear, you can try [validating the file](../classtweaker/index#validating-the-file)
and see if any errors appear.

The modified targets can then be used without any additional steps.
