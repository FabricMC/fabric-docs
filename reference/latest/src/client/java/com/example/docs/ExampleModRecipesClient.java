package com.example.docs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.recipe.ExampleModRecipes;
import com.example.docs.rendering.screens.inventory.UpgradingScreen;

public class ExampleModRecipesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ExampleModRecipes.UPGRADING_MENU_TYPE, UpgradingScreen::new);
	}

	private static void getRecipes() {
		ClientLevel clientLevel = Minecraft.getInstance().level;
		// #region recipe-sync-client
		clientLevel.recipeAccess().getSynchronizedRecipes().getAllOfType(ExampleModRecipes.UPGRADING_RECIPE_TYPE);
		// #endregion recipe-sync-client
	}
}
