package com.example.docs;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.object.equipment.ShieldModel;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

import com.example.docs.component.ModComponents;
import com.example.docs.item.shield.GuiditeShieldLayers;
import com.example.docs.item.shield.GuiditeShieldSpecialRenderer;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		// #particle_register_client
		// For this example, we will use the end rod particle behaviour.
		ParticleProviderRegistry.getInstance().register(ExampleMod.SPARKLE_PARTICLE, EndRodParticle.Provider::new);
		// #particle_register_client

		// #tooltip_provider_client
		ItemTooltipCallback.EVENT.register((stack, context, type, tooltip) -> {
			Integer count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);

			if (count != null) {
				tooltip.add(Component.translatable("item.example-mod.counter.info", count).withStyle(ChatFormatting.GOLD));
			}
		});
		// #tooltip_provider_client
		// #region shield-layer
		SpecialModelRenderers.ID_MAPPER.put(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "guidite_shield"), GuiditeShieldSpecialRenderer.Unbaked.MAP_CODEC);
		ModelLayerRegistry.registerModelLayer(GuiditeShieldLayers.GUIDITE_SHIELD, ShieldModel::createLayer);
		// #endregion shield-layer

	}
}
