package com.example.docs.menu.custom;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
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
	private static final int INPUT_SLOTS_COUNT = 2;
	private static final int RESULT_SLOT = 0;
	private static final int INPUT_SLOTS_START = RESULT_SLOT + 1; // 1
	private static final int INPUT_SLOTS_END = INPUT_SLOTS_COUNT + INPUT_SLOTS_START; // 3
	private static final int INVENTORY_START = INPUT_SLOTS_END; // 3
	private static final int INVENTORY_END = INVENTORY_START + 27; // 30
	private static final int HOTBAR_START = INVENTORY_END; // 30
	private static final int HOTBAR_END = HOTBAR_START + 9; // 39

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		Slot slot = this.slots.get(slotIndex);

		//noinspection ConstantValue
		if (slot == null || !slot.hasItem()) {
			return ItemStack.EMPTY;
		}

		ItemStack stack = slot.getItem();
		ItemStack clicked = stack.copy();

		if (slotIndex == RESULT_SLOT) {
			stack.getItem().onCraftedBy(stack, player);

			if (!this.moveItemStackTo(stack, INVENTORY_START, HOTBAR_END, true)) {
				return ItemStack.EMPTY;
			}

			slot.onQuickCraft(stack, clicked);
		} else if (slotIndex >= INVENTORY_START && slotIndex < HOTBAR_END) {
			if (!this.moveItemStackTo(stack, INPUT_SLOTS_START, INPUT_SLOTS_END, false)) {
				if (slotIndex < HOTBAR_START) {
					if (!this.moveItemStackTo(stack, HOTBAR_START, HOTBAR_END, false)) {
						return ItemStack.EMPTY;
					}
				} else if (!this.moveItemStackTo(stack, INVENTORY_START, INVENTORY_END, false)) {
					return ItemStack.EMPTY;
				}
			}
		} else if (!this.moveItemStackTo(stack, INVENTORY_START, HOTBAR_END, false)) {
			return ItemStack.EMPTY;
		}

		if (stack.isEmpty()) {
			slot.setByPlayer(ItemStack.EMPTY);
		} else {
			slot.setChanged();
		}

		if (stack.getCount() == clicked.getCount()) {
			return ItemStack.EMPTY;
		}

		slot.onTake(player, stack);

		if (slotIndex == RESULT_SLOT) {
			player.drop(stack, false);
		}

		return clicked;
	}
	// #endregion quickMove
}
