package com.example.docs.advancement;

import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * {@link UseToolCriterion} but with a parameter. Separated because there was no way to show the process to parameterize
 * in just one class.
 */
public class ParameterizedUseToolCriterion extends SimpleCriterionTrigger<ParameterizedUseToolCriterion.Conditions> {
	// :::datagen-advancements:new-trigger
	public void trigger(ServerPlayer player, int totalTimes) {
		trigger(player, conditions -> conditions.requirementsMet(totalTimes));
	}

	// :::datagen-advancements:new-trigger

	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}

	// :::datagen-advancements:new-parameter
	public record Conditions(Optional<ContextAwarePredicate> playerPredicate, int requiredTimes) implements SimpleCriterionTrigger.SimpleInstance {
		// :::datagen-advancements:new-parameter
		// :::datagen-advancements:new-codec
		public static Codec<ParameterizedUseToolCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				Codec.INT.fieldOf("requiredTimes").forGetter(Conditions::requiredTimes)
		).apply(instance, Conditions::new));
		// :::datagen-advancements:new-parameter
		@Override
		public Optional<ContextAwarePredicate> player() {
			return playerPredicate;
		}

		// :::datagen-advancements:new-requirements-met
		public boolean requirementsMet(int totalTimes) {
			return totalTimes > requiredTimes; // AbstractCriterion#trigger helpfully checks the playerPredicate for us.
		}

		// :::datagen-advancements:new-requirements-met
	}
}
