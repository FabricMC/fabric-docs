package com.example.docs.block.custom;

import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.block.entity.custom.EngineBlockEntity;

public class EngineBlock extends BaseEntityBlock {
	public static final MapCodec<EngineBlock> CODEC = simpleCodec(EngineBlock::new);

	public EngineBlock(Properties settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new EngineBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, ModBlockEntities.ENGINE_BLOCK_ENTITY, EngineBlockEntity::tick);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!(level.getBlockEntity(pos) instanceof EngineBlockEntity engineBlockEntity)) {
			return super.useWithoutItem(state, level, pos, player, hit);
		}

		if (player.getMainHandItem().is(ItemTags.COALS)) {
			if (engineBlockEntity.setFuelIfPossible(engineBlockEntity.getFuel() + 40)) {
				player.getMainHandItem().consume(1, player);
				playSound(level, SoundEvents.AXE_STRIP, pos);
				return InteractionResult.SUCCESS;
			}

			return InteractionResult.PASS;
		} else {
			if (engineBlockEntity.isRunning()) {
				engineBlockEntity.setNormalizedStress(engineBlockEntity.getNormalizedStress() + 0.2f);
				return InteractionResult.SUCCESS;
			} else if (engineBlockEntity.getFuel() > 0) {
				playSound(level, SoundEvents.LEVER_CLICK, pos);
				engineBlockEntity.turnOn();
				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.PASS;
	}

	private static void playSound(Level level, SoundEvent soundEvent, BlockPos pos) {
		if (level.isClientSide()) return;
		level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 0.8f, 1f);
	}
}
