---
title: Classpath Groups
description: Documentation for the Fabric Loom classpath groups feature.
authors:
  - modmuss50
---

Loom provides a DSL for configuring Fabric loader's classpath groups system property. This allows Fabric Loader to group different classpath entries together, which is useful for mods that split their code into multiple source sets, such as client and common code or common and platform-specific code. This can be important to ensure that your mods resources are correctly loaded. Whenever you have a single mod that is built from multiple source sets, you should define all of the source sets in the `loom.mods` block to ensure that Fabric Loader can group them correctly. This feature only applies when running the game in your development environment, and does not affect the final mod jar produced by your build (as everything is packaged into a single jar).

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            sourceSet sourceSets.client
        }
    }
}
```

In the example above, `example-mod` is built from two source sets: `main` and `client`. Loom will configure Fabric Loader to group these source sets together under the same classpath group, ensuring that they are loaded correctly at runtime.

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            sourceSet sourceSets.client
        }
        "example-mod-test" {
            sourceSet sourceSets.testmod
        }
    }
}
```

In the example above, `example-mod-test` is built from a single source set: `testmod`. Loom will configure Fabric Loader to group this source set under its own classpath group, separate from the `example-mod`.

## Sub Projects {#multi-project}

When you wish to define mods that span multiple Gradle projects (common in multi-platform setups), you can do so by specifying the source set name and the project path.

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            sourceSet("main", ":core")
        }
    }
}
```

If the project you are depending on does not use Loom you must apply the `net.fabricmc.fabric-loom-companion` plugin to that project. This allows the loom project to access the required data in a manner that follows Gradle's best practices. This plugin only exposes the necessary information for classpath groups and does not apply any of the other Loom functionality.

```groovy
plugins {
    id 'net.fabricmc.fabric-loom-companion'
}
```

## Shaded Dependencies {#shaded-dependencies}

If you are shading dependencies into your mod jar, you should also define the configuration that contains the shaded dependencies in the `loom.mods` block. This ensures that Fabric Loader can group the shaded dependencies correctly with your mod's code. You should not do this for other mod dependencies or dependedencies that you are jar-in-jar-ing with `include`.

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            configuration configurations.shade
        }
    }
}
```
