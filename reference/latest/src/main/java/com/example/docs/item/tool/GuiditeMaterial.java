package com.example.docs.item.tool;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

// :::1
public class GuiditeMaterial {
	// :::1
	public static final ToolMaterial INSTANCE = new ToolMaterial(
			BlockTags.INCORRECT_FOR_WOODEN_TOOL,
			// Durability
			455,
			// Mining speed multiplier
			5.0F,
			// Attack damage bonus
			1.5F,
			// Enchantability
			22,
			// Repair tag
			ItemTags.REPAIRS_DIAMOND_ARMOR // TODO make a tag for this ModItems.SUSPICIOUS_SUBSTANCE
	);
	// :::1
}
// :::1
