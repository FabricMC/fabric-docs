package com.example.docs.entity.attribute;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import com.example.docs.FabricDocsReference;

// :::1
public class ModEntityAttributes {
	public static final EntityAttribute AGGRO_RANGE = register(
			"aggro_range",
			8.0,
			0.0,
			Double.MAX_VALUE,
			false
	);

	public static void initialize() {
	}

	/**
	 * Register a custom entity attribute.
	 *
	 * @param name The short identifier for the new attribute
	 * @param defaultValue The default value of the attribute
	 * @param minValue The minimum allowed value for this attribute
	 * @param maxValue The maximum allowed value for this attribute
	 * @param syncedWithClient Whether the attribute should be synchronized with the client
	 * @return The registered attribute
	 */
	private static EntityAttribute register(
			String name, double defaultValue, double minValue, double maxValue, boolean syncedWithClient
	) {
		Identifier identifier = Identifier.of(FabricDocsReference.MOD_ID, name);
		EntityAttribute entityAttribute = new ClampedEntityAttribute(
				identifier.toTranslationKey(),
				defaultValue,
				minValue,
				maxValue
		).setTracked(syncedWithClient);

		return Registry.register(Registries.ATTRIBUTE, identifier, entityAttribute);
	}
}
// :::1
