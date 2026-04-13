---
title: Enum Extension
description: Learn how to add entries to enums with Mixin and Class Tweakers.
authors:
  - cassiancc
  - CelDaemon
  - its-miroma
  - Jab125
  - LlamaLad7
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

### Passing Constructor Arguments {#passing-constructor-arguments}

If the target enum has no default constructor, you must shadow a target class constructor and pass the needed arguments to your added
entry's declaration.

For example, let's add a new `RecipeCategory` entry. Create a constructor matching the desired one in the target class, and annotate it with `@Shadow`.

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeCategoryMixin.java#enum-extension-ctor-impls-example-mixin

### Implementing Abstract Methods {#implementing-abstract-methods}

To implement a target enum's abstract methods, shadow the abstract method, then override and implement it in your added entry. For instance,
let's add a new `ConversionType` entry:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/ConversionTypeMixin.java#enum-extension-abstract-method-impls-example-mixin

## Making the Class Tweaker Entry {#making-the-class-tweaker-entry}

If you are targeting a Minecraft enum, you can use a class tweaker entry to visibly modify the target enum in the decompiled source.

To opt into this feature, remember to use Loom 1.16 or above, and to set the [file header version](../class-tweakers/index#file-format) to `v2`.

The syntax for an enum extension entry is:

```:no-line-numbers
extend-enum  <targetClassName>  <ENUM_CONSTANT_NAME>
```

For class tweaking, classes use their [internal names](../mixins/bytecode#class-names).

For example, the class tweaker entry for the `RecipeBookType` constant we added in the [mixin section](#creating-the-mixin) would be:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#enum-extension-no-impls-example-entry{classtweaker:no-line-numbers}

## Applying Changes {#applying-changes}

You'll have to refresh your Gradle project and [regenerate sources](../getting-started/generating-sources) before you can see your added enum entries in the decompiled source.
If modifications do not appear, you can try [validating](../class-tweakers/index#validating-the-file) the file and checking if any errors appear.

::: info

You will not see [passed constructor arguments](#passing-constructor-arguments), [method implementations](#implementing-abstract-methods) or other elements in the decompiled source code.
That's because those are handled by the mixin, and are only applied at runtime.

:::

You can now use the enum constant in your code:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-usage-example

If you are only adding it with a mixin and it is not in the decompiled source, you can check against it by comparing the name:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-no-ct-usage-example-check

If you need to use the constant in multiple areas, obtain it by calling `valueOf` and store the result in a field:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-no-ct-usage-example-store

## Pitfalls {#pitfalls}

Enum extension cannot guarantee that entries you add will not break anything.

It's your responsibility to go through the target enum's usages, and try to
prevent issues where possible. If you are unable to solve some, and crashes arise, it might be best to not use enum extension at all.

This section goes over some patterns to watch out for and avoid when extending enums, but it is not exhaustive.

### Switch Expressions {#switch-expressions}

Switch statements are often used to handle enum constants. Because of this, a crash can happen if a switch expression does not handle
entries added by other mods. For example, say we have the following switch expression:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-problematic-switch-expr-example

Notice how there is no `default` clause. Even though we handled all the values in the Vanilla enum, and our own, this would throw if another mod adds a different entry.

How can you prevent this? There is no single universal way to avoid crashes - your approach should be adapted on a case-by-case basis. In general, though:

- If the `switch` expression is in a vanilla method, you can use a mixin to edit it
- If the `switch` expression comes from a mod, you should try getting in contact with the developers, to work together a compatible approach. Otherwise, you might have to create a mixin on the other mod.

### Serialized Enums {#serialized-enums}

Certain enums' entries get serialized automatically. An example is the `Variants` enum in the `Axolotl` class.

Extending these enums
would serialize your custom entry under Minecraft's namespace, and on some versions that might happen based on a numerical ID.
This is not great because it can affect the indices of all other entries.

It is best to avoid extending enums entirely if their entries are serialized like this. Instead, you might want to look for an API, if available.
