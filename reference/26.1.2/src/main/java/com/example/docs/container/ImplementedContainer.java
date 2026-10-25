package com.example.docs.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * A simple {@link Container} implementation with only default methods + an item list getter.
 *
 * @author Juuz
 */
public interface ImplementedContainer extends Container {
	/**
	 * Retrieves the item list of this container.
	 * Must return the same instance every time it's called.
	 */
	NonNullList<ItemStack> getItems();

	/**
	 * Creates a container from the item list.
	 */
	static ImplementedContainer of(NonNullList<ItemStack> items) {
		return () -> items;
	}

	/**
	 * Creates a new container with the specified size.
	 */
	static ImplementedContainer ofSize(int size) {
		return of(NonNullList.withSize(size, ItemStack.EMPTY));
	}

	/**
	 * Returns the container size.
	 */
	@Override
	default int getContainerSize() {
		return this.getItems().size();
	}

	/**
	 * Checks if the container is empty.
	 * @return true if this container has only empty stacks, false otherwise.
	 */
	@Override
	default boolean isEmpty() {
		for (int i = 0; i < this.getContainerSize(); i++) {
			ItemStack stack = this.getItem(i);

			if (!stack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Retrieves the item in the slot.
	 */
	@Override
	default ItemStack getItem(int slot) {
		return this.getItems().get(slot);
	}

	/**
	 * Removes items from a container slot.
	 * @param slot  The slot to remove from.
	 * @param count How many items to remove. If there are fewer items in the slot than what are requested,
	 *              takes all items in that slot.
	 */
	@Override
	default ItemStack removeItem(int slot, int count) {
		ItemStack result = ContainerHelper.removeItem(this.getItems(), slot, count);

		if (!result.isEmpty()) {
			this.setChanged();
		}

		return result;
	}

	/**
	 * Removes all items from a container slot.
	 * @param slot The slot to remove from.
	 */
	@Override
	default ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(this.getItems(), slot);
	}

	/**
	 * Replaces the current stack in an container slot with the provided stack.
	 * @param slot  The container slot of which to replace the item stack.
	 * @param stack The replacing item stack. If the stack is too big for
	 *              this container ({@link Container#getMaxStackSize()}),
	 *              it gets resized to this container's maximum amount.
	 */
	@Override
	default void setItem(int slot, ItemStack stack) {
		this.getItems().set(slot, stack);

		stack.limitSize(this.getMaxStackSize(stack));

		this.setChanged();
	}

	/**
	 * Clears the container.
	 */
	@Override
	default void clearContent() {
		this.getItems().clear();
	}

	/**
	 * Marks that the state has changed.
	 * Must be called after changes in the container, so that the game can properly save
	 * the container contents and notify neighboring blocks of container changes.
	 */
	@Override
	default void setChanged() {
		// Override if you want behavior.
	}

	/**
	 * @return true if the player can use the container, false otherwise.
	 */
	@Override
	default boolean stillValid(Player player) {
		return true;
	}
}
