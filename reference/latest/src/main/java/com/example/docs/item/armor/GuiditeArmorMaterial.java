package com.example.docs.item.armor;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import com.example.docs.item.ModItems;

// :::1
public class GuiditeArmorMaterial implements ArmorMaterial {
	//:::1
	// :::_10
	public static final GuiditeArmorMaterial INSTANCE = new GuiditeArmorMaterial();
	// :::_10
	// :::1
	// Base durability values for all the slots.
	// Boots, Leggings, Chestplate, Helmet
	// You shouldn't change these values.
	private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};

	// Protection values for all the slots.
	// For reference, diamond uses 3 for boots, 6 for leggings, 8 for chestplate
	// and 3 for helmet.
	private static final int PROTECTION_BOOTS = 3;
	private static final int PROTECTION_LEGGINGS = 6;
	private static final int PROTECTION_CHESTPLATE = 8;
	private static final int PROTECTION_HELMET = 3;

	// Storing the protection and durability values in an array allows
	// you to quickly get them by slot ID.
	private static final int[] PROTECTION_VALUES = new int[] {
			PROTECTION_BOOTS,
			PROTECTION_LEGGINGS,
			PROTECTION_CHESTPLATE,
			PROTECTION_HELMET
	};

	// :::1
	// :::2
	@Override
	public int getDurability(ArmorItem.Type type) {
		// Replace X with a multiplier that you see fit!
		// For reference, diamond uses a multiplier of 33, whilst
		// leather uses 11.
		return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * 33;
	}

	// :::2
	// :::3
	@Override
	public int getProtection(ArmorItem.Type type) {
		// This will get the protection value for the slot from
		// our array.
		return PROTECTION_VALUES[type.getEquipmentSlot().getEntitySlotId()];
	}

	// :::3
	// :::4
	@Override
	public int getEnchantability() {
		return 5;
	}

	// :::4
	// :::5
	@Override
	public SoundEvent getEquipSound() {
		// Example for Iron Armor
		return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
	}

	// :::5
	// :::6
	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(ModItems.SUSPICIOUS_SUBSTANCE);
	}

	// :::6
	// :::7
	@Override
	public String getName() {
		return "guidite";
	}

	// :::7
	// :::8
	@Override
	public float getToughness() {
		return 2.0F;
	}

	// :::8
	// :::9
	@Override
	public float getKnockbackResistance() {
		// I do not want Guidite Armor to give knockback resistance, therefore it's a zero.
		return 0;
	}

	// :::9
	// :::1
}
// :::1
