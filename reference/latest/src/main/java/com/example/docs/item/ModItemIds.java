package com.example.docs.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import com.example.docs.ExampleMod;

// #region mod_item_ids_class
public class ModItemIds {
	// #endregion mod_item_ids_class
	// #region acid_bucket
	public static final ResourceKey<Item> ACID_BUCKET = register("acid_bucket");
	// #endregion acid_bucket

	// #region counter
	public static final ResourceKey<Item> COUNTER = register("counter");
	// #endregion counter

	// #region create_armor_items
	public static final ResourceKey<Item> GUIDITE_HELMET = register("guidite_helmet");

	public static final ResourceKey<Item> GUIDITE_CHESTPLATE = register("guidite_chestplate");

	public static final ResourceKey<Item> GUIDITE_LEGGINGS = register("guidite_leggings");

	public static final ResourceKey<Item> GUIDITE_BOOTS = register("guidite_boots");
	// #endregion create_armor_items

	// #region axe
	public static final ResourceKey<Item> GUIDITE_AXE = register("guidite_axe");
	// #endregion axe

	// #region guidite_sword
	public static final ResourceKey<Item> GUIDITE_SWORD = register("guidite_sword");
	// #endregion guidite_sword

	// #region poisonous_apple
	public static final ResourceKey<Item> POISONOUS_APPLE = register("poisonous_apple");
	// #endregion poisonous_apple

	// #region suspicious_substance
	public static final ResourceKey<Item> SUSPICIOUS_SUBSTANCE = register("suspicious_substance");
	// #endregion suspicious_substance

	// #region custom_entity_spawn_egg
	public static final ResourceKey<Item> MINI_GOLEM_SPAWN_EGG = register("mini_golem_spawn_egg");
	// #endregion custom_entity_spawn_egg

	// #region mod_item_ids_class
	public static ResourceKey<Item> register(String name) {
		// Create the item key.
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name));
	}
}
// #endregion mod_item_ids_class
