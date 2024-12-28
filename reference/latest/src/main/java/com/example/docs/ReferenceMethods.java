package com.example.docs;

import com.example.docs.effect.FabricDocsReferenceEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

/**
 * A static-first class, used solely to provide version-aware
 * references to internal methods.
 */
public class ReferenceMethods {
	public static void addTaterEffect(LivingEntity entity) {
		// :::1
		var instance = new StatusEffectInstance(FabricDocsReferenceEffects.TATER, 5 * 20, 0, false, true, true);
		entity.addStatusEffect(instance);
		// :::1
	}
}
