package com.example.docs.block.entity.custom;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.container.ImplementedContainer;
import com.example.docs.menu.custom.DirtChestMenu;

/*
// :::be
public class DirtChestBlockEntity extends BlockEntity implements ImplementedContainer {
// :::be
*/

// :::menu
public class DirtChestBlockEntity extends BlockEntity implements ImplementedContainer, MenuProvider {
	// :::menu

	// :::be
	private final NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);

	// ...
	// :::be

	public DirtChestBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.DIRT_CHEST_BLOCK_ENTITY, blockPos, blockState);
	}

	/**
	 * Retrieves the item list of this container.
	 * Must return the same instance every time it's called.
	 */
	@Override
	public NonNullList<ItemStack> getItems() {
		return items;
	}

	// :::menu
	@Override
	@NonNull
	public Component getDisplayName() {
		return Component.translatable("block.example-mod.dirt_chest");
	}

	// :::menu

	/*
	// :::menu
	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
		return null;
	}
	// ...
	// :::menu
	 */

	// :::providerImplemented
	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
		return new DirtChestMenu(containerId, inventory, this);
	}
	// :::providerImplemented

	// :::be :::menu
}
// :::be :::menu
