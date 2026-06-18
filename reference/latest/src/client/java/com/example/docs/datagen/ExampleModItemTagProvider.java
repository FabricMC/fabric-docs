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
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;

import com.example.docs.ExampleMod;
import com.example.docs.item.ModItems;

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
		valueLookupBuilder(SMELLY_ITEMS)
				.add(Items.SLIME_BALL)
				.add(Items.ROTTEN_FLESH)
				.addOptionalTag(ItemTags.DIRT)
				.add(Items.OAK_PLANKS)
				.forceAddTag(ItemTags.BANNERS)
				.setReplace(true);
		// #endregion datagen_tags_build
		
		// #region shield_tags
		valueLookupBuilder(ConventionalItemTags.SHIELD_TOOLS)
						.add(ModItems.GUIDITE_SHIELD);
		valueLookupBuilder(ItemTags.DURABILITY_ENCHANTABLE)
						.add(ModItems.GUIDITE_SHIELD);
		// #endregion shield_tags

		// #region sword_tags
		valueLookupBuilder(ItemTags.SWORDS)
						.add(ModItems.GUIDITE_SWORD);
		valueLookupBuilder(ItemTags.AXES)
						.add(ModItems.GUIDITE_AXE);
		// #endregion sword_tags

		// #region datagen_tags_provider
	}
}
// #endregion datagen_tags_provider
