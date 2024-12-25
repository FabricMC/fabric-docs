package com.example.docs.advancement;

import com.example.docs.FabricDocsReference;

// :::datagen-advancements:mod-criteria
import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
	public static final UseToolCriterion USE_TOOL = Criteria.register(FabricDocsReference.MOD_ID + "/use_tool", new UseToolCriterion());
}
// :::datagen-advancements:mod-criteria