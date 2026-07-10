---
title: mixins.json Config
description: Learn how to register mixin classes, and how to configure their behavior from the mixins.json config file.
authors: 
  - MildestToucan
resources:
  https://github.com/FabricMC/Mixin/blob/main/src/main/java/org/spongepowered/asm/mixin/transformer/MixinConfig.java: MixinConfig class source code - GitHub
  https://github.com/SpongePowered/Mixin/wiki: Official Mixin Wiki - GitHub
  http://github.com/llamalad7/mixinextras/wiki: Official MixinExtras Wiki - GitHub
  https://github.com/LlamaLad7/MixinExtras/wiki/Expressions-Setup: Expressions Setup - MixinExtras Wiki
---

Mixin config files, conventionally named `<modid>.mixins.json`, are JSON files used to register mixin classes and tweak some of Mixin's behavior.

Mixin config files are [registered in a mod's fabric.mod.json file](../loader/fabric-mod-json#mixins), one mod may register multiple Mixin configs if needed.

You can consult the [source code of the Mixin config specification](https://github.com/FabricMC/Mixin/blob/main/src/main/java/org/spongepowered/asm/mixin/transformer/MixinConfig.java).
You will also find the Mixin config files used by this website's Example Mod below.

::: details Main `mixins.json` config file of the Example Mod

<<< @/reference/latest/src/main/resources/example-mod.mixins.json

:::

::: details Client `mixins.json` config file of the Example Mod

<<<@/reference/latest/src/client/resources/example-mod.client.mixins.json

:::

## Mixin Class Registration {#mixin-class-registration}

A Mixin config file must have a `package` field indicating the package path under which all of its mixins will be.

Mixin classes are then registered under one of the following fields:

- **`mixins`** for mixin classes that should load regardless of if the game launches from a client or server.
- **`client`** for mixin classes that should only load when the game is launched on the client. Should be used to register
mixins targeting client-only classes.
- **`server`** for mixin classes that should only load when the game is launched from a dedicated server. Note that mixins here will not apply in
singleplayer.

Mixin classes are specified by their path relative to the config's `package`, meaning their simple name prefixed by any subpackage paths in the mixin package.

For example, `"accessor.InventoryAccessor"` would mean the class `InventoryAccessor`, in the package `accessor` under the config mixin package.

## Config Options {#config-options}

The following fields are used to tweak the behavior of this config in relation to its mixins.

## Injector Options {#injector-options}

The following fields are options under the `injectors` field used to change the behavior of injectors for this config's mixins.

## Overwrite Options {#overwrite-options}

The following fields are options under the `overwrites` field used to change the behavior of overwriting for this config's mixins.
