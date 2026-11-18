package com.example.docs.advancement;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

/**
 * {@link UseToolCriterion} but with a parameter. Separated because there was no way to show the process to parameterize
 * in just one class.
 */
public class ParameterizedUseToolCriterion extends SimpleCriterionTrigger<ParameterizedUseToolCriterion.Conditions> {
	// #region datagen_advancements_new_trigger
	public void trigger(ServerPlayer player, int totalTimes) {
		this.trigger(player, conditions -> conditions.requirementsMet(totalTimes));
	}

	// #endregion datagen_advancements_new_trigger

	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}

	// #region datagen_advancements_new_parameter
	public record Conditions(Optional<ContextAwarePredicate> playerPredicate, int requiredTimes) implements SimpleCriterionTrigger.SimpleInstance {
		// #endregion datagen_advancements_new_parameter
		// #region datagen_advancements_new_codec
		public static Codec<ParameterizedUseToolCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				Codec.INT.fieldOf("requiredTimes").forGetter(Conditions::requiredTimes)
		).apply(instance, Conditions::new));
		// #endregion datagen_advancements_new_codec
		// #region datagen_advancements_new_parameter
		@Override
		public Optional<ContextAwarePredicate> player() {
			return this.playerPredicate;
		}

		// #region datagen_advancements_new_requirements_met
		public boolean requirementsMet(int totalTimes) {
			return totalTimes > this.requiredTimes; // AbstractCriterion#trigger helpfully checks the playerPredicate for us.
		}
		// #endregion datagen_advancements_new_requirements_met
	}
	// #endregion datagen_advancements_new_parameter
}
