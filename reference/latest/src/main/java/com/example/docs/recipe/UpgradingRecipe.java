package com.example.docs.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.Nullable;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

// #region base_class
public class UpgradingRecipe implements Recipe<UpgradingRecipeInput> {
	private final ItemStackTemplate result;
	private final Ingredient baseItem;
	private final Ingredient upgradeItem;

	public UpgradingRecipe(ItemStackTemplate result, Ingredient baseItem, Ingredient upgradeItem) {
		this.baseItem = baseItem;
		this.upgradeItem = upgradeItem;
		this.result = result;
	}

	public ItemStackTemplate getResult() {
		return this.result;
	}

	public Ingredient getBaseItem() {
		return this.baseItem;
	}

	public Ingredient getUpgradeItem() {
		return this.upgradeItem;
	}
	// #endregion base_class

	// #region implementing
	@Override
	public boolean matches(UpgradingRecipeInput recipeInput, Level level) {
		return this.baseItem.test(recipeInput.baseItem()) && this.upgradeItem.test(recipeInput.upgradeItem());
	}

	@Override
	public ItemStack assemble(UpgradingRecipeInput recipeInput) {
		return this.result.create();
	}
	// #endregion implementing

	// #region implement_registry_objects
	@Override
	public RecipeSerializer<? extends Recipe<UpgradingRecipeInput>> getSerializer() {
		return ExampleModRecipes.UPGRADING_RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<? extends Recipe<UpgradingRecipeInput>> getType() {
		return ExampleModRecipes.UPGRADING_RECIPE_TYPE;
	}
	// #endregion implement_registry_objects

	// #region recipe_book
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

	@Override
	public boolean showNotification() {
		return true;
	}

	@Override
	public String group() {
		return "upgrading";
	}
	// #endregion recipe_book

	// #region map_codec
	public static final MapCodec<UpgradingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
					instance.group(
									ItemStackTemplate.CODEC.fieldOf("result").forGetter(UpgradingRecipe::getResult),
									Ingredient.CODEC.fieldOf("baseItem").forGetter(UpgradingRecipe::getBaseItem),
									Ingredient.CODEC.fieldOf("upgradeItem").forGetter(UpgradingRecipe::getUpgradeItem)
					).apply(instance, UpgradingRecipe::new)
	);
	// #endregion map_codec

	// #region stream_codec
	public static final StreamCodec<RegistryFriendlyByteBuf, UpgradingRecipe> STREAM_CODEC = StreamCodec.composite(
					ItemStackTemplate.STREAM_CODEC,
					UpgradingRecipe::getResult,
					Ingredient.CONTENTS_STREAM_CODEC,
					UpgradingRecipe::getBaseItem,
					Ingredient.CONTENTS_STREAM_CODEC,
					UpgradingRecipe::getUpgradeItem,
					UpgradingRecipe::new
	);
	// #endregion stream_codec

	// #region base_class
}
// #endregion base_class
