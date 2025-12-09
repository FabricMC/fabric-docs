package com.example.docs.block.custom;

import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.block.entity.custom.DuplicatorBlockEntity;

// :::block
public class DuplicatorBlock extends BaseEntityBlock {
	// :::block

	public DuplicatorBlock(Properties settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return simpleCodec(DuplicatorBlock::new);
	}

	// :::block
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new DuplicatorBlockEntity(pos, state);
	}

	// :::block

	@Override
	protected RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	// :::useon
	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!(world.getBlockEntity(pos) instanceof DuplicatorBlockEntity duplicatorBlockEntity)) {
			return InteractionResult.PASS;
		}

		// :::useon

		// :::place
		if (!duplicatorBlockEntity.canPlaceItemThroughFace(0, stack, hit.getDirection())) {
			return InteractionResult.PASS;
		}

		// :::place

		// :::useon
		if (!player.getItemInHand(hand).isEmpty() && duplicatorBlockEntity.isEmpty()) {
			duplicatorBlockEntity.setItem(0, player.getItemInHand(hand).copy());
			player.getItemInHand(hand).setCount(0);
		}

		return InteractionResult.SUCCESS;
	}

	// :::useon

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, ModBlockEntities.DUPLICATOR_BLOCK_ENTITY, DuplicatorBlockEntity::tick);
	}

	// :::block
	// ...
}
// :::block
