package com.example.docs.entity.model;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

import com.example.docs.FabricDocsReference;

public class ModEntityModelLayers {
	public static final EntityModelLayer MINI_GOLEM = createMain("mini_golem");

	private static EntityModelLayer createMain(String name) {
		return new EntityModelLayer(Identifier.of(FabricDocsReference.MOD_ID, name), "main");
	}

	public static void registerModelLayers() {
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.MINI_GOLEM, MiniGolemEntityModel::getTexturedModelData);
	}
}
