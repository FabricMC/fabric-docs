package com.example.docs.item;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorType;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;

import com.example.docs.ExampleMod;
import com.example.docs.component.ModComponents;
import com.example.docs.item.armor.GuiditeArmorMaterial;
import com.example.docs.item.custom.CounterItem;
import com.example.docs.item.custom.LightningStick;

// :::1
public class ModItems {
	// :::1

	// :::guidite_tool_material
	public static final ToolMaterial GUIDITE_TOOL_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_WOODEN_TOOL,
			455,
			5.0F,
			1.5F,
			22,
			GuiditeArmorMaterial.REPAIRS_GUIDITE_ARMOR
	);
	// :::guidite_tool_material

	// :::6
	public static final Item GUIDITE_HELMET = register(
			"guidite_helmet",
			Item::new,
			new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.HELMET)
					.durability(ArmorType.HELMET.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
	);
	public static final Item GUIDITE_CHESTPLATE = register("guidite_chestplate",
			Item::new,
			new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.CHESTPLATE)
					.durability(ArmorType.CHESTPLATE.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
	);

	public static final Item GUIDITE_LEGGINGS = register(
			"guidite_leggings",
			Item::new,
			new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.LEGGINGS)
					.durability(ArmorType.LEGGINGS.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
	);

	public static final Item GUIDITE_BOOTS = register(
			"guidite_boots",
			Item::new,
			new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.BOOTS)
					.durability(ArmorType.BOOTS.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
	);
	// :::6
	public static final Item LIGHTNING_STICK = register("lightning_stick", LightningStick::new, new Item.Properties());
	// :::7
	public static final Item GUIDITE_SWORD = register(
			"guidite_sword",
			Item::new,
			new Item.Properties().sword(GUIDITE_TOOL_MATERIAL, 1f, 1f)
	);
	// :::7
	// :::_13
	public static final Item COUNTER = register(
			"counter",
			CounterItem::new,
			new Item.Properties()
					// Initialize the click count component with a default value of 0
					.component(ModComponents.CLICK_COUNT_COMPONENT, 0)
	);
	// :::_13
	// :::9
	public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "item_group"));
	public static final CreativeModeTab CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.GUIDITE_SWORD))
			.title(Component.translatable("itemGroup.example-mod"))
			.build();
	// :::9
	// :::5
	public static final Consumable POISON_FOOD_CONSUMABLE_COMPONENT = Consumables.defaultFood()
			// The duration is in ticks, 20 ticks = 1 second
			.onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 6 * 20, 1), 1.0f))
			.build();
	public static final FoodProperties POISON_FOOD_COMPONENT = new FoodProperties.Builder()
			.alwaysEdible()
			.build();
	// :::5

	// :::poisonous_apple
	public static final Item POISONOUS_APPLE = register(
			"poisonous_apple",
			Item::new,
			new Item.Properties().food(POISON_FOOD_COMPONENT, POISON_FOOD_CONSUMABLE_COMPONENT)
	);
	// :::poisonous_apple

	// :::2
	public static final Item SUSPICIOUS_SUBSTANCE = register("suspicious_substance", Item::new, new Item.Properties());
	// :::2

	//generated
	public static final Item RUBY = register("ruby", Item::new, new Item.Settings());

	//handheld
	public static final Item GUIDITE_AXE = register("guidite_axe", settings -> new AxeItem(GUIDITE_TOOL_MATERIAL, 5.0F, -3.0F, settings), new Item.Settings());

	//spawn egg
	// :::spawn_egg_register_item
	public static final SpawnEggItem CUSTOM_SPAWN_EGG = registerSpawnEgg(
			"custom_spawn_egg",
			EntityType.FROG,
			new Item.Settings()
	);
	// :::spawn_egg_register_item

	//dyeable
	public static final Item LEATHER_GLOVES = register("leather_gloves", Item::new, new Item.Settings());

	//condition
	public static final Item FLASHLIGHT = register("flashlight", settings -> new Item(settings) {
		@Override
		public ActionResult use(World world, PlayerEntity user, Hand hand) {
			user.setCurrentHand(hand);
			return ActionResult.CONSUME;
		}
	}, new Item.Settings());

	//custom
	public static final Item BALLOON = register("balloon", Item::new, new Item.Settings());

	//composite
	public static final Item ENHANCED_HOE = register("enhanced_hoe", settings -> new HoeItem(GUIDITE_TOOL_MATERIAL, -4.0F, 0.0F, settings), new Item.Settings());

	//select
	public static final Item DIMENSIONAL_CRYSTAL = register("dimensional_crystal", Item::new, new Item.Settings());

	//range dispatch
	public static final Item THROWING_KNIVES = register("throwing_knives", Item::new, new Item.Settings().maxCount(3));

	// :::spawn_egg_register_method
	public static SpawnEggItem registerSpawnEgg(String name, EntityType<? extends MobEntity> entityType, Item.Settings settings) {
		// Create the item key.
		RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, name));

		// Create the spawn egg item instance.
		SpawnEggItem spawnEggItem = new SpawnEggItem(entityType, settings.registryKey(itemKey));

		// Register the spawn egg item.
		Registry.register(Registries.ITEM, itemKey, spawnEggItem);

		return spawnEggItem;
	}

	// :::spawn_egg_register_method

	// :::1
	public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
		// Create the item key.
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, name));

		// Create the item instance.
		Item item = itemFactory.apply(settings.setId(itemKey));

		// Register the item.
		Registry.register(BuiltInRegistries.ITEM, itemKey, item);

		return item;
	}

	// :::1

	// :::3
	public static void initialize() {
		// :::3
		// :::4
		// Get the event for modifying entries in the ingredients group.
		// And register an event handler that adds our suspicious item to the ingredients group.
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
				.register((itemGroup) -> itemGroup.accept(ModItems.SUSPICIOUS_SUBSTANCE));
		// :::4

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register((itemGroup) -> {
					itemGroup.accept(ModItems.GUIDITE_HELMET);
					itemGroup.accept(ModItems.GUIDITE_BOOTS);
					itemGroup.accept(ModItems.GUIDITE_LEGGINGS);
					itemGroup.accept(ModItems.GUIDITE_CHESTPLATE);
				});

		// :::8
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register((itemGroup) -> itemGroup.accept(ModItems.GUIDITE_SWORD));
		// :::8

		// :::spawn_egg_item_group
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS)
				.register(itemGroup -> itemGroup.add(ModItems.CUSTOM_SPAWN_EGG));
		// :::spawn_egg_item_group

		// :::_12
		// Register the group.
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

		// Register items to the custom item group.
		ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
			itemGroup.accept(ModItems.SUSPICIOUS_SUBSTANCE);
			itemGroup.accept(ModItems.POISONOUS_APPLE);
			itemGroup.accept(ModItems.GUIDITE_SWORD);
			itemGroup.accept(ModItems.GUIDITE_HELMET);
			itemGroup.accept(ModItems.GUIDITE_BOOTS);
			itemGroup.accept(ModItems.GUIDITE_LEGGINGS);
			itemGroup.accept(ModItems.GUIDITE_CHESTPLATE);
			itemGroup.accept(ModItems.LIGHTNING_STICK);
			// ...
		});
		// :::_12

		ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
			itemGroup.add(ModItems.RUBY);
			itemGroup.add(ModItems.GUIDITE_AXE);
			itemGroup.add(ModItems.LEATHER_GLOVES);
			itemGroup.add(ModItems.FLASHLIGHT);
			itemGroup.add(ModItems.BALLOON);
			itemGroup.add(ModItems.ENHANCED_HOE);
			itemGroup.add(ModItems.DIMENSIONAL_CRYSTAL);
			itemGroup.add(ModItems.THROWING_KNIVES);
		});

		// :::_10
		// Add the suspicious substance to the composting registry with a 30% chance of increasing the composter's level.
		CompostingChanceRegistry.INSTANCE.add(ModItems.SUSPICIOUS_SUBSTANCE, 0.3f);
		// :::_10

		// :::_11
		// Add the suspicious substance to the registry of fuels, with a burn time of 30 seconds.
		// Remember, Minecraft deals with logical based-time using ticks.
		// 20 ticks = 1 second.
		FuelRegistryEvents.BUILD.register((builder, context) -> {
			builder.add(ModItems.SUSPICIOUS_SUBSTANCE, 30 * 20);
		});
		// :::_11
		// :::3
	}

	// :::3
	// :::1
}
// :::1
