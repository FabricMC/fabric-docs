package com.example.docs.advancement;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * {@link UseToolCriterion} but with a parameter. Separated because there was no way to show the process to parameterize
 * in just one class.
 */
public class ParameterizedUseToolCriterion extends AbstractCriterion<ParameterizedUseToolCriterion.Conditions> {
	// :::datagen-advancements:new-trigger
	public void trigger(ServerPlayerEntity player, int totalTimes) {
		trigger(player, conditions -> conditions.requirementsMet(totalTimes));
	}

	// :::datagen-advancements:new-trigger

	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}

	// :::datagen-advancements:new-parameter
	public record Conditions(Optional<LootContextPredicate> playerPredicate, int requiredTimes) implements AbstractCriterion.Conditions {
		// :::datagen-advancements:new-parameter
		// :::datagen-advancements:new-codec
		public static Codec<ParameterizedUseToolCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				Codec.INT.fieldOf("requiredTimes").forGetter(Conditions::requiredTimes)
		).apply(instance, Conditions::new));
		// :::datagen-advancements:new-parameter
		@Override
		public Optional<LootContextPredicate> player() {
			return playerPredicate;
		}

		// :::datagen-advancements:new-requirements-met
		public boolean requirementsMet(int totalTimes) {
			return totalTimes > requiredTimes; // AbstractCriterion#trigger helpfully checks the playerPredicate for us.
		}

		// :::datagen-advancements:new-requirements-met
	}
}
