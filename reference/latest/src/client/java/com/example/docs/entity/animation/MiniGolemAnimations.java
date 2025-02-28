package com.example.docs.entity.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class MiniGolemAnimations {
	public static final Animation DANCING = Animation.Builder.create(1)
			.looping()
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(
					Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2f, AnimationHelper.createRotationalVector(0, 0, 45), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4f, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6f, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8f, AnimationHelper.createRotationalVector(0, 0, -45), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation(EntityModelPartNames.LEFT_LEG, new Transformation(
					Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2f, AnimationHelper.createRotationalVector(0, 0, 45), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4f, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation(EntityModelPartNames.RIGHT_LEG, new Transformation(
					Transformation.Targets.ROTATE,
					new Keyframe(0.5f, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7f, AnimationHelper.createRotationalVector(0, 0, -45), Transformation.Interpolations.LINEAR),
					new Keyframe(0.9f, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.build();
}
