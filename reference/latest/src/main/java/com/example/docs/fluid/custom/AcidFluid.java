package com.example.docs.fluid.custom;

import java.util.Optional;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import com.example.docs.block.ModBlocks;
import com.example.docs.fluid.ModFluidTags;
import com.example.docs.fluid.ModFluids;
import com.example.docs.item.ModItems;

// #region abstract-fluid
public abstract class AcidFluid extends FlowingFluid {
	// #endregion abstract-fluid

	// #region sources
	@Override
	public Fluid getFlowing() {
		return ModFluids.ACID_FLOWING;
	}

	@Override
	public Fluid getSource() {
		return ModFluids.ACID_STILL;
	}

	@Override
	public boolean isSame(Fluid fluid) {
		return fluid == ModFluids.ACID_STILL || fluid == ModFluids.ACID_FLOWING;
	}

	// #endregion sources
	// #region bucket
	@Override
	public Item getBucket() {
		return ModItems.ACID_BUCKET;
	}

	// #endregion bucket
	// #region abstract-fluid
	@Override
	public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {
		if (!state.isSource() && !(Boolean) state.getValue(FALLING)) {
			if (random.nextInt(64) == 0) {
				world.playLocalSound(
						pos.getX() + 0.5,
						pos.getY() + 0.5,
						pos.getZ() + 0.5,
						SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, // Bubbling poison/swamp sound
						SoundSource.AMBIENT,
						random.nextFloat() * 0.25F + 0.75F,
						random.nextFloat() + 0.5F,
						false);
			}
		} else if (random.nextInt(10) == 0) {
			world.addParticle(
					ParticleTypes.UNDERWATER, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(),
					pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
		}
	}

	@Nullable
	@Override
	public ParticleOptions getDripParticle() {
		return ParticleTypes.DRIPPING_WATER;
	}

	@Override
	protected boolean canConvertToSource(ServerLevel world) {
		return world.getGameRules().get(GameRules.WATER_SOURCE_CONVERSION);
	}

	@Override
	protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
		BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
		Block.dropResources(state, world, pos, blockEntity);
	}

	@Override
	protected void entityInside(Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler) {
		handler.apply(InsideBlockEffectType.EXTINGUISH);

		if (!(world instanceof ServerLevel serverLevel) || !(entity instanceof LivingEntity livingEntity)) return;

		if (world.getGameTime() % 20 == 0) {
			// Hurt and weaken entities that step inside.
			livingEntity.hurtServer(serverLevel, world.damageSources().magic(), 2.0F); // 1 heart/sec
			livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, -3));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 300, -3));
		}
	}

	@Override
	protected int getSlopeFindDistance(LevelReader world) {
		return 4;
	}

	// #endregion abstract-fluid
	// #region legacy-block
	@Override
	protected BlockState createLegacyBlock(FluidState state) {
		return ModBlocks.ACID.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
	}
	// #endregion legacy-block
	// #region abstract-fluid
	@Override
	public int getDropOff(LevelReader world) {
		return 1;
	}

	@Override
	public int getTickDelay(LevelReader world) {
		return 5;
	}

	@Override
	public boolean canBeReplacedWith(FluidState state, BlockGetter world, BlockPos pos, Fluid fluid,
			Direction direction) {
		return direction == Direction.DOWN && !fluid.is(ModFluidTags.ACID);
	}

	@Override
	protected float getExplosionResistance() {
		return 100.0F;
	}

	@Override
	public Optional<SoundEvent> getPickupSound() {
		return Optional.of(SoundEvents.BUCKET_FILL);
	}
	// #endregion abstract-fluid
	// #region fluid-subclasses
	public static class Flowing extends AcidFluid {
		@Override
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		@Override
		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
		}

		@Override
		public boolean isSource(FluidState state) {
			return false;
		}
	}

	public static class Source extends AcidFluid {
		@Override
		public int getAmount(FluidState state) {
			return 8;
		}

		@Override
		public boolean isSource(FluidState state) {
			return true;
		}
	}
	// #endregion fluid-subclasses
	// #region abstract-fluid
}
// #endregion abstract-fluid
