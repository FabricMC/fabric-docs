---
title: Mixin Accessors
description: Learn how to access methods and fields using Mixin's Accessors and Invokers.
authors:
  - MildestToucan
---

Mixins are typically used to modify existing code to produce and tweak existing behaviors. However, Mixin also provides tools for
accessing inaccessible fields and methods by adding accessor methods to the target class.

[Class tweakers](../class-tweakers) provides a similar tool in the form of [access wideners](../class-tweakers/access-widening), but
Mixin's accessors do not require reloading Gradle, and can be applied to non-Minecraft targets.

[Access widening](../class-tweakers/access-widening) is still necessary to override final methods or subclass final classes,
or to reference private classes, as accessors can only target fields and methods.

## Creating the Accessor Interface {#creating-the-accessor-interface}

Accessor mixins must always be an interface, and must only contain `@Accessor` or `@Invoker` methods. The interface must be annotated with
`@Mixin` similarly to other mixin classes:

```java
// TODO: EXAMPLE OF MIXIN ACCESSOR HEADER
```

Accessor interfaces are conventionally named after their target class with `Accessor` suffixed.

## Field Accessors {#field-accessors}

## Method Invokers {#method-invokers}

### Constructor Invokers {#constructor-invokers}
