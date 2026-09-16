package com.example.docs.enchantment;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import com.example.docs.ExampleMod;

public class ModEnchantments {
	// #region register_enchantment
	public static final ResourceKey<Enchantment> THUNDERING = key("thundering");
	// #endregion register_enchantment
	public static final ResourceKey<Enchantment> REPULSION_CURSE = key("repulsion_curse");

	// #region key_helper
	private static ResourceKey<Enchantment> key(String path) {
		Identifier id = ExampleMod.id(path);
		return ResourceKey.create(Registries.ENCHANTMENT, id);
	}
	// #endregion key_helper
}
