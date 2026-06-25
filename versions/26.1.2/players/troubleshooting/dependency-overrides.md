---
title: Dependency Overrides
description: A guide to overriding dependencies set in a mod's `fabric.mod.json`.
authors:
  - cassiancc
  - skycatminepokie
  - ytg1234
authors-nogithub:
  - kb1000
resources:
  https://semver.org/: Semantic Versioning
---

<!---->

::: warning

Dependency overrides are used to give modpack developers control over their mods. This should not be used by regular players.

It is recommended to understand how [mod dependency fields are structured](../../develop/loader/fabric-mod-json#semantic-versioning) before continuing.

:::

Sometimes, when assembling a modpack, you might encounter mods with unhelpful dependency requirements - for example, a mod might be too stringent and require Minecraft `26.1`, despite it also working on `26.1.2`.

To counter this, Fabric Loader lets you override dependency requirements, so you can attempt to load a mod on a version of Minecraft it is not designed for.

::: tip

Overriding dependencies should only be a temporary solution if possible. If the mod is actively maintained, consider reporting this incompatibility on the issue tracker, and letting the developers upstream take care of the issue.

:::

## Setup {#setup}

:::: info

For the purposes of this example, we'll be using the following `fabric.mod.json` for a mod with the id `example-mod`. At any point, you can switch tabs in a code block to see how the dependency override affects this `fabric.mod.json`.

::: details `fabric.mod.json`

```json
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

::::

First, create a file named `fabric_loader_dependencies.json` inside the `.minecraft/config` folder.

Next, we fill in the file with the following boilerplate content:

::: code-group

```json [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {} // [!code highlight]
  }
}
```

```json [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

Let's go over it line-by-line.

First, we have `version`, which specifies the dependency override spec version we would like to use. At the time of writing this page, the latest version is version `1`.

Secondly, we have an `overrides` object that will contain all of our dependency overrides to various mods. To start, it includes an empty entry for `example-mod` that we can add dependency overrides to.

Keys inside the mod object can be one of the 5 dependency types (`depends`, `recommends`, `suggests`, `conflicts`, `breaks`). The value of any one of those keys must be a JSON object. This JSON object follows the exact same structure as a [`fabric.mod.json` dependency object](./fabric-mod-json#semantic-versioning).

The key may be optionally prefixed with `+` or `-` (e.g. `"+depends"`, `"-breaks"`).

::: tabs

== Prefixed with +

If the key is prefixed with `+`, the entries inside that JSON object will be added (or overridden if already exist) to the mod.

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "+depends": {
        "minecraft": ""
      }
    }
  }
}
```

== Prefixed with -

If the key is prefixed with `-`, the value of each entry is ignored completely and Fabric Loader will remove those entries from the resulting dependency map.

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": ""
      }
    }
  }
}
```

== Without a Prefix

If the key isn't prefixed, the dependency object will be replaced completely. **Be careful to prefix your keys!**

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "depends": {
        "minecraft": ""
      }
    }
  }
}
```

:::

## Overriding Dependencies {#overriding-dependencies}

Let's assume that a mod with ID `example-mod` depends on Minecraft version `26.1` **exactly**, but we want it to work on other 26.1 versions. Let's see how we can do that:

::: code-group

```json{5-6} [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "depends": {
        "minecraft": "26.1.x"
      }
    }
  }
}
```

```json{2,5-6} [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1.x"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

A `"minecraft"` dependency will now be overridden if specified (and we know it is). There is another way to do this:

::: code-group

```json{5-6} [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": "IGNORED"
      }
    }
  }
}
```

```json{2,5-6} [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1.x"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

As specified above, the value of key `"minecraft"` will be ignored when removing dependencies. If a dependency with a mod ID requirement of `minecraft` is found, it will be removed from our target mod `example-mod`.

We can also override the entire `depends` block, but with great power comes great responsibility. Be careful.

Aside from changing the `minecraft` dependency, we also want to remove all `suggests` dependencies. We can do that by removing the prefix on the `suggests` key, which replaces it with an empty object, essentially clearing it. This would look like this:

::: code-group

```json [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": ""
      },
      "suggests": {} // [!code highlight]
    }
  }
}
```

```json [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {} // [!code highlight]
}
```

:::

<!---->
