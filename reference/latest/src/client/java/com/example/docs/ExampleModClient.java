package com.example.docs;

import net.minecraft.ChatFormatting;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.network.chat.Component;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;

import com.example.docs.component.ModComponents;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		// #region particle-register-client
		// For this example, we will use the end rod particle behaviour.
		ParticleProviderRegistry.getInstance().register(ExampleMod.SPARKLE_PARTICLE, EndRodParticle.Provider::new);
		// #endregion particle-register-client

		// #region tooltip-provider-client
		ItemTooltipCallback.EVENT.register((stack, context, type, tooltip) -> {
			Integer count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);

			if (count != null) {
				tooltip.add(Component.translatable("item.example-mod.counter.info", count).withStyle(ChatFormatting.GOLD));
			}
		});
		// #endregion tooltip-provider-client
	}
}
