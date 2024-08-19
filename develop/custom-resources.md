---
title: Custom Resources
description: A comprehensive guide on how to use the resource system and load custom resources during the runtime.
authors:
  - Manchick
---

# Custom Resources {#what-are-custom-resources}

Some resources in Minecraft are loaded during the runtime, instead of being hard-coded into the game.
Each resource type falls into one of two categories: **Client Resources** and **Server Data**.
Client Resources are the ones that are loaded from a **resource pack**, thus being fully client-side, and accessible at any time.
Server Data, on the other hand, is used for various tasks on the server, and is loaded from a **data pack**.

::: info
In this article, we'll create two separate `JsonDataLoaders`. A server-side one,
to load `Fruit` objects from a `data pack`, and a client-side one - to load `Book`s
from a resource pack. We'll also briefly discuss how `Codec`s could help us on
our journey! In case you're unfamiliar with them, consider taking a closer look
at what [Codecs](../develop/codecs) are.
:::

## Creating a JsonDataLoader {#creating-a-json-data-loader}

The `JsonDataLoader` will function as the main key point in our system. It provides the `apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler)`
method, which we'll override to read files and load them into our storage.

Imagine you want to add a system, where all players can create their own `Fruit`s. Why not
make it dynamic and allow their creation with a data pack? We'll create a `FruitDataLoader`
that serves our needs. Make it extend `JsonDataLoader` and override all required methods.

Let's introduce some static fields, which we'll then use later on? We're also going to use
a `HashMap` to store our data, but you can always replace it with a more advanced registry.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/resources/FruitDataLoader.java)

Our current constructor takes in a `Gson` object and a string. We've already created a custom
`Gson` object as a static field, so we can simply replace it. The string, on the other hand, is a bit
trickier. It represents the **folder** within the pack. In our example, setting it to `"fruit"`
will load the files that are located within the `fruit` folder of the **data pack**:

```
root
└─ example
   └─ fruit
      ├─ orange.json
      ├─ apple.json
      └─ banana.json
```

With that being out of place, our constructor should now look like this:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/resources/FruitDataLoader.java)

## Deserializing Fields {#deserializing-fields}

Now that our class looks clean, we can take a proper look at the `apply` method. This is the place where
we're going to deserialize `JsonElements` into custom objects, and store them afterward.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/resources/FruitDataLoader.java)

::: warning
We should clear all existing entries before adding new ones, and this is **VERY IMPORTANT**. This
ensures that the deletion of a file **actually deletes** it after reloading.
:::

`JsonDataLoader` allows us to easily read **JSON** files from the specified folder. The `apply` method
is the exact place where you tend to do it. Since we deal with **JSON** files here, it makes total sense
(and is even preferred) to use `Codec`s for deserialization purposes. Let's take a look at an example
of how such codec might look like:

```java
Codec<Fruit> CODEC = Codec.STRING.fieldOf("name").xmap(Fruit::new, Fruit::name).codec();
```

This right here is probably the simplest codec you can get. It directly maps `name` field to
a `Fruit` object, meaning our JSON files should look like this:

```json
{
  "name": "apple"
}
```

The `prepared` map is an argument parsed to our `apply` method. It essentially maps `Identifiers` to
`JsonElements`. Identifiers specify paths within the folder, e.g `example:orange`, `example:apple`.
JsonElements are even more handy. Since we're using codecs, we can easily convert them into `Fruit` objects,
by calling the **deserialization** method. It may vary, but it always looks more-or-less like this:

```java
public static Optional<Fruit> deserialize(JsonElement json) {
	DataResult<Fruit> result = CODEC.parse(JsonOps.INSTANCE, json);
	return result.resultOrPartial(LOGGER::error);
}
```

::: tip
We can use an iterator to keep track on how many items we've loaded so far, to then log it to the console.
This is genuinely useful for debugging purposes.
:::

## Registering the Custom Resource {#registering-the-custom-resource}

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/resources/FruitDataLoader.java)

Custom resources, just like a lot of other features, require a proper registration within your `ModInitializer`.
Now, this registration process is quite big, so you might want to put it in a static method.

::: tip
Since our `FruitDataLoader` tends to load files from a **data pack**, we use the `SERVER_DATA` `ResourceType` here.
If your object should instead be loaded from a **resource pack**, considering changing this value to `CLIENT_RESOURCES`
:::

`getFabricId()` returns an identifier that is then used by Fabric to further register our `JsonDataLoader`. This
is usually the same as the `dataType` string parsed to the constructor, but with a proper namespace.

We create a new instance of our `JsonDataLoader` class in the `reload()` method, and then call the `reload()`
method again, but this time on the instance we've created. This ensures that our resources get reloaded, when needed.

With that our `FruitDataLoader` is done, and we can focus on the `BookDataLoader` to load our books.
Luckily, the process is similar. In fact, it's pretty much the same!

::: details Take a look at the `BookDataLoader` class
@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/resources/BookDataLoader.java)
:::

---

Example `Fruit` and `Book` classes used in this article. Feel free to copy them and adjust for your needs.
::: code-group
@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/resources/Fruit.java)
@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/resources/Book.java)
:::
