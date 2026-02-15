---
title: Codec
description: 一份用于理解和使用 Mojang 的 codec 系统以序列化和反序列化对象的全面指南。
authors:
  - enjarai
  - Syst3ms
---

codec 是用于简单地解析 Java 对象的系统，被包含在 Minecraft 所包含的 Mojang 的 DataFixerUpper (DFU) 库中。 在模组环境中，可用作读写 JSON 时 GSON 和 Jankson 的替代方案，尽管因为 Mojang 正在重写许多旧代码以使用 codec，codec 开始变量越来越重要。

codec 与 DFU 的另一个 API `DynamicOps` 一起使用。 一个 codec 定义一个对象的结构，而 dynamic ops 用于定义一个序列化格式，例如 JSON 或 NBT。 这意味着任何 codec 都可以与任何 dynamic ops 一起使用，反之亦然，这样使其极其灵活。

## 使用 codec{#using-codecs}

### 序列化和反序列化 {#serializing-and-deserializing}

codec 的基本用法是将对象序列化为特定格式或反序列化为特定格式。

一些原版的类已经定义了 codec，这些我们可以用作例子。 Mojang 默认提供了两个 dynamic ops 类，`JsonOps` 和 `NbtOps`，涵盖大部分的使用场景。

现在，假设我们要把一个 `BlockPos` 对象序列化成 JSON 再反序列化回对象。 我们可以分别使用 `BlockPos.CODEC` 中的静态方法 `Codec#encodeStart` 和 `Codec#parse`。

```java
BlockPos pos = new BlockPos(1, 2, 3);

// 序列化该 BlockPos 为 JsonElement
DataResult<JsonElement> result = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);
```

使用 codec 时，返回的结果为 `DataResult` 的形式。 这是个包装，可以代表成功或者失败。 有几种方式使用：如果只想要我们序列化的值， `DataResult#result` 会简单返回一个包含我们的值的 `Optional` ，而 `DataResult#resultOrPartial` 还允许我们提供一个函数来处理可能发生的任何错误。 后者对于自定义数据包资源尤其有用，因为我们想要记录错误，而不会在其他地方引起问题。

那么，让我们获取我们的序列化值，并将其转换回 `BlockPos` ：

```java
// 实际编写模组时，你当然会想要适当地处理空的 Optional。
JsonElement json = result.resultOrPartial(LOGGER::error).orElseThrow();

// 这里我们有我们的 JSON 值，应该对应于 `[1, 2, 3]`,
// 因为这是 BlockPos codec 使用的格式。
LOGGER.info("Serialized BlockPos: {}", json);

// 现在将 JsonElement 反序列化为 BlockPos
DataResult<BlockPos> result = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

// 我们将再次只是从 result 中获取我们的值
BlockPos pos = result.resultOrPartial(LOGGER::error).orElseThrow();

// 接下来我们可以看到我们成功序列化和反序列化我们的 BlockPos！
LOGGER.info("Deserialized BlockPos: {}", pos);
```

### 内置的 codec{#built-in-codecs}

正如之前所说，Mojang 已经为几个原版和标准 Java 类定义了 codec，包括但不限于 `BlockPos`、`BlockState`、`ItemStack`、`Identifier`、`Component` 和正则表达式 `Pattern`。 Mojang 自己的 codec 通常可以在类内找到名为 `CODEC` 的静态字段，其他的保持在 `Codecs` 类。 还要注意，所有原版注册表都包含方法来得到 `Codec`，例如，你可以使用 `BuiltInRegistries.BLOCK.byNameCodec()` 获取一个 `Codec<Block>`，可用于序列化为方块 id 或是反过来，以及一个 `holderByNameCodec()` 获取一个 `Codec<Holder<Block>>`。

Codec API 自己也包含一些基础类型的 codec，例如 `Codec.INT` 和 `Codec.STRING`。 这些都在 `Codec` 类中作为静态字段存在，通常用作更多复杂 codec 的基础，会在下方做出解释。

## 构建 codec{#building-codecs}

现在我们已经知道如何使用 codec，让我们看看我们如何构建自己的 codec。 假设我们有以下类，希望从 JSON 文件中反序列化其实例：

```java
public class CoolBeansClass {

    private final int beansAmount;
    private final Item beanType;
    private final List<BlockPos> beanPositions;

    public CoolBeansClass(int beansAmount, Item beanType, List<BlockPos> beanPositions) {...}

    public int getBeansAmount() { return this.beansAmount; }
    public Item getBeanType() { return this.beanType; }
    public List<BlockPos> getBeanPositions() { return this.beanPositions; }
}
```

相应的 JSON 文件可能如下所示：

```json
{
  "beans_amount": 5,
  "bean_type": "beanmod:mythical_beans",
  "bean_positions": [
    [1, 2, 3],
    [4, 5, 6]
  ]
}
```

我们可以将多个较小的 codec 组合成较大的 codec，从而为这个类制作 codec。 在这种情况下，我们的每个字段都需要：

- 一个 `Codec<Integer>`
- 一个 `Codec<Item>`
- 一个 `Codec<List<BlockPos>>`

第一个可以从前面提到的 `Codec` 类中的基本类型 codec 中得到，也就是 `Codec.INT`。 而第二个可以从 `BuiltInRegistries.ITEM` 注册表中获取，它有 `byNameCodec()` 方法，返回 `Codec<Item>`。 我们没有用于 `List<BlockPos>` 的默认 codec，但我们可以从 `BlockPos.CODEC` 制作一个。

### 列表 {#lists}

`Codec#listOf` 可用于创建任意 codec 的列表版本。

```java
Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
```

应该注意的是，以这种方式创建的 codec 总是会反序列化为一个 `ImmutableList`。 如果需要的是可变的列表，可以利用 [xmap](#mutually-convertible-types) 在反序列化期间转换为可变列表。

### 合并用于类似 Record 类的 codec{#merging-codecs-for-record-like-classes}

现在每个字段都有了单独的 codec，我们可以使用 `RecordCodecBuilder` 为我们的类将其合并为一个 codec。 假定我们的类有一个包含想序列化的所有字段的构造方法，并且每个字段都有相应的 getter 方法。 这使其非常适合与 record 一起使用，但也可以用于常规类。

来看看如何为我们的 `CoolBeansClass` 创建一个 codec：

```java
public static final Codec<CoolBeansClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("beans_amount").forGetter(CoolBeansClass::getBeansAmount),
    BuiltInRegistries.ITEM.byNameCodec().fieldOf("bean_type").forGetter(CoolBeansClass::getBeanType),
    BlockPos.CODEC.listOf().fieldOf("bean_positions").forGetter(CoolBeansClass::getBeanPositions)
    // 最多可以在这里声明 16 个字段
).apply(instance, CoolBeansClass::new));
```

在 group 中的每一行指定 codec、字段名称和 getter 方法。 调用 `Codec#fieldOf` 是为将 codec 转换为 [map codec](#mapcodec)，调用 `forGetter` 则是指定了从类的实例中检索字段值的 getter 方法。 同时，调用 `apply` 则指定了用于创建新实例的构造函数。 注意 group 中的字段的顺序应与构造函数中参数的顺序相同。

这里也可以使用 `Codec#optionalFieldOf` 使字段可选，在 [可选字段](#optional-fields) 章节会有解释。

### 不要将 MapCodec 与 Codec&lt;Map&gt; 混淆{#mapcodec}

调用 `Codec#fieldOf` 会将 `Codec<T>` 转换成 `MapCodec<T>`，这是 `Codec<T>` 的一个变体，但不是直接实现。 正如其名称所示，`MapCodec` 保证序列化为
键到值的映射，或所使用的 `DynamicOps` 类似类型。 一些函数可能需要使用 `MapCodec` 而不是常规的 codec。

这种创建 `MapCodec` 的特殊方式本质上是在一个映射中封装源 codec 的值，并使用给定的字段名作为键。 例如，一个 `Codec<BlockPos>` 序列化为 JSON 时看起来像是这样：

```json
[1, 2, 3]
```

但当使用 `BlockPos.CODEC.fieldOf("pos")` 转换为 `MapCodec<BlockPos>` 时，看起来像是这样：

```json
{
  "pos": [1, 2, 3]
}
```

虽然 map codec 最常见的用途是与其他 map codec 合并以构造一个完整类字段的 codec，如前文的[合并用于类似 Record 类的 codec](#merging-codecs-for-record-like-classes) 章节所述，但也可以通过使用 `MapCodec#codec` 再次转换成常规的 codec，这将保持封装输入值的相同行为。

#### 可选字段{#optional-fields}

`Codec#optionalFieldOf` 可用于创建一个可选的 map codec。 反序列化过程中，当特定字段不存在于容器中时，反序列化为一个空的 `Optional`，或指定的默认值。

```java
// 无默认值
MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");

// 有默认值
MapCodec<BlockPos> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ZERO);
```

要注意，如果字段存在，但其值无效，若字段的值无效则字段会完全无法反序列化。

### 常量、约束和组合{#constants-constraints-composition}

#### Unit {#unit}

`MapCodec.unitCodec` 可用于创建一个无论输入什么都总是反序列化为常量值的 codec。 序列化时什么也不做。

```java
Codec<Integer> theMeaningOfCodec = MapCodec.unitCodec(42);
```

#### 数值范围{#numeric-ranges}

`Codec.intRange` 及其伙伴 `Codec.floatRange` 和 `Codec.doubleRange` 可用于创建只接受在指定的**包含两端的**范围内的数字值的 codec， 这适用于序列化和反序列化。

```java
// 不能大于 2
Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
```

#### Pair{#pair}

`Codec.pair` 将两个 codec `Codec<A>` 和 `Codec<B>` 合并为 `Codec<Pair<A, B>>`。 请记住，它只能与序列化到特定字段的Codec配合使用，例如[转换的`MapCodec`](#mapcodec)或
[记录Codec](#merging-codecs-for-record-like-classes)。
结果的 codec 将序列化为结合了两个使用的 codec 字段的 map。

例如，运行这些代码：

```java
// 创建两个单独的装箱的 codec
Codec<Integer> firstCodec = Codec.INT.fieldOf("i_am_number").codec();
Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("this_statement_is_false").codec();

// 将其合并为 pair codec
Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);

// 用它序列化数据
DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, Pair.of(23, true));
```

将输出该 JSON：

```json
{
  "i_am_number": 23,
  "this_statement_is_false": true
}
```

#### Either{#either}

`Codec.either` 将两个 codec `Codec<A>` 和 `Codec<B>` 组合为 `Codec<Either<A, B>>`。 产生的 codec 会在反序列化过程中尝试使用第一个 codec，并且_仅当失败时_才尝试使用第二个。
如果第二个也失败，则会返回第二个 codec 的错误。

#### Map{#maps}

要处理有任意键的 map，如 `HashMap`，可以使用 `Codec.unboundedMap`。 这将返回给定 `Codec<K>` 和 `Codec<V>` 的 `Codec<Map<K, V>>`。 生成的 codec 将序列化为 JSON 对象，或当前 dynamic ops 可用的任何等效对象。

由于 JSON 和 NBT 的限制，使用的键的 codec _必须_序列化为字符串。 这包括类型自身不是字符串但会序列化为字符串的 codec，例如 `Identifier.CODEC`。 看看下面的例子：

```java
// 创建一个用于 ID 到整数映射的 codec
Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);

// 使用它来序列化数据
DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
    Identifier.fromNamespaceAndPath("example", "number"), 23,
    Identifier.fromNamespaceAndPath("example", "the_cooler_number"), 42
));
```

将输出该 JSON：

```json
{
  "example:number": 23,
  "example:the_cooler_number": 42
}
```

正如你所见，因为 `Identifier.CODEC` 直接序列化到字符串，所以这样做有效。 对于不序列化为字符串的简单对象，可以通过使用 [xmap及其相关函数](#mutually-convertible-types-and-you) 来转换它们以达到类似的效果。

### 相互可转换的类型{#mutually-convertible-types}

#### `xmap`{#xmap}

我们有两个可以互相转换的类，但没有继承关系。 例如，原版的 `BlockPos` 和 `Vec3d`。 如果我们有其中一个 codec，我们可以使用 `Codec#xmap` 创建一个双向的特定转换函数。

`BlockPos` 已有 codec，但让我们假装它不存在。 我们可以基于 `Vec3d` 的 codec 这样为它创建一个：

```java
Codec<BlockPos> blockPosCodec = Vec3d.CODEC.xmap(
    // 转换 Vec3d 到 BlockPos
    vec -> new BlockPos(vec.x, vec.y, vec.z),
    // 转换 BlockPos 到 Vec3d
    pos -> new Vec3d(pos.getX(), pos.getY(), pos.getZ())
);

// 当转换一个存在的类 （比如 `X`）到您自己的类 (`Y`)
// 最好是在 `Y` 类中添加 `toX` 和静态的 `fromX` 方法
// 并且使用在您的 `xmap` 调用中使用方法引用
```

#### flatComapMap、comapFlatMap 与 flatXMap{#flatcomapmap-comapflatmap-flatxmap}

`flatComapMap`、`comapFlatMap` 与 `flatXMap` 类似于 xmap，但允许一个或多个转换函数返回 DataResult。 这在实践中很有用，因为特定的对象实例可能并不总是适合转换。

以原版的 `Identifier` 为例。 虽然所有的 identifier 都可以转换为字符串，但并不是所有的字符串都是有效的 identifier，所以使用 xmap 意味着转换失败就会抛出难看的异常。
正因此，其内置 codec 实际上是 `Codec.STRING` 上的 `comapFlatMap`，很好地说明了如何使用：

```java
public class Identifier {
    public static final Codec<Identifier> CODEC = Codec.STRING.comapFlatMap(
        Identifier::validate, Identifier::toString
    );

    // ...

    public static DataResult<Identifier> validate(String id) {
        try {
            return DataResult.success(Identifier.parse(id));
        } catch (InvalidIdentifierException e) {
            return DataResult.error("Not a valid identifier: " + id + " " + e.getMessage());
        }
    }

    // ...
}
```

虽然这些方法非常有用，但方法名称有点让人困惑，所以这里有一个表格帮助你记住应该使用哪一个：

| 方法                      | A -> B 总是有效？ | B -> A 总是有效？ |
| ----------------------- | ------------ | ------------ |
| `Codec<A>#xmap`         | 是            | 是            |
| `Codec<A>#comapFlatMap` | 否            | 是            |
| `Codec<A>#flatComapMap` | 是            | 否            |
| `Codec<A>#flatXMap`     | 否            | 否            |

### 注册表分派{#registry-dispatch}

`Codec#dispatch` 让我们可以定义一个 codec 的注册表，并根据序列化数据中字段的值分派到一个特定的 codec。 当反序列化有不同字段的对象，而这些字段依赖于类型，但不同类型仍代表相同的事物时，这非常有用。

例如我们有一个抽象的 `Bean` 接口与两个实现类：`StringyBean` 和 `CountingBean`。 为了用注册表分派序列化这些，我们需要一些东西：

- 每个 bean 类型的独立 codec。
- 一个 `BeanType<T extends Bean>` 类或 record，代表 bean 的类型并可返回它的 codec。
- 一个在 `Bean` 中可以用于检索其 `BeanType<?>` 的函数。
- 一个 `Identifier` 到 `BeanType<?>` 的 map 或注册表
- 一个基于该注册表的 `Codec<BeanType<?>>`。 如果你使用 `net.minecraft.core.Registry`，那么可以简单地调用  `Registry#getCodec`。

有了这些，就可以创建一个 bean 的注册表分派 codec。

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/Bean.java)

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanType.java)

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/StringyBean.java)

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/CountingBean.java)

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanTypes.java)

```java
// 现在我们可以创建一个用于 bean 类型的 codec
// 基于之前创建的注册表
Codec<BeanType<?>> beanTypeCodec = BeanType.REGISTRY.getCodec();

// 基于那个，这是我们用于 bean 的注册表分派 codec！
// 第一个参数是这个 bean 类型的字段名
// 当省略时默认是 "type"。
Codec<Bean> beanCodec = beanTypeCodec.dispatch("type", Bean::getType, BeanType::codec);
```

我们的新 codec 将会这样将 bean 类序列化为 JSON，仅抓取与特定类型相关的字段：

```json
{
  "type": "example:stringy_bean",
  "stringy_string": "This bean is stringy!"
}
```

```json
{
  "type": "example:counting_bean",
  "counting_number": 42
}
```

### 递归 codec{#recursive-codecs}

有时，使用_自身_来解码特定字段的 codec 很有用，例如在处理某些递归数据结构时。 在原版代码中，这用于 `Component` 对象，可能会存储其他的 `Component` 作为子对象。 可以使用 `Codec#recursive` 构建这样的 codec。

例如，让我们尝试序列化单链列表。 列表是由一组节点的表示的，这些节点既包含一个值，也包含对列表中下一个节点的引用。 然后列表由其第一个节点表示，遍历列表是通过跟随下一个节点来完成的，直到没有剩余节点。 以下是存储整数的节点的简单实现。

```java
public record ListNode(int value, ListNode next) {}
```

我们无法通过普通方法为此构建 codec，因为对 `next` 字段要使用什么 codec？ 我们需要一个 `Codec<ListNode>`，这就是我们还在构建的！ `Codec#recursive` 能让我们使用看上去像魔法的 lambda 来达到这点。

```java
Codec<ListNode> codec = Codec.recursive(
  "ListNode", // codec的名称
  selfCodec -> {
    // 这里，`selfCodec` 代表 `Codec<ListNode>`，就像已经构造好了一样
    // 这个 lambda 应该返回我们从一开始就想要使用的 codec，
    // 通过 `selfCodec` 引用自身
    return RecordCodecBuilder.create(instance ->
      instance.group(
        Codec.INT.fieldOf("value").forGetter(ListNode::value),
         // `next`字段将使用自己 codec 递归处理
        Codecs.createStrictOptionalFieldCodec(selfCodec, "next", null).forGetter(ListNode::next)
      ).apply(instance, ListNode::new)
    );
  }
);
```

序列化的 `ListNode` 可能看起来像这样：

```json
{
  "value": 2,
  "next": {
    "value": 3,
    "next": {
      "value": 5
    }
  }
}
```

## 参考{#references}

- 关于 codec 及相关 API 的更全面的文档，可以在[非官方 DFU JavaDoc](https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec) 中找到。
- 本指南的总体结构受到了 [Forge 社区 Wiki 关于 Codec 的页面](https://forge.gemwire.uk/wiki/Codecs)的重大启发，这是对同一主题的更具 Forge 特色的解读。
