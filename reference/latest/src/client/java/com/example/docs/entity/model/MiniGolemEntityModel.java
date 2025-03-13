package com.example.docs.entity.model;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;

import com.example.docs.entity.animation.MiniGolemAnimations;
import com.example.docs.entity.state.MiniGolemEntityRenderState;
//:::model
public class MiniGolemEntityModel extends EntityModel<MiniGolemEntityRenderState> {
	private final ModelPart head;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public MiniGolemEntityModel(ModelPart root) {
		super(root);
		head = root.getChild(EntityModelPartNames.HEAD);
		leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);
		rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();
		root.addChild(
				EntityModelPartNames.BODY,
				ModelPartBuilder.create().cuboid(-6, -6, -6, 12, 12, 12),
				ModelTransform.pivot(0, 8, 0)
		);
		root.addChild(
				EntityModelPartNames.HEAD,
				ModelPartBuilder.create().uv(36, 0).cuboid(-3, -6, -3, 6, 6, 6),
				ModelTransform.pivot(0, 2, 0)
		);
		root.addChild(
				EntityModelPartNames.LEFT_LEG,
				ModelPartBuilder.create().uv(48, 12).cuboid(-2, 0, -2, 4, 10, 4),
				ModelTransform.pivot(-2.5f, 14, 0)
		);
		root.addChild(
				EntityModelPartNames.RIGHT_LEG,
				ModelPartBuilder.create().uv(48, 12).cuboid(-2, 0, -2, 4, 10, 4),
				ModelTransform.pivot(2.5f, 14, 0)
		);
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void setAngles(MiniGolemEntityRenderState state) {
		super.setAngles(state);

		if (state.dancingAnimationState.isRunning()) {
			this.animate(state.dancingAnimationState, MiniGolemAnimations.DANCING, state.age);
		} else {
			head.pitch = state.pitch * MathHelper.RADIANS_PER_DEGREE;
			head.yaw = state.yawDegrees * MathHelper.RADIANS_PER_DEGREE;
			float limbSwingAmplitude = state.limbAmplitudeMultiplier;
			float limbSwingAnimationProgress = state.limbFrequency;
			leftLeg.pitch = MathHelper.cos(limbSwingAnimationProgress * 0.2f + MathHelper.PI) * 1.4f * limbSwingAmplitude;
			rightLeg.pitch = MathHelper.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
		}
	}
}
//:::model