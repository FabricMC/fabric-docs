---
title: Enum Extension
description: Learn how to add entries to enums with Mixin and Class Tweakers.
authors:
  - MildestToucan
---

Enum extension is a Mixin feature that can reliably add new entries to an enum.

When targeting Minecraft enums, the mixin can be combined with [class tweaking](../class-tweakers) to add the new enum entries
to the decompiled source. The class tweaker entries can be [transitive](../class-tweakers/index#transitive-entries) so that mods depending on
yours will also see your added entries.

::: warning

Enum extension requires Loader 0.19.0 for Mixin support and Loom 1.16 for class tweaker support.

Additionally, class tweaker file headers must specify a version of `v2`.

:::
