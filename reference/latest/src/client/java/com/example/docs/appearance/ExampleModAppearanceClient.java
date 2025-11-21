package com.example.docs.appearance;

import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

import com.example.docs.ExampleMod;

public class ExampleModAppearanceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// :::item_tint_source
		ItemTintSources.ID_MAPPER.put(ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "color"), RainTintSource.MAP_CODEC);
		// :::item_tint_source
		// :::color_provider
		ColorProviderRegistry.BLOCK.register((blockState, blockAndTintGetter, blockPos, i) -> {
			if (blockAndTintGetter != null && blockPos != null) {
				BlockState stateBelow = blockAndTintGetter.getBlockState(blockPos.below());

				if (stateBelow.is(Blocks.GRASS_BLOCK)) {
					return 0x98FB98; //Color code in hex format.
				}
			}

			return 0xFFDAB9; // Color code in hex format.
		}, ExampleModAppearance.WAXCAP_BLOCK);
		// :::color_provider

		// :::block_render_layer_map
		BlockRenderLayerMap.putBlock(ExampleModAppearance.WAXCAP_BLOCK, ChunkSectionLayer.CUTOUT);
		// :::block_render_layer_map
	}
}
