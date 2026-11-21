package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import com.example.docs.ExampleMod;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.VillagerTrade;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

// region provider
public class ExampleModVillagerTradesTagProvider extends FabricTagsProvider<VillagerTrade> {
	public ExampleModVillagerTradesTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, Registries.VILLAGER_TRADE, registryLookupFuture);
	}

	// region tags
	@Override
	protected void addTags(HolderLookup.Provider registries) {
		// endregion provider
		builder(VillagerTradeTags.ARMORER_LEVEL_1)
				.add(ExampleModVillagerTradesProvider.ARMORER_1_EMERALD_GUIDITE_HELMET)
				.add(ExampleModVillagerTradesProvider.ARMORER_1_EMERALD_GUIDITE_CHESTPLATE)
				.add(ExampleModVillagerTradesProvider.ARMORER_1_EMERALD_GUIDITE_LEGGINGS)
				.add(ExampleModVillagerTradesProvider.ARMORER_1_EMERALD_GUIDITE_BOOTS);
		// region provider
	}
	//	endregion tags
}
// endregion provider
