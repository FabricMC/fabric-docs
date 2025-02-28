package com.example.docs.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import com.example.docs.FabricDocsReference;
import com.example.docs.entity.MiniGolemEntity;
import com.example.docs.entity.model.MiniGolemEntityModel;
import com.example.docs.entity.model.ModEntityModelLayers;
import com.example.docs.entity.state.MiniGolemEntityRenderState;

public class MiniGolemEntityRenderer extends MobEntityRenderer<MiniGolemEntity, MiniGolemEntityRenderState, MiniGolemEntityModel> {
	private static final Identifier TEXTURE = Identifier.of(FabricDocsReference.MOD_ID, "textures/entity/mini_golem.png");

	public MiniGolemEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new MiniGolemEntityModel(context.getPart(ModEntityModelLayers.MINI_GOLEM)), 0.375f);
	}

	@Override
	public MiniGolemEntityRenderState createRenderState() {
		return new MiniGolemEntityRenderState();
	}

	@Override
	public void updateRenderState(MiniGolemEntity entity, MiniGolemEntityRenderState state, float tickProgress) {
		super.updateRenderState(entity, state, tickProgress);
		state.dancingAnimationState.copyFrom(entity.dancingAnimationState);
	}

	@Override
	public Identifier getTexture(MiniGolemEntityRenderState state) {
		return TEXTURE;
	}
}
