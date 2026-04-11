package com.example.docs.entity.model;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

import com.example.docs.entity.animation.MiniGolemAnimations;
import com.example.docs.entity.state.MiniGolemEntityRenderState;

// #region model1
public class MiniGolemEntityModel extends EntityModel<MiniGolemEntityRenderState> {
	private final ModelPart head;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	// #endregion model1
	// #region dancing-animation
	private final KeyframeAnimation dancing;

	// #endregion dancing-animation
	// #region model1

	// #region dancing-animation
	public MiniGolemEntityModel(ModelPart root) {
		// #endregion dancing-animation
		super(root);
		head = root.getChild(PartNames.HEAD);
		leftLeg = root.getChild(PartNames.LEFT_LEG);
		rightLeg = root.getChild(PartNames.RIGHT_LEG);
		// #endregion model1
		// #region dancing-animation
		// ...
		this.dancing = MiniGolemAnimations.DANCING.bake(root);
		// #region model1
	}
	// #endregion dancing-animation
	// #endregion model1

	// #region model-texture-data
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();
		root.addOrReplaceChild(
				PartNames.BODY,
				CubeListBuilder.create().addBox(
						/* x */ -6,
						/* y */ -6,
						/* z */ -6,
						/* width */ 12,
						/* height */ 12,
						/* depth */ 12
				),
				PartPose.offset(0, 8, 0)
		);
		root.addOrReplaceChild(
				PartNames.HEAD,
				CubeListBuilder.create().texOffs(36, 0).addBox(-3, -6, -3, 6, 6, 6),
				PartPose.offset(0, 2, 0)
		);
		root.addOrReplaceChild(
				PartNames.LEFT_LEG,
				CubeListBuilder.create().texOffs(48, 12).addBox(-2, 0, -2, 4, 10, 4),
				PartPose.offset(-2.5f, 14, 0)
		);
		root.addOrReplaceChild(
				PartNames.RIGHT_LEG,
				CubeListBuilder.create().texOffs(48, 12).addBox(-2, 0, -2, 4, 10, 4),
				PartPose.offset(2.5f, 14, 0)
		);
		return LayerDefinition.create(modelData, 64, 32);
	}
	// #endregion model-texture-data

	// #region model-animation
	// #region dancing-animation
	@Override
	public void setupAnim(MiniGolemEntityRenderState state) {
		super.setupAnim(state);

		// #endregion model-animation
		if (state.dancingAnimationState.isStarted()) {
			this.dancing.apply(state.dancingAnimationState, state.ageInTicks);
		} else {
			// ... the leg swing animation code from before
			// #endregion dancing-animation
			// #region model-animation
			head.xRot = state.xRot * Mth.RAD_TO_DEG;
			head.yRot = state.yRot * Mth.RAD_TO_DEG;
			float limbSwingAmplitude = state.walkAnimationSpeed;
			float limbSwingAnimationProgress = state.walkAnimationPos;
			leftLeg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
			rightLeg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
			// #endregion model-animation
			// #region dancing-animation
		}
		// #region model-animation
	}
	// #endregion dancing-animation
}
// #endregion model-animation
