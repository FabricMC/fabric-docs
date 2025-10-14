package com.example.docs.advancement;

import java.util.Optional;

import com.mojang.serialization.Codec;

import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

// :::datagen-advancements:criterion-base
public class UseToolCriterion extends AbstractCriterion<UseToolCriterion.Conditions> {
	// :::datagen-advancements:criterion-base
	// :::datagen-advancements:criterion-trigger
	public void trigger(ServerPlayerEntity player) {
		trigger(player, Conditions::requirementsMet);
	}

	// :::datagen-advancements:criterion-trigger
	// :::datagen-advancements:criterion-base

	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}

	public record Conditions(Optional<LootContextPredicate> playerPredicate) implements AbstractCriterion.Conditions {
		public static Codec<UseToolCriterion.Conditions> CODEC = LootContextPredicate.CODEC.optionalFieldOf("player")
				.xmap(Conditions::new, Conditions::player).codec();

		@Override
		public Optional<LootContextPredicate> player() {
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
