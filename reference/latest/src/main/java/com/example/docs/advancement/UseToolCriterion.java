package com.example.docs.advancement;

import java.util.Optional;

import com.mojang.serialization.Codec;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

// :::datagen-advancements:criterion-base
public class UseToolCriterion extends SimpleCriterionTrigger<UseToolCriterion.Conditions> {
	// :::datagen-advancements:criterion-base
	// :::datagen-advancements:criterion-trigger
	public void trigger(ServerPlayer player) {
		trigger(player, Conditions::requirementsMet);
	}

	// :::datagen-advancements:criterion-trigger
	// :::datagen-advancements:criterion-base

	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}

	public record Conditions(Optional<ContextAwarePredicate> playerPredicate) implements SimpleCriterionTrigger.SimpleInstance {
		public static Codec<UseToolCriterion.Conditions> CODEC = ContextAwarePredicate.CODEC.optionalFieldOf("player")
				.xmap(Conditions::new, Conditions::player).codec();

		@Override
		public Optional<ContextAwarePredicate> player() {
			return playerPredicate;
		}

		// :::datagen-advancements:criterion-base
		// :::datagen-advancements:conditions-test
		public boolean requirementsMet() {
			return true; // AbstractCriterion#trigger helpfully checks the playerPredicate for us.
		}

		// :::datagen-advancements:conditions-test
		// :::datagen-advancements:criterion-base
	}
}
// :::datagen-advancements:criterion-base
