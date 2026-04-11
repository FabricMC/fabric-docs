package com.example.docs;

import net.minecraft.client.gui.screens.MenuScreens;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.menu.ModMenuType;
import com.example.docs.rendering.screens.inventory.DirtChestScreen;

// #region register-screens
public class ExampleModScreens implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ModMenuType.DIRT_CHEST, DirtChestScreen::new);
	}
}
// #endregion register-screens
