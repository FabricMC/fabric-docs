package com.example.docs.projectile;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.entity.ModEntityTypes;

// #region entrypoint
public class ExampleModProjectileClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// region renderer
		EntityRenderers.register(ModEntityTypes.HOT_TATER, ThrownItemRenderer::new);
		// endregion renderer
	}
}
// #endregion entrypoint
