package com.example.docs;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

import com.example.docs.effect.ExampleModEffects;

/**
 * A static-first class, used solely to provide version-aware
 * references to internal methods.
 */
public class ReferenceMethods {
	public static void addTaterEffect(LivingEntity entity) {
		// :::1
		var instance = new StatusEffectInstance(ExampleModEffects.TATER, 5 * 20, 0, false, true, true);
		entity.addStatusEffect(instance);
		// :::1
	}
}
