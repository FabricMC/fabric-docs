package com.example.docs.mixin.class_tweakers;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.data.recipes.RecipeCategory;

// #region enum-extension-ctor-impls-example-mixin
@Mixin(RecipeCategory.class)
enum RecipeCategoryMixin {
	EXAMPLE_MOD_RECIPE_CATEGORY("exampleModRecipeFolderName");

	@Shadow
	RecipeCategoryMixin(String recipeFolderName) {
	}
}
// #endregion enum-extension-ctor-impls-example-mixin
