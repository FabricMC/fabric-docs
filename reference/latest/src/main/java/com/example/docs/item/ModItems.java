package com.example.docs.item;

import com.example.docs.FabricDocsReference;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

// :::1
public class ModItems {
	// :::1

	// :::2
	public static final Item SUSPICIOUS_SUBSTANCE = register(
			new Item(new FabricItemSettings()),
			"suspicious_substance"
	);
	// :::2

	// :::1
	// We can use generics (<T extends Item>), so we don't need to
	// cast to an item when using this method.
	public static <T extends Item> T register(T item, String ID) {
		// Create the identifier for the item.
		Identifier itemID = new Identifier(FabricDocsReference.MOD_ID, ID);

		// Register the item.
		T registeredItem = Registry.register(Registries.ITEM, itemID, item);

		// Return the registered item!
		return registeredItem;
	}
	// :::1

	// :::3
	public static void initialize() {
		// :::3
		// :::4
		// Get the event for modifying entries in the ingredients group.
		// And register an event handler that adds our suspicious item to the ingredients group.
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
				.register((itemGroup) -> itemGroup.add(ModItems.SUSPICIOUS_SUBSTANCE));
		// :::4
		// :::3
	}
	// :::3
}
// :::1