package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import com.example.docs.FabricDocsReference;

// :::datagen-tags:provider
public class FabricDocsReferenceItemTagProvider extends FabricTagProvider.ItemTagProvider {
	// :::datagen-tags:provider
	// :::datagen-tags:tag-key
	public static final TagKey<Item> SMELLY_ITEMS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, "smelly_items"));
	// :::datagen-tags:tag-key
	// :::datagen-tags:provider
	public FabricDocsReferenceItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		// :::datagen-tags:provider
		// :::datagen-tags:build
		valueLookupBuilder(SMELLY_ITEMS)
				.add(Items.SLIME_BALL)
				.add(Items.ROTTEN_FLESH)
				.addOptionalTag(ItemTags.DIRT)
				.add(Items.OAK_PLANKS)
				.forceAddTag(ItemTags.BANNERS)
				.setReplace(true);
		// :::datagen-tags:build
		// :::datagen-tags:provider
	}
}
// :::datagen-tags:provider
