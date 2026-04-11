package com.example.docs.block;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

import com.example.docs.ExampleMod;
import com.example.docs.block.custom.CounterBlock;
import com.example.docs.block.custom.DirtChestBlock;
import com.example.docs.block.custom.DuplicatorBlock;
import com.example.docs.block.custom.EngineBlock;
import com.example.docs.block.custom.PrismarineLampBlock;
import com.example.docs.block.custom.VerticalSlabBlock;
import com.example.docs.damage.TaterBlock;
import com.example.docs.fluid.ModFluids;

// #region first-block
// #region static-initialization
public class ModBlocks {
	// #endregion static-initialization
	// #endregion first-block

	// #region acid
	public static final Block ACID = register(
			"acid",
			(props) -> new LiquidBlock(ModFluids.ACID_STILL, props),
			BlockBehaviour.Properties.ofFullCopy(Blocks.WATER),
			false
	);
	// #endregion acid

	// #region condensed-dirt
	public static final Block CONDENSED_DIRT = register(
			"condensed_dirt",
			Block::new,
			BlockBehaviour.Properties.of().sound(SoundType.GRASS),
			true
	);
	// #endregion condensed-dirt

	// #region condensed-oak-log
	public static final Block CONDENSED_OAK_LOG = register(
			"condensed_oak_log",
			RotatedPillarBlock::new,
			BlockBehaviour.Properties.of().sound(SoundType.WOOD),
			true
	);
	// #endregion condensed-oak-log

	// #region prismarine-lamp
	public static final Block PRISMARINE_LAMP = register(
			"prismarine_lamp",
			PrismarineLampBlock::new,
			BlockBehaviour.Properties.of()
					.sound(SoundType.LANTERN)
					.lightLevel(PrismarineLampBlock::getLuminance),
			true
	);
	// #endregion prismarine-lamp

	public static final ResourceKey<Block> ENGINE_BLOCK_KEY = ResourceKey.create(
			Registries.BLOCK,
			Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "engine")
	);
	public static final Block ENGINE_BLOCK = register(
			"engine",
			EngineBlock::new,
			BlockBehaviour.Properties.of().setId(ENGINE_BLOCK_KEY),
			true
	);

	// #region counter-block
	public static final Block COUNTER_BLOCK = register(
			"counter_block",
			CounterBlock::new,
			BlockBehaviour.Properties.of(),
			true
	);
	// #endregion counter-block

	public static final Block STEEL_BLOCK = register(
			"steel_block", RotatedPillarBlock::new, BlockBehaviour.Properties.of(), true
	);
	public static final Block PIPE_BLOCK = register(
			"pipe_block", Block::new, BlockBehaviour.Properties.of(), true
	);

	public static final Block RUBY_BLOCK = register(
			"ruby_block", Block::new, BlockBehaviour.Properties.of(), true
	);
	public static final Block RUBY_STAIRS = register(
			"ruby_stairs", settings -> new StairBlock(RUBY_BLOCK.defaultBlockState(), settings), BlockBehaviour.Properties.of(), true
	);
	public static final Block RUBY_SLAB = register(
			"ruby_slab", SlabBlock::new, BlockBehaviour.Properties.of(), true
	);
	public static final Block RUBY_FENCE = register(
			"ruby_fence", FenceBlock::new, BlockBehaviour.Properties.of(), true
	);

	public static final Block RUBY_DOOR = register(
			"ruby_door", settings -> new DoorBlock(BlockSetType.STONE, settings), BlockBehaviour.Properties.of(), true
	);
	public static final Block RUBY_TRAPDOOR = register(
			"ruby_trapdoor", settings -> new TrapDoorBlock(BlockSetType.STONE, settings), BlockBehaviour.Properties.of(), true
	);

	public static final Block VERTICAL_OAK_LOG_SLAB = register(
			"vertical_oak_log_slab", VerticalSlabBlock::new, BlockBehaviour.Properties.of(), true
	);

	// #region family-declaration
	public static final BlockFamily RUBY_FAMILY =
			new BlockFamily.Builder(ModBlocks.RUBY_BLOCK)
			.stairs(ModBlocks.RUBY_STAIRS)
			.slab(ModBlocks.RUBY_SLAB)
			.fence(ModBlocks.RUBY_FENCE)
			.getFamily();
	// #endregion family-declaration

	public static final Block DUPLICATOR_BLOCK = register(
			"duplicator", DuplicatorBlock::new, BlockBehaviour.Properties.of(), true
	);

	public static final Block DIRT_CHEST_BLOCK = register(
			"dirt_chest", DirtChestBlock::new, BlockBehaviour.Properties.of(), true
	);

	public static final Block TATER_BLOCK = register(
					"tater", TaterBlock::new, BlockBehaviour.Properties.of(), true
	);

	// #region waxcap-tinting
	public static final Block WAXCAP = register(
			"waxcap",
			Block::new,
			BlockBehaviour.Properties.of()
					.noCollision()
					.instabreak()
					.offsetType(BlockBehaviour.OffsetType.XYZ),
			true
	);
	// #endregion waxcap-tinting

	// #region first-block
	private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
		// Create a registry key for the block
		ResourceKey<Block> blockKey = keyOfBlock(name);
		// Create the block instance
		Block block = blockFactory.apply(settings.setId(blockKey));

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			// Items need to be registered with a different type of registry key, but the ID
			// can be the same.
			ResourceKey<Item> itemKey = keyOfItem(name);

			BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
			Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
		}

		return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
	}

	private static ResourceKey<Block> keyOfBlock(String name) {
		return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name));
	}

	private static ResourceKey<Item> keyOfItem(String name) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name));
	}
	// #endregion first-block

	// #region static-initialization
	public static void initialize() {
		// #endregion static-initialization
		setupItemGroups();
		// #region static-initialization
	}
	// #endregion static-initialization

	public static void setupItemGroups() {
		// #region add-to-creative-tab
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register((creativeTab) -> {
			creativeTab.accept(ModBlocks.CONDENSED_DIRT.asItem());
		});
		// #endregion add-to-creative-tab
	}

	// #region first-block
	// #region static-initialization
}
// #endregion static-initialization
// #endregion first-block
