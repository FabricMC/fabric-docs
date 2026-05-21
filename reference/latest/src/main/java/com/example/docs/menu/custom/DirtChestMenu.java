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

// :::menu
public class DirtChestMenu extends AbstractContainerMenu {
	private static final int SLOTS_ROWS = 3;
	private static final int SLOTS_COLUMNS = 3;
	private static final int SLOTS_START_X = 62;
	private static final int SLOTS_START_Y = 17;
	private static final int INVENTORY_START_X = 8;
	private static final int INVENTORY_START_Y = 84;

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

		// Add the slots for our container in a 3x3 grid.
		this.add3x3Slots(container, SLOTS_START_X, SLOTS_START_Y);

		// Add the player inventory slots.
		this.addStandardInventorySlots(inventory, INVENTORY_START_X, INVENTORY_START_Y);
	}

	private void add3x3Slots(Container container, int left, int top) {
		for (int x = 0; x < SLOTS_ROWS; x++) {
			for (int y = 0; y < SLOTS_COLUMNS; y++) {
				final int slot = x + y * SLOTS_COLUMNS;
				this.addSlot(new Slot(
								container,
								slot,
								left + x * SLOT_SIZE,
								top + x * SLOT_SIZE
				));
			}
		}
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		Slot slot = this.slots.get(slotIndex);

		if (!slot.hasItem()) {
			return ItemStack.EMPTY;
		}

		ItemStack stack = slot.getItem();
		ItemStack clicked = stack.copy();

		if (slotIndex < this.container.getContainerSize()) {
			if (!this.moveItemStackTo(stack, this.container.getContainerSize(), this.slots.size(), true)) {
				return ItemStack.EMPTY;
			}
		} else if (!this.moveItemStackTo(stack, 0, this.container.getContainerSize(), false)) {
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
		return this.container.stillValid(player);
	}
}
// :::menu
