package com.example.docs.gamerule;

import com.example.docs.ExampleMod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRules;

// :::gameruleClass
public class ExampleModGamerules implements ModInitializer {
	// Create and register a boolean gamerule, disabled by default
	public static final Identifier BADVISION_GAMERULE_IDENTIFIER = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "bad_vision");
	public static final GameRule<Boolean> BADVISION_BOOLEAN_GAMERULE = GameRuleBuilder
					.forBoolean(false) //default value declaration
					.category(GameRuleCategory.MISC)
					.buildAndRegister(BADVISION_GAMERULE_IDENTIFIER);
	// :::gameruleClass

	public static final Identifier DOUBLE_GAMERULE_IDENTIFIER = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "double_example");
	// :::double
	public static final GameRule<Double> DOUBLE_GAMERULE = GameRuleBuilder
					.forDouble(6.7) //default value declaration
					.category(GameRuleCategory.MISC)
					.buildAndRegister(DOUBLE_GAMERULE_IDENTIFIER);
	// :::double

	private static void initializeBadVision() {
		// :::badvision
		// In your mod's onInitialize():
		ServerTickEvents.END_WORLD_TICK.register(serverLevel -> {
			// runs every tick on the server
			// :::badvision
			// :::access
			boolean gameruleState = serverLevel.getGameRules().get(ExampleModGamerules.BADVISION_BOOLEAN_GAMERULE);
			// :::access
			// :::vanilla
			boolean doMobGriefing = serverLevel.getGameRules().get(GameRules.MOB_GRIEFING);
			// :::vanilla
			// :::badvision
			// Check for the state of the gamerule
			boolean badvisionGameruleStatus = serverLevel.getGameRules().get(ExampleModGamerules.BADVISION_BOOLEAN_GAMERULE);

			if (badvisionGameruleStatus) {
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
		// :::badvision
	}

	@Override
	public void onInitialize() {
		initializeBadVision();
	}
	// :::gameruleClass
}
// :::gameruleClass
