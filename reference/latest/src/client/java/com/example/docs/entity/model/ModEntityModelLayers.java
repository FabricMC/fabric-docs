package com.example.docs.entity.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

import com.example.docs.ExampleMod;

// #region model-layer
public class ModEntityModelLayers {
	public static final ModelLayerLocation MINI_GOLEM = createMain("mini_golem");

	private static ModelLayerLocation createMain(String name) {
		return new ModelLayerLocation(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name), "main");
	}

	public static void registerModelLayers() {
		ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.MINI_GOLEM, MiniGolemEntityModel::getTexturedModelData);
	}
}
// #endregion model-layer
