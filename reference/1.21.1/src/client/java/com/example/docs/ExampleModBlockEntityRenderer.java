package com.example.docs;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.rendering.blockentity.CounterBlockEntityRenderer;

// :::1
public class ExampleModBlockEntityRenderer implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRenderers.register(ModBlockEntities.COUNTER_BLOCK_ENTITY, CounterBlockEntityRenderer::new);
	}
}
// :::1
