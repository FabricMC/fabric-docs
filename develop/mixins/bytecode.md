---
title: Java Bytecode
description: Learn about Java bytecode so that you can write mixins effectively.
authors:
  - Earthcomputer
  - its-miroma
---

Mixins operate on Java bytecode, so to understand them one needs a grasp on their fundamentals.

To find out how to view the bytecode of a class in your IDE, please consult the Viewing Bytecode section of the [Tips and Tricks page](../getting-started/tips-and-tricks).

## Names and Symbols {#names-and-symbols}

Many things in bytecode, like classes, fields, and methods, are still identified by the name (and the descriptor for fields and methods, we'll get to that), just like they are in source code. However, the exact format of those names differs a little bit.

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

Anonymous classes use numbers instead of names. For example, if there were two anonymous classes in the `Foo` class from the code block above, their internal names would be `pkg/Foo$1` and `pkg/Foo$2` respectively.

Local classes (classes defined within a method) have a number followed by their name. For example, a local class name might look like `pkg/Foo$1Local`.

### Type Descriptors {#type-descriptors}

When bytecode needs to refer to primitive types or arrays, _type descriptors_ are used. Here is a table of data types and their respective type descriptors:

| Type      | Descriptor                                                      |
| --------- | --------------------------------------------------------------- |
| `boolean` | `Z`                                                             |
| `byte`    | `B`                                                             |
| `char`    | `C`                                                             |
| `double`  | `D`                                                             |
| `float`   | `F`                                                             |
| `int`     | `I`                                                             |
| `long`    | `J`                                                             |
| `short`   | `S`                                                             |
| `void`    | `V`                                                             |
| Arrays    | `[` + the element type: `int[]` -> `[I`                         |
| Objects   | `L` + the internal name + `;`: `String` -> `Ljava/lang/String;` |

### Field and Method Descriptors {#field-and-method-descriptors}

In bytecode, fields and methods are identified by combining their name and _descriptor_. For fields, that's the descriptor of their data type.

Methods, on the other hand, get theirs by combining the parameter types and the return type. For example, the following method:

```java
void drawText(int x, int y, String text, int color) {
    // ...
}
```

... has descriptor `(IILjava/lang/String;I)V`.

The descriptors for the parameter types are directly concatenated together, with no separator. In this case, there is `I` for `int` twice (both `x` and `y`), then `Ljava/lang/String;` for `String` (`text`), and one more `I` for the last `int` (`color`).

### Constructors and Static Initializers {#constructors-and-static-initializers}

At the bytecode level, constructors are just another method: the detailed differences between the two fall beyond the scope of this overview.

A constructor's method name is `<init>` (with the `<>` angled brackets), and the return type in its descriptor is `V` (`void`). All non-static field initializations, after compilation, will be found inside the `<init>` methods.

On the other hand, static initializers (the `static {}` block in the source code, as well as static field initializers with some exceptions) also are just another method that is run when a class is loaded: its name is `<clinit>`, and its descriptor is `()V`.

## Local Variables {#local-variables}

In source code, local variables are identified by their name. In bytecode, they are instead identified by a number, or index into the Local Variable Table (LVT). Method parameters are included in the LVT, as is the `this` object in non-static methods.

Consider the following method as an example:

::: code-group

```java [Source Code]
public int getX(int offset) {
    int result = this.x + offset;
    return result;
}
```

```bytecode [Bytecode]
public getX (I)I
  aload 0  // this
  getfield x
  iload 1  // offset
  iadd
  istore 2  // result

  iload 2  // result
  ireturn
```

:::

In the bytecode, `this` gets index 0, `offset` gets index 1, and `result` gets index 2.

Static methods do not have `this` in the LVT, so the first parameter of static methods gets index 0 directly.

Longs and doubles take up 2 indexes in the LVT. For example, in the following static method:

::: code-group

```java [Source Code]
public static double add(double x, double y, double z) {
    return x + y + z;
}
```

```bytecode [Bytecode]
static add (DDD)D
  dload 0  // x
  dload 2  // y
  dadd
  dload 4  // z
  dadd
  dreturn
```

:::

... the `x` parameter gets index 0, the `y` parameter gets index 2, and the `z` parameter gets index 4.

::: info

We've seen that bytecode doesn't need the names of local variables because it identifies them by their LVT index. Despite this, many libraries will retain debug information, including the names of local variables, to ease debugging and allow you to target local variables by name when developing mixins.

However, Minecraft 1.21.11 does not provide that by default and is therefore said to be obfuscated. Note that [future versions of Minecraft will be deobfuscated](../migrating-mappings/#whats-going-on-with-mappings).

:::

## The Operand Stack {#the-operand-stack}

Just like native assembly uses processor registers, Java bytecode uses the _Operand Stack_ to store temporary values.

Like any [stack](<https://en.wikipedia.org/wiki/Stack_(abstract_data_type)>), values are added ("pushed") to the top of the stack, and removed ("popped") from the top of the stack. Think of it like a stack of plates: when you add a plate onto the stack, you put it on top, and when you need one, you take the top one. Such a data structure is said to be _Last-In, First-Out_, because the last "plate" pushed onto the stack will be popped first.

Let's take a look at the previous `getX` example again:

::: code-group

```java [Source Code]
public int getX(int offset) {
    int result = this.x + offset;
    return result;
}
```

```bytecode [Bytecode]
public getX (I)I
  aload 0  // this
  getfield x
  iload 1  // offset
  iadd
  istore 2  // result

  iload 2  // result
  ireturn
```

:::

Let's imagine `getX(5)` is called when `this.x` has the value 42, and let's follow what happens, instruction by instruction:

::: tabs

== Start

| Index | Local Variable Table | Operand Stack |
| ----- | -------------------- | ------------- |
| 2     |                      |               |
| 1     | `offset`: 5          |               |
| 0     | `this`               |               |

Navigate through the instructions by clicking on the buttons above.

The diagram above will show the state of the Local Variable Table and the Operand Stack _after_ the instruction.

Notice how LVT slot 0 contains `this`: that's because `getX` is not a static method.

== aload 0

| Index | Local Variable Table | Operand Stack |
| ----- | -------------------- | ------------- |
| 2     |                      |               |
| 1     | `offset`: 5          |               |
| 0     | `this`               | `this`        |

Loads the variable from LVT slot 0 (`this`), and pushes its value onto the Operand Stack.

== getfield x

| Index | Local Variable Table | Operand Stack |
| ----- | -------------------- | ------------- |
| 2     |                      |               |
| 1     | `offset`: 5          |               |
| 0     | `this`               | 42            |

Pops the top value off the Operand Stack, gets the value of its `x` field (which we said was 42), and pushes it onto the Operand Stack.

== iload 1

| Index | Local Variable Table | Operand Stack |
| ----- | -------------------- | ------------- |
| 2     |                      |               |
| 1     | `offset`: 5          | 5             |
| 0     | `this`               | 42            |

Loads the variable from LVT slot 1 (`offset`), and pushes its value onto the Operand Stack.

== iadd

| Index | Local Variable Table | Operand Stack |
| ----- | -------------------- | ------------- |
| 2     |                      |               |
| 1     | `offset`: 5          |               |
| 0     | `this`               | 47            |

Pops the top two values off the Operand Stack, adds them up, and pushes that sum onto the Operand Stack.

== istore 2

| Index | Local Variable Table | Operand Stack |
| ----- | -------------------- | ------------- |
| 2     | `result`: 47         |               |
| 1     | `offset`: 5          |               |
| 0     | `this`               |               |

Pops the top value off the Operand Stack, and assigns it to the local variable in LVT slot 2 (`result`, which is a [local variable](#variable-instructions)).

== iload 2

| Index | Local Variable Table | Operand Stack |
| ----- | -------------------- | ------------- |
| 2     | `result`: 47         |               |
| 1     | `offset`: 5          |               |
| 0     | `this`               | 47            |

Loads the variable from LVT slot 2 (`result`), and pushes its value onto the Operand Stack.

== ireturn

Gets the top value of the Operand Stack, and returns it.

The method returns the value 47.

:::

## Conditional Instructions {#conditional-instructions}

We've seen how the JVM follows instructions sequentially, one after the other. However, certain instructions tell the JVM to jump to a different point in the bytecode:

- `goto`: Always jumps to the referenced instruction
- `ifeq`: Pops the top value off the Operand Stack, and, if it is equal to 0, jumps to the referenced instruction
- `ifne`: Pops the top value off the Operand Stack, and, if it is not equal to 0, jumps to the referenced instruction
- `if_icmpXX`: Pops the top two values of the Operand Stack, and compares them. If the comparison is true, then the JVM jumps to the referenced instruction. For example:
  - `if_icmpeq` (`==`): Succeeds if the two values are equal
  - `if_icmpgt` (`>`): Succeeds if the first is greater than the second
  - `if_icmple` (`<=`): Succeeds if the first is less than or equal to the second

For example, consider the following method:

::: code-group

```java [Source Code]
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

```bytecode [Bytecode]
static makeFoobar (Z)Ljava/lang/String;
  iload 0  // cond
  ifeq L1
  ldc "foo"
  astore 1  // result
  goto L2
L1
  ldc "bar"
  astore 1  // result
L2
  aload 1  // result
  areturn
```

:::

Notice that jump targets are marked with `L*`.

The `ifeq` instruction compares the value on the top of the Operand Stack (which is `cond` due to the previous `iload` instruction) to 0, and jumps to `L1` if it is equal to 0 (`false`).

If it is not equal to 0, which means `cond` is `true`, it continues through the next instructions until it gets to the `goto` instruction, which then skips over to `L2`.

The `if` block essentially becomes the lines from `ifeq L1` to `L1`, while the `else` block is `L1`-`L2`. The conditional "jump" instructions, reminiscent of [`goto`-era programming](https://xkcd.com/292/), are how `if` statements, loops, ternaries, and so on, are compiled.

Compilation can end up generating complex logic that is not only hard to read, but also hard to target with mixins. Consider the following classic example:

::: code-group

```java [Source Code]
static void doSomething(boolean cond1, boolean cond2) {
    if (cond1) {
        if (cond2) {
            System.out.println("Something is being done");
        }
        // inject here? // [!code highlight]
    }
}
```

```bytecode [Bytecode]
static doSomething (ZZ)V
  iload 0  // cond1
  ifeq L1
  iload 1  // cond2
  ifeq L1
  getstatic System.out
  invokevirtual println
L1
  return
```

:::

Because the bytecode for both `if` conditions jumps to the exact same label, there is no place in the bytecode corresponding to the `// inject here?` comment, meaning workarounds must be used to target it with mixins.

## Common Bytecode Patterns {#common-bytecode-patterns}

Here's a reference for the most common bytecode instructions and patterns you'll encounter while developing mixins. For a full advanced list of instructions, check out the [List of Java bytecode instructions on Wikipedia](https://en.wikipedia.org/wiki/List_of_Java_bytecode_instructions).

### Constants {#constant-instructions}

Constant instructions push a constant value onto the Operand Stack.

- `iconst_m1`, `iconst_0`, `iconst_1`, ..., `iconst_5`: the literal integers `-1` through `5`
- `lconst_0`, `dconst_1`, `fconst_2`, and the like: the literal numbers, as `long`, `double`, and `float` respectively
- `bipush`, `sipush`: pushes a larger integer constant
- `ldc`: can push several different types of constants, including strings and even larger integers

### Variables {#variable-instructions}

Loading instructions read a [value from the LVT](#local-variables), and push it onto the Operand Stack.

Storing instructions pop the top value off the Operand Stack, and write it to a local variable.

- `iload`, `istore`: loads or stores variables of type `int`, `boolean`, `byte`, `char`, and `short`
- `lload`, `lstore`: loads or stores variables of type `long`
- `fload`, `fstore`: loads or stores variables of type `float`
- `dload`, `dstore`: loads or stores variables of type `double`
- `aload`, `astore`: loads or stores variables of non-primitive types

### Fields {#field-instructions}

- `getfield`: reads a non-static field
- `putfield`: writes to a non-static field
- `getstatic`: reads a static field
- `putstatic`: writes to a static field

### Method Invocations {#method-instructions}

- `invokestatic`: invokes a static method
- `invokevirtual`: invokes a non-static method. Takes polymorphism and inheritance into account, calling the overridden version where applicable
- `invokespecial`: invokes a non-static method, exactly the one declared, without taking into account polymorphism/inheritance. Uses include calling constructors and superclass methods
- `invokeinterface`: invokes a non-static interface method

### Conditionals {#conditional-instructions-2}

See [Conditional Instructions](#conditional-instructions).

### Operators {#operator-instructions}

Operator instructions generally pop two values off the Operand Stack, perform an operation, and push the result. Here is a list of some common operator instructions:

- `iadd`, `ladd`, `fadd`, `dadd`: addition
- `isub`, `lsub`, `fsub`, `dsub`: subtraction
- `imul`, `lmul`, `fmul`, `dmul`: multiplication
- `idiv`, `ldiv`, `fdiv`, `ddiv`: division
- `irem`, `lrem`, `frem`, `drem`: modulo
- `ineg`, `lneg`, `fneg`, `dneg`: negation. Pops only one value off the stack

The prefixes `i`, `l`, `f`, `d`, as seen with [variable instructions](#variable-instructions), determine the type of data on which to apply the operator.

### Returns {#return-instructions}

Return instructions close the method call, returning the value at the top of the Operand Stack (except for `void` methods).

When prefixed with `i`, `l`, `f`, `d`, and `a`, just like [variable instructions](#variable-instructions), the method returns a value of that type. The instruction for `void` methods is simply `return`.

### New Object Creation {#new-object-creation}

In source code, writing `new MyClass()` creates a new instance of `MyClass` and calls its constructor. In bytecode, these two steps become distinct operations. Take the following code, for example:

::: code-group

```java [Source Code]
static Creeper createCreeper(Level level) {
    return new Creeper(level);
}
```

```bytecode [Bytecode]
static createCreeper (Lnet/minecraft/world/level/Level;)Lnet/mineraft/world/entity/monster/Creeper;
  new net/minecraft/world/entity/monster/Creeper
  dup
  aload 0  // level
  invokespecial net/minecraft/world/entity/monster/Creeper.<init> (Lnet/minecraft/world/level/Level;)V
  areturn
```

:::

Let's examine what happens on the Operand Stack.

::: tabs

== Start

| Index | Local Variable Table | Operand Stack |
| ----- | -------------------- | ------------- |
| 2     |                      |               |
| 1     |                      |               |
| 0     | `level`              |               |

LVT slot 0 contains `level`. It does not contain `this` because the method is static.

== new Creeper

| Index | Local Variable Table | Operand Stack    |
| ----- | -------------------- | ---------------- |
| 2     |                      |                  |
| 1     |                      |                  |
| 0     | `level`              | uninit `Creeper` |

Allocates an uninitialized instance of `Creeper`, and pushes a reference to it onto the Operand Stack.

== dup

| Index | Local Variable Table | Operand Stack    |
| ----- | -------------------- | ---------------- |
| 2     |                      |                  |
| 1     |                      | uninit `Creeper` |
| 0     | `level`              | uninit `Creeper` |

Duplicates the top value of the Operand Stack.

We need the Operand Stack to contain two pointers to the same object because one will be consumed by `invokespecial <init>`, and the other will be returned by `areturn`.

== aload 0

| Index | Local Variable Table | Operand Stack    |
| ----- | -------------------- | ---------------- |
| 2     |                      | `level`          |
| 1     |                      | uninit `Creeper` |
| 0     | `level`              | uninit `Creeper` |

Loads the variable from LVT slot 0 (`level`), and pushes its value onto the Operand Stack.

<!-- markdownlint-disable no-inline-html -->

== invokespecial <init>

<!-- markdownlint-enable no-inline-html -->

| Index | Local Variable Table | Operand Stack |
| ----- | -------------------- | ------------- |
| 2     |                      |               |
| 1     |                      |               |
| 0     | `level`              | `Creeper`     |

Pops the parameters that will be fed into `Creeper.<init>` (in this case `level`), and the pointer to the object that will be initialized, off the Operand Stack, then calls the constructor.

Because [constructors return `void`](#constructors-and-static-initializers), the Operand Stack loses one of the two pointers to `Creeper`.

== areturn

Gets the top value of the Operand Stack, and returns it.
The method returns the pointer to the `Creeper` instance.

:::

### Lambdas {#lambdas}

Lambda expressions are compiled to a separate method, which is then called by a lambda instance that got instantiated by an `invokedynamic` instruction.

The details of the `invokedynamic` instruction are beyond the scope of this overview, but it's useful to know what kind of code to expect. Some `invokedynamic` operands have been omitted in this section for simplicity.

Here's an example:

::: code-group

```java [Source Code]
static void hello() {
    Runnable r = () -> System.out.println("Hello, World!");
    r.run();
}
```

```bytecode [Bytecode]
static hello ()V
  invokedynamic run ()Ljava/lang/Runnable; java/lang/invoke/LambdaMetafactory.metafactory ()V lambda$hello$1 ()V
  astore 0  // r

  aload 0  // r
  invokeinterface run

  return

static lambda$hello$1 ()V
  getstatic System.out
  ldc "Hello, World!"
  invokevirtual println

  return
```

:::

You can see here that the contents of the lambda have been moved into a separate method, in this case `lambda$hello$1`.

If you want to target the contents of a lambda with mixins, this is the method you'll want to be targeting. The lambda instance is then created with the `invokedynamic` instruction and then stored into the variable `r`.

If the lambda captures any variables, these variables will end up as parameters to the lambda methods. For example:

::: code-group

```java [Source Code]
static void hello(String name) {
    Runnable r = () -> System.out.println("Hello, " + name + "!");
    r.run();
}
```

```bytecode [Bytecode]
static hello (Ljava/lang/String;)V
  aload 0  // name
  invokedynamic run (Ljava/lang/String;)Ljava/lang/Runnable; java/lang/invoke/LambdaMetafactory.metafactory ()V lambda$hello$1 (Ljava/lang/String;)V ()V
  astore 1  // r

  aload 1  // r
  invokeinterface run

  return

static lambda$hello$1 (Ljava/lang/String;)V
  getstatic System.out
  aload 0  // name
  invokedynamic makeConcatWithConstants (Ljava/lang/String;)Ljava/lang/String; java/lang/invoke/StringConcatFactory.makeConcatWithConstants "Hello, \1!"
  invokevirtual println

  return
```

:::

Here, the `name` parameter is being passed as a parameter into the lambda. Notice also how string concatenation is implemented with `invokedynamic`.
