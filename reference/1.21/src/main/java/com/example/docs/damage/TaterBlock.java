package com.example.docs.damage;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// :::1
public class TaterBlock extends Block {
	public TaterBlock(Settings settings) {
		super(settings);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (entity instanceof LivingEntity) {
			DamageSource damageSource = new DamageSource(
					world.getRegistryManager()
							.get(RegistryKeys.DAMAGE_TYPE)
							.entryOf(FabricDocsReferenceDamageTypes.TATER_DAMAGE));
			entity.damage(damageSource, 5.0f);
		}
	}
}
// :::1
