package com.example.docs.gamerule;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRules;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;

import com.example.docs.ExampleMod;

// #region gamerule_class
public class ExampleModGameRules implements ModInitializer {
	// Create and register a boolean gamerule, disabled by default
	public static final GameRule<Boolean> BAD_VISION_BOOLEAN_GAMERULE = GameRuleBuilder
					.forBoolean(false) // Default value declaration
					.category(GameRuleCategory.MISC)
					.buildAndRegister(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "bad_vision"));
	// #endregion gamerule_class

	// #region double
	public static final GameRule<Double> DOUBLE_GAMERULE = GameRuleBuilder
					.forDouble(6.7) // Default value declaration
					.category(GameRuleCategory.MISC)
					.buildAndRegister(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "double_example"));
	// #endregion double

	private static void initializeBadVision() {
		// #region badvision_implement
		// In your mod's onInitialize():
		ServerTickEvents.END_LEVEL_TICK.register(serverLevel -> {
			// Runs every tick on the server
			// #endregion badvision_implement
			// #region vanilla
			boolean doMobGriefing = serverLevel.getGameRules().get(GameRules.MOB_GRIEFING);
			// #endregion vanilla
			// #region badvision_get
			// #region badvision_implement
			// Check for the state of the gamerule
			boolean badVisionEnabled = serverLevel.getGameRules().get(ExampleModGameRules.BAD_VISION_BOOLEAN_GAMERULE);
			// #endregion badvision_get

			if (badVisionEnabled) {
				// If the gamerule is true
				for (Player player : serverLevel.getPlayers(p -> true)) {
					// Apply blindness to every player
					player.addEffect(new MobEffectInstance(
									MobEffects.BLINDNESS,
									40,
									1,
									false,
									false,
									false
					));
				}
			}
		});
		// #endregion badvision_implement
	}

	@Override
	public void onInitialize() {
		initializeBadVision();
	}
	// #region gamerule_class
}
// #endregion gamerule_class
