package com.example.docs;

import com.example.docs.menu.ModMenuType;

import com.example.docs.rendering.screens.inventory.DirtChestScreen;

import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.gui.screens.MenuScreens;

// :::registerScreens
public class ExampleModScreens implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ModMenuType.DIRT_CHEST, DirtChestScreen::new);
	}
}
// :::registerScreens
