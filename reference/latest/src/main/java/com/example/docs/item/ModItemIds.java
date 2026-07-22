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
	public static final ResourceKey<Item> ACID_BUCKET = create("acid_bucket");
	// #endregion acid_bucket

	// #region counter
	public static final ResourceKey<Item> COUNTER = create("counter");
	// #endregion counter

	// #region create_armor_items
	public static final ResourceKey<Item> GUIDITE_HELMET = create("guidite_helmet");

	public static final ResourceKey<Item> GUIDITE_CHESTPLATE = create("guidite_chestplate");

	public static final ResourceKey<Item> GUIDITE_LEGGINGS = create("guidite_leggings");

	public static final ResourceKey<Item> GUIDITE_BOOTS = create("guidite_boots");
	// #endregion create_armor_items

	// #region axe
	public static final ResourceKey<Item> GUIDITE_AXE = create("guidite_axe");
	// #endregion axe

	// #region guidite_sword
	public static final ResourceKey<Item> GUIDITE_SWORD = create("guidite_sword");
	// #endregion guidite_sword

	// #region poisonous_apple
	public static final ResourceKey<Item> POISONOUS_APPLE = create("poisonous_apple");
	// #endregion poisonous_apple

	// #region suspicious_substance
	public static final ResourceKey<Item> SUSPICIOUS_SUBSTANCE = create("suspicious_substance");
	// #endregion suspicious_substance

	// #region custom_entity_spawn_egg
	public static final ResourceKey<Item> MINI_GOLEM_SPAWN_EGG = create("mini_golem_spawn_egg");
	// #endregion custom_entity_spawn_egg

	public static final ResourceKey<Item> LIGHTNING_STICK = create("lightning_stick");
	public static final ResourceKey<Item> RUBY = create("ruby");
	public static final ResourceKey<Item> LEATHER_GLOVES = create("leather_gloves");
	public static final ResourceKey<Item> FLASHLIGHT = create("flashlight");
	public static final ResourceKey<Item> BALLOON = create("balloon");
	public static final ResourceKey<Item> ENHANCED_HOE = create("enhanced_hoe");
	public static final ResourceKey<Item> DIMENSIONAL_CRYSTAL = create("dimensional_crystal");
	public static final ResourceKey<Item> THROWING_KNIVES = create("throwing_knives");
	public static final ResourceKey<Item> LIGHTNING_TATER = create("lightning_tater");
	public static final ResourceKey<Item> TEST_ITEM = create("test_item");

	// #region mod_item_ids_class
	public static ResourceKey<Item> create(String name) {
		// Create the item key.
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name));
	}
}
// #endregion mod_item_ids_class
