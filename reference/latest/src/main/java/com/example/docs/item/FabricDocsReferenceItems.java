package com.example.docs.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class FabricDocsReferenceItems implements ModInitializer {
	// :::1
	public static final Item COOL_TATER_ITEM = new Item(new FabricItemSettings());
	// :::1

	// :::4
	private static final ItemGroup COOL_TATER_ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(COOL_TATER_ITEM))
			.displayName(Text.translatable("itemGroup.fabric-docs-reference.cool_tater")).entries(
					(displayContext, entries) -> {
						entries.add(COOL_TATER_ITEM);
					}
			).build();
	// :::4

	@Override
	public void onInitialize() {
		// :::2
		Registry.register(Registries.ITEM, new Identifier("fabric-docs-reference", "cool_tater"), COOL_TATER_ITEM);
		// :::2

		// :::3
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
			entries.add(COOL_TATER_ITEM);
			entries.addAfter(Items.POTATO, COOL_TATER_ITEM);
		});
		// :::3

		// :::5
		Registry.register(Registries.ITEM_GROUP, new Identifier("fabric-docs-reference", "cool_tater"), COOL_TATER_ITEM_GROUP);
		// :::5
	}
}
