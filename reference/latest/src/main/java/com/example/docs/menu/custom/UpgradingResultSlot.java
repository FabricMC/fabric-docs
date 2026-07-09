package com.example.docs.menu.custom;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

// #region slot
public class UpgradingResultSlot extends Slot {
	private final UpgradingMenu menu;

	public UpgradingResultSlot(UpgradingMenu menu, Container container, int slot, int x, int y) {
		super(container, slot, x, y);
		this.menu = menu;
	}

	@Override
	public void onTake(Player player, ItemStack carried) {
		this.menu.onTake(player, carried);
	}

	@Override
	public boolean mayPlace(ItemStack itemStack) {
		return false;
	}

	@Override
	public boolean isFake() {
		return true;
	}
}
// #endregion slot
