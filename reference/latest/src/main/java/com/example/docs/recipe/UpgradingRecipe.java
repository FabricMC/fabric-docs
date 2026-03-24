package com.example.docs.recipe;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

// :::baseClass
public class UpgradingRecipe implements Recipe<UpgradingRecipeInput> {
	private final ItemStack result;
	private final Ingredient baseItem;
	private final Ingredient upgradeItem;

	public UpgradingRecipe(ItemStack result, Ingredient baseItem, Ingredient upgradeItem) {
		this.baseItem = baseItem;
		this.upgradeItem = upgradeItem;
		this.result = result;
	}

	public ItemStack getResult() {
		return result;
	}

	public Ingredient getBaseItem() {
		return baseItem;
	}

	public Ingredient getUpgradeItem() {
		return upgradeItem;
	}
	// :::baseClass

	// :::implementing
	@Override
	public boolean matches(UpgradingRecipeInput recipeInput, Level level) {
		return baseItem.test(recipeInput.baseItem()) && upgradeItem.test(recipeInput.upgradeItem());
	}

	@Override
	public ItemStack assemble(UpgradingRecipeInput recipeInput, HolderLookup.Provider provider) {
		return result.copy();
	}
	// :::implementing

	// :::implementRegistryObjects
	@Override
	public RecipeSerializer<? extends Recipe<UpgradingRecipeInput>> getSerializer() {
		return ExampleModRecipes.UPGRADING_RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<? extends Recipe<UpgradingRecipeInput>> getType() {
		return ExampleModRecipes.UPGRADING_RECIPE_TYPE;
	}
	// :::implementRegistryObjects

	// :::recipeBook
	@Override
	public @Nullable RecipeBookCategory recipeBookCategory() {
		return null;
	}

	@Override
	public PlacementInfo placementInfo() {
		return PlacementInfo.NOT_PLACEABLE;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}
	// :::recipeBook
	// :::baseClass
}
// :::baseClass
