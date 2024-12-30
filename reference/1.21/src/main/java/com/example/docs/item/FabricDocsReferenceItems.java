package com.example.docs.item;

import net.fabricmc.api.ModInitializer;

import com.example.docs.item.armor.ModArmorMaterials;

// :::1
public class FabricDocsReferenceItems implements ModInitializer {
	@Override
	public void onInitialize() {
		// :::1
		// :::2
		ModArmorMaterials.initialize();
		// :::2
		// :::1
		ModItems.initialize();
	}
}
// :::1
