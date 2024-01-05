---
title: Fabric Loader
description: Technical information about Fabric Loader.
---

# Fabric Loader

Fabric Loader is Fabric's lightweight mod loader. It provides the
necessary tools to make Minecraft modifiable without depending on a
specific version of the game. Game specific (and game version specific)
hooks belong in Fabric API. It is possible to adapt Fabric Loader for
many Java applications (for instance games like Slay the Spire and
Starmade).

Fabric Loader has services to allow mods to have some code executed
during initialization, to transform classes, declare and provide mod
dependencies, all in a number of different environments.

For each Fabric Loader version, there is Javadoc available at

    https://maven.fabricmc.net/docs/fabric-loader-[loader version]

For example, Fabric Loader 0.11.1's documentation is at
<https://maven.fabricmc.net/docs/fabric-loader-0.11.1/>

## Features

### Mods

A mod is a jar with a [fabric.mod.json](/documentation/fabric_mod_json)
mod metadata file in its root declaring how it should be loaded. It
primarily declares a mod ID and version as well as
[entrypoints](/documentation/entrypoint) and mixin configurations. The
mod ID identifies the mod so that any mod with the same ID is considered
to be the same mod. Only one version of a mod may be loaded at a time. A
mod may declare other mods that it depends on or conflicts with. Fabric
Loader will attempt to satisfy dependencies and load the appropriate
versions of mods, or fail to launch otherwise.

Fabric Loader makes all mods equally capable of modifying the game. As
an example, anything Fabric API does can be done by any other mod.

Mods are loaded both from the classpath and from the mods directory.
They are expected to match the mappings in the current environment,
meaning Fabric Loader will not remap any mods.

### Nested JARs

Nested JARs allow a mod to provide its own dependencies, so Fabric
Loader can pick the best version matching the dependencies instead of
requiring separate installation of dependencies. They also allow clean
packaging of submodules, so each module can be used separately. Non-mod
libraries can be repackaged as mods for nested JAR usage. A mod may
bundle a number of other mods within its JAR. A nested JAR must itself
also be a mod, which again can have nested JARs. Fabric Loader will load
nested JARs while attempting to satisfy dependency constraints.

Nested JARs are not extracted, they are instead loaded in in-memory file
system using jimfs. See the
[guidelines](/tutorial/loader04x#nested_jars) for how to use nested JARs
effectively. Nested JARs must be declared by their paths relative to the
containing JAR's root.

### Entrypoints

Fabric Loader has an [entrypoint](/documentation/entrypoint) system,
which is used by mods to expose parts of the code for usage by Fabric
Loader or other mods. Fabric Loader uses it for mod initialization.
Initializers are loaded and called early during the game's
initialization which allows a mod to run some code to make its
modifications. These entrypoints are typically used to bootstrap mods by
registering registry objects, event listeners and other callbacks for
doing things later.

### Mixin

Mixin allows mods to transform Minecraft classes and even mod classes,
and is the only method of class transformation that Fabric Loader
officially supports. A mod can declare its own mixin configuration which
enables the use of Mixin.

Mixin was not specifically made for Fabric, so Fabric Loader uses a
slightly modified version of Mixin. However, the documentation of the
upstream version is still mostly valid. The modifications are mostly
related to making it work without LegacyLauncher/LaunchWrapper.

### Mappings

Fabric Loader provides an API to determine names of classes, fields and
methods with respect to the different environments that mods may be
loaded in. This can be used to support reflection in any environment
provided Fabric Loader has access to mappings to resolve the name.

## Fabric Loader Internals

### Deobfuscation

When launched in a non-development environment, Fabric Loader will
[remap](/tutorial/mappings) the Minecraft jar and realms client jar to
intermediary names. Mods are expected to be mapped to intermediary,
which will be compatible with this environment. The remapped jars are
cached and saved in
`${gameDir}/.fabric/remappedJars/${minecraftVersion}` for re-use across
launches.

### Class loading and transformation

Fabric Loader depends on a custom class loader to transform some classes
at runtime. Classes belonging to a mod or Minecraft are loaded with a
class loader that applies transformations to classes before they are
loaded. Other classes, those belonging to other libraries, cannot be
transformed. With Knot, these classes are delegated to the default
classloader for isolation and performance.

Fabric Loader will perform side stripping on mod classes and Minecraft
classes depending on the physical side that is launched. This involves
completely removing classes, methods and fields annotated with
`@Environment` annotations where the environment does not match. It also
involves removing interface implementations on classes annotated with
`@EnvironmentInterface` where the environment does not match. On
Minecraft classes, this is used to simulate which classes and members
that are available in the targeted runtime development environment. The
annotation can be applied to mod classes to avoid class loading issues.

Package access hacks might be applied to Minecraft classes depending on
the mappings in the current environment. With official (obfuscated)
names and intermediary names, most classes are placed in the same
package. However, Yarn mappings place classes in various packages which
sometimes creates illegal access violations due to the access rules of
protected and package-private members. Therefore, in a development
environment where such access issues are expected to exist, Minecraft
classes are transformed so that package-private and protected members
are made public. Outside a development environment we know that the
package structure is flat, so the package access hack is not needed.
Note that this transformation is applied at runtime, which means it is
not visible in the source.

### Launchers

A launcher (not to be confused with the game launcher) is something
provides a method to use Fabric Loader in a Java process. A launcher
must provide a few features to support Fabric Loader's functionality
such as class transformation and dynamic class loading. Knot and
LegacyLauncher/LaunchWrapper are the current supported launchers.

Knot is the default launcher included in Fabric Loader, designed
specifically for Fabric Loader's features with support for modern
versions of Java. Knot has the main classes
`net.fabricmc.loader.launch.knot.KnotClient` and
`net.fabricmc.loader.launch.knot.KnotServer` for clients and servers
respectively.

When launching a server using Knot in a production environment, the
`net.fabricmc.loader.launch.server.FabricServerLauncher` main class must
be used, which is a main class that wraps the launch of KnotServer. It
can be configured with the `fabric-server-launcher.properties` placed in
the current working directory. The file has one property, `serverJar`,
whose value is 'server.jar' by default, which is used to configure the
path to the minecraft server jar.

Fabric Loader can also be launched with LegacyLauncher/LaunchWrapper
using the tweakers `net.fabricmc.loader.launch.FabricClientTweaker` and
`net.fabricmc.loader.launch.FabricServerTweaker` for clients and servers
respectively. However, LegacyLauncher/LaunchWrapper support is currently
outdated.

## Integrating Fabric Loader into a dedicated application

As stated above, Fabric Loader can be used outside of Minecraft to add
mod loading for Java applications. This section will briefly summarize
what do you need to do to integrate Fabric loader to your Java
game/dedicated app. This was tested with **Gradle 7** (specifically
7.3.1), **Java 8** and **IntelliJ IDEA 2022.3.1 (Community Edition)**.

### Gradle dependencies

First, you'll need to add Fabric Loader as a dependency, and its
dependencies in you `build.gradle`:

``` groovy
repositories {
    // your other repositories...
    
    maven {
        url "https://repo.spongepowered.org/maven/"
    }
    maven {
        url "https://maven.fabricmc.net/"
    }
}

dependencies {
    // your dependencies...
    
    implementation "net.fabricmc:fabric-loader:0.13.0"
    implementation "net.fabricmc:access-widener:2.1.0"
    implementation "net.fabricmc:tiny-mappings-parser:0.2.2.14"
    implementation "com.google.guava:guava:21.0"
    implementation "com.google.code.gson:gson:2.8.7"
    implementation "org.ow2.asm:asm:9.2"
    implementation "org.ow2.asm:asm-analysis:9.2"
    implementation "org.ow2.asm:asm-commons:9.2"
    implementation "org.ow2.asm:asm-tree:9.2"
    implementation "org.ow2.asm:asm-util:9.2"
}
```

### Launching your app with Fabric Loader

If you've launched your app before through your own `main(String[])`, to
incorporate Fabric Loader into your app, you will need to set its main
class to use one of the Knot launchers. For client-side (or if you don't
have client/server separation), specify
`net.fabricmc.loader.impl.launch.knot.KnotClient` as your main class.
For server-side only, specify
`net.fabricmc.loader.impl.launch.knot.KnotServer`. You might ask, "but
how will Fabric Loader know how to load my game?" That's where
**GameProvider** comes into play.

### Setting up GameProvider

**GameProvider** is an interface that lets Fabric Loader locate your
game, configure Fabric launcher, and then finally launch the game. For
Fabric Loader to instantiate your implementation of **GameProvider**,
you need to define it as a service that can be found by
[ServiceLoader](https://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html).
In your project's resources, create
`META-INF/services/net.fabricmc.loader.impl.game.GameProvider` file, and
paste into it the full class name to your **GameProvider**. Something
like this:

    net.developer.app.fabric.AppGameProvider

This should allow Fabric Loader find your **GameProvider**. Now let's
breakdown **GameProvider** interface.

#### Implementing GameProvider

For your game actually to launch, you need to carefully implement
**GameProvider** in a specific way. Here is a breakdown of each method
does:

-   `getGameId()` and `getGameName()`, self-explanatory, app's ID like
    `epic_app`, and app's name `Epic App`.
-   `getRawGameVersion()` and `getNormalizedGameVersion()`, it's not
    entirely clear, but raw version can include git-hash, or any
    information about the game that developer may want to know (like
    `v0.1.2+build17#f73acde`), while normalized version seems to be for
    display (like `v0.1.2`).
-   `getBuiltinMods()` allows you to provide built-in mod(s), which can
    be used by mods to target specific version, but it's completely
    optional, and you can just return `Collections.emptyList()`.
-   `getEntryPoint()`, full class name of your main app class. It seems
    to be used only in Minecraft.
-   `getLaunchDirectory()` is a very important method. It should return
    where the app's resources folder is (like configs, data, saves,
    etc.). That's where Fabric Loader will create `config` and `mods`
    folder, and from which mods will be loaded from!
-   `isObfuscated()` whether your app is obfuscated.
-   `requiresUrlClassLoader()` it forces **Fabric Loader** to use a
    different compatibility class loader.
-   `isEnabled()` whether your app is enabled. Just `return true;`
-   `locateGame(FabricLauncher, String[])` that's where you need to
    parse the arguments with `net.fabricmc.loader.impl.util.Arguments`,
    locate the game directory and `return true;` if game directory was
    found.
-   `initialize(FabricLauncher)` here you can add extra configuration to
    **FabricLauncher**. **Don't initialize your game here**!
-   `getEntryPointTransformer()` return a
    `net.fabricmc.loader.impl.game.patch.GameTransformer` that does
    extra modification on the app's jar. If you're setting up Fabric
    Loader to work with a separate app which you currently aren't
    developing in your development environment, then simply return a
    subclass of **GameTransformer** that returns `null` in its
    `transform(String)` method.
-   `unlockClassPath(FabricLauncher)` is called after transformers (i.e.
    Mixin) were initialized and mods were detected and loaded (but not
    initialized).
-   `launch(ClassLoader)` this is where you launch your app.
    **Important**: the app must be launched through reflection using the
    given **ClassLoader**, about that later.
-   `getArguments()` simply return the `Arguments` instance you
    initialized in `locateGame(FabricLauncher, String[]))`.
-   `getLaunchArguments(boolean)` simply
    `return this.getArguments.toArray()`.

An example **GameProvider** would look like this:

``` java
public class AppGameProvider implements GameProvider
{
    private Arguments arguments;
    private Path gameDirectory;
    private GameTransformer transformer = new AppGameTransformer();

    @Override
    public String getGameId()
    {
        return "app";
    }

    @Override
    public String getGameName()
    {
        return "App";
    }

    @Override
    public String getRawGameVersion()
    {
        return App.FULL_VERSION;
    }

    @Override
    public String getNormalizedGameVersion()
    {
        return App.VERSION;
    }

    @Override
    public Collection<BuiltinMod> getBuiltinMods()
    {
        return Collections.emptyList();
    }

    @Override
    public String getEntrypoint()
    {
        return "net.developer.app.App";
    }

    @Override
    public Path getLaunchDirectory()
    {
        return this.gameDirectory;
    }

    @Override
    public boolean isObfuscated()
    {
        return false;
    }

    @Override
    public boolean requiresUrlClassLoader()
    {
        return false;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public boolean locateGame(FabricLauncher launcher, String[] args)
    {
        this.arguments = new Arguments();
        this.arguments.parse(args);

        this.gameDirectory = Paths.get(this.arguments.get("gameDirectory"));

        return Files.isDirectory(this.gameDirectory);
    }

    @Override
    public void initialize(FabricLauncher launcher)
    {}

    @Override
    public GameTransformer getEntrypointTransformer()
    {
        return this.transformer;
    }

    @Override
    public void unlockClassPath(FabricLauncher launcher)
    {
        for (Path path : this.jars)
        {
            launcher.addToClassPath(path);
        }
    }

    @Override
    public void launch(ClassLoader loader)
    {
        try
        {
            Class main = loader.loadClass(this.getEntrypoint());
            Method method = main.getMethod("main", String[].class);

            method.invoke(null, (Object) this.arguments.toArray());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Arguments getArguments()
    {
        return this.arguments;
    }

    @Override
    public String[] getLaunchArguments(boolean sanitize)
    {
        return this.arguments.toArray();
    }
}
```

This class example assumes that argument `--gameDirectory` is the one
that specifies where app's working directory is located. Adjust if
needed.

### Final touches

Once you get Fabric Loading up and running in your app, you would see
extra log in the console upon launching your app (if you did everything
correctly):

```txt:no-line-numbers
[14:20:00] [INFO] [FabricLoader/GameProvider]: Loading App 0.1 (dev) with Fabric Loader 0.13.0
[14:20:00] [INFO] [FabricLoader/]: Loading 2 mods:
  - fabricloader 0.13.0
  - java 8
[14:20:01] [WARN] [FabricLoader/ModRemap]: Runtime mod remapping disabled due to no fabric.remapClasspathFile being specified. You may need to update loom.
[14:20:01] [INFO] [FabricLoader/Mixin]: SpongePowered MIXIN Subsystem Version=0.8.5 Source=file:/C:/Users/Developer/.gradle/caches/modules-2/files-2.1/org.spongepowered/mixin/0.8.5/9d1c0c3a304ae6697ecd477218fa61b850bf57fc/mixin-0.8.5.jar Service=Knot/Fabric Env=CLIENT
[14:20:01] [INFO] [FabricLoader/Mappings]: Mappings not present!
```

That's a good sign. However, that's not all.

#### Initializing mods

If you would try making a mod for your app either as a separate project
or as a submodule in your project, there could be a couple of things
that may not work either in development or in release. First thing is
mods won't initialize. I.e. `ModInitializer.onInitialize()` won't be
called. To fix that, simply call
`net.fabricmc.loader.impl.game.minecraft.Hooks.startClient(File, Object)`
(where `File` is game directory, and `Object` could be the main object
of your game) somewhere after you initialized event busses, base
registries, etc. so that mods could start modding your app.

Something like this:

``` java
    public MainApp(App app)
    {
        super();

        this.development = app.development;

        this.events.register(this);
        
        this.registerCore(this, app.gameDirectory);
        this.registerFactories();
        this.registerFoundation();
        this.registerMiscellaneous();
        
        Hooks.startClient(app.gameDirectory, app);

        if (this.development)
        {
            this.watchDog = new WatchDog(this.assetsFolder);
            this.watchDog.register(this.textures);
            this.watchDog.register(this.models);
            this.watchDog.register(this.sounds);
            this.watchDog.start();
        }
    }
```

#### Mixins don't work?

And finally, even if you registered Mixins in your mod, they may load,
but they won't be able to actually patch their target class. If you have
following message in the log:

```txt:no-line-numbers
[12:20:05] [WARN] [FabricLoader/Knot]: * CLASS LOADER MISMATCH! THIS IS VERY BAD AND WILL PROBABLY CAUSE WEIRD ISSUES! *
  - Expected game class loader: net.fabricmc.loader.impl.launch.knot.KnotClassLoader@446a1e84
  - Actual game class loader: null
Could not find the expected class loader in game class loader parents!
```

Then it's the root of the issue. To fix this, you need to add your app's
jar, or folder (in development) to `FabricLauncher`'s class path. This
could be done by looking in the class path for your jar/folder, and
detecting some key files unique to your app. Here is what you can do:

``` java
public class AppGameProvider implements GameProvider
{
    /* ... */
    private List<Path> jars = new ArrayList<Path>();
    private Set<String> jarFiles;

    public AppGameProvider()
    {
        this.jarFiles = new HashSet<String>();

        this.jarFiles.add("net/developer/app/App.class");
        this.jarFiles.add("net/developer/app/MainApp.class");
    }
    
    /* ... */

    @Override
    public boolean locateGame(FabricLauncher launcher, String[] args)
    {
        this.arguments = new Arguments();
        this.arguments.parse(args);

        this.gameDirectory = Paths.get(this.arguments.get("gameDirectory"));

        for (Path path : launcher.getClassPath())
        {
            if (this.isHostApp(path))
            {
                this.jars.add(path);
            }
        }

        return Files.isDirectory(this.gameDirectory);
    }

    private boolean isHostApp(Path path)
    {
        if (Files.isDirectory(path))
        {
            for (String string : this.jarFiles)
            {
                if (Files.exists(path.resolve(string)))
                {
                    return true;
                }
            }

            return false;
        }

        try (ZipFile zip = new ZipFile(path.toFile()))
        {
            for (String string : this.jarFiles)
            {
                if (zip.getEntry(string) != null)
                {
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }
    
    /* ... */

    @Override
    public void unlockClassPath(FabricLauncher launcher)
    {
        for (Path path : this.jars)
        {
            launcher.addToClassPath(path);
        }
    }
    
    /* ... */
}
```

After detecting the app's jar(s)/folder(s) and adding them to
`FabricLauncher`'s class path, Mixins should work in both development
and release environment after that.

#### Loading mods from classpath

If you're testing the mod from the submodule that has your app and
Fabric Loader in the classpath, instead of compiling the mod and placing
it into app's launch directory, for Fabric Loader to load your mod from
classpath, you need to add `-Dfabric.development=true` to JVM flags when
running it, then mod(s) from classpath would be loaded too.

For more reference check out [Fabric
Loader's](https://github.com/FabricMC/fabric-loader) source,
`net.fabricmc.loader.impl.game.minecraft.MinecraftGameProvider`, and
this [GameProvider
example](https://github.com/PseudoDistant/ExampleGameProvider).