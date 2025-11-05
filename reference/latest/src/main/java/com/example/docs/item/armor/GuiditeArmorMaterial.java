package com.example.docs.item.armor;

import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import com.example.docs.ExampleMod;

public class GuiditeArmorMaterial {
	// :::base_durability
	public static final int BASE_DURABILITY = 15;
	// :::base_durability

	// :::material_key
	public static final ResourceKey<EquipmentAsset> GUIDITE_ARMOR_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "guidite"));
	// :::material_key

	// :::repair_tag
	public static final TagKey<Item> REPAIRS_GUIDITE_ARMOR = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "repairs_guidite_armor"));
	// :::repair_tag

	// :::guidite_armor_material
	public static final ArmorMaterial INSTANCE = new ArmorMaterial(
			BASE_DURABILITY,
			Map.of(
					ArmorType.HELMET, 3,
					ArmorType.CHESTPLATE, 8,
					ArmorType.LEGGINGS, 6,
					ArmorType.BOOTS, 3
			),
			5,
			SoundEvents.ARMOR_EQUIP_IRON,
			0.0F,
			0.0F,
			REPAIRS_GUIDITE_ARMOR,
			GUIDITE_ARMOR_MATERIAL_KEY
	);
	// :::guidite_armor_material
}
