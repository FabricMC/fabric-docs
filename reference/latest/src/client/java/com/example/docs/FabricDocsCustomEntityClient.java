package com.example.docs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import com.example.docs.entity.ModEntityTypes;
import com.example.docs.entity.model.ModEntityModelLayers;
import com.example.docs.entity.renderer.MiniGolemEntityRenderer;

public class FabricDocsCustomEntityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModEntityModelLayers.registerModelLayers();
		EntityRendererRegistry.register(ModEntityTypes.MINI_GOLEM, MiniGolemEntityRenderer::new);
	}
}
