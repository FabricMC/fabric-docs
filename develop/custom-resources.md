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

## Deciding on your Resource Type {#deciding-on-a-resource-type}

Which type you'll use is completely up to you and your needs. Most of the time, the type depends on
the environment you're working with. For example, for recipes for a custom block, you'd want to use the
server data, while client resources might be handy if you want to allow the creation of new themes for your screen.

# Creating a JsonDataLoader

> This article assumes you have a class for the object you want to read with a proper deserialization method.
> For example, `MyClass#deserialize(JsonElement element)`. If you're unsure of how to create such methods, consider
> taking a closer look at what a [Codec](../develop/codecs.md) is.

The `JsonDataLoader` will function as the main key point in our system. It provides the `apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler)`
method, which we'll override to load the read files into our storage.

> We'll create a `JsonDataLoader` for loading JSON files from the `fruit` folder
> in a data pack, deserialize them into actual fruits, and store them
> for further retrieval.

Let's start by creating a class itself. It should extend `JsonDataLoader`, and override the constructor
and the `apply` method.


@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/resources/EmptyDataLoader.java)

Let's add some static fields, which we'll then use later on. We're also going to use
a `HashMap` to store our data, but you can always replace it with a more advanced registry.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/resources/FruitDataLoader.java)

Our current constructor takes in a Gson object and a string. We've already created a custom
Gson object as a static field, so we can simply replace it. The string, on the other hand, is a bit
trickier. It represents the **folder** within the pack. In our example, setting it to `"fruit"`
will load the files that are located within the `fruit` folder of the pack:

```
root
└─ example
   └─ fruit
      ├─ orange.json
      ├─ apple.json
      └─ banana.json
```

With that being out of place, or constructor should now look like this:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/resources/FruitDataLoader.java)

## Deserializing Fields

Now that our class looks clean, we can take a proper look at the `apply` method. This is the place where
we're going to deserialize `JsonElements` into custom objects, and store them afterward.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/resources/FruitDataLoader.java)

We can use an iterator to keep track on how many items we've loaded so far, to then log it to the console.
This is genuinely useful for debugging purposes.

We should clear all existing entries before adding new ones, and this is VERY IMPORTANT. This
ensures that the deletion of a file **actually deletes** it after reloading.

The `Map.Entry<>` is used here to bundle an identifier with a JsonElement. While it's clear what the JsonElement
represents - the JsonElement read from the file, what is the identifier for?

It's actually quite simple, the identifier corresponds to the location of the file within
your folder, specified by that very string in the constructor. In our example with fruits, the identifiers
would be:

```
example:orange
example:apple
example:banana
```

We then iterate over the entries of the prepared map, deserialize them, and load them into
our very own registry, a simple `HashMap` in our case.

# Registering the Custom Resource

With that our `JsonDataLoader` is done, all that is left is to register it within our `ModInitializer`.
Since the registration method is quite big, you might want to create a separate static method. Let's take
a look:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/resources/FruitDataLoader.java)

Right away, you can see that we use the `ResourceType` enum to specify the resource type. We've discussed
what they are at the beginning of the article. We'll use `SERVER_DATA`, but you can go for `CLIENT_RESOURCES`
if you need.

`getFabricId()` returns an identifier that is then used by Fabric to further register our `JsonDataLoader`. This
is usually the same as the `dataType` string parsed to the constructor, but with a proper namespace.

We create a new instance of our `JsonDataLoader` class in the `reload()` method, and then call the `reload()`
method again, but this time on the instance we've created. This ensures that our resources get reloaded, when needed
(Either by pressing CTRL + T or typing /reload, depending on your `ResourceType`).

---

An example `Fruit` class used in this article. Feel free to copy it and adjust for your needs.

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/resources/FruitDataLoader.java)