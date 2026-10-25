package com.example.docs.recipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;

import com.example.docs.ExampleMod;
import com.example.docs.recipe.extending.EnchantingSmithingDemoSlotDisplay;
import com.example.docs.recipe.extending.EnchantingSmithingRecipe;
import com.example.docs.recipe.extending.StewSpikingCraftingRecipe;

public class ExampleModRecipes implements ModInitializer {
	// #region registration
	public static final RecipeSerializer<UpgradingRecipe> UPGRADING_RECIPE_SERIALIZER = Registry.register(
					BuiltInRegistries.RECIPE_SERIALIZER,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "upgrading"),
					new RecipeSerializer<>(UpgradingRecipe.CODEC, UpgradingRecipe.STREAM_CODEC)
	);

	public static final RecipeType<UpgradingRecipe> UPGRADING_RECIPE_TYPE = Registry.register(
					BuiltInRegistries.RECIPE_TYPE,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "upgrading"),
					new RecipeType<UpgradingRecipe>() { }
	);
	// #endregion registration

	// TODO: recipe book support, requires enum extensions + screen changes
	public static final RecipeBookCategory UPGRADING_RECIPE_BOOK_CATEGORY = Registry.register(
					BuiltInRegistries.RECIPE_BOOK_CATEGORY,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "upgrading"),
					new RecipeBookCategory()
	);

	@Override
	public void onInitialize() {
		// #region recipe_sync
		RecipeSynchronization.synchronizeRecipeSerializer(UPGRADING_RECIPE_SERIALIZER);
		// #endregion recipe_sync

		// #region enchanting_smithing_registration
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "smithing_enchanting"), EnchantingSmithingRecipe.SERIALIZER);
		Registry.register(BuiltInRegistries.SLOT_DISPLAY, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "enchanting_smithing"), EnchantingSmithingDemoSlotDisplay.TYPE);
		// #endregion enchanting_smithing_registration

		// #region stew_spiking_registration
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "stew_spiking"), StewSpikingCraftingRecipe.SERIALIZER);
		// #endregion stew_spiking_registration
	}
}
