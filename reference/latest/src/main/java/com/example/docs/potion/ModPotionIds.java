package com.example.docs.potion;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.alchemy.Potion;

import com.example.docs.ExampleMod;

// #region mod_potion_ids_class
public class ModPotionIds {
	public static final ResourceKey<Potion> TATER_POTION = create("tater");

	private static ResourceKey<Potion> create(String name) {
		Identifier id = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name);
		return ResourceKey.create(Registries.POTION, id);
	}
}
// #endregion mod_potion_ids_class
