package com.example.docs.damage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

// #region complete-block
public class TaterBlock extends Block {
	public TaterBlock(Properties settings) {
		super(settings);
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		if (entity instanceof LivingEntity && level instanceof ServerLevel serverLevel) {
			// #region create-damage-source
			DamageSource damageSource = new DamageSource(
					level.registryAccess()
							.lookupOrThrow(Registries.DAMAGE_TYPE)
							.get(ExampleModDamageTypes.TATER_DAMAGE.identifier()).orElseThrow());
			// #endregion create-damage-source
			// #region hurt-entity
			entity.hurtServer(serverLevel, damageSource, 5.0f);
			// #endregion hurt-entity
		}
	}
}
// #endregion complete-block
