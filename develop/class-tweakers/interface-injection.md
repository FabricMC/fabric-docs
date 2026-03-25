---
title: Interface Injection
description: Learn how to implement interfaces on Minecraft classes' in the decompiled source.
authors-nogithub:
  - salvopelux
authors:
  - Daomephsta
  - Earthcomputer
  - Juuxel
  - MildestToucan
  - Sapryx
  - SolidBlock-cn
---

Interface injection is a type of [class tweaking](../class-tweakers/) used to add interface implementations on Minecraft classes directly
in the decompiled source.

It is already possible to use mixins to make a target class implement a new interface at runtime, but class tweakers can make it visible in the
decompiled source. This means that the added methods do not require casting to the interface to be used in mod code as would be needed with just mixins.
