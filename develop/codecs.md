---
title: Codecs
description: A comprehensive guide for understanding and using Mojang's codec system for serializing and deserializing objects.
authors:
  - CelDaemon
  - enjarai
  - Syst3ms
resources:
  https://docs.neoforged.net/docs/datastorage/codecs/: Codecs - NeoForge Docs
  https://docs.neoforged.net/docs/networking/streamcodecs/: Stream Codecs - NeoForge Docs
  https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec: Unofficial DFU JavaDocs
  https://forge.gemwire.uk/wiki/Codecs: Codecs - Forge Community Wiki
---

Codec is a system for easily serializing Java objects, and is included in Mojang's DataFixerUpper (DFU)
library, which is included with Minecraft. In a modding context they can be used as an alternative
to GSON and Jankson when reading and writing custom JSON files, though they're starting to become
more and more relevant, as Mojang is rewriting a lot of old code to use Codecs.

Codecs are used in conjunction with another API from DFU, `DynamicOps`. A codec defines the structure of an object, while dynamic ops are used to define a format to be serialized to and from, such as JSON or NBT. This means any codec can be used with any dynamic ops, and vice versa, allowing for great flexibility.

## Using Codecs {#using-codecs}

### Serializing and Deserializing {#serializing-and-deserializing}

The basic usage of a codec is to serialize and deserialize objects to and from a specific format.

Since a few vanilla classes already have codecs defined, we can use those as an example. Mojang has also provided us with two dynamic ops classes by default, `JsonOps` and `NbtOps`, which tend to cover most use cases.

Now, let's say we want to serialize a `BlockPos` to JSON and back. We can do this using the codec statically stored at `BlockPos.CODEC` with the `Codec#encodeStart` and `Codec#parse` methods, respectively.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#encode-blockpos [Java]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/serialize_blockpos.json [Output]

:::

When using a codec, values are returned in the form of a `DataResult`. This is a wrapper that can represent either a success or a failure. We can use this in several ways: If we just want our serialized value, `DataResult#result` will simply return an `Optional` containing our value, while `DataResult#resultOrPartial` also lets us supply a function to handle any errors that may have occurred. The latter is particularly useful for custom datapack resources, where we'd want to log errors without causing issues elsewhere.

So let's grab our serialized value and turn it back into a `BlockPos`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#parse-blockpos

### Built-in Codecs {#built-in-codecs}

As mentioned earlier, Mojang has already defined codecs for several vanilla and standard Java classes, including but not limited to `BlockPos`, `BlockState`, `ItemStack`, `Identifier`, `Component`, and regex `Pattern`s. Codecs for Mojang's own classes are usually found as static fields named `CODEC` on the class itself, while most others are kept in the `Codecs` class. It should also be noted that all vanilla registries contain a method to get a `Codec`, for example, you can use `BuiltInRegistries.BLOCK.byNameCodec()` to get a `Codec<Block>` which serializes to the block id and back and a `holderByNameCodec()` to get a `Codec<Holder<Block>>`.

The Codec API itself also contains some codecs for primitive types, such as `Codec.INT` and `Codec.STRING`. These are available as statics on the `Codec` class, and are usually used as the base for more complex codecs, as explained below.

## Building Codecs {#building-codecs}

Now that we've seen how to use codecs, let's take a look at how we can build our own. Suppose we have the following class, and we want to deserialize instances of it from JSON files:

<<< @/reference/latest/src/main/java/com/example/docs/codec/CoolBeansClass.java#bean-class

The corresponding JSON file might look something like this:

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/cool_beans.json

We can make a codec for this class by putting together multiple smaller codecs into a larger one. In this case, we'll need one for each field:

- a `Codec<Integer>`
- a `Codec<Item>`
- a `Codec<List<BlockPos>>`

We can get the first one from the aforementioned primitive codecs in the `Codec` class, specifically `Codec.INT`. While the second one can be obtained from the `BuiltInRegistries.ITEM` registry, which has a `byNameCodec()` method that returns a `Codec<Item>`. We don't have a default codec for `List<BlockPos>`, but we can make one from `BlockPos.CODEC`.

### Lists {#lists}

`Codec#listOf` can be used to create a list version of any codec:

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#list-codec [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#list-codec-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/list_codec.json [Output]

:::

It should be noted that codecs created in this way will always deserialize to an `ImmutableList`. If you need a mutable list instead, you can make use of [xmap](#mutually-convertible-types) to convert to one during
deserialization.

### Merging Codecs for Record-Like Classes {#merging-codecs-for-record-like-classes}

Now that we have separate codecs for each field, we can combine them into one codec for our class using
a `RecordCodecBuilder`. This assumes that our class has a constructor containing every field we want to serialize, and that every field has a corresponding getter method. This makes it perfect to use in conjunction with records, but it can also be used with regular classes.

Let's take a look at how to create a codec for our `CoolBeansClass`:

::: code-group

<<< @/reference/latest/src/main/java/com/example/docs/codec/CoolBeansClass.java#bean-codec [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#bean-codec-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/cool_beans.json [Output]

:::

Each line in the group specifies a codec, a field name, and a getter method. The `Codec#fieldOf` call is used to convert the codec into a [map codec](#mapcodec), and the `forGetter` call specifies the getter method used to retrieve the value of the field from an instance of the class. Meanwhile, the `apply` call specifies the constructor used to create new instances. Note that the order of the fields in the group should be the same as the order of the arguments in the constructor.

You can also use `Codec#optionalFieldOf` in this context to make a field optional, as explained in
the [Optional Fields](#optional-fields) section.

### MapCodec, Not to Be Confused With Codec&lt;Map&gt; {#mapcodec}

Calling `Codec#fieldOf` will convert a `Codec<T>` into a `MapCodec<T>`, which is a variant, but not direct
implementation of `Codec<T>`. `MapCodec`s, as their name suggests are guaranteed to serialize into a
key to value map, or its equivalent in the `DynamicOps` used. Some functions may require one over a regular codec.

This particular way of creating a `MapCodec` essentially boxes the value of the source codec inside a map, with the given field name as the key. For example, a `Codec<BlockPos>` when serialized into JSON would look like this:

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/plain_codec.json

But when converted into a `MapCodec<BlockPos>` using `BlockPos.CODEC.fieldOf("pos")`, it would look like this:

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/map_codec.json

While the most common use for map codecs is to be merged with other map codecs to construct a codec for a full class worth of fields, as explained in the [Merging Codecs for Record-like Classes](#merging-codecs-for-record-like-classes) section above, they can also be turned back into regular codecs using `MapCodec#codec`, which will retain the same behavior of
boxing their input value.

#### Optional Fields {#optional-fields}

`Codec#optionalFieldOf` can be used to create an optional map codec. This will, when the specified field is not present in the container during deserialization, either be deserialized as an empty `Optional` or a specified default value.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#optional-field [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#optional-field-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/optional_field.json [Output]

:::

To add the default value, we can pass it as the second parameter in the `optionalFieldOf` method.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#default-field [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#default-field-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/default_field.json [Output]

:::

Do note that if the field is present, but the value is invalid, the field fails to deserialize at all if the field value is invalid.

### Constants, Constraints, and Composition {#constants-constraints-composition}

#### Unit {#unit}

`MapCodec.unitCodec` can be used to create a codec that always deserializes to a constant value, regardless of the input. When serializing, it will do nothing.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#unit-codec [Codec]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/unit.json [Output]

:::

#### Numeric Ranges {#numeric-ranges}

`Codec.intRange` and its pals, `Codec.floatRange` and `Codec.doubleRange` can be used to create a codec that only accepts number values within a specified **inclusive** range. This applies to both serialization and deserialization.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#numeric-range [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#numeric-range-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/numeric_range.json [Output]

:::

#### Pair {#pair}

`Codec.pair` merges two codecs, `Codec<A>` and `Codec<B>`, into a `Codec<Pair<A, B>>`. Keep in mind it only works properly with codecs that serialize to a specific field, such as [converted `MapCodec`s](#mapcodec) or
[record codecs](#merging-codecs-for-record-like-classes).
The resulting codec will serialize to a map combining the fields of both codecs used.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#pair-codec [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#pair-codec-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/pair.json [Output]

:::

#### Either {#either}

`Codec.either` combines two codecs, `Codec<A>` and `Codec<B>`, into a `Codec<Either<A, B>>`. The resulting codec will, during deserialization, attempt to use the first codec, and _only if that fails_, attempt to use the second one.
If the second one also fails, the error of the _second_ codec will be returned.

#### Maps {#maps}

For processing maps with arbitrary keys, such as `HashMap`s, `Codec.unboundedMap` can be used. This returns a
`Codec<Map<K, V>>` for a given `Codec<K>` and `Codec<V>`. The resulting codec will serialize to a JSON object or the equivalent for the current dynamic ops.

Due to limitations of JSON and NBT, the key codec used _must_ serialize to a string. This includes codecs for types that aren't strings themselves, but do serialize to them, such as `Identifier.CODEC`. See the example below:

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#map-codec [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#map-codec-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/map.json [Output]

:::

As you can see, this works because `Identifier.CODEC` serializes directly to a string value. A similar effect can be achieved for simple objects that don't serialize to strings by using [xmap & friends](#mutually-convertible-types) to convert them.

### Mutually Convertible Types {#mutually-convertible-types}

#### `xmap` {#xmap}

Say we have two classes that can be converted to each other, but don't have a parent-child relationship. For example, a vanilla `BlockPos` and `Vec3d`. If we have a codec for one, we can use `Codec#xmap` to create a codec for the other by specifying a conversion function for each direction.

`BlockPos` already has a codec, but let's pretend it doesn't. We can create one for it by basing it on the
codec for `Vec3d` like this:

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#convert-xmap [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#convert-xmap-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/xmap.json [Output]

:::

#### flatComapMap, comapFlatMap, and flatXMap {#flatcomapmap-comapflatmap-flatxmap}

`Codec#flatComapMap`, `Codec#comapFlatMap` and `flatXMap` are similar to xmap, but they allow one or both of the conversion functions to return a DataResult. This is useful in practice because a specific object instance may not always be valid for conversion.

Take for example vanilla `Identifier`s. While all Identifiers can be turned into strings, not all strings are valid Identifiers, so using xmap would mean throwing ugly exceptions when the conversion fails.
Because of this, its built-in codec is actually a `comapFlatMap` on `Codec.STRING`, nicely
illustrating how to use it:

<<< @/reference/latest/src/main/java/com/example/docs/codec/Identifier.java#identifier-flatmap

While these methods are really helpful, their names are a bit confusing, so here's a table to help you remember which one to use:

| Method                  | A -> B always valid? | B -> A always valid? |
| ----------------------- | -------------------- | -------------------- |
| `Codec<A>#xmap`         | Yes                  | Yes                  |
| `Codec<A>#comapFlatMap` | No                   | Yes                  |
| `Codec<A>#flatComapMap` | Yes                  | No                   |
| `Codec<A>#flatXMap`     | No                   | No                   |

### Registry Dispatch {#registry-dispatch}

`Codec#dispatch` let us define a registry of codecs and dispatch to a specific one based on the value of a
field in the serialized data. This is very useful when deserializing objects that have different fields depending on their type, but still represent the same thing.

For example, say we have an abstract `Bean` interface with two implementing classes: `StringyBean` and `CountingBean`. To serialize these with a registry dispatch, we'll need a few things:

- Separate codecs for every type of bean.
- A `BeanType<T extends Bean>` class or record that represents the type of bean, and can return the codec for it.
- A function on `Bean` to retrieve its `BeanType<?>`.
- A map or registry to map `Identifier`s to `BeanType<?>`s.
- A `Codec<BeanType<?>>` based on this registry. If you use a `net.minecraft.core.Registry`, one can be easily made
  using `Registry#byNameCodec`.

With all of this, we can create a registry dispatch codec for beans:

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#registry-dispatch [Codec]
<<< @/reference/latest/src/main/java/com/example/docs/codec/Bean.java#bean-interface [Bean]
<<< @/reference/latest/src/main/java/com/example/docs/codec/BeanType.java#bean-type-record [BeanType]
<<< @/reference/latest/src/main/java/com/example/docs/codec/StringyBean.java#stringy-bean-class [StringyBean]
<<< @/reference/latest/src/main/java/com/example/docs/codec/CountingBean.java#counting-bean-class [CountingBean]
<<< @/reference/latest/src/main/java/com/example/docs/codec/BeanTypes.java#bean-types-class [BeanTypes]

:::

Our new codec will serialize beans to JSON like this, grabbing only fields that are relevant to their specific type:

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/stringy_bean.json

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/counting_bean.json

### Recursive Codecs {#recursive-codecs}

Sometimes it is useful to have a codec that uses _itself_ to decode specific fields, for example when dealing with certain recursive data structures. In vanilla code, this is used for `Component` objects, which may store other `Component`s as children. Such a codec can be constructed using `Codec#recursive`.

For example, let's try to serialize a singly-linked list. This way of representing lists consists of a bunch of nodes that hold both a value and a reference to the next node in the list. The list is then represented by its first node, and traversing the list is done by following the next node until none remain. Here is a simple implementation of nodes that store integers.

<<< @/reference/latest/src/main/java/com/example/docs/codec/ListNode.java#node-record

We can't construct a codec for this by ordinary means, because what codec would we use for the `next` field? We would need a `Codec<ListNode>`, which is what we are in the middle of constructing! `Codec#recursive` lets us achieve that using a magic-looking lambda:

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#recursive-codec [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#recursive-codec-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/recursive.json [Output]

:::

<!---->
