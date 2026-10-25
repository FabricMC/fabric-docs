package com.example.docs.enum_extension;

import net.minecraft.world.inventory.RecipeBookType;

// Class to hold example code for enum extensions.
class ExampleModEnumExtension {
	// #region enum_extension_added_constant_no_ct_usage_example_store
	public static final RecipeBookType ADDED_RECIPE_BOOK_TYPE = RecipeBookType.valueOf("EXAMPLE_MOD_RECIPE_BOOK_TYPE");
	// #endregion enum_extension_added_constant_no_ct_usage_example_store

	// #region enum_extension_added_constant_usage_example
	void exampleRecipeBookTypeMethod(RecipeBookType recipeBookType) {
		if (recipeBookType == RecipeBookType.EXAMPLE_MOD_RECIPE_BOOK_TYPE) {
			// ...
		}
	}
	// #endregion enum_extension_added_constant_usage_example

	// #region enum_extension_added_constant_no_ct_usage_example_check
	void exampleRecipeBookTypeMethodNoCT(RecipeBookType recipeBookType) {
		if (recipeBookType.name().equals("EXAMPLE_MOD_RECIPE_BOOK_TYPE")) {
			// ...
		}
	}
	// #endregion enum_extension_added_constant_no_ct_usage_example_check

	// #region enum_extension_problematic_switch_expr_example
	void exampleProblematicSwitch(RecipeBookType recipeBookType) {
		String s = switch (recipeBookType) {
			case SMOKER -> "smoker";
			case CRAFTING -> "crafting";
			case FURNACE -> "furnace";
			case BLAST_FURNACE -> "blast_furnace";
			case EXAMPLE_MOD_RECIPE_BOOK_TYPE -> "example_mod_recipe_book_type";
		};
		// ...
	}
	// #endregion enum_extension_problematic_switch_expr_example
}
