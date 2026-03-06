package com.example.docs.recipe;

import com.example.docs.ExampleMod;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeType;

public class ExampleModRecipes implements ModInitializer {
	//:::1
	public static UpgradingRecipeSerializer UPGRADING_RECIPE_SERIALIZER = Registry.register(
					BuiltInRegistries.RECIPE_SERIALIZER,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "upgrading"),
					new UpgradingRecipeSerializer()
	);

	public static RecipeType<UpgradingRecipe> UPGRADING_RECIPE_TYPE = Registry.register(
					BuiltInRegistries.RECIPE_TYPE,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "upgrading"),
					new RecipeType<UpgradingRecipe>() {}
	);

	public static RecipeBookCategory UPGRADING_RECIPE_BOOK_CATEGORY = Registry.register(
					BuiltInRegistries.RECIPE_BOOK_CATEGORY,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "upgrading"),
					new RecipeBookCategory()
	);
	//:::1

	@Override
	public void onInitialize() {
	}
}
