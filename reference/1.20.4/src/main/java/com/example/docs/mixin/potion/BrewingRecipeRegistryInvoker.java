package com.example.docs.mixin.potion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;

// :::1
@Mixin(BrewingRecipeRegistry.class)
public interface BrewingRecipeRegistryInvoker {
	@Invoker("registerPotionRecipe")
	static void invokeRegisterPotionRecipe(Potion input, Item item, Potion output) {
		throw new AssertionError();
	}
}
// :::1
