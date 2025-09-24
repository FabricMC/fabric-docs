package com.example.docs.datagen;

// :::datagen-recipes:provider
import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

public class ExampleModRecipeProvider extends FabricRecipeProvider {
	public ExampleModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate(RecipeExporter recipeExporter) {
		// :::datagen-recipes:provider
		// :::datagen-recipes:shapeless
		ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.DIRT) // You can also specify an int to produce more than one
				.input(Items.COARSE_DIRT) // You can also specify an int to require more than one, or a tag to accept multiple things
				// Create an advancement that gives you the recipe
				.criterion(FabricRecipeProvider.hasItem(Items.COARSE_DIRT), FabricRecipeProvider.conditionsFromItem(Items.COARSE_DIRT))
				.offerTo(recipeExporter);
		// :::datagen-recipes:shapeless
		// :::datagen-recipes:shaped
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CRAFTING_TABLE, 4)
				.pattern("ll")
				.pattern("ll")
				.input('l', ItemTags.LOGS) // 'l' means "any log"
				.group("multi_bench") // Put it in a group called "multi_bench" - groups are shown in one slot in the recipe book
				.criterion(FabricRecipeProvider.hasItem(Items.CRAFTING_TABLE), FabricRecipeProvider.conditionsFromItem(Items.CRAFTING_TABLE))
				.offerTo(recipeExporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LOOM, 4)
				.pattern("ww")
				.pattern("ll")
				.input('w', ItemTags.WOOL) // 'w' means "any wool"
				.input('l', ItemTags.LOGS)
				.group("multi_bench")
				.criterion(FabricRecipeProvider.hasItem(Items.LOOM), FabricRecipeProvider.conditionsFromItem(Items.LOOM))
				.offerTo(recipeExporter);
		FabricRecipeProvider.createDoorRecipe(Items.OAK_DOOR, Ingredient.ofItems(Items.OAK_BUTTON)) // Using a helper method!
				.criterion(FabricRecipeProvider.hasItem(Items.OAK_BUTTON), FabricRecipeProvider.conditionsFromItem(Items.OAK_BUTTON))
				.offerTo(recipeExporter);
		// :::datagen-recipes:shaped
		// :::datagen-recipes:other
		FabricRecipeProvider.offerSmelting(recipeExporter,
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
