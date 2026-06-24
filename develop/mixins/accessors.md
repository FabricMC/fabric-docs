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
// TODO: example of mixin accessor header
```

Accessor interfaces are conventionally named after their target class with `Accessor` suffixed.

## Field Accessors {#field-accessors}

Fields can be accessed using `@Accessor`-annotated getter and/or setter methods:

::: tabs

== Instance Field

Getter/Setter Syntax:

```java
// Setter
@Accessor("<field name>")
void modid$setFieldName(FieldType value);

// Getter
@Accessor("<field name>")
FieldType modid$getFieldName();
```

Example:

```java
//TODO: @Acccessor instance field example
```

Usage:

```java
//TODO: @Accessor instance field usage example
```

== Static Field

Getter/Setter Syntax:

For static fields, the modId prefix is unnecessary, as static methods cannot clash with one another.

The getter's body can be a stub to satisfy the Java compiler.

```java
@Accessor("<field name>")
static void setFieldName(FieldType value) {
}

@Accessor("<field name>")
static FieldType getFieldName() {
    throw new AssertionError();
}
```

:::

### Setting Final Fields {#setting-final-fields}

If the target field is final, annotate the accessor's setter method with `@Mutable` to remove the final flag during application:

```java
// TODO: @Mutable setter for final field example
```

## Method Invokers {#method-invokers}

To call inaccessible methods, create a method matching the signature annotated with `@Invoker`:

::: tabs

== Instance Method

Syntax:

```java
@Invoker("<method name>")
MethodReturnType modid$invokeMethodName(/* matching params */)
```

Example:

```java
// TODO: @Invoker instance method example
```

Usage:

```java
// TODO: @Invoker instance method usage example
```

== Static Method

Syntax:

```java
@Invoker("<method name>")
static MethodReturnType invokeMethodName(/* matching params */) {
    throw new AssertionError();
}
```

Example:

```java
// TODO: @Invoker static method example
```

Usage:

```java
// TODO: @Invoker static method usage example
```

== Constructor

Syntax:

```java
@Invoker("<init>")
static TargetClass createTargetClass(/* matching params */)
```

Example:

```java
// TODO: @Invoker constructor example
```

Usage:

```java
// TODO: @Invoker constructor usage example
```

:::

## Using Accessors for Final Classes {#accessors-for-final-classes}

Because the Java compiler is stricter around final classes, it doesn't allow us to directly cast instances of it to an accessor interface.

To get around this, we only need to first cast to `Object` and then to the Accessor interface:

```java
// TODO: Double casting to use instance accessor methods on final class instance example
```
