package com.example.docs.menu.custom;

import java.util.List;
import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import com.example.docs.block.ModBlocks;
import com.example.docs.menu.ModMenuTypes;
import com.example.docs.recipe.ExampleModRecipes;
import com.example.docs.recipe.UpgradingRecipe;
import com.example.docs.recipe.UpgradingRecipeInput;

// #region menu
public class UpgradingMenu extends AbstractContainerMenu {
	private final Container input = new SimpleContainer(2) {
		@Override
		public void setChanged() {
			super.setChanged();
			UpgradingMenu.this.slotsChanged(this);
		}
	};

	private final ResultContainer output = new ResultContainer();

	private final ContainerLevelAccess access;
	private final Level level;

	public UpgradingMenu(int containerId, Inventory inventory) {
		this(containerId, inventory, ContainerLevelAccess.NULL, inventory.player.level());
	}

	public UpgradingMenu(int containerId, Inventory inventory, ContainerLevelAccess access, Level level) {
		super(ModMenuTypes.UPGRADING_MENU_TYPE, containerId);

		this.access = access;
		this.level = level;

		addSlot(new Slot(this.input, 0, 27, 47));
		addSlot(new Slot(this.input, 1, 76, 47));

		addSlot(new UpgradingResultSlot(this, this.output, 0, 134, 47));

		addStandardInventorySlots(inventory, 8, 84);
	}

	/**
	 * Called by {@link UpgradingResultSlot#onTake(Player, ItemStack)}.
	 */
	protected void onTake(final Player player, final ItemStack stack) {
		stack.onCraftedBy(player, stack.getCount());
		this.output.awardUsedRecipes(player, List.of(this.input.getItem(0), this.input.getItem(1)));
		this.input.removeItem(0, 1);
		this.input.removeItem(1, 1);
	}

	@Override
	public void slotsChanged(Container container) {
		super.slotsChanged(container);

		if (container == this.input) {
			this.createResult();
		}
	}

	public void createResult() {
		UpgradingRecipeInput recipeInput = new UpgradingRecipeInput(this.input.getItem(0), this.input.getItem(1));
		Optional<RecipeHolder<UpgradingRecipe>> maybeRecipe;

		if (this.level instanceof ServerLevel serverLevel) {
			maybeRecipe = serverLevel.recipeAccess().getRecipeFor(ExampleModRecipes.UPGRADING_RECIPE_TYPE, recipeInput, serverLevel);
		} else {
			maybeRecipe = Optional.empty();
		}

		if (maybeRecipe.isPresent()) {
			RecipeHolder<UpgradingRecipe> recipeHolder = maybeRecipe.get();
			UpgradingRecipe recipe = recipeHolder.value();

			this.output.setRecipeUsed(recipeHolder);
			this.output.setItem(0, recipe.assemble(recipeInput));
		} else {
			// We can set the used recipe to null if no recipe was found.
			this.output.setRecipeUsed(null);
			this.output.setItem(0, ItemStack.EMPTY);
		}
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(this.access, player, ModBlocks.UPGRADING_BLOCK);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.access.execute((level, blockPos) -> this.clearContainer(player, this.input));
	}

	@Override
	public boolean canTakeItemForPickAll(final ItemStack carried, final Slot target) {
		return target.container != this.output && super.canTakeItemForPickAll(carried, target);
	}
}
// #endregion menu
