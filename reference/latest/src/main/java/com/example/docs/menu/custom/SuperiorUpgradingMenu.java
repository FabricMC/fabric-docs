package com.example.docs.menu.custom;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("unused")
public class SuperiorUpgradingMenu extends UpgradingMenu {
	public SuperiorUpgradingMenu(int containerId, Inventory inventory) {
		super(containerId, inventory);
	}

	public SuperiorUpgradingMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
		super(containerId, inventory, access);
	}

	// #region quickMove
	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		return super.quickMoveStack(player, slotIndex);
	}
	// #endregion quickMove
}
