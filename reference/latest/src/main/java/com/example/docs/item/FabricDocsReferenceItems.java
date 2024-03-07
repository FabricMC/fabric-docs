package com.example.docs.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class FabricDocsReferenceItems implements ModInitializer {
	// :::1
	public static final Item COOL_TATER_ITEM = new Item(new FabricItemSettings());
	// :::1

	@Override
	public void onInitialize() {
		// :::2
		Registry.register(Registries.ITEM, new Identifier("fabric-docs-reference", "cool_tater"), COOL_TATER_ITEM);
		// :::2
	}
}
