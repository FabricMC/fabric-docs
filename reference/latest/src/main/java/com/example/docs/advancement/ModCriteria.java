package com.example.docs.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

// #region datagen-advancements--mod-criteria
public class ModCriteria {
	// #region datagen-advancements:mod-criteria-init
	// #region datagen-advancements:mod-criteria
	public static final UseToolCriterion USE_TOOL = CriteriaTriggers.register(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "use_tool").toString(), new UseToolCriterion());
	// #endregion datagen-advancements:mod-criteria
	// #region datagen-advancements:new-mod-criteria
	public static final ParameterizedUseToolCriterion PARAMETERIZED_USE_TOOL = CriteriaTriggers.register(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "parameterized_use_tool").toString(), new ParameterizedUseToolCriterion());

	// #endregion datagen-advancements--mod-criteria
	// #endregion datagen-advancements--mod-criteria-init
	public static void init() {
	}

	// #endregion datagen-advancements--new-mod-criteria
	// #region datagen-advancements--mod-criteria
}

// #endregion datagen-advancements--mod-criteria
