package com.example.docs.block;

import com.example.docs.block.custom.VerticalSlabBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import com.example.docs.FabricDocsReference;
import com.example.docs.block.custom.CounterBlock;
import com.example.docs.block.custom.EngineBlock;
import com.example.docs.block.custom.PrismarineLampBlock;
import com.example.docs.item.ModItems;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

// :::1
public class ModBlocks {
	// :::1

	// :::2
	public static final RegistryKey<Block> CONDENSED_DIRT_KEY = RegistryKey.of(
			RegistryKeys.BLOCK,
			Identifier.of(FabricDocsReference.MOD_ID, "condensed_dirt")
	);

	public static final Block CONDENSED_DIRT = register(
			new Block(AbstractBlock.Settings.create().registryKey(CONDENSED_DIRT_KEY).sounds(BlockSoundGroup.GRASS)),
			CONDENSED_DIRT_KEY,
			true
	);

	// :::2
	// :::3
	public static final RegistryKey<Block> CONDENSED_OAK_LOG_KEY = RegistryKey.of(
			RegistryKeys.BLOCK,
			Identifier.of(FabricDocsReference.MOD_ID, "condensed_oak_log")
	);

	public static final Block CONDENSED_OAK_LOG = register(
			new PillarBlock(
					AbstractBlock.Settings.create()
							.registryKey(CONDENSED_OAK_LOG_KEY)
							.sounds(BlockSoundGroup.WOOD)
			), CONDENSED_OAK_LOG_KEY, true
	);

	// :::3
	// :::4
	public static final RegistryKey<Block> PRISMARINE_LAMP_KEY = RegistryKey.of(
			RegistryKeys.BLOCK,
			Identifier.of(FabricDocsReference.MOD_ID, "prismarine_lamp")
	);
	public static final Block PRISMARINE_LAMP = register(
			new PrismarineLampBlock(
					AbstractBlock.Settings.create()
							.registryKey(PRISMARINE_LAMP_KEY)
							.sounds(BlockSoundGroup.LANTERN)
							.luminance(PrismarineLampBlock::getLuminance)
			), PRISMARINE_LAMP_KEY, true
	);
	// :::4
	public static final RegistryKey<Block> ENGINE_BLOCK_KEY = RegistryKey.of(
			RegistryKeys.BLOCK,
			Identifier.of(FabricDocsReference.MOD_ID, "engine")
	);
	public static final Block ENGINE_BLOCK = register(
			new EngineBlock(AbstractBlock.Settings.create().registryKey(ENGINE_BLOCK_KEY)), ENGINE_BLOCK_KEY, true
	);

	// :::5
	public static final RegistryKey<Block> COUNTER_BLOCK_KEY = RegistryKey.of(
			RegistryKeys.BLOCK,
			Identifier.of(FabricDocsReference.MOD_ID, "counter_block")
	);
	public static final Block COUNTER_BLOCK = register(
			new CounterBlock(AbstractBlock.Settings.create().registryKey(COUNTER_BLOCK_KEY)), COUNTER_BLOCK_KEY, true
	);
	// :::5

	public static final Block STEEL_BLOCK = registerBlock(
			"steel_block", PillarBlock::new, AbstractBlock.Settings.create()
	);
	public static final Block PIPE_BLOCK = registerBlock(
			"pipe_block", Block::new, AbstractBlock.Settings.create()
	);

	public static final Block RUBY_BLOCK = registerBlock(
			"ruby_block", Block::new, AbstractBlock.Settings.create()
	);
	public static final Block RUBY_STAIRS = registerBlock(
			"ruby_stairs", settings -> new StairsBlock(RUBY_BLOCK.getDefaultState(), settings), AbstractBlock.Settings.create()
	);
	public static final Block RUBY_SLAB = registerBlock(
			"ruby_slab", SlabBlock::new, AbstractBlock.Settings.create()
	);
	public static final Block RUBY_FENCE = registerBlock(
			"ruby_fence", FenceBlock::new, AbstractBlock.Settings.create()
	);

	public static final Block RUBY_DOOR = registerBlock(
			"ruby_door", settings -> new DoorBlock(BlockSetType.STONE, settings), AbstractBlock.Settings.create()
	);
	public static final Block RUBY_TRAPDOOR = registerBlock(
			"ruby_trapdoor", settings -> new TrapdoorBlock(BlockSetType.STONE, settings), AbstractBlock.Settings.create()
	);

	public static final Block VERTICAL_OAK_LOG_SLAB = registerBlock(
			"vertical_oak_log_slab", VerticalSlabBlock::new, AbstractBlock.Settings.create());

	// :::datagen-model:family-declaration
	public static final BlockFamily RUBY_FAMILY =
			new BlockFamily.Builder(ModBlocks.RUBY_BLOCK)
			.stairs(ModBlocks.RUBY_STAIRS)
			.slab(ModBlocks.RUBY_SLAB)
			.fence(ModBlocks.RUBY_FENCE)
			.build();
	// :::datagen-model:family-declaration

	// :::1
	public static Block register(Block block, RegistryKey<Block> blockKey, boolean shouldRegisterItem) {
		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			// Items need to be registered with a different type of registry key, but the ID
			// can be the same.
			RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());

			BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
			Registry.register(Registries.ITEM, itemKey, blockItem);
		}

		return Registry.register(Registries.BLOCK, blockKey, block);
	}

	// :::1

	/** Helper methods for registering blocks (Fellteros). <br>
	* Block would look like this:
	 * <blockquote><pre>
	 *     public static final Block TEST = registerBlock("test", Block::new, AbstractBlock.Settings.create());
	 * </pre></blockquote>
	 * */
	private static Block registerBlock(String name, @NotNull Function<AbstractBlock.Settings, Block> function, AbstractBlock.@NotNull Settings settings) {
		Block block = function.apply(settings.registryKey(keyOfBlock(name)));
		Registry.register(Registries.ITEM, Identifier.of(FabricDocsReference.MOD_ID, name), new BlockItem(block, new Item.Settings().useBlockPrefixedTranslationKey().registryKey(keyOfItem(name))));
		return Registry.register(Registries.BLOCK, keyOfBlock(name), block);
	}
	private static RegistryKey<Item> keyOfItem(String name) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, name));
	}
	private static RegistryKey<Block> keyOfBlock(String name) {
		return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FabricDocsReference.MOD_ID, name));
	}


	public static void initialize() {
		setupItemGroups();
	}

	public static void setupItemGroups() {
		// :::6
		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.add(ModBlocks.CONDENSED_DIRT.asItem());
		});
		// :::6

		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.add(ModBlocks.CONDENSED_OAK_LOG.asItem());
			itemGroup.add(ModBlocks.PRISMARINE_LAMP.asItem());
			itemGroup.add(ModBlocks.COUNTER_BLOCK.asItem());
			itemGroup.add(ModBlocks.ENGINE_BLOCK.asItem());
			itemGroup.add(RUBY_BLOCK);
			itemGroup.add(RUBY_STAIRS);
			itemGroup.add(RUBY_SLAB);
			itemGroup.add(RUBY_FENCE);
			itemGroup.add(RUBY_DOOR);
			itemGroup.add(RUBY_TRAPDOOR);
			itemGroup.add(VERTICAL_OAK_LOG_SLAB);
		});
	}

	// :::1
}
// :::1
