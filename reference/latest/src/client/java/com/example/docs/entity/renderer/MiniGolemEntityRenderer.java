package com.example.docs.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import com.example.docs.ExampleMod;
import com.example.docs.entity.MiniGolemEntity;
import com.example.docs.entity.model.MiniGolemEntityModel;
import com.example.docs.entity.model.ModEntityModelLayers;
import com.example.docs.entity.state.MiniGolemEntityRenderState;

//:::renderer
public class MiniGolemEntityRenderer extends MobRenderer<MiniGolemEntity, MiniGolemEntityRenderState, MiniGolemEntityModel> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "textures/entity/mini_golem.png");

	public MiniGolemEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new MiniGolemEntityModel(context.bakeLayer(ModEntityModelLayers.MINI_GOLEM)), 0.375f); // 0.375 shadow radius
	}

	@Override
	public MiniGolemEntityRenderState createRenderState() {
		return new MiniGolemEntityRenderState();
	}
	//:::renderer

	@Override
	public void extractRenderState(MiniGolemEntity entity, MiniGolemEntityRenderState state, float tickProgress) {
		super.extractRenderState(entity, state, tickProgress);
		state.dancingAnimationState.copyFrom(entity.dancingAnimationState);
	}
	//:::renderer

	@Override
	public ResourceLocation getTextureLocation(MiniGolemEntityRenderState state) {
		return TEXTURE;
	}
}
//:::renderer
