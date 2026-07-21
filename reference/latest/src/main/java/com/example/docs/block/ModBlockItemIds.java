package com.example.docs.block;

import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

// #region first_block
public class ModBlockItemIds {
	// #endregion first_block

	// #region condensed_dirt
	public static final BlockItemId CONDENSED_DIRT = create(
			"condensed_dirt"
	);
	// #endregion condensed_dirt

	// #region condensed_oak_log
	public static final BlockItemId CONDENSED_OAK_LOG = create(
			"condensed_oak_log"
	);
	// #endregion condensed_oak_log

	// #region counter_block
	public static final BlockItemId COUNTER_BLOCK = create(
			"counter_block"
	);
	// #endregion counter_block

	public static final BlockItemId DIRT_CHEST_BLOCK = create(
			"dirt_chest"
	);

	public static final BlockItemId DUPLICATOR_BLOCK = create(
			"duplicator"
	);

	public static final BlockItemId ENGINE_BLOCK = create(
			"engine"
	);

	public static final BlockItemId FRIENDS_BLOCK = create(
			"friends_block"
	);

	public static final BlockItemId PIPE_BLOCK = create(
			"pipe_block"
	);

	// #region prismarine_lamp
	public static final BlockItemId PRISMARINE_LAMP = create(
			"prismarine_lamp"
	);
	// #endregion prismarine_lamp

	public static final BlockItemId RUBY_BLOCK = create(
			"ruby_block"
	);
	public static final BlockItemId RUBY_STAIRS = create(
			"ruby_stairs"
	);
	public static final BlockItemId RUBY_SLAB = create(
			"ruby_slab"
	);
	public static final BlockItemId RUBY_FENCE = create(
			"ruby_fence"
	);

	public static final BlockItemId RUBY_DOOR = create(
			"ruby_door"
	);
	public static final BlockItemId RUBY_TRAPDOOR = create(
			"ruby_trapdoor"
	);

	public static final BlockItemId STEEL_BLOCK = create(
			"steel_block"
	);

	public static final BlockItemId TATER_BLOCK = create(
			"tater"
	);

	public static final BlockItemId VERTICAL_OAK_LOG_SLAB = create(
			"vertical_oak_log_slab"
	);

	// #region waxcap_tinting
	public static final BlockItemId WAXCAP = create(
			"waxcap"
	);
	// #endregion waxcap_tinting

	// #region workstation
	public static final BlockItemId UPGRADING_BLOCK = create("upgrading_block");
	// #endregion workstation

	// #region first_block
	private static BlockItemId create(String name) {
		Identifier id = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name);
		return BlockItemId.create(id, id);
	}
	// #endregion first_block

	// #region first_block
}
// #endregion first_block
