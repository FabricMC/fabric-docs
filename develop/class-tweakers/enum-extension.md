---
title: Enum Extension
description: Learn how to add entries to enums with Mixin and Class Tweakers.
authors:
  - cassiancc
  - its-miroma
  - MildestToucan
---

Enum extension is a Mixin feature that can reliably add new entries to an enum.

When targeting Minecraft enums, you can use mixins together with [class tweaking](../class-tweakers) to display new enum entries
in the decompiled source. If that is set to be [transitive](../class-tweakers/index#transitive-entries), mods that depend on yours will also see your added entries.

::: warning

Enum extension requires at least Loader 0.19.0 for Mixin support and at least Loom 1.16 for class tweaker support.

Additionally, class tweaker file headers must specify `v2` as the version to use enum extensions.

:::

## Creating the Mixin {#creating-the-mixin}

Before creating the mixin class, make sure that Loader 0.19.0 or above is an explicit dependency
in your `fabric.mod.json` file:

```json:no-line-numbers
...
"depends": {
  ...
  "fabricloader": ">=0.19.0"
  ...
}
...
```

Even if you are using the correct Loader version as a Gradle dependency, you must explicitly depend on at least version 0.19.0 in
order to opt into this Mixin feature.

To make an enum extension, create an `enum` in your mixin package, annotate it with `@Mixin`, and add your constants to it as if they were
part of the targeted enum class. For example, let's add a new entry to `RecipeBookType`:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeBookTypeMixin.java#enum-extension-no-impls-example-mixin

::: warning IMPORTANT

You should always prefix the enum constants you add with your mod ID to ensure uniqueness. For these Docs, we'll use `EXAMPLE_MOD_`.

:::

### Additional Fields and Methods {#additional-fields-and-methods}

If the targeted enum declares additional fields or method implementations, you'll have to shadow the relevant constructor.

For example, let's add a new `RecipeCategory` entry. Create a constructor matching the target, and annotate it with `@Shadow`.

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeCategoryMixin.java#enum-extension-ctor-impls-example-mixin

## Making the Class Tweaker Entry {#making-the-class-tweaker-entry}

If you are targeting a Minecraft enum, you can use a class tweaker entry to visibly modify the target enum in the decompiled source.

To opt into this feature, remember to use Loom 1.16 or above, and to set the [file header version](../class-tweakers/index#file-format) to `v2`.

The syntax for an enum extension entry is:

```classtweaker:no-line-numbers
extend-enum  <targetClassName>  <ENUM_CONSTANT_NAME>
```

For class tweaking, classes use their [internal names](../mixins/bytecode#class-names).

For example, the class tweaker entries for the constants we added in the [mixin section](#creating-the-mixin) would respectively be:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#enum-extension-no-impls-example-entry

and

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#enum-extension-ctor-impls-example-entry

## Applying Changes {#applying-changes}

You'll have to refresh your Gradle project by [regenerating sources](../getting-started/generating-sources) before you can see your added enum entries in the decompiled source.
If modifications do not appear, you can try [validating](../class-tweakers/index#validating-the-file) the file and checking if any errors appear.

::: info

You will not see [fields, methods or other elements](#additional-fields-and-methods) in the decompiled source code. That's because those are handled by the mixin, and are only applied at runtime.

:::

You can now use the enum constant in your code:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-usage-example

If you are only adding it with a mixin and it is not in the decompiled source, get it by calling `Enum#valueOf`:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-no-ct-usage-example

## Pitfalls {#pitfalls}

Enum extension by itself makes no guarantees that your added entry will not break anything. You should find the modified enum's usages to try and
prevent issues where possible. If an issue that would cause a crash arises and it cannot be fixed, it may be best to not use enum extension at all.

This section goes over patterns to look out for and to avoid in your own code, but it is in no way exhaustive.

### Switch Expressions {#switch-expressions}

Switches are often used to handle enum constants, this can lead to a crash if a switch expression has no `default` case to handle
entries added by mods. For example, if we had the following switch expression:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-problematic-switch-expr-example

Even though we handle all the values that are in the Vanilla enum and our own, this would throw for any other mod's added entry.

If the expression is from a mod, you should consider raising an issue to the developers to find a more compatible option.
If the expression is in a Vanilla method or you are unable to get the issue fixed by the mod developers, a mixin is likely needed.

There is no one way for mixins to modify switch expressions to avoid throws, and the approach should be made on a case-by-case basis.

### Serialized Enums {#serialized-enums}

Certain enums may have their entries serialized automatically, such as the static `Variants` enum in the `Axolotl` class. Adding an entry to these enums
would make your entry serialized under Minecraft's namespace by the codec, on some versions having it be serialized based on a numerical ID.

It is best to avoid extending enums whose entries are serialized like this entirely, and if possible looking for an API.
