---
title: Java Bytecode
description: Learn about Java bytecode, so that you can write mixins effectively.
authors:
- Earthcomputer
---

Mixins operate on Java bytecode, so to understand them one needs a grasp on the fundamentals of Java bytecode.

## Viewing the Bytecode of a Class {#viewing-bytecode}

You can look at the bytecode of any Minecraft/library class using your IDE. This shows you exactly what is going on there, whereas the decompiled code might not.

To find out how to see the bytecode of a class, please consult the Viewing Bytecode section of the [Tips and Tricks page](../getting-started/tips-and-tricks) for your IDE.

## Names and Symbols {#names-and-symbols}

In bytecode, many things like classes, fields, and methods, are still identified by the name (and the descriptor for fields and methods, we'll get to that), just like they are in source code. However the exact format of those names differs a little bit.

### Class Names {#class-names}

Classes are generally referred to by their _internal name_, which is roughly equivalent to the fully qualified class name (full name including the package), where all dots `.` are replaced with slashes `/`. For example, the internal name of the `java.lang.Object` class is `java/lang/Object`.

Nested classes use `$` symbols to separate their name from the enclosing classes. For example, given:

```java
package pkg;
class Foo {
    class Bar {
    }
}
```

... the internal name of `Bar` would be `pkg/Foo$Bar`.

Anonymous classes are named similarly to nested classes, and instead of a name they use numbers. For example, if there were two anonymous classes in the `Foo` class from the code block above, their internal names would be `pkg/Foo$1` and `pkg/Foo$2` respectively.

Local classes (classes defined within a method), have a number followed by the name. For example, a local class name might look like `pkg/Foo$1Local`.

### Type Descriptors {#type-descriptors}

When bytecode needs to refer to primitive types or arrays, _type descriptors_ are used. Here is a table of data types and their respective type descriptors:

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
| Arrays    | `[` + the element type: `int[]` -> `[I`                              |
| Objects   | `L` + the internal name + `;`: `String` -> `Ljava/lang/String;`      |

### Field and Method Descriptors {#field-and-method-descriptors}

In bytecode, fields and methods are identified by combining their name and _type descriptor_.

Descriptors for methods combine the parameter types and the return type. For example, the following method:

```java
void drawText(int x, int y, String text, int color) {
    // ...
}
```

```text
(IILjava/lang/String;I)V
```

... has descriptor `(IILjava/lang/String;I)V`.

The descriptors for the parameter types are directly concatenated together, with no separator. In this case, there is `I` for `int` twice (both `x` and `y`), then `Ljava/lang/String;` for `String` (`text`), and one more `I` for the last `int` (`color`).

### Constructors and Static Initializers {#constructors-and-static-initializers}

At the bytecode level, constructors are just another method: the detailed differences between the two fall beyond the scope of this overview.

A constructor's method name is `<init>` (with the `<>` angled brackets), and the return type in its descriptor is `V` (`void`). All non-`static` field initializations, after compilation, will be found inside of the `<init>` methods.

On the other hand, static initializers (the `static {}` block in Java), which is run when a class is loaded. It's also just another method: its name is `<clinit>`, and its descriptor is `()V`. Just like non-`static` fields and constructors, `static` field initializers (with some exceptions) are compiled into the `<clinit>` method of a class.

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

... the `x` parameter gets index 1, the `y` parameter gets index 3, and the `z` parameter gets index 5.

In static methods, there is no `this` object, so other variables start counting at 0 instead of 1.

Even though local variables are identified by index, many libraries retain debug information containing the names of the local variables as well. This does not yet include Minecraft, as it will remain obfuscated until the upcoming version 26.1. Mixin is able to use this debug information to allow you to target local variables by name.

## The Operand Stack {#the-operand-stack}

While native assembly uses registers, Java bytecode uses the _operand stack_ to store temporary values. Like any stack, values are added ("pushed") to the top of the stack, and removed ("popped") from the top of the stack. The operand stack can be thought of like a stack of plates -- when you add a plate onto the stack, you put it onto the top, and when you remove a plate from the stack, you remove it from the top, such that the last plate added to the stack is the first one removed.

Take the previous `getX` example again:

```java
public int getX(int offset) {
    int result = this.x + offset;
    return result;
}
```

```text
public getX (I)I
  aload 0  # this
  getfield x
  iload 1  # offset
  iadd
  istore 2  # result

  iload 2  # result
  ireturn
```

Let's say that `getX` is called when `this.x` has the value 42, and `offset` has the value 5. We'll follow what happens instruction-by-instruction.

<!-- markdownlint-disable no-emphasis-as-heading -->
**aload 0**

Loads the variable in LVT slot 0 and pushes its value onto the operand stack. After this instruction is finished, the operand stack is as follows:

| Operand Stack     |
|-------------------|
| Pointer to `this` |

**getfield x**

Pops the top value off the operand stack, gets the `x` field out of it and pushes its value onto the operand stack. After this instruction is finished, the operand stack is as follows:

| Operand Stack |
|---------------|
| 42            |

**iload 1**

Loads the variable in LVT slot 1 and pushes its value onto the operand stack. After this instruction is finished, the operand stack is as follows:

| Operand Stack |
|---------------|
| 5             |
| 42            |

**iadd**

Pops the top two values off the operand stack, adds them, and pushes the result onto the operand stack. After this instruction is finished, the operand stack is as follows:

| Operand Stack |
|---------------|
| 47            |

**istore 2**

Pops the top value of the operand stack and assigns it to the variable in LVT slot 2. After this instruction is finished, the operand stack is empty and the variable in LVT slot 2 has a value of 47.

**iload 2**

Loads the variable in LVT slot 2 and pushes its value onto the operand stack. After this instruction is finished, the operand stack is as follows:

| Operand Stack |
|---------------|
| 47            |

**ireturn**

Returns the value the top of the operand stack from the method. The value 47 is returned from the method.

<!-- markdownlint-enable no-emphasis-as-heading -->

## Conditional Instructions {#conditional-instructions}

Most of the time, when the JVM finishes executing an instruction, it continues sequentially onto the next instruction. However, certain instructions tell the JVM to jump to a different instruction depending on a certain condition and continue from there:

| Instructions   | Description                                                                                                                                                                                                                                                                                                                        |
|----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `goto`         | Always jumps to the referenced instruction.                                                                                                                                                                                                                                                                                        |
| `ifeq`, `ifne` | Pops the top value off the operand stack and checks if it's equal to 0. `ifeq` jumps to the referenced instruction if it is equal to 0, `ifne` jumps to the referenced instruction if it is not equal to 0.                                                                                                                        |
| `if_icmpxx`    | Pops the top two values of the operand stack and compares them. If the comparison succeeds then the JVM jumps to the referenced instruction. For example, `if_icmpeq` succeeds if the two values are equal, `if_icmpgt` if the first is greater than the second, and `if_icmple` if the first is less than or equal to the second. |

For example, consider the following Java method:

```java
static String makeFoobar(boolean cond) {
    String result;
    if (cond) {
        result = "foo";
    } else {
        result = "bar";
    }
    return result;
}
```

This would compile to something like the following:

```text
static makeFoobar (Z)Ljava/lang/String;
  iload 0  # cond
  ifeq L1
  ldc "foo"
  astore 1  # result
  goto L2
L1
  ldc "bar"
  astore 1  # result
L2
  aload 1  # result
  areturn
```

The `ifeq` instruction compares the value on the top of the operand stack (which is `cond` due to the previous `iload` instruction) to 0, and jumps to `L1` if it is equal to 0 (`false`). Otherwise it continues to the next instruction. `L1` is essentially the `else` block. Otherwise, it continues onto the next instructions, which is to load the string literal `"foo"` and assign it to the `result` variable. The `goto` instruction then skips over the `else` block.

These conditional instructions are how if statements, loops, ternaries, and so on are compiled. Complicated code structures can end up generating a complex web of jump instructions that are not only hard to read but also hard to target with Mixin. Take the following classic example:

```java
static void doSomething(boolean cond1, boolean cond2) {
    if (cond1) {
        if (cond2) {
            System.out.println("Something is being done");
        }
        // inject here?
    }
}
```

```text
static doSomething (ZZ)V
  iload 0  # cond1
  ifeq L1
  iload 1  # cond2
  ifeq L1
  getstatic System.out
  invokevirtual println
L1
  return
```

Because the bytecode for both if conditions jump to the exact same label, there is no place in the bytecode corresponding to the `// inject here?` comment, making it impossible to target with Mixin.

## Common Instructions {#common-instructions}

### Constants {#constant-instructions}

Constant instructions push a constant value onto the operand stack.

- `iconst_m1`, `iconst_0`, `iconst_1`, ..., `iconst_5`: the literal `int` values `-1` through `5`.
- `lconst_0`, `fconst_0`, `dconst_0`: literals of `long`, `float`, and `double` respectively.
- `bipush`, `sipush`: pushes an `int` constant, supports larger `int` values.
- `ldc`: pushes a constant of one of several different types, including numeric types and strings. Used for even larger integers and other types.

### Variables {#variable-instructions}

Load instructions reads the value of a local variable and pushes it onto the oeprand stack. Store instructions pops the top value on the operand stack and writes it to a local variable.

- `iload`, `istore`: loads and stores variables of type `int`, `boolean`, `byte`, `char`, and `short`.
- `lload`, `lstore`: loads and stores variables of type `long`.
- `fload`, `fstore`: loads and stores variables of type `float`.
- `dload`, `dstore`: loads and stores variables of type `double`.
- `aload`, `astore`: loads and stores variables of a non-primitive type.

### Fields {#field-instructions}

- `getfield`: reads a non-static field.
- `putfield`: writes a non-static field.
- `getstatic`: reads a static field.
- `putstatic`: writes a static field.

### Method Invocations {#method-instructions}

- `invokestatic`: invokes a static method.
- `invokevirtual`: invokes a non-static method. Takes polymorphism/inheritance into account, calling the overridden version if it is overridden.
- `invokespecial`: invokes a non-static method, exactly the one declared without taking into account polymorphism/inheritance. Used to call constructors and superclass methods like `super.blah()`.
- `invokeinterface`: invokes an interface method (can be non-static or static).

### Conditionals {#conditional-instructions-2}

See [Conditional Instructions](#conditional-instructions).

### Operators {#operator-instructions}

Operator instructions generally pop two values off the operand stack, perform the operation, and push the result. Here is a list of some common operator instructions:

- `iadd`, `ladd`, `fadd`, `dadd`: addition.
- `isub`, `lsub`, `fsub`, `dsub`: subtraction.
- `imul`, `lmul`, `fmul`, `dmul`: multiplication.
- `idiv`, `ldiv`, `fdiv`, `ddiv`: division.
- `irem`, `lrem`, `frem`, `drem`: modulo.
- `ineg`, `lneg`, `fneg`, `dneg`: negation. Pops one value off the stack rather than two.

There are different versions of each of these instructions depending on the data type: `i` for `int`, `l` for `long`, `f` for `float`, and `d` for `double`.

### Returns {#return-instructions}

Return out of the method, with the value on top of the operand stack (except in the case of `void`). Prefixed with `i`, `l`, `f`, `d`, and `a` in the same way as [variable instructions](#variable-instructions), except for `void` for which the instruction is simply `return`.

## Lambdas {#lambdas}

Lambda expressions are compiled by making them into a separate method, and using the `invokedynamic` instruction to instantiate the lambda instance. The details of the `invokedynamic` instruction are beyond the scope of this article, but it's useful to know what kind of code to expect. Some `invokedynamic` operands have been omitted in this section for simplicity. Here's an example:

```java
static void hello() {
    Runnable r = () -> System.out.println("Hello, World!");
    r.run();
}
```

```text
static hello ()V
  invokedynamic run ()Ljava/lang/Runnable; java/lang/invoke/LambdaMetafactory.metafactory ()V lambda$hello$1 ()V
  astore 0  # r

  aload 0  # r
  invokeinterface run

  return

static lambda$hello$1 ()V
  getstatic System.out
  ldc "Hello, World!"
  invokevirtual println

  return
```

You can see here that the contents of the lambda has been moved into a separate method, in this case `lambda$hello$1`. If you want to target the contents of a lambda with Mixin, this is the method you'll want to be targeting. The lambda instance is then created with the `invokedynamic` instruction and then stored into the variable `r`.

If the lambda captures any variables, these variables will end up as parameters to the lambda methods. For example:

```java
static void hello(String yourName) {
    Runnable r = () -> System.out.println("Hello, " + yourName + "!");
    r.run();
}
```

```text
static hello (Ljava/lang/String;)V
  aload 0  # yourName
  invokedynamic run (Ljava/lang/String;)Ljava/lang/Runnable; java/lang/invoke/LambdaMetafactory.metafactory ()V lambda$hello$1 (Ljava/lang/String;)V ()V
  astore 1  # r

  aload 1  # r
  invokeinterface run

  return

static lambda$hello$1 (Ljava/lang/String;)V
  getstatic System.out
  aload 0  # yourName
  invokedynamic makeConcatWithConstants (Ljava/lang/String;)Ljava/lang/String; java/lang/invoke/StringConcatFactory.makeConcatWithConstants "Hello, \1!"
  invokevirtual println

  return
```

Here, the `yourName` parameter is being passed as a parameter into the lambda. Notice also how string concatenation is implemented with `invokedynamic`.
