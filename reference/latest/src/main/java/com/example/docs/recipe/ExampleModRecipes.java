package com.example.docs.recipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;

import com.example.docs.ExampleMod;
import com.example.docs.block.custom.UpgradingBlock;
import com.example.docs.menu.custom.UpgradingMenu;

public class ExampleModRecipes implements ModInitializer {
	//:::registration
	public static final UpgradingRecipeSerializer UPGRADING_RECIPE_SERIALIZER = Registry.register(
					BuiltInRegistries.RECIPE_SERIALIZER,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "upgrading"),
					new UpgradingRecipeSerializer()
	);

	public static final RecipeType<UpgradingRecipe> UPGRADING_RECIPE_TYPE = Registry.register(
					BuiltInRegistries.RECIPE_TYPE,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "upgrading"),
					new RecipeType<UpgradingRecipe>() { }
	);

	//:::registration

	// TODO - recipe book support, requires enum extensions + screen changes
	public static final RecipeBookCategory UPGRADING_RECIPE_BOOK_CATEGORY = Registry.register(
					BuiltInRegistries.RECIPE_BOOK_CATEGORY,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "upgrading"),
					new RecipeBookCategory()
	);

	//:::menuRegistration
	public static final MenuType<UpgradingMenu> UPGRADING_MENU_TYPE = Registry.register(
					BuiltInRegistries.MENU,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "upgrading"),
					new MenuType<>(UpgradingMenu::new, FeatureFlags.VANILLA_SET)
	);

	private static final Identifier UPGRADING_BLOCK_ID = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "upgrading_block");

	public static final UpgradingBlock UPGRADING_BLOCK = Registry.register(
					BuiltInRegistries.BLOCK,
					UPGRADING_BLOCK_ID,
					new UpgradingBlock(BlockBehaviour.Properties.of()
									.setId(ResourceKey.create(Registries.BLOCK, UPGRADING_BLOCK_ID))
					)
	);
	//:::menuRegistration

	@Override
	public void onInitialize() {
		//:::recipeSync
		RecipeSynchronization.synchronizeRecipeSerializer(UPGRADING_RECIPE_SERIALIZER);
		//:::recipeSync
	}
}
