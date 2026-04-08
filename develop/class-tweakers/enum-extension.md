---
title: Enum Extension
description: Learn how to add entries to enums with Mixin and Class Tweakers.
authors:
  - MildestToucan
---

Enum extension is a Mixin feature that can reliably add new entries to an enum.

When targeting Minecraft enums, the mixin can be combined with [class tweaking](../class-tweakers) to display the new enum entries
to the decompiled source. This can be [transitive](../class-tweakers/index#transitive-entries) to allow mods depending on yours to see your added entries.

::: warning

Enum extension requires Loader 0.19.0 for Mixin support and Loom 1.16 for class tweaker support.

Additionally, class tweaker file headers must specify a version of `v2` to use enum extensions.

:::

## Creating the Mixin {#creating-the-mixin}

Before creating the mixin class, make sure that you are depending explicitly on Loader 0.19.0 or above
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

Even if you are using the correct Loader version as a Gradle dependency, you must explicitly depend on it in
order to opt into new Mixin features.

To make an enum extension, create a `@Mixin`-annotated enum class, and add your constants to it as if they were
part of the targeted enum class. For example, adding a new `RecipeBookType` entry would look like this:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeBookTypeMixin.java#enum-extension-no-impls-example-mixin

Since there are no fields or methods to implement in the target enum, there's no need to do anything else.

::: warning

Added enum constants should always be prefixed by your mod id to ensure uniqueness. To preserve capitalization, the prefix would typically be
`MODID_`.

:::

If the enum entries need fields or to implement methods, you should shadow the relevant constructor by creating a matching one and annotating it with
`@Shadow`. For example, adding a new `RecipeCategory` entry would look like this:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeCategoryMixin.java#enum-extension-ctor-impls-example-mixin

## Making the Class Tweaker Entry {#making-the-class-tweaker-entry}

If you are targeting a Minecraft enum, you can use a class tweaker entry to make the added entry appear in the target enum.

To opt into this feature, remember to use Loom 1.16 or above, and to have the file [header](../class-tweakers/index#file-format) have its

The syntax for an enum extension entry is:

```classtweaker:no-line-numbers
extend-enum    <targetClassName>    <ENUM_CONSTANT_NAME>
```

For class tweaking, classes use their [internal names](../mixins/bytecode#class-names).

For example, the class tweaker entries for the constants we added in the [mixin section](#creating-the-mixin) would respectively be:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#enum-extension-no-impls-example-entry

and

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#enum-extension-ctor-impls-example-entry

## Applying Changes {#applying-changes}

To see your added enum entries in the decompiled source, you must refresh your Gradle project by [regenerating sources](../getting-started/generating-sources).
If modifications do not appear, you can try [validating](../class-tweakers/index#validating-the-file) the file and checking if any errors appear.

The added entries should now appear in the decompiled source. If the enum has fields or other elements for its entries to implement, those will not appear for your
added entry, and are instead left to your mixin to implement at runtime.

You can now use the enum constant from your code:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-usage-example
