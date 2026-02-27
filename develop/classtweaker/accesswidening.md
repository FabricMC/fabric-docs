---
title: Access Widening
description: Documentation on how to use access wideners with ClassTweaker
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

Access widening is a type of class tweaking used to loosen the access limits of classes, methods and fields. This includes making them public, or making them extendable.

Access widening is an alternative to [mixin accessors](https://wiki.fabricmc.net/tutorial:mixin_accessors) for accessing fields and methods, but, unlike accessors, class tweakers can make
(package) private classes public, or make it possible to override final methods and subclass final classes.

However accessors, unlike class tweakers, may target non-Minecraft classes.

## Access Modifiers {#access-modifiers}

Access widening can apply the following modifiers to targets:

### Accessible {#accessible}

`accessible` can affect classes, methods and fields:

- Fields and Classes are made `public`.
- Methods are made `public`, and are made `final` if originally `private`.

Making a method or field accessible also makes the class accessible.

### Extendable {#extendable}

`extendable` can affect classes and methods:

- Classes are made `public` and `final` is removed.
- Methods are made `protected` and `final` is removed.

Making a method extendable also makes the class extendable.

### Mutable {#mutable}

`mutable` can affect fields to remove their `final` flag. If you need to make a private final field both accessible and
mutable, you must use two directives, one for each change.

## Specifying Targets {#specifying-targets}

To see how to format and register your class tweaker file, please see the [introduction](../classtweaker/).

Classes use their [internal names](../mixins/bytecode#class-names), additionally fields and method targets must specify a [descriptor](../mixins/bytecode#field-and-method-descriptors).

### Classes {#classes}

```
<accessible / extendable>   class   <className>
```

### Methods {#methods}

```
<accessible / extendable>   method   <className>   <methodName>   <methodDescriptor>
```

### Fields {#fields}

```
<accessible / mutable>   field   <className>   <methodName>   <methodDescriptor>
```
