package com.example.docs.item.armor;

import java.util.Map;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import com.example.docs.ExampleMod;

public class GuiditeArmorMaterial {
	// #region material_key
	public static final ResourceKey<EquipmentAsset> GUIDITE_ARMOR_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ExampleMod.id("guidite"));
	// #endregion material_key

	// #region repair_tag
	public static final TagKey<Item> REPAIRS_GUIDITE_ARMOR = TagKey.create(BuiltInRegistries.ITEM.key(), ExampleMod.id("repairs_guidite_armor"));
	// #endregion repair_tag

	// #region guidite_armor_material
	public static final ArmorMaterial INSTANCE = new ArmorMaterial(
			15,
			Map.of(
					ArmorType.HELMET, 3,
					ArmorType.CHESTPLATE, 8,
					ArmorType.LEGGINGS, 6,
					ArmorType.BOOTS, 3,
					ArmorType.BODY, 8
			),
			5,
			SoundEvents.ARMOR_EQUIP_IRON,
			0.0F,
			0.0F,
			REPAIRS_GUIDITE_ARMOR,
			GUIDITE_ARMOR_MATERIAL_KEY
	);
	// #endregion guidite_armor_material
}
