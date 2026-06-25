package com.example.docs.menu.custom;

import java.util.List;
import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

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

	private final Level level;

	public UpgradingMenu(int i, Inventory inventory) {
		super(ExampleModRecipes.UPGRADING_MENU_TYPE, i);

		this.level = inventory.player.level();

		addSlot(new Slot(this.input, 0, 27, 47));
		addSlot(new Slot(this.input, 1, 76, 47));

		addSlot(new Slot(this.output, 0, 134, 47) {
			@Override
			public void onTake(Player player, ItemStack itemStack) {
				UpgradingMenu.this.onTake(player, itemStack);
			}
		});

		addStandardInventorySlots(inventory, 8, 84);
	}

	@Override
	public void slotsChanged(Container container) {
		super.slotsChanged(container);

		if (container == this.input) {
			if (this.level instanceof ServerLevel serverLevel) {
				UpgradingRecipeInput recipeInput = new UpgradingRecipeInput(this.input.getItem(0), this.input.getItem(1));
				Optional<RecipeHolder<UpgradingRecipe>> recipe = serverLevel.recipeAccess().getRecipeFor(ExampleModRecipes.UPGRADING_RECIPE_TYPE, recipeInput, serverLevel);

				if (recipe.isPresent()) {
					this.output.setItem(0, recipe.get().value().assemble(recipeInput));
					this.output.setRecipeUsed(recipe.get());
				} else {
					this.output.clearContent();
					this.output.setRecipeUsed(null);
				}
			}
		}
	}

	public void onTake(Player player, ItemStack stack) {
		stack.onCraftedBy(player, stack.getCount());
		this.output.awardUsedRecipes(player, List.of(this.input.getItem(0), this.input.getItem(1)));

		this.input.removeItem(0, stack.getCount());
		this.input.removeItem(1, stack.getCount());
	}

	@Override
	public ItemStack quickMoveStack(Player player, int i) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		clearContainer(player, this.input);
	}
}
// #endregion menu
