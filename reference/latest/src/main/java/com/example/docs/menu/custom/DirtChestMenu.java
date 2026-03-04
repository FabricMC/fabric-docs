package com.example.docs.menu.custom;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import com.example.docs.menu.ModMenuType;

// :::menu
public class DirtChestMenu extends AbstractContainerMenu {
	private final Container container;

	// Client-side constructor
	public DirtChestMenu(final int containerId, final Inventory inventory) {
		this(containerId, inventory, new SimpleContainer(9));
	}

	// Server-side constructor
	public DirtChestMenu(final int containerId, final Inventory inventory, final Container container) {
		super(ModMenuType.DIRT_CHEST, containerId);
		checkContainerSize(container, 9);
		this.container = container;
		container.startOpen(inventory.player);

		int rows = 3;
		int columns = 3;

		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				int slot = x + y * 3;
				this.addSlot(new Slot(container, slot, 62 + x * 18, 17 + y * 18));
			}
		}

		this.addStandardInventorySlots(inventory, 8, 84);
	}

	// :::menu

	@Override
	public ItemStack quickMoveStack(Player player, int i) {
		return null;
	}

	// :::menu
	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}
	// :::menu

	// :::menu
}
// :::menu
