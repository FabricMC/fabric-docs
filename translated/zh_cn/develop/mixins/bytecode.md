---
title: Java 字节码
description: 学习 Java 字节码以便高效编写 mixin。
authors:
  - Earthcomputer
  - its-miroma
---

mixin 是在 Java 字节码上操作的，所以要理解 mixin 需要对字节码有所了解。

要了解如何在你的 IDE 中查看一个类的字节码，请查询[技巧和提示](../getting-started/tips-and-tricks)页面的“查看字节码”一节。

## 名称和符号{#names-and-symbols}

字节码中的这么多东西，例如类、字段和方法，都是通过名称来识别的（还有字段和方法的描述符，后面会提到），就像在源代码中的名称一样。 但是，这些名字的准确格式有些区别。

### 类名{#class-names}

类通常是按照其 _内部名称_ 引用的，大致等于完整的类名（完整名称包含包），但所有的点`.`替代为斜线`/`。 例如，类 `java.lang.Object` 的内部名是 `java/lang/Object`。

内部类的名称和其所在的类的名称用 `$` 隔开。 例如，已知：

```java
package pkg;
class Foo {
    class Bar {
    }
}
```

... `Bar` 的内部名称会是 `pkg/Foo$Bar`。

匿名类使用数字，而不是名称。 例如，如果上面的代码块中的 `Foo` 类有两个匿名类，则这两个匿名类的内部名称则分别会是 `pkg/Foo$1` 和 `pkg/Foo$2`。

本地类（在方法中定义的类）会是数字后面跟着名字。 例如，一个本地类的名称可能像是这样：`pkg/Foo$1Local`。

### 类型描述符{#type-descriptors}

字节码需要引用原始类型或数组时，则会用上 _类型描述符_。 这个表格列举了各数据类型及其对应类型描述符：

| 类型        | 描述符                                               |
| --------- | ------------------------------------------------- |
| `boolean` | `Z`                                               |
| `byte`    | `B`                                               |
| `char`    | `C`                                               |
| `double`  | `D`                                               |
| `float`   | `F`                                               |
| `int`     | `I`                                               |
| `long`    | `J`                                               |
| `short`   | `S`                                               |
| `void`    | `V`                                               |
| 数组        | `[` + 元素类型：`int[]` -> `[I`                        |
| 对象        | `L` + 内部名称 + `;`：`String` -> `Ljava/lang/String;` |

### 字段和方法描述符{#field-and-method-descriptors}

在字节码中，字段和方法是结合其名称和 _描述符_ 来识别的。 对于字段，是其数据类型的描述符，

而方法的描述符则是结合其参数类型和返回类型。 例如，以下方法：

```java
void drawText(int x, int y, String text, int color) {
    // ...
}
```

... 其描述符是 `(IILjava/lang/String;I)V`。

参数类型的描述符是直接连接在一起的，没有分隔符。 此例中，`I` 两次表示 `int`（`x` 和 `y`），然后是 `Ljava/lang/String;` 表示 `String`（`text`），然后还有个 `I` 表示最后的 `int`（`color`）。

### 构造方法和静态初始化{#constructors-and-static-initializers}

从字节码来看，构造方法只是另一个方法，这两者的差别超出了本概述的范围。

构造方法的方法名称是 `<init>`（带有尖括号 `<>`），返回值是 `V`（`void`）。 所有的非静态字段的初始化在编译后都可在 `<init>` 方法中找到。

而静态初始化（源代码的 `static{}` 块，以及静态字段的初始化，有些例外）也只是另一个方法，当类加载时运行，其名称为 `<clinit>`，描述符为 `()V`。

## 局部变量{#local-variables}

在源代码中，局部变量按名称区分。 在字节码中，则是按数字区分的，或局部变量表（Local Variable Table，LVT）中的索引。 LVT 也包含方法的参数，以及非静态方法中的 `this`。

以下面的这个方法为例：

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

在字节码中，`this` 的索引为 0，`offset` 的索引为 1，`result` 的索引为 2。

静态方法在 LVT 中没有 `this`，所以静态方法的第一个参数的索引直接是 0。

long（长整型）和double（双精度浮点烽）在 LVT 中占 2 个索引。 例如，在以下静态方法中：

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

... 参数 `x` 的索引是 0，参数 `y` 的索引是 2，参数 `z` 的索引是 4。

::: info

可以看到，字节码不需要局部变量的名称，因为是根据 LVT 索引识别的。 尽管如此，许多库仍保留调试信息，包括局部变量的名称，这样调试更方便，并且你可以在开发 mixin 时根据名称找到局部变量。

然而，Minecraft 1.21.1 默认不提供这个，因此可以说是混淆的。 注意[未来版本的 Minecraft 会是未混淆的](../migrating-mappings/#whats-going-on-with-mappings)。

:::

## 操作数栈{#the-operand-stack}

就像原生的汇编语言使用处理器寄存器一样，Java 的字节码使用 _操作数栈_ 来存储临时值。

和其他的[栈](https://en.wikipedia.org/wiki/Stack_\(abstract_data_type\))一样，值是添加（进栈）到栈顶，从栈顶移除（出栈）的。 可以想想一叠盘子：把盘子加到这叠盘子，会加到这叠顶部，而如果需要一个盘子，也会从顶部取。 这样的数据结构称为 _后入先出_，因为最后放上去（进栈）的“盘子”会被最先取走（出栈）。

我们再来看看刚刚的 `getX` 的例子：

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

想象一下，当 `this.x` 的值为 42 时，调用了 `getX(5)`，然后看看发生了什么，一个指令一个指令地来：

::: tabs

== 开始

| 索引 | 局部变量表                       | 操作数栈 |
| -- | --------------------------- | ---- |
| 2  |                             |      |
| 1  | `offset`: 5 |      |
| 0  | `this`                      |      |

点击上面的按钮可在不同指令之间导航。

上面的图会显示指令 _之后_ 的当前的局部变量表（LVT）和操作数栈的状态。

现在 LVT 的槽位 0 包含 `this`：这是因为 `getX` 不是静态方法。

== aload 0

| 索引 | 局部变量表                       | 操作数栈   |
| -- | --------------------------- | ------ |
| 2  |                             |        |
| 1  | `offset`: 5 |        |
| 0  | `this`                      | `this` |

从 LVT 槽 0（`this`）加载变量，将其值推入操作数栈。

== getfield x

| 索引 | 局部变量表                       | 操作数栈 |
| -- | --------------------------- | ---- |
| 2  |                             |      |
| 1  | `offset`: 5 |      |
| 0  | `this`                      | 42   |

取出操作数栈顶的值，得到其 `x` 字段的值（我们说过是 42），并将其推入操作数栈。

== iload 1

| 索引 | 局部变量表                       | 操作数栈 |
| -- | --------------------------- | ---- |
| 2  |                             |      |
| 1  | `offset`: 5 | 5    |
| 0  | `this`                      | 42   |

从 LVT 槽位 1（`offset`）加载变量，将其值推入操作数栈。

== iadd

| 索引 | 局部变量表                       | 操作数栈 |
| -- | --------------------------- | ---- |
| 2  |                             |      |
| 1  | `offset`: 5 |      |
| 0  | `this`                      | 47   |

取出操作数栈顶的两值，将其相加，并将其总和推入操作数栈。

== istore 2

| 索引 | 局部变量表                        | 操作数栈 |
| -- | ---------------------------- | ---- |
| 2  | `result`: 47 |      |
| 1  | `offset`: 5  |      |
| 0  | `this`                       |      |

取出操作数栈顶的值，将其分配给 LVT 槽位 2 的局部变量（`result`，是个[局部变量](#variable-instructions)）。

== iload 2

| 索引 | 局部变量表                        | 操作数栈 |
| -- | ---------------------------- | ---- |
| 2  | `result`: 47 |      |
| 1  | `offset`: 5  |      |
| 0  | `this`                       | 47   |

从 LVT 槽位 2（`result`）加载变量，将其值推入操作数栈。

== ireturn

获取操作数栈顶的值，并将其返回。

方法返回值 47。

:::

## 条件指令{#conditional-instructions}

我们已经看到了 JVM 如何一个接一个顺次随从指令。 但是，有的指令会告诉 JVM 跳到字节码中的不同点：

- `goto`：始终跳到被引用的指令
- `ifeq`：取出操作数栈顶的值，如果等于 0，跳到被引用的指令
- `ifne`：取出操作数栈顶的值，如果不等于 0，跳到被引用的指令
- `if_icmpXX`：取出操作数栈顶的两个值并比较。 若比较为 true，则 JVM 跳到被引用的指令。 例如：
  - `if_icmpeq`（`==`）：若两值相等则成功
  - `if_icmpgt`（`>`）：若第一个值大于第二个则成功
  - `if_icmple`（`<=`）：若第一个值小于或等于第二个则成功

例如，考虑以下方法：

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

注意跳转目标用 `L*` 标记。

`ifeq` 指令将操作数栈顶的值（是 `cond`，因为前面的 `iload` 指令）与 0 比较，如果等于 0（`false`）则跳到 `L1`。

如果不等于 0，即 `cond` 是 `true`，则继续执行后面的指令，直到达到 `goto` 指令，跳到 `L2`。

这个 `if` 块本质上是 `ifeq L1` 到 `L1` 之间的块，而 `else` 块是 `L1` 到 `L2`。 条件“跳转”指令让人想起[`goto` 时代的编程](https://xkcd.com/292/)，`if` 语句、循环、三元运算符等，就是这样编译的。

编译会导致生成复杂逻辑，不仅不易阅读，还不易用 mixin 定位。 以下面的这个类为例：

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

因为两个 `if` 的条件都会跳到恰好同一个标签，所以字节码中没有地方对应 `// inject here?` 注释，也就是说，用 mixin 定位时需要采取变通方法。

## 常见字节码模式{#common-bytecode-patterns}

这是你在开发 mixin 时可能遇到的最常见的字节码指令和模式和参考。 完整的指令列表，请参考[英文维基百科上的字节码指令列表](https://en.wikipedia.org/wiki/List_of_Java_bytecode_instructions)。

### 常量{#constant-instructions}

常量指令会向操作数栈推入一个常量值。

- `iconst_m1`、`iconst_0`、`iconst_1`、…、`iconst_5`：从 `-1` 到 `5` 的整数值
- `lconst_0`、`dconst_1`、`fconst_2` 以及类似：字面的数字，其类型分别是 `long`、`double` 和 `float`
- `bipush`、`sipush`：推入一个更大的整数常量
- `ldc`：可推入几种不同类型的常量，包括字符串和更大的整数

### 变量{#variable-instructions}

加载指令会从 [LVT 读取值](#local-variables)，并将其推入操作数栈。

存储指令会取出操作数栈顶的值，并写入局部变量。

- `iload`、`istore`：加载或存储类型为 `int`、`boolean`、`byte`、`char`、`short` 的变量
- `lload`、`lstore`：加载或存储类型为 `long` 的变量
- `fload`、`fstore`：加载或存储类型为 `float` 的变量
- `dload`、`dstore`：加载或存储类型为 `double` 的变量
- `aload`、`astore`：加载或存储类型非基础类型的变量

### 字段{#field-instructions}

- `getfield`：读取非静态字段
- `putfield`：写入非静态字段
- `getstatic`：读取静态字段
- `putstatic`：写入静态字段

### 方法调用{#method-instructions}

- `invokestatic`：调用静态方法
- `invokevirtual`：调用非静态方法。 考虑多态和继承，在可行时调用其被覆盖的版本
- `invokespacial`：调用非静态方法，且就是声明的那个方法，不考虑多态和继承。 包括调用构造方法和超类方法
- `invokeinterface`：调用非静态接口方法

### 条件{#conditional-instructions-2}

参见[条件指令](#conditional-instructions)。

### 运算{#operator-instructions}

运算指令通常是取出操作数栈的两个值，进行运算，并推入结果。 这是一些常见运算指令的列表：

- `iadd`、`ladd`、`fadd`、`dadd`：加法
- `isub`、`lsub`、`fsub`、`dsub`：减法
- `imul`、`lmul`、`fmul`、`dmul`：乘法
- `idiv`、`ldiv`、`fdiv`、`ddiv`：除法
- `irem`、`lrem`、`frem`、`drem`：取模
- `ineg`、`lneg`、`fneg`、`dneg`：取反。 仅取出操作数栈顶的一个值

前缀 `i`、`l`、`f`、`d`，在前面的[变量指令](#variable-instructions)中也看到了，决定着进行运算的数据的类型。

### 返回{#return-instructions}

返回指令会关闭方法调用，返回操作数栈顶的值（除了 `void` 方法）。

当前缀有 `i`、`l`、`f`、`d` 和 `a` 时，就像[变量指令](#variable-instructions)那样，方法会返回该类型的值。 `void` 方法的指令就只是 `return`。

### 创建新对象{#new-object-creation}

在源代码中，写 `new MyClass()` 会返回一个 `MyClass` 的新实例，并调用其构造方法。 在字码码中，这两个步骤是分开的操作。 例如，看看下面的代码：

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

来看看操作数栈中发生了什么。

::: tabs

== 开始

| 索引 | 局部变量表   | 操作数栈 |
| -- | ------- | ---- |
| 2  |         |      |
| 1  |         |      |
| 0  | `level` |      |

LVT 槽位 0 包含 `level`。 不包含 `this`，因为方法是静态的。

== new Creeper

| 索引 | 局部变量表   | 操作数栈           |
| -- | ------- | -------------- |
| 2  |         |                |
| 1  |         |                |
| 0  | `level` | 非初始化 `Creeper` |

分配未初始化的 `Creeper` 实例，将其引用推入操作数栈。

== dup

| 索引 | 局部变量表   | 操作数栈           |
| -- | ------- | -------------- |
| 2  |         |                |
| 1  |         | 非初始化 `Creeper` |
| 0  | `level` | 非初始化 `Creeper` |

重复操作数栈顶的值。

我们需要操作数栈包含同一个对象的两个指针，因为一个会被 `invokespecial <init>` 使用，另一个会被 `areturn` 返回。

== aload 0

| 索引 | 局部变量表   | 操作数栈           |
| -- | ------- | -------------- |
| 2  |         | `level`        |
| 1  |         | 非初始化 `Creeper` |
| 0  | `level` | 非初始化 `Creeper` |

从 LVT 槽位 0（`level`）加载变量，将其值推入操作数栈。

<!-- markdownlint-disable no-inline-html -->

== invokespecial <init>

<!-- markdownlint-enable no-inline-html -->

| 索引 | 局部变量表   | 操作数栈      |
| -- | ------- | --------- |
| 2  |         |           |
| 1  |         |           |
| 0  | `level` | `Creeper` |

从操作数栈中取出将传入 `Creeper.<init>` 的参数（在此例中是 `level`），以及会被初始化的对象的指针，然后调用构造方法。

因为[构造方法返回 `void`](#constructors-and-static-initializers)，操作数栈会失去 `Creeper` 的两个指针中的一个。

== areturn

获取操作数栈顶的值，并将其返回。
方法返回 `Creeper` 实例的指针。

:::

### lambda {#lambdas}

lambda 表达式是在单独的方法中编译的，会由将被 `invokedynamic` 指令初始化的一个 lambda 实例调用。

`invokedynamic` 指令的细节本文不作细述，但了解其预期得到什么类型的代码也是有用的。 本段省略了一些 `invokedynamic` 操作数，以使其简洁。

例如：

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

可以看到，lambda 的内容被移动到了单独的方法中，在此例中是 `lambda$hello$1`。

如果想用 mixin 定位到 lambda 的内容，那么这就是你需要定位到的方法。 然后，会用 `invokedynamic` 指令创建方法实例，并将其存储在变量 `r` 中。

如果 lambda 捕获了任何变量，这些变量最终会成为这些 lambda 方法的参数。 例如：

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

这里，`name` 参数会作为参数传递给 lambda。 还要注意 `invokedynamic` 是如何实现字符串连接的。
