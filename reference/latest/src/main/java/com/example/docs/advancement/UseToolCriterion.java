package com.example.docs.advancement;

import java.util.Optional;

import com.mojang.serialization.Codec;

import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

// #region datagen-advancements--criterion-base
public class UseToolCriterion extends SimpleCriterionTrigger<UseToolCriterion.Conditions> {
	// #endregion datagen-advancements--criterion-base
	// #region datagen-advancements--criterion-trigger
	public void trigger(ServerPlayer player) {
		trigger(player, Conditions::requirementsMet);
	}
	// #endregion datagen-advancements--criterion-trigger
	// #region datagen-advancements--criterion-base

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
		// #endregion datagen-advancements--criterion-base

		// #region datagen-advancements--conditions-test
		public boolean requirementsMet() {
			return true; // AbstractCriterion#trigger helpfully checks the playerPredicate for us.
		}
		// #endregion datagen-advancements--conditions-test
		// #region datagen-advancements--criterion-base
	}
}
// #endregion datagen-advancements--criterion-base
