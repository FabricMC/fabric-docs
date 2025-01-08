package com.example.docs.item;

import net.fabricmc.api.ModInitializer;

import com.example.docs.item.armor.GuiditeArmorMaterial;

// :::1
public class FabricDocsReferenceItems implements ModInitializer {
	@Override
	public void onInitialize() {
		ModItems.initialize();
	}
}
// :::1
