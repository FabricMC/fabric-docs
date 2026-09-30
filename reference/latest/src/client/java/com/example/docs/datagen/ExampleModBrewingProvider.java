package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBrewingProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import com.example.docs.potion.ExampleModPotions;

public class ExampleModBrewingProvider extends FabricRecipeProvider {
	public ExampleModBrewingProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	// #region register_recipes
	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
		return new RecipeProvider(recipes, advancements) {
			@Override
			public void buildRecipes() {
				FabricBrewingProvider brewingProvider = new FabricBrewingProvider(output) {
					@Override
					protected void buildMixes() {
						buildMix(
								// Input potion.
								Potions.WATER,
								// Ingredient
								Items.POTATO,
								// Output potion.
								ExampleModPotions.TATER_POTION
						);
					}
				};
				brewingProvider.buildRecipes();
			}
		};
	}
	// #endregion register_recipes
}
