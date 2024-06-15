package com.example.docs.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;

import com.example.docs.FabricDocsReference;
import com.example.docs.item.armor.GuiditeArmorMaterial;
import com.example.docs.item.custom.LightningStick;
import com.example.docs.item.tool.GuiditeMaterial;

// :::1
public class ModItems {
	// :::1

	// :::6
	public static final Item GUIDITE_HELMET = register(new ArmorItem(GuiditeArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new Item.Settings()), "guidite_helmet");
	public static final Item GUIDITE_BOOTS = register(new ArmorItem(GuiditeArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new Item.Settings()), "guidite_boots");
	public static final Item GUIDITE_LEGGINGS = register(new ArmorItem(GuiditeArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new Item.Settings()), "guidite_leggings");
	public static final Item GUIDITE_CHESTPLATE = register(new ArmorItem(GuiditeArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new Item.Settings()), "guidite_chestplate");
	// :::6
	public static final Item LIGHTNING_STICK = register(new LightningStick(new FabricItemSettings()), "lightning_stick");
	// :::7
	public static final Item GUIDITE_SWORD = register(new SwordItem(GuiditeMaterial.INSTANCE, 2, 0.5F, new FabricItemSettings()), "guidite_sword");
	// :::7

	// :::5
	public static final FoodComponent SUSPICIOUS_FOOD_COMPONENT = new FoodComponent.Builder()
			.alwaysEdible()
			.snack()
			// The duration is in ticks, 20 ticks = 1 second
			.statusEffect(new StatusEffectInstance(StatusEffects.POISON, 6 * 20, 1), 1.0f)
			.build();
	// :::5

	// :::2
	public static final Item SUSPICIOUS_SUBSTANCE = register(
			// Ignore the food component for now, we'll cover it later in the food section.
			new Item(new FabricItemSettings().food(SUSPICIOUS_FOOD_COMPONENT)),
			"suspicious_substance"
	);
	// :::2

	// :::1
	// We can use generics (<T extends Item>), so we don't need to
	// cast to an item when using this method.
	public static <T extends Item> T register(T item, String ID) {
		// Create the identifier for the item.
		Identifier itemID = new Identifier(FabricDocsReference.MOD_ID, ID);

		// Register the item.
		T registeredItem = Registry.register(Registries.ITEM, itemID, item);

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

		// :::9
		var groupRegistryKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier(FabricDocsReference.MOD_ID, "item_group"));
		ItemGroupEvents.modifyEntriesEvent(groupRegistryKey).register(itemGroup -> {
			itemGroup.add(ModItems.SUSPICIOUS_SUBSTANCE);
			itemGroup.add(ModItems.GUIDITE_SWORD);
			itemGroup.add(ModItems.GUIDITE_HELMET);
			itemGroup.add(ModItems.GUIDITE_BOOTS);
			itemGroup.add(ModItems.GUIDITE_LEGGINGS);
			itemGroup.add(ModItems.GUIDITE_CHESTPLATE);
			itemGroup.add(ModItems.LIGHTNING_STICK);
			// ...
		});
		// :::9

		// :::_10
		// Add the suspicious substance to the composting registry with a 30% chance of increasing the composter's level.
		CompostingChanceRegistry.INSTANCE.add(ModItems.SUSPICIOUS_SUBSTANCE, 0.3f);
		// :::_10

		// :::_11
		// Add the suspicious substance to the flammable block registry with a burn time of 30 seconds.
		// Remember, Minecraft deals with logical based-time using ticks.
		// 20 ticks = 1 second.
		FuelRegistry.INSTANCE.add(ModItems.GUIDITE_SWORD, 30 * 20);
		// :::_11
		// :::3
	}

	// :::3
}
// :::1
