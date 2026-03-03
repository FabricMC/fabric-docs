---
title: Class Tweakers
description: Learn what class tweakers are, and how to set them up.
authors:
  - Earthcomputer
  - its-miroma
  - MildestToucan
---

Class tweakers, known as access wideners prior to gaining further functionality, are used to complement Mixin's bytecode manipulation by providing additional transformation tools
and allowing for certain runtime modifications to be visible from the development environment.

Class tweakers are not specific to a given Minecraft version, but are only available starting from Fabric Loader 0.18.0, and may only target Vanilla Minecraft classes.

Before moving on to the tools class tweakers provide, we will go over how to set them up.

## Setup {#setup}

### File Format {#file-format}

Class tweaker files are conventionally named after your modid, appended with `.classtweaker` to help IDE plugins recognize them. They should be stored in `resources`.

The file must have the following header as its first line:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::classtweaker-setup:file-header:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

Class tweaker files can have blank lines and comments starting with `#`. Comments can start at the end of a line.

Class tweaker entry syntax depends on the feature utilized, but you may only have one entry per line.

### Specifying The File Location {#specifying-the-file-location}

The class tweaker file's location must be specified in your `build.gradle` and `fabric.mod.json` files.

The files are still named after access wideners for compatibility reasons.

#### build.gradle {#build-gradle}

@[code lang=groovy:no-line-numbers transcludeWith=:::classtweaker-setup:gradle:::](@/reference/latest/build.gradle)

#### fabric.mod.json {#fabric-mod-json}

Make sure that you depend on Fabric Loader 0.18.0 or above to use class tweakers:

```json:no-line-numbers
...

"accessWidener": "example-mod.classtweaker",

...
```

After specifying the file location in your `build.gradle` file, make sure to reload your gradle project in the IDE.

## Validating the File {#validating-the-file}

By default, class tweaker will ignore entries referencing modification targets that cannot be found. To check if all the classes, fields and methods specified in the
file are valid, run the `validateAccessWidener` Gradle task. Entries containing invalid targets will raise errors.
