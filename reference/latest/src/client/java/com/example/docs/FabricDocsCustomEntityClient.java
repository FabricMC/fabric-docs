package com.example.docs;

import com.example.docs.entity.CustomEntityModel;
import com.example.docs.entity.FabricDocsReferanceCustomEntity;
import com.example.docs.entity.CustomEntityRender;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FabricDocsCustomEntityClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {

		EntityModelLayerRegistry.registerModelLayer(CustomEntityModel.CustomEntity, CustomEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(FabricDocsReferanceCustomEntity.CustomEntity, CustomEntityRender);
		//TODO: Temp Need Help & Review
	}
}