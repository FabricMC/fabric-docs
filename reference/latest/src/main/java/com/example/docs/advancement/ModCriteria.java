package com.example.docs.advancement;

import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

// #region datagen_advancements_mod_criteria
public class ModCriteria {
	public static final UseToolCriterion USE_TOOL = register("use_tool", new UseToolCriterion());

	// #endregion datagen_advancements_mod_criteria
	// #region datagen_advancements_new_mod_criteria
	public static final ParameterizedUseToolCriterion PARAMETERIZED_USE_TOOL = register("parameterized_use_tool", new ParameterizedUseToolCriterion());
	// #endregion datagen_advancements_new_mod_criteria

	// #region datagen_advancements_mod_criteria
	private static <T extends CriterionTrigger<?>> T register(final String name, final T criterion) {
		return Registry.register(BuiltInRegistries.TRIGGER_TYPES, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name), criterion);
	}
	// #endregion datagen_advancements_mod_criteria

	// #region datagen_advancements_mod_criteria_init
	public static void init() {
	}
	// #endregion datagen_advancements_mod_criteria_init

	// #region datagen_advancements_mod_criteria
}
// #endregion datagen_advancements_mod_criteria
