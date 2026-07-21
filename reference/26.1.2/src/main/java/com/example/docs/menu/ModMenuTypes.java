package com.example.docs.menu;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import com.example.docs.ExampleMod;
import com.example.docs.menu.custom.DirtChestMenu;
import com.example.docs.menu.custom.UpgradingMenu;

// #region register_menu
public class ModMenuTypes {
	public static final MenuType<DirtChestMenu> DIRT_CHEST = register("dirt_chest", DirtChestMenu::new);
	// #endregion register_menu

	// #region upgrading_menu_registration
	public static final MenuType<UpgradingMenu> UPGRADING_MENU_TYPE = register("upgrading", UpgradingMenu::new);
	// #endregion upgrading_menu_registration

	// #region register_menu

	public static <T extends AbstractContainerMenu> MenuType<T> register(
					String name,
					MenuType.MenuSupplier<T> constructor
	) {
		return Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name), new MenuType<>(constructor, FeatureFlagSet.of()));
	}
	// #endregion register_menu

	public static void initialize() {
	}

	// #region register_menu
}
// #endregion register_menu
