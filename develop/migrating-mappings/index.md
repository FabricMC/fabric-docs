---
title: Migrating Mappings
description: Learn how to migrate your mod's obfuscation mappings.
authors:
  - ArduFish
  - asiekierka
  - cassiancc
  - Daomephsta
  - deirn
  - Earthcomputer
  - florensie
  - Friendly-Banana
  - IMB11
  - jamierocks
  - JamiesWhiteShirt
  - liach
  - MildestToucan
  - modmuss50
  - natanfudge
  - Spinoscythe
  - UpcraftLP
authors-nogithub:
  - basil4088
---

You may wish to migrate from Yarn to Mojang Mappings, especially if you are planning on [updating your mod past the Mounts of Mayhem game drop](#whats-going-on-with-mappings), or vice versa.

There are two ways to accomplish this: you can either use the **Loom Gradle Plugin**, or the **Ravel IntelliJ IDEA Plugin**.

Loom offers a semi-automated migration of the mappings through the `migrateMappings` task, but **does not support migrating code written in Kotlin**.

Ravel is a plugin for IntelliJ IDEA that adds a GUI dialog for migration. Unlike Loom however, Ravel also **supports Kotlin**. In addition, Ravel might perform better than Loom for more complex projects since it uses IDE to resolve changes.

::: info

The Fabric API used Ravel to migrate from Yarn to Mojang Mappings. See [PR #4690 on the Fabric API repo](https://github.com/FabricMC/fabric/pull/4960).

:::

Neither option is perfect, and you still have to review the results and make manual fixes, especially if migrating Mixins.

<ChoiceComponent :choices="[
  {
    name: 'Loom Gradle Plugin',
    href: './loom',
    icon: 'simple-icons:gradle',
    color: '#4DC9C0',
  },
  {
    name: 'Ravel IntelliJ IDEA Plugin',
    href: './ravel',
    icon: 'simple-icons:intellijidea',
    color: '#FE2857',
  },
]" />

## What's Going On with Mappings? {#whats-going-on-with-mappings}

Historically, Minecraft: Java Edition has made use of obfuscation, which led to the development of obfuscation maps that Fabric Loom uses for modding. There were two choices: either Fabric's own Yarn mappings, or the official Mojang mappings.

Mojang have recently announced [they're removing code obfuscation from Minecraft: Java Edition](https://www.minecraft.net/en-us/article/removing-obfuscation-in-java-edition), and the Fabric Project followed up with [its plan for handling this change](https://fabricmc.net/2025/10/31/obfuscation.html). If you plan to update your mod to this version, you will need to move to Mojang's obfuscation mappings first before updating.

## What Are Mappings? {#mappings}

Minecraft: Java Edition has been obfuscated since its release, which means that its code had human-friendly class names like `Creeper` replaced with gibberish like `brc`. In order to easily mod it, Fabric Loom makes use of obfuscation maps: references which translate obfuscated class names, such as `brc`, back to human-friendly names like `CreeperEntity`.

As a Fabric developer, you'll encounter three main sets of names:

- **Intermediary**: The mapping set used by compiled Fabric mods; for example `brc` may become `class_1548`. The point behind Intermediary is offering a stable set of names across releases, as obfuscated class names change with each new version of Minecraft. This often allows mods built for one version to work on others, as long as the affected parts of the game haven't changed too much.
- **Yarn**: an open-source mapping set developed by Fabric for humans to write mods. Most Fabric mods used Yarn Mappings, as they were the default before 2025. An example mapping might be `CreeperEntity`.
- **Mojang Mappings**: The game's official obfuscation mappings, released by Mojang in 2019 to aid mod development. Notably, Mojang's obfuscation mappings lack parameter names and Javadocs, which is why some users also layer [Parchment](https://parchmentmc.org/) over the official mappings. An example mapping might be `Creeper`.

Minecraft 26.1 will be deobfuscated and include parameter names, so there won't be a need for any obfuscation mappings.
