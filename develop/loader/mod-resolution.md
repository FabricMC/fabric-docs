---
title: Mod Resolution
description: An explanation of how Fabric Loader chooses which mods to load.
authors:
  - Deximus-Maximus
resources:
---

Fabric Loader will attempt to load the largest set of mods that can be loaded without conflicts,
choosing the most compatible version of each mod. Only one version of a mod can be loaded at a time.

## Mod Discovery {#mod-discovery}

To choose which mods to load, it must first find all the mods, such as all the mods in the `mods`
folder. See [Mod Sources](#mod-sources) for other sources. As mods can contain other mods
(see [Loom's `include` configuration](../loom/options)), Loader will search within each mod for more
mods. Note that bundled mods can also bundle mods of their own. There are three types of mods:

### Plain Mods {#plain-mods-disc}

These are mods that you would find directly in the `mods` folder or another mod source.

### Nested Mods {#nested-mods-disc}

These are mods that are found within other mods.

### Builtin Mods {#builtin-mods-disc}

These are mods that Loader's game provider provides, for instance, `minecraft` is provided by
Loader as a builtin mod. See [Built-in Mods](#built-in-sources) for more information.

## Mod Resolution {#mod-resolution}

Once Loader has found all the mods, it will begin resolution. This is where Loader will attempt to
find the most compatible version of each mod, and each mod group is handled slightly differently.

### Plain Mods {#plain-mods-res}

Plain mods are generally preferred over nested mods, and some version of this mod shall be loaded.

### Nested Mods {#nested-mods-res}

Nested mods are not required to be loaded, unless they are required by another mod. This is useful
for mods that provide additional features to other mods that may not be present, or allowing the
parent mod to provide different implementations depending on the environment or other loaded mods.

This differs from other methods of conditional loading, such as manually checking if a mod is loaded
or using features such as resource conditions for optionally loading resources or mixin config
plugins for optionally applying mixins. Accidental references to unavailable classes can more
easily be avoided when using nested mods to implement compatibility features. Because a nested mod
has its own dependencies, Fabric Loader can handle more complex version requirements, and influence
mod resolution. See the [optional nested influence example](#optional-nested-influence) for an
example.

### Builtin Mods {#builtin-mods-res}

These mods are always loaded.

### Examples {#examples}

#### Incompatible Nested Mod Skipped {#incompatible-nested-mod-skipped}

Here, only `a` and `b` are loaded.

| Mod | Parent | Nested Mods | Depends On         | Breaks With |
| --- | ------ | ----------- | ------------------ | ----------- |
| a   |        | aa          |                    |             |
| aa  | a      |             | any version of `c` |             |
| b   |        |             |                    |             |

#### Incompatible Nested Mod Failure {#incompatible-nested-mod-failure}

Mod resolution fails if there are incompatible required nested mods, prompting the user with a
solution.

| Mod | Parent | Nested Mods | Depends On          | Breaks With |
| --- | ------ | ----------- | ------------------- | ----------- |
| a   |        | aa          | any version of `aa` |             |
| aa  | a      |             | any version of `c`  |             |
| b   |        |             |                     |             |

#### Multiple Versions of the Same Mod Nested {#multiple-versions-of-the-same-mod-nested}

The most compatible version of the mod is loaded when there are multiple versions of the same mod.
`a`, `aa` version 2.0.0, and `b` are all loaded.

| Mod | Version | Parent | Nested Mods           | Depends On          | Breaks With        |
| --- | ------- | ------ | --------------------- | ------------------- | ------------------ |
| a   | 1.0.0   |        | aa 1.0.0 and aa 2.0.0 | any version of `aa` |                    |
| aa  | 1.0.0   | a      |                       | any version of `c`  |                    |
| aa  | 2.0.0   | a      |                       |                     | any version of `c` |
| b   | 2.0.0   |        |                       | any version of `a`  |                    |

#### Multiple Copies of the Same Plain Mod {#multiple-copies-of-the-same-mod-plain}

Mod resolution fails if there are multiple copies of the same plain mod.

| Mod | Version | Parent | Nested Mods | Depends On         | Breaks With |
| --- | ------- | ------ | ----------- | ------------------ | ----------- |
| a   | 1.0.0   |        |             |                    |             |
| a   | 1.0.0   |        |             |                    |             |
| b   | 2.0.0   |        |             | any version of `a` |             |

#### Multiple Versions of the Same Plain Mod {#multiple-versions-of-the-same-mod-plain}

The latest most compatible version of the mod is loaded when there are multiple versions of the same mod,
in this case `a` version 2.0.0 is loaded with `b`.

| Mod | Version | Parent | Nested Mods | Depends On         | Breaks With |
| --- | ------- | ------ | ----------- | ------------------ | ----------- |
| a   | 1.0.0   |        |             |                    |             |
| a   | 2.0.0   |        |             |                    |             |
| b   | 2.0.0   |        |             | any version of `a` |             |

#### Picking the Most Compatible Version of a Mod {#picking-the-most-compatible-version-of-a-mod}

`a` version 1.0.0 is loaded with `b`.

| Mod | Version | Parent | Nested Mods | Depends On | Breaks With |
| --- | ------- | ------ | ----------- | ---------- | ----------- |
| a   | 1.0.0   |        |             |            |             |
| a   | 2.0.0   |        |             |            |             |
| b   | 2.0.0   |        |             |            | a >= 2.0.0  |

#### Preferring Plain Mods Over Nested Mods {#preferring-plain-mods-over-nested-mods}

`aa` version 1.0.1 is loaded instead of `aa` version 2.0.0. This only occurs when the plain mod
is compatible with the rest of the mod set, otherwise the nested version is loaded.

| Mod | Version | Parent | Nested Mods | Depends On | Breaks With |
| --- | ------- | ------ | ----------- | ---------- | ----------- |
| a   | 1.0.0   |        | aa 2.0.0    |            |             |
| aa  | 2.0.0   | a      |             |            |             |
| aa  | 1.0.1   |        |             |            |             |

#### Optional Nested Mods Influencing Version Selection {#optional-nested-influence}

When `aa` depends on `dd` `1.x`, `dd` version 1.0.0 is loaded instead of `dd` version 2.0.0. Without
that dependency on `1.x`, `dd` version 2.0.0 would be loaded.

| Mod | Version | Parent | Nested Mods        | Depends On | Breaks With |
| --- | ------- | ------ | ------------------ | ---------- | ----------- |
| a   | 1.0.0   |        | aa 2.0.0           |            |             |
| aa  | 2.0.0   | a      |                    | dd 1.x     |             |
| b   | 2.0.0   |        |                    |            |             |
| c   | 1.0.0   |        | cc 1.0.0           |            |             |
| cc  | 1.0.0   | c      |                    |            |             |
| d   | 1.0.0   |        | dd 1.0.0 and 2.0.0 |            |             |
| dd  | 1.0.0   | d      |                    |            |             |
| dd  | 2.0.0   | d      |                    |            |             |

## Mod Sources {#mod-sources}

### Built-in Mods {#built-in-sources}

For minecraft, Loader provides several builtin mods:

- `minecraft`
- `java`
  - The java version being used to run the game, such as `8` or `25`.

Fabric Loader will also list itself in the mod list as `fabricloader`, but it is technically not a
builtin mod.

### Mods Folder {#mods-folder}

By default, Loader will look for mods in the `mods` folder located in the `.minecraft` folder.
This search is not recursive, so it will not look in subfolders.

This folder can be changed by setting the `fabric.modFolder` system property.

### Additional Mod Sources {#additional-sources}

Mods can be loaded from other locations by adding them to the `fabric.addMods` system property,
which takes a list of file paths. Paths are separated by the standard system path separator -
`:` on Unix and `;` on Windows.

If the file path is prefixed with `@`, and is a text file, Loader will read this file treating each
line as if it were added to the `fabric.addMods` system property.

If the file path is not a directory, such as a `.jar` file, then Loader will attempt to load the file
as a mod.

If the file path is a directory, Loader will search that directory for mods just like the `mods`
folder.
