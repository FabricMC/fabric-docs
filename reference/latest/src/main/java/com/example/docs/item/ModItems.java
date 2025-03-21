package com.example.docs.item;

import java.util.function.Function;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;

import com.example.docs.FabricDocsReference;
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
			settings -> new ArmorItem(GuiditeArmorMaterial.INSTANCE, EquipmentType.HELMET, settings),
			new Item.Settings().maxDamage(EquipmentType.HELMET.getMaxDamage(GuiditeArmorMaterial.BASE_DURABILITY))
	);
	public static final Item GUIDITE_CHESTPLATE = register("guidite_chestplate",
			settings -> new ArmorItem(GuiditeArmorMaterial.INSTANCE, EquipmentType.CHESTPLATE, settings),
			new Item.Settings().maxDamage(EquipmentType.CHESTPLATE.getMaxDamage(GuiditeArmorMaterial.BASE_DURABILITY))
	);

	public static final Item GUIDITE_LEGGINGS = register(
			"guidite_leggings",
			settings -> new ArmorItem(GuiditeArmorMaterial.INSTANCE, EquipmentType.LEGGINGS, settings),
			new Item.Settings().maxDamage(EquipmentType.LEGGINGS.getMaxDamage(GuiditeArmorMaterial.BASE_DURABILITY))
	);

	public static final Item GUIDITE_BOOTS = register(
			"guidite_boots",
			settings -> new ArmorItem(GuiditeArmorMaterial.INSTANCE, EquipmentType.BOOTS, settings),
			new Item.Settings().maxDamage(EquipmentType.BOOTS.getMaxDamage(GuiditeArmorMaterial.BASE_DURABILITY))
	);
	// :::6
	public static final Item LIGHTNING_STICK = register("lightning_stick", LightningStick::new, new Item.Settings());
	// :::7
	public static final Item GUIDITE_SWORD = register(
			"guidite_sword",
			settings -> new SwordItem(GUIDITE_TOOL_MATERIAL, 1f, 1f, settings),
			new Item.Settings()
	);
	// :::7
	// :::_13
	public static final Item COUNTER = register(
			"counter",
			CounterItem::new,
			new Item.Settings()
					// Initialize the click count component with a default value of 0
					.component(ModComponents.CLICK_COUNT_COMPONENT, 0)
	);
	// :::_13
	// :::9
	public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(FabricDocsReference.MOD_ID, "item_group"));
	public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.GUIDITE_SWORD))
			.displayName(Text.translatable("itemGroup.fabric_docs_reference"))
			.build();
	// :::9
	// :::5
	public static final ConsumableComponent POISON_FOOD_CONSUMABLE_COMPONENT = ConsumableComponents.food()
			// The duration is in ticks, 20 ticks = 1 second
			.consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.POISON, 6 * 20, 1), 1.0f))
			.build();
	public static final FoodComponent POISON_FOOD_COMPONENT = new FoodComponent.Builder()
			.alwaysEdible()
			.build();
	// :::5

	// :::poisonous_apple
	public static final Item POISONOUS_APPLE = register(
			"poisonous_apple",
			Item::new,
			new Item.Settings().food(POISON_FOOD_COMPONENT, POISON_FOOD_CONSUMABLE_COMPONENT)
	);
	// :::poisonous_apple

	// :::2
	public static final Item SUSPICIOUS_SUBSTANCE = register("suspicious_substance", Item::new, new Item.Settings());
	// :::2
	// :::1
	public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
		// Create the item key.
		RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, name));

		// Create the item instance.
		Item item = itemFactory.apply(settings.registryKey(itemKey));

		// Register the item.
		Registry.register(Registries.ITEM, itemKey, item);

		return item;
	}

	// :::1

	// :::3
	public static void initialize() {
		// :::3
		// :::4
		// Get the event for modifying entries in the ingredients group.
		// And register an event handler that adds our suspicious item to the ingredients group.
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
				.register((itemGroup) -> itemGroup.add(ModItems.SUSPICIOUS_SUBSTANCE));
		// :::4

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
				.register((itemGroup) -> {
					itemGroup.add(ModItems.GUIDITE_HELMET);
					itemGroup.add(ModItems.GUIDITE_BOOTS);
					itemGroup.add(ModItems.GUIDITE_LEGGINGS);
					itemGroup.add(ModItems.GUIDITE_CHESTPLATE);
				});

		// :::8
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
				.register((itemGroup) -> itemGroup.add(ModItems.GUIDITE_SWORD));
		// :::8

		// :::_12
		// Register the group.
		Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

		// Register items to the custom item group.
		ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
			itemGroup.add(ModItems.SUSPICIOUS_SUBSTANCE);
			itemGroup.add(ModItems.POISONOUS_APPLE);
			itemGroup.add(ModItems.GUIDITE_SWORD);
			itemGroup.add(ModItems.GUIDITE_HELMET);
			itemGroup.add(ModItems.GUIDITE_BOOTS);
			itemGroup.add(ModItems.GUIDITE_LEGGINGS);
			itemGroup.add(ModItems.GUIDITE_CHESTPLATE);
			itemGroup.add(ModItems.LIGHTNING_STICK);
			// ...
		});
		// :::_12

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
