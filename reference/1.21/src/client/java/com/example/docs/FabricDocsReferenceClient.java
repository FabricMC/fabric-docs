package com.example.docs;

import net.minecraft.client.particle.EndRodParticle;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class FabricDocsReferenceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		// #particle_register_client
		// For this example, we will use the end rod particle behaviour.
		ParticleFactoryRegistry.getInstance().register(FabricDocsReference.SPARKLE_PARTICLE, EndRodParticle.Factory::new);
		// #particle_register_client
	}
}
