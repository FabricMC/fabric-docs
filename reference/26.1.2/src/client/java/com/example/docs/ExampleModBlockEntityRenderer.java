package com.example.docs;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.rendering.blockentity.CounterBlockEntityRenderer;

// #region register_block_entity_renderer
public class ExampleModBlockEntityRenderer implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRenderers.register(ModBlockEntities.COUNTER_BLOCK_ENTITY, CounterBlockEntityRenderer::new);
	}
}
// #endregion register_block_entity_renderer
