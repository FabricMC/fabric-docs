---
title: Mixins
description: Learn about what mixins are, and how to get started with writing them.
authors:
  - ArkoSammy12
  - cassiancc
  - its-miroma
  - MildestToucan
resources:
  https://github.com/SpongePowered/Mixin/wiki: Official Mixin Wiki - GitHub
  https://github.com/LlamaLad7/MixinExtras/wiki: Official MixinExtras Wiki - GitHub
  https://mcdev.io/: Official Minecraft Development IntelliJ plugin website
---

Mixin is a framework used in modding to patch the code of Minecraft or other mods at runtime. This is mainly useful to hook into parts of code that do not have
existing [events](../events) and APIs, or to apply precise modifications and tweaks to the target code for your mod's specific needs.

Mixin is not specific to any Minecraft version, nor even to Minecraft itself. The Mixin version you use depends on your Fabric Loader version, and you should ensure that
your [loader dependency](../loader/fabric-mod-json#dependency-resolution)'s minimum version matches the one you develop your mod with for consistent mixin behavior.

This page will give a basic overview of Mixin as a framework, its tools, and provide resources to get started with its syntax and systems, alongside further reading and help channels.

::: info PREREQUISITES

Mixin is a tool that assumes users are already familiar with Java code, and have a basic knowledge of JVM bytecode.

In order to best understand this page and Mixin, it is heavily recommended to be comfortable with Java programming itself, and to first read the
[bytecode page](../mixins/bytecode).

For information on how to set up Mixin configuration files, see the [mixins.json](../mixins/mixins-json) page,
and the [fabric.mod.json page's mixin section](../loader/fabric-mod-json#mixins).

:::

## Overview {#overview}

Before getting into how to _use_ Mixin, it's best to first have a basic understanding on how it _works_, and the tools it provides without getting lost in technical details.

This section will provide an overview of the types of tools that Mixin provides to use it, and how they are used by Mixin to modify code.
We will detail the specific ways those tools are used and structured in later dedicated sections about usage.

Understanding the fundamental types of tools that Mixin provides, and the ways they affect the targeted code, is key to developing an understanding of how to then use those tools.

### Mixin Classes {#mixin-classes}

Every modification done using Mixin is done by using "mixin classes". These classes are special Java classes structured in a way to declare changes and additions to be made
to certain target classes at runtime, typically just one.

Mixin classes are special in that they should never be instantiated or even loaded or mentioned in non-Mixin code, and they should generally be treated as though they do not exist
at runtime, with the notable exception of [accessor mixin interfaces](../mixins/accessors).

### Merging {#merging}

The primary way Mixin applies modifications is by pre-processing and then "merging" nearly all of a mixin class's content into the target class. This means any additional fields, methods,
static initializers, interface implementation declarations, etc. are added to the target class's bytecode.

All of the additional tools Mixin provides are built on top of merging, giving users ways to control and add steps to certain members' merging, rather than adding entirely separate transformations.

### Injectors {#injectors}

Injectors are represented by method annotations used in mixin classes, which declare ways in which to modify existing methods using the annotated "handler" method.

Injector-annotated methods will be merged as usual, but will additionally have calls to them injected into existing methods in ways that differ based on the specific injector used.

The effects of injectors vary greatly, ranging from adding a call to the handler to wrapping the entire method. It is best to read the relevant documentation for each different injector
before using it, and to ask for support when unsure of which one is applicable to a specific goal.

The main benefit of injectors over other ways of transforming methods and classes is that most of them are able to "stack", meaning that it is possible for multiple mods to apply the same
injection on the same target without any of them causing a crash or messing up one another's targeting.

### Adding New Members {#adding-new-members}

New members declared in a mixin class will be merged and added to the target class, which can be useful as helper methods for injector handlers, storing more information through
additional fields, etc.

Added members' names should be prefixed with a unique id, typically your modID, to prevent accidentally clashing with another member.
Mixin additionally provides the `@Unique` annotation for members declared in a mixin class, which will guarantee the member will never overwrite another, and will be renamed instead
of clashing.

## Basic Usage {#basic-usage}

Now that we have some understanding of what Mixin does, we can start learning fundamental syntax and usage principles.

This section will demonstrate basic usages of Mixin as a way to introduce its syntax and apply concepts we learned about in the [overview](#overview).
Do not however treat this as a tutorial fitting for any real use-case, however. You should [seek help](#help-channels) and read more specific documentation to get the knowledge you need
on a case-by-case basis.

::: tip

For writing mixins, it is highly recommended to use [IntelliJ IDEA](../getting-started/intellij-idea/setting-up) alongside the
[Minecraft Development Plugin](../getting-started/intellij-idea/setting-up#installing-minecraft-development-plugin), as the latter provides very powerful Mixin autocompletion,
error highlighting, and quick-fixes.

:::

### Creating a Mixin Class {#creating-a-mixin-class}

For the sake of demonstration, we will be modifying the following `Person` class:

<<< @/reference/latest/src/main/java/com/example/docs/mixin_docs_examples/introduction/Person.java#mixin_introduction_person_example_target_class

To make a mixin class targeting it, we first create a new Java class in our mod's [mixin package](../mixins/mixins-json#mixin-class-registration), and register it in our
mixin config.
