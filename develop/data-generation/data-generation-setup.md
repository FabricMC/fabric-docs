---
title: Data Generation Setup
description: A guide to setting up Data Generation with Fabric API.
authors:
  - skycatminepokie
---

# Data Generation Setup {#data-generation-setup}

## What is Data Generation? {#what-is-data-generation}
Data generation (or datagen) is an API for programmatically generating recipes, advancements, tags, item models, language files, loot tables, and basically anything JSON-based.

## Enabling Data Generation {#enabling-data-generation}

### At Project Creation {#enabling-data-generation-at-project-creation}
The easiest way to enable datagen is at project creation. Check the "Enable Data Generation" box when using the template generator. %%TODO: images%%

::: tip
If datagen is enabled, you should have a "Data Generation" run configuration and a `runDatagen` Gradle task.
:::

### Manually {#manually-enabling-data-generation}
First, we need to enable datagen in the `build.gradle` file.

@[code lang=groovy transcludeWith=:::data-generation-setup:1](@/reference/build.gradle)

Next, we need an entrypoint class. This is where our datagen starts. Place this somewhere in the `client` package - this example places it at `src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java`. %%TODO: Cleanup%%

@[code lang=java transcludeWith=:::data-generation-setup:2](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

Finally, we need to tell Fabric about the entrypoint in our `fabric.mod.json`:

```json
{
  // ...
  "entrypoints": {
    // ...
    "fabric-datagen": [ // [!code ++]
	  "com.exmaple.docs.datagen.FabricDocsReferenceDataGenerator" // [!code ++]
	] // [!code ++]
  }
}
```

::: warning
Don't forget to add a comma (`,`) after the previous entrypoint block! %%TODO: Find a better way to convey this%%
:::

Close and reopen IntelliJ to create a run configuration for datagen. %%todo: wording%%

## Creating a Pack {#creating-a-pack}
Inside your datagen entrypoint, we need to create a `Pack`. Later, you'll add **providers**, which put generated data into this `Pack`. %%TODO: Move to a region somehow%%

```java
@Override
public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
	FabricDataGenerator.Pack pack = generator.createPack();
}
```

## Running Datagen {#running-datagen}
To run datagen, use the run configuration in your IDE, or run `./gradlew runDatagen` in the console. The generated files will be created in `src/main/generated`. %%TODO: Run config picture?%%

## Next Steps {#next-steps}

Now that datagen is set up, we need to add **providers**. These are what generate the data to add to your `Pack`. The following pages outline how to do this. %%TODO: Wording%%