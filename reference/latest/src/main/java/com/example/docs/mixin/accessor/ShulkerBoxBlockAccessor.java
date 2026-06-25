package com.example.docs.mixin.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;

import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

//#region mixin_accessors_static_invoker_example
@Mixin(ShulkerBoxBlock.class)
public interface ShulkerBoxBlockAccessor {
	@Invoker("canOpen")
	static boolean canOpen(BlockState state, Level level, BlockPos pos, ShulkerBoxBlockEntity blockEntity) {
		throw new AssertionError("Untransformed @Accessor");
	}
}
//#endregion mixin_accessors_static_invoker_example
