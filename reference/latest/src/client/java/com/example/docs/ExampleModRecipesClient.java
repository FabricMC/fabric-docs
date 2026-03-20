package com.example.docs;

import com.example.docs.recipe.ExampleModRecipes;

import com.example.docs.rendering.screens.inventory.UpgradingScreen;

import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.gui.screens.MenuScreens;

public class ExampleModRecipesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ExampleModRecipes.UPGRADING_MENU_TYPE, UpgradingScreen::new);
	}
}
