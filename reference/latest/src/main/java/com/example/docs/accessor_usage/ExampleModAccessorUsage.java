package com.example.docs.accessor_usage;

import com.example.docs.mixin.accessor.ClientboundCustomPayloadPacketAccessor;
import com.example.docs.mixin.accessor.InventoryAccessor;
import com.example.docs.mixin.accessor.ShulkerBoxBlockAccessor;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

import com.example.docs.mixin.accessor.IdentifierAccessor;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// Class to hold example code for using the example accessor mixins
@SuppressWarnings("unused")
final class ExampleModAccessorUsage {
	private ExampleModAccessorUsage() {
	}

	//#region mixin_accessors_static_field_accessor_example_usage
	void exampleStaticFieldAccessorUsage(int newPayloadSize) {
		int maxPayloadSize = ClientboundCustomPayloadPacketAccessor.getMaxPayloadSize();
	
		ClientboundCustomPayloadPacketAccessor.setMaxPayloadSize(newPayloadSize);
	}
	//#endregion mixin_accessors_static_field_accessor_example_usage

	@SuppressWarnings("StatementWithEmptyBody")
	//#region mixin_accessors_static_invoker_example_usage
	void exampleStaticMethodInvokerUsage(BlockState state, Level level, BlockPos pos, ShulkerBoxBlockEntity blockEntity) {
		if (ShulkerBoxBlockAccessor.canOpen(state, level, pos, blockEntity)) {
			/* ... */
		}
	}
	//#endregion mixin_accessors_static_invoker_example_usage

	@SuppressWarnings("StatementWithEmptyBody")
	//#region mixin_accessors_instance_invoker_example_usage
	void exampleInstanceMethodInvokerUsage(Inventory inventory, ItemStack slotItemStack, ItemStack newItemStack) {
		if (((InventoryAccessor) inventory).example_mod$hasRemainingSpaceForItem(slotItemStack, newItemStack)) {
			/* ... */
		}
	}
	//#endregion mixin_accessors_instance_invoker_example_usage

	//#region mixin_accessors_constructor_invoker_example_usage
	void exampleConstructorInvokerUsage(String namespace, String path) {
		Identifier identifier = IdentifierAccessor.newIdentifier(namespace, path);
	}
	//#endregion mixin_accessors_constructor_invoker_example_usage
}
