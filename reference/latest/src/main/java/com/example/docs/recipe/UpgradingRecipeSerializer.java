package com.example.docs.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
//:::mapCodec

public class UpgradingRecipeSerializer implements RecipeSerializer<UpgradingRecipe> {
	public static final MapCodec<UpgradingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
					instance.group(
									ItemStack.STRICT_CODEC.fieldOf("result").forGetter(UpgradingRecipe::getResult),
									Ingredient.CODEC.fieldOf("baseItem").forGetter(UpgradingRecipe::getBaseItem),
									Ingredient.CODEC.fieldOf("upgradeItem").forGetter(UpgradingRecipe::getUpgradeItem)
					).apply(instance, UpgradingRecipe::new)
	);
	//:::mapCodec

	//:::streamCodec
	public static final StreamCodec<RegistryFriendlyByteBuf, UpgradingRecipe> STREAM_CODEC = StreamCodec.composite(
					ItemStack.STREAM_CODEC,
					UpgradingRecipe::getResult,
					Ingredient.CONTENTS_STREAM_CODEC,
					UpgradingRecipe::getBaseItem,
					Ingredient.CONTENTS_STREAM_CODEC,
					UpgradingRecipe::getUpgradeItem,
					UpgradingRecipe::new
	);
	//:::streamCodec

	//:::implementing
	@Override
	public MapCodec<UpgradingRecipe> codec() {
		return CODEC;
	}

	@Override
	public StreamCodec<RegistryFriendlyByteBuf, UpgradingRecipe> streamCodec() {
		return STREAM_CODEC;
	}
	//:::implementing
	//:::mapCodec
}
//:::mapCodec
