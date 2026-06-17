package com.example.docs.mixin.class_tweakers;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.data.recipes.RecipeCategory;

// #region enum_extension_ctor_impls_example_mixin
@Mixin(RecipeCategory.class)
enum RecipeCategoryMixin {
	EXAMPLE_MOD_RECIPE_CATEGORY("exampleModRecipeFolderName");

	@Shadow
	RecipeCategoryMixin(String recipeFolderName) {
	}
}
// #endregion enum_extension_ctor_impls_example_mixin
