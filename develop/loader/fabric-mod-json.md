---
title: fabric.mod.json
description: A guide to the `fabric.mod.json` specification.
authors:
  - cassiancc
  - falseresync
  - jamieswhiteshirt
  - IMB11
  - SolidBlock-cn
authors-nogithub:
  - skyland1a
resources:
  https://github.com/FabricMC/fabric-loom/blob/dev/1.15/src/main/java/net/fabricmc/loom/api/fmj/FabricModJsonV1Spec.java: Source Code for fabric.mod.json v1 Spec
  https://github.com/FabricMC/fabric-language-kotlin/blob/master/README.md: Fabric Language Kotlin's Language Provider
  https://spdx.org/licenses/: SPDX License Identifiers
  https://semver.org/: Semantic Versioning
  https://jubianchi.github.io/semver-check/: Semantic Version Comparison Tool
---

The `fabric.mod.json` file is a metadata file used by Fabric Loader to load mods. In order to be loaded, a mod must have this file with the exact name placed in the root directory of the mod JAR.

A `fabric.mod.json` comes included in the `src/main/resources` folder of the template mod, and can be [generated via Fabric Loom](../loom/tasks#fabric-mod-json).

## Mandatory Fields {#mandatory-fields}

- **`schemaVersion`** Must be the first entry and must always be `1`. Required for Fabric Loader to parse the file correctly.
- **`id`** A string value that defines the mod's identifier - allowed characters include Latin letters, digits, underscores, and hyphens, with length from 2 to 64.
- **`version`** A string value that defines the mod's version, expected to match the [Semantic Versioning 2.0.0](https://semver.org/) specification.

```json
"schemaVersion": 1,
"id": "example-mod",
"version": "1.0.0"
```

## Optional Fields {#optional-fields}

### Mod Loading {#mod-loading}

#### Environment {#environment}

- **`environment`**: A string value that defines which environments the mod should be run on:
  - **`*`**: Runs in all environments. Default.
  - **`client`**: Runs on the physical client side. If set, your mod will not be loaded on dedicated servers.
  - **`server`**: Runs on the physical server side. If set, your mod will not be loaded on clients or in singleplayer.

```json
"environment": "*"
```

#### Entrypoints {#entrypoints}

- **`entrypoints`** An object that defines the main classes of your mod. that will be loaded.
  - **`main`** An array of string class names that implement `ModInitializer`.
  - **`client`** An array of string class names that implement `ClientModInitializer`. This entrypoint is run after `main` and only on the physical client side.
  - **`server`** An array of string class names that implement `DedicatedServerModInitializer`. This entrypoint is run after `main` and only on the physical server side.

Fabric provides three main entrypoints, but other mods may provide their own (i.e. `fabric-datagen` provided by Fabric API). Each entry point can contain any number of classes to load.

Classes (or methods or static fields) could be defined in two ways. If you're using Java, then just list the classes (or else) full names. For example:

```json
"main": [
    "net.fabricmc.example.ExampleMod",
    "net.fabricmc.example.ExampleMod::handle"
]
```

If you're using any other language, consult the language adapter's documentation. The Kotlin one can be found on [Fabric Language Kotlin's README](https://github.com/FabricMC/fabric-language-kotlin/blob/master/README.md).

#### Jars {#jars}

- **`jars`**: An array of nested JARs inside your mod's JAR to load. When using Loom, using `include` on your dependencies will automatically populate this. Each entry is an object containing `file` key. That should be a path inside your mod's JAR to the nested JAR. For example:

```json
"jars": [
   {
      "file": "nested/vendor/dependency.jar"
   }
]
```

#### Language Adapaters {#language-adapters}

- **`languageAdapters`**: A dictionary of classes that implement `LanguageAdapter`.

```json
"languageAdapters": {
   "kotlin": "net.fabricmc.language.kotlin.KotlinAdapter"
}
```

#### Mixins {#mixins}

- **`mixins`**: A list of mixin configuration files. Each entry is the path to the mixin configuration file inside your mod's JAR or an object containing following fields:
  - **`config`**: The path to the mixin configuration file inside your mod's JAR.
  - **`environment`**: The same as upper level **environment** field. See above. For example:

```json
"mixins": [
   "modid.mixins.json",
   {
      "config": "modid.client-mixins.json",
      "environment": "client"
   }
]
```

#### Access Wideners {#accesswideners}

- **`accessWidener`**: A string identifying an [access widener or class tweaker](../class-tweakers/index) file.

```json
"accessWidener": "example-mod.classtweaker"
```

#### Provides {#provides}

- **`provides`**： An array of mod ids that can be used as the aliases for the mod. Fabric Loader will treat these ids as mods that exist. If there are other mods using that id, they will not be loaded.

```json
"provides": [
   "example_mod"
]
```

### Dependency Resolution {#dependency-resolution}

The following keys will accept a dictionary of dependencies. For more details on how to structure the dictionary, see below:

- **`depends`**: For dependencies required to run. **If any are missing, Fabric Loader will trigger a crash**.
- **`recommends`**: For dependencies not required to run. For each missing dependency, Fabric Loader will log a warning.
- **`suggests`**: For dependencies not required to run. Use this as a kind of metadata.
- **`breaks`**: For mods whose together with yours might cause a game crash. **If any are present, Fabric Loader will trigger a crash**.
- **`conflicts`**: For mods whose together with yours cause some kind of bugs, etc. For each conflicting mod present, Fabric Loader will log a warning.

#### Semantic Versioning {#semantic-versioning}

The key of each entry is the Mod ID of the dependency.

The value of each key is a string or array of strings declaring supported version ranges of the dependency. In the case of an array, an "OR" relationship is assumed - that is, only one range has to match for the collective range to be satisfied.

1. To depend on any version of the dependency, just write `*`.
2. To depend on only a specific version of the dependency, just write its version number, e.g. `26.1`.
3. To depend on all versions of the dependency above a version, write `>26`.
4. For a more complicated dependency, like a dependency on Minecraft 26.1 and its hotfix, 26.1.1, we could use any of the following examples:

::: details Examples for a dependency on 26.1 and 26.1.1

- `[26.1, 26.1.1]` - Will only load on 26.1 and 26.1.1, none of their snapshots, pre-releases, release candidates, or the April Fools version 26w14a (parsed by Fabric as `26.1.1-alpha.26.14.a`).
- `>26 <26.2` - [Will load on all versions higher than 26 and lower than 26.2](https://jubianchi.github.io/semver-check/#/%3E26%20%3C26.2/26.1.0), including all snapshots, pre-releases, release candidates, but also the nonexistent 26.0 versions.
- `>=26.1 <26.2` - [Will load on all versions greater than or equal to than 26.1 and lower than 26.2](https://jubianchi.github.io/semver-check/#/%3E26%20%3C26.2/26.1.0), including 26.1 and any hotfixes for it, as well as snapshots, pre-releases, release candidates for these hotfixes..
- `26.1.x` - [Will load on any 26.1.x version](https://jubianchi.github.io/semver-check/#/26.1.x/26.1), including snapshots, pre-releases, and release candidates for 26.1 and 26.1.1.
- `~26.1` - [Will load on any 26.1.x version](https://jubianchi.github.io/semver-check/#/~26.1/26.1), including snapshots, pre-releases, and release candidates for 26.1 and 26.1.1.
- `^26.1` - [Will load on any 26.x.x version](https://jubianchi.github.io/semver-check/#/^26.1/26.1), including snapshots, pre-releases, and release candidates for 26.1, 26.2, and above, but not including 27.x.

:::

```json
"depends": {
    "example-mod": "*",
    "minecraft": [
        "26.1",
        "26.1.1"
    ]
}
"suggests": {
    "another-mod": ">1.0.0"
}
```

### Metadata {#metadata}

- **`name`**: A string that defines the user-friendly mod's name. If not present, assume it matches **id**.
- **`description`**: A string that defines the mod's description. If not present, assume empty string.

```json
"name": "Example mod",
"description": "This is an example description! Tell everyone what your mod is about!",
```

#### Contact {#contact}

- **`contact`**: A dictionary that defines the contact information for the project.
  - **`email`**: A string that defines the contact e-mail pertaining to the mod. Must be a valid e-mail address.
  - **`homepage`**: A string that defines the project or user's homepage. Must be a valid HTTP/HTTPS address.
  - **`irc`**: A string that defines the IRC channel pertaining to the mod. Must be of a valid URL format - for example: `irc://irc.esper.net:6667/charset` for `#charset` at EsperNet - the port is optional, and assumed to be 6667 if not present.
  - **`issues`**: A string that defines the project's issue tracker. Must be a valid HTTP/HTTPS address.
  - **`sources`**: A string that defines the project's source code repository. Must be a valid URL - it can, however, be a specialized URL for a given VCS (such as Git or Mercurial).

The list is not exhaustive - mods may provide additional, non-standard keys (such as **`discord`**, **`slack`**, **`twitter`**, etc) - if possible, they should be valid URLs.

```json
"contact": {
    "homepage": "https://fabricmc.net",
    "sources": "https://github.com/FabricMC/fabric-example-mod"
}
```

#### Authors and Contributors {#authors-contributors}

- **`authors`** An array of authors of the mod. Entries can be either a string or an object with the fields listed below.
- **`contributors`** An array of contributors to the mod. Entries can be either a string or an object with the fields listed below.

Fields:

- **`name`** A mandatory string for the person's real name, or username.
- **`contact`** An optional object for the person's contact information. The same as upper level [**`contact`**](#contact).

```json
"authors": [
    "Me!",
    {
        "name": "Tiny Potato",
        "contact": {
          "homepage": "https://fabricmc.net",
          "sources": "https://github.com/FabricMC/fabric-example-mod"
        }
    }
]
```

#### License {#license}

- **`license`** A string or array that defines the licensing information.

This should provide the complete set of preferred licenses conveying the entire mod package. In other words, compliance with all listed licenses should be sufficient for usage, redistribution, etc. of the mod package as a whole.

For cases where a part of code is dual-licensed, choose the preferred license. The list is not exhaustive, serves primarily as a kind of hint, and does not prevent you from granting additional rights/licenses on a case-by-case basis.

To aid automated tools, it is recommended to use [SPDX License Identifiers](https://spdx.org/licenses/) for open-source licenses.

```json
"license": "CC0-1.0"
```

#### Icon {#icon}

- **`icon`** A string or dictionary that defines the mod's icon. Icons are square PNG files. (Minecraft resource packs use 128×128, but that is not a hard requirement - a power of two is, however, recommended.) Can be provided in one of two forms:
  - A path to a single PNG file.
  - A dictionary of images widths to their files' paths.

```json
"icon": "assets/modid/icon.png"
```

### Custom Fields {#custom-fields}

You can add any field you want to add inside `custom` field. Loader would ignore them. However, it's highly recommended to namespace your fields to avoid conflicts with other mods.
