---
title: Class Tweakers
description: An introduction to Class Tweakers and their setup process.
authors:
  - Earthcomputer
  - its-miroma
  - MildestToucan
---

Class tweakers, known as access wideners prior to gaining further functionality, are used to complement Mixin's bytecode manipulation by providing additional transformation tools and allowing for certain runtime modifications to be visible within the development environment.

Before moving on to the specific features Class Tweakers provide, we will go over the setup process.

## Setup Process {#setup-process}

Class tweakers may only target Minecraft classes, and are only available on Fabric Loader 0.18 and above.

### File Format {#file-format}

Class tweaker files are conventionally named after your modid, appended with the `.classtweaker` extension to help IDE plugins recognize them. They should be stored in `resources`.

The file must have the following header format:

```classtweaker
classTweaker v1 <namespace>
```

The namespace should almost never be set to anything other than `named`.

Class tweaker files can have blank lines and comments starting with `#`

```classtweaker
# Comments like this are supported, as well as at the end of the line.
```

Any whitespace can be used to separate in a class tweaker file, but tabs are recommended.

Classes are specified using their [internal names](../mixins/bytecode#class-names).

### Specifying File Location {#specifying-file-location}

The class tweaker location must be specified in your `build.gradle` and in your `fabric.mod.json` file.

`build.gradle`:

```groovy
loom {
    accessWidenerPath = file("src/main/resources/modid.classtweaker")
}
```

`fabric.mod.json`:

```json
...

"accessWidener": "modid.classtweaker",

...
```
