---
title: Interface Injection
description: Learn how to implement interfaces on Minecraft classes in the decompiled source.
authors-nogithub:
  - salvopelux
authors:
  - Daomephsta
  - Earthcomputer
  - its-miroma
  - Juuxel
  - MildestToucan
  - Sapryx
  - SolidBlock-cn
---

Interface injection is a type of [class tweaking](../class-tweakers/) used to add interface implementations on Minecraft classes
in the decompiled source.

The implementation being visible in the class's decompiled source removes the need to cast to the interface to use its methods.

Additionally, interface injections can be [transitive](../class-tweakers/index#transitive-entries), allowing libraries to more easily
expose their added methods to mods that depend on them.

To showcase interface injection, this page's snippets will use an example where we add a new helper method to `FlowingFluid`.

## Creating the Interface {#creating-the-interface}

In a package that is not your mixin package, create the interface you'd like to inject:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/BucketEmptySoundGetter.java#interface-injection-example-interface

In our case, we'll throw by default since we plan to implement the method through a mixin.

::: warning

The methods of injected interfaces must all be `default` to be injected with a class tweaker,
even if you plan to implement the methods in the target class using a mixin.

Methods should also be prefixed by your mod ID with a separator such as `$` or `_` so that they
cannot clash with other mods' added methods.

:::

## Implementing the Interface {#implementing-the-interface}

::: tip

If the interface's methods are fully implemented with the interface's `default`s,
you do not need to use a mixin to inject the interface, the [class tweaker entry](#making-the-class-tweaker-entry) will be enough.

:::

To create overrides of the interface's methods in the target class, you should use a mixin that implements the interface and targets the class
you want to inject the interface into.

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/FlowingFluidMixin.java#interface-injection-example-mixin

The overrides will be added to the target class at runtime, but will not appear in the decompiled source even if you use class tweaker to make the
interface implementation visible.

## Making the Class Tweaker Entry {#making-the-class-tweaker-entry}

Interface injection uses the following syntax:

```classtweaker:no-line-numbers
inject-interface    <targetClassName>    <injectedInterfaceName>
```

For class tweaking, classes and interfaces use their [internal names](../mixins/bytecode#class-names).

For our example interface, the entry would be:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-example-entry

### Generic Interfaces {#generic-interfaces}

If your interface has generics, you can specify them in the entry. For this, add `<>` angled brackets
at the end of the interface name with the generics in Java bytecode signature format between the brackets.

The signature format is:

| Description            | Java example             | Syntax                                                                      | Signature Format Example  |
| ---------------------- | ------------------------ | --------------------------------------------------------------------------- | ------------------------- |
| Class type             | `java.lang.String`       | [Descriptor](../mixins/bytecode#type-descriptors) format                    | `Ljava/lang/String;`      |
| Array type             | `java.lang.String[]`     | [Descriptor](../mixins/bytecode#type-descriptors) format                    | `[Ljava/lang/String;`     |
| Primitive              | `boolean`                | [Descriptor](../mixins/bytecode#type-descriptors) character                 | `Z`                       |
| Type variable          | `T`                      | `T` + name + `;`                                                            | `TT;`                     |
| Generic class type     | `java.util.List<T>`      | L + [internal name](../mixins/bytecode#class-names) + `<` + generics + `>;` | `Ljava/util/List<TT;>;`   |
| Wildcard               | `?`, `java.util.List<?>` | `*` character                                                               | `*`, `java/util/List<*>;` |
| Extends wildcard bound | `? extends String`       | `+` + the bound                                                             | `+Ljava/lang/String;`     |
| Super wildcard bound   | `? super String`         | `-` + the bound                                                             | `-Ljava/lang/String;`     |

So to inject the interface:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/GenericInterface.java#interface-injection-generic-interface

with the generics `<? extends String, Boolean[]>`

The class tweaker entry would be:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-generic-interface-entry

## Applying Changes {#applying-changes}

To see your interface implementation applied, you must refresh your Gradle project by [regenerating sources](../getting-started/generating-sources).
If modifications do not appear, you can try [validating](../class-tweakers/index#validating-the-file) the file and checking if any errors appear.

The added methods can now be used on instances of the class the interface was injected into:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/ExampleModInterfaceInjection.java#interface-injection-using-added-method

You can also override the methods in subclasses of the interface injection target if needed.
