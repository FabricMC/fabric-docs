package com.example.docs.block.custom;

import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
		if (!(world.getBlockEntity(pos) instanceof EngineBlockEntity engineBlockEntity)) {
			return super.onUse(state, world, pos, player, hit);
		}

		if (player.getMainHandStack().isIn(ItemTags.COALS)) {
			if (engineBlockEntity.setFuelIfPossible(engineBlockEntity.getFuel() + 40)) {
				player.getMainHandStack().decrementUnlessCreative(1, player);
				playSound(world, SoundEvents.ITEM_AXE_STRIP, pos);
				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		} else {
			if (engineBlockEntity.isRunning()) {
				engineBlockEntity.setNormalizedStress(engineBlockEntity.getNormalizedStress() + 0.2f);
				return ActionResult.SUCCESS;
			} else if (engineBlockEntity.getFuel() > 0) {
				playSound(world, SoundEvents.BLOCK_LEVER_CLICK, pos);
				engineBlockEntity.turnOn();
				return ActionResult.SUCCESS;
			}
		}

		return ActionResult.PASS;
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	private static void playSound(World world, SoundEvent soundEvent, BlockPos pos) {
		if (world.isClient()) return;
		world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 0.8f, 1f);
	}
}
