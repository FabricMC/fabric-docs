package com.example.docs.component;

import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

//::1
public class ModComponents {
	//::1

	//::2
	public static final DataComponentType<Integer> CLICK_COUNT_COMPONENT = Registry.register(
			BuiltInRegistries.DATA_COMPONENT_TYPE,
			Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "click_count"),
			DataComponentType.<Integer>builder().persistent(Codec.INT).build()
	);
	//::2

	public static final DataComponentType<ComponentWithTooltip> COMPONENT_WITH_TOOLTIP = Registry.register(
			BuiltInRegistries.DATA_COMPONENT_TYPE,
			Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "click_count_with_tooltip"),
			DataComponentType.<ComponentWithTooltip>builder().persistent(ComponentWithTooltip.CODEC).build()
	);

	//::3
	public static final DataComponentType<MyCustomComponent> MY_CUSTOM_COMPONENT = Registry.register(
			BuiltInRegistries.DATA_COMPONENT_TYPE,
			Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "custom"),
			DataComponentType.<MyCustomComponent>builder().persistent(MyCustomComponent.CODEC).build()
	);
	//::3

	//::1
	protected static void initialize() {
		ExampleMod.LOGGER.info("Registering {} components", ExampleMod.MOD_ID);
		// Technically this method can stay empty, but some developers like to notify
		// the console, that certain parts of the mod have been successfully initialized
	}
}
//::1
