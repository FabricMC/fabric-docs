package com.example.docs.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import com.example.docs.ExampleMod;
import com.example.docs.component.ModComponents;
import com.example.docs.item.armor.ModArmorMaterials;
import com.example.docs.item.custom.CounterItem;
import com.example.docs.item.custom.LightningStick;
import com.example.docs.item.tool.GuiditeMaterial;

// :::1
public class ModItems {
	// :::1

	// :::6
	public static final Item GUIDITE_HELMET = register(new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), "guidite_helmet");
	public static final Item GUIDITE_CHESTPLATE = register(new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), "guidite_chestplate");
	public static final Item GUIDITE_LEGGINGS = register(new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), "guidite_leggings");
	public static final Item GUIDITE_BOOTS = register(new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), "guidite_boots");
	// :::6
	public static final Item LIGHTNING_STICK = register(new LightningStick(new Item.Properties()), "lightning_stick");
	// :::7
	public static final Item GUIDITE_SWORD = register(new SwordItem(GuiditeMaterial.INSTANCE, new Item.Properties()), "guidite_sword");
	// :::7
	// :::_13
	public static final Item COUNTER = register(new CounterItem(
		// Initialize the click count component with a default value of 0
		new Item.Properties().component(ModComponents.CLICK_COUNT_COMPONENT, 0)
	), "counter");
	// :::_13
	// :::9
	public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "item_group"));
	public static final CreativeModeTab CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.GUIDITE_SWORD))
			.title(Component.translatable("itemGroup.example-mod"))
			.build();
	// :::9
	// :::5
	public static final FoodProperties POISON_FOOD_COMPONENT = new FoodProperties.Builder()
			.alwaysEdible()
			.fast()
			// The duration is in ticks, 20 ticks = 1 second
			.effect(new MobEffectInstance(MobEffects.POISON, 6 * 20, 1), 1.0f)
			.build();
	// :::5

	// :::poisonous_apple
	public static final Item POISONOUS_APPLE = register(
			new Item(new Item.Properties().food(POISON_FOOD_COMPONENT)),
			"poisonous_apple"
	);
	// :::poisonous_apple

	// :::2
	public static final Item SUSPICIOUS_SUBSTANCE = register(
			new Item(new Item.Properties()),
			"suspicious_substance"
	);
	// :::2

	// :::1
	public static Item register(Item item, String id) {
		// Create the identifier for the item.
		ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, id);

		// Register the item.
		Item registeredItem = Registry.register(BuiltInRegistries.ITEM, itemID, item);

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

		// :::_10
		// Add the suspicious substance to the composting registry with a 30% chance of increasing the composter's level.
		CompostingChanceRegistry.INSTANCE.add(ModItems.SUSPICIOUS_SUBSTANCE, 0.3f);
		// :::_10

		// :::_11
		// Add the suspicious substance to the registry of fuels, with a burn time of 30 seconds.
		// Remember, Minecraft deals with logical based-time using ticks.
		// 20 ticks = 1 second.
		FuelRegistry.INSTANCE.add(ModItems.SUSPICIOUS_SUBSTANCE, 30 * 20);
		// :::_11
		// :::3
	}

	// :::3
}
// :::1
