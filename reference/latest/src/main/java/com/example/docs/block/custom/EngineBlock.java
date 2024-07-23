package com.example.docs.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.block.entity.custom.EngineBlockEntity;

public class EngineBlock extends BlockWithEntity {
	public static final MapCodec<EngineBlock> CODEC = createCodec(EngineBlock::new);

	public EngineBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new EngineBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, ModBlockEntities.ENGINE_BLOCK_ENTITY, EngineBlockEntity::tick);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!(world.getBlockEntity(pos) instanceof EngineBlockEntity blockEntity))
			return super.onUse(state, world, pos, player, hit);

		if (blockEntity.isTicking()) return super.onUse(state, world, pos, player, hit);
		blockEntity.setTick(0);    // starts ticking
		return ActionResult.SUCCESS;
	}
}
