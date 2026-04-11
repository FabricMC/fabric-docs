---
title: Class Tweakers
description: Learn what class tweakers are, and how to set them up.
authors:
  - cassiancc
  - Earthcomputer
  - its-miroma
  - MildestToucan
---

Class tweakers, formerly known as access wideners before gaining further functionality, provide transformation tools complementary to Mixin bytecode manipulation. They also allow some runtime modifications to be accessible within the development environment.

::: warning

Class tweakers are not specific to a given Minecraft version, but are only available starting from Fabric Loader 0.18.0 and Loom 1.12, and may only target Vanilla Minecraft classes.

:::

## Setup {#setup}

### File Format {#file-format}

Class tweaker files are conventionally named after your modid, `example-mod.classtweaker`, to help IDE plugins recognize them. They should be stored in `resources`.

The file must have the following header as its first line:

```classtweaker
classTweaker v1 official
```

Class tweaker files can have blank lines and comments starting with `#`. Comments can start at the end of a line.

Syntax can vary based on the feature used, but modifications are each declared as "entries" on separate lines and start with a "directive" specifying the type of modification to apply.
An entry's elements can be separated using any whitespace, including tabs.

#### Transitive Entries {#transitive-entries}

In order to make your changes to the decompiled source visible to mods that depend on yours, prefix the directive with `transitive-`:

```classtweaker:no-line-numbers
# Transitive Access Widening directives
transitive-accessible
transitive-extendable
transitive-mutable

# Transitive Interface Injection directive
transitive-inject-interface
```

### Specifying The File Location {#specifying-the-file-location}

The class tweaker file's location must be specified in your `build.gradle` and `fabric.mod.json` files. Remember that you must also depend on Fabric Loader 0.18.0 or above to use class tweakers.

The specifications are still named after access wideners to preserve backwards compatibility.

#### build.gradle {#build-gradle}

<<< @/reference/latest/build.gradle#classtweaker-setup--gradle

#### fabric.mod.json {#fabric-mod-json}

```json:no-line-numbers
...

"accessWidener": "example-mod.classtweaker",

...
```

After specifying the file location in your `build.gradle` file, make sure to reload your Gradle project in the IDE.

## Validating the File {#validating-the-file}

By default, class tweaker will ignore entries referencing modification targets that cannot be found. To check if all the classes, fields and methods specified in the file are valid, run the `validateAccessWidener` Gradle task.

Errors will point out any invalid entry, but they can be about which part of an entry is invalid.
