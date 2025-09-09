package com.example.docs.item.armor;

import java.util.Map;

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

import com.example.docs.ExampleMod;

public class GuiditeArmorMaterial {
	// :::base_durability
	public static final int BASE_DURABILITY = 15;
	// :::base_durability

	// :::material_key
	public static final RegistryKey<EquipmentAsset> GUIDITE_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(ExampleMod.MOD_ID, "guidite"));
	// :::material_key

	// :::repair_tag
	public static final TagKey<Item> REPAIRS_GUIDITE_ARMOR = TagKey.of(Registries.ITEM.getKey(), Identifier.of(ExampleMod.MOD_ID, "repairs_guidite_armor"));
	// :::repair_tag

	// :::guidite_armor_material
	public static final ArmorMaterial INSTANCE = new ArmorMaterial(
			BASE_DURABILITY,
			Map.of(
					EquipmentType.HELMET, 3,
					EquipmentType.CHESTPLATE, 8,
					EquipmentType.LEGGINGS, 6,
					EquipmentType.BOOTS, 3
			),
			5,
			SoundEvents.ITEM_ARMOR_EQUIP_IRON,
			0.0F,
			0.0F,
			REPAIRS_GUIDITE_ARMOR,
			GUIDITE_ARMOR_MATERIAL_KEY
	);
	// :::guidite_armor_material
}
