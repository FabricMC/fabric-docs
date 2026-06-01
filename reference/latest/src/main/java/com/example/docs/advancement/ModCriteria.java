package com.example.docs.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

// :::datagen-advancements:mod-criteria
public class ModCriteria {
	// :::datagen-advancements:mod-criteria-init
	// :::datagen-advancements:mod-criteria
	public static final UseToolCriterion USE_TOOL = CriteriaTriggers.register(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "use_tool").toString(), new UseToolCriterion());
	// :::datagen-advancements:mod-criteria
	// :::datagen-advancements:new-mod-criteria
	public static final ParameterizedUseToolCriterion PARAMETERIZED_USE_TOOL = CriteriaTriggers.register(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "parameterized_use_tool").toString(), new ParameterizedUseToolCriterion());

	// :::datagen-advancements:mod-criteria
	// :::datagen-advancements:mod-criteria-init
	public static void init() {
	}

	// :::datagen-advancements:new-mod-criteria
	// :::datagen-advancements:mod-criteria
}

// :::datagen-advancements:mod-criteria
