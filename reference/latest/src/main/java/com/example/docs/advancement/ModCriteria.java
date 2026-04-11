package com.example.docs.advancement;

import net.minecraft.advancements.CriteriaTriggers;

import com.example.docs.ExampleMod;

// #region datagen-advancements--mod-criteria
public class ModCriteria {
	// #region datagen-advancements--mod-criteria-init
	// #endregion datagen-advancements--mod-criteria
	public static final UseToolCriterion USE_TOOL = CriteriaTriggers.register(ExampleMod.MOD_ID + ":use_tool", new UseToolCriterion());
	// #region datagen-advancements--mod-criteria
	// #region datagen-advancements--new-mod-criteria
	public static final ParameterizedUseToolCriterion PARAMETERIZED_USE_TOOL = CriteriaTriggers.register(ExampleMod.MOD_ID + ":parameterized_use_tool", new ParameterizedUseToolCriterion());

	// #endregion datagen-advancements--mod-criteria
	// #endregion datagen-advancements--mod-criteria-init
	public static void init() {
	}

	// #endregion datagen-advancements--new-mod-criteria
	// #region datagen-advancements--mod-criteria
}

// #endregion datagen-advancements--mod-criteria
