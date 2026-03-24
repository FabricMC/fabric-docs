package com.example.docs.damage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

// :::1
public class TaterBlock extends Block {
	public TaterBlock(Properties settings) {
		super(settings);
	}

	@Override
	public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
		if (entity instanceof LivingEntity) {
			DamageSource damageSource = new DamageSource(
					world.registryAccess()
							.registryOrThrow(Registries.DAMAGE_TYPE)
							.getHolderOrThrow(ExampleModDamageTypes.TATER_DAMAGE));
			entity.hurt(damageSource, 5.0f);
		}
	}
}
// :::1
