package com.example.docs.item.armor;

import java.util.Map;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;

public class ModArmorMaterials {
	// :::3
	public static final int GUIDITE_DURABILITY_MULTIPLIER = 15;
	// :::3
	// :::2
	public static final ArmorMaterial GUIDITE = new ArmorMaterial(
			// Durability of the armor material.
			15,
			// Defense (protection) point values for each armor piece.
			Map.of(
				EquipmentType.HELMET, 3,
				EquipmentType.CHESTPLATE, 8,
				EquipmentType.LEGGINGS, 6,
				EquipmentType.BOOTS, 3
			),
			// Enchantability. For reference, leather has 15, iron has 9, and diamond has 10.
			5,
			// The sound played when the armor is equipped.
			SoundEvents.ITEM_ARMOR_EQUIP_IRON,
			// Toughness
			0.0F,
			// Knockback resistance
			0.0F,
			// The ingredient(s) used to repair the armor.
			ItemTags.REPAIRS_DIAMOND_ARMOR, // TODO make a tag for this ModItems.SUSPICIOUS_SUBSTANCE
			EquipmentAssetKeys.TURTLE_SCUTE // TODO make a custom key for this
	);
	// :::2

	public static void initialize() { }
}
