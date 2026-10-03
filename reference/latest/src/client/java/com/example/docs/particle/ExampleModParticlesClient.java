package com.example.docs.particle;

import net.minecraft.client.particle.EndRodParticle;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;

// #region entrypoint
public class ExampleModParticlesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Registers a ParticleProvider for your ParticleType
		// For this example, we use the end rod particle's logic via EndRodParticle.Provider::new
		ParticleProviderRegistry.getInstance().register(ExampleModParticles.SPARKLE_PARTICLE, EndRodParticle.Provider::new);
	}
}
// #endregion entrypoint
