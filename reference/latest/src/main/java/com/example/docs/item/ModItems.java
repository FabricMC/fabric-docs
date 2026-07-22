package com.example.docs.item;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.fabricmc.fabric.api.registry.FuelValueEvents;

import com.example.docs.ExampleMod;
import com.example.docs.block.ModBlocks;
import com.example.docs.component.ModComponents;
import com.example.docs.debug.TestItem;
import com.example.docs.entity.ModEntityTypes;
import com.example.docs.fluid.ModFluids;
import com.example.docs.item.armor.GuiditeArmorMaterial;
import com.example.docs.item.custom.CounterItem;
import com.example.docs.item.custom.LightningStick;
import com.example.docs.networking.basic.LightningTaterItem;

// #region mod_items_class
public class ModItems {
	// #endregion mod_items_class

	// #region guidite_incorrect_blocks_tag
	public static final TagKey<Block> INCORRECT_FOR_GUIDITE_TOOL = TagKey.create(Registries.BLOCK,
			Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "incorrect_for_guidite_tool"));
	// #endregion guidite_incorrect_blocks_tag

	// #region guidite_tool_material
	public static final ToolMaterial GUIDITE_TOOL_MATERIAL = new ToolMaterial(
			INCORRECT_FOR_GUIDITE_TOOL, // incorrect blocks for drops
			455, // durability
			5.0F, // speed
			1.5F, // attack damage bonus
			22, // enchantment value
			GuiditeArmorMaterial.REPAIRS_GUIDITE_ARMOR // repair items
	);
	// #endregion guidite_tool_material

	// #region create_armor_items
	public static final Item GUIDITE_HELMET = register(
			ModItemIds.GUIDITE_HELMET,
			Item::new,
			new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.HELMET)
					.durability(ArmorType.HELMET.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
	);
	public static final Item GUIDITE_CHESTPLATE = register(
			ModItemIds.GUIDITE_CHESTPLATE,
			Item::new,
			new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.CHESTPLATE)
					.durability(ArmorType.CHESTPLATE.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
	);

	public static final Item GUIDITE_LEGGINGS = register(
			ModItemIds.GUIDITE_LEGGINGS,
			Item::new,
			new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.LEGGINGS)
					.durability(ArmorType.LEGGINGS.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
	);

	public static final Item GUIDITE_BOOTS = register(
			ModItemIds.GUIDITE_BOOTS,
			Item::new,
			new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.BOOTS)
					.durability(ArmorType.BOOTS.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
	);
	// #endregion create_armor_items
	public static final Item LIGHTNING_STICK = register(ModItemIds.LIGHTNING_STICK, LightningStick::new, new Item.Properties());
	// #region guidite_sword
	public static final Item GUIDITE_SWORD = register(
			ModItemIds.GUIDITE_SWORD,
			Item::new,
			new Item.Properties().sword(GUIDITE_TOOL_MATERIAL, 1f, 1f)
	);
	// #endregion guidite_sword
	// #region shield
	public static final Item GUIDITE_SHIELD = register(
					"guidite_shield",
					ShieldItem::new,
					new Item.Properties().durability(336)
									.component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)
									.repairable(GuiditeArmorMaterial.REPAIRS_GUIDITE_ARMOR)
									.equippableUnswappable(EquipmentSlot.OFFHAND)
									.delayedComponent(DataComponents.BLOCKS_ATTACKS, (context) -> new BlocksAttacks(0.25F, 1.0F, List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)), new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F), Optional.of(context.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)), Optional.of(SoundEvents.SHIELD_BLOCK), Optional.of(SoundEvents.SHIELD_BREAK)))
									.component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK)
	);
	// #endregion shield
	// #region counter
	public static final Item COUNTER = register(
			ModItemIds.COUNTER,
			CounterItem::new,
			new Item.Properties()
					// Initialize the click count component with a default value of 0
					.component(ModComponents.CLICK_COUNT_COMPONENT, 0)
	);
	// #endregion counter
	// #region custom_creative_tab
	public static final ResourceKey<CreativeModeTab> CUSTOM_CREATIVE_TAB_KEY = ResourceKey.create(
			BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "creative_tab")
	);
	public static final CreativeModeTab CUSTOM_CREATIVE_TAB = FabricCreativeModeTab.builder()
			.icon(() -> new ItemStack(ModItems.GUIDITE_SWORD))
			.title(Component.translatable("creativeTab.example-mod"))
			.displayItems((params, output) -> {
				output.accept(ModItems.SUSPICIOUS_SUBSTANCE);
				output.accept(ModItems.POISONOUS_APPLE);
				// #endregion custom_creative_tab
				output.accept(ModItems.ACID_BUCKET);
				output.accept(ModItems.COUNTER);
				output.accept(ModItems.LIGHTNING_TATER);
				output.accept(ModItems.TEST_ITEM);
				output.accept(ModItems.GUIDITE_SWORD);
				output.accept(ModItems.GUIDITE_HELMET);
				output.accept(ModItems.GUIDITE_BOOTS);
				output.accept(ModItems.GUIDITE_LEGGINGS);
				output.accept(ModItems.GUIDITE_CHESTPLATE);
				output.accept(ModItems.LIGHTNING_STICK);
				// #region custom_creative_tab

				// The tab builder also accepts Blocks
				output.accept(ModBlocks.CONDENSED_DIRT);
				output.accept(ModBlocks.CONDENSED_OAK_LOG);
				// #endregion custom_creative_tab
				output.accept(ModBlocks.PRISMARINE_LAMP);
				output.accept(ModBlocks.STEEL_BLOCK);
				output.accept(ModBlocks.PIPE_BLOCK);
				output.accept(ModBlocks.DUPLICATOR_BLOCK);
				output.accept(ModBlocks.DIRT_CHEST_BLOCK);
				output.accept(ModBlocks.COUNTER_BLOCK);
				output.accept(ModBlocks.ENGINE_BLOCK);
				output.accept(ModBlocks.FRIENDS_BLOCK);
				output.accept(ModBlocks.RUBY_BLOCK);
				output.accept(ModBlocks.RUBY_STAIRS);
				output.accept(ModBlocks.RUBY_SLAB);
				output.accept(ModBlocks.RUBY_FENCE);
				output.accept(ModBlocks.RUBY_DOOR);
				output.accept(ModBlocks.RUBY_TRAPDOOR);
				output.accept(ModBlocks.VERTICAL_OAK_LOG_SLAB);
				output.accept(ModBlocks.WAXCAP);
				output.accept(ModBlocks.TATER_BLOCK);
				// #region custom_creative_tab

				// And custom ItemStacks
				ItemStack stack = new ItemStack(Items.SEA_PICKLE);
				stack.set(DataComponents.ITEM_NAME, Component.literal("Pickle Rick"));
				stack.set(DataComponents.LORE, new ItemLore(List.of(Component.literal("I'm pickle riiick!!"))));
				output.accept(stack);
			})
			.build();
	// #endregion custom_creative_tab
	// #region custom_food
	public static final Consumable POISON_FOOD_CONSUMABLE_COMPONENT = Consumables.defaultFood()
			// The duration is in ticks, 20 ticks = 1 second
			.onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 6 * 20, 1), 1.0f))
			.build();
	public static final FoodProperties POISON_FOOD_COMPONENT = new FoodProperties.Builder()
			.alwaysEdible()
			.build();
	// #endregion custom_food

	// #region poisonous_apple
	public static final Item POISONOUS_APPLE = register(
			ModItemIds.POISONOUS_APPLE,
			Item::new,
			new Item.Properties().food(POISON_FOOD_COMPONENT, POISON_FOOD_CONSUMABLE_COMPONENT)
	);
	// #endregion poisonous_apple

	// #region suspicious_substance
	public static final Item SUSPICIOUS_SUBSTANCE = register(ModItemIds.SUSPICIOUS_SUBSTANCE, Item::new, new Item.Properties());
	// #endregion suspicious_substance

	// #region custom_entity_spawn_egg
	public static final Item MINI_GOLEM_SPAWN_EGG = register(
			ModItemIds.MINI_GOLEM_SPAWN_EGG,
			SpawnEggItem::new,
			new Item.Properties().spawnEgg(ModEntityTypes.MINI_GOLEM)
	);
	// #endregion custom_entity_spawn_egg

	public static final Item RUBY = register(ModItemIds.RUBY, Item::new, new Item.Properties());

	// #region axe
	public static final Item GUIDITE_AXE = register(
					ModItemIds.GUIDITE_AXE,
					settings -> new AxeItem(GUIDITE_TOOL_MATERIAL, 5.0F, -3.0F, settings),
					new Item.Properties());
	// #endregion axe

	public static final Item LEATHER_GLOVES = register(ModItemIds.LEATHER_GLOVES, Item::new, new Item.Properties());

	public static final Item FLASHLIGHT = register(ModItemIds.FLASHLIGHT, settings -> new Item(settings) {
		@Override
		public InteractionResult use(Level level, Player user, InteractionHand hand) {
			user.startUsingItem(hand);
			return InteractionResult.CONSUME;
		}
	}, new Item.Properties());

	public static final Item BALLOON = register(ModItemIds.BALLOON, Item::new, new Item.Properties());

	public static final Item ENHANCED_HOE = register(ModItemIds.ENHANCED_HOE, settings -> new HoeItem(GUIDITE_TOOL_MATERIAL, -4.0F, 0.0F, settings), new Item.Properties());

	public static final Item DIMENSIONAL_CRYSTAL = register(ModItemIds.DIMENSIONAL_CRYSTAL, Item::new, new Item.Properties());

	public static final Item THROWING_KNIVES = register(ModItemIds.THROWING_KNIVES, Item::new, new Item.Properties().stacksTo(3));

	public static final Item LIGHTNING_TATER = register(ModItemIds.LIGHTNING_TATER, LightningTaterItem::new, new Item.Properties());

	public static final Item TEST_ITEM = register(ModItemIds.TEST_ITEM, TestItem::new, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).component(DataComponents.CUSTOM_NAME, Component.literal("[Use on Stone Block]")));

	// #region mod_items_class
	public static Item register(ResourceKey<Item> itemKey, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
		// Create the item instance.
		Item item = itemFactory.apply(settings.setId(itemKey));

		// Register the item.
		Registry.register(BuiltInRegistries.ITEM, itemKey, item);

		return item;
	}

	// #endregion mod_items_class

	// #region initialize
	public static void initialize() {
		// #endregion initialize
		// #region add_to_creative_tab
		// Get the event for modifying entries in the ingredients group.
		// And register an event handler that adds our suspicious item to the ingredients group.
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
				.register((creativeTab) -> creativeTab.accept(ModItems.SUSPICIOUS_SUBSTANCE));
		// #endregion add_to_creative_tab

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register((creativeTab) -> {
					creativeTab.accept(ModItems.GUIDITE_HELMET);
					creativeTab.accept(ModItems.GUIDITE_BOOTS);
					creativeTab.accept(ModItems.GUIDITE_LEGGINGS);
					creativeTab.accept(ModItems.GUIDITE_CHESTPLATE);
				});

		// #region add_guidite_sword_to_create_tab
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register((creativeTab) -> {
					creativeTab.accept(ModItems.GUIDITE_SWORD);
					creativeTab.accept(ModItems.GUIDITE_AXE);
				});
		// #endregion add_guidite_sword_to_create_tab

		// #region register_creative_tab
		// Register the group.
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, CUSTOM_CREATIVE_TAB);
		// #endregion register_creative_tab

		// #region spawn_egg_creative_tab
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(creativeTab -> {
			creativeTab.accept(ModItems.MINI_GOLEM_SPAWN_EGG);
		});
		// #endregion spawn_egg_creative_tab
		CreativeModeTabEvents.modifyOutputEvent(CUSTOM_CREATIVE_TAB_KEY).register(creativeTab -> {
			creativeTab.accept(ModItems.RUBY);
			creativeTab.accept(ModItems.GUIDITE_AXE);
			creativeTab.accept(ModItems.LEATHER_GLOVES);
			creativeTab.accept(ModItems.FLASHLIGHT);
			creativeTab.accept(ModItems.BALLOON);
			creativeTab.accept(ModItems.ENHANCED_HOE);
			creativeTab.accept(ModItems.DIMENSIONAL_CRYSTAL);
			creativeTab.accept(ModItems.THROWING_KNIVES);
		});

		// #region compostable_item
		// Add the suspicious substance to the composting registry with a 30% chance of increasing the composter's level.
		CompostableRegistry.INSTANCE.add(ModItems.SUSPICIOUS_SUBSTANCE, 0.3f);
		// #endregion compostable_item

		// #region fuel_item
		// Add the suspicious substance to the registry of fuels, with a burn time of 30 seconds.
		// Remember, Minecraft deals with logical based-time using ticks.
		// 20 ticks = 1 second.
		FuelValueEvents.BUILD.register((builder, context) -> {
			builder.add(ModItems.SUSPICIOUS_SUBSTANCE, 30 * 20);
		});
		// #endregion fuel_item
		// #region initialize
	}
	// #endregion initialize

	// #region acid_bucket
	public static final Item ACID_BUCKET = register(
			ModItemIds.ACID_BUCKET,
			props -> new BucketItem(ModFluids.ACID_STILL, props),
			new Item.Properties()
					.craftRemainder(Items.BUCKET)
					.stacksTo(1)
	);
	// #endregion acid_bucket

	// #region mod_items_class
}
// #endregion mod_items_class
