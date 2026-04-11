package com.example.docs.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

// #region recipe-input
public record UpgradingRecipeInput(ItemStack baseItem, ItemStack upgradeItem) implements RecipeInput {
	@Override
	public ItemStack getItem(int index) {
		return switch (index) {
			case 0 -> baseItem;
			case 1 -> upgradeItem;
			default -> ItemStack.EMPTY;
		};
	}

	@Override
	public int size() {
		return 2;
	}
}
// #endregion recipe-input
