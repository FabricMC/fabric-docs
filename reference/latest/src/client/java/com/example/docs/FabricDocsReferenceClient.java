package com.example.docs;

import net.minecraft.client.particle.EndRodParticle;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class FabricDocsReferenceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		ParticleFactoryRegistry.getInstance().register(FabricDocsReference.MY_PARTICLE, EndRodParticle.Factory::new);
	}
}
