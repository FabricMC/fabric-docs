---
title: Java Bytecode
description: Learn the basics of Java bytecode so that you can write mixins more effectively.
authors:
- Earthcomputer
---

Mixins operate on Java bytecode, so having a basic grasp on the fundamentals of how Java bytecode works is essential to understanding Mixins.

## Viewing the Bytecode of a Class {#viewing-bytecode}

If can look at the bytecode of any Minecraft/library class using your IDE. This shows you exactly what is going on there in a way the decompiled code will not.

To find out how view the bytecode of a class, please consult the [tips and tricks](../getting-started/tips-and-tricks) article for your IDE.

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

You can see here that the contents of the lambda has been moved into a separate method, in this case `lambda$hello$1`. If you want to target the contents of a lambda with Mixin, this is the method you'll want to be targetting. The lambda instance is then created with the `invokedynamic` instruction and then stored into the variable `r`.

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
