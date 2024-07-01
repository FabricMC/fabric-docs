package com.example.docs.item.tool;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import com.example.docs.item.ModItems;

// :::1
public class GuiditeMaterial implements ToolMaterial {
	// Your IDE should override the interface's methods for you, or at least shout at you to do so.
	// :::1
	// :::8
	public static final GuiditeMaterial INSTANCE = new GuiditeMaterial();
	// :::8

	// :::2
	@Override
	public int getDurability() {
		return 455;
	}

	// :::2
	// :::3
	@Override
	public float getMiningSpeedMultiplier() {
		return 5.0F;
	}

	// :::3
	// :::4
	@Override
	public float getAttackDamage() {
		return 1.5F;
	}

	// :::4
	// :::5
	@Override
	public int getMiningLevel() {
		return 3;
	}

	// :::5
	// :::6
	@Override
	public int getEnchantability() {
		return 22;
	}

	// :::6
	// :::7
	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(ModItems.SUSPICIOUS_SUBSTANCE, Items.POTATO);
	}

	// :::7

	// :::1
}
// :::1
