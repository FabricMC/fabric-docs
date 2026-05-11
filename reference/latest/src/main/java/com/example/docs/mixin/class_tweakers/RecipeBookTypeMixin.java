package com.example.docs.mixin.class_tweakers;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.inventory.RecipeBookType;

// #region enum-extension-no-impls-example-mixin
@Mixin(RecipeBookType.class)
enum RecipeBookTypeMixin {
	EXAMPLE_MOD_RECIPE_BOOK_TYPE
}
// #endregion enum-extension-no-impls-example-mixin
