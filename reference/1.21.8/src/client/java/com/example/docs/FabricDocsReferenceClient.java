package com.example.docs;

import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

import com.example.docs.component.ModComponents;

public class FabricDocsReferenceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		// #particle_register_client
		// For this example, we will use the end rod particle behaviour.
		ParticleFactoryRegistry.getInstance().register(FabricDocsReference.SPARKLE_PARTICLE, EndRodParticle.Factory::new);
		// #particle_register_client

		// #tooltip_provider_client
		ItemTooltipCallback.EVENT.register((stack, context, type, tooltip) -> {
			int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
			tooltip.add(Text.translatable("item.fabric-docs-reference.counter.info", count).formatted(Formatting.GOLD));
		});
		// #tooltip_provider_client
	}
}
