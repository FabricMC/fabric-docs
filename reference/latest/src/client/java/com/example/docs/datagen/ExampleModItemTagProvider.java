package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import com.example.docs.ExampleMod;

// #region datagen_tags__provider
public class ExampleModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	// #endregion datagen_tags__provider
	// #region datagen_tags__tag_key
	public static final TagKey<Item> SMELLY_ITEMS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "smelly_items"));
	// #endregion datagen_tags__tag_key
	// #region datagen_tags__provider
	public ExampleModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		// #endregion datagen_tags__provider
		// #region datagen_tags__build
		valueLookupBuilder(SMELLY_ITEMS)
				.add(Items.SLIME_BALL)
				.add(Items.ROTTEN_FLESH)
				.addOptionalTag(ItemTags.DIRT)
				.add(Items.OAK_PLANKS)
				.forceAddTag(ItemTags.BANNERS)
				.setReplace(true);
		// #endregion datagen_tags__build
		// #region datagen_tags__provider
	}
}
// #endregion datagen_tags__provider
