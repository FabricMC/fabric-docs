---
title: Java Bytecode
description: Learn the basics of Java bytecode so that you can write mixins more effectively.
authors:
- Earthcomputer
---

Mixins operate on Java bytecode, so having a basic grasp on the fundamentals of how Java bytecode works is essential to understanding Mixins.

## Viewing the Bytecode of a Class {#show-bytecode}

You can view the bytecode of a library class (such as a Minecraft class) in IntelliJ by opening the class in the editor, then selecting `Show Bytecode` inside the `View` menu. This shows you exactly what is going on there in a way the decompiled code will not.

::: details Screenshots
![Show bytecode 1](/assets/develop/mixins/show_bytecode_1.png)

![Show bytecode 2](/assets/develop/mixins/show_bytecode_2.png)
:::

## Names and Symbols {#names-and-symbols}

In bytecode, many things like classes, fields, and methods, are still identified by the name (and descriptor for fields and methods, we'll get to that), just like they are in Java. However the exact format of those names differ a little bit.

### Class Names {#class-names}

Classes are generally referred to by their _internal name_, which is roughly equivalent to the fully qualified class name (full name including the package), except that all `.` dots are replaced with `/` slashes. For example, the internal name of the `java.lang.Object` class is `java/lang/Object`.

Nested classes use a `$` symbol to separate their name from their enclosing class. For example, if you have a class named `Inner` inside a class named `Foo` inside a package named `pkg`, then the internal name of `Inner` would be `pkg/Foo$Inner`.

Anonymous classes are similar to nested classes, except that their names are simply a number. The first anonymous class declared within a class has the name `1`, the second has the name `2`, and so on. So if there was an anonymous class inside the `Foo` class above, its name might be `pkg/Foo$1`.

Local classes (classes defined within a method), have a number followed by the name. For example, a local class name might look like `pkg/Foo$1Local`.

### Type Descriptors {#type-descriptors}

Sometimes bytecode needs to refer to a type which could be a primitive type or array type, not only a class type. In this situations, _type descriptors_ are used. Here is a table of data types and how they map to their type descriptors:

| Type      | Descriptor                                                           |
|-----------|----------------------------------------------------------------------|
| `boolean` | `Z`                                                                  |
| `byte`    | `B`                                                                  |
| `char`    | `C`                                                                  |
| `double`  | `D`                                                                  |
| `float`   | `F`                                                                  |
| `int`     | `I`                                                                  |
| `long`    | `J`                                                                  |
| `short`   | `S`                                                                  |
| `void`    | `V`                                                                  |
| Arrays    | `[` + the element type, e.g. `int[]` -> `[I`                         |
| Objects   | `L` + the internal name + `;`, e.g. `String` -> `Ljava/lang/String;` |

### Field and Method Descriptors {#field-and-method-descriptors}

In bytecode, fields and methods are identified by the comibination of their name and _descriptor_. A field descriptor is simply the type descriptor of the data type of the field.

For methods, the descriptor is the combination of the parameter types and the return type of the method. Here is an example of a method descriptor for the following method:

```java
void drawText(int x, int y, String text, int color) {
    // ...
}
```

```text
(IILjava/lang/String;I)V
```

You may notice that it consists of the parameter types inside parentheses, followed by the return type, in this case `V` for `void`. The descriptors for the parameter types are directly concatenated together with no separator. In this case, there are two copies of `I` for `int`, then `Ljava/lang/String;` for the `String`, and then one more `I` for the last `int`.

### Constructors and Static Initializers {#constructors-and-static-initializers}

At the bytecode level, constructors are just another method, and are special only in ways beyond the scope of this overview. A constructor's method name is `<init>` (with the `<>` angled brackets), and the return type in its descriptor is `V` (`void`). All non-static field initializers (the part after the `=` sign in a field declaration) are compiled into the `<init>` methods of a class and assigned to the field there.

Static initializers are the `static {}` block in Java that is run when a class is loaded. It's also just another method, its name is `<clinit>` and its descriptor is `()V`. Just as for non-static fields and constructors, static field initializers (with some exceptions) are compiled into the `<clinit>` method of a class.

## Local Variables {#local-variables}

In Java code, local variables are identified by their name. In bytecode, they are instead identified by a number, or index into the Local Variable Table (LVT). Method parameters are included in the LVT, as is the `this` object in non-static methods.

Consider the following method as an example:

```java
public int getX(int offset) {
    int result = this.x + offset;
    return result;
}
```

This would compile to something like the following:

```text
public getX (I)I
  iload 0  # this
  getfield x
  iload 1  # offset
  iadd
  istore 2  # result

  iload 2  # result
  ireturn
```

Here, `this` is local variable 0, `offset` is local variable 1, and `result` is local variable 2.

Longs and doubles take up 2 indexes in the LVT. This means that in the following method:

```java
public Vec3 add(double x, double y, double z) {
    // ...
}
```

The `x` parameter has index 1, the `y` parameter has index 3, and the `z` parameter has index 5.

In static methods, there is no `this` object, so other variables start counting at 0 instead of 1.

Even though local variables are identified by index, many libraries retain debug information containing the names of the local variables as well. This includes Minecraft since the unobfuscated releases started in version 26.1 (upcoming). Mixin is able to use this debug information to allow you to target local variables by name.
