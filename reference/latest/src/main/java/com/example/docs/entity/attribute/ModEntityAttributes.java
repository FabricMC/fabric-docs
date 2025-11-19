package com.example.docs.entity.attribute;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import com.example.docs.ExampleMod;

// :::1
public class ModEntityAttributes {
	public static final Attribute AGGRO_RANGE = register(
			"aggro_range",
			8.0,
			0.0,
			Double.MAX_VALUE,
			false
	);

	public static void initialize() {
	}

	private static Attribute register(
			String name, double defaultValue, double minValue, double maxValue, boolean syncedWithClient
	) {
		ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, name);
		Attribute entityAttribute = new RangedAttribute(
				identifier.toLanguageKey(),
				defaultValue,
				minValue,
				maxValue
		).setSyncable(syncedWithClient);

		return Registry.register(BuiltInRegistries.ATTRIBUTE, identifier, entityAttribute);
	}
}
// :::1
