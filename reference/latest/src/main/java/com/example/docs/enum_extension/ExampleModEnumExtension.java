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

	// #region enum-extension-added-constant-no-ct-usage-example
	void exampleRecipeBookTypeMethodNoCT(RecipeBookType recipeBookType) {
		if (recipeBookType == RecipeBookType.valueOf("EXAMPLE_MOD_RECIPE_BOOK_TYPE")) {
			/* ... */
		}
	}
	// #endregion enum-extension-added-constant-no-ct-usage-example

	// #region enum-extension-problematic-switch-expr-example
	void exampleProblematicSwitch(RecipeBookType recipeBookType) {
		String s = switch (recipeBookType) {
			case SMOKER -> "smoker";
			case CRAFTING -> "crafting";
			case FURNACE -> "furnace";
			case BLAST_FURNACE -> "blast_furnace";
			case EXAMPLE_MOD_RECIPE_BOOK_TYPE -> "example_mod_recipe_book_type";
		};
		/* ... */
	}
	// #endregion enum-extension-problematic-switch-expr-example
}
