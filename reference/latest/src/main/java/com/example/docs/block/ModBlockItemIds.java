package com.example.docs.block;

import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

// #region first_block
public class ModBlockItemIds {
	// #endregion first_block

	// #region condensed_dirt
	public static final BlockItemId CONDENSED_DIRT = register(
			"condensed_dirt"
	);
	// #endregion condensed_dirt

	// #region condensed_oak_log
	public static final BlockItemId CONDENSED_OAK_LOG = register(
			"condensed_oak_log"
	);
	// #endregion condensed_oak_log

	// #region counter_block
	public static final BlockItemId COUNTER_BLOCK = register(
			"counter_block"
	);
	// #endregion counter_block

	public static final BlockItemId DIRT_CHEST_BLOCK = register(
			"dirt_chest"
	);

	public static final BlockItemId DUPLICATOR_BLOCK = register(
			"duplicator"
	);

	public static final BlockItemId ENGINE_BLOCK = register(
			"engine"
	);

	public static final BlockItemId FRIENDS_BLOCK = register(
			"friends_block"
	);

	public static final BlockItemId PIPE_BLOCK = register(
			"pipe_block"
	);

	// #region prismarine_lamp
	public static final BlockItemId PRISMARINE_LAMP = register(
			"prismarine_lamp"
	);
	// #endregion prismarine_lamp

	public static final BlockItemId RUBY_BLOCK = register(
			"ruby_block"
	);
	public static final BlockItemId RUBY_STAIRS = register(
			"ruby_stairs"
	);
	public static final BlockItemId RUBY_SLAB = register(
			"ruby_slab"
	);
	public static final BlockItemId RUBY_FENCE = register(
			"ruby_fence"
	);

	public static final BlockItemId RUBY_DOOR = register(
			"ruby_door"
	);
	public static final BlockItemId RUBY_TRAPDOOR = register(
			"ruby_trapdoor"
	);

	public static final BlockItemId STEEL_BLOCK = register(
			"steel_block"
	);

	public static final BlockItemId TATER_BLOCK = register(
			"tater"
	);

	public static final BlockItemId VERTICAL_OAK_LOG_SLAB = register(
			"vertical_oak_log_slab"
	);

	// #region waxcap_tinting
	public static final BlockItemId WAXCAP = register(
			"waxcap"
	);
	// #endregion waxcap_tinting

	// #region first_block
	private static BlockItemId register(String name) {
		Identifier id = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name);
		return BlockItemId.create(id, id);
	}
	// #endregion first_block

	// #region first_block
}
// #endregion first_block
