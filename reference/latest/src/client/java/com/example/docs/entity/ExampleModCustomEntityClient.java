package com.example.docs.entity;

import net.minecraft.client.renderer.entity.EntityRenderers;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.entity.model.ModEntityModelLayers;
import com.example.docs.entity.renderer.MiniGolemEntityRenderer;

// #region register-client
public class ExampleModCustomEntityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModEntityModelLayers.registerModelLayers();
		// #endregion register-client
		// #region register-renderer
		EntityRenderers.register(ModEntityTypes.MINI_GOLEM, MiniGolemEntityRenderer::new);
		// #endregion register-renderer
		// #region register-client
	}
}
// #endregion register-client
