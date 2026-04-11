package com.example.docs.menu.custom;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import com.example.docs.block.entity.custom.DirtChestBlockEntity;
import com.example.docs.menu.ModMenuType;

// #region menu
public class DirtChestMenu extends AbstractContainerMenu {
	private final Container container;

	// Client-side constructor
	public DirtChestMenu(final int containerId, final Inventory inventory) {
		this(containerId, inventory, new SimpleContainer(DirtChestBlockEntity.CONTAINER_SIZE));
	}

	// Server-side constructor
	public DirtChestMenu(final int containerId, final Inventory inventory, final Container container) {
		super(ModMenuType.DIRT_CHEST, containerId);
		checkContainerSize(container, DirtChestBlockEntity.CONTAINER_SIZE);
		this.container = container;

		// Some containers do custom logic when opened by a player.
		container.startOpen(inventory.player);

		int rows = 3;
		int columns = 3;

		// Add the slots for our container in a 3x3 grid.
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				int slot = x + y * 3;
				this.addSlot(new Slot(container, slot, 62 + x * 18, 17 + y * 18));
			}
		}

		// Add the player inventory slots.
		this.addStandardInventorySlots(inventory, 8, 84);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		Slot slot = this.slots.get(slotIndex);

		if (!slot.hasItem()) {
			return ItemStack.EMPTY;
		}

		ItemStack stack = slot.getItem();
		ItemStack clicked = stack.copy();

		if (slotIndex < container.getContainerSize()) {
			if (!this.moveItemStackTo(stack, container.getContainerSize(), this.slots.size(), true)) {
				return ItemStack.EMPTY;
			}
		} else if (!this.moveItemStackTo(stack, 0, container.getContainerSize(), false)) {
			return ItemStack.EMPTY;
		}

		if (stack.isEmpty()) {
			slot.setByPlayer(ItemStack.EMPTY);
		} else {
			slot.setChanged();
		}

		return clicked;
	}

	@Override
	public boolean stillValid(Player player) {
		return container.stillValid(player);
	}
}
// #endregion menu
