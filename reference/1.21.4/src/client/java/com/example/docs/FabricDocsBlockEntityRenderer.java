package com.example.docs;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.rendering.blockentity.CounterBlockEntityRenderer;

// :::1
public class FabricDocsBlockEntityRenderer implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(ModBlockEntities.COUNTER_BLOCK_ENTITY, CounterBlockEntityRenderer::new);
	}
}
// :::1
