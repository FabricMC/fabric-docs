package com.example.docs.item.armor;

import java.util.Map;

import com.example.docs.FabricDocsReference;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModArmorMaterials {

	// :::guidite_armor_material
	public static final int GUIDITE_DURABILITY_MULTIPLIER = 15;
	public static final RegistryKey<EquipmentAsset> GUIDITE_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(FabricDocsReference.MOD_ID, "guidite_armor"));
	public static final TagKey<Item> REPAIRS_GUIDITE_ARMOR = TagKey.of(Registries.ITEM.getKey(), Identifier.of(FabricDocsReference.MOD_ID, "repairs_guidite_armor"));

	public static final ArmorMaterial GUIDITE_ARMOR_MATERIAL = new ArmorMaterial(
			// Durability of the armor material.
			15,
			// Defense (protection) point values for each armor piece.
			Map.of(
					EquipmentType.HELMET, 3,
					EquipmentType.CHESTPLATE, 8,
					EquipmentType.LEGGINGS, 6,
					EquipmentType.BOOTS, 3
			),
			// "Enchantability". For reference, leather has 15, iron has 9, and diamond has 10.
			5,
			// The sound played when the armor is equipped.
			SoundEvents.ITEM_ARMOR_EQUIP_IRON,
			// Toughness
			0.0F,
			// Knockback resistance
			0.0F,
			// The ingredient(s) used to repair the armor.
			REPAIRS_GUIDITE_ARMOR,
			// The armor material's key.
			GUIDITE_ARMOR_MATERIAL_KEY
	);
	// :::guidite_armor_material

	public static void initialize() {
	}
}
