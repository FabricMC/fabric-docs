package com.example.docs.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

//#region mixin_accessors_instance_invoker_example
@Mixin(Inventory.class)
public interface InventoryAccessor {
	@Invoker("hasRemainingSpaceForItem")
	boolean example_mod$hasRemainingSpaceForItem(ItemStack slotItemStack, ItemStack newItemStack);
}
//#endregion mixin_accessors_instance_invoker_example
