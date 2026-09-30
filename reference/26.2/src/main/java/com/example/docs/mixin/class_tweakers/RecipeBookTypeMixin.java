package com.example.docs.mixin.class_tweakers;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.inventory.RecipeBookType;

// #region enum_extension_no_impls_example_mixin
@Mixin(RecipeBookType.class)
enum RecipeBookTypeMixin {
	EXAMPLE_MOD_RECIPE_BOOK_TYPE
}
// #endregion enum_extension_no_impls_example_mixin
