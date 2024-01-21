---
title: Codecs
description: A comprehensive guide for understanding and using Mojang's codec system for serializing and deserializing objects.
authors:
  - enjarai
---

# Codecs

Codec is a system for easily serializing java objects, and is included in Mojang's DataFixerUpper (DFU)
library, which is included with Minecraft. In a modding context they can be used as an alternative 
to GSON and Jankson when reading and writing custom json files, though they're starting to become 
more and more relevant, as mojang is rewriting a lot of old code to use Codecs.

Codecs are used in conjunction with another API from DFU, `DynamicOps`. A codec defines the structure of an object, while
dynamic ops are used to define a format to be serialized to and from, such as json or NBT. This means any codec can be
used with any dynamic ops, and vice versa, allowing for great flexibility.

## Using Codecs

### Serializing and Deserializing

The basic usage of a codec is to serialize and deserialize objects to and from a specific format.

Since a few vanilla classes already have codecs defined, we can use those as an example. Mojang has also provided us
with two dynamic ops classes by default, `JsonOps` and `NbtOps`, which tend to cover most use cases.

Now, let's say we want to serialize a `BlockPos` to json and back. We can do this using the codec statically stored
at `BlockPos.CODEC` with the `Codec#encodeStart` and `Codec#parse` methods, respectively.

```java
BlockPos pos = new BlockPos(1, 2, 3);

// Serialize the BlockPos to a JsonElement
DataResult<JsonElement> result = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);
```

When using a codec, values are returned in the form of a `DataResult`. This is a wrapper that can represent either a
success or a failure. We can use this in several ways: If we just want our serialized value, `DataResult#result` will
simply return an `Optional` containing our value, while `DataResult#resultOrPartial` also lets us supply a function to
handle any errors that may have occurred. The latter is particularly useful for custom datapack resources, where we'd
want to log errors without causing issues elsewhere.

So let's grab our serialized value and turn it back into a `BlockPos`:

```java
// When actually writing a mod, you'll want to properly handle empty Optionals of course
JsonElement json = result.resultOrPartial(LOGGER::error).orElseThrow();

// Here we have our json value, which should correspond to `[1, 2, 3]`, 
// as that's the format used by the BlockPos codec.
LOGGER.info("Serialized BlockPos: {}", json);

// Now we'll deserialize the JsonElement back into a BlockPos
DataResult<BlockPos> result = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

// Again, we'll just grab our value from the result
BlockPos pos = result.resultOrPartial(LOGGER::error).orElseThrow();

// And we can see that we've successfully serialized and deserialized our BlockPos!
LOGGER.info("Deserialized BlockPos: {}", pos);
```

### Built-in Codecs

As mentioned earlier, Mojang has already defined codecs for several vanilla and standard Java classes, including but not
limited to `BlockPos`, `BlockState`, `ItemStack`, `Identifier`, `Text`, and regex `Pattern`s. Codecs for Mojang's own
classes are usually found as static fields named `CODEC` on the class itself, while most others are kept in the `Codecs`
class. It should also be noted that all vanilla registries contain a `getCodec()` method, for example, you
can use `Registries.BLOCK.getCodec()` to get a `Codec<Block>` which serializes to the block id and back.

The codec api itself also contains some codecs for primitive types, such as `Codec.INT` and `Codec.STRING`. These are
available as statics on the `Codec` class, and are usually used as the base for more complex codecs, as explained below.

## Building Codecs

Now that we've seen how to use codecs, let's take a look at how we can build our own. Suppose we have the following
class, and we want to deserialize instances of it from json files:

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

The corresponding json file might look something like this:

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

We can make a codec for this class by putting together multiple smaller codecs into a larger one. In this case, we'll
need one for each field:

- a `Codec<Integer>`
- a `Codec<Item>`
- a `Codec<List<BlockPos>>`

We can get the first one from the aforementioned primitive codecs in the `Codec` class, specifically `Codec.INT`. While
the second one can be obtained from the `Registries.ITEM` registry, which has a `getCodec()` method that returns a 
`Codec<Item>`. We don't have a default codec for `List<BlockPos>`, but we can make one from `BlockPos.CODEC`.

### Lists

`Codec#listOf` can be used to create a list version of any codec:

```java
Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
```

It should be noted that codecs created in this way will always deserialize to an `ImmutableList`. If you need a mutable
list instead, you can make use of [xmap](#mutually-convertible-types-and-you) to convert to one during
deserialization.

### Merging Codecs for Record-like Classes

Now that we have separate codecs for each field, we can combine them into one codec for our class using
a `RecordCodecBuilder`. This assumes that our class has a constructor containing every field we want to serialize, and
that every field has a corresponding getter method. This makes it perfect to use in conjunction with records, but it can
also be used with regular classes.

Let's take a look at how to create a codec for our `CoolBeansClass`:

```java
public static final Codec<CoolBeansClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("beans_amount").forGetter(CoolBeansClass::getBeansAmount),
    Registries.ITEM.getCodec().fieldOf("bean_type").forGetter(CoolBeansClass::getBeanType),
    BlockPos.CODEC.listOf().fieldOf("bean_positions").forGetter(CoolBeansClass::getBeanPositions)
    // Up to 16 fields can be declared here
).apply(instance, CoolBeansClass::new));
```

Each line in the group specifies a codec, a field name, and a getter method. The `Codec#fieldOf` call is used to convert
the codec into a [map codec](#mapcodec-not-to-be-confused-with-codecltmapgt), and the `forGetter` call specifies the getter 
method used to retrieve the value of the field from an instance of the class. Meanwhile, the `apply` call specifies the 
constructor used to create new instances. Note that the order of the fields in the group should be the same as the order 
of the arguments in the constructor.

You can also use `Codec#optionalFieldOf` in this context to make a field optional, as explained in
the [Optional Fields](#optional-fields) section.

### MapCodec, not to be confused with Codec&lt;Map&gt;

Calling `Codec#fieldOf` will convert a `Codec<T>` into a `MapCodec<T>`, which is a variant, but not direct
implementation of `Codec<T>`. `MapCodec`s, as their name suggests are guaranteed to serialize into a 
key to value map, or its equivalent in the `DynamicOps` used. Some functions may require one over a regular codec.

This particular way of creating a `MapCodec` essentially boxes the value of the source codec 
inside a map, with the given field name as the key. For example, a `Codec<BlockPos>`
when serialized into json would look like this:

```json
[1, 2, 3]
```

But when converted into a `MapCodec<BlockPos>` using `BlockPos.CODEC.fieldOf("pos")`, it would look like this:

```json
{
  "pos": [1, 2, 3]
}
```

While the most common use for map codecs is to be merged with other map codecs to construct a codec for a full class worth of
fields, as explained in the [Merging Codecs for Record-like Classes](#merging-codecs-for-record-like-classes) section
above, they can also be turned back into regular codecs using `MapCodec#codec`, which will retain the same behavior of
boxing their input value.

#### Optional Fields

`Codec#optionalFieldOf` can be used to create an optional map codec. This will, when the specified field is not present
in the container during deserialization, either be deserialized as an empty `Optional` or a specified default value.

```java
// Without a default value
MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");

// With a default value
MapCodec<BlockPos> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ORIGIN);
```

Do note that optional fields will silently ignore any errors that may occur during deserialization. This means that if
the field is present, but the value is invalid, the field will always be deserialized as the default value.

**Since 1.20.2**, Minecraft itself (not DFU!) does however provide `Codecs#createStrictOptionalFieldCodec`, 
which fails to deserialize at all if the field value is invalid.

### Constants, Constraints, and Composition

#### Unit

`Codec.unit` can be used to create a codec that always deserializes to a constant value, regardless of the input. When
serializing, it will do nothing.

```java
Codec<Integer> theMeaningOfCodec = Codec.unit(42);
```

#### Numeric Ranges

`Codec.intRange` and its pals, `Codec.floatRange` and `Codec.doubleRange` can be used to create a codec that only 
accepts number values within a specified **inclusive** range. This applies to both serialization and deserialization.

```java
// Can't be more than 2
Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
```

#### Pair

`Codec.pair` merges two codecs, `Codec<A>` and `Codec<B>`, into a `Codec<Pair<A, B>>`. Keep in mind it only works 
properly with codecs that serialize to a specific field, such as 
[converted `MapCodec`s](#mapcodec-not-to-be-confused-with-codecltmapgt) or 
[record codecs](#merging-codecs-for-record-like-classes). 
The resulting codec will serialize to a map combining the fields of both codecs used.

For example, running this code:

```java
// Create two seperate boxed codecs
Codec<Integer> firstCodec = Codec.INT.fieldOf("i_am_number").codec();
Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("this_statement_is_false").codec();

// Merge them into a pair codec
Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);

// Use it to serialize data
DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, Pair.of(23, true));
```

Will output this json:

```json
{
  "i_am_number": 23,
  "this_statement_is_false": true
}
```

#### Either

`Codec.either` combines two codecs, `Codec<A>` and `Codec<B>`, into a `Codec<Either<A, B>>`. The resulting codec will,
during deserialization, attempt to use the first codec, and *only if that fails*, attempt to use the second one. 
If the second one also fails, the error of the *second* codec will be returned.

#### Maps

For processing maps with arbitrary keys, such as `HashMap`s, `Codec.unboundedMap` can be used. This returns a
`Codec<Map<K, V>>` for a given `Codec<K>` and `Codec<V>`. The resulting codec will serialize to a json object or 
whatever equivalent is available for the current dynamic ops.

Due to limitations of json and nbt, the key codec used *must* serialize to a string. This includes codecs for types that
aren't strings themselves, but do serialize to them, such as `Identifier.CODEC`. See the example below:

```java
// Create a codec for a map of identifiers to integers
Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);

// Use it to serialize data
DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
    new Identifier("example", "number"), 23,
    new Identifier("example", "the_cooler_number"), 42
));
```

This will output this json:

```json
{
  "example:number": 23,
  "example:the_cooler_number": 42
}
```

As you can see, this works because `Identifier.CODEC` serializes directly to a string value. A similar effect can be 
achieved for simple objects that don't serialize to strings by using [xmap & friends](#mutually-convertible-types-and-you) to 
convert them.

### Mutually Convertible Types and You

#### xmap

Say we have two classes that can be converted to each other, but don't have a parent-child relationship. For example,
a vanilla `BlockPos` and `Vec3d`. If we have a codec for one, we can use `Codec#xmap` to create a codec for the other by
specifying a conversion function for each direction.

`BlockPos` already has a codec, but let's pretend it doesn't. We can create one for it by basing it on the
codec for `Vec3d` like this:

```java
Codec<BlockPos> blockPosCodec = Vec3d.CODEC.xmap(
    // Convert Vec3d to BlockPos
    vec -> new BlockPos(vec.x, vec.y, vec.z),
    // Convert BlockPos to Vec3d
    pos -> new Vec3d(pos.getX(), pos.getY(), pos.getZ())
);

// When converting an existing class (`X` for example) 
// to your own class (`Y`) this way, it may be nice to 
// add `toX` and static `fromX` methods to `Y` and use 
// method references in your `xmap` call.
```

#### flatComapMap, comapFlatMap, and flatXMap

`Codec#flatComapMap`, `Codec#comapFlatMap` and `flatXMap` are similar to xmap, but they allow one or both of the 
conversion functions to return a DataResult. This is useful in practice because a specific object instance may not 
always be valid for conversion. 

Take for example vanilla `Identifier`s. While all identifiers can be turned into strings, not all strings are valid identifiers, 
so using xmap would mean throwing ugly exceptions when the conversion fails. 
Because of this, its built-in codec is actually a `comapFlatMap` on `Codec.STRING`, nicely
illustrating how to use it:

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

While these methods are really helpful, their names are a bit confusing, so here's a table to help you remember which 
one to use:

| Method                  | A -> B always valid? | B -> A always valid? |
| ----------------------- | -------------------- | -------------------- |
| `Codec<A>#xmap`         | Yes                  | Yes                  |
| `Codec<A>#comapFlatMap` | No                   | Yes                  |
| `Codec<A>#flatComapMap` | Yes                  | No                   |
| `Codec<A>#flatXMap`     | No                   | No                   |

### Registry Dispatch

`Codec#dispatch` let us define a registry of codecs and dispatch to a specific one based on the value of a 
field in the serialized data. This is very useful when deserializing objects that have different fields depending on
their type, but still represent the same thing.

For example, say we have an abstract `Bean` interface with two implementing classes: `StringyBean` and `CountingBean`. To serialize
these with a registry dispatch, we'll need a few things:

- Separate codecs for every type of bean.
- A `BeanType<T extends Bean>` class or record that represents the type of bean, and can return the codec for it.
- A function on `Bean` to retrieve its `BeanType<?>`.
- A map or registry to map `Identifier`s to `BeanType<?>`s.
- A `Codec<BeanType<?>>` based on this registry. If you use a `net.minecraft.registry.Registry`, one can be easily made 
  using `Registry#getCodec`.

With all of this, we can create a registry dispatch codec for beans:

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/Bean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanType.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/StringyBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/CountingBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanTypes.java)

```java
// Now we can create a codec for bean types 
// based on the previously created registry
Codec<BeanType<?>> beanTypeCodec = BeanType.REGISTRY.getCodec();

// And based on that, here's our registry dispatch codec for beans! 
// The first argument is the field name for the bean type.
// When left out, it will default to "type".
Codec<Bean> beanCodec = beanTypeCodec.dispatch("type", Bean::getType, BeanType::getCodec);
```

Our new codec will serialize beans to json like this, grabbing only fields that are relevant to their specific type:

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

## References

- A much more comprehensive documentation of codecs and related APIs can be found at the
  [Unofficial DFU JavaDoc](https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec.html).
- The general structure of this guide was heavily inspired by the
  [Forge Community Wiki's page on codecs](https://forge.gemwire.uk/wiki/Codecs), a more Forge-specific take on the same
  topic.