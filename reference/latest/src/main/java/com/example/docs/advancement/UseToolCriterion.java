package com.example.docs.advancement;

import java.util.Optional;

import com.mojang.serialization.Codec;

import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

// #region datagen_advancements__criterion_base
public class UseToolCriterion extends SimpleCriterionTrigger<UseToolCriterion.Conditions> {
	// #endregion datagen_advancements__criterion_base
	// #region datagen_advancements__criterion_trigger
	public void trigger(ServerPlayer player) {
		trigger(player, Conditions::requirementsMet);
	}
	// #endregion datagen_advancements__criterion_trigger
	// #region datagen_advancements__criterion_base

	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}

	public record Conditions(Optional<ContextAwarePredicate> playerPredicate) implements SimpleCriterionTrigger.SimpleInstance {
		public static Codec<UseToolCriterion.Conditions> CODEC = ContextAwarePredicate.CODEC.optionalFieldOf("player")
				.xmap(Conditions::new, Conditions::player).codec();

		@Override
		public Optional<ContextAwarePredicate> player() {
			return this.playerPredicate;
		}
		// #endregion datagen_advancements__criterion_base

		// #region datagen_advancements__conditions_test
		public boolean requirementsMet() {
			return true; // AbstractCriterion#trigger helpfully checks the playerPredicate for us.
		}
		// #endregion datagen_advancements__conditions_test
		// #region datagen_advancements__criterion_base
	}
}
// #endregion datagen_advancements__criterion_base
