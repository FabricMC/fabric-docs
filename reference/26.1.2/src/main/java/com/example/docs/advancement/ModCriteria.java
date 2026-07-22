package com.example.docs.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

// #region datagen_advancements_mod_criteria
public class ModCriteria {
	public static final UseToolCriterion USE_TOOL = CriteriaTriggers.register(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "use_tool").toString(), new UseToolCriterion());
	// #endregion datagen_advancements_mod_criteria

	// #region datagen_advancements_new_mod_criteria
	public static final ParameterizedUseToolCriterion PARAMETERIZED_USE_TOOL = CriteriaTriggers.register(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "parameterized_use_tool").toString(), new ParameterizedUseToolCriterion());
	// #endregion datagen_advancements_new_mod_criteria

	// #region datagen_advancements_mod_criteria_init
	public static void init() {
	}
	// #endregion datagen_advancements_mod_criteria_init

	// #region datagen_advancements_mod_criteria
}
// #endregion datagen_advancements_mod_criteria
