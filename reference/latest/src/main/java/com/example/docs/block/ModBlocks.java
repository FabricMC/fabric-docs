package com.example.docs.block;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.references.BlockItemId;
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

import com.example.docs.block.custom.CounterBlock;
import com.example.docs.block.custom.DirtChestBlock;
import com.example.docs.block.custom.DuplicatorBlock;
import com.example.docs.block.custom.EngineBlock;
import com.example.docs.block.custom.FriendsBlock;
import com.example.docs.block.custom.PrismarineLampBlock;
import com.example.docs.block.custom.VerticalSlabBlock;
import com.example.docs.damage.TaterBlock;
import com.example.docs.fluid.ModFluids;

// #region first_block
// #region static_initialization
public class ModBlocks {
	// #endregion static_initialization
	// #endregion first_block

	// #region acid
	public static final Block ACID = register(
			ModBlockIds.ACID,
			(props) -> new LiquidBlock(ModFluids.ACID_STILL, props),
			BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
	);
	// #endregion acid

	// #region condensed_dirt
	public static final Block CONDENSED_DIRT = register(
			ModBlockItemIds.CONDENSED_DIRT,
			Block::new,
			BlockBehaviour.Properties.of().sound(SoundType.GRASS)
	);
	// #endregion condensed_dirt

	// #region condensed_oak_log
	public static final Block CONDENSED_OAK_LOG = register(
			ModBlockItemIds.CONDENSED_OAK_LOG,
			RotatedPillarBlock::new,
			BlockBehaviour.Properties.of().sound(SoundType.WOOD)
	);
	// #endregion condensed_oak_log

	// #region counter_block
	public static final Block COUNTER_BLOCK = register(
			ModBlockItemIds.COUNTER_BLOCK,
			CounterBlock::new,
			BlockBehaviour.Properties.of()
	);
	// #endregion counter_block

	public static final Block DIRT_CHEST_BLOCK = register(
			ModBlockItemIds.DIRT_CHEST_BLOCK, DirtChestBlock::new, BlockBehaviour.Properties.of()
	);

	public static final Block DUPLICATOR_BLOCK = register(
			ModBlockItemIds.DUPLICATOR_BLOCK, DuplicatorBlock::new, BlockBehaviour.Properties.of()
	);

	public static final Block ENGINE_BLOCK = register(
			ModBlockItemIds.ENGINE_BLOCK,
			EngineBlock::new,
			BlockBehaviour.Properties.of()
	);

	public static final Block FRIENDS_BLOCK = register(
			ModBlockItemIds.FRIENDS_BLOCK, FriendsBlock::new, BlockBehaviour.Properties.of()
	);

	public static final Block PIPE_BLOCK = register(
			ModBlockItemIds.PIPE_BLOCK, Block::new, BlockBehaviour.Properties.of()
	);

	// #region prismarine_lamp
	public static final Block PRISMARINE_LAMP = register(
			ModBlockItemIds.PRISMARINE_LAMP,
			PrismarineLampBlock::new,
			BlockBehaviour.Properties.of()
					.sound(SoundType.LANTERN)
					.lightLevel(PrismarineLampBlock::getLuminance)
	);
	// #endregion prismarine_lamp

	public static final Block RUBY_BLOCK = register(
			ModBlockItemIds.PRISMARINE_LAMP, Block::new, BlockBehaviour.Properties.of()
	);
	public static final Block RUBY_STAIRS = register(
			ModBlockItemIds.RUBY_STAIRS, settings -> new StairBlock(RUBY_BLOCK.defaultBlockState(), settings), BlockBehaviour.Properties.of()
	);
	public static final Block RUBY_SLAB = register(
			ModBlockItemIds.RUBY_SLAB, SlabBlock::new, BlockBehaviour.Properties.of()
	);
	public static final Block RUBY_FENCE = register(
			ModBlockItemIds.RUBY_FENCE, FenceBlock::new, BlockBehaviour.Properties.of()
	);

	public static final Block RUBY_DOOR = register(
			ModBlockItemIds.RUBY_DOOR, settings -> new DoorBlock(BlockSetType.STONE, settings), BlockBehaviour.Properties.of()
	);
	public static final Block RUBY_TRAPDOOR = register(
			ModBlockItemIds.RUBY_TRAPDOOR, settings -> new TrapDoorBlock(BlockSetType.STONE, settings), BlockBehaviour.Properties.of()
	);

	// #region family_declaration
	public static final BlockFamily RUBY_FAMILY =
			new BlockFamily.Builder(ModBlocks.RUBY_BLOCK)
					.stairs(ModBlocks.RUBY_STAIRS)
					.slab(ModBlocks.RUBY_SLAB)
					.fence(ModBlocks.RUBY_FENCE)
					.getFamily();
	// #endregion family_declaration

	public static final Block STEEL_BLOCK = register(
			ModBlockItemIds.STEEL_BLOCK, RotatedPillarBlock::new, BlockBehaviour.Properties.of()
	);

	public static final Block TATER_BLOCK = register(
			ModBlockItemIds.TATER_BLOCK, TaterBlock::new, BlockBehaviour.Properties.of()
	);

	public static final Block VERTICAL_OAK_LOG_SLAB = register(
			ModBlockItemIds.VERTICAL_OAK_LOG_SLAB, VerticalSlabBlock::new, BlockBehaviour.Properties.of()
	);

	// #region waxcap_tinting
	public static final Block WAXCAP = register(
			ModBlockItemIds.WAXCAP,
			Block::new,
			BlockBehaviour.Properties.of()
					.noCollision()
					.instabreak()
					.offsetType(BlockBehaviour.OffsetType.XYZ)
	);
	// #endregion waxcap_tinting

	// #region first_block
	private static Block register(ResourceKey<Block> id, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
		// Create the block instance
		Block block = blockFactory.apply(properties.setId(id));
		return Registry.register(BuiltInRegistries.BLOCK, id, block);
	}

	private static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
		// Create the block instance
		Block block = register(id.block(), blockFactory, properties);

		// Create the block item instance
		BlockItem blockItem = new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix().setId(id.item()));
		Registry.register(BuiltInRegistries.ITEM, id.item(), blockItem);

		return block;
	}
	// #endregion first_block

	// #region static_initialization
	public static void initialize() {
		// #endregion static_initialization
		setupItemGroups();
		// #region static_initialization
	}
	// #endregion static_initialization

	public static void setupItemGroups() {
		// #region add_to_creative_tab
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register((creativeTab) -> {
			creativeTab.accept(ModBlocks.CONDENSED_DIRT.asItem());
		});
		// #endregion add_to_creative_tab
	}

	// #region first_block
	// #region static_initialization
}
// #endregion static_initialization
// #endregion first_block
