package com.example.docs.enchantment;

import net.fabricmc.api.ModInitializer;

public class FabricDocsReferenceEnchantments implements ModInitializer {
	@Override
	public void onInitialize() {
		ModEnchantmentEffects.registerModEnchantmentEffects();
	}
}
