package com.example.docs.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

// :::1
public class TaterEffect extends StatusEffect {
	protected TaterEffect() {
		// category: StatusEffectCategory - describes if the effect is helpful (BENEFICIAL), harmful (HARMFUL) or useless (NEUTRAL)
		// color: int - Color is the color assigned to the effect (in RGB)
		super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
	}

	// Called every tick to check if the effect can be applied or not
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		// In our case, we just make it return true so that it applies the effect every tick
		return true;
	}

	// Called when the effect is applied
	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		if (entity instanceof PlayerEntity) {
			((PlayerEntity) entity).addExperience(1 << amplifier); // Higher amplifier gives you experience faster
		}
	}
}
// :::1
