package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import com.example.docs.FabricDocsReference;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.util.Identifier;

// :::datagen-tags:1
public class FabricDocsReferenceItemTagProvider extends FabricTagProvider<Item> {
	// :::datagen-tags:1
	// :::datagen-tags:3
	public static final TagKey<Item> SMELLY_ITEMS = TagKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "smelly_items"));
	// :::datagen-tags:3
	// :::datagen-tags:1
	public FabricDocsReferenceItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.ITEM, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		// :::datagen-tags:1
		// :::datagen-tags:4
		getOrCreateTagBuilder(SMELLY_ITEMS)
				.add(Items.SLIME_BALL)
				.add(Items.ROTTEN_FLESH)
				.addOptionalTag(ItemTags.DIRT)
				.add(Identifier.ofVanilla("wooden_planks"))
				.forceAddTag(ItemTags.BANNERS)
				.setReplace(true);
		// :::datagen-tags:4
		// :::datagen-tags:1
	}
}
// :::datagen-tags:1