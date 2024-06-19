---
title: Codecs
description: 一份全面的指南，用于理解和使用 Mojang 的 codec 系统，以便序列化和反序列化对象。
authors:
  - enjarai
---

# Codecs

Codec 是一个为了轻松序列化 Java 对象的系统，它被包含在 Mojang 的 DataFixerUpper (DFU) 库中，并且 DFU 被包含在 Minecraft 中。 在模组开发的上下文中，它们可以作为用于读写自定义 json 文件的 GSON 和 Jankson 的替代品。但因为 Mojang 正在重写许多旧代码以使用 Codec，它们和模组开发越来越息息相关。

Codec 与 DFU 的另一个 API `DynamicOps` 一起使用。 一个 Codec 定义一个对象的结构，而 dynamic ops 用于定义一个序列化格式，例如 json 或 NBT。 这意味着任何 codec 都可以与任何 dynamic ops 一起使用，反之亦然，这样就允许了极大的灵活性。

## 使用 Codec

### 序列化与反序列化

Codec 的基本用法是将对象序列化为特定格式或反序列化为特定格式。

一些原版的类已经定义了 codec，我们可以把这些作为例子。 Mojang 默认提供了两个动态操作类 `JsonOps` 和 `NbtOps`，它们可以涵盖大部分的使用场景。

假设现在我们要把一个 `BlockPos` 对象序列化成 json 再反序列化回对象。 我们可以分别使用 `BlockPos.CODEC` 中的静态方法 `Codec#encodeStart` 和 `Codec#parse`。

```java
BlockPos pos = new BlockPos(1, 2, 3);

// 序列化该 BlockPos 为 JsonElement
DataResult<JsonElement> result = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);
```

当使用 codec 的时候，结果以 `DataResult` 的形式返回。 这是一个可以代表成功或者失败的包装。 我们可以通过几种方式使用这个：如果我们只想要我们序列化的值， `DataResult#result` 会简单地返回一个包含我们值的 `Optional` ，而 `DataResult#resultOrPartial` 还允许我们提供一个函数来处理可能发生的任何错误。 后者对于自定义数据包资源尤其有用，因为我们想要记录错误，而不会在其他地方引起问题。

那么，让我们获取我们的序列化值，并将其转换回 `BlockPos` ：

```java
// 在实际编写模组时，您当然会想要适当地处理空的Optional。
JsonElement json = result.resultOrPartial(LOGGER::error).orElseThrow();

// 这里我们有我们的json值，应该对应于 `[1, 2, 3]`,
// 因为这是 BlockPos codec 使用的格式。
LOGGER.info("Serialized BlockPos: {}", json);

// 现在我们将反序列化 JsonElement 为 BlockPos
DataResult<BlockPos> result = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

// 我们将再次只是从 result 中获取我们的值
BlockPos pos = result.resultOrPartial(LOGGER::error).orElseThrow();

// 接下来我们可以看到我们成功序列化和反序列化我们的 BlockPos！
LOGGER.info("Deserialized BlockPos: {}", pos);
```

### 内置的 Codec

正如之前所说，Mojang 已经为几个原版和标准 Java 类定义了 codec，包括但不限于 `BlockPos`、`BlockState`、`ItemStack`、`Identifier`、`Text` 和正则表达式 `Pattern`。 Mojang 自己的 codec 通常可以在类内找到名为 `CODEC` 的静态字段，其他的保持在 `Codecs` 类。 应当注意的是所有原版注册表包含一个 `getCodec()` 方法，例如，你可以使用 `Registries.BLOCK.getCodec()` 获取一个 `Codec<Block>`，可用于将 block 序列化为 id 或是反过来。

Codec API 自己包含了一些 基础类型的 codec，就像 `Codec.INT` 和 `Codec.STRING`。 它们都在 `Codec` 类中作为静态形式存在，并通常作为更复杂 codec 的基础，会在下方做出解释。

## 构建 Codec

现在我们已经知道如何使用 codec，让我们看看我们如何构建自己的 codec。 假设我们有以下类，并且我们希望从json文件中反序列化其实例：

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

相应的 json 文件可能如下所示：

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

我们可以通过将多个较小的 Codec 组合成一个较大的 Codec 来为此类制作一个 Codec。 在这种情况下，我们的每个字段都需要：

- 一个 `Codec<Integer>`
- 一个 `Codec<Item>`
- 一个 `Codec<List<BlockPos>>`

我们可以从 `Codec` 类中上文所述的基本类型 codec 中获取第一个，具体来说是 `Codec.INT`。 第二个可以从 `Registries.ITEM` 注册表中得到，它有一个 `getCodec()` 方法返回一个 `Codec<Item>`。 我们没有用于 `List<BlockPos>` 的默认 codec，但我们可以从 `BlockPos.CODEC` 制作一个。

### List

`Codec#listOf` 可用于创建任意 codec 的列表版本。

```java
Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
```

应该注意的是，以这种方式创建的 codec 总是会反序列化为一个 `ImmutableList`。 如果你需要一个可变的列表，你可以利用 [xmap](#mutually-convertible-types-and-you) 在反序列化期间转换为一个可变列表。

### 合并用于类似 Record 类的 Codec

现在我们有每个字段单独的 codec，我们可以为我们的类使用 `RecordCodecBuilder` 合并它们为一个 codec。 假设我们的类有一个包含我们所有想要序列化的字段的构造方法并且每个字段都有相应的 getter 方法。 这使得它非常适合与 record 一起使用，但也可以用于常规类。

来看看如何为我们的 `CoolBeansClass` 创建一个 codec：

```java
public static final Codec<CoolBeansClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("beans_amount").forGetter(CoolBeansClass::getBeansAmount),
    Registries.ITEM.getCodec().fieldOf("bean_type").forGetter(CoolBeansClass::getBeanType),
    BlockPos.CODEC.listOf().fieldOf("bean_positions").forGetter(CoolBeansClass::getBeanPositions)
    // 最多可以在这里声明 16 个字段
).apply(instance, CoolBeansClass::new));
```

在 group 中的每一行指定一个 codec，一个字段名和一个 getter 方法。 `Codec#fieldOf` 调用用于将编解码器转换为 [map codec](#mapcodec)，而 `forGetter` 调用指定了用于从类的实例中检索字段值的 `getter` 方法。 与此同时，`apply` 调用指定了用于创建新实例的构造函数。 需要注意的是在 group 中的字段顺序需要和构造方法的参数顺序保持一致。

在这个上下文中您也可以使用 `Codec#optionalFieldOf` 使字段可选，解释在 [Optional Fields](#optional-fields) 部分。

### 不要将 MapCodec 与 Codec&lt;Map&gt; 混淆 {#mapcodec}

调用 `Codec#fieldOf` 会将 `Codec<T>` 转换成 `MapCodec<T>`，这是 `Codec<T>` 的一个变体，但不是直接实现。 正如它们名称所暗示的，`MapCodec` 保证序列化成一个键值映射，或者在使用的 `DynamicOps` 中的等效物。 一些函数可能需要使用 `MapCodec` 而不是常规编解码器。

这种创建 `MapCodec` 的特殊方式本质上是在一个map中封装源 codec 的值，使用给定的字段名作为键。 例如，一个 `Codec<BlockPos>` 序列化为 json 时看起来像是这样：

```json
[1, 2, 3]
```

但当使用 `BlockPos.CODEC.fieldOf("pos")` 转换为 `MapCodec<BlockPos>` 是，他看起来像是这样：

```json
{
  "pos": [1, 2, 3]
}
```

虽然 map codec 最常见的用途是与其他 map codec 合并以构造一个完整类字段的 codec，正如前文的 [合并用于类似 Record 类的 Codec](#merging-codecs-for-record-like-classes) 部分所述，但它们也可以通过使用 `MapCodec#codec` 再次转换成常规编解码器，这将保持封装输入值的相同行为。

#### 可选字段

`Codec#optionalFieldOf` 可用于创建一个可选的 map codec。 他将在特定字段不存在于容器中时反序列化为一个空的 `Optional` 或是特定默认值。

```java
// 无默认值
MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");

// 有默认值
MapCodec<BlockPos> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ORIGIN);
```

需要注意，可选字段会默默忽略可能发生的任何错误。 这意味着如果这个字段存在，但值无效，该字段总是会被反序列化为默认值。

**从 1.20.2 开始**，Minecraft 自己 (不是 DFU！) 提供了 `Codecs#createStrictOptionalFieldCodec`，如果字段值无效，则完全不进行反序列化。

### 常量、约束和组合

#### Unit

`Codec.unit` 可被用于创建一个总是反序列化为一个常量值的 codec，无论输入如何。 当序列化时不做任何事。

```java
Codec<Integer> theMeaningOfCodec = Codec.unit(42);
```

#### 数值范围

`Codec.intRange` 及其伙伴 `Codec.floatRange` 和 `Codec.doubleRange` 可用于创建一个只接受在指定**包含**范围内的数字值的 codec。 这适用于序列化和反序列化。

```java
// 不能大于 2
Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
```

#### Pair

`Codec.pair` 将两个 codec `Codec<A>` 和 `Codec<B>` 合并为 `Codec<Pair<A, B>>`。 请记住，它只适用于序列化到特定字段的 codec，例如 [转换的 `MapCodec`](#mapcodec) 或 [record codec](#merging-codecs-for-record-like-classes)。
结果 codec 将序列化为结合了两个使用的 codec 字段的 map。

例如，运行这些代码：

```java
// 创建两个单独的包装 codec
Codec<Integer> firstCodec = Codec.INT.fieldOf("i_am_number").codec();
Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("this_statement_is_false").codec();

// 合并他们为 pair codec
Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);

// 用它序列化 json
DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, Pair.of(23, true));
```

将输出该 json：

```json
{
  "i_am_number": 23,
  "this_statement_is_false": true
}
```

#### Either

`Codec.either` 将两个 codec `Codec<A>` 和 `Codec<B>` 组合为 `Codec<Either<A, B>>`。 在反序列化过程中，结果 codec 将首先尝试使用第一个 codec，**只有在失败时**才尝试使用第二个 codec。
如果第二个也失败，将返回**第二个** codec 的错误。

#### Map

对于处理具有任意键的 map，如 `HashMaps`，可以使用 `Codec.unboundedMap`。 这将返回给定 `Codec<K>` 和 `Codec<V>` 的 `Codec<Map<K, V>>`。 结果 codec 将序列化为一个 json object 或当前 dynamic ops 可用的等效物。

由于 json 和 nbt 的局限性，作为键的 codec **必须**序列化为 string。 这包含类型不是 string 但序列化为他们自身的 codec，例如 `Identifier.CODEC`。 在下面的示例中：

```java
// 创建一个 Identifier 到 Integer 的 map 的 codec
Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);

// 使用它序列化数据
DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
    new Identifier("example", "number"), 23,
    new Identifier("example", "the_cooler_number"), 42
));
```

将输出该 json：

```json
{
  "example:number": 23,
  "example:the_cooler_number": 42
}
```

正如你所见，因为 `Identifier.CODEC` 直接序列化到字符串，所以可以工作。 对于不序列化为字符串的简单对象，可以通过使用 [xmap及其相关函数](#mutually-convertible-types-and-you) 来转换它们以达到类似的效果。

### 相互可转换的类型与您

#### xmap

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
//  并且使用在您的 `xmap` 调用中使用方法引用
```

#### flatComapMap、comapFlatMap 与 flatXMap

`flatComapMap`、`comapFlatMap` 与 `flatXMap` 类似于 xmap，但是他们允许一个或多个转换函数返回 DataResult。 这在实践中很有用，因为特定的对象实例可能并不总是适合转换。

以原版的 `Identifier` 为例。 虽然所有的标识符都可以转换为字符串，但并不是所有的字符串都是有效的标识符，所以使用 xmap 意味着当转换失败时会抛出不雅观的异常。
正因为此，它的内置编解码器实际上是 `Codec.STRING` 上的一个 `comapFlatMap`，很好地说明了如何使用它：

```java
public class Identifier {
    public static final Codec<Identifier> CODEC = Codec.STRING.comapFlatMap(
        Identifier::validate, Identifier::toString
    );

    // ...

    public static DataResult<Identifier> validate(String id) {
        try {
            return DataResult.success(new Identifier(id));
        } catch (InvalidIdentifierException e) {
            return DataResult.error("Not a valid resource location: " + id + " " + e.getMessage());
        }
    }
    
    // ...
}
```

虽然这些方法非常有用，但它们的名称有点让人困惑，所以这里有一个表格帮助您记住应该使用哪一个：

| 方法                      | A -> B 总是有效？ | B -> A 总是有效？ |
| ----------------------- | ------------ | ------------ |
| `Codec<A>#xmap`         | Yes          | Yes          |
| `Codec<A>#comapFlatMap` | No           | Yes          |
| `Codec<A>#flatComapMap` | Yes          | No           |
| `Codec<A>#flatXMap`     | No           | No           |

### 注册表分派

`Codec#dispatch` 让我们可以定义一个 codec 的注册表，并根据序列化数据中字段的值分派到一个特定的 codec。 当反序列化具有不同字段的对象，这些字段依赖于它们的类型，但仍代表相同的事物时，这非常有用。

例如我们有一个抽象的 `Bean` 接口与两个实现类：`StringyBean` 和 `CountingBean`。 为了用注册表分派序列化他们，我们需要一些事：

- 每个 bean 类型的独立 codec。
- 一个 `BeanType<T extends Bean>` 类或 record，代表 bean 的类型并可返回它的 codec。
- 一个在 `Bean` 中可以用于检索其 `BeanType<?>` 的函数。
- 一个 `Identifier` 到 `BeanType<?>` 的 map 或注册表
- 一个基于改注册表的 `Codec<BeanType<?>>`。 如果你使用 `net.minecraft.registry.Registry` 可以简单的调用  `Registry#getCodec`。

集齐这些，我们可以创建一个 bean 的注册表分派 codec。

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
Codec<Bean> beanCodec = beanTypeCodec.dispatch("type", Bean::getType, BeanType::getCodec);
```

我们的新 codec 将会这样将 bean 类序列化为 json，仅抓取与它们特定类型相关的字段：

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

## 引用

- 关于编解码器及相关API的更全面的文档，可以在[非官方DFU JavaDoc](https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec.html)中找到。
- 本指南的总体结构受到了 [Forge 社区 Wiki 关于 Codec 的页面](https://forge.gemwire.uk/wiki/Codecs)的重大启发，这是对同一主题的更具 Forge 特色的解读。
