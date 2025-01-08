package com.example.docs.item;

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
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;

import com.example.docs.FabricDocsReference;
import com.example.docs.component.ModComponents;
import com.example.docs.item.armor.ModArmorMaterials;
import com.example.docs.item.custom.CounterItem;
import com.example.docs.item.custom.LightningStick;

import java.util.Map;

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
			ModArmorMaterials.REPAIRS_GUIDITE_ARMOR
	);
	// :::guidite_tool_material

	// :::6
	public static final RegistryKey<Item> GUIDITE_HELMET_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "guidite_helmet"));
	public static final Item GUIDITE_HELMET = register(new ArmorItem(ModArmorMaterials.GUIDITE_ARMOR_MATERIAL, EquipmentType.HELMET, new Item.Settings().registryKey(GUIDITE_HELMET_KEY).maxDamage(EquipmentType.HELMET.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), GUIDITE_HELMET_KEY);

	public static final RegistryKey<Item> GUIDITE_CHESTPLATE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "guidite_chestplate"));
	public static final Item GUIDITE_CHESTPLATE = register(new ArmorItem(ModArmorMaterials.GUIDITE_ARMOR_MATERIAL, EquipmentType.CHESTPLATE, new Item.Settings().registryKey(GUIDITE_CHESTPLATE_KEY).maxDamage(EquipmentType.CHESTPLATE.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), GUIDITE_CHESTPLATE_KEY);

	public static final RegistryKey<Item> GUIDITE_LEGGINGS_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "guidite_leggings"));
	public static final Item GUIDITE_LEGGINGS = register(new ArmorItem(ModArmorMaterials.GUIDITE_ARMOR_MATERIAL, EquipmentType.LEGGINGS, new Item.Settings().registryKey(GUIDITE_LEGGINGS_KEY).maxDamage(EquipmentType.LEGGINGS.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), GUIDITE_LEGGINGS_KEY);

	public static final RegistryKey<Item> GUIDITE_BOOTS_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "guidite_boots"));
	public static final Item GUIDITE_BOOTS = register(new ArmorItem(ModArmorMaterials.GUIDITE_ARMOR_MATERIAL, EquipmentType.BOOTS, new Item.Settings().registryKey(GUIDITE_BOOTS_KEY).maxDamage(EquipmentType.BOOTS.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), GUIDITE_BOOTS_KEY);
	// :::6
	public static final RegistryKey<Item> LIGHTNING_STICK_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "lightning_stick"));
	public static final Item LIGHTNING_STICK = register(new LightningStick(new Item.Settings().registryKey(LIGHTNING_STICK_KEY)), LIGHTNING_STICK_KEY);
	// :::7
	public static final RegistryKey<Item> GUIDITE_SWORD_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "guidite_sword"));
	public static final Item GUIDITE_SWORD = register(new SwordItem(GUIDITE_TOOL_MATERIAL, 1f, 1f, new Item.Settings().registryKey(GUIDITE_SWORD_KEY)), GUIDITE_SWORD_KEY);
	// :::7
	// :::_13
	public static final RegistryKey<Item> COUNTER_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "counter"));
	public static final Item COUNTER = register(new CounterItem(
		new Item.Settings()
				.registryKey(COUNTER_KEY)
				// Initialize the click count component with a default value of 0
				.component(ModComponents.CLICK_COUNT_COMPONENT, 0)
	), COUNTER_KEY);
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
	public static final RegistryKey<Item> POISONOUS_APPLE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "poisonous_apple"));
	public static final Item POISONOUS_APPLE = register(
			new Item(new Item.Settings().registryKey(POISONOUS_APPLE_KEY).food(POISON_FOOD_COMPONENT, POISON_FOOD_CONSUMABLE_COMPONENT)),
			POISONOUS_APPLE_KEY
	);
	// :::poisonous_apple

	// :::2
	public static final RegistryKey<Item> SUSPICIOUS_SUBSTANCE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "suspicious_substance"));
	public static final Item SUSPICIOUS_SUBSTANCE = register(
			new Item(new Item.Settings().registryKey(SUSPICIOUS_SUBSTANCE_KEY)),
			SUSPICIOUS_SUBSTANCE_KEY
	);
	// :::2

	// :::1
	public static Item register(Item item, RegistryKey<Item> registryKey) {
		// Register the item.
		Item registeredItem = Registry.register(Registries.ITEM, registryKey.getValue(), item);

		// Return the registered item!
		return registeredItem;
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
}
// :::1
