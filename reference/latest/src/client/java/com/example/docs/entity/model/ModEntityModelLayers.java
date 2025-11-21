package com.example.docs.entity.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

import com.example.docs.ExampleMod;

//:::model_layer
public class ModEntityModelLayers {
	public static final ModelLayerLocation MINI_GOLEM = createMain("mini_golem");

	private static ModelLayerLocation createMain(String name) {
		return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, name), "main");
	}

	public static void registerModelLayers() {
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.MINI_GOLEM, MiniGolemEntityModel::getTexturedModelData);
	}
}
//::model_layer
