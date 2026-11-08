package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.VillagerTrade;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

public class ExampleModVillagerTradesTagProvider extends FabricTagsProvider<VillagerTrade> {
	public ExampleModVillagerTradesTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, Registries.VILLAGER_TRADE, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		getOrCreateRawBuilder(VillagerTradeTags.ARMORER_LEVEL_1)
				.add(TagEntry.element(ExampleModVillagerTradesProvider.ARMORER_1_EMERALD_GUIDITE_HELMET.identifier()))
				.add(TagEntry.element(ExampleModVillagerTradesProvider.ARMORER_1_EMERALD_GUIDITE_CHESTPLATE.identifier()))
				.add(TagEntry.element(ExampleModVillagerTradesProvider.ARMORER_1_EMERALD_GUIDITE_LEGGINGS.identifier()))
				.add(TagEntry.element(ExampleModVillagerTradesProvider.ARMORER_1_EMERALD_GUIDITE_BOOTS.identifier()));
	}
}
