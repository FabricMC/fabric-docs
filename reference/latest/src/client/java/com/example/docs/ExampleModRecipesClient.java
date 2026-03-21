package com.example.docs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.recipe.ExampleModRecipes;
import com.example.docs.rendering.screens.inventory.UpgradingScreen;

import net.minecraft.client.multiplayer.ClientLevel;

public class ExampleModRecipesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ExampleModRecipes.UPGRADING_MENU_TYPE, UpgradingScreen::new);
		ClientLevel clientLevel = Minecraft.getInstance().level;
		//:::recipeSyncClient
		clientLevel.recipeAccess().getSynchronizedRecipes().getAllOfType(ExampleModRecipes.UPGRADING_RECIPE_TYPE);
		//:::recipeSyncClient
	}
}
