package com.example.docs;

import com.example.docs.effect.ExampleModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

/**
 * A static-first class, used solely to provide version-aware
 * references to internal methods.
 */
public class ReferenceMethods {
	public static void addTaterEffect(LivingEntity entity) {
		// :::1
		var instance = new MobEffectInstance(ExampleModEffects.TATER, 5 * 20, 0, false, true, true);
		entity.addEffect(instance);
		// :::1
	}
}
