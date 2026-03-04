package com.example.docs;

import net.minecraft.client.gui.screens.MenuScreens;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.menu.ModMenuType;
import com.example.docs.rendering.screens.inventory.DirtChestScreen;

// :::registerScreens
public class ExampleModScreens implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ModMenuType.DIRT_CHEST, DirtChestScreen::new);
	}
}
// :::registerScreens
