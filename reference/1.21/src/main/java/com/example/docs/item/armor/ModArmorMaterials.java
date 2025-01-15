package com.example.docs.item.armor;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import com.example.docs.FabricDocsReference;
import com.example.docs.item.ModItems;

public class ModArmorMaterials {
	// :::3
	public static final int GUIDITE_DURABILITY_MULTIPLIER = 15;
	// :::3
	// :::2
	public static final RegistryEntry<ArmorMaterial> GUIDITE = registerMaterial("guidite",
			// Defense (protection) point values for each armor piece.
			Map.of(
				ArmorItem.Type.HELMET, 3,
				ArmorItem.Type.CHESTPLATE, 8,
				ArmorItem.Type.LEGGINGS, 6,
				ArmorItem.Type.BOOTS, 3
			),
			// Enchantability. For reference, leather has 15, iron has 9, and diamond has 10.
			5,
			// The sound played when the armor is equipped.
			SoundEvents.ITEM_ARMOR_EQUIP_IRON,
			// The ingredient(s) used to repair the armor.
			() -> Ingredient.ofItems(ModItems.SUSPICIOUS_SUBSTANCE),
			0.0F,
			0.0F,
			// Guidite is NOT dyeable, so we will pass false.
			false);
	// :::2

	// :::1
	public static RegistryEntry<ArmorMaterial> registerMaterial(String id, Map<ArmorItem.Type, Integer> defensePoints, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredientSupplier, float toughness, float knockbackResistance, boolean dyeable) {
		// Get the supported layers for the armor material
		List<ArmorMaterial.Layer> layers = List.of(
			// The ID of the texture layer, the suffix, and whether the layer is dyeable.
			// We can just pass the armor material ID as the texture layer ID.
			// We have no need for a suffix, so we'll pass an empty string.
			// We'll pass the dyeable boolean we received as the dyeable parameter.
			new ArmorMaterial.Layer(Identifier.of(FabricDocsReference.MOD_ID, id), "", dyeable)
		);

		ArmorMaterial material = new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredientSupplier, layers, toughness, knockbackResistance);
		// Register the material within the ArmorMaterials registry.
		material = Registry.register(Registries.ARMOR_MATERIAL, Identifier.of(FabricDocsReference.MOD_ID, id), material);

		// The majority of the time, you'll want the RegistryEntry of the material - especially for the ArmorItem constructor.
		return RegistryEntry.of(material);
	}

	// :::1
	public static void initialize() { }
}
