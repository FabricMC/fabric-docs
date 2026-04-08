package com.example.docs.enum_extension;

import net.minecraft.world.inventory.RecipeBookType;

// Class to hold example code for enum extensions.
class ExampleModEnumExtension {
	// #region enum-extension-added-constant-usage-example
	void exampleRecipeBookTypeMethod(RecipeBookType recipeBookType) {
		if (recipeBookType == RecipeBookType.EXAMPLE_MOD_RECIPE_BOOK_TYPE) {
			/* ... */
		}
	}
	// #endregion enum-extension-added-constant-usage-example
}
