package com.example.docs.datagen;

// :::datagen-recipes:provider
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ExampleModRecipeProvider extends FabricRecipeProvider {
	public ExampleModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void buildRecipes(RecipeOutput recipeExporter) {
		// :::datagen-recipes:provider
		// :::datagen-recipes:shapeless
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.DIRT) // You can also specify an int to produce more than one
				.requires(Items.COARSE_DIRT) // You can also specify an int to require more than one, or a tag to accept multiple things
				// Create an advancement that gives you the recipe
				.unlockedBy(FabricRecipeProvider.getHasName(Items.COARSE_DIRT), FabricRecipeProvider.has(Items.COARSE_DIRT))
				.save(recipeExporter);
		// :::datagen-recipes:shapeless
		// :::datagen-recipes:shaped
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CRAFTING_TABLE, 4)
				.pattern("ll")
				.pattern("ll")
				.define('l', ItemTags.LOGS) // 'l' means "any log"
				.group("multi_bench") // Put it in a group called "multi_bench" - groups are shown in one slot in the recipe book
				.unlockedBy(FabricRecipeProvider.getHasName(Items.CRAFTING_TABLE), FabricRecipeProvider.has(Items.CRAFTING_TABLE))
				.save(recipeExporter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LOOM, 4)
				.pattern("ww")
				.pattern("ll")
				.define('w', ItemTags.WOOL) // 'w' means "any wool"
				.define('l', ItemTags.LOGS)
				.group("multi_bench")
				.unlockedBy(FabricRecipeProvider.getHasName(Items.LOOM), FabricRecipeProvider.has(Items.LOOM))
				.save(recipeExporter);
		FabricRecipeProvider.doorBuilder(Items.OAK_DOOR, Ingredient.of(Items.OAK_BUTTON)) // Using a helper method!
				.unlockedBy(FabricRecipeProvider.getHasName(Items.OAK_BUTTON), FabricRecipeProvider.has(Items.OAK_BUTTON))
				.save(recipeExporter);
		// :::datagen-recipes:shaped
		// :::datagen-recipes:other
		FabricRecipeProvider.oreSmelting(recipeExporter,
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
}
// :::datagen-recipes:provider
