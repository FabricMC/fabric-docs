package com.example.docs.entity;

import net.minecraft.client.renderer.entity.EntityRenderers;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.entity.model.ModEntityModelLayers;
import com.example.docs.entity.renderer.MiniGolemEntityRenderer;

// #region register_client
public class ExampleModCustomEntityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModEntityModelLayers.registerModelLayers();
		// #endregion register_client
		// #region register_renderer
		EntityRenderers.register(ModEntityTypes.MINI_GOLEM, MiniGolemEntityRenderer::new);
		// #endregion register_renderer
		// #region register_client
	}
}
// #endregion register_client
