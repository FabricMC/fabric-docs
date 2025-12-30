package com.example.docs.entity.attribute;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import com.example.docs.ExampleMod;

public class ModAttributes {
	// :::attributes
	public static final Holder<Attribute> AGGRO_RANGE = register(
			"aggro_range",
			8.0,
			0.0,
			Double.MAX_VALUE,
			false
	);
	// :::attributes

	// :::initialize
	public static void initialize() {
	}
	// :::initialize

	// :::register
	private static Holder<Attribute> register(
			String name, double defaultValue, double minValue, double maxValue, boolean syncedWithClient
	) {
		Identifier identifier = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name);
		Attribute entityAttribute = new RangedAttribute(
				identifier.toLanguageKey(),
				defaultValue,
				minValue,
				maxValue
		).setSyncable(syncedWithClient);

		return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, identifier, entityAttribute);
	}
	// :::register
}
