package com.example.docs.entity.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.geom.PartNames;

// :::dancing_animation
public class MiniGolemAnimations {
	public static final AnimationDefinition DANCING = AnimationDefinition.Builder.withLength(1)
			.looping()
			.addAnimation(PartNames.HEAD, new AnimationChannel(
					AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2f, KeyframeAnimations.degreeVec(0, 0, 45), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8f, KeyframeAnimations.degreeVec(0, 0, -45), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation(PartNames.LEFT_LEG, new AnimationChannel(
					AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2f, KeyframeAnimations.degreeVec(0, 0, 45), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation(PartNames.RIGHT_LEG, new AnimationChannel(
					AnimationChannel.Targets.ROTATION,
					new Keyframe(0.5f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7f, KeyframeAnimations.degreeVec(0, 0, -45), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
}
// :::dancing_animation