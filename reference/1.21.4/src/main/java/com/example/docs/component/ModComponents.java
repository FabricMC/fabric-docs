package com.example.docs.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import com.example.docs.FabricDocsReference;

//::1
public class ModComponents {
	//::1

	//::2
	public static final DataComponentType<Integer> CLICK_COUNT_COMPONENT = Registry.register(
			BuiltInRegistries.DATA_COMPONENT_TYPE,
			ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, "click_count"),
			DataComponentType.<Integer>builder().persistent(Codec.INT).build()
	);
	//::2

	//::3
	public static final DataComponentType<MyCustomComponent> MY_CUSTOM_COMPONENT = Registry.register(
			BuiltInRegistries.DATA_COMPONENT_TYPE,
			ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, "custom"),
			DataComponentType.<MyCustomComponent>builder().persistent(MyCustomComponent.CODEC).build()
	);
	//::3

	//::1
	protected static void initialize() {
		FabricDocsReference.LOGGER.info("Registering {} components", FabricDocsReference.MOD_ID);
		// Technically this method can stay empty, but some developers like to notify
		// the console, that certain parts of the mod have been successfully initialized
	}
}
//::1
