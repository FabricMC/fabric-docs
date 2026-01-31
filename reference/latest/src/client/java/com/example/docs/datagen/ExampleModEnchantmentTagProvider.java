package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import com.example.docs.enchantment.ModEnchantments;

// :::provider
public class ExampleModEnchantmentTagProvider extends FabricTagProvider<Enchantment> {
	public ExampleModEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.ENCHANTMENT, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		// ...
		// :::provider
		// :::non-treasure-tag
		builder(EnchantmentTags.NON_TREASURE).add(ModEnchantments.THUNDERING);
		// :::non-treasure-tag
		// :::curse-tag
		builder(EnchantmentTags.CURSE).add(ModEnchantments.REBOUNDING_CURSE);
		// :::curse-tag
		// :::provider
	}
	// :::provider
}
// :::provider
