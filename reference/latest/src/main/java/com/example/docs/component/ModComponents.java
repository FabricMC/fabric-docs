package com.example.docs.component;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

import com.example.docs.ExampleMod;

// #region mod_components
public class ModComponents {
	// #endregion mod_components

	// #region integer_component
	public static final DataComponentType<Integer> CLICK_COUNT_COMPONENT = Registry.register(
			BuiltInRegistries.DATA_COMPONENT_TYPE,
			Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "click_count"),
			DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build()
	);
	// #endregion integer_component

	public static final DataComponentType<ComponentWithTooltip> COMPONENT_WITH_TOOLTIP = Registry.register(
			BuiltInRegistries.DATA_COMPONENT_TYPE,
			Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "click_count_with_tooltip"),
			DataComponentType.<ComponentWithTooltip>builder().persistent(ComponentWithTooltip.CODEC).networkSynchronized(ComponentWithTooltip.STREAM_CODEC).build()
	);

	// #region custom_component
	public static final DataComponentType<AdvancedCustomComponent> ADVANCED_CUSTOM_COMPONENT = Registry.register(
			BuiltInRegistries.DATA_COMPONENT_TYPE,
			Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "custom"),
			DataComponentType.<AdvancedCustomComponent>builder().persistent(AdvancedCustomComponent.CODEC).networkSynchronized(AdvancedCustomComponent.STREAM_CODEC).build()
	);
	// #endregion custom_component

	// #region mod_components
	protected static void initialize() {
		ExampleMod.LOGGER.info("Registering {} components", ExampleMod.MOD_ID);
		// Technically this method can stay empty, but some developers like to notify
		// the console, that certain parts of the mod have been successfully initialized
	}
}
// #endregion mod_components
