package com.example.docs.appearance;

import java.util.List;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;

import com.example.docs.ExampleMod;
import com.example.docs.block.ModBlocks;
import com.example.docs.fluid.ModFluids;

public class ExampleModAppearanceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// :::item_tint_source
		ItemTintSources.ID_MAPPER.put(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "color"), RainTintSource.MAP_CODEC);
		// :::item_tint_source
		// :::color_provider
		BlockColorRegistry.register(List.of(new BlockTintSource() {
			@Override
			public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
				BlockState stateBelow = level.getBlockState(pos.below());

				if (stateBelow.is(Blocks.GRASS_BLOCK)) {
					return 0xFF98FB98; // Color code in hex format
				}

				return 0xFFFFDAB9; // Color code in hex format
			}

			@Override
			public int color(BlockState state) {
				return 0xFFFFDAB9; // Color code in hex format
			}
		}), ModBlocks.WAXCAP);
		// :::color_provider

		// :::fluid_texture
		FluidRenderingRegistry.register(
				ModFluids.ACID_STILL,
				ModFluids.ACID_FLOWING,
				new FluidModel.Unbaked(
						new Material(Identifier.withDefaultNamespace("block/water_still")),
						new Material(Identifier.withDefaultNamespace("block/water_flow")),
						new Material(Identifier.withDefaultNamespace("block/water_overlay")),
						BlockTintSources.constant(0xFF075800)
				)
		);
		// :::fluid_texture
	}
}
