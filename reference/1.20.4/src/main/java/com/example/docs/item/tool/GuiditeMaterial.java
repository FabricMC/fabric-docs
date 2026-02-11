package com.example.docs.item.tool;

import com.example.docs.item.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

// :::1
public class GuiditeMaterial implements Tier {
	// Your IDE should override the interface's methods for you, or at least shout at you to do so.
	// :::1
	// :::8
	public static final GuiditeMaterial INSTANCE = new GuiditeMaterial();
	// :::8

	// :::2
	@Override
	public int getUses() {
		return 455;
	}

	// :::2
	// :::3
	@Override
	public float getSpeed() {
		return 5.0F;
	}

	// :::3
	// :::4
	@Override
	public float getAttackDamageBonus() {
		return 1.5F;
	}

	// :::4
	// :::5
	@Override
	public int getLevel() {
		return 3;
	}

	// :::5
	// :::6
	@Override
	public int getEnchantmentValue() {
		return 22;
	}

	// :::6
	// :::7
	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.of(ModItems.SUSPICIOUS_SUBSTANCE, Items.POTATO);
	}

	// :::7

	// :::1
}
// :::1
