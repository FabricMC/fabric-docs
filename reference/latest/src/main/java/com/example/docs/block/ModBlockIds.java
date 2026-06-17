package com.example.docs.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

import com.example.docs.ExampleMod;

// #region first_block
public class ModBlockIds {
	// #endregion first_block

	// #region acid
	public static final ResourceKey<Block> ACID = register(
			"acid"
	);
	// #endregion acid

	// #region first_block
	private static ResourceKey<Block> register(String name) {
		Identifier id = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name);
		return ResourceKey.create(Registries.BLOCK, id);
	}
	// #endregion first_block

	// #region first_block
}
// #endregion first_block
