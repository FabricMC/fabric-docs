package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import com.example.docs.ExampleMod;
import com.example.docs.item.ModItemIds;

// #region datagen_tags_provider
public class ExampleModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	// #endregion datagen_tags_provider
	// #region datagen_tags_tag_key
	public static final TagKey<Item> SMELLY_ITEMS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "smelly_items"));
	// #endregion datagen_tags_tag_key
	// #region datagen_tags_provider
	public ExampleModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		// #endregion datagen_tags_provider
		// #region datagen_tags_build
		builder(SMELLY_ITEMS)
				.add(ItemIds.SLIME_BALL)
				.add(ItemIds.ROTTEN_FLESH)
				.addOptionalTag(ItemTags.DIRT)
				.add(BlockItemIds.OAK_PLANKS)
				.forceAddTag(ItemTags.BANNERS)
				.setReplace(true);
		// #endregion datagen_tags_build

		// #region sword_tags
		builder(ItemTags.SWORDS)
						.add(ModItemIds.GUIDITE_SWORD);
		builder(ItemTags.AXES)
						.add(ModItemIds.GUIDITE_AXE);
		// #endregion sword_tags

		// #region datagen_tags_provider
	}
}
// #endregion datagen_tags_provider
