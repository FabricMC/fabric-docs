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

## Validating The File {#validating-the-file}

By default, class tweaker entries that specify a non-existent class, method or field will be ignored.

In order to check that all the classes, fields and methods specified in the file exist, run the `validateAccessWidener` Gradle task.

If a specified element does not exist, an error message indicating the invalid entry will appear.
