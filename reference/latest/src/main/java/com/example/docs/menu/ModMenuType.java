package com.example.docs.menu;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import com.example.docs.menu.custom.DirtChestMenu;

// #region register-menu
public class ModMenuType {
	public static final MenuType<DirtChestMenu> DIRT_CHEST = register("dirt_chest", DirtChestMenu::new);

	public static <T extends AbstractContainerMenu> MenuType<T> register(
					String name,
					MenuType.MenuSupplier<T> constructor
	) {
		return Registry.register(BuiltInRegistries.MENU, name, new MenuType<>(constructor, FeatureFlagSet.of()));
	}
	// #endregion register-menu

	public static void initialize() {
	}

	// #region register-menu
}
// #endregion register-menu
