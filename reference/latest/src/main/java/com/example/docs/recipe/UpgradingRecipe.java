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

// :::baseClass
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
	// :::baseClass

	// :::implementing
	@Override
	public boolean matches(UpgradingRecipeInput recipeInput, Level level) {
		return this.baseItem.test(recipeInput.baseItem()) && this.upgradeItem.test(recipeInput.upgradeItem());
	}

	@Override
	public ItemStack assemble(UpgradingRecipeInput recipeInput) {
		return this.result.create().copy();
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

	@Override
	public boolean showNotification() {
		return true;
	}

	@Override
	public String group() {
		return "upgrading";
	}
	// :::recipeBook

	//:::mapCodec
	public static final MapCodec<UpgradingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
					instance.group(
									ItemStackTemplate.CODEC.fieldOf("result").forGetter(UpgradingRecipe::getResult),
									Ingredient.CODEC.fieldOf("baseItem").forGetter(UpgradingRecipe::getBaseItem),
									Ingredient.CODEC.fieldOf("upgradeItem").forGetter(UpgradingRecipe::getUpgradeItem)
					).apply(instance, UpgradingRecipe::new)
	);
	//:::mapCodec

	//:::streamCodec
	public static final StreamCodec<RegistryFriendlyByteBuf, UpgradingRecipe> STREAM_CODEC = StreamCodec.composite(
					ItemStackTemplate.STREAM_CODEC,
					UpgradingRecipe::getResult,
					Ingredient.CONTENTS_STREAM_CODEC,
					UpgradingRecipe::getBaseItem,
					Ingredient.CONTENTS_STREAM_CODEC,
					UpgradingRecipe::getUpgradeItem,
					UpgradingRecipe::new
	);
	//:::streamCodec

	// :::baseClass
}
// :::baseClass
