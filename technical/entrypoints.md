---
title: Entrypoints
description: Technical information about Fabric Loader's Entrypoints system.
---

# Entrypoints

Entrypoints are declared in a mod's
[fabric.mod.json](../specifications/fabric-mod-json.md) to expose parts of the
code for usage by Fabric Loader or other mods. They are primarily used
to make some code run during game initialization to initialize mods,
though the entrypoint system has other uses as well. Entrypoints are
loaded by language adapters, which will attempt to produce a Java object
of a specified type using the name of the code object.

An entrypoint is exposed under some name, refers to some code object and
must be based on a familiar **entrypoint prototype**. An entrypoint
prototype defines the name (such as "main" or "client") and the expected
type of object that the entrypoint should refer to (such as the
`ModInitializer` interface). An **entrypoint prototype provider**
declares an entrypoint prototype and is responsible for accessing
entrypoints, and can state how the they will be used. Fabric Loader
provides some built-in entrypoint prototypes, while mods can also
provide their own.

Entrypoints can be considered a more powerful implementation of [Java
Service Provider Interfaces](https://www.baeldung.com/java-spi).

### Basic usage

A mod can declare any amount of entrypoints under different names in
their fabric.mod.json. "main" is used to initialize the parts of a mod
that are common to both a Minecraft client and a dedicated server, such
as registry entries. "client" is used to initialize the parts of a mod
that are reserved for clients only, such as registration of
rendering-related objects.

``` javascript
{
  [...]
  "entrypoints": {
    "main": [
      "net.fabricmc.ExampleMod"
    ],
    "client": [
      "net.fabricmc.ExampleClientMod"
    ]
  }
  [...]
}
```

**Caution:** It is recommended to use separate classes for main, client
and server entrypoints to avoid class loading issues. Consider the case
where the same class is used for both a main and a client entrypoint.
When launched on a dedicated server, even if the "client" entrypoint is
never loaded, the class that contains the client initialization logic
will be. Even if the client logic will never be executed, the act of
merely loading the code may trigger class loading issues.

### Built-in entrypoint prototypes

Fabric Loader provides four built-in entrypoint prototypes for mod
initialization, some of which are designed to deal with initialization
with respect to physical sides (see [side](/tutorial/side)). The main,
client and server entrypoints are loaded and called early during the
game's initialization, at a point where **most but not all** game
systems are ready for modification. These entrypoints are typically used
to bootstrap mods by registering registry objects, event listeners and
other callbacks for doing things later.

-   **main**: The first normal initialization entrypoint to run. Uses
    the type `ModInitializer` and will call `onInitialize`.
-   **client**: Will run after main only in a physical client. Uses the
    type `ClientModInitializer` and will call `onInitializeClient`.
-   **server**: Will run after main only in a physical server. Uses the
    type `DedicatedServerModInitializer` and will call
    `onInitializeServer`.
-   **preLaunch (not recommended for general use)**: The earliest
    possible entrypoint, which is called just before the game launches.
    Use with caution to not interfere with the game's initialization.
    Uses the type `PreLaunchEntryPoint` and will call `onPreLaunch`.

All main entrypoints are called before all client/server entrypoints.
The exact time these entrypoints are called is unspecified and may vary
between different versions of the game.

### Code reference types

An entrypoint's code reference is turned into an instance of the
entrypoint prototype's type. The most common way to make an entrypoint
is to refer to a class which implements the expected type, but these
code references can be made in multiple ways. Internally, a language
adapter is responsible for interpreting the references and turning them
into instances. The default language adapter is designed for Java code,
and thus supports the following types of references:

-   **Class reference**: ex. `net.fabricmc.example.ExampleMod` refers to
    the non-abstract class by this name. The class must have a public
    constructor with no arguments. The class must implement or extend
    the expected type. The resulting object is a new instance of the
    class.
-   **Method reference**: ex. `net.fabricmc.example.ExampleMod::method`
    refers to a public method in that class by that name. If the method
    is nonstatic, a new instance of the class is constructed for the
    method to be called on, meaning the class must be non-abstract and
    also have a public constructor with no arguments. Methods can only
    be used for interface types. The method must accept the same
    arguments and have the same return type as the abstract method(s) in
    the interface. The resulting value is a proxy implementation of the
    interface, which will implement abstract methods by delegating to
    this method.
-   **Static field reference**: ex.
    `net.fabricmc.example.ExampleMod::field` refers to the field in that
    class by that name. The field must be static and public. The the
    type of the field must be compatible with the expected type. The
    resuling value is the value of that field.

References to class members must be unambiguous, meaning the class must
contain one and only one field or method with the targeted name. The
language adapter cannot resolve methods overloads. In case of ambiguity,
the entrypoint will fail to resolve.

Language adapters for other languages can be implemented by mods.
[fabric-language-kotlin](https://github.com/FabricMC/fabric-language-kotlin)
provides a language adapter for Kotlin.

### Other entrypoint applications

Mods can call each others' entrypoints for integration purposes. An
entrypoint is loaded lazily when entrypoints for a specific entrypoint
prototype are requested, which makes an entrypoint an excellent tool for
optional mod integrations. A mod may become an entrypoint prototype
provider by declaring that other mods should provide entrypoints based
on an entrypoint prototype, often using a class or interface that the
mod provides in its API. Mods can safely use this class or interface
even if the provider is not installed (rendering the class or interface
inaccessible) because entrypoints are loaded only on request. When the
provider is not present, the entrypoint will simply be ignored.

Entrypoint instances can be accessed by calling
`FabricLoader#getEntrypointContainers(name, type)`. This returns a list
of entrypoint containers. These containers contain the entrypoint
instance and the mod container of the mod which provided the instance.
This can be used by a mod to determine which mods have registered an
entrypoint.

Entrypoint instances are memoized by their name and also their type.
Using the same code reference for multiple entrypoints will result in
multiple instances. Though highly absurd in practice, if
`getEntrypoints` is called multiple times with the same name but
different types, instances are constructed and memoized per type.

### A note about load order and phases (or a lack thereof)

Fabric Loader does not have a concept of a load order or loading phases.
Initializer entrypoints are the mechanism with which most mod loading is
usually done, but whether or not an initializer has been called does not
determine whether or not a mod can be considered to be "loaded". Thus,
it is unreasonable to expect that a mod has completed its modifications
to the game after its initializers have been called. Additionally, the
order in which entrypoints are called is mostly undefined and cannot be
altered. The only guarantee is that a list of initializers in a
fabric.mod.json file are called in the order in which they are declared.
Fabric Loader does not provide multiple phases of initializers to work
around the lack of order, either.

A common example is the expectation that mod A should be able to load
after mod B because mod A will replace an object registered by mod B.
Alternatively, mod C wants to be loaded before mod D because mod D will
do something in response to a registration performed by mod C. This is
cannot be done for two reasons:

1.  Mod initializers are not required to represent a transition in a
    "mod loading lifecycle" so that after the initializer is called, all
    its registry objects are registered.
2.  The order in which mod initializers are called is undefined, and
    cannot be influenced so that mod A's initializers are called after
    mod B's initializers, or so that mod C's initializers are called
    before mod D's initializers.

Leaving aside the missing guarantee of registration of all objects in
initializers, one might argue that there should therefore be other
entrypoints to perform "pre-initialization" and "post-initialization" so
that there is a sense of order. This creates a multi-phase loading
scheme, which in practice creates issues with the establishment of
conventions for which operations are to be performed in which phase,
uncertainty and lack of adherence to these conventions and outliers
which do not fit.