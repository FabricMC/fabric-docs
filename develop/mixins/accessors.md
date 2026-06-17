---
title: Mixin Accessors
description: Learn how to access methods and fields using Mixin's Accessors and Invokers.
authors:
  - Bawnorton
  - bluebear94
  - cassiancc
  - MildestToucan
---

Mixins are typically used to modify existing code to produce and tweak behavior. However, Mixin also provides tools for
accessing inaccessible fields and methods in the form of accessor mixins.

[Class tweakers](../class-tweakers) provides a similar tool in the form of [access wideners](../class-tweakers/access-widening), but
Mixin's accessors do not require reloading Gradle, and can be applied to non-Minecraft targets.

Access widening is still necessary to override final methods or subclass final classes,
or to reference private classes, as accessors can only target fields and methods.

## Creating the Accessor Interface {#creating-the-accessor-interface}

Accessor mixins must always be an interface, and must only contain methods annotated with `@Accessor` or `@Invoker`. The interface must be annotated with `@Mixin`
similarly to other mixin classes.

Accessor interfaces are conventionally named after their target class with `Accessor` suffixed, and placed in an `accessor` subpackage within your mixin package.
Ie `your.package.mixin.accessor`

## Field Accessors {#field-accessors}

Fields can be accessed using `@Accessor`-annotated getter and/or setter methods:

::: tabs

== Instance Field

Getter/Setter Syntax:

Instance accessor methods should be prefixed by your mod's ID and a separator (conventionally `$` or `_`) to ensure it does not clash with any other method.

```java
@Accessor("<field name>")
FieldType example_mod$getFieldName();

@Accessor("<field name>")
void example_mod$setFieldName(FieldType value);
```

Example:

<<< @/reference/latest/src/client/java/com/example/docs/mixin/client/accessor/HudAccessor.java#mixin_accessors_instance_field_accessor_example

Usage:

<<< @/reference/latest/src/client/java/com/example/docs/accessor_usage/client/ExampleModAccessorUsageClient.java#mixin_accessors_instance_field_accessor_example_usage

== Static Field

Getter/Setter Syntax:

For static fields, the modId prefix is unnecessary, as static methods cannot clash with one another.

To satisfy the Java compiler, we make static accessor methods' bodies always throw, although they will always be implemented
by Mixin during application.

```java
@Accessor("<field name>")
static FieldType getFieldName() {
    throw new AssertionError("Untransformed @Accessor");
}

@Accessor("<field name>")
static void setFieldName(FieldType value) {
}
```

Example:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/ClientboundCustomPayloadPacketAccessor.java#mixin_accessors_static_field_accessor_example

Usage:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_static_field_accessor_example_usage

:::

### Setting Final Fields {#setting-final-fields}

If the target field is final, annotate the accessor's setter method with `@Mutable` to remove the final flag during application:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/VillagerAccessor.java#mixin_accessors_mutable_setter_example

## Method Invokers {#method-invokers}

To call inaccessible methods or constructors, create a method matching the signature annotated with `@Invoker`:

::: tabs

== Instance Method

Syntax:

Instance invoker methods should be prefixed by your mod's ID and a separator (conventionally `$` or `_`) to ensure it does not clash with any other method.

Invoker methods are typically either named directly after the target method, or either `invoke` or `call` followed by the method name.

```java
@Invoker("<method name>")
MethodReturnType example_mod$methodName(/* matching parameters */)
```

Example:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/InventoryAccessor.java#mixin_accessors_instance_invoker_example

Usage:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_instance_invoker_example_usage

== Static Method

Syntax:

For static methods, the modId prefix is unnecessary, as static methods cannot clash with one another.

To satisfy the Java compiler, we make static invoker methods' bodies always throw, although they will always be implemented
by Mixin during application.

```java
@Invoker("<method name>")
static MethodReturnType invokeMethodName(/* matching parameters */) {
    throw new AssertionError("Untransformed @Accessor");
}
```

Example:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/ShulkerBoxBlockAccessor.java#mixin_accessors_static_invoker_example

Usage:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_static_invoker_example_usage

== Constructor

Syntax:

Constructor invoker methods are conventionally named `createTargetClass` or `newTargetClass`, are always `static`
and always use `<init>` for the `@Invoker` annotation value.

```java
@Invoker("<init>")
static TargetClass newTargetClass(/* matching parameters */)
```

Example:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/IdentifierAccessor.java#mixin_accessors_constructor_invoker_example

Usage:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_constructor_invoker_example_usage

:::

## Using Accessors for Final Classes {#accessors-for-final-classes}

Because the Java compiler is stricter around final classes, it doesn't allow us to directly cast instances of it to an accessor interface.

To get around this, we only need to first cast to `Object` and then to the Accessor interface:

```java
((TargetClassAccessor) (Object) targetClassInstance).example_mod$accessorMethod(/* ... */)
```
