package com.example.docs.block.entity.custom;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.container.ImplementedContainer;
import com.example.docs.menu.custom.DirtChestMenu;

/*
// #region be
public class DirtChestBlockEntity extends BlockEntity implements ImplementedContainer {
// #endregion be
*/

// #region menu
public class DirtChestBlockEntity extends BlockEntity implements ImplementedContainer, MenuProvider {
	// #endregion menu

	// #region be
	public static final int CONTAINER_SIZE = 3 * 3;
	private final NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);

	// ...
	// #endregion be

	public DirtChestBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.DIRT_CHEST_BLOCK_ENTITY, pos, state);
	}

	/**
	 * Retrieves the item list of this container.
	 * Must return the same instance every time it's called.
	 */
	@Override
	public NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	protected void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);
		ContainerHelper.saveAllItems(valueOutput, items);
	}

	@Override
	protected void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);
		ContainerHelper.loadAllItems(valueInput, items);
	}

	// #region menu
	@Override
	@NonNull
	public Component getDisplayName() {
		return Component.translatable("block.example-mod.dirt_chest");
	}

	// #endregion menu

	/*
	// #region menu
	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
		return null;
	}
	// ...
	// #endregion menu
	 */

	// #region provider-implemented
	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
		return new DirtChestMenu(containerId, inventory, this);
	}
	// #endregion provider-implemented

	// #region be
	// #region menu
}
// #endregion be
// #endregion menu
