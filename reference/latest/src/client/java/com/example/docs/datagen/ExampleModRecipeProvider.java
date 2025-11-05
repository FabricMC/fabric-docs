package com.example.docs.datagen;

// :::datagen-recipes:provider
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.registry.ResourceKeys;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

public class ExampleModRecipeProvider extends FabricRecipeProvider {
	public ExampleModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
		return new RecipeProvider(registryLookup, exporter) {
			@Override
			public void buildRecipes() {
				HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(ResourceKeys.ITEM);
				// :::datagen-recipes:provider
				// :::datagen-recipes:shapeless

				shapeless(RecipeCategory.BUILDING_BLOCKS, Items.DIRT) // You can also specify an int to produce more than one
						.requires(Items.COARSE_DIRT) // You can also specify an int to require more than one, or a tag to accept multiple things
						// Create an advancement that gives you the recipe
						.unlockedBy(getHasName(Items.COARSE_DIRT), has(Items.COARSE_DIRT))
						.save(output);
				// :::datagen-recipes:shapeless
				// :::datagen-recipes:shaped
				shaped(RecipeCategory.MISC, Items.CRAFTING_TABLE, 4)
						.pattern("ll")
						.pattern("ll")
						.define('l', ItemTags.LOGS) // 'l' means "any log"
						.group("multi_bench") // Put it in a group called "multi_bench" - groups are shown in one slot in the recipe book
						.unlockedBy(getHasName(Items.CRAFTING_TABLE), has(Items.CRAFTING_TABLE))
						.save(output);
				shaped(RecipeCategory.MISC, Items.LOOM, 4)
						.pattern("ww")
						.pattern("ll")
						.define('w', ItemTags.WOOL) // 'w' means "any wool"
						.define('l', ItemTags.LOGS)
						.group("multi_bench")
						.unlockedBy(getHasName(Items.LOOM), has(Items.LOOM))
						.save(output);
				doorBuilder(Items.OAK_DOOR, Ingredient.of(Items.OAK_BUTTON)) // Using a helper method!
						.unlockedBy(getHasName(Items.OAK_BUTTON), has(Items.OAK_BUTTON))
						.save(output);
				// :::datagen-recipes:shaped
				// :::datagen-recipes:other
				oreSmelting(
						List.of(Items.BREAD, Items.COOKIE, Items.HAY_BLOCK), // Inputs
						RecipeCategory.FOOD, // Category
						Items.WHEAT, // Output
						0.1f, // Experience
						300, // Cooking time
						"food_to_wheat" // group
				);
				// :::datagen-recipes:other
				// :::datagen-recipes:provider
			}
		};
	}

	@Override
	public String getName() {
		return "ExampleModRecipeProvider";
	}
}
// :::datagen-recipes:provider
