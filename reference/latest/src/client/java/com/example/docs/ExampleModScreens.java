package com.example.docs;

import net.minecraft.client.gui.screens.MenuScreens;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.menu.ExampleModMenuTypes;
import com.example.docs.rendering.screens.inventory.DirtChestScreen;

// #region register_screens
public class ExampleModScreens implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ExampleModMenuTypes.DIRT_CHEST, DirtChestScreen::new);
	}
}
// #endregion register_screens
