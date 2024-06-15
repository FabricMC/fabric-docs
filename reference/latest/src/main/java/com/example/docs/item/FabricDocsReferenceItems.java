package com.example.docs.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import com.example.docs.FabricDocsReference;

// :::1
public class FabricDocsReferenceItems implements ModInitializer {
	// :::1
	// :::2
	public static final ItemGroup MY_MOD_ITEMGROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.GUIDITE_SWORD))
			.displayName(Text.translatable("itemGroup.fabric_docs_reference"))
			.build();
	// :::2
	// :::1
	@Override
	public void onInitialize() {
		ModItems.initialize();
		// :::1
		// :::3
		Registry.register(Registries.ITEM_GROUP, new Identifier(FabricDocsReference.MOD_ID, "item_group"), MY_MOD_ITEMGROUP);
		// :::3
		// :::1
	}
}
// :::1
