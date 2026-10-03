package com.example.docs.particle;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.BlockEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

import com.example.docs.ExampleMod;

// #region entrypoint
public class ExampleModParticles implements ModInitializer {
	// This is your ParticleType, you'll use it any time you want to spawn your particle in code
	public static final SimpleParticleType SPARKLE_PARTICLE = FabricParticleTypes.simple();

	@Override
	public void onInitialize() {
		// Registers your ParticleType with the Identifier path of "sparkle_particle"
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, ExampleMod.id("sparkle_particle"), ExampleModParticles.SPARKLE_PARTICLE);
		// #endregion entrypoint

		// An example of adding or sending particles after interacting with a block while holding a firework star
		BlockEvents.USE_ITEM_ON.register((itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult) -> {
			if (itemStack.is(Items.FIREWORK_STAR)) {
				if (level.isClientSide()) {
					// #region client_send_particles
					// Determine positions (in this case, from a previous BlockPos variable - adding 0.5 to make sure it's centered)
					double x = blockPos.getX() + 0.5;
					double y = blockPos.getY() + 0.5;
					double z = blockPos.getZ() + 0.5;
					// Determine velocities
					// nextDouble() generates a number from 0 to 1 (inclusive), which we then limit to 0.15 total
					double velocityX = level.getRandom().nextDouble() * 0.15;
					double velocityY = level.getRandom().nextDouble() * 0.15;
					double velocityZ = level.getRandom().nextDouble() * 0.15;
					// Spawn our particle by passing our ParticleType
					level.addParticle(ExampleModParticles.SPARKLE_PARTICLE, x, y, z, velocityX, velocityY, velocityZ);
					// #endregion
				} else if (level instanceof ServerLevel serverLevel) {
					// #region server_send_particles
					// Determine positions (in this case, from a previous BlockPos variable - adding 0.5 to make sure it's centered)
					double x = blockPos.getX() + 0.5;
					double y = blockPos.getY() + 0.5;
					double z = blockPos.getZ() + 0.5;
					// Determine count
					int count = 2;
					// Determine maximum random distance from the initial position
					double distX = 0.25;
					double distY = 0.25;
					double distZ = 0.25;
					// Determine maximum random speed used for the velocities
					double speed = 0.15;
					// Send our particle by passing our ParticleType
					serverLevel.sendParticles(ExampleModParticles.SPARKLE_PARTICLE, x, y, z, count, distX, distY, distZ, speed);
					// #endregion
				}
			}

			// Don't actually change the result of the use item on event
			return InteractionResult.PASS;
		});

		// #region entrypoint
	}
}
// #endregion entrypoint
