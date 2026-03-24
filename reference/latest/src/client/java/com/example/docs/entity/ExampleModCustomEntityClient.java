package com.example.docs.entity;

import net.minecraft.client.renderer.entity.EntityRenderers;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.entity.model.ModEntityModelLayers;
import com.example.docs.entity.renderer.MiniGolemEntityRenderer;

//:::register_client
public class ExampleModCustomEntityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModEntityModelLayers.registerModelLayers();
		//:::register_client
		//:::register_renderer
		EntityRenderers.register(ModEntityTypes.MINI_GOLEM, MiniGolemEntityRenderer::new);
		//:::register_renderer
		//:::register_client
	}
}
//:::register_client
