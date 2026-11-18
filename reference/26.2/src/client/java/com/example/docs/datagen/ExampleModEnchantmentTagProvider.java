package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import com.example.docs.enchantment.ModEnchantments;

// #region provider
public class ExampleModEnchantmentTagProvider extends FabricTagsProvider<Enchantment> {
	public ExampleModEnchantmentTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.ENCHANTMENT, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		// ...
		// #endregion provider
		// #region non_treasure_tag
		builder(EnchantmentTags.NON_TREASURE).add(ModEnchantments.THUNDERING);
		// #endregion non_treasure_tag
		// #region curse_tag
		builder(EnchantmentTags.CURSE).add(ModEnchantments.REPULSION_CURSE);
		// #endregion curse_tag
		// #region provider
	}
}
// #endregion provider
